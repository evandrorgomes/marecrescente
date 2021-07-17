import { Component, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Perfis } from 'app/shared/enums/perfis';
import { PedidoExame } from '../../../laboratorio/pedido.exame';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { StatusPedidosExame } from '../../../shared/enums/status.pedidos.exame';
import { MessageBox } from '../../../shared/modal/message.box';
import { PacienteUtil } from '../../../shared/paciente.util';
import { ModalPedidoExameFase3Component } from '../../busca/pedidoexame/paciente/modal.pedido.exame.fase3.component';
import { Paciente } from '../../paciente';
import { PacienteService } from '../../paciente.service';
import { TiposExame } from './../../../shared/enums/tipos.exame';
import { GenotipoDTO } from './../../genotipo.dto';
import { StringUtil } from 'app/shared/util/string.util';


/**
 * Classe generica para exibição do header de paciente.
 * A classe não tem comportamento customizado (a princípio),
 * apenas centraliza o formato do HTML a todos os headers.
 * A utilização consiste em informar um texto com binding nos
 * atributos que devem ser exibidos por referência.
 * Ex.: <header-paciente texto="Meu nome é {{paciente.nome}}"/>
 *
 * TODO: Ajustar o template, assim que o HTML sugerido estiver pronto.
 *
 * @author Pizão
 */
@Component({
    selector: 'header-paciente',
    templateUrl: './header.paciente.component.html',
    styleUrls: ['./header.paciente.component.css'],
    encapsulation: ViewEncapsulation.Emulated

})
export class HeaderPacienteComponent implements OnInit {

    public chaveParcial: string = "_identificacao"
    protected chaveGenotipo: string = "_genotipo";

    public isContempladoPortaria: string;
    sexo: string;
    private labelsInternacionalizadas;
    idade: string;
    cid: string;
    descricaoCid: string;
    nomeMedicoResponsavel: string;
    statusPaciente: string;
    genotipoLista: GenotipoDTO[] = [];
    peso:string;
    exibirGenotipo:boolean = false;
    ultimoPedidoExame:PedidoExame = null;
    exameFase3Data:any = null;
    private _exibirBotaoPedirExame3Fase: boolean = false;
    private _exibirDivGenotipo:boolean = false;
    private _deveExibirBotaoFase3:boolean = false;
    private _deveExibirStatus:boolean = false;
    private _statusExame:string;
    private _pediuExame:boolean = false;

    textoBotaoGenotipo:string;
    /**
     * Recebe as labels internacionalizadas da tela.
     */
    private anoLabel: string;
    private _paciente: Paciente;

    @Input()
    public exibirSomenteDadosPessoais: boolean = false;


    constructor(private translate: TranslateService,
         private autenticacaoService: AutenticacaoService,
          private pacienteService: PacienteService, private messageBox: MessageBox) {}

    ngOnInit(): void {

    }

    popularCabecalhoIdentificacaoPorPaciente(rmr: number) {
        this.translate.get("botao.exibirGenotipo").subscribe(res => {
            this.textoBotaoGenotipo = res;
        });
        this.translate.get("pacienteForm.evolucaoGroup").subscribe(res => {
            this.anoLabel = res;
            this.labelsInternacionalizadas = res;

            let loadPacienteDoBack: boolean = true;
            let pacienteRecuperado: Paciente;
            let identificacao: string = localStorage.getItem(this.autenticacaoService.usuarioLogado().username + this.chaveParcial);
            if (!StringUtil.isNullOrEmpty(identificacao)) {
                pacienteRecuperado = JSON.parse(identificacao);
                if (pacienteRecuperado.rmr == rmr) {
                    this.popularCabecalhoIdentificacao(pacienteRecuperado);
                    loadPacienteDoBack = false;
                }
            }

            if (loadPacienteDoBack) {
                this.pacienteService.obterIdentificacaoPacientePorRmr(rmr).then((result) => {
                    this.popularCabecalhoIdentificacao(result);
                    localStorage.setItem(this.autenticacaoService.usuarioLogado().username + this.chaveParcial, JSON.stringify(this._paciente));
                });
            }

            if(this.exibirBotaoPedirExame3Fase){
                this._deveExibirBotaoFase3 = (!this.ultimoPedidoExame) || (this.ultimoPedidoExame && this.ultimoPedidoExame.statusPedidoExame.id === StatusPedidosExame.CANCELADO) ;
                if(!this._deveExibirBotaoFase3){
                    this._statusExame = this.ultimoPedidoExame.statusPedidoExame.descricao;
                    this._deveExibirStatus = true;
                }
            }
        });
    }
    /**
     * Metodo que recebe o paciente e prepara os dados para serem exibidas no html
     *
     * @param {Paciente} paciente
     * @memberof HeaderPacienteComponent
     */
    private popularCabecalhoIdentificacao(paciente) {
        this._paciente = paciente;

        if (paciente.diagnostico.cid.transplante) {
            this.isContempladoPortaria = 'azul';
        } else {
            this.isContempladoPortaria = 'amarelo';
        }

        this.sexo = this.criarGeneroDecorator(paciente.sexo);

        PacienteUtil.retornarIdadeFormatada(paciente.dataNascimento, this.translate).then(res => {
            this.idade = res;
        })

        if(this.temPermissaoParaObterStatusPacientePorRmr()){
            // Retorna o status do paciente.
            this.popularHeaderStatusPaciente(paciente.rmr);
        }

        this.cid = paciente.diagnostico.cid.codigo;
        this.descricaoCid = paciente.diagnostico.cid.descricao;
        this.nomeMedicoResponsavel = null;

        this.autenticacaoService.usuarioLogado().perfis.forEach(perfil => {
            if(perfil == Perfis.AVALIADOR && paciente.medicoResponsavel) {
                this.nomeMedicoResponsavel = paciente.medicoResponsavel.nome;
            }
        });

        PacienteUtil.retornarPesoFormatado( this._paciente.evolucoes[0].peso, this.translate).then(res => {
            this.peso = res;
        });

        if (this.exibirGenotipo && paciente.genotipo && paciente.genotipo.valores) {
            this.popularGenotipo(paciente);
        }
    }

    private popularGenotipo(paciente: any) {
        let locus: string[] = [];
        paciente.genotipo.valores.forEach(x => {
            let existe: boolean = false;
            locus.forEach(y => {
                if (x.locus.codigo === y) {
                    existe = true;
                }
            });
            if (!existe) {
                locus.push(x.locus.codigo);
            }
        });
        locus.forEach(l => {
            let genotipo: GenotipoDTO = new GenotipoDTO();
            genotipo.locus = l;
            genotipo.primeiroAlelo = paciente.genotipo.valores.find(x => x.locus.codigo == l && x.posicao == 1).alelo;
            genotipo.segundoAlelo = paciente.genotipo.valores.find(x => x.locus.codigo == l && x.posicao == 2).alelo;
            this.genotipoLista.push(genotipo);
        });
    }

    private criarGeneroDecorator(sexo: string): string {
        return (sexo === "M") ? this.labelsInternacionalizadas['homem'] : this.labelsInternacionalizadas['mulher'];
    }

	public get paciente(): Paciente {
		return this._paciente;
    }
    public exibirOcultarDivGenotipo(){
        if(this._exibirDivGenotipo){
            this.translate.get("botao.exibirGenotipo").subscribe(res => {
                this.textoBotaoGenotipo = res;
            });
            this._exibirDivGenotipo = false;
        }
        else{
            this.translate.get("botao.ocultarGenotipo").subscribe(res => {
                this.textoBotaoGenotipo = res;
            });
            this._exibirDivGenotipo = true;
        }
    }

    private exibirModalSolicitarFase3(tipo:any): void {

        if(tipo == 0){
            this.exameFase3Data.idTipoExame = TiposExame.TESTE_CONFIRMATORIO;
        }else if(tipo == 1){
            this.exameFase3Data.idTipoExame = TiposExame.TESTE_CONFIRMATORIO_SWAB;
        }else if(tipo == 6){
            this.exameFase3Data.idTipoExame = TiposExame.TESTE_CONFIRMATORIO_SOMENTE_SWAB;
        }

        let modal = this.messageBox.dynamic(this.exameFase3Data, ModalPedidoExameFase3Component);
        modal.target = this;
        modal.closeOption = (target?: any) => {
            this.resultadoModal(target);
        }
        modal.show();
    }

    private resultadoModal(headerReferencia:any){
        if(this.pediuExame){
            this.translate.get("pedidoexamefase3.statusAguardandoAmostra").subscribe(res => {
                this._statusExame = res;
            });
            headerReferencia.deveExibirBotaoFase3 = false;
            headerReferencia.deveExibirBotaoSwabFase3 = false;
            headerReferencia._deveExibirStatus = true;
            headerReferencia.exibirOcultarDivGenotipo();
        }
    }


    /**
     * Getter exibirDivGenotipo
     * @return {boolean }
     */
	public get exibirDivGenotipo(): boolean  {
		return this._exibirDivGenotipo;
	}

    /**
     * Setter exibirDivGenotipo
     * @param {boolean } value
     */
	public set exibirDivGenotipo(value: boolean ) {
		this._exibirDivGenotipo = value;
	}


    /**
     * Getter deveExibirBotaoFase3
     * @return {boolean }
     */
	public get deveExibirBotaoFase3(): boolean  {
		return this._deveExibirBotaoFase3;
	}

    /**
     * Setter deveExibirBotaoFase3
     * @param {boolean } value
     */
	public set deveExibirBotaoFase3(value: boolean ) {
		this._deveExibirBotaoFase3 = value;
	}


    /**
     * Getter statusExame
     * @return {string}
     */
	public get statusExame(): string {
		return this._statusExame;
	}

    /**
     * Setter statusExame
     * @param {string} value
     */
	public set statusExame(value: string) {
		this._statusExame = value;
    }

    /**
     * Getter pediuExame
     * @return {boolean }
     */
	public get pediuExame(): boolean  {
		return this._pediuExame;
	}

    /**
     * Setter pediuExame
     * @param {boolean } value
     */
	public set pediuExame(value: boolean ) {
		this._pediuExame = value;
    }

    /**
     * Getter deveExibirStatus
     * @return {boolean }
     */
	public get deveExibirStatus(): boolean  {
		return this._deveExibirStatus;
	}

    /**
     * Popula o cabeçalho, status do paciente.
     *
     * @param rmr do paciente.
     */
    public popularHeaderStatusPaciente(rmr: number) {
        // Retorna o status do paciente.
        this.pacienteService.obterStatusPacientePorRmr(rmr).then(res => {
            this.statusPaciente = res.descricao;
        });
    }

    public temPermissaoParaObterStatusPacientePorRmr(): boolean {
        return this.autenticacaoService.temRecurso('VISUALIZAR_IDENTIFICACAO_COMPLETA') ||
               this.autenticacaoService.temRecurso('VISUALIZAR_IDENTIFICACAO_PARCIAL');
    }

    public get exibirBotaoPedirExame3Fase(): boolean {
        return this._exibirBotaoPedirExame3Fase;
    }

    public set exibirBotaoPedirExame3Fase(value: boolean) {
        this._exibirBotaoPedirExame3Fase = value;
    }

    public removeItemPorChaveParcial(){
        localStorage.removeItem(this.autenticacaoService.usuarioLogado().username + this.chaveParcial);
    }
}

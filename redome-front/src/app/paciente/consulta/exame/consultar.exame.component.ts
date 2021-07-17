import { FiltroConsultaModule } from 'app/admin/crud/consulta/filtro/filtro.consulta.module';
import { Location } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PedidoExameService } from '../../../laboratorio/pedido.exame.service';
import { ExamePaciente } from '../../cadastro/exame/exame.paciente';
import { CampoMensagem } from "../../../shared/campo.mensagem";
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { ErroMensagem } from '../../../shared/erromensagem';
import { StatusExame } from '../../../shared/enums/status.exame';
import { MensagemModalComponente } from '../../../shared/modal/mensagem.modal.component';
import { PacienteUtil } from '../../../shared/paciente.util';
import { ArquivoExame } from '../../cadastro/exame/arquivo.exame';
import { ArquivoExameService } from '../../cadastro/exame/arquivo.exame.service';
import { PacienteService } from '../../paciente.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { ExameService } from '../../cadastro/exame/exame.service';
import { GenotipoDTO } from '../../genotipo.dto';
import { GenotipoService } from '../../genotipo.service';
import { HeaderPacienteComponent } from '../identificacao/header.paciente.component';
import { ExameDTO } from './exame.dto';
import { ArrayUtil } from '../../../shared/util/array.util';
import { Recursos } from 'app/shared/enums/recursos';
import { timeout } from 'rxjs/operator/timeout';
import { TiposAmostra } from 'app/shared/enums/tipos.amostra';
import { TiposExame } from 'app/shared/enums/tipos.exame';


@Component({
    selector: 'consultar-exame',
    moduleId: module.id,
    templateUrl: './consultar.exame.component.html'
})

export class ConsultarExameComponent implements PermissaoRotaComponente, OnInit {

    /**
     * Representa o RMR selecionado anteriormente (paciente).
     * É este atributo que define de quem é as informações exibidas na tela.
     */
    public rmrSelecionado: number;

    idExameSelecionado: number;

    public descricaoTipoAmostra: string;

    /**
     * Recebe todas as informações a serem exibidas na tela.
     */
    public dtoExame: ExameDTO = new ExameDTO();


    /**
     * Recebe as labels internacionalizadas da tela.
     */
    public labels: Object = {};

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild("exameDescartadoModal")
    private modalMsgExameDescartado;

    @ViewChild("modalMsg")
    private modalMsg;
    /**
     * Referência utilizadas para exibir informações no HTML.
     */
    public exameSelecionado: ExamePaciente = new ExamePaciente();

    private static TIPOS_SOMENTE_DOWNLOAD: string[] = [".tif", ".tiff"];

    private _temPermissaoNovoExame: boolean;

    genotipoDTO: GenotipoDTO[] = [];

    /**
     * Indica quando o paciente possui exames disponíveis para exibição.
     * São retirados dessa lista os exames com status Descartados, somente os
     * Não Verificados e os Aceitos são exibidos. 
     */
    private _temExamesParaExibicao: boolean = true;

    /**
     * Mensagem que deverá ser exibida no {{exameDescartadoModal}}
     */
    mensagemModal: string;

    pacienteEmObito: boolean;

    /**
     * Modal invocado quando o exame é descartado no back-end e 
     * é solicitada a exibição pelo front-end. 
     */
    @ViewChild("exameDescartadoModal")
    private exameDescartadoModal: MensagemModalComponente;

    /**
     * Indica quando existe um genótipo para exibição.
     */
    public temGenotipoParaExibicao: boolean = true;


    constructor(private translate: TranslateService,
        private pacienteService: PacienteService,
        private exameService: ExameService,
        private activatedRoute: ActivatedRoute,
        private arquivoExameService: ArquivoExameService,
        private autenticacaoService: AutenticacaoService,
        private location: Location, private router: Router,
        private genotipoService: GenotipoService,
        private pedidoExameService: PedidoExameService) {

        this.carregarInformacoesTela();
    }

    ngOnInit(): void {
        // Carrega os dados das labels (internacionalização).
        this.translate.get("pacienteForm.exameGroup").subscribe(res => {
            this.labels = res;
        });

        Promise.resolve(this.headerPaciente).then(() => {
            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmrSelecionado);
        });

        this.activatedRoute.queryParamMap.subscribe(queryParam => {
            if (queryParam.keys.length != 0) {
                this.idExameSelecionado = Number(queryParam.get('idExame'));
            }

        });

        this.genotipoService.obterGenotipo(this.rmrSelecionado).then(res => {
            this.genotipoDTO = res;
            this.temGenotipoParaExibicao = ArrayUtil.isNotEmpty(this.genotipoDTO);

        }).catch((erro: ErroMensagem) => {
            this.tratarErro(erro);
        });

    }

    private tratarErro(erro: ErroMensagem) {
        let mensagem = "";
        if (erro.listaCampoMensagem && erro.listaCampoMensagem.length >= 0) {
            erro.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                mensagem += campoMensagem.mensagem;
            })
        }
        else {
            mensagem = erro.mensagem.toString();
        }
        if (mensagem == "") {
            mensagem = erro.statusText.toString();
        }
        this.modalMsg.mensagem = mensagem;
        this.modalMsg.abrirModal();
    }


    private carregarInformacoesTela() {
        // Capturar parâmetro vindo da URL.
        this.activatedRoute.params.subscribe(params => {
            this.rmrSelecionado = params['idPaciente'];
            this.idExameSelecionado = params['idExame'];

            this.carregarExamesParaTela();
        });
    }
    private usuarioLogadoEhMedicoResponsavelPaciente(): Promise<boolean> {
        let usernameMedico: string = "";
        return this.pacienteService.obterFichaPaciente(this.rmrSelecionado).then(result => {
            usernameMedico = result.medicoResponsavel.usuario.username;
            return this.autenticacaoService.usuarioLogado().username == usernameMedico;
        }).catch(erro => {
            return false;
        });

    }

    /**
     * 
     * Método que seleciona um exame na combo.
     * Se o exame estiver descartado, exibe o modal informando
     * e atualiza a lista da combo.
     * 
     * @param {number} exameId 
     * @memberof ConsultarExameComponent
     */
    public selecionarExame(exameId: number): void {
        this.exameService.obterExame(exameId)
            .then(result => {
                this.exameSelecionado = result;
            }, (error: ErroMensagem) => {
                this.mensagemModal = error.listaCampoMensagem[0].mensagem;
                this.exameDescartadoModal.abrirModal();
                this.carregarInformacoesTela();
            });
    }

    private formatarExame(exame: ExamePaciente): string {
        let todosLocusDoExameConcatenados: string;
        exame.locusExames.forEach(locus => {
            if (!todosLocusDoExameConcatenados) {
                todosLocusDoExameConcatenados = locus.id.locus.codigo + "*"
            }
            else {
                todosLocusDoExameConcatenados = todosLocusDoExameConcatenados + ", " + locus.id.locus.codigo + "*"
            }
        })
        let labelComboExame = PacienteUtil.converterStringEmData(exame.dataExame).toLocaleDateString() + " - " + todosLocusDoExameConcatenados;
        if (exame.statusExame == StatusExame.NAO_VERIFICADO) {
            labelComboExame += " (" + this.labels["nao_conferido"] + ")";
        }
        return labelComboExame;
    }

    private retornaNomeArquivoExameSelecionado(caminhoArquivo: String): String {
        let indexDaBarra = caminhoArquivo.indexOf("/") + 1;
        caminhoArquivo = caminhoArquivo.substring(indexDaBarra, caminhoArquivo.length);
        let indexDoUnderline = caminhoArquivo.indexOf("_");
        caminhoArquivo = caminhoArquivo.substring(indexDoUnderline + 1);
        return caminhoArquivo;
    }

    /**
     * Faz a chamada para o download do arquivo no servidor REST. 
     * 
     * @param arquivoExame 
     */
    public baixarArquivo(arquivoExame: ArquivoExame) {
        this.arquivoExameService.baixarArquivoExame(
            arquivoExame.id,
            this.retornaNomeArquivoExameSelecionado(arquivoExame.caminhoArquivo));
    }

    /**
     * Faz a chamada para visualização do arquivo.
     * 
     * @param arquivoExame 
     */
    public visualizarArquivo(arquivoExame: ArquivoExame) {
        this.arquivoExameService.visualizarArquivoExame(arquivoExame.id);
    }

    /**
     * Faz a chamada para o download do arquivo zipado.
     * 
     * @param arquivoExame 
     */
    public baixarArquivoZipado(arquivoExame: ArquivoExame) {
        this.arquivoExameService.baixarArquivoExameZipado(arquivoExame.id,
            this.retornaNomeArquivoExameSelecionado(arquivoExame.caminhoArquivo));
    }

    /**
     * Faz a chamada para o download do arquivo zipado.
     * 
     * @param arquivoExame 
     */
    public baixarTodosArquivosZipados(exame: ExamePaciente) {
        this.exameService.baixarArquivosExamesZipados(exame.id, "laudo_exame");
    }

    /**
     * Verifica se o arquivo poderá ser visualizado ou somente baixado.
     * 
     * @param nomeArquivo 
     */
    public verificarExtensaoPermiteVisualizar(nomeArquivo: string): boolean {
        let extensao: string = nomeArquivo.substring(nomeArquivo.lastIndexOf("."));
        let tipoEncontrado: string[] =
            ConsultarExameComponent.TIPOS_SOMENTE_DOWNLOAD
                .filter(ext => ext === extensao);

        return (tipoEncontrado.length > 0);
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarExameComponent];
    }

    /**
     * Lista os exames relativos ao paciente selecionado.
     * Somente exames não descartados serão exibidos.     * 
     */
    private carregarExamesParaTela(): void {
        this.pacienteService.listarExamesPorPaciente(this.rmrSelecionado)
            .then(result => {
                this.dtoExame = <ExameDTO>result;
                this.pacienteEmObito = this.dtoExame.pacienteEmObito;
                this._temExamesParaExibicao =
                    this.dtoExame.exames != null && this.dtoExame.exames.length > 0;

                if (this._temExamesParaExibicao) {
                    if (this.idExameSelecionado) {
                        this.dtoExame.exames.forEach((exame: ExamePaciente) => {
                            if (exame.id == this.idExameSelecionado) {
                                this.selecionarExame(exame.id);
                            }
                        });
                    }
                    else {
                        this.exameSelecionado = this.dtoExame.exameSelecionado;
                        this.idExameSelecionado = this.exameSelecionado.id;
                    }
                }

                if (this._temExamesParaExibicao) {
                    this.listTiposAmostraExame().filter(tipo => {
                            if(this.exameSelecionado.tipoAmostra === tipo.id){
                                return this.descricaoTipoAmostra = tipo.descricao;
                            }
                    });
                }

                this.usuarioLogadoEhMedicoResponsavelPaciente().then(resp => {
                    this._temPermissaoNovoExame = this.autenticacaoService
                        .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.NovoExameComponent])
                        && resp && !this.dtoExame.buscaEmMatch;
                }, (error: ErroMensagem) => {
                    throw new Error("Erro ocorreu ao buscar de permissão de criação de exame.");
                });
            }, (error: ErroMensagem) => {
                throw new Error("Erro ocorreu ao buscar informações na consulta de exame.");
            });

    }

    public get temPermissaoNovoExame(): boolean {
        return this._temPermissaoNovoExame;
    }

	public get temExamesParaExibicao(): boolean  {
		return this._temExamesParaExibicao;
    }
    
    public deveExibirLaboratorio(): boolean {
        return (this.exameSelecionado != null && this.exameSelecionado.laboratorio != null  && this.exameSelecionado.laboratorio.id != null);
    }

    public deveExibirLaboratorioParticular(): boolean {
        return (this.exameSelecionado != null && this.exameSelecionado.laboratorioParticular != null && this.exameSelecionado.laboratorioParticular == true);
    }
    
    public baixarInstrucaoColeta(): void {
        setTimeout(() => {
            this.pedidoExameService.downloadInstrucaoColetaSangueCTSwab(this.dtoExame.idBusca, this.dtoExame.idTipoExame,
                this.obterNomearquivo());
        }, 900);
    }

    public exibirInstrucaoColetaExameCT(): boolean {
        return this.dtoExame.idTipoExame === TiposExame.TESTE_CONFIRMATORIO
                || this.dtoExame.idTipoExame === TiposExame.TESTE_CONFIRMATORIO_SWAB
                || this.dtoExame.idTipoExame === TiposExame.TESTE_CONFIRMATORIO_SOMENTE_SWAB;
    }

    public obterNomearquivo(){
        if(this.dtoExame.idTipoExame === TiposExame.TESTE_CONFIRMATORIO){
            return 'instrucao_coleta_sangue_CT.pdf';
        }else if(this.dtoExame.idTipoExame === TiposExame.TESTE_CONFIRMATORIO_SWAB){
            return 'instrucao_coleta_swab_oral_CT.pdf';
        }else{
            return 'instrucao_coleta_sangue_swab_oral_CT.pdf';
        }
    }


    /**
     * @description Lista todos os tipos de amostra
     * @author ergomes
     * @public
     */
    public listTiposAmostraExame(): any[] {
        const sangue = {
            id: TiposAmostra.SANGUE,
            descricao: this.labels['sangue'],
        }
        const swab = {
            id: TiposAmostra.SWAB,
            descricao: this.labels['swab'],
        }
        const sangueSwab = {
            id: TiposAmostra.SANGUE_SWAB,
            descricao: this.labels['sangueSwab'],
        }

        return [sangue, swab, sangueSwab];
    }
}
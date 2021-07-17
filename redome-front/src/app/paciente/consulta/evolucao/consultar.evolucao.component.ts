import { Location } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Busca } from 'app/paciente/busca/busca';
import { BuscaService } from 'app/paciente/busca/busca.service';
import { TiposBuscaChecklist } from 'app/shared/enums/tipos.buscaChecklist';
import { BuscaChecklistService } from 'app/shared/service/busca.checklist.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { ErroMensagem } from "../../../shared/erromensagem";
import { PacienteUtil } from '../../../shared/paciente.util';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { ArquivoEvolucao } from '../../cadastro/evolucao/arquivo.evolucao';
import { Evolucao } from '../../cadastro/evolucao/evolucao';
import { EvolucaoService } from '../../cadastro/evolucao/evolucao.service';
import { PacienteService } from '../../paciente.service';
import { HeaderPacienteComponent } from "../identificacao/header.paciente.component";
import { EvolucaoDTO } from './evolucao.dto';
import { TipoTransplante } from 'app/shared/dominio/tipoTransplante';
import { DataUtil } from 'app/shared/util/data.util';
import { DateTypeFormats } from 'app/shared/util/date/date.type.formats';

@Component({
    selector: 'consultar-evolucao',
    moduleId: module.id, 
    templateUrl: './consultar.evolucao.component.html'
})

export class ConsultarEvolucaoComponent implements PermissaoRotaComponente, OnInit {

    /**
     * Representa o RMR selecionado anteriormente (paciente).
     * É este atributo que define de quem é as informações exibidas na tela.
     */
    public rmrSelecionado:number;

    private idEvolucaoSelecionada: number;

    /**
     * Recebe todas as informações a serem exibidas na tela.
     */
    public dto:EvolucaoDTO;

    /**
     * Recebe as labels internacionalizadas da tela.
     */
    public labels:Object = {};

    /**
     * Referência utilizadas para exibir informações no HTML.
     */
    public textoEmDestaque:string;
    public texto:string;
    public evolucaoSelecionada:Evolucao;
    public evolucoes:Evolucao[];
    private pacienteEmObito:boolean = false;

    private _temPermissaoNovaEvolucao:boolean;
    public _temPerfilAnalistaBusca: boolean= false;
    public _busca: Busca;

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild(ConsultarEvolucaoComponent)
    private form: ConsultarEvolucaoComponent;


    constructor(private translate: TranslateService, 
                private pacienteService:PacienteService,
                private evolucaoService:EvolucaoService,
                private serviceBusca:BuscaService,
                private buscaChecklistService: BuscaChecklistService,
                private activatedRouter: ActivatedRoute,
                private autenticacaoService: AutenticacaoService,
                private location: Location, private router: Router) {

        // Capturar parâmetro vindo da URL.
        this.activatedRouter.params.subscribe(params => {
            this.rmrSelecionado = params['idPaciente']; 
            this.idEvolucaoSelecionada = params['idEvolucao']; 
            this.pacienteService.listarEvolucoesPorPaciente(this.rmrSelecionado)
                        .then(result => {
                            this.dto = <EvolucaoDTO> result;
                            this.pacienteEmObito = this.dto.pacienteEmObito;
                            this.evolucoes = this.dto.evolucoes;
                            if (this.idEvolucaoSelecionada) {
                                this.evolucoes.forEach((evolucao: Evolucao) => {
                                    if (this.idEvolucaoSelecionada == evolucao.id) {
                                        this.selecionarEvolucao(this.idEvolucaoSelecionada);
                                    }
                                })
                            }
                            else {
                                this.idEvolucaoSelecionada = this.dto.evolucaoSelecionada.id;
                                this.evolucaoSelecionada = new Evolucao().jsonToEntity(this.dto.evolucaoSelecionada);
                                this._temPermissaoNovaEvolucao = this.autenticacaoService
                                    .temPermissaoAcesso(ComponenteRecurso.Componente[ComponenteRecurso.Componente.NovaEvolucaoComponent])
                                    && !this.pacienteEmObito;
                            }

                        }, (error: ErroMensagem) => {});
        });
    }

    ngOnInit(): void {

        Promise.resolve(this.headerPaciente).then(() => {
            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmrSelecionado);
        });

        // Carrega os dados das labels (internacionalização).
        this.translate.get("pacienteForm.evolucaoGroup").subscribe(res => {
            this.labels = res;
        });

        this._temPerfilAnalistaBusca = this.autenticacaoService.temPerfilAnalistaBusca();
        if(this._temPerfilAnalistaBusca){
            this.serviceBusca.obterBuscaPorRMR(this.rmrSelecionado).then(res => {
                this._busca = new Busca().jsonToEntity(res);
                this.buscaChecklistService.marcarVistoDaBusca(this._busca.id, TiposBuscaChecklist.NOVA_EVOLUCAO_CLINICA);

            }, (error: ErroMensagem) => {
                throw new Error("Erro localizar busca do paciente");
            });
        }
    }

    public selecionarEvolucao(evolucaoId:number):void{
        this.evolucaoService.obterEvolucao(evolucaoId)
            .then(result => {
                this.evolucaoSelecionada =   new Evolucao().jsonToEntity(result);
            });
    }
    public evolucaoDecorator(evolucao:Evolucao):string{
        let dataCriacao:Date = PacienteUtil.converterStringEmData(evolucao.dataCriacao);
        return (dataCriacao.toLocaleDateString() + " - " + 
                    evolucao.motivo + " - " + 
                        evolucao.condicaoPaciente);
    }

    public cmvDecorator():string{
        return (this.evolucaoSelecionada != null && this.evolucaoSelecionada.cmv ? 
                    this.labels["positivo"] : this.labels["negativo"]);
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarEvolucaoComponent];
    }

	public get temPermissaoNovaEvolucao(): boolean {
		return this._temPermissaoNovaEvolucao;
	}
    
    obterNomeArquivoSemTimeStamp(nomeArquivo:String){
        let index = nomeArquivo.indexOf("_");
        return  nomeArquivo.substring(index + 1, nomeArquivo.length);
    }

    
    /**
     * Faz a chamada para o download do arquivo no servidor REST. 
     * 
     * @param arquivoEvolucao
     */
    public baixarArquivo(arquivoEvolucao: ArquivoEvolucao) {
        this.evolucaoService.baixarArquivoEvolucao(
            arquivoEvolucao.id,
            this.retornaNomeArquivoExameSelecionado(arquivoEvolucao.caminhoArquivo));
    }

    private retornaNomeArquivoExameSelecionado(caminhoArquivo: String): String {
        let indexDaBarra = caminhoArquivo.indexOf("/") + 1;
        caminhoArquivo = caminhoArquivo.substring(indexDaBarra, caminhoArquivo.length);
        let indexDoUnderline = caminhoArquivo.indexOf("_");
        caminhoArquivo = caminhoArquivo.substring(indexDoUnderline + 1);
        return caminhoArquivo;
    }

}
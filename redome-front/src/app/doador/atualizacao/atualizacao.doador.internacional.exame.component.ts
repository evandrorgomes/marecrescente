import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { dominioService } from "../../export.mock.spec";
import { PedidoExameService } from '../../laboratorio/pedido.exame.service';
import { ExameService } from '../../paciente/cadastro/exame/exame.service';
import { GenotipoDTO } from '../../paciente/genotipo.dto';
import { GenotipoService } from '../../paciente/genotipo.service';
import { BaseForm } from "../../shared/base.form";
import { DominioService } from "../../shared/dominio/dominio.service";
import { StatusDoador } from "../../shared/dominio/status.doador";
import { ErroMensagem } from "../../shared/erromensagem";
import { HistoricoNavegacao } from "../../shared/historico.navegacao";
import { MessageBox } from "../../shared/modal/message.box";
import { ArrayUtil } from '../../shared/util/array.util';
import { DataUtil } from "../../shared/util/data.util";
import { RouterUtil } from "../../shared/util/router.util";
import { Doador } from "../doador";
import { DoadorInternacional } from "../doador.internacional";
import { DoadorService } from "../doador.service";
import { PacienteUtil } from "../../shared/paciente.util";
import { StatusExame } from "../../shared/enums/status.exame";
import { PedidoIdmService } from "../../laboratorio/pedido.idm.service";
import { FormatterUtil } from "../../shared/util/formatter.util";
import {ExameDoadorInternacionalDto} from "../../shared/dto/exame.doador.internacional.dto";

/**
 * Classe que representa o componente de exames de doador internacional.
 * @author Filipe Paes
 */
@Component({
    selector: "atualizacao-doador-internacional-exame",
    moduleId: module.id,
    templateUrl: "./atualizacao.doador.internacional.exame.component.html",
})
export class AtualizacaoDoadorInternacionalExameComponent extends BaseForm<Doador> implements OnInit {

    public doador:DoadorInternacional;
    private _idDoador:number;
    public rmrAssociado: string;


     /**
     * Representa o RMR selecionado anteriormente (paciente).
     * É este atributo que define de quem é as informações exibidas na tela.
     */
    public rmrSelecionado: number;

    idExameSelecionado: number;

    public examesDoador:ExameDoadorInternacionalDto[] = [];


    /**
     * Recebe as labels internacionalizadas da tela.
     */
    public labels: Object = {};

    public exameSelecionado: ExameDoadorInternacionalDto = new ExameDoadorInternacionalDto();



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
     * Indica quando existe um genótipo para exibição.
     */
    public temGenotipoParaExibicao: boolean = true;




    /**
     * Construtor padrão
     * @param serviceDominio serviço injetado para buscar os dominios
     * @author Filipe Paes
     */
    constructor(private fb: FormBuilder, translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private exameService: ExameService,
        private router: Router,
        private dominioService: DominioService,
        private doadorService: DoadorService,
        private messageBox: MessageBox,
        private genotipoService: GenotipoService,
        private pedidoExameService: PedidoExameService,
        private peidoIdmService: PedidoIdmService) {
        super();
        this.translate = translate;

        this.buildForm();
    }


    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações
     * @author Fillipe Queiroz
     */
    buildForm(): void {
    }

    public voltar() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }

    public preencherFormulario(entidade: Doador): void {}

    ngOnInit(): void {
        this.translate.get("pacienteForm.exameGroup").subscribe(res => {
            this.labels = res;
        });



        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'id').then(res => {
            this._idDoador = <number>res;

            this.carregarExames(this._idDoador);

            this.genotipoService.obterGenotipoDoador(this._idDoador).then(res => {
                this.genotipoDTO = res;
                this.temGenotipoParaExibicao = ArrayUtil.isNotEmpty(this.genotipoDTO);

            }).catch((error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });



            this.doadorService.obterDoadorInternacional(this._idDoador).then(res => {
                if(res){
                    this.doador = new DoadorInternacional();
                    this.doador.id =  <number>res.id;
                    this.doador.grid = res.grid;
                    this.doador.idade = <number>res.idade;
                    if(!this.doador.idade){
                        this.doador.dataNascimento = DataUtil.toDate(res.dataNascimento);
                    }
                    this.doador.peso = res.peso;
                    this.doador.abo = res.abo;
                    this.doador.sexo = res.sexo;
                    this.doador.statusDoador = new StatusDoador();
                    this.doador.statusDoador.id = res.statusDoador.id
                    this.doador.statusDoador.descricao = res.statusDoador.descricao
                    this.rmrAssociado = res.rmrAssociado;
                }
            }, (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
        })

        this.translate.get("doadorForm.cadastro").subscribe(res => {
            this._formLabels = res;
        });

    }


    private carregarExames(idDoador:number){
        this.doadorService.listarExamesDoadorInternacional(this._idDoador)
        .then(result => {
            if(!result){
                this.temExamesParaExibicao = false;
            }
            else{
                result.forEach(e => {
                    let exameDoadorInternacional: ExameDoadorInternacionalDto = e;
                    this.examesDoador.push(exameDoadorInternacional);
                });
                this.exameSelecionado = this.examesDoador[0];
                this.idExameSelecionado = this.exameSelecionado.id;
            }
        },(error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }


    private formatarExame(exame: ExameDoadorInternacionalDto): string {
        let todosLocusDoExameConcatenados: string;
        let labelComboExame:string = "";
        if(exame.tipoExame == ExameDoadorInternacionalDto.TIPO_EXAME_HLA){
            exame.locusExames.forEach(locus => {
                if (!todosLocusDoExameConcatenados) {
                    todosLocusDoExameConcatenados = locus.id.locus.codigo + "*"
                }
                else {
                    todosLocusDoExameConcatenados = todosLocusDoExameConcatenados + ", " + locus.id.locus.codigo + "*"
                }
            })
            labelComboExame = (exame.dataCriacao != null? PacienteUtil.converterStringEmData(exame.dataCriacao).toLocaleDateString() +" - ": "" ) + todosLocusDoExameConcatenados;
            if (exame.statusExame == StatusExame.NAO_VERIFICADO) {
                labelComboExame += " (" + this.labels["nao_conferido"] + ")";
            }
        }
        else{
            labelComboExame = (exame.dataCriacao != null? PacienteUtil.converterStringEmData(exame.dataCriacao).toLocaleDateString() +" - ": "" ) + this.labels["idm"] ;
        }
        return labelComboExame;
    }

   /**
     * Faz a chamada para o download do arquivo de CRM associado
     * ao pré cadastro.
     */
    public baixarArquivo(exameIdm:ExameDoadorInternacionalDto) {
        this.peidoIdmService.baixarArquivoIdm(exameIdm.id, this.obterNomeArquivo(exameIdm.caminhoArquivo));
    }

    public obterNomeArquivo(value: string): string {
        if (value) {
            return FormatterUtil.obterNomeArquivoSelecionado(value).toString();
        }
        return "";
    }

    nomeComponente(): string {
        return "AtualizacaoDoadorInternacionalExameComponent";
    }

    private exibirMensagemErro(error: ErroMensagem) {
        let msg: string = "";
        if (error.mensagem) {
            msg = error.mensagem.toString();

        } else {
            error.listaCampoMensagem.forEach(obj => {
                msg += obj.mensagem + " \r\n";

            })
        }
        this.messageBox.alert(msg).show();
    }

    public form(): FormGroup {
        throw new Error("Method not implemented.");
    }



    /**
     * Getter idDoador
     * @return {number}
     */
	public get idDoador(): number {
		return this._idDoador;
	}

    /**
     * Setter idDoador
     * @param {number} value
     */
	public set idDoador(value: number) {
		this._idDoador = value;
    }


    /**
     * Getter temExamesParaExibicao
     * @return {boolean }
     */
	public get temExamesParaExibicao(): boolean  {
		return this._temExamesParaExibicao;
	}

    /**
     * Setter temExamesParaExibicao
     * @param {boolean } value
     */
	public set temExamesParaExibicao(value: boolean ) {
		this._temExamesParaExibicao = value;
	}

}

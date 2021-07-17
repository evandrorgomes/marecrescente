import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { BaseForm } from "../../shared/base.form";
import { CampoMensagem } from "../../shared/campo.mensagem";
import { DominioService } from "../../shared/dominio/dominio.service";
import { Registro } from "../../shared/dominio/registro";
import { ErroMensagem } from "../../shared/erromensagem";
import { HistoricoNavegacao } from "../../shared/historico.navegacao";
import { MessageBox } from "../../shared/modal/message.box";
import { Paginacao } from "../../shared/paginacao";
import { Doador } from "../doador";
import { DoadorInternacional } from "../doador.internacional";
import { DoadorService } from "../doador.service";
import { StatusDoador } from './../../shared/dominio/status.doador';
/**
 * Classe que representa o componente de avaliações de resultado de workup
 * @author Fillipe Queiroz
 */
@Component({
    selector: "consulta-doador-internacional",
    moduleId: module.id,
    templateUrl: "./consulta.doador.internacional.component.html"
})
export class ConsultaDoadorInternacionalComponent extends BaseForm<DoadorInternacional> implements OnInit {

    public doadorForm: FormGroup;
    public origens: Registro[] = [];
    public pagamentos: Registro[] = [];

    paginacaoDoadores: Paginacao;
    qtdRegistroDoadores: number = 10;

    public idPaisBrasil:number = 1


    /**
     * Construtor padrão
     * @param serviceDominio serviço injetado para buscar os dominios 
     * @author Fillipe Queiroz
     */
    constructor(private fb: FormBuilder, translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private router: Router, private dominioService: DominioService,
        private doadorService: DoadorService, private messageBox: MessageBox) {
            super();
            this.translate = translate;
            this.paginacaoDoadores = new Paginacao('', 0, this.qtdRegistroDoadores);
            this.paginacaoDoadores.number = 1;

            this.buildForm();
    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações 
     * @author Fillipe Queiroz
     */
    buildForm(): void {

        this.doadorForm = this.fb.group({
            'idRegistro': [null, null],
            'origem': [null, null],
            'grid': [null, null]
        });
    }

    public voltar() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }

    public form(): FormGroup {
        return this.doadorForm;
    }

    public preencherFormulario(doador: DoadorInternacional): void {}

    public listarDoadoresInternacionais(pagina: number) {
        this.doadorService.listarDoadoresInternacionais(
            pagina - 1,
            this.qtdRegistroDoadores,
            this.doadorForm.get("idRegistro").value,
            this.doadorForm.get("origem").value,
            this.doadorForm.get("grid").value
        ).then(res => {
            let doadores: Doador[] = [];
            if (res && res.content) {
                res.content.forEach(doadorBack => {
                    try {
                        let doador: DoadorInternacional = new DoadorInternacional();
                        doador.id = <number> doadorBack.id;
                        doador.idRegistro = <string>doadorBack.idRegistro;
                        doador.grid = <string>doadorBack.grid;
                        doador.registroOrigem = new Registro();
                        doador.registroOrigem.nome = <string>doadorBack.registroOrigem.nome;
                        doador.statusDoador = new StatusDoador();
                        if(doadorBack.statusDoador.id == StatusDoador.INATIVO_PERMANENTE
                            || doadorBack.statusDoador.id == StatusDoador.INATIVO_TEMPORARIO){
                                doador.statusDoador.descricao = <string>doadorBack.statusDoador.descricao; 
                        }
                        doadores.push(doador);
                      
                    } catch (e) {
                        this.translate.get("erro.retornoBack").subscribe(res => {
                            this.messageBox.alert(res).show();
                        })
                    }
                });
                this.paginacaoDoadores.content = doadores;
                this.paginacaoDoadores.totalElements = res.totalElements;
                this.paginacaoDoadores.quantidadeRegistro = this.qtdRegistroDoadores
            }else{
                this.paginacaoDoadores.content = null;
                this.paginacaoDoadores.totalElements = 0;
                this.paginacaoDoadores.quantidadeRegistro = this.qtdRegistroDoadores
            }
        },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }



    ngOnInit(): void {

        this.translate.get("doadorForm.consulta").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.doadorForm);
            this.setMensagensErro(this.doadorForm);
        });

        this.dominioService.listarRegistrosInternacionais().then(res => {
            if (res) {
                res.forEach(registroBack => {
                    
                    let registro: Registro = new Registro().jsonToEntity(registroBack);
                   
                    registro.id = <number>registroBack.id;
                    registro.nome = <string>registroBack.nome;
                    this.origens.push(registro);
                })
            }
        })

    }

    consultar() {
        if (!this.doadorForm.get("idRegistro").value && !this.doadorForm.get("origem").value && !this.doadorForm.get("grid").value) {
            this.setFieldRequiredSemForm("idRegistro");
            this.setFieldRequiredSemForm("origem");
            this.setFieldRequiredSemForm("grid");
        } else {
            this.resetFieldRequiredSemForm("idRegistro");
            this.resetFieldRequiredSemForm("origem");
            this.resetFieldRequiredSemForm("grid");
        }
        if (this.validateForm()) {
            this.listarDoadoresInternacionais(this.paginacaoDoadores.number);
        }
    }

    nomeComponente(): string {
        return "ConsultaDoadorInternacionalComponent";
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaComponent
     */
    selecinaQuantidadePorPagina(event: any, modal: any) {
        this.listarDoadoresInternacionais(1);
    }
    /**
     * Método acionado quando muda a página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaComponent
     */
    mudarPagina(event: any) {
        this.listarDoadoresInternacionais(event.page);
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

    private exibirMensagemSucesso(campoMensagem: CampoMensagem) {
        this.messageBox.alert(campoMensagem.mensagem).show();
    }

    public abrirDetalhe(doador: Doador): void{
        this.router.navigateByUrl("/doadores/" + doador.id + "/atualizarDoadorInternacional");
    }

    
}
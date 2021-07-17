import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { dominioService } from "../../export.mock.spec";
import { Locus } from "../../paciente/cadastro/exame/locus";
import { LocusExame } from "../../paciente/cadastro/exame/locusexame";
import { LocusExamePk } from "../../paciente/cadastro/exame/locusexamepk";
import { BaseForm } from "../../shared/base.form";
import { DominioService } from "../../shared/dominio/dominio.service";
import { FonteCelula } from "../../shared/dominio/fonte.celula";
import { Registro } from "../../shared/dominio/registro";
import { StatusDoador } from "../../shared/dominio/status.doador";
import { ValorGenotipoDTO } from "../../shared/dto/valor.genotipo.dto";
import { TiposDoador } from "../../shared/enums/tipos.doador";
import { ErroMensagem } from "../../shared/erromensagem";
import { HistoricoNavegacao } from "../../shared/historico.navegacao";
import { HlaComponent } from "../../shared/hla/hla.component";
import { MessageBox } from "../../shared/modal/message.box";
import { RouterUtil } from "../../shared/util/router.util";
import { RessalvaComponent } from "../cadastro/contato/ressalvas/ressalva.component";
import { Doador } from "../doador";
import { DoadorInternacional } from "../doador.internacional";
import { DoadorService } from "../doador.service";
import { DataUtil } from "../../shared/util/data.util";
import { ArrayUtil } from "../../shared/util/array.util";
import { ModalInativarDoadorInternacionalComponent } from "./inativar.doador.internacional.modal.component";
/**
 * Classe que representa o componente de avaliações de resultado de workup
 * @author Fillipe Queiroz
 */
@Component({
    selector: "atualizacao-doador-internacional",
    moduleId: module.id,
    templateUrl: "./atualizacao.doador.internacional.component.html",
    providers: [{ provide: DominioService, useValue: dominioService }]
})
export class AtualizacaoDoadorInternacionalComponent extends BaseForm<Doador> implements OnInit {
    public data: Array<string | RegExp>
    public fontesCelulas: FonteCelula[] = [];
    public origens: Registro[] = [];
    public pagamentos: Registro[] = [];

    @ViewChild('ressalva')
    private ressalvaComponent: RessalvaComponent;
    
    @ViewChild("hlaComponent")
    private hlaComponent: HlaComponent;
    

    public doador:DoadorInternacional;
    private _idDoador:number;
    public rmrAssociado: string;

    private valoresGenotipo: ValorGenotipoDTO[];
    


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

        this.buildForm();
    }


    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações 
     * @author Fillipe Queiroz
     */
    buildForm(): void {
        this.data = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/]
    }

    public voltar() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }

    public preencherFormulario(entidade: Doador): void {}

    ngOnInit(): void {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'id').then(res => {
            this._idDoador = <number>res;

            this.ressalvaComponent.popularRessalvas(this._idDoador);

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

                    this.doador.registroOrigem = 
                        new Registro(res.registroOrigem.id, res.registroOrigem.nome);
                    this.doador.registroPagamento = 
                        new Registro(res.registroPagamento.id, res.registroPagamento.nome);

                    this.doador.tipoDoador = res.tipoDoador;

                    let hla: LocusExame[] = [];
                    this.valoresGenotipo = res.valoresGenotipo;
                    if(ArrayUtil.isNotEmpty(this.valoresGenotipo)){
                        this.valoresGenotipo.forEach(valorGenotipo =>{
                            let locusExame: LocusExame = new LocusExame();
                            locusExame.id = new LocusExamePk();
                            locusExame.id.locus = new Locus(valorGenotipo.locus);
                            locusExame.primeiroAlelo = valorGenotipo.primeiroAlelo;
                            locusExame.segundoAlelo = valorGenotipo.segundoAlelo;
                            hla.push(locusExame);
                        });
                        this.hlaComponent.setValue(hla);
                    }
                }
            }, (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
        })

        this.translate.get("doadorForm.cadastro").subscribe(res => {
            this._formLabels = res;
        });

        this.dominioService.listarRegistros().then( res =>{
            if (res) {
                res.forEach(registroBack => {
                    let registro: Registro = new Registro();
                    registro.id = <number>registroBack.id;
                    registro.nome = <string>registroBack.nome;
                    this.origens.push(registro);
                })
                res.forEach(registroBack => {
                    let registro: Registro = new Registro();
                    registro.id = <number>registroBack.id;
                    registro.nome = <string>registroBack.nome;
                    this.pagamentos.push(registro);
                })
            }
        })
    }

    salvar() {}

    nomeComponente(): string {
        return "AtualizacaoDoadorInternacionalComponent";
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

    visualizarExames(){
        this.router.navigateByUrl('/doadores/' + this.doador.id + '/atualizarDoadorInternacional/exame');
    }

    abrirModalInativarDoador(){
        let data: any = {
            doador:this.doador,
            fecharModalSucesso: () => {
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());                
            }
        }
        this.messageBox.dynamic(data, ModalInativarDoadorInternacionalComponent)
            .show();
   }
    

}
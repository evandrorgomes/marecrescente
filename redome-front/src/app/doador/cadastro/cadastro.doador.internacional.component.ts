import {DoadorService} from 'app/doador/doador.service';
import {DateMoment} from './../../shared/util/date/date.moment';
import {Component, OnInit, ViewChild} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {DoadorInternacional} from "../doador.internacional";
import {PedidoExame} from '../../laboratorio/pedido.exame';
import {Locus} from '../../paciente/cadastro/exame/locus';
import {PacienteService} from "../../paciente/paciente.service";
import {Registro} from "../../shared/dominio/registro";
import {TiposExame} from '../../shared/enums/tipos.exame';
import {PacienteUtil} from "../../shared/paciente.util";
import {LocusExame} from "../../paciente/cadastro/exame/locusexame";
import {LocusVO} from "../../paciente/cadastro/exame/locusvo";
import {BaseForm} from "../../shared/base.form";
import {DominioService} from "../../shared/dominio/dominio.service";
import {ErroMensagem} from "../../shared/erromensagem";
import {HistoricoNavegacao} from "../../shared/historico.navegacao";
import {HlaComponent} from "../../shared/hla/hla.component";
import {MessageBox} from "../../shared/modal/message.box";
import {ArrayUtil} from "../../shared/util/array.util";
import {DataUtil} from "../../shared/util/data.util";
import {RouterUtil} from "../../shared/util/router.util";
import {StringUtil} from "../../shared/util/string.util";
import {RessalvaDoador} from "../ressalva.doador";
import {LocusComponent} from '../../shared/hla/locus/locus.component';
import {RessalvaComponent} from "./contato/ressalvas/ressalva.component";
import {ErroUtil} from "../../shared/util/erro.util";
import {CordaoInternacional} from "../cordao.internacional";
import {ExameCordaoInternacional} from "../exame.cordao.internacional";
import {DateTypeFormats} from '../../shared/util/date/date.type.formats';
import {TiposDoador} from "../../shared/enums/tipos.doador";
import {DoadorCordaoInternacionalDto} from "../../shared/dto/doador.cordao.internacional.dto";
import {PedidoDto} from "../../shared/dto/pedido.dto";
import {DoadorInternacionalService} from "../../shared/service/doador.internacional.service";
import {CordaoInternacionalService} from "../../shared/service/cordao.internacional.service";

/**
 * Classe que representa o componente de avaliações de resultado de workup
 * @author Fillipe Queiroz
 */
@Component({
    selector: "cadastro-doador-internacional",
    moduleId: module.id,
    templateUrl: "./cadastro.doador.internacional.component.html"
})
export class CadastroDoadorInternacionalComponent extends BaseForm<DoadorInternacional> implements OnInit {

    public doadorForm: FormGroup;

    public data: Array<string | RegExp>

    public origens: Registro[] = [];

    public pagamentos: Registro[] = [];

    public rmr: number;

    private _isCordao:boolean;

    @ViewChild('ressalva')
    private ressalvaComponent: RessalvaComponent;

    @ViewChild("hlaComponent")
    private hlaComponent: HlaComponent;

    @ViewChild("locusComponent")
    private locusComponent: LocusComponent;


    /**
     * Construtor padrão
     * @param serviceDominio serviço injetado para buscar os dominios
     * @author Fillipe Queiroz
     */
    constructor(private fb: FormBuilder, translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private router: Router, private dominioService: DominioService,
        private pacienteService: PacienteService, private messageBox: MessageBox,
        private doadorService:DoadorService, private doadorInternacionalService: DoadorInternacionalService,
        private cordaoInternacionalService: CordaoInternacionalService) {
        super();
        this.translate = translate;

        this.buildForm();
    }

    private adicionarLocusIniciaisNoForm(){
        this.hlaComponent.locusObrigatorios = [LocusVO.LOCI_A, LocusVO.LOCI_B];
        this.hlaComponent.setValue([new LocusExame(LocusVO.LOCI_A), new LocusExame(LocusVO.LOCI_B)]);
    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações
     * @author Fillipe Queiroz
     */
    buildForm(): void {

        this.doadorForm = this.fb.group({
            'id': [null, Validators.required],
            'grid': [null, null],
            'idade': [null, null],
            'peso': [null, null],
            'abo': [null, null],
            'sexo': [null, Validators.required],
            'origem': [null, Validators.required],
            'pagamento': [null, Validators.required],
            'dataNascimento': [null, null],
            "cadastradoEmdis": null,
            "pedidoexame": false,
            "pedidoexamect": false,
            'quantidadeTotalTCN':[null, null],
            'quantidadeTotalCD34':[null, null],
            'volume':[null, null],
            'isCordao':['false', Validators.required]
        });
        this.data = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/];
    }

    public onChangeTipoDoador(isCordao:boolean){
        this._isCordao = isCordao;
    }

    public replicarPagamento():void{
        let idPagamentoSelecionado: string = this.getPropertyValue("pagamento");
        if(StringUtil.isNullOrEmpty(idPagamentoSelecionado)){
            this.setPropertyValue("pagamento", this.doadorForm.get("origem").value);
            this.setMensagemErroPorCampo('pagamento');
        }
    }

    public voltar() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    public form(): FormGroup {
        return this.doadorForm;
    }

    public preencherFormulario(entidade: DoadorInternacional): void {}

    ngOnInit(): void {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'rmr').then(res => {
            this.rmr = <number>res;
        })

        this._isCordao = this.getPropertyValue("isCordao") == "false" ? false : true;



        this.translate.get("doadorForm.cadastro").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.doadorForm);
            this.setMensagensErro(this.doadorForm);
        });

        this.adicionarLocusIniciaisNoForm();

        this.dominioService.listarRegistrosInternacionais().then( res =>{
            if (res) {
                res.forEach(registroBack => {
                    let registro: Registro = new Registro();
                    registro.id = registroBack.id;
                    registro.nome = registroBack.nome;
                    this.origens.push(registro);
                })
                res.forEach(registroBack => {
                    let registro: Registro = new Registro();
                    registro.id = registroBack.id;
                    registro.nome = registroBack.nome;
                    this.pagamentos.push(registro);
                })
            }
        });

    }

    /**
     * Reimplementando a validação do formulário para validar também o HLA.
     */
    public validateForm():boolean{
        let formularioValido: boolean = super.validateForm();
        let hlaValido: boolean = this.hlaComponent.validateForm();
        let pedidoExameValido = true;
        if (this.form().get("pedidoexame").value == true) {
            pedidoExameValido = this.locusComponent.validateForm();
        }

        return formularioValido && hlaValido && pedidoExameValido;
    }

    salvar() {

        if (!this.doadorForm.get('idade').value && !this.doadorForm.get('dataNascimento').value && !this._isCordao) {
            this.setFieldRequiredSemForm('dataNascimento');
            this.setFieldRequiredSemForm('idade');
        } else {
            this.resetFieldRequiredSemForm('dataNascimento');
            this.resetFieldRequiredSemForm('idade');
        }
        if(this._isCordao){
            this.setFieldRequiredSemForm("quantidadeTotalTCN");
            this.setFieldRequiredSemForm("quantidadeTotalCD34");
            this.setFieldRequiredSemForm("volume");
        }else{
            this.resetFieldRequiredSemForm("quantidadeTotalTCN");
            this.resetFieldRequiredSemForm("quantidadeTotalCD34");
            this.resetFieldRequiredSemForm("volume");
        }

        if (this.validateForm()) {

            let doador: DoadorCordaoInternacionalDto = this.popularDoadorInternacionalDto(!this._isCordao ? TiposDoador.INTERNACIONAL : TiposDoador.CORDAO_INTERNACIONAL );
            doador.rmrAssociado = this.rmr;

            let pedido: PedidoDto = null;
            if (this.form().get("pedidoexame").value) {
                let locusSelecionados: Locus[] = this.locusComponent.values;
                pedido = new PedidoDto(TiposExame.TIPIFICACAO_HLA_ALTA_RESOLUCAO, locusSelecionados);
            }
            if (this.form().get("pedidoexamect").value) {
                pedido = new PedidoDto(TiposExame.TESTE_CONFIRMATORIO);
            }

            let service: Promise<string>;
            if (!this._isCordao) {
                service = this.doadorInternacionalService.salvarDoadorInternacional(doador, pedido);
            }
            else {
                service = this.cordaoInternacionalService.salvarCordaoInternacional(doador, pedido);
            }
            service.then(result => {
                   this.messageBox.alert(result)
                      .withTarget(this)
                      .withCloseOption((target?: any) => {
                          this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                      })
                      .show();


                }, (error: ErroMensagem) =>{
                    ErroUtil.exibirMensagemErro(error, this.messageBox)
                });
        }
    }

    private popularDoadorInternacionalDto(tipo: number):DoadorCordaoInternacionalDto {
        let doador: DoadorCordaoInternacionalDto = new DoadorCordaoInternacionalDto(tipo);
        doador.idRegistro = this.getPropertyValue("id");
        doador.grid = this.getPropertyValue("grid");

        if(!StringUtil.isNullOrEmpty(this.getPropertyValue("idade"))){
            doador.idade = new Number(this.getPropertyValue("idade")).valueOf();
        }

        doador.peso = PacienteUtil.arredondar(this.getPropertyValue("peso"), 1);
        doador.abo = this.getPropertyValue("abo");
        doador.sexo = this.getPropertyValue("sexo");
        doador.registroOrigem = new Registro(new Number(this.getPropertyValue("origem")).valueOf());
        doador.registroPagamento = new Registro(this.getPropertyValue("pagamento"));
        doador.cadastradoEmdis = false;

        let ressalvas: RessalvaDoador[] = this.ressalvaComponent.ressalvas;
        if(ArrayUtil.isNotEmpty(ressalvas)){
            let ressalvaSometeTexto: string [] = [];
            ressalvas.forEach(ressalva =>{
                ressalvaSometeTexto.push(ressalva.observacao);
                //ressalva.doador = null;
            });
            doador.ressalvas = ressalvaSometeTexto;
        }

        if(!StringUtil.isNullOrEmpty(this.getPropertyValue("dataNascimento"))){
            doador.dataNascimento = DataUtil.toDate( this.getPropertyValue("dataNascimento") );
        }

        doador.locusExames = this.hlaComponent.getValue();


        if (TiposDoador.CORDAO_INTERNACIONAL === doador.tipoDoador) {
            doador.volume = this.getPropertyValue("volume");
            doador.quantidadeTotalTCN = this.getPropertyValue("quantidadeTotalTCN");
            doador.quantidadeTotalCD34 = this.getPropertyValue("quantidadeTotalCD34");
        }
        return doador;
    }

    nomeComponente(): string {
        return "CadastroDoadorComponent";
    }


    /**
     * Quando o usuário pretende informar a idade do doador,
     * nesse caso, o campo data nascimento torna-se desnecessário.
     */
    public exibirNascimentoDoador(): void{
        if(StringUtil.isNullOrEmpty(this.getPropertyValue("dataNascimento"))
                && StringUtil.isNullOrEmpty(this.getPropertyValue("idade"))){
            this.enabled("idade");
            this.enabled("dataNascimento");
        }
        else if(StringUtil.isNullOrEmpty(this.getPropertyValue("idade"))){
            this.enabled("dataNascimento");
            this.enabled("idade", false);
            this.formErrors["idade"] = null;
        }
        else if(StringUtil.isNullOrEmpty(this.getPropertyValue("dataNascimento"))){
            this.enabled("idade");
            this.enabled("dataNascimento", false);
            this.formErrors["dataNascimento"] = null;
        }
    }

    public calcularAnoNascimentoPelaIdade(){
        let idade:number = this.getPropertyValue("idade");
        let data = new Date();
        let ano :number = data.getFullYear();
        let mes = (data.getUTCMonth() + 1) < 10? "0" + (data.getUTCMonth() + 1):(data.getUTCMonth() + 1);
        let dia = data.getUTCDate()< 10?"0" +  data.getUTCDate(): data.getUTCDate();
        let anoNascimento:number = ano - idade;
        this.setPropertyValue("dataNascimento", dia + "/" + mes + "/" + anoNascimento)
    }

    public calcularIdadePelaDataDeNascimento(){
        let dataAniversario = this.getPropertyValue("dataNascimento");
        this.setPropertyValue("idade",
        DataUtil.calcularIdade(DateMoment.getInstance().parse(dataAniversario, DateTypeFormats.DATE_ONLY)));
    }

    verificaPedidoSelecionado(evento: any,) {
        if (evento.target.id == "ckPedidoExame") {
            if (evento.target.checked) {
                if (this.form().get("pedidoexamect").value == true) {
                    this.form().get("pedidoexamect").setValue(false);
                }

                this.locusComponent.required = true;
            }
            else {
                this.locusComponent.required = false;
            }
        }
        else if (evento.target.id == "ckPedidoExameCt" && evento.target.checked) {
            if (this.form().get("pedidoexame").value == true) {
                this.form().get("pedidoexame").setValue(false);
                this.locusComponent.required = false;
            }
        }

    }

    public verificarExistenciaDeDoadorPorGrid(){
        let grid: string = this.getPropertyValue("grid");
        this.doadorService.obterDoadorInternacionalPorGrid(grid).then(res=>{
            if(res){
                let mensagemValidacao: string;
                this.translate.get("doadores.mensagem.existenteGrid", {"grid":grid}).subscribe(res => {
                   mensagemValidacao = res;
                });
                this.messageBox.alert(mensagemValidacao)
                .show();

                this.translate.get("mensagem.erro.invalido", {"campo":"grid"}).subscribe(res => {
                    this.markAsInvalid(this.doadorForm.get("grid"));
                    this.formErrors['grid'] = res;
                 });
            }
            else{
                this.resetFieldRequired(this.doadorForm, "grid");
            }
        }, (error: ErroMensagem) =>{
            ErroUtil.exibirMensagemErro(error, this.messageBox)
        });
    }

    public fecharMensagemSucesso(target: any) {
        target.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }


    /**
     * Getter isCordao
     * @return {boolean}
     */
	public get isCordao(): boolean {
		return this._isCordao;
	}

    /**
     * Setter isCordao
     * @param {boolean} value
     */
	public set isCordao(value: boolean) {
		this._isCordao = value;
	}


}

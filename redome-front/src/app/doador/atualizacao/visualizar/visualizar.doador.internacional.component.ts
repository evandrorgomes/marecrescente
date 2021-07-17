import {Component, OnInit, ViewChild} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {OrigemPagamentoDoadorDTO} from 'app/shared/dto/origem.pagamento.doador.dto';
import {CordaoInternacionalService} from "app/shared/service/cordao.internacional.service";
import {DoadorInternacionalService} from 'app/shared/service/doador.internacional.service';
import {StringUtil} from 'app/shared/util/string.util';
import {HeaderDoadorInternacionalComponent} from '../../../doadorinternacional/identificacao/header.doador.internacional.component';
import {Locus} from "../../../paciente/cadastro/exame/locus";
import {LocusExame} from "../../../paciente/cadastro/exame/locusexame";
import {LocusExamePk} from "../../../paciente/cadastro/exame/locusexamepk";
import {BuildForm} from "../../../shared/buildform/build.form";
import {StringControl} from "../../../shared/buildform/controls/string.control";
import {DominioService} from "../../../shared/dominio/dominio.service";
import {FonteCelula} from "../../../shared/dominio/fonte.celula";
import {Registro} from "../../../shared/dominio/registro";
import {StatusDoador} from "../../../shared/dominio/status.doador";
import {DoadorCordaoInternacionalDto} from "../../../shared/dto/doador.cordao.internacional.dto";
import {StatusDoadorDTO} from '../../../shared/dto/status.doador.internacional.dto';
import {ValorGenotipoDTO} from "../../../shared/dto/valor.genotipo.dto";
import {TiposDoador} from "../../../shared/enums/tipos.doador";
import {ErroMensagem} from "../../../shared/erromensagem";
import {HistoricoNavegacao} from "../../../shared/historico.navegacao";
import {HlaComponent} from "../../../shared/hla/hla.component";
import {Modal, ModalConfirmation} from '../../../shared/modal/factory/modal.factory';
import {MessageBox} from "../../../shared/modal/message.box";
import {ArrayUtil} from "../../../shared/util/array.util";
import {DataUtil} from "../../../shared/util/data.util";
import {DateMoment} from '../../../shared/util/date/date.moment';
import {DateTypeFormats} from '../../../shared/util/date/date.type.formats';
import {ErroUtil} from '../../../shared/util/erro.util';
import {MascaraUtil} from '../../../shared/util/mascara.util';
import {RouterUtil} from "../../../shared/util/router.util";
import {RessalvaComponent} from "../../cadastro/contato/ressalvas/ressalva.component";
import {DoadorInternacional} from "../../doador.internacional";
import {DoadorService} from "../../doador.service";
import {ModalInativarDoadorInternacionalComponent} from "../inativar.doador.internacional.modal.component";
import {NumberControl} from './../../../shared/buildform/controls/number.control';
import {FormatterUtil} from './../../../shared/util/formatter.util';

/**
 * Classe que representa o componente de avaliações de resultado de workup
 * @author Fillipe Queiroz
 */
@Component({
    selector: "visualizar-doador-internacional",
    moduleId: module.id,
    templateUrl: "./visualizar.doador.internacional.component.html"
})
export class VisualizarDoadorInternacionalComponent implements OnInit {
    public fontesCelulas: FonteCelula[] = [];
    public origens: Registro[] = [];
    public pagamentos: Registro[] = [];
    public editarDadosPessoais:boolean = false;
    public editarDadosRegistro:boolean = false;
    public statusInativoDoador:boolean = false;
    public tipoCordao:boolean = false;
    @ViewChild('ressalva')
    private ressalvaComponent: RessalvaComponent;
    @ViewChild("hlaComponent")
    private hlaComponent: HlaComponent;
    public doador: DoadorCordaoInternacionalDto;
    public doadorInternacional: DoadorInternacional;
    private _idDoador:number;
    public rmrAssociado: string;
    private valoresGenotipo: ValorGenotipoDTO[];
    public dadosPessoaisBuildForm: BuildForm<any>;
    public registroBuildForm: BuildForm<any>;
    public _peso: string;

    public abos:String[] = ["A-", "A+", "B-", "B+", "AB-", "AB+", "O-", "O+"];

    public opcoesSexo:String[] = ["M","F"];
    public labelsSexo:String[] =[];
    @ViewChild("headdoadorinternacional")
    public headerDoadorInternacional:HeaderDoadorInternacionalComponent;


    /**
     * Construtor padrão
     * @param serviceDominio serviço injetado para buscar os dominios
     * @author Fillipe Queiroz
     */
    constructor(private fb: FormBuilder, private translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private router: Router, private dominioService: DominioService,
        private doadorInternacionalService: DoadorInternacionalService,
        private cordaoInternacionalService: CordaoInternacionalService,
        private doadorService: DoadorService, private messageBox: MessageBox) {

        this.buildForm();
    }


    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações
     * @author Fillipe Queiroz
     */
    buildForm(): void {
        this.dadosPessoaisBuildForm = new BuildForm<any>()
        .add(new StringControl("idRegistro", [ Validators.required ] ))
        .add(new StringControl("grid", [ Validators.required ] ))
        .add(new NumberControl("idade"))
        .add(new StringControl("peso"))
        .add(new StringControl("abo" ))
        .add(new StringControl("sexo", [ Validators.required ] ))
        .add(new StringControl("dataNascimento"))
        .add(new StringControl("quantidadeTotalTCN"))
        .add(new StringControl("quantidadeTotalCD34"))
        .add(new StringControl("volume"));

        this.registroBuildForm = new BuildForm<any>()
        .add(new NumberControl("registroOrigem", [Validators.required]))
        .add(new NumberControl("registroPagamento", [Validators.required]));
    }

    public voltar() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }

    ngOnInit(): void {


        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'id').then(res => {
            this._idDoador = <number>res;

            Promise.resolve(this.headerDoadorInternacional).then(() => {
                this.headerDoadorInternacional.popularCabecalho(this._idDoador);
            });

            this.ressalvaComponent.popularRessalvas(this._idDoador);

            this.obterDoadorInternacional();

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
        })

        this.translate.get("doadorForm.cadastro.masc").subscribe(res => {
            this.labelsSexo[0] = res;
        });
        this.translate.get("doadorForm.cadastro.fem").subscribe(res => {
            this.labelsSexo[1] = res;
        });

    }

    private obterDoadorInternacional(){
        this.doadorService.obterDoadorInternacional(this._idDoador).then(res => {
            if (res) {
                this.carregarDoador(res);
            }
        }, (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    carregarFormularioDadosPessoais(){
        let valoresDoador: any = {
            'idRegistro': this.doador.idRegistro,
            'grid': this.doador.grid,
            'peso': this._peso,
            'abo':  this.doador.abo,
            'sexo': this.doador.sexo,
            'idade': this.doador.idade,
            'dataNascimento': FormatterUtil.aplicarMascaraData(this.doador.dataNascimento),
            'quantidadeTotalTCN': this.doador.quantidadeTotalTCN,
            'quantidadeTotalCD34': this.doador.quantidadeTotalCD34,
            'volume': this.doador.volume
        }

        this.dadosPessoaisBuildForm.form.reset();
        this.dadosPessoaisBuildForm.value = valoresDoador;
    }

    carregarFormlarioRegistro(){
        let valoresDoador: any = {
            'registroOrigem': this.doador.registroOrigem.id,
            'registroPagamento': this.doador.registroPagamento.id,
        }
        this.registroBuildForm.form.reset();
        this.registroBuildForm.value = valoresDoador;
    }

    alternarModoIdadeEDataNascimento(){
        if(this.doador.idade != null){
            this.form().controls["dataNascimento"].disable({onlySelf: true, emitEvent: true});
            this.form().controls["idade"].enable({onlySelf: true, emitEvent: true});
        }
        else{
            this.form().controls["dataNascimento"].enable({onlySelf: true, emitEvent: true});
            this.form().controls["idade"].disable({onlySelf: true, emitEvent: true});
        }
    }
    carregarDoador(res: any){
        this.doador = new DoadorCordaoInternacionalDto(res.tipoDoador);
        this.doador.id =  <number>res.id;
        this.doador.idRegistro = res.idRegistro;
        this.doador.grid = res.grid;
        this.doador.idade = <number>res.idade;

        if(this.doador.idade){
            this.doador.dataNascimento = DataUtil.toDate(res.dataNascimento);
        }
        this.doador.peso = res.peso;
        this._peso = FormatterUtil.obterPesoFormatado(String(res.peso),1);

        this.doador.abo = res.abo;
        this.doador.sexo = res.sexo;
        this.doador.statusDoador = new StatusDoadorDTO();
        this.doador.statusDoador.id = res.statusDoador.id;
        this.doador.statusDoador.descricao = res.statusDoador.descricao;
        this.rmrAssociado = res.rmrAssociado;

        this.doador.registroOrigem =
            new Registro(res.registroOrigem.id, res.registroOrigem.nome);
        this.doador.registroPagamento =
            new Registro(res.registroPagamento.id, res.registroPagamento.nome);

        this.doador.quantidadeTotalCD34 = res.quantidadeTotalCD34;
        this.doador.quantidadeTotalTCN = res.quantidadeTotalCD34;
        this.doador.volume = res.volume;
        if(this.doador.tipoDoador === TiposDoador.CORDAO_INTERNACIONAL){
            this.tipoCordao = true;
        }

        this.verificarStatusInativoDoador();

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

    verificarStatusInativoDoador(){
        let status: Number = this.doador.statusDoador.id;
        if (status == StatusDoador.INATIVO_PERMANENTE || status == StatusDoador.INATIVO_TEMPORARIO){
            this.statusInativoDoador = true;
        }
    }

    nomeComponente(): string {
        return "VisualizarDoadorInternacionalComponent";
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
        return <FormGroup>this.dadosPessoaisBuildForm.form;
    }

    public formRegistro():FormGroup{
        return <FormGroup>this.registroBuildForm.form;
    }

    visualizarAtualizarDoadorInternacional(){
        this.router.navigateByUrl('/doadores/' + this.doador.id + '/atualizarDoadorInternacional');
    }

    visualizarExames(){
        this.router.navigateByUrl('/doadores/' + this.doador.id + '/atualizarDoadorInternacional/exame');
    }

    abrirModalInativarDoador(){
        let data: any = {
            doador:this.doador,
            fecharModalSucesso: () => {
                this.statusInativoDoador = true;
                this.visualizarAtualizarDoadorInternacional();
            }
        }
        this.messageBox.dynamic(data, ModalInativarDoadorInternacionalComponent)
            .show();
   }

   abrirModalReativarDoador(): void{
    this.translate.get("doadores.mensagem.confirmarReativacao").subscribe(mensagem =>{
        let modalConfirmacao: ModalConfirmation = this.messageBox.confirmation(mensagem);
        modalConfirmacao.yesOption = () => {

            this.doadorInternacional = new DoadorInternacional();
            this.doadorInternacional.id = this.doador.id;
            this.doadorInternacional.statusDoador = new StatusDoador();
            this.doadorInternacional.statusDoador.id = StatusDoador.ATIVO;

            this.doadorService.atualizarStatusDoador(this.doadorInternacional).then(res => {
                let modal: Modal = this.messageBox.alert(res.mensagem);
                modal.closeOption = () => {
                    this.statusInativoDoador = false;
                    this.visualizarAtualizarDoadorInternacional();
                };
                modal.show();
            }, (error: ErroMensagem) => {
                this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
            });
        };
        modalConfirmacao.show();
    });
}

   modoEditarDadosPessoais(){
        this.editarDadosPessoais = true;
        this.carregarFormularioDadosPessoais();
   }

   cancelarEdicaoDadosPessoais(){
       this.editarDadosPessoais = false;
   }

   modoEditarRegistro(){
       this.editarDadosRegistro = true;
       this.carregarFormlarioRegistro();
   }


   cancelarEdicaoRegistro(){
        this.editarDadosRegistro = false;
   }

   salvarEdicaoDadosPessoais(){
    if(this.dadosPessoaisBuildForm.valid){
        if(this.doador.tipoDoador === TiposDoador.INTERNACIONAL){
            let doadorAtualizado:DoadorCordaoInternacionalDto = new DoadorCordaoInternacionalDto(this.doador.tipoDoador);
            doadorAtualizado.idRegistro = this.dadosPessoaisBuildForm.form.get("idRegistro").value;
            doadorAtualizado.grid = this.dadosPessoaisBuildForm.form.get("grid").value;
            doadorAtualizado.abo = this.dadosPessoaisBuildForm.form.get("abo").value;
            doadorAtualizado.sexo = this.dadosPessoaisBuildForm.form.get("sexo").value;

            if (!StringUtil.isNullOrEmpty(this.dadosPessoaisBuildForm.form.get("dataNascimento").value)){
                doadorAtualizado.dataNascimento = DataUtil.toDate(this.dadosPessoaisBuildForm.form.get("dataNascimento").value);
            }

            if (!StringUtil.isNullOrEmpty(this.dadosPessoaisBuildForm.form.get("idade").value)){
                doadorAtualizado.idade = new Number(this.dadosPessoaisBuildForm.form.get("idade").value).valueOf();
            }
            doadorAtualizado.peso = FormatterUtil.arredondar(this.dadosPessoaisBuildForm.form.get("peso").value, 1);

            this.doadorInternacionalService.atualizarDadosPesoaisDoadorInternacional(this.doador.id, doadorAtualizado).then(res=>{
                this.obterDoadorInternacional();
                this.cancelarEdicaoDadosPessoais();
            },(error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            })
        }
    }
   }

    salvarEdicaoRegistro(){
        if(this.registroBuildForm.valid){

            let origemPagamentoDoadorDTO: OrigemPagamentoDoadorDTO = new OrigemPagamentoDoadorDTO();

            origemPagamentoDoadorDTO.idRegistroOrigem = this.registroBuildForm.form.get("registroOrigem").value;
            origemPagamentoDoadorDTO.idRegistroPagamento = this.registroBuildForm.form.get("registroPagamento").value;
            origemPagamentoDoadorDTO.idDoador = this.doador.id;

            if(this.doador instanceof DoadorInternacional){

                this.doadorInternacionalService.alterarRegistroPagamentoDoador(origemPagamentoDoadorDTO).then(res=>{
                    this.obterDoadorInternacional();
                    this.cancelarEdicaoRegistro();
                },(error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                })

            }else{

                this.cordaoInternacionalService.alterarRegistroPagamentoDoador(origemPagamentoDoadorDTO).then(res=>{
                    this.obterDoadorInternacional();
                    this.cancelarEdicaoRegistro();
                },(error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                })

            }
        }
    }

   public get maskData(): Array<string | RegExp> {
        return MascaraUtil.data;
   }

   public get maskPeso(): string {
    return this.form().controls["peso"].value(this._peso); //FormatterUtil.obterPesoFormatado(this._peso,1);
   }


   public calcularAnoNascimentoPelaIdade(){
        let idade:number = this.form().get("idade").value;
        let data = new Date();
        let ano :number = data.getFullYear();
        let mes = (data.getUTCMonth() + 1) < 10? "0" + (data.getUTCMonth() + 1):(data.getUTCMonth() + 1);
        let dia = data.getUTCDate()< 10?"0" +  data.getUTCDate(): data.getUTCDate();
        let anoNascimento:number = ano - idade;
        this.form().controls["dataNascimento"].value(dia + "/" + mes + "/" + anoNascimento);
    }

    public calcularIdadePelaDataDeNascimento(){
        let dataAniversario = this.form().get("dataNascimento").value;
        this.form().controls["idade"].value(DataUtil.calcularIdade(DateMoment.getInstance().parse(dataAniversario, DateTypeFormats.DATE_ONLY)));
    }


}

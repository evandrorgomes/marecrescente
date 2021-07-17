import { RetornoInclusaoDTO } from './../../../shared/dto/retorno.inclusao.dto';
import { ErroUtil } from './../../../shared/util/erro.util';
import { BuscaPreliminarService } from './../../../shared/service/busca.preliminar.service';
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from "@ngx-translate/core";
import { BuscaPreliminar } from "app/shared/model/busca.preliminar";
import { BaseForm } from "../../../shared/base.form";
import { MessageBox } from '../../../shared/modal/message.box';
import { ValidateData } from '../../../validators/data.validator';
import { HlaComponent } from "app/shared/hla/hla.component";
import { LocusVO } from "app/paciente/cadastro/exame/locusvo";
import { LocusExame } from "app/paciente/cadastro/exame/locusexame";
import { PacienteUtil } from "app/shared/paciente.util";
import { LocusExamePreliminar } from "app/shared/model/locus.exame.preliminar";
import { ErroMensagem } from 'app/shared/erromensagem';
import { StatusRetornoInclusaoDTO } from '../../../shared/dto/status.retorno.inclusao.dto';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { LocusExamePk } from '../exame/locusexamepk';
import { Locus } from 'app/paciente/cadastro/exame/locus';
import { DataUtil } from '../../../shared/util/data.util';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { RouterUtil } from 'app/shared/util/router.util';
import { StringUtil } from '../../../shared/util/string.util';

/**
 * Component responsavel por cadastrar busca preliminar
 * @author Bruno Sousa
 * @export
 * @class CadastrarBuscaPreliminarComponent
 * @extends {BaseForm}
 * @implements {OnInit}
 */
@Component({
    selector: 'cadastrar-busca-preliminar',
    moduleId: module.id,
    templateUrl: './cadastrar.busca.preliminar.component.html'
})
export class CadastrarBuscaPreliminarComponent extends BaseForm<BuscaPreliminar> implements OnInit {

    private _formGroup: FormGroup;
    public data: Array<string | RegExp>;

    @ViewChild("hlaComponent")
    public hlaComponent: HlaComponent;

    private static ULTIMA_BUSCA_REALIZADA_KEY: string = "ultimaBuscaRealizadaKey";


    constructor(private fb: FormBuilder, translate: TranslateService,
        private messageBox: MessageBox, private activatedRouter: ActivatedRoute,
        private router: Router, private buscaPreliminarService: BuscaPreliminarService) {
        super();
        this.translate = translate;
        
        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/];
        this.buildForm();        
    }

    ngOnInit() {
        this.translate.get("cadastrarBuscaPreliminarComponent").subscribe(res => {
            this._formLabels = res;
            this.criarMensagensErro(this._formGroup);
        });

        this.hlaComponent.locusObrigatorios = [LocusVO.LOCI_A, LocusVO.LOCI_B];

        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "cache").then(usarCache => {
            let ultimaBuscaRealizada: BuscaPreliminar = null;
            if (usarCache != null) {
                ultimaBuscaRealizada = this.recuperarUltimaBuscaRealizada();
            }

            if(ultimaBuscaRealizada){
                this.preencherFormulario(ultimaBuscaRealizada);
            }
            else{
                this.hlaComponent.setValue([
                    new LocusExame(LocusVO.LOCI_A), 
                    new LocusExame(LocusVO.LOCI_B), 
                    new LocusExame(LocusVO.LOCI_C), 
                    new LocusExame(LocusVO.LOCI_DRB1),
                    new LocusExame(LocusVO.LOCI_DQB1)]);
            }
        });
    }
    
    /**
     * Constrói os FormControl para os campos referentes a prescrição de medula.
     * @author Bruno Sousa
     */
    buildForm() {
        this._formGroup = this.fb.group({
            'nome': [null, Validators.required],            
            'dataNascimento': [null, Validators.compose([Validators.required, ValidateData])],
            'peso': [null, Validators.required],
            'abo': [null, Validators.required]
        });
    }

    /**
     * @inheritDoc
     */
    public validateForm():boolean{        
        let valid: boolean = super.validateForm();
        let hlaValid = this.hlaComponent.validateForm();
        return valid && hlaValid;
    }

    /**
     * @inheritDoc
     */
    public form(): FormGroup {
        return this._formGroup;
    }

    /**
     * @inheritDoc
     */
    public preencherFormulario(entidade: BuscaPreliminar): void {
        this.setPropertyValue("nome", entidade.nomePaciente);

        if(!StringUtil.isNullOrEmpty(entidade.dataNascimento)){
            this.setPropertyValue("dataNascimento", 
                DateMoment.getInstance().parse(entidade.dataNascimento.toString()));
        }

        this.setPropertyValue("peso", entidade.peso);
        this.setPropertyValue("abo", entidade.abo);

        let locusExames: LocusExame[] = 
            entidade.locusExamePreliminar.map(locusExamePreliminar => {
                let locusExame: LocusExame = new LocusExame();
                locusExame.id = new LocusExamePk();
                locusExame.id.locus = new Locus(locusExamePreliminar.locus);
                locusExame.primeiroAlelo = locusExamePreliminar.primeiroAlelo;
                locusExame.segundoAlelo = locusExamePreliminar.segundoAlelo;
                return locusExame;
            }
        );

        this.hlaComponent.setValue(locusExames);
    }

    /**
     * @inheritDoc
     */
    nomeComponente(): string {
        return "CadastrarBuscaPreliminarComponent"
    }

    /**
     * Método que faz a chamada ao back para salvar a busca preliminar.
     * 
     * @author Bruno Sousa
     * @memberof CadastrarBuscaPreliminarComponent
     */
    public buscarMatchs() {
        if (this.validateForm()) {
            let buscaPreliminar: BuscaPreliminar = this.recuperarFormulario();

            this.buscaPreliminarService.salvarBuscarPreliminar(buscaPreliminar).then(res => {
                let retornoInclusaoDTO: RetornoInclusaoDTO = new RetornoInclusaoDTO().jsonToEntity(res);

                if (retornoInclusaoDTO.status == StatusRetornoInclusaoDTO.SUCESSO) {
                    this.router.navigateByUrl("pacientes/buscapreliminar/" + retornoInclusaoDTO.idObjeto + "/matchpreliminar");
                }
                else {
                    this.messageBox.alert(res.mensagem).show();
                }

            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            }) 
        }
    }

    /**
     * Recupera os atributos e preenche a entidade com as informações
     * informadas no relatório.
     */
    private recuperarFormulario(): BuscaPreliminar{
        let buscaPreliminar: BuscaPreliminar = new BuscaPreliminar();
        buscaPreliminar.nomePaciente = this.form().get("nome").value;
        buscaPreliminar.dataNascimento = PacienteUtil.obterDataSemMascara(this.form().get("dataNascimento").value);
        buscaPreliminar.abo = this.form().get("abo").value;
        buscaPreliminar.peso =  PacienteUtil.arredondar(this.form().get("peso").value, 1);
        buscaPreliminar.locusExamePreliminar = this.hlaComponent.getValue()
            .map(locusExame => {
                let locusExamePreliminar: LocusExamePreliminar = new LocusExamePreliminar();
                //locusExamePreliminar.buscaPreliminar = buscaPreliminar;
                locusExamePreliminar.locus = locusExame.id.locus.codigo;
                locusExamePreliminar.primeiroAlelo = locusExame.primeiroAlelo;
                locusExamePreliminar.segundoAlelo = locusExame.segundoAlelo;
                return locusExamePreliminar;
            }
        );

        this.registrarBusca(buscaPreliminar);

        return buscaPreliminar;
    }

    private recuperarUltimaBuscaRealizada(): BuscaPreliminar{
        let jsonBusca: string = 
            localStorage.getItem(CadastrarBuscaPreliminarComponent.ULTIMA_BUSCA_REALIZADA_KEY);
        return JSON.parse(jsonBusca);
    }

    private registrarBusca(buscaPreliminar: BuscaPreliminar): void{
        localStorage.setItem(
            CadastrarBuscaPreliminarComponent.ULTIMA_BUSCA_REALIZADA_KEY, JSON.stringify(buscaPreliminar));
    }

    /**
     * Método provisório que realiza a chamada a busca preliminar,
     * forçando que nenhum match seja encontrado.
     * 
     * @author Bruno Sousa
     * @memberof CadastrarBuscaPreliminarComponent
     */
    public simularNoMatchs() {
        if (this.validateForm()) {
            let buscaPreliminar: BuscaPreliminar = this.recuperarFormulario();

            this.buscaPreliminarService.simularBuscarPreliminar(buscaPreliminar).then(res => {
                let retornoInclusaoDTO: RetornoInclusaoDTO = new RetornoInclusaoDTO().jsonToEntity(res);

                if (retornoInclusaoDTO.status == StatusRetornoInclusaoDTO.SUCESSO) {
                    this.router.navigateByUrl("pacientes/buscapreliminar/" + retornoInclusaoDTO.idObjeto + "/matchpreliminar");
                }
                else {
                    this.messageBox.alert(res.mensagem).show();
                }

            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            }) 
        }
    }

}
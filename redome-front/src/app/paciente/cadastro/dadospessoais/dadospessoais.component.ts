import {BaseEntidade} from '../../../shared/base.entidade';
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { Subscription } from 'rxjs';
import { PacienteService } from '../../paciente.service';
import { TranslateService } from '@ngx-translate/core';
import { ErroMensagem } from '../../../shared/erromensagem';
import { PacienteConstantes } from '../../paciente.constantes';
import { BaseForm } from '../../../shared/base.form';
import { Component, OnInit, Input } from '@angular/core';
import { Paciente } from '../../paciente';
import { FormGroup, FormBuilder, FormControl, Validators, AbstractControl } from '@angular/forms';
import { UF } from '../../../shared/dominio/uf'
import { Pais } from '../../../shared/dominio/pais'
import { Etnia } from '../../../shared/dominio/etnia'
import { Raca } from '../../../shared/dominio/raca'
import { DominioService } from '../../../shared/dominio/dominio.service'


@Component({
    selector: 'dados-pessoais',
    moduleId: module.id,
    templateUrl: './dadospessoais.component.html',
    //styleUrls: ['../paciente.css']
})
export class DadosPessoaisComponent extends BaseForm<Paciente> implements OnInit {

    public dadosPessoaisForm: FormGroup;

    @Input()
    etapaAtual: String;

    private _ufs: UF[];
    private _paises: Pais[];
    private _etnias: Etnia[];
    private _racas: Raca[];

    groupEtnia: FormGroup;
    groupUf: FormGroup;
    groupRaca: FormGroup;
    groupPais: FormGroup;
    groupResponsavel: FormGroup;

    // Indica INDIGENA foi selecionada na combo Raça
    public racaIndigenaSelecionada:Boolean = false;

    // Indica quando nacionalidade BRASIL é selecionada
    public nacionalidadeBrasilSelecionada:Boolean = false;

          
    ngOnInit(): void {

        this.serviceDominio.getUfs().then(res => {
            this._ufs = res;
        }, (error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
        }); 

        this.serviceDominio.getPaises().then(res => {
            this._paises = res;
        }, (error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
        });

        this.serviceDominio.getEtnias().then(res => {
            this._etnias = res;
        }, (error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
        });

        this.serviceDominio.getRacas().then(res => {
            this._racas = res;
        }, (error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
        });

        this.translate.get("pacienteForm.dadosPessoaisGroup").subscribe(res => {
            this._formLabels = res;
            this.criarMensagensErro(this.dadosPessoaisForm);
            this.setMensagensErro(this.dadosPessoaisForm);
        });
        this.retornarTrueSeNacionalidadeBrasil();
        this.retornarTrueSeRacaIndigena();
    }

    /**
     * Reseta o formulário limpando mensagens de error e valores dos campos
     * 
     * @author Fillipe Queiroz
     * @memberof DadosPessoaisComponent
     */
    resetaFormulario(){
        this.clearMensagensErro(this.dadosPessoaisForm);
        this.dadosPessoaisForm.reset();
    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações 
     * @author Fillipe Queiroz
     */
    buildForm() {
        this.groupEtnia = this.fb.group({
            'id': null
        });

        this.groupPais = this.fb.group({
            'id':[ PacienteConstantes.BRASIL_ID, Validators.required]
        });

        this.groupRaca = this.fb.group({
            'id':[null, Validators.required]
        });

        this.groupUf = this.fb.group({
            'sigla':[null, null]
        });

        this.dadosPessoaisForm = this.fb.group({
            'sexo': [null, Validators.required],
            'abo': [null, Validators.required],
            'etnia' : this.groupEtnia,
            'raca': this.groupRaca,
            'pais':this.groupPais,
            'uf': this.groupUf,
        });
        this.criarFormsErro(this.dadosPessoaisForm);

        //Chamada aos métodos default para que a tela funcione com validações
        //this.criarMensagensErro(this.dadosPessoaisForm);
        //Opcionais
        
        return this.dadosPessoaisForm;

    }

    /**
     * Construtor padrão
     * @param serviceDominio serviço injetado para buscar os dominios 
     * @author Fillipe Queiroz
     */
    constructor(private fb: FormBuilder, private serviceDominio: DominioService, private servicePaciente: PacienteService, translate: TranslateService) {
        super();
        this.translate = translate;

        this.buildForm();
    }
    

    /**
     *  Metodo que valida os campos obrigatórios
     * @author Fillipe Queiroz
     */
    realizarValidacoesDinamicas() {
        this.retornarTrueSeNacionalidadeBrasil();
        this.retornarTrueSeRacaIndigena();
    }

    public obterRacas(): Raca[] {
        return this._racas;
    }

    public obterPaises(): Pais[] {
        return this._paises;
    }


    public fecharModal(modal: any, input: any){
        modal.hide();
        if (input) {
            input.focus();
        }
    }

    /**
     * Método chamado quando a combo de Raca é alterada.
     * Se a opção escolhida for INDIGENA, a etnia é desbloqueada e passa a ser obrigatória.
     * @author Pizão
     */
    public retornarTrueSeRacaIndigena(): void{
        let racaSelecionada: FormControl = 
                    <FormControl> this.getField( this.dadosPessoaisForm, "raca" );
        this.racaIndigenaSelecionada = 
            (racaSelecionada != null 
                && racaSelecionada.value.id == PacienteConstantes.INDIGENA_ID);

        if(this.racaIndigenaSelecionada){
            this.setFieldRequired(this.groupEtnia, 'id');
        } else {
            this.resetFieldRequired(this.groupEtnia,'id');
        }
    }

        /**
     * Método chamado quando a combo nacionalidade é alterada.
     * Se a escolhida for Brasil, deve desbloquear e torna obrigatório
     * o campo naturalidade.
     * @author Pizão
     */
    public retornarTrueSeNacionalidadeBrasil(): void{
        let nacionalidadeSelecionada: FormControl = 
                <FormControl> this.getField( this.dadosPessoaisForm, "pais" );
        this.nacionalidadeBrasilSelecionada = 
                (nacionalidadeSelecionada != null 
                    && nacionalidadeSelecionada.value.id == PacienteConstantes.BRASIL_ID);

        if(this.nacionalidadeBrasilSelecionada){
            this.setFieldRequired(this.groupUf, 'sigla');
        } else {
            this.resetFieldRequired(this.groupUf,'sigla');
        }
    }

    // Override
    public form(): FormGroup{
        return this.dadosPessoaisForm;
    }

    // Override
    public preencherFormulario(paciente:Paciente): void {
        if(paciente){
            this.setPropertyValue("sexo", paciente.sexo);
            this.setPropertyValue("abo", paciente.abo);
            
            if(paciente.raca != null){
                this.setPropertyValue("raca.id", paciente.raca.id);
                this.retornarTrueSeRacaIndigena();
            }

            if (paciente.etnia && paciente.etnia.id) {
                this.setPropertyValue("etnia.id", paciente.etnia.id);
            }

            if(paciente.pais != null){
                this.setPropertyValue("pais.id", paciente.pais.id);
                this.retornarTrueSeNacionalidadeBrasil();
            }
            
            if (paciente.naturalidade && paciente.naturalidade.sigla) {
                this.setPropertyValue("uf.sigla", paciente.naturalidade.sigla);
            }
        }
    }

    nomeComponente(): string {
        return null;
    }

	public get ufs(): UF[] {
		return this._ufs;
	}

	public get paises(): Pais[] {
		return this._paises;
	}

	public get etnias(): Etnia[] {
		return this._etnias;
	}

	public get racas(): Raca[] {
		return this._racas;
	}

}
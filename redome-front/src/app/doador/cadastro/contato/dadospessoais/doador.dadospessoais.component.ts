import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { EstadoCivil } from 'app/shared/dominio/estadoCivil';
import { Municipio } from 'app/shared/dominio/municipio';
import { PacienteConstantes } from '../../../../paciente/paciente.constantes';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';
import { BaseForm } from '../../../../shared/base.form';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import { Etnia } from '../../../../shared/dominio/etnia';
import { Pais } from '../../../../shared/dominio/pais';
import { Raca } from '../../../../shared/dominio/raca';
import { UF } from '../../../../shared/dominio/uf';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { FormatterUtil } from '../../../../shared/util/formatter.util';
import { DoadorNacional } from '../../../doador.nacional';
import { DoadorService } from '../../../doador.service';
import { RessalvaDoador } from '../../../ressalva.doador';

/**
 * Classe que representa o componente de telefone de contato
 * @author Bruno Sousa
 */
@Component({
    selector: "doador-dados-pessoais",
    moduleId: module.id,
    templateUrl: "./doador.dadospessoais.component.html",
    //styleUrls: ['../../paciente.css']
})
export class DoadorDadosPessoaisComponent extends BaseForm<DoadorNacional> implements OnInit, AfterViewInit {

    public dadosPessoaisForm: FormGroup;

    mostraDados: String = '';
    mostraFormulario: String = 'hide';
    private _doador: DoadorNacional;
    
    public _ufs: UF[];
    private _paises: Pais[];
    private _etnias: Etnia[];
    private _racas: Raca[];
    private _estadosCivis: EstadoCivil[];
    racaTemEtnias:boolean = false;
    ehBrasileiro:boolean = false;
    ressalva:string;

    @Input()
    public fase: number;

    @ViewChild('modalMsg') 
    public modalMsg;

    public data: Array<string | RegExp>
    private _esconderLinkAlterarDadosPessoais: boolean = false;
    public _municipios: Municipio[];

    @Input()
    set esconderLinkAlterarIdentificacao (value: string) {
        if (!value) {
            this._esconderLinkAlterarDadosPessoais = true;
        } else {
            this._esconderLinkAlterarDadosPessoais = value == 'true' ? true : false;
        }
        
    }

    constructor(private fb: FormBuilder, translate: TranslateService, private doadorService:DoadorService,
        private activatedRouter: ActivatedRoute,
        private autenticacaoService:AutenticacaoService, private serviceDominio: DominioService) {
        super();
        this.translate = translate;
        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]
        this.buildForm();
    }
    
    ngOnInit(): void {

        this.translate.get("DoadorDadosPessoais").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.dadosPessoaisForm);
            this.setMensagensErro(this.dadosPessoaisForm);
        });

        this.serviceDominio.getUfs().then(res => {
            this._ufs = [];
            if (res) {
                res.forEach(uf => {
                    this._ufs.push(new UF().jsonToEntity(uf));
                });
            } 
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

        this.serviceDominio.getEstadosCivis().then(res => {
            this._estadosCivis = res;
        }, (error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
        });
    }

    ngAfterViewInit(): void {
        setTimeout(() => {
            if(this.fase == 2){
                this.setFieldRequired(this.form(), 'abo');
            }
        });
    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações 
     * @author Fillipe Queiroz
     */
    buildForm() {
        this.dadosPessoaisForm = this.fb.group({
            'raca': [null],
            'etnia': [null], 
            'naturalidade': [null],
            'municipio': [null],
            'nacionalidade': [null, Validators.required],
            'peso': [null],
            'altura': [null],
            'nomePai': [null],
            'ressalva':[null],
            'fumante': [false, null],
            'estadoCivil': [null, Validators.required],
            'abo': [null]
        });
    }

    public form(): FormGroup {
        return this.dadosPessoaisForm;
    }

    public preencherFormulario(doador: DoadorNacional): void {
        this._doador = doador;
        if (this._doador.raca) {
            this.setPropertyValue('raca', this._doador.raca.id);
        }
        if (this._doador.etnia) {
            this.setPropertyValue('etnia', this._doador.etnia.id);
        }
        if (this._doador.naturalidade) {
            this.popularListaMunicpiosPorUf(this._doador.naturalidade.uf.sigla);
            this.setPropertyValue('naturalidade', this._doador.naturalidade.uf.sigla);
            this.setPropertyValue('municipio', this._doador.naturalidade.id);
        }
        if (this._doador.pais) {
            this.setPropertyValue('nacionalidade', this._doador.pais.id);
        }
        this.setPropertyValue('peso', this._doador.peso);
        this.setPropertyValue('altura', this._doador.altura);
        this.setPropertyValue('nomePai', this._doador.nomePai);
        this.setPropertyValue('abo', this._doador.abo);

        this.racaTemEtnias = this._doador.raca && PacienteConstantes.RACA_INDIGENA_COM_ETNIAS == this._doador.raca.nome
        if (this.racaTemEtnias) {
            this.setFieldRequired(this.form(), 'etnia');
        }

        this.esconderNaturalidade(this._doador.pais.id);

        this.setPropertyValue('fumante', this._doador.fumante);
        
        if(this._doador.ressalvas){

            this.setPropertyValue('ressalva',this._doador.ressalvas.map(ressalva=>{
                return ressalva.observacao;
            }).join("|"));

            this.ressalva = this.dadosPessoaisForm.get('ressalva').value;
        }

        if (this._doador.estadoCivil) {
            this.setPropertyValue('estadoCivil', this._doador.estadoCivil.id);
        }

    }

    nomeComponente(): string {
        return "DoadorDadosPessoaisComponent";
    }

    private editar() {
        this.mostraDados = 'hide';
        this.mostraFormulario = '';
    }

    cancelarEdicao() {
        this.mostraFormulario = 'hide';
        this.mostraDados = '';        
    }

	public get doador(): DoadorNacional {
		return this._doador;
    }
    
    salvarDadosPessoais() {
        if (this.validateForm()) {
            let racaId: number = this.dadosPessoaisForm.get("raca").value || null;
            if (racaId) {
                let raca: Raca = this._racas.find(obj => obj.id == racaId);
                this._doador.raca = new Raca(racaId, raca.nome);
            }
            else {
                this._doador.raca = null;
            }

            let etniaId: number = this.dadosPessoaisForm.get("etnia").value || null;
            if (etniaId) {
                if (this._doador.raca && PacienteConstantes.RACA_INDIGENA_COM_ETNIAS == this._doador.raca.nome) {
                    let etnia:Etnia=this._etnias.find(etnia=> etnia.id == etniaId);
                    this._doador.etnia = new Etnia(etniaId, etnia.nome);
                }
                else {
                    this._doador.etnia = null;
                }
            }
            else {
                this._doador.etnia = null;
            }

            let paisId: number = this.dadosPessoaisForm.get("nacionalidade").value || null;
            if (paisId) {
                let pais: Pais = this._paises.find(obj => obj.id == paisId);
                this._doador.pais = new Pais(paisId, pais.nome);
            }

            let id: number = this.dadosPessoaisForm.get("municipio").value || null;
            if (id != null && this._doador.pais && this._doador.pais.nome == PacienteConstantes.PAIS_BRASIL) {
                let municipio: Municipio = this._municipios.find(muni=> muni.id == id);
                this._doador.naturalidade = new Municipio(municipio.id, municipio.uf.sigla);
            }
            else {
                this._doador.naturalidade = null;
            }
            this._doador.peso = FormatterUtil.obterPesoSemMascara(this.dadosPessoaisForm.get('peso').value);
            this._doador.altura = FormatterUtil.obterAlturaSemMascara(this.dadosPessoaisForm.get('altura').value);
            this._doador.nomePai = this.dadosPessoaisForm.get('nomePai').value;
            this._doador.abo = this.dadosPessoaisForm.get('abo').value;
            this._doador.fumante = this.dadosPessoaisForm.get('fumante').value || false;

            let ressalva:string = this.dadosPessoaisForm.get('ressalva').value;
            this._doador.ressalvas = [];
            let ressalvaDoador:RessalvaDoador= new RessalvaDoador();
            ressalvaDoador.observacao = ressalva;
            this.ressalva =ressalva;
            this._doador.ressalvas.push(ressalvaDoador);

            this.doadorService.alterarDadosPessoais(this._doador.id, this._doador).then(
                res => {
                    this.cancelarEdicao();
                },
                (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
            });

            let estadoCivilId: number = this.dadosPessoaisForm.get("estadoCivil").value || null;
            if (estadoCivilId) {
                let estadoCivil: EstadoCivil = this._estadosCivis.find(obj => obj.id == estadoCivilId);
                this._doador.estadoCivil = new EstadoCivil(estadoCivilId, estadoCivil.nome);
            }
            else {
                this._doador.estadoCivil = null;
            }

        }
    }   

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalMsg.mensagem = obj.mensagem;
        })
        this.modalMsg.abrirModal();
    } 


    deveEsconderLinkAlterarDadosPessoais(): boolean {
        return this._esconderLinkAlterarDadosPessoais || !this.temPermissaoParaAlterarDadosPessoais();
    }

    temPermissaoParaAlterarDadosPessoais(): boolean {
        return this.autenticacaoService.temRecurso('ATUALIZAR_DADOS_PESSOAIS_DOADOR');
    }

    esconderEtnia(idRaca){
        let racaAtual:Raca = this._racas.find(raca=>raca.id == idRaca);

        this.racaTemEtnias = racaAtual && PacienteConstantes.RACA_INDIGENA_COM_ETNIAS == racaAtual.nome
        if(this.racaTemEtnias){
            this.setFieldRequired(this.form(),'etnia');
        }
    }

    esconderNaturalidade(idPais) {
        this.ehBrasileiro = PacienteConstantes.BRASIL_ID == idPais;
        if(this.ehBrasileiro){
            this.setFieldRequired(this.form(),'naturalidade');
            this.setFieldRequired(this.form(),'municipio');
        }
        else  {
            this.resetFieldRequired(this.form(),'naturalidade');
            this.resetFieldRequired(this.form(),'municipio');
        }
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

	public get municipios(): Municipio[] {
		return this._municipios;
    }

    /**
     * Getter estadoCivis
     * @return {EstadoCivil[]}
     */
	public get estadosCivis(): EstadoCivil[] {
		return this._estadosCivis;
	}

    /**
     * Retorna TRUE se a fase atual for 3.
     */
    public isFase3(): boolean {
        return this.fase == 2;
    }

    private popularListaMunicpiosPorUf(sigla: string): void {
        this.serviceDominio.listarMunicipiosPorUf(sigla).then(res => {
            this._municipios = [];
            if (res) {
                res.forEach(municipio => {
                    this._municipios.push(new Municipio().jsonToEntity(municipio));
                });
            }             
        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        }); 
    }
       

}
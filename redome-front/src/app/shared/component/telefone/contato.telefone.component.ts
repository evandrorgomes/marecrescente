import { BaseForm } from '../../base.form';
import { DominioService } from '../../dominio/dominio.service';
import { CodigoInternacional } from '../../dominio/codigo.internacional';
import { ContatoTelefonico } from '../../classes/contato.telefonico';
import { TranslateService } from '@ngx-translate/core';
import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray } from "@angular/forms";

/**
 * Classe que representa o componente de telefone de contato
 * @author Bruno Sousa
 */
@Component({
    selector: "contato-telefone",
    moduleId: module.id,
    templateUrl: "./contato.telefone.component.html"
})
export class ContatoTelefoneComponent extends BaseForm<ContatoTelefonico[]> implements OnInit {

    public telefoneForm: FormGroup;

    listaCodigoInternacional: CodigoInternacional[];
    
    escondeBotaoAdicionarTelefone:boolean = false;

    private _validarPrincipal: boolean = true;

    @Input()
    set esconderBotaoAdicionarTelefone (value: string) {
        if (!value) {
            this.escondeBotaoAdicionarTelefone = true;
        } else {
            this.escondeBotaoAdicionarTelefone = value == 'true' ? true : false;
        }
        
    }
    
	@Input()
    set validarPrincipal (value: string) {
        if (!value) {
            this._validarPrincipal = true;
        } else {
            this._validarPrincipal = value == 'true' ? true : false;
        }
        
    }

    @Input()
    esconderFlagPrincipal: boolean = false;

    /**
     * Construtor 
     * @param serviceDominio - serviço de dominio para buscar os dominios
     * @author Bruno Sousa
     */
    constructor(private fb: FormBuilder, private serviceDominio: DominioService, translate: TranslateService) {
        super();
        this.translate = translate;
        this.buildForm();
    }

    ngOnInit() {
        this.serviceDominio.listarCodigoInternacional().then(res => {
            this.listaCodigoInternacional = res;
        });
    }

    /**
     * Método principal de construção do formulário
     * @author Rafael Pizão
     */
    buildForm() {
        this.criarListasTelefone(1);
    }

    /**
     * Cria as linhas para a lista de telefones, considerando a quantidade solicitada
     * no parametro.
     * 
     * @param qtdLinhas quantidade de linhas de telefone a serem criadas. 
     */
    private criarListasTelefone(qtdLinhas:number):void{
        let telefoneFormArray: FormArray = new FormArray([]);

        for(let i=0; i < qtdLinhas; i++){
            telefoneFormArray.setControl(i, this.criarTelefoneGroup());
        }

        this.telefoneForm = this.fb.group({
            listaTelefone: telefoneFormArray
        });
    }

    /**
     * Verifica se o formulário é válido.
     * @author Rafael Pizão
     */
    public validateForm(): boolean {
        super.validateForm();
        this.resetMsgErrors();
        return this.verificarValidacaoTelefones();
    }

    /**
     * Método para remover um item dos telefones
     * @param item - referente a um telefone
     * @author Rafael Pizão
     */
    removerContato(i) {
        this.listaTelefoneForm.removeAt(i);
    }

    /**
     * reseta as mensagens de erro de telefones
     * @author Rafael Pizão
     */
    resetMsgErrors() {
        this.formErrors['telefonePrincipal'] = '';
        this.formErrors['telefonesContato'] = '';
    }
    
    /**
     * verifica as obrigatoriedades de telefone
     * @author Bruno Sousa
     * @return boolean se foi validado com sucesso
     */
    verificarValidacaoTelefones():boolean{
        let valid: boolean = this.telefoneForm.valid;
        if(!valid){
            this.translate.get("mensagem.erro.camposObrigatorios").subscribe(res => {
                this.formErrors["telefonesContato"] = res;
            });
            return valid;
        }

        if(this._validarPrincipal){
            let validPrincipal: boolean = false;
            if (this.listaTelefoneForm.length > 1) { 
                this.listaTelefoneForm.controls.forEach((telGroup: FormGroup) => {
                    if (telGroup.get('principal').value) {
                        validPrincipal = true;
                    }
                });
            }
            else {
                this.listaTelefoneForm.get('0.principal').setValue(true);
                validPrincipal = true;
            }
            if (!validPrincipal) {
                this.translate.get("mensagem.erro.campoPrincipal", {campo: 'telefone'}).subscribe(res => {
                    this.formErrors['telefonePrincipal'] = res;
                });
            }
            valid = validPrincipal;
        }

        return valid;
    }

    get listaTelefoneForm(): FormArray { 
        return this.telefoneForm.get('listaTelefone') as FormArray; 
    }

    adicionarTel() {
        this.listaTelefoneForm.push(this.criarTelefoneGroup());
    }

    private criarTelefoneGroup(): FormGroup {
        return this.fb.group({
            principal: false,
            nome: [null,  Validators.required],
            tipo: ['1',  Validators.required],
            codInter: [55,  Validators.required],
            codArea: [null,  Validators.required],
            numero: [null,  Validators.required],
            complemento: null
        });
    }

    /**
     * Retorna a lista com os telefones.
     * @author Bruno Sousa
     */
    listarTelefonesContato(): ContatoTelefonico[] {
        let retorno: ContatoTelefonico[] = [];
        this.listaTelefoneForm.controls.forEach((telGroup: FormGroup) => {
            let telContato: ContatoTelefonico = new ContatoTelefonico();
            telContato.principal = telGroup.get("principal").value;
            telContato.nome = telGroup.get("nome").value;
            telContato.tipo = telGroup.get("tipo").value;
            telContato.codInter = telGroup.get("codInter").value;
            telContato.codArea = telGroup.get("codArea").value;
            telContato.numero = telGroup.get("numero").value;
            telContato.complemento = telGroup.get("complemento").value != null ? telGroup.get("complemento").value : '';
            telContato.excluido = false;
            retorno.push(telContato);
        });
        return retorno;
    }

    marcarComoPrincipal(i) {
        let lista: FormArray = this.listaTelefoneForm;

        if (lista.get(i + ".principal").value == true) {
            for (let p = 0; p <= lista.length - 1; p++) {
                if (p==i) {
                    continue;
                }
                lista.get(p + ".principal").setValue(false);
            }
        }
    }

    public form(): FormGroup{
        return this.telefoneForm;
    }

    public preencherFormulario(contatos:ContatoTelefonico[]): void {
        if(contatos){
            this.criarListasTelefone(contatos.length);

            let index:number = 0;
            contatos.forEach(contato => {
                
                this.setPropertyValue("listaTelefone." + index + ".tipo", contato.tipo);
                this.setPropertyValue("listaTelefone." + index + ".codInter", contato.codInter);
                this.setPropertyValue("listaTelefone." + index + ".codArea", contato.codArea);
                this.setPropertyValue("listaTelefone." + index + ".numero", contato.numero);
                this.setPropertyValue("listaTelefone." + index + ".nome", contato.nome);
                this.setPropertyValue("listaTelefone." + index + ".complemento", contato.complemento != null ? contato.complemento : '');
                this.setPropertyValue("listaTelefone." + index + ".principal", contato.principal);
                index++;
            });
        }
    }

    nomeComponente(): string {
        return "ContatoTelefoneComponent";
    }


};
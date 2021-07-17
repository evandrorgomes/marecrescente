import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl, ValidationErrors } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UsuarioService } from 'app/admin/usuario/usuario.service';
import { ArrayControl } from 'app/shared/buildform/controls/array.control';
import { Laboratorio } from 'app/shared/dominio/laboratorio';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { LaboratorioService } from 'app/shared/service/laboratorio.service';
import { ErroUtil } from 'app/shared/util/erro.util';
import { BuildForm } from '../../../shared/buildform/build.form';
import { StringControl } from '../../../shared/buildform/controls/string.control';
import { RouterUtil } from '../../../shared/util/router.util';
import { Usuario } from './../../../shared/dominio/usuario';
import { BaseForm } from 'app/shared/base.form';
/**
 * Classe que representa o componente de detalhe do paciente
 * @author ergomes
 */
@Component({
    selector: 'datelhe-laboratorio',
    templateUrl: './detalhe.laboratorio.component.html'
})
export class DetalheLaboratorioComponent extends BaseForm<Laboratorio> implements OnInit {

    public laboratorio: Laboratorio;
    public dadosSomenteLeitura:Boolean = false;
    
    public formulario: BuildForm<Laboratorio>;

    private _id: number;
    private _usuarios: Usuario[] = [];
    public _modoInclusaoHabilitado: boolean = true;

    public _exibirLinkEditarUsuarios: boolean = false;
    public _mostraFormularioUsuarios: string = 'hide';
    public _mostraUsuarios: string = '';
    public _exibirBotoesEditarUsuarios: boolean = false;
    
    public _mostraLaboratorio: string = '';
    public _exibirLinkEditarLaboratorio: boolean = true;
    public _mostraFormularioLaboratorio: string = 'hide';
    public _exibirBotoesEditarLaboratorio: boolean = true;

    private _usuariosDisponiveis: Usuario[] = [];
    private _usuariosSelecionados: Usuario[] = [];
    private _buildFormDisponiveis: BuildForm<any>;
    private _buildFormSelecionados: BuildForm<any>;

    private _buildForm: BuildForm<Laboratorio>;


    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Evandro Gomes
     */
    constructor(protected translate: TranslateService,
        protected activatedRouter: ActivatedRoute,
        protected router: Router,
        protected laboratorioService: LaboratorioService,
        protected messageBox: MessageBox,
        protected usuarioService: UsuarioService) {

        super();
        this.translate = translate;
    
        this.formulario = new BuildForm<Laboratorio>();
        this.formulario.add(
            new StringControl("nome", [ Validators.required ]),
            new StringControl("uf", [ Validators.required ]),
            new StringControl("tipoLogradouro", [ Validators.required ])
        );

        this._buildFormDisponiveis = new BuildForm<any>()
        .add(new ArrayControl("selecionados"));

        this._buildFormSelecionados = new BuildForm<any>();
        this._buildFormSelecionados.add(new ArrayControl("selecionados", [this.ValidateListMin, this.ValidateMaxItens] ));
    }

    /**
     * 
     * @memberOf DetalheComponent
     */
    ngOnInit(): void {

        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idLaboratorio").then(res => {
            this.modoEdicao();
            this.id = <number>res;
            this.obterLaboratorio();
        });

        this.usuarioService.listarUsuarioLaboratorio().then(res => {
            if (res) {
                res.forEach(usu => {
                    this._usuariosDisponiveis.push(new Usuario().jsonToEntity(usu));
                });
            }
         }, (error: ErroMensagem) => {
            this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
        });
    }

    private obterLaboratorio() {
        this.laboratorioService.obterLaboratorio(this.id).then(res => {
            this.laboratorio = new Laboratorio().jsonToEntity(res);
            this.formulario.get("nome").value = this.laboratorio.nome;
            this.formulario.get("uf").value = this.laboratorio.endereco.municipio.uf;
            this._modoInclusaoHabilitado = false;
            this.dadosSomenteLeitura = true;
        });
    }

    public adicionarUsuarios(usuariosLista? : any[]) {
        if ((this._buildFormDisponiveis.value && this._buildFormDisponiveis.value.selecionados && 
            this._buildFormDisponiveis.value.selecionados.length != 0)||usuariosLista) {
            let selecionados = usuariosLista? usuariosLista: this._buildFormDisponiveis.value.selecionados;
            for (let idx = 0; idx < selecionados.length; idx++) {
                let indice:number = 0;
                let usuario: Usuario = this._usuariosDisponiveis
                    .find((usuario, index) => {
                        indice = index;
                        return usuario.id == selecionados[idx];
                    });
                if (usuario) {
                    this._usuariosDisponiveis.splice(indice, 1);
                    this._usuariosSelecionados.push(usuario);
                }
            }
            this._buildFormSelecionados.getChildAsBuildFormControl("selecionados").value = 
                this._usuariosSelecionados.map(laboratorio => laboratorio.id);
        }
    }

    public removerUsuarios() {
        if (this._buildFormSelecionados.value && this._buildFormSelecionados.value.selecionados && 
            this._buildFormSelecionados.value.selecionados.length != 0) {
            let selecionados = this._buildFormSelecionados.value.selecionados;
            for (let idx = 0; idx < selecionados.length; idx++) {
                let indice:number = 0;
                let usuario: Usuario = this._usuariosSelecionados
                    .find((usuario, index) => {
                        indice = index;
                        return usuario.id == selecionados[idx];
                    });
                if (usuario) {
                    this._usuariosSelecionados.splice(indice, 1);
                    this._usuariosDisponiveis.push(usuario);
                }
            }
            this._buildFormSelecionados.form.reset();
            if (this._usuariosSelecionados.length != 0) {                
                this._buildFormSelecionados.getChildAsBuildFormControl("selecionados").value = 
                    this._usuariosSelecionados.map(laboratorio => laboratorio.id);
            }
        }
    }

    public salvarEdicaoUsuarios(){
        if (this._buildFormSelecionados.valid) {
            this.laboratorioService.atualizarUsuarios(this._id, this.usuariosSelecionados).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withTarget(this)
                    .withCloseOption((target?: any) => {
                        this.obterLaboratorio();
                        this.cancelarEdicaoUsuarios();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    public cancelarEdicaoUsuarios(): void {
        this._exibirLinkEditarUsuarios = true;
        this._mostraFormularioUsuarios = 'hide';
        this._mostraUsuarios = '';
        this._exibirBotoesEditarUsuarios = false;
    }
    public editarUsuarios(){
        this._exibirLinkEditarUsuarios = false;
        this._mostraFormularioUsuarios = '';
        this._mostraUsuarios = 'hide';
        this._exibirBotoesEditarUsuarios = true;

        if (this.laboratorio.usuarios &&this.laboratorio.usuarios.length > 0) {
            let listaUsuarios: any[] = [];
            this.laboratorio.usuarios.forEach( usuario =>{
                listaUsuarios.push(usuario.id);
            })
            this._usuariosSelecionados = this.laboratorio.usuarios;
        }
    }

    public formUsuariosDisponiveis(): FormGroup {
        return this._buildFormDisponiveis.form;
    }

    public formUsuariosSelecionados(): FormGroup {
        return this._buildFormSelecionados.form;
    }

    public cancelarEdicao(): void{
        this.dadosSomenteLeitura = true;
    }

    public salvar(): void {
    }

    public get modoInclusaoHabilitado(): boolean{
        return this._modoInclusaoHabilitado;
    }

    nomeComponente(): string {
        return "DetalheLaboratorioComponent";
    }

    private modoEdicao() {
        this._modoInclusaoHabilitado = false;
        this._exibirLinkEditarUsuarios = true;
        this._mostraUsuarios = '';

        this._mostraLaboratorio = '';
        this._exibirLinkEditarLaboratorio = true;
        this._mostraFormularioLaboratorio = 'hide';
        this._exibirBotoesEditarLaboratorio = true;
    }

    private modoInclusao() {
        this._modoInclusaoHabilitado = true;
        this._mostraFormularioUsuarios = '';
        this._mostraUsuarios = 'hide';

        this._mostraLaboratorio = 'hide';
        this._exibirLinkEditarLaboratorio = false;
        this._mostraFormularioLaboratorio = '';
        this._exibirBotoesEditarLaboratorio = false;
    }

    public form(): FormGroup {
        return this.formulario.form;
    }

    public preencherFormulario(entidade: Laboratorio): void {
        throw new Error("Method not implemented.");
    }

    ValidateListMin(control: FormControl): ValidationErrors {
        let value: any[] = control.value;
        if (!value && control.dirty && !control.pristine) {
            if(!value || value.length < 1){
                return { "required" : true }
            }
        } 
        return null;
    }

    ValidateMaxItens(control: FormControl): ValidationErrors {
        let value: any[] = control.value;
        if (value && control.dirty && !control.pristine) {
            if(value && value.length > 3){
                return { "invalidMaxItens" : true }
            }
        } 
        return null;
    }

    /**
     * Getter usuariosDisponiveis
     * @return {Usuario[] }
     */
	public get usuariosDisponiveis(): Usuario[]  {
		return this._usuariosDisponiveis;
	}

    /**
     * Setter usuariosDisponiveis
     * @param {Usuario[] } value
     */
	public set usuariosDisponiveis(value: Usuario[] ) {
		this._usuariosDisponiveis = value;
    }
    

    /**
     * Getter usuariosSelecionados
     * @return {Usuario[] }
     */
	public get usuariosSelecionados(): Usuario[]  {
		return this._usuariosSelecionados;
	}

    /**
     * Setter usuariosSelecionados
     * @param {Usuario[] } value
     */
	public set usuariosSelecionados(value: Usuario[] ) {
		this._usuariosSelecionados = value;
	}

    /**
     * Getter usuarios
     * @return {Usuario[] }
     */
	public get usuarios(): Usuario[]  {
		return this._usuarios;
	}

    /**
     * Setter usuarios
     * @param {Usuario[] } value
     */
	public set usuarios(value: Usuario[] ) {
		this._usuarios = value;
	}

    public get id(): number {
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }

}
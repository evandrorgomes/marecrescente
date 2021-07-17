import { FuncaoTransplante } from './../../../../shared/dominio/funca.transplante';
import { forEach } from '@angular/router/src/utils/collection';
import { Component, OnInit } from "@angular/core";
import { PermissaoRotaComponente } from "app/shared/permissao.rota.componente";
import { BuildForm } from "app/shared/buildform/build.form";
import { Router } from "@angular/router";
import { CentroTransplanteService } from "app/admin/centrotransplante/centrotransplante.service";
import { UsuarioService } from "app/admin/usuario/usuario.service";
import { MessageBox } from "app/shared/modal/message.box";
import { NumberControl } from "app/shared/buildform/controls/number.control";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ComponenteRecurso } from "app/shared/enums/componente.recurso";
import { UsuarioLogado } from "../../../../shared/dominio/usuario.logado";
import { FuncaoCentroTransplante } from '../../../../shared/enums/funcao.centro.transplante';
import { CentroTransplante } from '../../../../shared/dominio/centro.transplante';
import { ErroMensagem } from 'app/shared/erromensagem';
import { ErroUtil } from 'app/shared/util/erro.util';
import { StringControl } from 'app/shared/buildform/controls/string.control';
import { ArrayControl } from 'app/shared/buildform/controls/array.control';

@Component({
    selector: 'analista-busca-centro-avaliador',
    templateUrl: './analistabusca.centroavaliador.component.html'    
  })
export class AnalistaBuscaCentroAvaliadorComponent implements PermissaoRotaComponente, OnInit {

    private _idUsuarioSelecionado: number;
    private _buildForm: BuildForm<UsuarioLogado>;
    private _buildFormDisponiveis: BuildForm<any>;
    private _buildFormSelecionados: BuildForm<any>;

    public _usuarios: UsuarioLogado[];

    private _centrosAvaliadores: CentroTransplante[];
    private _centrosDiponives: CentroTransplante[];

    private _centrosSelecionados: CentroTransplante[];


    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private router: Router, private fb:FormBuilder,
        private centroTransplanteService: CentroTransplanteService,
        private usuarioService: UsuarioService, private messageBox: MessageBox) {

        this._buildForm = new BuildForm<UsuarioLogado>()
            .add(new NumberControl("id"));

        this._buildFormDisponiveis = new BuildForm<any>()
            .add(new ArrayControl("selecionados"));

        this._buildFormSelecionados = new BuildForm<any>()
            .add(new ArrayControl("selecionados"));
        
    }

    ngOnInit(): void {
        this._idUsuarioSelecionado = null;
        this._centrosDiponives = [];
        this._centrosSelecionados = [];
        this.usuarioService.listarAnalistasBusca().then(res => {
            this._usuarios = [];
            if (res) {
                res.forEach(usuario => {
                    this._usuarios.push(new UsuarioLogado().jsonToEntity(usuario));
                });
            }
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

        this.centroTransplanteService.listarCentroTransplantes(null, null, null, FuncaoCentroTransplante.AVALIADOR).then(res => {
            this._centrosAvaliadores = [];
            if (res && res.content) {
                res.content.forEach(centro => {
                    this._centrosAvaliadores.push(new CentroTransplante().jsonToEntity(centro));
                });
            }            
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.AnalistaBuscaCentroAvaliadorComponent];
    }

    public form(): FormGroup {
        return this._buildForm.form;
    }

    public formCentrosDisponiveis(): FormGroup {
        return this._buildFormDisponiveis.form;
    }

    public formCentrosSelecionados(): FormGroup {
        return this._buildFormSelecionados.form;
    }

    public salvar(): void {
        if (this._idUsuarioSelecionado) {
            let usuario: UsuarioLogado = this._usuarios.find(usuario => usuario.id == this._idUsuarioSelecionado);
            usuario.centrosTransplantesParaTarefas = this._centrosSelecionados;
            this.usuarioService.atualizarRelacaoCentroAvaliador(this._idUsuarioSelecionado, usuario).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withCloseOption((targer?: any) => {
                        this.ngOnInit();
                    })
                    .show();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });

        }
    }

    public get listaCentrosDisponiveis(): CentroTransplante[] {
        return this._centrosDiponives;
    }

    public get listaCentrosSelecionados(): CentroTransplante[] {
        return this._centrosSelecionados;        
    }

    public selecionarUsuario(value: number) {
        this._idUsuarioSelecionado = value;        
        if (this._idUsuarioSelecionado) {
            let usuario: UsuarioLogado = this._usuarios.find(usuario => usuario.id == this._idUsuarioSelecionado);
            if (usuario.centrosTransplantesParaTarefas) {
                this._centrosDiponives = this._centrosAvaliadores.filter(centro => {
                    let achou = usuario.centrosTransplantesParaTarefas.some(usuarioCentro => usuarioCentro.id == centro.id);                    
                    return !achou;
                });
                this._centrosSelecionados = usuario.centrosTransplantesParaTarefas;
            }
            else {
                this._centrosDiponives = this._centrosAvaliadores;
                this._centrosSelecionados = [];
            }
        }
        else {
            this._centrosDiponives = [];
            this._centrosSelecionados = [];
        }
    }

    public adicionarCentros() {
        let usuario: UsuarioLogado = this._usuarios.find(usuario => usuario.id == this._idUsuarioSelecionado);        
        if (this._buildFormDisponiveis.value && this._buildFormDisponiveis.value.selecionados && 
            this._buildFormDisponiveis.value.selecionados.length != 0) {
            let selecionados = this._buildFormDisponiveis.value.selecionados;
            for (let idx = 0; idx <= selecionados.length - 1; idx++) {                
                let centro: CentroTransplante = this._centrosAvaliadores
                    .find((centro, index) => centro.id == selecionados[idx]);
                if (centro) {
                    this._centrosSelecionados.push(centro);
                }
            }
            if (this._centrosSelecionados.length != 0) {
                this._buildFormDisponiveis.form.reset();
                this._centrosDiponives = this._centrosAvaliadores.filter(centro => {
                    let achou = this._centrosSelecionados.some(usuarioCentro => usuarioCentro.id == centro.id);
                    return !achou;
                });
            }
        }
    }

    public removerCentros() {
        let usuario: UsuarioLogado = this._usuarios.find(usuario => usuario.id == this._idUsuarioSelecionado);        
        if (this._buildFormSelecionados.value && this._buildFormSelecionados.value.selecionados && 
            this._buildFormSelecionados.value.selecionados.length != 0) {
            let selecionados = this._buildFormSelecionados.value.selecionados;
            let centrosTransplantesParaTarefas = this._centrosSelecionados;
            for (let idx = 0; idx <= selecionados.length - 1; idx++) {
                let index = centrosTransplantesParaTarefas.findIndex(
                    centro => centro.id == selecionados[idx]);
                if (index != undefined) {
                    centrosTransplantesParaTarefas = centrosTransplantesParaTarefas
                        .filter((centro, idx2) => idx2 != index);
                }
            }
            
            this._centrosSelecionados = centrosTransplantesParaTarefas;
            this._buildFormSelecionados.form.reset();
        
            this._buildFormDisponiveis.form.reset();
            this._centrosDiponives = this._centrosAvaliadores.filter(centro => {
                let achou = this._centrosSelecionados.some(usuarioCentro => usuarioCentro.id == centro.id);                    
                return !achou;
            });
        
        }
    }

    public habiltaBotaoAdicionar(): boolean {
        return !(this._idUsuarioSelecionado && this._centrosDiponives && this._centrosDiponives.length != 0 
                && this._buildFormDisponiveis.value.selecionados && this._buildFormDisponiveis.value.selecionados.length != 0);
    }

    public habiltaBotaoRemover(): boolean {
        return !(this._idUsuarioSelecionado && this._buildFormSelecionados.value.selecionados
            && this._buildFormSelecionados.value.selecionados.length != 0);
    }

    public habiltaBotaoSalvar(): boolean {
        return !this._idUsuarioSelecionado
    }
}
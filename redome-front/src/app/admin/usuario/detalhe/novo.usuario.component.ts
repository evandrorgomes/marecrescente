import { Component } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { DetalheUsuarioComponent } from "app/admin/usuario/detalhe/detalhe.usuario.component";
import { Perfil } from "app/shared/dominio/perfil";
import { ErroMensagem } from "app/shared/erromensagem";
import { ArrayUtil } from "app/shared/util/array.util";
import { ErroUtil } from "app/shared/util/erro.util";
import { MessageBox } from "../../../shared/modal/message.box";
import { SistemaService } from "../../../shared/service/sistema.service";
import { PerfilService } from "../../perfil/perfil.service";
import { UsuarioService } from "../usuario.service";
import { BscupService } from "app/shared/service/bscup.service";

/**
 * @description Abre a tela para cadastro de um novo usuário, reutilizando
 * a tela de detalhe como base.
 * @author Pizão
 * @export
 * @class NovoUsuarioComponent
 * @extends {DetalheUsuarioComponent}
 */
@Component({
    selector: 'novo-usuario',
    templateUrl: './detalhe.usuario.component.html'
})
export class NovoUsuarioComponent extends DetalheUsuarioComponent {

    constructor(protected translate: TranslateService, 
        protected activatedRouter: ActivatedRoute,
        protected router: Router,
        protected usuarioService: UsuarioService,
        protected messageBox: MessageBox,
        protected perfilService: PerfilService,
        protected sistemaService: SistemaService,
        protected bscupService: BscupService) {
        
        super(translate, activatedRouter,
            router, usuarioService,
            messageBox, perfilService,
            sistemaService, bscupService);

        this.habilitarModoInclusao();
    }

    /**
     * @description Lista os perfis para disponibiliza-los para seleção no
     * cadastro do novo usuário (método reimplementado para diferenciar o comportamento
     * com a tela de detalhe).
     * @author Pizão
     * @protected
     */
    protected listarPerfis(): void{
        this.perfilService.listarPerfis().then(perfis => {
            this.listaPerfisDisponiveis = [];
            
            perfis.forEach(jsonPerfil => {
                let perfil: Perfil = new Perfil().jsonToEntity(jsonPerfil);
                this.listaPerfisDisponiveis.push(perfil);
            });

            this.listaTodosPerfis = ArrayUtil.clone(this.listaPerfisDisponiveis);
            ArrayUtil.sort(this.listaPerfisDisponiveis, "descricao");

        }, (error: ErroMensagem) => {
            this.messageBox.alert(ErroUtil.extrairMensagensErro(error));
        });
    }

    nomeComponente(): string {
        return "NovoUsuarioComponent";
    }
}
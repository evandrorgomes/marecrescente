import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Busca } from 'app/paciente/busca/busca';
import { DiagnosticoComponent } from 'app/paciente/cadastro/diagnostico/diagnostico.component';
import { Paciente } from 'app/paciente/paciente';
import { ComponenteRecurso } from 'app/shared/enums/componente.recurso';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { PermissaoRotaComponente } from 'app/shared/permissao.rota.componente';
import { RouterUtil } from 'app/shared/util/router.util';
import { BaseForm } from '../../../shared/base.form';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { Modal } from '../../../shared/modal/factory/modal.factory';
import { HeaderPacienteComponent } from '../../consulta/identificacao/header.paciente.component';
import { PacienteService } from '../../paciente.service';
import { ErroUtil } from '../../../shared/util/erro.util';
/**
 * @description Tela para solicitação de nova busca para um determinado paciente.
 * Essa nova busca é solicitada após o paciente já ter passado pelo sistema do Redome, ou seja,
 * já teve uma busca anterior finalizada (por algum motivo cancelada ou mesmo já transplantado). 
 * 
 * @author Pizão
 * @export
 * @class NovaBuscaComponent
 * @extends {BaseForm<Busca>}
 * @implements {AfterViewInit}
 * @implements {PermissaoRotaComponente}
 */
@Component({
    selector: 'nova.busca',
    moduleId: module.id,
    templateUrl: './nova.busca.component.html',
})

export class NovaBuscaComponent extends BaseForm<Busca> implements AfterViewInit, PermissaoRotaComponente {

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild("diagnosticoComponent")
    private diagnosticoComponent: DiagnosticoComponent;

    public rmr: number;
    private paciente: Paciente;

    /**
     * Construtor padrão
     * @author Fillipe Queiroz
     */
    constructor(private fb: FormBuilder, translate: TranslateService,
        private activatedRouter: ActivatedRoute, private router: Router,
        private pacienteService: PacienteService, private messageBox: MessageBox) {
        super();
        this.translate = translate;

        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idPaciente").then(value => {
            this.rmr = value;
        });
    }

    ngAfterViewInit(): void{
        this.headerPaciente.exibirSomenteDadosPessoais = true;
        this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmr);
    }

    /**
     * Solicitar a nova busca ao confirmar na tela.
     */
    public solicitarNovaBusca(): void{
        this.pacienteService.solicitarNovaBusca(this.rmr).then(res => {
            let modal: Modal = this.messageBox.alert(res.mensagem);
            modal.closeOption = () => {
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            };
            modal.show();

        }, (error: ErroMensagem) => {
            this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
        });
    }

    public form(): FormGroup {
        throw new Error("Method not implemented.");
    }

    public preencherFormulario(entidade: Busca): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.NovaBuscaComponent];
    }

}
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Busca } from 'app/paciente/busca/busca';
import { Diagnostico } from 'app/paciente/cadastro/diagnostico/diagnostico';
import { DiagnosticoComponent } from 'app/paciente/cadastro/diagnostico/diagnostico.component';
import { Paciente } from 'app/paciente/paciente';
import { ComponenteRecurso } from 'app/shared/enums/componente.recurso';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { PermissaoRotaComponente } from 'app/shared/permissao.rota.componente';
import { DataUtil } from 'app/shared/util/data.util';
import { RouterUtil } from 'app/shared/util/router.util';
import { BaseForm } from 'app/shared/base.form';
import { HeaderPacienteComponent } from 'app/paciente/consulta/identificacao/header.paciente.component';
import { Modal } from 'app/shared/modal/factory/modal.factory';
import { HistoricoNavegacao } from 'app/shared/historico.navegacao';
import { AvaliacaoNovaBuscaService } from '../../../../../shared/service/avaliacao.nova.busca.service';
import { ReprovarAvaliacaoModal } from './modalReprovacao/reprovar.avaliacao.modal';
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
    selector: 'avaliar.nova.busca.detalhe',
    moduleId: module.id,
    templateUrl: './avaliar.nova.busca.detalhe.component.html',
})

export class AvaliarNovaBuscaDetalheComponent extends BaseForm<Busca> implements AfterViewInit, PermissaoRotaComponente {

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild("diagnosticoComponent")
    private diagnosticoComponent: DiagnosticoComponent;

    public rmr: number;
    private idAvaliacao: number;
    private paciente: Paciente;

    /**
     * Construtor padrão
     * @author Fillipe Queiroz
     */
    constructor(private fb: FormBuilder, translate: TranslateService,
        private activatedRouter: ActivatedRoute, private router: Router,
        private avaliacaoNovaBuscaService: AvaliacaoNovaBuscaService, private messageBox: MessageBox) {
        super();
        this.translate = translate;

        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ["idPaciente", "idAvaliacao"]).then(value => {
            this.rmr = value['idPaciente'];
            this.idAvaliacao = value['idAvaliacao'];
        });
    }

    ngAfterViewInit(): void{
        this.headerPaciente.exibirSomenteDadosPessoais = true;
        this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmr);
    }

    /**
     * @description Aprova a avaliação que solicita a nova busca.
     * Ao ser aprovado, a nova busca é criada já liberada para match.
     * @author Pizão
     */
    public aprovar(): void{
        this.avaliacaoNovaBuscaService.aprovar(this.idAvaliacao).then(res => {
            let modal: Modal = this.messageBox.alert(res.mensagem);
            modal.closeOption = () => {
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            };
            modal.show();

        }, (error: ErroMensagem) => {
            this.messageBox.alert("Erro ao aprovar a avaliação do paciente: " + this.rmr).show();
        });
    }

    /**
     * @description Abre o modal solicitando a justitificativa para
     * reprovação.
     * @author Pizão
     */
    public abrirModalReprovacao(): void{
        let modal: Modal = this.messageBox.dynamic({'idAvaliacao': this.idAvaliacao}, ReprovarAvaliacaoModal);
        modal.show();
    }

    public form(): FormGroup {
        throw new Error("Method not implemented.");
    }

    public preencherFormulario(entidade: Busca): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente
            [ComponenteRecurso.Componente.AvaliarNovaBuscaDetalheComponent];
    }

}
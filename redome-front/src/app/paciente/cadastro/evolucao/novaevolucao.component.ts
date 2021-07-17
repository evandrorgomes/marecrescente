import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DataUtil } from 'app/shared/util/data.util';
import { FileItem } from 'ng2-file-upload';
import { BaseForm } from '../../../shared/base.form';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { Cid } from '../../../shared/dominio/cid';
import { CondicaoPaciente } from '../../../shared/dominio/condicaoPaciente';
import { EstagioDoenca } from '../../../shared/dominio/estagio.doenca';
import { Motivo } from '../../../shared/dominio/motivo';
import { TipoTransplante } from '../../../shared/dominio/tipoTransplante';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { TipoPendencias } from '../../../shared/enums/tipo.pendencia';
import { ErroMensagem } from '../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { PacienteUtil } from '../../../shared/paciente.util';
import { FormatterUtil } from '../../../shared/util/formatter.util';
import { AvaliacaoService } from '../../avaliacao/avaliacao.service';
import { Pendencia } from '../../avaliacao/pendencia';
import { HeaderPacienteComponent } from '../../consulta/identificacao/header.paciente.component';
import { Paciente } from '../../paciente';
import { PacienteService } from '../../paciente.service';
import { Diagnostico } from '../diagnostico/diagnostico';
import { AssociaPendenciaComponent } from '../pendencia/associa.pendencia.component';
import { ArquivoEvolucao } from './arquivo.evolucao';
import { Evolucao } from './evolucao';
import { EvolucaoComponent } from './evolucao.component';
import { EvolucaoService } from './evolucao.service';

/**
 * Componente responsável pelos dados de evolução
 * @author Fillipe Queiroz
 * @export
 * @class EvolucaoComponent
 * @extends {BaseForm}
 */
@Component({
    selector: 'novaevolucao',
    moduleId: module.id,
    templateUrl: './novaevolucao.component.html',
})
export class NovaEvolucaoComponent extends BaseForm<Evolucao> implements OnInit {
    private cid;

    rmr: number;
    mensagemSucesso: string;

    private possuiAvaliacao: boolean  = false;
    private pendencias: Pendencia[] = [];

    @ViewChild(EvolucaoComponent)
    private evolucaoComponent: EvolucaoComponent;

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild(AssociaPendenciaComponent)
    private associaPendencia: AssociaPendenciaComponent;


    /**
     * Construtor padrão
     * @author Fillipe Queiroz
     */
    constructor(private fb: FormBuilder, translate: TranslateService, private pacienteService: PacienteService,
        private activatedRouter: ActivatedRoute, private evolucaoService: EvolucaoService, private router: Router,
        private avaliacaoService: AvaliacaoService ) {
        super();
        this.translate = translate;

    }

    ngOnInit() {
        this.evolucaoComponent.enviarArquivoNaSelecao = false;
        this.activatedRouter.params.subscribe(params => {
            this.rmr = +params['idPaciente'];

            Promise.resolve(this.headerPaciente).then(() => {
                this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmr);
            });

            this.pacienteService.obterUltimaEvolucaoPorPaciente(this.rmr).then(
                result => {
                    this.evolucaoComponent.evolucaoForm.get("resultadoCMV").setValue(result["cmv"]);
                    this.evolucaoComponent.evolucaoForm.get("altura").setValue(result["altura"]);
                    this.evolucaoComponent.evolucaoForm.get("dataUltimoTransplante").setValue(result["dataUltimoTransplante"]);
                    if(result['tiposTransplante']){
                        this.evolucaoComponent.checarItensTransplantesAnteriores(result['tiposTransplante']);
                    }
                    this.evolucaoComponent.habilitarDataUltimoTransplante({currentTarget:{checked:false}});
                    this.activatedRouter.queryParamMap.subscribe((queryParam:ParamMap) => {

                         //Verifica se tem pendencias to tipo evolução da avaliação atual em aberto para o paciente
                        this.pacienteService.listarPendenciasEmAbertoPorTipo(this.rmr, TipoPendencias.EVOLUCAO ).then(lista => {
                            if (lista && lista.length != 0) {
                                lista.forEach(item => {
                                    let pendencia: Pendencia = new Pendencia();
                                    pendencia.id = item.id;
                                    pendencia.descricao = item.descricao;
                                    this.pendencias.push(pendencia);
                                });
                                this.possuiAvaliacao = true;
                                this.associaPendencia.mostrarPergunta = true;
                                let idPendencia;
                                if (queryParam.keys.length != 0) {
                                    idPendencia = queryParam.get('idPendencia');
                                    this.associaPendencia.marcarRadioAssociarPendencias();
                                    this.associaPendencia.marcarRadioRespondePendencias();
                                }

                                this.associaPendencia.montaformPendencia( this.pendencias, idPendencia );
                            }

                        }).catch(error => {

                        });


                    }, error => {
                    })

                },
                (error: ErroMensagem) => {
                }
            );

            this.pacienteService.obterIdentificacaoPacientePorRmr(this.rmr).then((result:Paciente) =>{
                this.cid = result.diagnostico.cid.id;
                this.evolucaoComponent.obterEstagios(this.cid);
            },
            (error: ErroMensagem)=>{

            });
        });

        this.evolucaoComponent.evolucaoForm.get("motivo").setValue("");
        this.evolucaoComponent.isMotivosVisiveis = true;

    }

    public form(): FormGroup{
        return null;
    }

    public fecharSucessoModal(sucessoModal: any) {
        sucessoModal.hide();
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    onSubmit(sucessoModal: any) {

        let evolucaoOk: boolean = this.evolucaoComponent.validateForm();
        let associaOk: boolean = this.possuiAvaliacao ? this.associaPendencia.validateForm(): false;
        if (evolucaoOk){

            if (this.possuiAvaliacao && associaOk && this.confirmaAssociacaoPendencia()) {
                this.evolucaoService.incluirRespondendoPendencia(this.popularEvolucao(),
                    this.popularPendencias(), this.popularResposta(), this.popularRespondePendencia(), this.evolucaoComponent.obterArquivosDeEvolucao()).then((res: string) => {
                        this.mensagemSucesso = res;
                        sucessoModal.show();
                    },
                    (error: ErroMensagem) => {
                        this.marcarCamposInvalidosRetornadosBackend(error.listaCampoMensagem);
                    });

            }
            else if (!this.possuiAvaliacao || (this.possuiAvaliacao && !this.confirmaAssociacaoPendencia())  ) {
                this.evolucaoService.incluir(this.popularEvolucao(), this.evolucaoComponent.obterArquivosDeEvolucao()).then(res => {
                    this.mensagemSucesso = res['mensagem'];
                    this.headerPaciente.removeItemPorChaveParcial();
                    sucessoModal.show();
                },
                (error: ErroMensagem) => {
                    this.marcarCamposInvalidosRetornadosBackend(error.listaCampoMensagem);
                });
            }
        }

    }
    /**
     * Marca os campos de todas as etapas com erro, identificadas de acordo
     * com a mensagem retornada do backend
     *
     * @param mensagens
     */
    private marcarCamposInvalidosRetornadosBackend(mensagens:CampoMensagem[]){
        mensagens.forEach(mensagemErro => {
            if(this.evolucaoComponent.existsField(mensagemErro.campo)){
                this.evolucaoComponent.markAsInvalid(this.evolucaoComponent.getField(this.evolucaoComponent.form(), mensagemErro.campo));
                this.evolucaoComponent.formErrors[mensagemErro.campo] = mensagemErro.mensagem;
            }
        });
    }

    popularEvolucao(): Evolucao{
        let evolucao: Evolucao = new Evolucao();

        evolucao.altura = FormatterUtil.obterAlturaSemMascara( this.evolucaoComponent.evolucaoForm.get("altura").value ) || null;
        let idMotivo = this.evolucaoComponent.evolucaoForm.get("motivo").value || null;
        if (idMotivo) {
            evolucao.motivo = new Motivo(idMotivo);
        }
        else {
            evolucao.motivo = null;
        }

        evolucao.peso = FormatterUtil.obterPesoSemMascara( this.evolucaoComponent.evolucaoForm.get("peso").value ) || null;
        evolucao.cmv = this.evolucaoComponent.evolucaoForm.get("resultadoCMV").value;
        evolucao.tratamentoAnterior =  this.evolucaoComponent.evolucaoForm.get("tratamentoAnterior").value || null;
        evolucao.tratamentoAtual = this.evolucaoComponent.evolucaoForm.get("tratamentoAtual").value || null;
        evolucao.condicaoAtual = this.evolucaoComponent.evolucaoForm.get("condicaoAtual").value || null;

        let estagioDoenca = this.evolucaoComponent.evolucaoForm.get("estagioDoenca");
        let idEstagio;

        if(estagioDoenca) {
            idEstagio = estagioDoenca.value || null
        }

        if (idEstagio) {
            evolucao.estagioDoenca = new EstagioDoenca(idEstagio);
        }
        else {
            evolucao.estagioDoenca = null
        }

        let idCondicaoPaciente = this.evolucaoComponent.evolucaoForm.get("evolucao").value;
        if (idCondicaoPaciente) {
            let condicaoPaciente: CondicaoPaciente = new CondicaoPaciente(idCondicaoPaciente);
            evolucao.condicaoPaciente = condicaoPaciente;
        }
        else {
            evolucao.condicaoPaciente = null;
        }

        if (this.evolucaoComponent.evolucaoForm.get('exameAnticorpo').value == 0) {
            evolucao.exameAnticorpo = false;
        }
        else {
            evolucao.exameAnticorpo = true;
        }
        evolucao.dataExameAnticorpo = PacienteUtil.obterDataSemMascara( this.evolucaoComponent.evolucaoForm.get('dataExameAnticorpo').value) || null ;
        evolucao.resultadoExameAnticorpo = this.evolucaoComponent.evolucaoForm.get('resultadoExameAnticorpo').value;

        if (!evolucao.altura && !evolucao.motivo && !evolucao.peso && !evolucao.cmv
            && !evolucao.tratamentoAnterior && !evolucao.tratamentoAtual && !evolucao.condicaoAtual
            && !evolucao.estagioDoenca) {
            evolucao = null;
        }
        let paciente:Paciente = new Paciente();
        paciente.rmr = this.rmr;

        let diagnostico:Diagnostico = new Diagnostico();
        let cid:Cid = new Cid(this.cid);
        diagnostico.cid = cid;
        paciente.diagnostico = diagnostico;
        evolucao.paciente = paciente;

        let arquivosEvolucao:Map<string, FileItem> =   this.evolucaoComponent.obterArquivosDeEvolucao();
        if(arquivosEvolucao && arquivosEvolucao.size > 0){
            evolucao.arquivosEvolucao = [];
            arquivosEvolucao.forEach((item: FileItem, key: string) => {
                let arquivo:ArquivoEvolucao =  new ArquivoEvolucao();
                arquivo.caminhoArquivo = item.file.name;
                evolucao.arquivosEvolucao.push(arquivo);
            });
        }

        this.evolucaoComponent.transplantesAnterioresFormArray.controls.forEach(c => {
            if(c.get('check').value){
                let tipoTransplante = new TipoTransplante();
                tipoTransplante.id = c.get('id').value;
                evolucao.tiposTransplante.push(tipoTransplante);
            }
        });
        if(evolucao.tiposTransplante.length > 0){
            evolucao.dataUltimoTransplante = DataUtil.dataStringToDate(this.evolucaoComponent.evolucaoForm.get("dataUltimoTransplante").value);
        }

        return evolucao;
    }

    private popularPendencias(): Pendencia[] {
        return this.associaPendencia.listarPendenciasSelecionadas();
    }

    private popularResposta(): string {
        return this.associaPendencia.obterResposta();
    }

    private popularRespondePendencia(): boolean {
        return this.associaPendencia.obterRespondePendencia();
    }

    // Override
    public preencherFormulario(evolucao:Evolucao): void {}

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.NovaEvolucaoComponent];
    }

    private confirmaAssociacaoPendencia(): boolean {
        return this.associaPendencia.obterAssociaPendencias();
    }

    public botaoCancelar() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }


}

import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ExamePaciente } from './exame.paciente';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { TipoPendencias } from '../../../shared/enums/tipo.pendencia';
import { PedidoExame } from '../../../laboratorio/pedido.exame';
import { PedidoExameService } from '../../../laboratorio/pedido.exame.service';
import { BaseForm } from '../../../shared/base.form';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { ErroMensagem } from '../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { Pendencia } from '../../avaliacao/pendencia';
import { HeaderPacienteComponent } from '../../consulta/identificacao/header.paciente.component';
import { Paciente } from '../../paciente';
import { PacienteService } from '../../paciente.service';
import { AssociaPendenciaComponent } from '../pendencia/associa.pendencia.component';
import { ExameComponent } from './exame.component';
import { ExameService } from './exame.service';
import { MessageBox } from '../../../shared/modal/message.box';

/**
 * Component responsavel exames
 * @author Filipe Paes
 * @export
 * @class ExameComponent
 * @extends {BaseForm}
 * @implements {OnDestroy}
 */
@Component({
    selector: 'novoexame',
    moduleId: module.id,
    templateUrl: './novoexame.component.html',
})
export class NovoExameComponent extends BaseForm<ExamePaciente> implements OnInit {
    private modoPedidoExame: boolean = false;

    public pedidoExame:PedidoExame;

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild(ExameComponent)
    private exameComponente: ExameComponent;

    @ViewChild(AssociaPendenciaComponent)
    private associaPendencia: AssociaPendenciaComponent;
    
    private cid;

    mensagemSucesso: string;

    private sucessoNovoExame: string = "";

    private _idExameInserido:number;

    rmr: number;

    private possuiAvaliacao: boolean  = false;
    private pendencias: Pendencia[] = [];

    /**
     * Construtor padrão
     * @author Fillipe Queiroz
     */
    constructor(private fb: FormBuilder, translate: TranslateService, private pacienteService: PacienteService,
        private activatedRouter: ActivatedRoute, private router: Router, private exameService: ExameService, 
        private pedidoExameService:PedidoExameService, private messageBox: MessageBox) {
        super();
        this.translate = translate;
    }


    /**
     * método executado ao inicializar o componente de exame, que obtém o cabeçalho
     * do paciente pelo rmr informado.
     * 
     * @memberof NovoExameComponent
     */
    ngOnInit() {

        Promise.resolve(this.headerPaciente).then(() => {
            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmr);
        });

        this.exameComponente.iniciarPorTelaCadastro = false;

        this.activatedRouter.params.subscribe(params => {
            this.rmr = +params['idPaciente']; 
            

            this.pacienteService.obterIdentificacaoPacientePorRmr(this.rmr).then((result:Paciente) =>{                
                this.cid = result.diagnostico.cid.id;
            },
            (error: ErroMensagem)=>{
                this.exibirMensagemErro(error);
            });

            this.activatedRouter.queryParamMap.subscribe(queryParam => {
                if(queryParam.get('idPedidoExame')){
                    this.pedidoExame = new PedidoExame();
                    this.pedidoExame.id = Number.parseInt(queryParam.get('idPedidoExame'));
                    this.exameComponente.idPedidoExame = this.pedidoExame.id;
                }else{
                    //Verifica se tem pendencias to tipo evolução da avaliação atual em aberto para o paciente
                    this.pacienteService.listarPendenciasEmAbertoPorTipo(this.rmr, TipoPendencias.EXAME ).then(lista => {
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
    
                    }, (erro: ErroMensagem)=> {
                        this.exibirMensagemErro(erro);
                    });
                }

            },
            (erro: ErroMensagem)=> {
                this.exibirMensagemErro(erro);
            })
        });

        this.translate.get("mensagem.sucesso.novoExame").subscribe(res => {
            this.sucessoNovoExame = res;
        });
    }

    /**
     * Metodo executado ao submeter o formulario de novo exame
     * 
     * @param {*} sucessoModal 
     * @memberof NovoExameComponent
     */
    onSubmit(sucessoModal: any) {

        let exameOk: boolean = this.exameComponente.validateForm();
        let associaOk: boolean = this.possuiAvaliacao ? this.associaPendencia.validateForm(): false;
        

        if(exameOk){
            let data: FormData = this.recuperarArquivosLaudoUploaded();
            let exame: ExamePaciente = this.exameComponente.exame;
            exame.paciente = new Paciente();
            exame.paciente.rmr = this.rmr;
            
            if (this.possuiAvaliacao && associaOk && this.confirmaAssociacaoPendencia()) {
                this.exameService.salvarRespondendoPendencia(data, exame, 
                    this.popularPendencias(), this.popularResposta(), this.popularRespondePendencia()).then((res: string) => {
                        this.mensagemSucesso = res;
                        sucessoModal.show();
                    },
                    (error: ErroMensagem) => {
                        this.marcarCamposInvalidosRetornadosBackend(error.listaCampoMensagem);
                    });            
            }
            else if(this.pedidoExame && this.pedidoExame.id){
                this.pedidoExame = this.exameComponente.pedidoExame;
                this.pedidoExame.exame = exame;
                this.pedidoExameService.enviarResultado(data, this.pedidoExame).then(res => { 
                    this._idExameInserido = res[1].mensagem;
                    this.mensagemSucesso = this.sucessoNovoExame;
                    sucessoModal.show();
                }, 
                (error: ErroMensagem) => {
                    this.marcarCamposInvalidosRetornadosBackend(error.listaCampoMensagem);
                });    

            }else if (!this.possuiAvaliacao || (this.possuiAvaliacao && !this.confirmaAssociacaoPendencia())  ) {
                this.exameService.salvar(data, exame).then(res => {
                    this._idExameInserido = res[1].mensagem;
                    this.mensagemSucesso = this.sucessoNovoExame;
                    sucessoModal.show();
                },
                (error: ErroMensagem) => {
                    this.marcarCamposInvalidosRetornadosBackend(error.listaCampoMensagem);
                });    
            }
        }
        
    }
    private exibirMensagemErro(error: ErroMensagem) {
        let mensagem: string = "";
        error.listaCampoMensagem.forEach(obj => {
            mensagem += obj.mensagem + "\n\r";
        })
        this.messageBox.alert(mensagem).show();       
    }
    /**
     * Marca os campos de todas as etapas com erro, identificadas de acordo 
     * com a mensagem retornada do backend
     * 
     * @param mensagens 
     */
    private marcarCamposInvalidosRetornadosBackend(mensagens:CampoMensagem[]){
        mensagens.forEach(mensagemErro => {
            if(this.exameComponente.existsField(mensagemErro.campo)){
                this.exameComponente.markAsInvalid(this.exameComponente.getField(this.exameComponente.form(), mensagemErro.campo));
                this.exameComponente.formErrors[mensagemErro.campo] = mensagemErro.mensagem;
            }
        });
    }

    private recuperarArquivosLaudoUploaded():FormData{
        let data: FormData = new FormData();
        this.exameComponente.uploader.queue.forEach(item => {
            data.append("file", item._file, item.file.name);
        });
        return data;
    }


    public fecharSucessoModal(sucessoModal: any) {
        sucessoModal.hide();
        if(this.pedidoExame){
            this.router.navigateByUrl('/laboratorios');    
        }else{
            this.router.navigateByUrl('/pacientes/' + this.rmr + '/exames?idExame=' + this._idExameInserido);
        }
        HistoricoNavegacao.urlRetorno();
    }

    public form(): FormGroup{
        return null;
    }

    public preencherFormulario(exame:ExamePaciente): void{}

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.NovoExameComponent];
    }

    private confirmaAssociacaoPendencia(): boolean {
        return this.associaPendencia.obterAssociaPendencias();
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

    public botaoCancelar() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

}
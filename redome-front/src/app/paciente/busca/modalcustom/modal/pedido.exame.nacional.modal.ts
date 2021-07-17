import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { TipoExame } from 'app/laboratorio/tipo.exame';
import { BuildForm } from 'app/shared/buildform/build.form';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import { Laboratorio } from 'app/shared/dominio/laboratorio';
import { LaboratorioService } from 'app/shared/service/laboratorio.service';
import { TipoExameService } from 'app/shared/service/tipo.exame.service';
import { SolicitacaoService } from '../../../../doador/solicitacao/solicitacao.service';
import { CampoMensagem } from '../../../../shared/campo.mensagem';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { IModalComponent } from '../../../../shared/modal/factory/i.modal.component';
import { IModalContent } from '../../../../shared/modal/factory/i.modal.content';
import { Modal } from '../../../../shared/modal/factory/modal.factory';
import { MessageBox } from '../../../../shared/modal/message.box';
import { ErroUtil } from '../../../../shared/util/erro.util';
import {DoadorService} from "../../../../doador/doador.service";
import {EnderecoContatoDoador} from "../../../../doador/endereco.contato.doador";

@Component({
    selector: 'pedido-exame-nacional-modal',
    templateUrl: './pedido.exame.nacional.modal.html'
})

export class PedidoExameNacionalModal implements OnInit, IModalContent {
    data: any;
    target: IModalComponent;
    close: (target: IModalComponent) => void;

    private _tiposExame: TipoExame[];
    private _laboratrios: Laboratorio[];
    private _fase2Form: BuildForm<any>;
    private _fase3Form: BuildForm<any>;

    private _municipio: string = "";
    private _uf: string = "";


    constructor(private messageBox: MessageBox, private tipoExameService: TipoExameService,
        private solicitacaoService: SolicitacaoService, private laboratorioService: LaboratorioService,
        private doadorService: DoadorService) {

            this._fase2Form = new BuildForm<any>()
                .add(new NumberControl('tipoExame', [ Validators.required ] ));

            this._fase3Form = new BuildForm<any>()
                .add(new NumberControl('laboratorio', [ Validators.required ] ));

    }

    ngOnInit(): void {
        this.target.target['match'] = this.data['match'];
        if(this.data.tipoSolicitacao === "F2"){
            this.tipoExameService.listarTiposExameFase2Nacional().then(res =>{
                if (res) {
                    this._tiposExame = [];
                    res.forEach(tipo => {
                        this._tiposExame.push(new TipoExame().jsonToEntity(tipo));
                    });
                }
             },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error,this.messageBox);
            })
        }
        else if(this.data.tipoSolicitacao === "F3") {
           this.doadorService.listarEnderecoDoador(this.data.match.idDoador).then( (res: EnderecoContatoDoador[]) => {
               let endereco: EnderecoContatoDoador;
                if (res && res.length !== 0) {
                    endereco = res.find(enderecoContato => enderecoContato.principal );
                    if (!endereco) {
                        endereco = res.find(enderecoContato => enderecoContato.correspondencia );
                        if (!endereco) {
                            endereco = res[0];
                        }
                    }
                }
                if (endereco && endereco.municipio && endereco.municipio.uf) {
                    this._municipio = endereco.municipio.descricao;
                    this._uf = endereco.municipio.uf.sigla;
                }
           },
           (error: ErroMensagem) => {
               ErroUtil.exibirMensagemErro(error,this.messageBox);
           });
            this.laboratorioService.listarLaboratoriosCTExame().then(res => {
                if (res) {
                    this._laboratrios = [];
                    res.forEach(laboratorio => {
                        this._laboratrios.push(new Laboratorio().jsonToEntity(laboratorio));
                    });
                }
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error,this.messageBox);
            });
        }
    }
    salvar() {
        if (this.data.tipoSolicitacao == 'F2') {
            if (this._fase2Form.valid) {
                this.target.hide();
                const idTipoExame: number = this._fase2Form.value['tipoExame'];
                this.solicitacaoService.solicitarFase2Nacional(this.data.match.id, idTipoExame)
                    .then((retorno: CampoMensagem) => {
                        let alert: Modal = this.messageBox.alert(retorno.mensagem.toString());
                        alert.target = this.target.target;
                        alert.closeOption = this.data.closeOption;
                        alert.show();
                    }, (error: ErroMensagem) =>{
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
            }
        }
        else if (this.data.tipoSolicitacao === 'F3') {
            if (this._fase3Form.valid) {
                this.target.hide();
                const idLaboratorio: number = this._fase3Form.value['laboratorio'];
                this.solicitacaoService.solicitarFase3Nacional(this.data.match.id, idLaboratorio,
                    this.data['resolverDivergencia'] ? true : false ).then((retorno: CampoMensagem) => {
                        const alert: Modal = this.messageBox.alert(retorno.mensagem.toString());
                        alert.target = this.target.target;
                        alert.closeOption = this.data.closeOption;
                        alert.show();
                    }, (error: ErroMensagem) =>{
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
            }
        }
    }

    public descricoes(): string[] {
        const descricoes: string[] = [];
        if (this._tiposExame) {
            this._tiposExame.forEach(tipoExame => {
                descricoes.push(tipoExame.descricao);
            });
        }
        return descricoes;
    }

    public valores(): number[] {
        const valores: number[] = [];
        if (this._tiposExame) {
            this._tiposExame.forEach(tipoExame => {
                valores.push(tipoExame.id);
            });
        }
        return valores;
    }

    public fase2Form(): FormGroup {
        return this._fase2Form.form;
    }

    public fase3Form(): FormGroup {
        return this._fase3Form.form;
    }

    public laboratorios(): Laboratorio[] {
        return this._laboratrios;
    }

    public get municipio(): string {
        return this._municipio;
    }

    public get uf(): string {
        return this._uf;
    }


}

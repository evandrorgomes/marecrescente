import { Component } from "@angular/core";
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';
import { FormBuilder } from '@angular/forms';
import { TranslateService } from "@ngx-translate/core";
import { PedidoExameService } from '../../../../laboratorio/pedido.exame.service';
import { EnderecoContato } from '../../../../shared/classes/endereco.contato';
import { Laboratorio } from '../../../../shared/dominio/laboratorio';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { IModalComponent } from '../../../../shared/modal/factory/i.modal.component';
import { IModalContent } from '../../../../shared/modal/factory/i.modal.content';
import { MessageBox } from '../../../../shared/modal/message.box';
import { LaboratorioService } from '../../../../shared/service/laboratorio.service';
import { SolicitacaoService } from './../../../../doador/solicitacao/solicitacao.service';


/**
 * Classe que representa o componente de analise de match
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-pedido-exame-fase3-paciente',
    templateUrl: './modal.pedido.exame.fase3.component.html'
})
export class ModalPedidoExameFase3Component implements IModalContent, OnInit {
    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;
    dataResult:any;
    private _laboratorios: Laboratorio[] = [];
    public idLaboratorioSelecionado: string;
    public mensagemErroLaboratorio: string;

    private _mensagemObrigatorio: string;
    private _mensagemMesmoLaboratorio: string;

    constructor(private translate: TranslateService, private fb: FormBuilder, private messageBox: MessageBox,
        private laboratorioService: LaboratorioService, private pedidoExameService: PedidoExameService,
        private solicitacaoService: SolicitacaoService) {

    }

    public ngOnInit() {

        this.translate.get("modalpedidoexamefase3.laboratorio" ).subscribe(label => {
            this.translate.get("mensagem.erro.obrigatorio", {"campo": label}).subscribe(res => {
                this._mensagemObrigatorio = res;
            });    
        });

        this.translate.get("modalpedidoexamefase3.mensagemmesmolaboratorio" ).subscribe(res => {
            this._mensagemMesmoLaboratorio = res;
        });

        
        this.laboratorioService.listarLaboratoriosCTExame().then(res => {
            this._laboratorios = [];
            if (res) {
                res.forEach(item => {
                    let laboratorio: Laboratorio = new Laboratorio();
                    laboratorio.id = new Number(item.id).valueOf();
                    laboratorio.nome = item.nome;
                    laboratorio.quantidadeExamesCT = new Number(item.quantidadeExamesCT).valueOf();
                    laboratorio.quantidadeAtual = new Number(item.quantidadeAtual).valueOf();
                    if (item.endereco) {
                        let endereco: EnderecoContato = new EnderecoContato();
                        endereco.municipio = item.endereco.municipio;
                        laboratorio.endereco = endereco;
                    }
                    this._laboratorios.push(laboratorio);
                })
                if (this.data && this.data.laboratorioDePrefencia && this.data.laboratorioDePrefencia.id && !this.data.alteracaoLaboratorio) {
                    this.idLaboratorioSelecionado = this.data.laboratorioDePrefencia.id;
                }
            }
        }, (erro: ErroMensagem) => {
            this.exibirMensagemErro(erro);
        });
    }

    private exibirMensagemErro(erro: ErroMensagem) {
        let modalErro = this.messageBox.alert("erro").showButtonOk(false);
        modalErro.target = this;
        modalErro.closeOption = this.fechar;
        modalErro.show();
    }

    public fechar(target: any) {    
        target.close(target.target);
    }

    public get laboratorios(): Laboratorio[] {
        return this._laboratorios;
    }

    public get municipioEnderecoPaciente(): string {
        return this.data ? this.data.municipioEnderecoPaciente : "";
    }

    private validarForm(): boolean {
        this.mensagemErroLaboratorio = null;
        let valid: boolean  = this.idLaboratorioSelecionado != null && this.idLaboratorioSelecionado != "";
        if (!valid) {
            this.mensagemErroLaboratorio = this._mensagemObrigatorio;
        }
        else if (this.data.alteracaoLaboratorio) {
            if (this.idLaboratorioSelecionado == this.data.laboratorioAtual.id) {
                valid = false;
                this.mensagemErroLaboratorio = this._mensagemMesmoLaboratorio;
            }            
        }
        return valid;
    }

    public confirmar() {
        if (this.validarForm()) {
            this.target.hide();
            if (!this.data.alteracaoLaboratorio) {
                this.solicitacaoService.solicitarFase3Paciente(this.data.idBusca, new Number(this.idLaboratorioSelecionado).valueOf(),
                        this.data.idTipoExame
                    ).then(res => {
                    this.messageBox.alert(res.mensagem).show();
                    this.target.target.pediuExame = true;
                    this.close(this.target);
                }, (error: ErroMensagem) => {
                    this.messageBox.alert(error.mensagem.toString()).show();
                });
            }
            else {                
                this.pedidoExameService.alterLaboratorioPedidoExame(this.data.idPedido, new Number(this.idLaboratorioSelecionado).valueOf()).then(res => {
                    this.messageBox.alert(res.mensagem).show();
                    this.target.target.pediuExame = true;
                    this.close(this.target);
                }, (error: ErroMensagem) => {
                    this.messageBox.alert(error.mensagem.toString()).show();
                });
            }
        }
    }

    obterDescricaoLaboratorio(lab: Laboratorio): string{
        let descricaoLab: string = lab.nome + " " + lab.quantidadeAtual + "/" + lab.quantidadeExamesCT;
        if(lab.endereco){
            let endereco: EnderecoContato = lab.endereco;
            return endereco.municipio.descricao + " - " + endereco.municipio.uf.sigla + " - " + descricaoLab;
        }
        return descricaoLab;
    }

}
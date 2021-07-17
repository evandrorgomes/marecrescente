import { Component, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { DoadorNacional } from '../../doador.nacional';
import { DoadorService } from '../../doador.service';
import { EnderecoContatoDoador } from '../../endereco.contato.doador';
import { HemoEntidade } from '../hemoentidade';
import { SolicitacaoService } from '../solicitacao.service';
import { EvolucaoDoador } from 'app/shared/classes/evolucao.doador';
import { forEach } from '@angular/router/src/utils/collection';
import { ErroMensagem } from '../../../shared/erromensagem';
import { MessageBox } from '../../../shared/modal/message.box';
import { ErroUtil } from '../../../shared/util/erro.util';
import { ModalEvolucaoDoadorComponent } from './modal.evolucao.doador.component';
import { EvolucaoDoadorService } from 'app/shared/service/evolucao.doador.service';
import { EvolucaoDoadorVo } from 'app/shared/vo/evolucao.doador.vo';

/**
 * Classe Component para listar as evolucoes do doador na tela de contato.
 * 
 * @author brunosousa.
 */
@Component({
    selector: 'listar-evolucao-doador',
    templateUrl: './listar.evolucao.doador.component.html'
})
export class ListarEvolucaoDoadorComponent implements OnInit {

    private _idDoador: number;
    public _evolucoes: EvolucaoDoador[];
    public _semEvolucao: string;
    public _evolucao: EvolucaoDoador;
        
    constructor(private doadorService: DoadorService, protected translate: TranslateService,
        private messageBox: MessageBox, private evolucaoDoadorService: EvolucaoDoadorService){

    }

    ngOnInit(): void {
        this.translate.get('contato.semevolucao').subscribe(res => {
            this._semEvolucao = res;
        })
    }

    prreencherListaEvolucoes(idDoador: number) {
        this._idDoador = idDoador;
        this.listarEvolucoes();
    }   
    
    private listarEvolucoes(): void {
        this.doadorService.listarEvolucoesOrdernadasPorDataCriacaoDecrescente(this._idDoador).then(res =>{
            this._evolucoes = [];
            this._evolucao = null;
            if (res && res.length != 0) {
                res.forEach(evolucao => {
                    this._evolucoes.push(new EvolucaoDoador().jsonToEntity(evolucao));
                });
                this._evolucao = this._evolucoes[0];
            }
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public mudarEvolucao(idEvolucaoDoador: number) {
        this._evolucao = this._evolucoes.find(evolucaoDoador => evolucaoDoador.id == idEvolucaoDoador);
    }

    public abrirModalEvolucaoDoador(): void {
        const data = {			
			finalizar: (evolucaoDoadorVo: EvolucaoDoadorVo) => {

                evolucaoDoadorVo.idDoador = this._idDoador;
                this.evolucaoDoadorService.criarNovaEvolucaoDoador(evolucaoDoadorVo).then(res => {
                    this.listarEvolucoes();
                },
                (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
			}
		};
		const modalDinamica = this.messageBox.dynamic(data, ModalEvolucaoDoadorComponent);
		modalDinamica.target = this;

		modalDinamica.show();
    }

}

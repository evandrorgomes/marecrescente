import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DoadorEmailContatoComponent } from '../contato/email/doador.contato.email.component';
import { DoadorContatoEnderecoComponent } from '../contato/endereco/doador.contato.endereco.component';
import { DoadorContatoTelefoneComponent } from '../contato/telefone/doador.contato.telefone.component';
import { DoadorNacional } from '../../doador.nacional';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { DoadorService } from '../../doador.service';
import { DoadorDadosPessoaisComponent } from '../contato/dadospessoais/doador.dadospessoais.component';
import { DoadorIdentificacaoComponent } from '../identificacao/doador.identificacao.component';
import { HeaderDoadorComponent } from '../../consulta/header/header.doador.component';
import { ContatoTelefonicoDoador } from '../../contato.telefonico.doador';
import { EmailContatoDoador } from '../../email.contato.doador';
import { EnderecoContatoDoador } from '../../endereco.contato.doador';
import { ConvertUtil } from 'app/shared/util/convert.util';

/**
 * Classe que representa o componente de inclusão de disponibilidades pelo analista workup.
 * @author Fillipe Queiroz
 */
@Component({
    selector: "visualizar-doador",
    templateUrl: './visualizar.doador.component.html',
    styleUrls: ['./../../doador.css']
})
export class VisualizarDoadorComponent implements OnInit {

    private _idDoador:number;

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;
    
    @ViewChild('doadorIdentificacaoComponent')
    private doadoridentificacao: DoadorIdentificacaoComponent;

    @ViewChild('doadorContatoEnderecoComponent')
    private doadorContatoEnderecoComponent:DoadorContatoEnderecoComponent;

    @ViewChild('doadorEmailContatoComponent')
    private doadorEmailContatoComponent: DoadorEmailContatoComponent;

    @ViewChild('doadorContatoTelefoneComponent')
    doadorContatoTelefoneComponent:DoadorContatoTelefoneComponent;

    @ViewChild('doadorDadosPessoaisComponent')
    private doadorDadosPessoaisComponent:DoadorDadosPessoaisComponent;
    
    private doador:DoadorNacional;

    constructor(private router: Router, 
        translate: TranslateService, private doadorService: DoadorService,
        private activatedRouter: ActivatedRoute, 
        private dominioService: DominioService, 
        private fb: FormBuilder) {
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     * 
     * @memberof PacienteBuscaComponent
     */
    ngOnInit(): void {

        this.headerDoador.clearCache();


        this.activatedRouter.queryParamMap.subscribe(queryParam => {
            if (queryParam.keys.length != 0) {
                this._idDoador = Number(queryParam.get('idDoador'));
            }
            Promise.resolve(this.headerDoador).then(() => {
                this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this._idDoador);
            });

        });

        this.doadorService.obterDoadorPorId(this._idDoador).then(res => {
            this.doador = ConvertUtil.parseJsonDoadorParaDoador(res.doador);
            Promise.resolve(this.headerDoador).then(() => {
                this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this.doador.id);
            });
            this.doadoridentificacao.preencherFormulario(this.doador);
            this.doadorContatoEnderecoComponent.preencherDados(this._idDoador, this.doador.enderecosContato);
            this.doadorEmailContatoComponent.preencherDados(this.doador.id, this.doador.emailsContato);
            this.doadorContatoTelefoneComponent.preencherDados(this.doador.id, this.doador.contatosTelefonicos);
            this.doadorDadosPessoaisComponent.preencherFormulario(this.doador);
        });


    }
    public voltarConsulta() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }
    /**
     * Nome do componente atual
     * 
     * @returns {string} 
     * @memberof ConferenciaComponent
     */
    nomeComponente(): string {
        return "VisualizarDoadorComponent";
    }



};
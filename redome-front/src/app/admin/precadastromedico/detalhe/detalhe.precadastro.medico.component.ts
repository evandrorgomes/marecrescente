import { HistoricoNavegacao } from './../../../shared/historico.navegacao';
import { FormatterUtil } from './../../../shared/util/formatter.util';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PreCadastroMedico } from 'app/shared/classes/precadastro.medico';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { MedicoService } from 'app/shared/service/medico.service';
import { ErroUtil } from 'app/shared/util/erro.util';
import { ComponenteRecurso } from "../../../shared/enums/componente.recurso";
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { RouterUtil } from '../../../shared/util/router.util';
import { ModalReprovarPreCadastroMedicoComponent } from './reprovar/modal.reprovar.precadastro.medico.component';
import { Modal } from 'app/shared/modal/factory/modal.factory';
/**
 * Classe que representa o componente de detalhe do paciente
 * @author Bruno Sousa
 */
@Component({
    selector: 'datelhe-precadastro-medico',
    templateUrl: './detalhe.precadastro.medico.component.html'
})
export class DetalhePreCadastroMedicoComponent implements PermissaoRotaComponente, OnInit {

    private _id: number;
    private _preCadastroMedico: PreCadastroMedico;
    private _TIPOS_SOMENTE_DOWNLOAD: string[] = [".tif", ".tiff"];
    
    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private router: Router, private activatedRouter: ActivatedRoute,
        private medicoService: MedicoService, private messageBox: MessageBox) {
    }

    /**
     * 
     * @memberOf DetalheComponent
     */
    ngOnInit(): void {

        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idPreCadastroMedico").then(res => {
            this._id = <number>res;
            this.obterPreCadastroMedico(); 
            
        })
    }

    private obterPreCadastroMedico() {
        this.medicoService.obterPreCadastroPorId(this._id).then(preCadastroMedico => {
            this._preCadastroMedico = new PreCadastroMedico().jsonToEntity(preCadastroMedico);
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.DetalhePreCadastroMedicoComponent];
    }

    public get preCadastroMedico(): PreCadastroMedico {
        return this._preCadastroMedico;
    }

    public obterNomeArquivo(value: string): string {
        if (value) {
            return FormatterUtil.obterNomeArquivoSelecionado(value).toString();
        }
        return "";
    }

    /**
     * Verifica se o arquivo poderá ser visualizado ou somente baixado.
     * 
     * @param nomeArquivo 
     */
    public verificarExtensaoPermiteVisualizar(nomeArquivo: string): boolean {
        let extensao: string = nomeArquivo.substring(nomeArquivo.lastIndexOf("."));
        let tipoEncontrado: string[] =
            this._TIPOS_SOMENTE_DOWNLOAD
                .filter(ext => ext === extensao);

        return (tipoEncontrado.length > 0);
    }

    /**
     * Faz a chamada para o download do arquivo de CRM associado
     * ao pré cadastro. 
     */
    public baixarArquivo() {
        this.medicoService.baixarArquivoCRM(
            this._id, this.obterNomeArquivo(this._preCadastroMedico.arquivoCrm));
    }

    public reprovarPreCadastro() {
        let data: any = {
            idPreCadastroMedico: this._preCadastroMedico.id,
            fecharModalSucesso: () => {
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());                
            }
        }

        this.messageBox.dynamic(data, ModalReprovarPreCadastroMedicoComponent)
            .show();
    }

    public aprovarPreCadastro(): void{
        this.medicoService.aprovarPreCadastro(this._id).then(res => {
            let modal: Modal = this.messageBox.alert(res.mensagem);
            modal.closeOption = () =>{
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            };
            modal.show();
        }, (error: ErroMensagem) => {
            this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
        });
    }

}
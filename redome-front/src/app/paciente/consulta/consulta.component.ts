import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AutenticacaoService } from '../../shared/autenticacao/autenticacao.service';
import { SelectCentrosComponent } from '../../shared/component/selectcentros/select.centros.component';
import { UsuarioLogado } from '../../shared/dominio/usuario.logado';
import { ComponenteRecurso } from '../../shared/enums/componente.recurso';
import { ErroMensagem } from '../../shared/erromensagem';
import { MessageBox } from '../../shared/modal/message.box';
import { PacienteUtil } from '../../shared/paciente.util';
import { Paginacao } from '../../shared/paginacao';
import { PermissaoRotaComponente } from '../../shared/permissao.rota.componente';
import { PacienteService } from '../paciente.service';
import { ErroUtil } from './../../shared/util/erro.util';
/**
 * Classe que representa o componente de consulta do paciente
 * @author Bruno Sousa
 */
@Component({
    selector: 'consulta',
    templateUrl: './consulta.component.html'
})
export class ConsultaComponent implements PermissaoRotaComponente, OnInit {

     @ViewChild('selectCenrtos') selectCentros: SelectCentrosComponent;

    protected usuario: UsuarioLogado = null;

    consultaForm: FormGroup;

    paginacao: Paginacao;

    private dadosDaBusca= {
        'rmr': '',
        'nome': '',
        'idCentroAvaliador': null,
        'idTipoFuncao': null,
        'quantidadeRegistro': 10,
        'pagina': 1
    }

    quantidadeRegistro: number = 10;

    public _centroSelecionado: any = null;

    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private _fb: FormBuilder,
        private servicePaciente: PacienteService, private router: Router,
        private translate: TranslateService, private autenticacao: AutenticacaoService,
        private messageBox: MessageBox) {

        this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);
        this.paginacao.number = 1;
    }

    /**
     *
     * @memberOf ConsultaComponent
     */
    ngOnInit(): void {
        this.consultaForm = this._fb.group({
            'rmr': [null, null],
            'nome': [null, null]
        });

    }

     ngAfterViewInit() {
         setTimeout(() => {
             this.carregaBuscaAnterior();
         });
     }

    /**
     * Faz o carregamento da busca anterior caso exista, e exclui a mesma
     *
     *
     * @memberOf ConsultaComponent
     */
    private carregaBuscaAnterior() {
        let buscaPaciente = localStorage.getItem(this.autenticacao.usuarioLogado().username + "buscaPaciente");
        if (buscaPaciente) {
            let busca = JSON.parse(buscaPaciente);
            this.dadosDaBusca.nome = busca.nome;
            this.dadosDaBusca.rmr = busca.rmr;
            this.dadosDaBusca.idCentroAvaliador = parseInt(busca.idCentroAvaliador);
            this.dadosDaBusca.idTipoFuncao = parseInt(busca.idTipoFuncao);
            this.dadosDaBusca.pagina = parseInt(busca.pagina);
            this.dadosDaBusca.quantidadeRegistro = parseInt(busca.quantidadeRegistro);
            this.quantidadeRegistro = this.dadosDaBusca.quantidadeRegistro;
            this.excluiBuscaAnterior();
            this.resetaOformularioParaOsDadosAnteriores();
            this.buscarPaciente(this.dadosDaBusca['pagina'], true);
        }
    }

    /**
     * Exclui a busca anterior
     *
     * @private
     *
     * @memberOf ConsultaComponent
     */
    private excluiBuscaAnterior() {
        localStorage.removeItem(this.autenticacao.usuarioLogado().username + "buscaPaciente");
    }

    /**
     * Método acionado pelo botão de consulta
     * @author Bruno Sousa
     */
    onSubmit(pagina: number) {
        this.dadosDaBusca['rmr'] = this.consultaForm.get('rmr').value;
        this.dadosDaBusca['nome'] = this.consultaForm.get('nome').value;
        this.dadosDaBusca['idCentroAvaliador'] = this._centroSelecionado.id;
        this.dadosDaBusca['idTipoFuncao'] = this._centroSelecionado.tipo;
        this.paginacao.content = [];
        this.paginacao.totalElements = 0;
        return this.buscarPaciente(pagina);
    }

    /**
     *  Método que envia os dados para a consulta, quando é retornado apenas um registro
     * é verificado se o paciente é do médico logado, caso seja é redirecionado automaticamente
     * para a página de detalhe
     *
     * @private
     * @param {number} pagina
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    private buscarPaciente(pagina: number, buscaAnterior?: boolean) {
        /* if (!buscaAnterior) {
             buscaAnterior = false;
         }*/
        if (this.paginacao.number == pagina && this.paginacao.content.length != 0 && this.dadosDaBusca['quantidadeRegistro'] == this.paginacao.quantidadeRegistro) {
            return;
        }
        this.dadosDaBusca['pagina'] = pagina;

        let meusPacientes = false;

        if (this.dadosDaBusca['idCentroAvaliador'] == -99) {
            meusPacientes = true;
        }

        this.servicePaciente.listarPacientePorRmrOuNome(
            (this.dadosDaBusca['rmr'] && this.dadosDaBusca['rmr'] != '' ? Number(this.dadosDaBusca['rmr']) : null) ,
            this.dadosDaBusca['nome'], meusPacientes, this.dadosDaBusca['idTipoFuncao'], (this.dadosDaBusca['idCentroAvaliador'] != -99 ? this.dadosDaBusca['idCentroAvaliador'] : null),
            pagina - 1, this.dadosDaBusca['quantidadeRegistro'])
            .then((res: any) => {
                this.paginacao.content = res.content;
                this.paginacao.totalElements = res.totalElements;
                this.paginacao.quantidadeRegistro = this.dadosDaBusca['quantidadeRegistro'];
                this.paginacao.number = pagina;

                /*if (this.paginacao.content.length == 1 && !buscaAnterior) {
                   //let paciente: any = this.paginacao.content[0] ;
                    if (this.deveExibirIconeDetalhePaciente(this.paginacao.content[0]) ){
                        this.salvaBusca();
                        this.router.navigateByUrl("/pacientes/" + this.paginacao.content[0].rmr);
                    }
                }*/
            })
            .catch((erro: ErroMensagem)  => {
                ErroUtil.exibirMensagemErro(erro, this.messageBox);
            });

    }

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    mudarPagina(event: any) {
        this.resetaOformularioParaOsDadosAnteriores();
        this.buscarPaciente(event.page);
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    selecinaQuantidadePorPagina(event: any, modal: any) {
        this.dadosDaBusca['quantidadeRegistro'] = this.quantidadeRegistro;
        this.resetaOformularioParaOsDadosAnteriores();
        this.buscarPaciente(1);
    }

    /**
     * Método para restaurar os valores pesquisados quando é trocado a página ou a quantidade de registros
     * pois o usuário pode ter alterado os termos da busca
     *
     * @private
     *
     * @memberOf ConsultaComponent
     */
    private resetaOformularioParaOsDadosAnteriores() {
        this.consultaForm.get('rmr').setValue(this.dadosDaBusca['rmr'])
        this.consultaForm.get('nome').setValue(this.dadosDaBusca['nome'])
        //this._centroSelecionado.id = this.dadosDaBusca['idCentroAvaliador'];
        //this._centroSelecionado.tipo = this.dadosDaBusca['idTipoFuncao'];*/
        this.selectCentros.value = { id: this.dadosDaBusca['idCentroAvaliador'],
                                     tipo: this.dadosDaBusca['idTipoFuncao']
        };
    }

    /**
     * Salva a busca atual
     *
     * @private
     *
     * @memberOf ConsultaComponent
     */
    private salvaBusca() {
        localStorage.setItem(this.autenticacao.usuarioLogado().username + "buscaPaciente",  JSON.stringify(this.dadosDaBusca));
    }

    /**
     * Carrega a tela de detalhe e salva a busca atual para ser utilizada quando voltar
     *
     * @param {number} rmr
     *
     * @memberOf ConsultaComponent
     */
    carregaTelaDetalhe(rmr: number) {
        this.salvaBusca();
        this.router.navigateByUrl("/pacientes/" + rmr);
    }

    /**
     * Formata a data de nascimento para dd/mm/yyyy
     *
     * @param {string} data
     * @returns {string}
     * @memberof ConsultaComponent
     */
    formataDataNascimento(data: string): string {
        return PacienteUtil.converterStringEmData(data).toLocaleDateString();
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaComponent];
    }

    /**
     * Carrega a tela de pendências de um paciente.
     *
     * @param {number} rmr
     *
     * @memberOf ConsultaComponent
     */
    carregaTelaPendenciaPaciente(rmr: number) {
        this.salvaBusca();
        this.router.navigateByUrl("/pacientes/"+rmr+"/avaliacaoMedico")
    }

    /**
     * Carrega a tela de Notificações para um determinado paciente
     *
     * @param {number} rmr
     *
     * @memberOf ConsultaComponent
     */
    carregaTelaNotificacoes(rmr: number) {
        this.salvaBusca();
        this.router.navigateByUrl('/pacientes/notificacoes?idPaciente=' + rmr );
    }

    /**
     * Carrega a tela de analise de match
     *
     * @param {number} rmr
     *
     * @memberOf ConsultaComponent
     */
    carregaTelaAnaliseMatch(rmr: number) {
        this.router.navigateByUrl("/pacientes/" + rmr + "/matchs");
    }

    /**
     * Carrega a tela de histórico do paciente (evolução da busca)
     * listando todos os eventos registrados para um determinado paciente (RMR).
     *
     * @param {number} rmr
     */
    carregaTelaLogPaciente(rmr: number) {
        this.router.navigateByUrl("/pacientes/" + rmr + "/logs");
    }

    public deveExibirIconeDetalhePaciente(paciente): boolean {
        if (this.autenticacao.verificaUsuarioLogadoEMedicoResponsavelPaciente(paciente)) {
            return true;
        }
        else if (this.autenticacao.verificaUsuarioLogadoEMedicoAvaliadorDoCentroAvaliadorDoPaciente(paciente)) {
            return true;
        }
        else if (this.autenticacao.verificaUsuarioLogadoEMedicoTransplantadorAssociadoABuscaDoPaciente(paciente)) {
            return true;
        }
        return false;
    }

    public deveExibirIconeAnaliseMatch(paciente): boolean {
        if(!paciente.temBuscaAtiva){
            return false;    
        }
        else if (this.autenticacao.verificaUsuarioLogadoEMedicoResponsavelPaciente(paciente)) {
            return true;
        }
        else if (this.autenticacao.verificaUsuarioLogadoEMedicoTransplantadorAssociadoABuscaDoPaciente(paciente)) {
            return true;
        }
        return false;
    }

    public deveExibirIconeVisualizarPendencias(paciente): boolean {
        if (this.autenticacao.verificaUsuarioLogadoEMedicoResponsavelPaciente(paciente)) {
            return true;
        }
        return false;
    }

    public deveExibirIconequantidadeNotificacoes(paciente): boolean {
        if (this.autenticacao.verificaUsuarioLogadoEMedicoResponsavelPaciente(paciente) && paciente.quantidadeNotificacoes > 0) {
            return true;
        }
        return false;
    }

    public deveExibirIconeQuantiddadeNotificacoesNaoLidas(paciente): boolean {
        return paciente.quantidadeNotificacoesNaoLidas > 0;
    }


}

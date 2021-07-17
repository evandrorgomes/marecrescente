import { Component, OnInit, ViewChild, AfterViewInit } from "@angular/core";
import { PermissaoRotaComponente } from "app/shared/permissao.rota.componente";
import { TranslateService } from '@ngx-translate/core/src/translate.service';
import { ComponenteRecurso } from "app/shared/enums/componente.recurso";
import { HistoricoNavegacao } from "app/shared/historico.navegacao";
import { Router, ActivatedRoute } from "@angular/router";
import { RouterUtil } from "app/shared/util/router.util";
import { HeaderPacienteComponent } from "app/paciente/consulta/identificacao/header.paciente.component";
import { BuscaService } from "../busca.service";
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { UploadArquivoComponent } from "app/shared/upload/upload.arquivo.component";
import { ArquivoRelatorioInternacional } from "app/shared/classes/arquivo.relatorio.internacional";
import { forEach } from '@angular/router/src/utils/collection';
import { ErroMensagem } from "app/shared/erromensagem";
import { ErroUtil } from "app/shared/util/erro.util";
import { MessageBox } from '../../../shared/modal/message.box';
import { ArquivoRelatorioInternacionalService } from "app/shared/service/arquivo.relatorio.internacional.service";
import { Busca } from "../busca";
import { FileItem } from 'ng2-file-upload';
import { Recursos } from "app/shared/enums/recursos";


/**
 * Classe que representa o componente de listagem de arquivosd da busca internacional.
 * @author Filipe Paes
 */
@Component({
    selector: 'app-busca-intenacional',
    templateUrl: './busca.internacional.component.html'
})
export class BuscaInternacionalComponent implements PermissaoRotaComponente, OnInit {

    @ViewChild('headerPaciente')
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild("uploadComponent")
    private uploadComponent: UploadArquivoComponent;
    
    private idBusca: number;

    public _temPermissaoUpload: boolean;
    public _temPermissaoExcluir: boolean;

    public _descricao: string;

    public _arquivos: ArquivoRelatorioInternacional[];
    
    
    constructor(private translate: TranslateService, private router: Router, private activatedRoute: ActivatedRoute,
        private buscaService: BuscaService,  private autenticacaoService: AutenticacaoService, private messageBox: MessageBox,
        private arquivoRelatorioInternacionalService: ArquivoRelatorioInternacionalService) {

    }
    
    ngOnInit(): void {

        this.uploadComponent.extensoes = 'extensaoArquivoRelatorioInternacional';
        this.uploadComponent.tamanhoLimite = 'tamanhoArquivoRelatorioInternacionalEmByte';
        this.uploadComponent.quantidadeMaximaArquivos = 'quantidadeArquivosRelatorioInternacional';            
        this.uploadComponent.uploadObrigatorio = true;

        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRoute, ['idPaciente', 'idBusca']).then(res => {
            this.idBusca = new Number(res['idBusca']).valueOf();
            const rmr: number = new Number(res['idPaciente']).valueOf();

            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(rmr);

            this._temPermissaoUpload = this.autenticacaoService.temRecurso(Recursos.CADASTRAR_ARQUIVO_RELATORIO_INTERNACIONAL);
            this._temPermissaoExcluir = this.autenticacaoService.temRecurso(Recursos.EXCLUIR_ARQUIVO_RELATORIO_BUSCA_INTERNACIONAL);

            this.listarArquivos();

        });
    }
    
    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.BuscaInternacionalComponent];
    }

    private listarArquivos(): void {
        this.buscaService.listarArquivosRelatorioInternacional(this.idBusca).then(res => {            
            this._arquivos = undefined;
            if (res) {
                this._arquivos = [];
                res.forEach(arquivo => {
                    this._arquivos.push(new ArquivoRelatorioInternacional().jsonToEntity(arquivo));
                });
            }
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

    }


    public voltar(): void {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    public enviar(): void {

        if (this.uploadComponent.validateForm()) {

            const arquivoRelatorioInternacional: ArquivoRelatorioInternacional = new ArquivoRelatorioInternacional();
            arquivoRelatorioInternacional.descricao = this._descricao;
            arquivoRelatorioInternacional.busca = new Busca();
            arquivoRelatorioInternacional.busca.id = this.idBusca;

            let arquivo: FileItem;
            this.uploadComponent.arquivos.forEach((item: FileItem, key: string) => {
                arquivo = item;
            });
            
            this.arquivoRelatorioInternacionalService.salvar(arquivo, arquivoRelatorioInternacional).then(res => {
                this.messageBox.alert(res.mensagem).withCloseOption(() => {              
                    this.uploadComponent.clear();                    
                    this._descricao = '';
                    this.listarArquivos();
                })
                .show();
            });

        }

    }

    public retornaNomeArquivo(caminhoArquivo: string): string {
        const indexDaBarra = caminhoArquivo.indexOf('/') + 1;
        caminhoArquivo = caminhoArquivo.substring(indexDaBarra, caminhoArquivo.length);
        const indexDoUnderline = caminhoArquivo.indexOf('_');
        caminhoArquivo = caminhoArquivo.substring(indexDoUnderline + 1);
        return caminhoArquivo;
    }


    /**
     * Faz a chamada para o download do arquivo no servidor REST. 
     * 
     * @param arquivoExame 
     */
    public baixarArquivo(arquivo: ArquivoRelatorioInternacional) {
        this.arquivoRelatorioInternacionalService.downloadArquivo(arquivo.id,
            this.retornaNomeArquivo(arquivo.caminho)); 
    }

    /**
     * Faz a chamada para o download do arquivo zipado.
     * 
     * @param arquivoExame 
     */
    public baixarArquivoZipado(arquivo: ArquivoRelatorioInternacional) {
        this.arquivoRelatorioInternacionalService.downloadZip(arquivo.id, this.removerExtensaoArquivo(
            this.retornaNomeArquivo(arquivo.caminho)));
    }

    private removerExtensaoArquivo(nomeArquivo: string): string {
        const indexDoPonto = nomeArquivo.indexOf('.') ;
        if (indexDoPonto !== -1) {
            return nomeArquivo.substring(0, indexDoPonto);
        }
        return nomeArquivo;
    }

    /**
     * Faz a chamada para o download do arquivo zipado.
     * 
     * @param arquivoExame 
     */
    public baixarTodosArquivosZipados() {
        this.buscaService.downloadArquivosRelatorioInternacional(this.idBusca);
    }

    public excluirArquivo(arquivo: ArquivoRelatorioInternacional) {

        this.arquivoRelatorioInternacionalService.excluirPorId(arquivo.id).then((res: any) => {
            this.messageBox.alert(res).withCloseOption(() => {
                this.listarArquivos();
            })
            .show();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
        
    }


}
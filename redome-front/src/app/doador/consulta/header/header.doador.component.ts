import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Doador } from '../../doador';
import { DoadorNacional } from '../../doador.nacional';
import { DoadorService } from '../../doador.service';
import { CentroTransplante } from '../../../shared/dominio/centro.transplante';
import { DataUtil } from '../../../shared/util/data.util';
import { BancoSangueCordao } from '../../../shared/dominio/banco.sangue.cordao';
import { Registro } from '../../../shared/dominio/registro';
import { TiposDoador } from '../../../shared/enums/tipos.doador';
import { CordaoInternacional } from '../../cordao.internacional';
import { CordaoNacional } from '../../cordao.nacional';
import { DoadorInternacional } from '../../doador.internacional';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { StatusDoador } from '../../../shared/dominio/status.doador';


/**
 * Classe generica para exibição do header do doador.
 * A classe tem comportamento customizado (cache dos dados), 
 * centralizando o formato do HTML a todos os headers. 
 * 
 * @author Bruno Sousa
 */
@Component({
    selector: 'header-doador',
    templateUrl: './header.doador.component.html',
    encapsulation: ViewEncapsulation.Emulated

})
export class HeaderDoadorComponent implements OnInit {

    public static chaveParcial: string = "doador_header"

    sexo: string;
    private labelsInternacionalizadas;
    idade: string;
    private anoLabel: string;

    // Doador carregado, da base ou do cache.
    public doador: Doador;

    /**
     * @description Exibido em letras maiores, na parte superior do header.
     * Pode exibir DMR + nome ou ID + Registro, doador nacional e internacional, respectivamente.
    **/
    public identificacao: string;
    
    /**
     * @description Informações exibidas ao lado da identificação.
     * Como, por exemplo, nome + sexo que são exibidos para doador nacional.
     */
    public informacoesPessoais: string;

    centroColeta: string;
    dataLimite: Date;

    public _dataPedidoContato: Date;
    public _dataUltimoPedidoContato: Date;

    constructor(private translate: TranslateService,
        private autenticacaoService: AutenticacaoService,
        private doadorService: DoadorService) { }

    ngOnInit(): void { }

    
    popularCabecalhoIdentificacaoPorDoador(idDoador: number) {
        this.translate.get('textosGenericos').subscribe(res => {
            this.labelsInternacionalizadas = res;
 
            let doadorCache: Doador;
            let cache: string = 
                new String(localStorage.getItem(this.autenticacaoService.usuarioLogado().username + HeaderDoadorComponent.chaveParcial))?
                localStorage.getItem(this.autenticacaoService.usuarioLogado().username + HeaderDoadorComponent.chaveParcial):null;
            
            if (cache && cache !== "undefined") {
                doadorCache = JSON.parse(cache);
            }

            if (doadorCache && doadorCache.id === idDoador) {
                this.doador = this.popularCabecalhoIdentificacao(doadorCache);
            }
            else {
                this.doadorService.obterIdentificacaoDoadorPorId(idDoador).then((result) => {
                    this.doador = this.popularCabecalhoIdentificacao(result);
                    localStorage.setItem(
                        this.autenticacaoService.usuarioLogado().username +
                        HeaderDoadorComponent.chaveParcial, JSON.stringify(this.doador));
                });
            }
        });

    }

    /**
     * Popula o doador com os dados do doador, de acordo com o tipo.
     * 
     * @param doadorRetornado doador sem formatação retornado do banco.
     * @return doador populado.
     */
    private popularCabecalhoIdentificacao(doadorRetornado: any): Doador {
        let tipoDoadorId: number = new Number(doadorRetornado.idTipoDoador).valueOf();
        if (isNaN(tipoDoadorId)) {
            tipoDoadorId = new Number(doadorRetornado.tipoDoador).valueOf();
        }

        if (TiposDoador.NACIONAL == tipoDoadorId) {
            return this.popularCabecalhoDoadorNacional(doadorRetornado);
        }
        else if (TiposDoador.INTERNACIONAL == tipoDoadorId) {
            return this.popularCabecalhoDoadorInternacional(doadorRetornado);
        }
        else if (TiposDoador.CORDAO_NACIONAL == tipoDoadorId) {
            return this.popularCabecalhoDoadorCordaoNacional(doadorRetornado);
        }
        else if (TiposDoador.CORDAO_INTERNACIONAL == tipoDoadorId) {
            return this.popularCabecalhoDoadorCordaoInternacional(doadorRetornado);
        }
    }

    /**
     * Popula o doador com os dados do doador medula nacional.
     * 
     * @param doadorRetornado doador sem formatação retornado do banco.
     * @return doador nacional populado.
     */
    private popularCabecalhoDoadorNacional(doadorRetornado: any): DoadorNacional {
        let nacional: DoadorNacional = new DoadorNacional();
        nacional.id = new Number(doadorRetornado.id).valueOf();
        nacional.dmr = new Number(doadorRetornado.identificacao).valueOf();
        nacional.nome = doadorRetornado.nome;
        nacional.sexo = doadorRetornado.sexo;
        if (doadorRetornado.statusDoador) {
            nacional.statusDoador = new StatusDoador(doadorRetornado.statusDoador.id);
            nacional.statusDoador.descricao = doadorRetornado.statusDoador.descricao;
        }
        if (doadorRetornado.dataNascimento) {
            if (doadorRetornado.dataNascimento.indexOf("T") == -1) {
                nacional.dataNascimento = new Date(doadorRetornado.dataNascimento + 'T00:00:00');
            } else {
                nacional.dataNascimento = new Date(doadorRetornado.dataNascimento);
            }
        }

        this.translate.get('textosGenericos').subscribe(res => {
            this.labelsInternacionalizadas = res;
            this.sexo = this.criarGeneroDecorator(nacional.sexo);
            this.idade = "";

            if (nacional.dataNascimento) {
                this.idade = this.retornarIdadeFormatada(nacional.dataNascimento) + " (" + nacional.dataNascimento.toLocaleDateString() + ")";
            }

            this.identificacao = ('DMR ' + nacional.dmr + ' : ' + (nacional.nome ? nacional.nome : '') + '  | ');
            this.informacoesPessoais = this.sexo + ', ' + this.idade + ', ';
        });

        return nacional;
    }

    /**
     * Popula o doador com os dados do doador de medula internacional.
     * 
     * @param doadorRetornado doador sem formatação retornado do banco.
     * @return doador internacional populado.
     */
    private popularCabecalhoDoadorInternacional(doadorRetornado: any): DoadorInternacional {
        let internacional: DoadorInternacional = new DoadorInternacional();
        internacional.id = new Number(doadorRetornado.id).valueOf();
        internacional.idRegistro = doadorRetornado.identificacao;
        internacional.registroOrigem = 
            new Registro(doadorRetornado.registroOrigem.id, doadorRetornado.registroOrigem.nome);
        internacional.sexo = doadorRetornado.sexo;
        internacional.statusDoador = new StatusDoador();
        internacional.statusDoador.descricao = doadorRetornado.statusDoador.descricao
        internacional.statusDoador.id = doadorRetornado.statusDoador.id
        internacional.dataNascimento = new Date(doadorRetornado.dataNascimento + 'T00:00:00');

        this.translate.get('textosGenericos').subscribe(res => {
            this.labelsInternacionalizadas = res;
            this.sexo = this.criarGeneroDecorator(internacional.sexo);
            this.idade = internacional.idade ? 
                new String(internacional.idade).valueOf() : 
                    "≅" + this.retornarIdadeFormatada(internacional.dataNascimento);

            this.identificacao = ('ID ' + internacional.idRegistro + ' : ' + internacional.registroOrigem.nome + '  | ');
            this.informacoesPessoais = this.sexo + ', ' + this.idade + ', ';
        });

        return internacional;
    }

    /**
     * Popula o doador com os dados do doador de cordão nacional.
     * 
     * @param doadorRetornado doador sem formatação retornado do banco.
     * @return doador nacional populado.
     */
    private popularCabecalhoDoadorCordaoNacional(doadorRetornado: any): CordaoNacional {
        let cordaoNacional: CordaoNacional = new CordaoNacional();
        cordaoNacional.id = new Number(doadorRetornado.id).valueOf();
        cordaoNacional.idBancoSangueCordao = doadorRetornado.identificacao;
        cordaoNacional.bancoSangueCordao = 
            new BancoSangueCordao(doadorRetornado.bancoSangueCordao.id, doadorRetornado.bancoSangueCordao.nome);
        cordaoNacional.sexo = doadorRetornado.sexo;
        cordaoNacional.statusDoador = new StatusDoador();
        cordaoNacional.statusDoador.descricao = doadorRetornado.statusDoador.descricao
        cordaoNacional.statusDoador.id = doadorRetornado.statusDoador.id
        cordaoNacional.dataNascimento = new Date(doadorRetornado.dataNascimento + 'T00:00:00');

        this.translate.get('textosGenericos').subscribe(res => {
            this.labelsInternacionalizadas = res;
            this.sexo = this.criarGeneroDecorator(cordaoNacional.sexo);

            this.identificacao = ('ID ' + cordaoNacional.idBancoSangueCordao + ' : ' + cordaoNacional.bancoSangueCordao.nome + '  | ');
            this.informacoesPessoais = this.sexo + ', ';
        });

        return cordaoNacional;
    }

    /**
     * Popula o doador com os dados do doador de cordão nacional.
     * 
     * @param doadorRetornado doador sem formatação retornado do banco.
     * @return doador nacional populado.
     */
    private popularCabecalhoDoadorCordaoInternacional(doadorRetornado: any): CordaoInternacional {
        let cordaoInternacional: CordaoInternacional = new CordaoInternacional();
        cordaoInternacional.id = new Number(doadorRetornado.id).valueOf();
        cordaoInternacional.idRegistro = doadorRetornado.identificacao;
        cordaoInternacional.registroOrigem = 
            new Registro(doadorRetornado.registroOrigem.id, doadorRetornado.registroOrigem.nome);
        cordaoInternacional.sexo = doadorRetornado.sexo;
        cordaoInternacional.statusDoador = new StatusDoador();
        cordaoInternacional.statusDoador.descricao = doadorRetornado.statusDoador.descricao
        cordaoInternacional.statusDoador.id = doadorRetornado.statusDoador.id
        cordaoInternacional.dataNascimento = new Date(doadorRetornado.dataNascimento + 'T00:00:00');

        this.translate.get('textosGenericos').subscribe(res => {
            this.labelsInternacionalizadas = res;
            this.sexo = this.criarGeneroDecorator(cordaoInternacional.sexo);

            this.identificacao = ('ID ' + cordaoInternacional.idRegistro + ' : ' + cordaoInternacional.registroOrigem.nome + '  | ');
            this.informacoesPessoais = this.sexo + ', ' + this.idade + ', ';
        });

        return cordaoInternacional;
    }

    /**
     * Obtém a classe CSS para formatar a exibição do status do doador.
     * @return string contendo a classe referente ao status.
     */
    public obterClasseCSS(): string {
        if (this.doador) {
            let doadorAtivo: boolean = this.doador.statusDoador.id == StatusDoador.ATIVO ||
                this.doador.statusDoador.id == StatusDoador.ATIVO_RESSALVA;
            return doadorAtivo ? 'label label-success' : 'label label-danger'
        }
        return "";
    }

    /**
     * Metodo que recebe o paciente e prepara os dados para serem exibidas no html
     * 
     * @param {DoadorNacional} doador 
     * @memberof HeaderDoadorComponent
     */
    public popularCabecalhoIdentificacaoParaCentroDeColeta(doador: DoadorNacional, centro: CentroTransplante, dataLimite: Date) {

        this.popularCabecalhoIdentificacaoPorDoador(doador.id);

        if(centro){
            this.centroColeta = centro.nome;
        }
        this.dataLimite = dataLimite;
        this.popularCabecalhoIdentificacao(doador);
    }

    public popularCabecalhoIdentificacaoParaContatoDoador(doador: DoadorNacional, dataPedidoContato, dataUltimoPedidoContato: Date) {

        this.popularCabecalhoIdentificacaoPorDoador(doador.id);
        
        this._dataPedidoContato = dataPedidoContato;
        this._dataUltimoPedidoContato = dataUltimoPedidoContato;

        this.popularCabecalhoIdentificacao(doador);
    }

    private retornarIdadeFormatada(dataNascimento: Date): string {
        if (!dataNascimento) {
            return "";
        }
        let idadeEmAnos: number = DataUtil.calcularIdadeComDate(dataNascimento);
        return ("" + idadeEmAnos + " " + this.labelsInternacionalizadas['anos']);
    }

    private criarGeneroDecorator(sexo: string): string {
        return (sexo === "M") ? this.labelsInternacionalizadas['H'] : this.labelsInternacionalizadas['M'];
    }

    public clearCache(): boolean {
        const identificacaoCache: any =
            localStorage.getItem(
                this.autenticacaoService.usuarioLogado().username + HeaderDoadorComponent.chaveParcial);

        if (identificacaoCache) {
            localStorage.removeItem(this.autenticacaoService.usuarioLogado().username + HeaderDoadorComponent.chaveParcial);
        }
        return (identificacaoCache);
    }

    /**
     * Popula o cabeçalho, ignorando o cache.
     * 
     * @param doador referência para o doador pesquisado.
     */
    public popularCabecalhoIdentificacaoNoCache(idDoador: number) {
        this.clearCache();
        this.popularCabecalhoIdentificacaoPorDoador(idDoador);
    }

}
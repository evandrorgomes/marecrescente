import { IdentificacaoDoador } from 'app/shared/util/doador.util';
import { transition, trigger, useAnimation } from "@angular/animations";
import { Component, OnInit, ViewChild } from "@angular/core";
import { TranslateService } from '@ngx-translate/core';
import { Locus } from 'app/paciente/cadastro/exame/locus';
import { Printable } from 'app/shared/component/impressao/printable';
import { ConvertUtil } from 'app/shared/util/convert.util';
import { fadeIn } from 'ng-animate';
import { FasesMatch } from '../../../../shared/enums/fases.match';
import { TiposValorHla } from '../../../../shared/enums/tipo.valor.hla';
import { TiposDoador } from '../../../../shared/enums/tipos.doador';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { IModalComponent } from "../../../../shared/modal/factory/i.modal.component";
import { IModalContent } from "../../../../shared/modal/factory/i.modal.content";
import { MessageBox } from '../../../../shared/modal/message.box';
import { HlaService } from '../../../../shared/service/hla.service';
import { DataUtil } from '../../../../shared/util/data.util';
import { DateTypeFormats } from '../../../../shared/util/date/date.type.formats';
import { ErroUtil } from '../../../../shared/util/erro.util';
import { GenotipoDoadorComparadoDTO } from '../../../genotipo.comparado.doador.dto';
import { GenotipoComparadoDTO } from '../../../genotipo.comparado.dto';
import { GenotipoDTO } from '../../../genotipo.dto';
import { GenotipoService } from '../../../genotipo.service';
import { SubHeaderPacienteComponent } from '../subheaderpaciente/subheader.paciente.component';
import { AnaliseMatchDTO } from './../analise.match.dto';

/**
 * Classe que representa o modal de comparação de genótipos
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-genotipo-comparado',
    templateUrl: './modal.genotipo.comparado.component.html',
    animations: [
        trigger('fadeIn', [
          transition(':enter', useAnimation(fadeIn)),
        ])
      ]
})
export class ModalGenotipoComparadoComponent  implements OnInit, IModalContent {

    fadeIn  = false;

    data: any;
    target: IModalComponent;
    close: (target: IModalComponent) => void;

    @ViewChild("subHeaderPaciente")
    public subHeaderPacienteComponent:SubHeaderPacienteComponent;

    public divIdenticador: string = '0';
    public divPaciente: boolean = false;

    public codigoLocus: string;
    public pacienteSelecionado: boolean = false;

    public _textoLocus: any;
    public _primeiroAleloPaciente: string;
    public _segundoAleloPaciente: string;
    public _primeiroAleloDoador: string;
    public _segundoAleloDoador: string;

    private _cacheAlelo: Map<string, string> = new Map<string, string>();
    private _valorPrimeiroAleloPaciente: string;
    private _valorSegundoAleloPaciente: string;
    private _valorPrimeiroAleloDoador: string;
    private _valorSegundoAleloDoador: string;

    public analiseMatchDTO: AnaliseMatchDTO;
    private labelsInternacionalizadas: any;
    private _cssQualificacao: Map<string, string> = new Map<string, string>([
        ["P", "qualificacaoP"], ["L", "qualificacaoL"], ["M", "qualificacaoM"], ["A", "qualificacaoA"]
    ]);

    public genotipoComparadoDTO: GenotipoComparadoDTO;
    private _textoFases: any;

    @ViewChild("areaImpressao")
    public areaImpressao: Printable;

    constructor(private translate: TranslateService, private hlaService: HlaService,
        private messageBox: MessageBox, private genotipoService: GenotipoService) {
    }

    ngOnInit(): void {
        this.translate.get("textosGenericos.fases").subscribe(res => {
            this._textoFases = res;
        });

        this.translate.get('textosGenericos').subscribe(res => {
            this.labelsInternacionalizadas = res;
        });

        if (this.data._rmr) {
            this.genotipoService.listarGenotiposComparados(this.data._rmr, this.data.listaIdsDoadores).then(res => {
                this._cacheAlelo.clear();
                this.genotipoComparadoDTO = this.criarGenotipoComparadoDTO(res);
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
        else if (this.data._idBuscaPreliminar) {
            this.genotipoService.listarGenotiposComparadosBuscaPreliminar(
                    this.data._idBuscaPreliminar, this.data.listaIdsDoadores).then(res => {
                    this._cacheAlelo.clear();
                this.genotipoComparadoDTO = this.criarGenotipoComparadoDTO(res);
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }

        this.subHeaderPacienteComponent.analiseMatchDTO = this.data.analiseDto;
    }

    private criarGenotipoComparadoDTO(res: any): GenotipoComparadoDTO {
        let genotipoComparadoDTO: GenotipoComparadoDTO = new GenotipoComparadoDTO();
        genotipoComparadoDTO.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());
        genotipoComparadoDTO.nomePaciente = ConvertUtil.parseJsonParaAtributos(res.nomePaciente, new String());
        genotipoComparadoDTO.dataNascimento = ConvertUtil.parseJsonParaAtributos(
            res.dataNascimento, new Date());
            //DataUtil.toDate(DataUtil.toDateFormat(res.dataNascimento, DateTypeFormats.DATE_ONLY));
        genotipoComparadoDTO.idade = this.idadeFormatada(genotipoComparadoDTO.dataNascimento);
        genotipoComparadoDTO.sexo = this.criarGeneroDecorator(res.sexo);
        genotipoComparadoDTO.abo = ConvertUtil.parseJsonParaAtributos(res.abo, new String());
        genotipoComparadoDTO.peso = ConvertUtil.parseJsonParaAtributos(res.peso, new Number());

        let listaLocus: Locus[] = [];
        if (res.listaLocus) {
            res.listaLocus.forEach(item => {
                let locus: Locus = new Locus();
                locus.codigo = item.codigo;
                locus.ordem = new Number(item.ordem).valueOf();
                listaLocus.push(locus);
            })
        }
        genotipoComparadoDTO.listaLocus = listaLocus;
        genotipoComparadoDTO.genotipoPaciente = this.criarListaGenotipoDTO(res.genotipoPaciente);

        genotipoComparadoDTO.genotiposDoadores = [];
        if (res.genotiposDoadores) {
            res.genotiposDoadores.forEach(item => {
                let genotipoComparado: GenotipoDoadorComparadoDTO = new GenotipoDoadorComparadoDTO();
                genotipoComparado.idDoador = item.idDoador;

                genotipoComparado.dmr = item.dmr;
                genotipoComparado.idRegistro = item.idRegistro;
                genotipoComparado.identificadorDoador = item.identificadorDoador;

                genotipoComparado.dataNascimento = DataUtil.toDate(DataUtil.toDateFormat(item.dataNascimento, DateTypeFormats.DATE_ONLY));
                genotipoComparado.idade = this.idadeFormatada(genotipoComparado.dataNascimento);
                genotipoComparado.sexo = item.sexo;
                genotipoComparado.sexoFormatado = this.criarGeneroDecorator(item.sexo);
                genotipoComparado.abo = item.abo;
                genotipoComparado.classificacao = item.classificacao;
                genotipoComparado.mismatch = item.mismatch;
                genotipoComparado.peso = new Number(item.peso).valueOf();
                genotipoComparado.fase = this.getTitulo(item.fase);
                genotipoComparado.genotipoDoador = this.criarListaGenotipoDTO(item.genotipoDoador);
                genotipoComparado.tipoDoador = new Number(item.tipoDoador).valueOf();
                genotipoComparado.dataAtualizacao = new Date(item.dataAtualizacao);
                genotipoComparado.descricaoTipoPermissividade = item.descricaoTipoPermissividade;

                if (TiposDoador.CORDAO_NACIONAL == genotipoComparado.tipoDoador ||
                    TiposDoador.CORDAO_INTERNACIONAL == genotipoComparado.tipoDoador ) {
                    genotipoComparado.idRegistro = item.idRegistro;
                    genotipoComparado.idBscup = item.idBscup;
                    genotipoComparado.quantidadeTCNPorKilo = new Number(item.quantidadeTCNPorKilo).valueOf();
                    genotipoComparado.quantidadeCD34PorKilo = new Number(item.quantidadeCD34PorKilo).valueOf();
                }
                else if (TiposDoador.NACIONAL == genotipoComparado.tipoDoador ||
                    TiposDoador.INTERNACIONAL == genotipoComparado.tipoDoador ) {
                    genotipoComparado.peso = new Number(item.peso).valueOf();
                    if (TiposDoador.INTERNACIONAL == genotipoComparado.tipoDoador ) {
                        genotipoComparado.idRegistro = item.idRegistro;
                    }
                }


                genotipoComparadoDTO.genotiposDoadores.push(genotipoComparado);
            })
        }

        this.data.ordernarLista(genotipoComparadoDTO);

        return genotipoComparadoDTO;
    }


    public carregarAlelosGenotipos(genotipo: GenotipoDTO): Promise<any> {

        let valorPrimeiroAlelo: string = "";
        let valorSegundoAlelo: string = "";

        let valorPrAlelo: string = "";
        let valorSegAlelo: string = "";

        if (genotipo.tipoPrimeiroAlelo === TiposValorHla.NMDP) {
            valorPrAlelo = genotipo.primeiroAlelo.split(":")[1];
        }
        else if (genotipo.tipoPrimeiroAlelo === TiposValorHla.GRUPO_G || genotipo.tipoPrimeiroAlelo === TiposValorHla.GRUPO_P) {
            valorPrAlelo = genotipo.primeiroAlelo;
        }
        if (valorPrAlelo) {
            if (this._cacheAlelo && this._cacheAlelo.has(valorPrAlelo)) {
                valorPrimeiroAlelo = this._cacheAlelo.get(valorPrAlelo);
            }
        }
        if (genotipo.tipoSegundoAlelo === TiposValorHla.NMDP) {
            valorSegAlelo = genotipo.segundoAlelo.split(":")[1];
        }
        else if (genotipo.tipoSegundoAlelo === TiposValorHla.GRUPO_G || genotipo.tipoSegundoAlelo === TiposValorHla.GRUPO_P) {
            valorSegAlelo = genotipo.segundoAlelo;
        }

        if (valorSegAlelo) {
            if (this._cacheAlelo && this._cacheAlelo.has(valorSegAlelo)) {
                valorSegundoAlelo = this._cacheAlelo.get(valorSegAlelo);
            }
        }
        if (valorPrimeiroAlelo && valorSegundoAlelo) {
            return Promise.resolve({
                valorPrimeiroAlelo: valorPrimeiroAlelo,
                valorSegundoAlelo: valorSegundoAlelo
            });
        }

        if (valorPrimeiroAlelo && !valorSegundoAlelo) {
            return new Promise((resolve) => {
                if (genotipo.tipoSegundoAlelo === TiposValorHla.NMDP) {

                    this.hlaService.obterSubTiposNmdp(valorSegAlelo).then(res => {
                        valorSegundoAlelo = res;
                        this._cacheAlelo.set(valorSegAlelo, res);
                        resolve({
                            valorPrimeiroAlelo: valorPrimeiroAlelo,
                            valorSegundoAlelo: valorSegundoAlelo
                        });
                    });
                }
                else if (genotipo.tipoSegundoAlelo === TiposValorHla.GRUPO_G || genotipo.tipoSegundoAlelo === TiposValorHla.GRUPO_P) {
                    this.hlaService.obterGrupo(genotipo.tipoSegundoAlelo, genotipo.locus, valorSegAlelo).then(res => {
                        valorSegundoAlelo = res;
                        this._cacheAlelo.set(valorSegAlelo, res);
                        resolve({
                            valorPrimeiroAlelo: valorPrimeiroAlelo,
                            valorSegundoAlelo: valorSegundoAlelo
                        });
                    });
                }
                else{
                  resolve({
                    valorPrimeiroAlelo: valorPrimeiroAlelo,
                    valorSegundoAlelo: valorSegundoAlelo
                  });
                }
            });
        }
        if (!valorPrimeiroAlelo && valorSegundoAlelo) {
            return new Promise((resolve) => {
                if (genotipo.tipoPrimeiroAlelo === TiposValorHla.NMDP) {
                    this.hlaService.obterSubTiposNmdp(valorPrAlelo).then(res => {
                        valorPrimeiroAlelo = res;
                        this._cacheAlelo.set(valorPrAlelo, res);
                        resolve({
                            valorPrimeiroAlelo: valorPrimeiroAlelo,
                            valorSegundoAlelo: valorSegundoAlelo
                        });
                    });
                }
                else if (genotipo.tipoPrimeiroAlelo === TiposValorHla.GRUPO_G || genotipo.tipoPrimeiroAlelo == TiposValorHla.GRUPO_P) {
                    this.hlaService.obterGrupo(genotipo.tipoPrimeiroAlelo, genotipo.locus, valorPrAlelo).then(res => {
                        valorPrimeiroAlelo = res;
                        this._cacheAlelo.set(valorPrAlelo, res);
                        resolve({
                            valorPrimeiroAlelo: valorPrimeiroAlelo,
                            valorSegundoAlelo: valorSegundoAlelo
                        });
                    });
                }
                resolve({
                  valorPrimeiroAlelo: valorPrimeiroAlelo,
                  valorSegundoAlelo: valorSegundoAlelo
                });
            });
        }
        if (!valorPrimeiroAlelo && !valorSegundoAlelo) {
            let promise1: Promise<any>;
            let promise2: Promise<any>;
            if (genotipo.tipoPrimeiroAlelo === TiposValorHla.NMDP) {
                promise1 = this.hlaService.obterSubTiposNmdp(valorPrAlelo);
            }
            else if (genotipo.tipoPrimeiroAlelo === TiposValorHla.GRUPO_G || genotipo.tipoPrimeiroAlelo == TiposValorHla.GRUPO_P) {
                promise1 = this.hlaService.obterGrupo(genotipo.tipoPrimeiroAlelo, genotipo.locus, valorPrAlelo);
            }

            if (genotipo.tipoSegundoAlelo === TiposValorHla.NMDP) {
                promise2 = this.hlaService.obterSubTiposNmdp(valorSegAlelo);
            }
            else if (genotipo.tipoSegundoAlelo === TiposValorHla.GRUPO_G || genotipo.tipoSegundoAlelo == TiposValorHla.GRUPO_P) {
                promise2 = this.hlaService.obterGrupo(genotipo.tipoSegundoAlelo, genotipo.locus, valorSegAlelo);
            }

            return new Promise((resolve) => {
                Promise.all([promise1, promise2]).then( ([result1, result2]) => {
                    if (result1) {
                        valorPrimeiroAlelo = result1;
                        this._cacheAlelo.set(valorPrAlelo, result1);
                    }

                    if (result2) {
                        valorSegundoAlelo = result2;
                        this._cacheAlelo.set(valorSegAlelo, result2);
                    }

                    if (valorPrimeiroAlelo && valorSegundoAlelo) {
                        resolve({
                            valorPrimeiroAlelo: valorPrimeiroAlelo,
                            valorSegundoAlelo: valorSegundoAlelo
                        });
                    }
                });
            });
        }
    }

    public abrirDivGenotipoDescritivo(selecaoPaciente: boolean, genotipoComparadoDTO: GenotipoComparadoDTO,
                genotipoDoadorDTO: GenotipoDoadorComparadoDTO, codigo: string): any {

        let genotipoPaciente: GenotipoDTO = null;
        let genotipoDoador: GenotipoDTO = null;

        this._primeiroAleloPaciente = "";
        this._segundoAleloPaciente = "";

        this._primeiroAleloDoador = "";
        this._segundoAleloDoador = "";

        this._textoLocus = codigo;

        if(selecaoPaciente){
            this.pacienteSelecionado = true;
            this.divIdenticador = '0';
            if(this.codigoLocus === codigo){
                this.divPaciente = false;
                this.codigoLocus = '';
                return;
            }
            this.divPaciente = true;
            this.codigoLocus = codigo;
        }else{
            this.pacienteSelecionado = false;
            if(this.divIdenticador === genotipoDoadorDTO.identificadorDoador && this.codigoLocus === codigo){
              this.divIdenticador = '0';
              return;
            }
            this.codigoLocus = codigo;
            this.divIdenticador = genotipoDoadorDTO.identificadorDoador;

            genotipoDoador = genotipoDoadorDTO.obterValorAleloGenotipoDoador(codigo);

            if (this.verificarTipoNmdpValorGValorP(genotipoDoador.tipoPrimeiroAlelo) ||
            this.verificarTipoNmdpValorGValorP(genotipoDoador.tipoSegundoAlelo)) {

                this._primeiroAleloDoador = genotipoDoador.primeiroAlelo;
                this._segundoAleloDoador = genotipoDoador.segundoAlelo;

                this.carregarAlelosGenotipos(genotipoDoador).then(res => {
                    this._valorPrimeiroAleloDoador = res.valorPrimeiroAlelo;
                    this._valorSegundoAleloDoador = res.valorSegundoAlelo;
                });
            }
        }

        genotipoPaciente = genotipoComparadoDTO.obterValorAleloGenotipoPaciente(codigo);
        this._primeiroAleloPaciente = genotipoPaciente.primeiroAlelo;
        this._segundoAleloPaciente = genotipoPaciente.segundoAlelo;

        if (this.verificarTipoNmdpValorGValorP(genotipoPaciente.tipoPrimeiroAlelo) ||
                this.verificarTipoNmdpValorGValorP(genotipoPaciente.tipoSegundoAlelo)) {

            this.carregarAlelosGenotipos(genotipoPaciente).then(res => {
                this._valorPrimeiroAleloPaciente = res.valorPrimeiroAlelo;
                this._valorSegundoAleloPaciente = res.valorSegundoAlelo;
            });
        }else{
            this.pacienteSelecionado = false;
        }

        this.fadeIn = !this.fadeIn;
    }

    public fecharDivGenotipoDescritivo(dmr: number) {
        if(this.pacienteSelecionado){
            this.pacienteSelecionado = false;
        }
        this.divIdenticador = '0';
        this.divPaciente = false;
    }

    private verificarTipoNmdpValorGValorP(tipo: number): boolean {
        return tipo == TiposValorHla.NMDP || tipo == TiposValorHla.GRUPO_G || tipo == TiposValorHla.GRUPO_P;
    }

    public exibirQualificacaoAlelo(qualificador: string): boolean {
      return qualificador != null && qualificador != '-';
    }

    public exibirPercentualProbabilidade(probabilidade: string): boolean {
      console.log(probabilidade);
      return probabilidade != null && probabilidade != '';
    }

    public ehCordaoInternacional(genotipoDoadorComparadoDTO: GenotipoDoadorComparadoDTO): boolean {
        if (genotipoDoadorComparadoDTO) {
            return genotipoDoadorComparadoDTO.tipoDoador == TiposDoador.CORDAO_INTERNACIONAL;
        }
        return false;
    }

    public ehCordaoNacional(genotipoDoadorComparadoDTO: GenotipoDoadorComparadoDTO): boolean {
        if (genotipoDoadorComparadoDTO) {
            return genotipoDoadorComparadoDTO.tipoDoador == TiposDoador.CORDAO_NACIONAL;
        }
        return false;
    }

    public ehCordao(genotipoDoadorComparadoDTO: GenotipoDoadorComparadoDTO): boolean {
        return this.ehCordaoNacional(genotipoDoadorComparadoDTO) || this.ehCordaoInternacional(genotipoDoadorComparadoDTO);
    }

    public ehMedulaInternacional(genotipoDoadorComparadoDTO: GenotipoDoadorComparadoDTO): boolean {
        if (genotipoDoadorComparadoDTO) {
            return genotipoDoadorComparadoDTO.tipoDoador == TiposDoador.INTERNACIONAL;
        }
        return false;
    }

    public ehMedulaNacional(genotipoDoadorComparadoDTO: GenotipoDoadorComparadoDTO): boolean {
        if (genotipoDoadorComparadoDTO) {
            return genotipoDoadorComparadoDTO.tipoDoador == TiposDoador.NACIONAL;
        }
        return false;
    }

    public ehMedula(genotipoDoadorComparadoDTO: GenotipoDoadorComparadoDTO): boolean {
        return this.ehMedulaNacional(genotipoDoadorComparadoDTO) || this.ehMedulaInternacional(genotipoDoadorComparadoDTO);
    }

    public quantidadeTCNPorKiloFormatada(quantidadeTCNPorKilo: number): string {
        if (this.labelsInternacionalizadas) {
            return (this.labelsInternacionalizadas['quantidadeTCNPorKilo'] + ": " + quantidadeTCNPorKilo);
        }
        return "";

    }

    public quantidadeCD34PorKiloFormatada(quantidadeCD34PorKilo: number): string {
        if (this.labelsInternacionalizadas) {
            return (this.labelsInternacionalizadas['quantidadeCD34PorKilo'] + ": " + quantidadeCD34PorKilo);
        }
        return "";

    }

    public obterCSSPorQualificacao(qualificacao: string): string {
        return this._cssQualificacao.has(qualificacao) ? this._cssQualificacao.get(qualificacao) : "";
    }

    public idadeFormatada(dataNascimento: Date): string {
        if (dataNascimento && this.labelsInternacionalizadas) {
            let idadeEmAnos: number = DataUtil.calcularIdadeComDate(dataNascimento);
            return ("" + idadeEmAnos + " " + this.labelsInternacionalizadas['anos']);
        }
        return "";
    }

    private criarGeneroDecorator(sexo: string): string {
        if (sexo) {
            return (sexo === "M") ? this.labelsInternacionalizadas['homem'] : this.labelsInternacionalizadas['mulher'];
        }
        return "";
    }

    public criarListaGenotipoDTO(lista: any[]): GenotipoDTO[] {
        let retorno: GenotipoDTO[] = [];
        lista.forEach(item => {
            let genotipo: GenotipoDTO = new GenotipoDTO();
            genotipo.locus = item.locus;
            genotipo.primeiroAlelo = item.primeiroAlelo;
            genotipo.segundoAlelo = item.segundoAlelo;
            genotipo.examePrimeiroAlelo = item.examePrimeiroAlelo;
            genotipo.exameSegundoAlelo = item.exameSegundoAlelo;
            genotipo.tipoPrimeiroAlelo = item.tipoPrimeiroAlelo;
            genotipo.tipoSegundoAlelo = item.tipoSegundoAlelo;
            genotipo.qualificacaoAlelo = item.qualificacaoAlelo;
            genotipo.probabilidade = item.probabilidade;
            retorno.push(genotipo);
        })
        return retorno;
    }

    public getTitulo(fase: string): string {
        if (this._textoFases) {
            if (fase == FasesMatch.FASE_1) {
                return this._textoFases["fase1"];
            }
            else if (fase == FasesMatch.FASE_2) {
                return this._textoFases["fase2"];
            }
            else if (fase == FasesMatch.FASE_3) {
                return this._textoFases["fase3"];
            }
            else if (fase == FasesMatch.DISPONIBILIZADO) {
                return this._textoFases["disponibilizado"];
            }
        }
        else {
            return "";
        }
    }

    /**
     * Abre a tela com a comparação realizada no formato PDF, preparado
     * para impressão.
     * @return
     */
    public prepararParaImpressao(): void{
        if (this.data._rmr) {
            this.translate.get("genotipodivergente.nomeArquivoGenotipoComparado").subscribe(nomeTraduzido => {
                let nomeSugeridoArquivo: string = this.data._rmr + "_" + nomeTraduzido + ".pdf";
                this.genotipoService.downloadGenotiposComparados(this.data._rmr, this.data.listaIdsDoadores, nomeSugeridoArquivo);
            });
        }
    }

    public obterAnimacao(): string {
      if (this.divIdenticador !== '0') {
            return 'animated fadeInDown';
        }
        return '';
    }

    /**
     * Getter valorPrimeiroAleloPaciente
     * @return {string}
     */
	public get valorPrimeiroAleloPaciente(): string {
		return this._valorPrimeiroAleloPaciente;
	}

    /**
     * Getter valorSegundoAleloPaciente
     * @return {string}
     */
	public get valorSegundoAleloPaciente(): string {
		return this._valorSegundoAleloPaciente;
	}

    /**
     * Getter valorPrimeiroAleloDoador
     * @return {string}
     */
	public get valorPrimeiroAleloDoador(): string {
		return this._valorPrimeiroAleloDoador;
	}

    /**
     * Getter valorSegundoAleloDoador
     * @return {string}
     */
	public get valorSegundoAleloDoador(): string {
		return this._valorSegundoAleloDoador;
	}

}

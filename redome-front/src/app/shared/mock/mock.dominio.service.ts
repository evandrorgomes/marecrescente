import { Configuracao } from '../dominio/configuracao';
import { Observable } from 'rxjs';
import { FontesCelulas } from '../enums/fontes.celulas';
import { CentroTransplante } from '../dominio/centro.transplante';
import { CondicaoPaciente } from '../dominio/condicaoPaciente';
import { Motivo } from '../dominio/motivo';
import { TipoTransplante } from '../dominio/tipoTransplante';
export class MockDominioService {
    private _RES_LOCUS = [
        {
            "codigo": "A"
        },
        {
            "codigo": "B"
        },
        {
            "codigo": "C"
        },
        {
            "codigo": "DRB1"
        },
        {
            "codigo": "DRB3"
        },
        {
            "codigo": "DRB4"
        },
        {
            "codigo": "DRB5"
        },
        {
            "codigo": "DPB1"
        },
        {
            "codigo": "DQA1"
        },
        {
            "codigo": "DQB1"
        }
    ];

    _RES_METODOLOGIA = [
        {
            id: '1',
            sigla: 'SSP',
            descricao: 'Sequence Specific Primers'
        },
        {
            id: '2',
            sigla: 'SSO',
            descricao: 'Sequence Specific Oligonucleotide'
        },
        {
            id: '3',
            sigla: 'SBT',
            descricao: 'Sequence Based typing'
        },
        {
            id: '4',
            sigla: 'NGS',
            descricao: 'Next Generation Sequencing'
        }
    ];

    _RES_CONFIGURACAO = [
        {
            chave: 'extensaoArquivoLaudo',
            valor: 'image/png,image/jpeg,image/bmp,image/tiff,image/jpg'
        },
        {
            chave: 'tamanhoArquivoLaudoEmByte',
            valor: '5242880'
        },
        {
            chave: 'qtdArquivosLaudo',
            valor: '5'
        },
        {
            chave: 'quantidadeFavoritosAvaliacaoMatch',
            valor: '10'
        }
    ];
    
    _RES_COD_INTERNACIONAL = [];

    _RES_UF = [{sigla: 'RJ', nome: 'Rio de Janeiro'}];

    _RES_PAISES = [{id: 1, nome: 'BRASIL'}];

    _RES_ETNIAS = [];

    _RES_RACAS = [
        {id: 1, nome: 'BRANCA'},
        {id: 2, nome: 'PRETA'},
        {id: 3, nome: 'PARDA'}, 
        {id: 4, nome: 'AMARELA'},
        {id: 5, nome: 'INDÍGENA'},
        {id: 99, nome: 'SEM INFORMAÇÃO'}        
    ];

    listarLocus() {
        return Observable.of(this._RES_LOCUS).toPromise();
    }

    listarMetodologias() {
        return Observable.of(this._RES_METODOLOGIA).toPromise();
    }

    listarConfiguracoes(lista: String[]): Promise<any> {
        return Observable.of(this._RES_CONFIGURACAO).toPromise();
    }

    listarCodigoInternacional() {
        return Observable.of(this._RES_COD_INTERNACIONAL).toPromise();
    }

    obterConfiguracao(chave:string){
        return Observable.of(this._RES_COD_INTERNACIONAL).toPromise();
    }

    getUfs() {
        return Observable.of(this._RES_UF).toPromise();
    }

    getPaises() {
        return Observable.of(this._RES_PAISES).toPromise();
    }

    getEtnias() {
        return Observable.of(this._RES_ETNIAS).toPromise();
    }

    getRacas() {
        return Observable.of(this._RES_RACAS).toPromise();
    }

    listarFonteCelula(siglas?: Array<FontesCelulas>): Promise<any> {
        return null;
    }

    listarCentroAvaliador(): Promise<CentroTransplante[]> {
        return Observable.of(null).toPromise();
    }

    listarCentroTransplante(): Promise<CentroTransplante[]> {
        return Observable.of(null).toPromise();
    }

    getCondicoesPaciente(): Promise<CondicaoPaciente[]> {
        return Observable.of(null).toPromise();
    }

    getMotivos(): Promise<Motivo[]> {
        return Observable.of(null).toPromise();
    }

    listarTipoTransplante(): Promise<TipoTransplante[]> {
        return Observable.of(null).toPromise();
    }

    listarRegistros(): Promise<any> {
        return Observable.of(null).toPromise();
    }

    listarRegistrosInternacionais(): Promise<any> {
        return Observable.of(null).toPromise();
    }

    getUfsSemNaoInformado(): Promise<any> {
        return Observable.of(this._RES_UF).toPromise();       
    }
}
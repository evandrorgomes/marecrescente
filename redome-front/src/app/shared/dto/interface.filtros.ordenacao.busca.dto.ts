import { TiposDoador } from "../enums/tipos.doador";
import { GenotipoDTO } from '../../paciente/genotipo.dto';

export interface IFiltrosOrdenacaoBuscaDTO {

    id: number;
    idDoador: number;
    dmr: number;
    sexo: string;
    dataNascimento: Date;
    mismatch: string;
    abo: string;
    dataAtualizacao: Date;
    peso: number;
    tipoDoador: TiposDoador;
    idRegistro: string;
    idBscup: String;
    fase: string;
    genotipoDoador:GenotipoDTO[];
    somaQualificacao: number;
    ordenacaoWmdaMatch: number;

    toJSON(): any;

}


import { TiposColuna, TipoColuna } from './../enums/tipos.coluna';
import { CordaoInternacional } from '../../doador/cordao.internacional';
import { CordaoNacional } from '../../doador/cordao.nacional';
import { DoadorInternacional } from '../../doador/doador.internacional';
import { TiposDoador } from '../enums/tipos.doador';
import { DoadorNacional } from '../../doador/doador.nacional';
import { DateMoment } from './date/date.moment';

export class ConvertUtil {

    public static parseJsonParaAtributos(valorJson: any, tipo: any): any {
        if (valorJson != null) {
            if (tipo instanceof TipoColuna) {
                if (tipo.codigo == TiposColuna.NUMBER.valueOf()) {
                    return <number>valorJson;    
                }
                else if (tipo.codigo == TiposColuna.DATE.valueOf()) {
                    return DateMoment.getInstance().parse(valorJson);
                }
                else if (tipo.codigo == TiposColuna.STRING.valueOf()) {
                    return <string>valorJson;    
                }
                else  if (tipo.codigo == TiposColuna.BOOLEAN.valueOf()) {
                    return <boolean>valorJson;
                }
            }
            else if (tipo instanceof Number) {
                return <number>valorJson;
            } else if (tipo instanceof Date) {
                return DateMoment.getInstance().parse(valorJson);
            } else if (tipo instanceof String) {
                return <string>valorJson;
            } else if (tipo instanceof Boolean) {
                return <boolean>valorJson;
            }
        }
    }

    public static parseJsonDoadorParaDoador(valorJson: any): any {
        if (valorJson ) {
            if (valorJson.tipoDoador) {
                if (valorJson.tipoDoador == TiposDoador[TiposDoador.NACIONAL]) {
                    return new DoadorNacional().jsonToEntity(valorJson);
                }
                else if (valorJson.tipoDoador == TiposDoador[TiposDoador.INTERNACIONAL]) {
                    return new DoadorInternacional().jsonToEntity(valorJson);
                }
                else if (valorJson.tipoDoador == TiposDoador[TiposDoador.CORDAO_NACIONAL]) {
                    return new CordaoNacional().jsonToEntity(valorJson);
                }
                else if (valorJson.tipoDoador == TiposDoador[TiposDoador.CORDAO_INTERNACIONAL]) {
                    return new CordaoInternacional().jsonToEntity(valorJson);
                }
            }else if(valorJson.dmr){
                return new DoadorNacional().jsonToEntity(valorJson);
            }
            else {
                throw new Error("� obrigat�rio receber o tipo do doador.");
            }


        }
    }


}
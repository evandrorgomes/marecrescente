import { PacienteUtil } from './../paciente.util';
import { TiposDoador } from "../enums/tipos.doador";
import { Doador } from "../../doador/doador";
import { DoadorNacional } from "../../doador/doador.nacional";
import { CordaoNacional } from "../../doador/cordao.nacional";
import { Paciente } from "app/paciente/paciente";
import { CordaoInternacional } from 'app/doador/cordao.internacional';

export class DoadorUtil{

    /**
     * Identifica o doador de acordo com seu tipo.
     * Para medula nacional é exibido nome, para cordão nacional o banco de cordão,
     * para o cordão e medula internacional o registro de origem.
     * @param tarefaLogistica DTO que recebe o que será exibido na lista.
     * @param doador Doador que deverá preencher informações do DTO de acordo com seu tipo.
     *
     * @return retornar o nome que identifica o doador (nome, banco, registro, etc).
     */
    public static obterNomeIdentificacaoDoador(doador: Doador): IdentificacaoDoador{
        const doadorIdentificado: IdentificacaoDoador = new IdentificacaoDoador();
        doadorIdentificado.id = doador.id;

        switch (doador.idTipoDoador) {
            case TiposDoador.NACIONAL:
                doadorIdentificado.idChave = (<DoadorNacional> doador).identificacao ?
                    (<DoadorNacional> doador).identificacao.toString()
                    : doadorIdentificado.idChave = (<DoadorNacional> doador).dmr.toString();
                doadorIdentificado.nome = (<DoadorNacional> doador).nome;
                break;

            case TiposDoador.CORDAO_NACIONAL:
                doadorIdentificado.idChave = (<CordaoNacional> doador).idBancoSangueCordao;
                doadorIdentificado.nome = (<CordaoNacional> doador).bancoSangueCordao.nome;
                break;

            case TiposDoador.INTERNACIONAL:
                // FIXME: O ID do registro não está no Doador, mesmo entidade que está o Registro.
                doadorIdentificado.idChave = doador['idRegistro'];
                doadorIdentificado.nome = doador.registroOrigem.nome;
                break;

            case TiposDoador.CORDAO_INTERNACIONAL:
                // FIXME: O ID do registro não está no Doador, mesmo entidade que está o Registro.
                doadorIdentificado.idChave = (<CordaoInternacional> doador).identificacao;
                doadorIdentificado.nome = (<CordaoInternacional> doador).registroOrigem.nome;
                break;

            default:
                throw new Error('Tipo de doador ' + doador.idTipoDoador + ' é desconhecido.');
        }

        return doadorIdentificado;
    }

    /**
     * @description Se o doador selecionado é medula.
     * Serve para exibir ou não algumas informações, como a fonte de célula.
     * @author Pizão
     * @returns {boolean}
     */
    public static isMedula(doador: Doador): boolean {
        return doador.idTipoDoador === TiposDoador.NACIONAL || doador.idTipoDoador === TiposDoador.INTERNACIONAL;
    }

    /**
     * @description Se o doador selecionado é cordão.
     * @author Pizão
     * @returns {boolean}
     */
    public isCordao(doador: Doador): boolean{
        return !DoadorUtil.isMedula(doador);
    }

    // tslint:disable-next-line:member-ordering
    public static obterCPFSemMascara(_cpfComMascara: any): string {
        return PacienteUtil.obterCPFSemMascara(_cpfComMascara);
    }

}

export class IdentificacaoDoador {
    // ID interno do banco.
    public id: number;
    // ID de apresentação do doador.
    public idChave: string;
    // Nome do doador.
    public nome: string;
}

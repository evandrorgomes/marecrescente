import { ResultadoWorkup } from "../../../../doador/cadastro/resultadoworkup/resultado/resultado.workup";

export class VisualizarResultadoWorkupDataEvent {


    public carregarResultadoWorkup: (resultadoWorkup: ResultadoWorkup, dmr?: string) => void;
    public existePedidosAdicionais: () => boolean;

}

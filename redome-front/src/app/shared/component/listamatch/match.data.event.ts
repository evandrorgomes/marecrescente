import { MatchDTO } from "./match.dto";

export class MatchDataEvent {

    public adicionarFavoritos: (match: MatchDTO) => Boolean;
    public removerFavoritos: (match: MatchDTO) => void;
    public comentar: (match: MatchDTO) => void;
    public comparar: (match: MatchDTO) => void;
    public verificarDivergencia: (match: MatchDTO) => void;
    public visualizarRessalva: (match: MatchDTO) => void;
    public solicitarFase2: (match: MatchDTO) => Promise<any>;
    public solicitarFase3: (match: MatchDTO) => Promise<any>;
    public editarPedidoExame: (match: MatchDTO) => Promise<any>;
    public cadastrarResultado: (match: MatchDTO) => void;
    public cancelar: (match: MatchDTO) => Promise<any>;
    public disponibilizar: (match: MatchDTO) => void;
    public preescrever: (match: MatchDTO) => void;
    public atualizarChecklist: (idChecklist: number) => void;

}
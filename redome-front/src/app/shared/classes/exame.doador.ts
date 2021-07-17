import { Exame } from "app/paciente/cadastro/exame/exame.";
import { Doador } from "app/doador/doador";

export abstract class ExameDoador extends Exame {
    

    protected _doador: Doador;


}
import {Locus} from "../../paciente/cadastro/exame/locus";
import {BaseEntidade} from "../base.entidade";

export class PedidoDto extends BaseEntidade{

   private _tipoExame: number;
   private _locus: Locus[];

   constructor(tipoExame: number, locus?: Locus[]) {
      super();
      this._tipoExame = tipoExame;
      this._locus = locus;
   }

   get tipoExame(): number {
      return this._tipoExame;
   }

   set tipoExame(value: number) {
      this._tipoExame = value;
   }

   get locus(): Locus[] {
      return this._locus;
   }

   set locus(value: Locus[]) {
      this._locus = value;
   }

   jsonToEntity(res: any): PedidoDto {
      return null;
   }
}

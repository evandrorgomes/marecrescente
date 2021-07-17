import { AnaliseMatchDTO } from './../analise.match.dto';
import {OnInit, Component, ChangeDetectorRef} from "@angular/core";
import { Modal } from '../../../../shared/modal/factory/modal.factory';
import { MessageBox } from '../../../../shared/modal/message.box';
import {Recursos} from "../../../../shared/enums/recursos";
import {AutenticacaoService} from "../../../../shared/autenticacao/autenticacao.service";
import {RecusarCentroTransplanteModal} from "../../centroTransplante/recusarTransplante/recusar.centro.transplante.modal";

@Component({
    selector: 'sub-header-paciente',
    templateUrl: './subheader.paciente.component.html'
})
export class SubHeaderPacienteComponent implements OnInit {

    private _analiseMatchDTO: AnaliseMatchDTO;

    public temRecursoRecusarCT: boolean = false;

    constructor( private messageBox: MessageBox, private autenticacaoService: AutenticacaoService, private cdr: ChangeDetectorRef){

    }

    ngOnInit(): void {

    }

    /**
     * Getter analiseMatchDTO
     * @return {AnaliseMatchDTO}
     */
	public get analiseMatchDTO(): AnaliseMatchDTO {
		return this._analiseMatchDTO;
	}

    /**
     * Setter analiseMatchDTO
     * @param {AnaliseMatchDTO} value
     */
	public set analiseMatchDTO(value: AnaliseMatchDTO) {
		this._analiseMatchDTO = value;
       this.temRecursoRecusarCT = this.autenticacaoService.temRecurso(Recursos.RECUSAR_CT_BUSCA) &&
          this.verificarCentroTransplanteUsuarioComABusca();
		this.cdr.detectChanges();

	}

    public exibirResultadoAnticorpo(){
        let modal: Modal = this.messageBox.alert(this._analiseMatchDTO.resultadoExameAnticorpo);
        modal.show();
    }

   private verificarCentroTransplanteUsuarioComABusca(): boolean {
      if (this._analiseMatchDTO && this._analiseMatchDTO.centroTransplanteConfirmado && this.autenticacaoService.usuarioLogado().centros && this.autenticacaoService.usuarioLogado().centros.length > 0) {
         let achouCentro: boolean = false;
         this.autenticacaoService
            .usuarioLogado().centros
            .forEach(centro => {
               if (centro.id == this._analiseMatchDTO.centroTransplanteConfirmado.id) {
                  achouCentro = true;
               }
            });
         return achouCentro;
      }
      return false;

   }

   /**
    * Método invocado quando o medico transplantador decide que o centro
    * não será mais o responsável pelo transplante do paciente.
    */
   public recusarTransplante(): void{

      const data = {
         rmr: this._analiseMatchDTO.rmr,
         atualizarHeader: () => {
            this.analiseMatchDTO.centroTransplanteConfirmado = null;
         }
      }

      let modal: Modal =
         this.messageBox.dynamic(data, RecusarCentroTransplanteModal);
      modal.target = this;
      modal.show();
   }

}

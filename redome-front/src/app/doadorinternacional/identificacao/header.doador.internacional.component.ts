import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { TranslateService } from '@ngx-translate/core';
import { DoadorService } from '../../doador/doador.service';
import { PacienteUtil } from '../../shared/paciente.util';
import { DoadorInternacional } from './../../doador/doador.internacional';


/**
 * Classe de cabeçalho para Doador Internacional.
 * @author Filipe Paes
 */
@Component({
    selector: 'header-doador-internacional',
    templateUrl: './header.doador.internacional.component.html',
    styleUrls: ['./header.doador.internacional.component.css'],
    encapsulation: ViewEncapsulation.Emulated

})
export class HeaderDoadorInternacionalComponent implements OnInit {
    private _doador: DoadorInternacional;
    private labelsInternacionalizadas;
    sexo: string;
    idade: string;
    cid: string;
    peso:string;
    rmrSelecionado:string;


    constructor(private translate: TranslateService,
          private doadorService: DoadorService) {}

    ngOnInit(): void {
        
    }

    popularCabecalho(idDoador: number) {
        this.translate.get("pacienteForm.evolucaoGroup").subscribe(res => {
            this.labelsInternacionalizadas = res;
            this.doadorService.obterDoadorInternacional(idDoador).then(res=>{
                this.popularCabecalhoIdentificacao(res);
            });
        });
    }
    /**
     * Metodo que recebe o paciente e prepara os dados para serem exibidas no html
     * 
     * @param {Paciente} paciente 
     * @memberof HeaderPacienteComponent
     */
    private popularCabecalhoIdentificacao(doador: DoadorInternacional) {
        this._doador = doador;
        this.sexo = this.criarGeneroDecorator(doador.sexo);
        if(this._doador.idade){
            this._doador.idade = this._doador.idade;
        }
        else{
            PacienteUtil.retornarIdadeFormatada(doador.dataNascimento, this.translate).then(res => {
                this.idade = res;
            })
        }
        this.translate.get("doadorForm.vinculo").subscribe(res => {
            this.rmrSelecionado = res + ":" + this._doador.rmrAssociado;
        });
        PacienteUtil.retornarPesoFormatado( this._doador.peso, this.translate).then(res => {
            this.peso = res;
        });
    }

    

    private criarGeneroDecorator(sexo: string): string {
        return (sexo === "M") ? this.labelsInternacionalizadas['homem'] : this.labelsInternacionalizadas['mulher'];
    }

	public get doador(): DoadorInternacional {
		return this._doador;
    }
}
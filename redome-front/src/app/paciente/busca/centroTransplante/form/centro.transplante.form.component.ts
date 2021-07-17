import { Input, ViewChild, Component } from "@angular/core";
import { CentroTransplante } from "../../../../shared/dominio/centro.transplante";
import { TranslateService } from "@ngx-translate/core";
import { ErroMensagem } from "../../../../shared/erromensagem";
import { FormGroup, FormBuilder, Validators, FormControl } from "@angular/forms";
import { ContatoCentroTransplantadorDTO } from "../contato.centro.transplantador.dto";
import { BaseForm } from "../../../../shared/base.form";
import { DetalheAvaliacaoPacienteDTO } from "../detalhe.avaliacao.paciente.dto";
import { CentroTransplanteService } from "../../../../admin/centrotransplante/centrotransplante.service";
import { PacienteService } from "../../../paciente.service";
import { CampoMensagem } from "../../../../shared/campo.mensagem";

@Component({
    selector: 'centro-transplante-form',
    templateUrl: './centro.transplante.form.component.html'
})
export class CentroTransplanteFormComponent extends BaseForm<DetalheAvaliacaoPacienteDTO>{
    
    @Input()
    public rmr: number;

    public detalhe: DetalheAvaliacaoPacienteDTO;
    public centrosTransplante: ContatoCentroTransplantadorDTO[];
    public centroSelecionado: ContatoCentroTransplantadorDTO;

    @ViewChild("modalErro")
    public modalError: any;

    public centroTransplanteGroup: FormGroup;
    public textoEndereco: string;
    public textoContato: string;
    private mensagens: any;


    constructor(private centroTransplanteService: CentroTransplanteService, 
        private pacienteService: PacienteService, 
        translate: TranslateService, formBuilder: FormBuilder) {
        
        super();
        this.translate = translate;

        this.centroTransplanteGroup = formBuilder.group({
            'novoCentroTransplante': [null, Validators.required]
        });

        this.translate.get("confirmarCentroTransplante").subscribe(res => {
            this.mensagens = res;
        });
    }

    ngOnInit(): void {}

    public carregarDados(rmr: number): Promise<any> {
        return this.pacienteService.obterDetalheAvaliacaoPaciente(rmr)
            .then(res => {
                this.rmr = rmr;
                this.detalhe = res;
                this.centrosTransplante = res.contatosPorCentroTransplante;
            });
    }

    public obterCentroSelecionado(): ContatoCentroTransplantadorDTO{
        if(this.centrosTransplante == null || this.centrosTransplante.length == 0){
            return null;
        }
        let centroTransplanteControl: FormControl = 
                this.centroTransplanteGroup.get("novoCentroTransplante") as FormControl;
        let centroId:number = centroTransplanteControl.value;
        let contatoCentro: ContatoCentroTransplantadorDTO = null;
        
        for (let i: number = 0; i < this.centrosTransplante.length; i++){
            if(this.centrosTransplante[i].id == centroId){
                contatoCentro = this.centrosTransplante[i];
                break;
            }
        }

        return contatoCentro;
    }

    private exibirMensagemErro(error: ErroMensagem) {
        this.modalError.mensagem = error.mensagem;
        this.modalError.abrirModal();
    }

    private exibirMensagemSucesso(campoMensagem: CampoMensagem) {
        this.modalError.mensagem = campoMensagem.mensagem;
        this.modalError.abrirModal();
    }

    public exibirEnderecoCentroTransplante(): void{
        if(this.centroSelecionado == null || this.centroSelecionado.tipoLogradouro == null){
            this.textoEndereco = this.mensagens.centroSemEndereco;
        }
        else {
            let contato: ContatoCentroTransplantadorDTO = this.centroSelecionado;
            let enderecoDescrito: string = 
                contato.tipoLogradouro + " " + contato.nomeLogradouro + " - " +
                        contato.bairro + ", " + contato.municipio + ", " + contato.uf + "\n";

            this.textoEndereco = enderecoDescrito;
        }
    }

    public exibirContatoCentroTransplante(): void{
        if(this.centroSelecionado == null || this.centroSelecionado.numero == null){
            this.textoContato = this.mensagens.centroSemContato;
        }
        else {
            let contato: ContatoCentroTransplantadorDTO = this.centroSelecionado;
            let contatoDescrito: string = 
                "+" + contato.codigoInternacional + 
                    " (" + contato.codigoArea + ") " + contato.numero;

            this.textoContato = contatoDescrito;
        }
    }

    public selecionarCentroTransplantador(): void{
        this.centroSelecionado = this.obterCentroSelecionado();
        this.exibirEnderecoCentroTransplante();
        this.exibirContatoCentroTransplante();
    }

    public form(): FormGroup {
        return this.centroTransplanteGroup;
    }
    public preencherFormulario(entidade: DetalheAvaliacaoPacienteDTO): void {
        throw new Error("Method not implemented.");
    }
    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }

}
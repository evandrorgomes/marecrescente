import { ComponentFixture, TestBed, async } from "@angular/core/testing";
import { AppComponent } from "../../../../../app.component";
import { DetalheCancelarAgendamentoPedidoColetaComponent } from "./detalhe.cancelar.agendamento.component";
import { MockTarefaService } from "../../../../../shared/mock/mock.tarefa.service";
import { MockPedidoColetaService } from "../../../../../shared/mock/mock.pedido.coleta.service";
import { MockActivatedRoute } from "../../../../../shared/mock/mock.activated.route";
import { MockDoadorService } from "../../../../../shared/mock/mock.doador.service";
import { AppModule } from "../../../../../app.module";
import { TarefaService } from "../../../../../shared/tarefa.service";
import { PedidoColetaService } from "../../pedido.coleta.service";
import { ActivatedRoute } from "@angular/router";
import { DoadorService } from "../../../../doador.service";
import { AutenticacaoService } from "../../../../../shared/autenticacao/autenticacao.service";
import { MockAutenticacaoService } from "../../../../../shared/mock/mock.autenticacao.service";
import { Observable } from "rxjs";
import { PedidoColeta } from "../../pedido.coleta";
import { PedidoWorkup } from "../../../workup/pedido.workup";
import { CentroTransplante } from "../../../../../shared/dominio/centro.transplante";
import { Solicitacao } from "../../../../solicitacao/solicitacao";
import { Prescricao } from "../../../workup/prescricao";
import { FonteCelula } from "../../../../../shared/dominio/fonte.celula";
import { DateMoment } from "../../../../../shared/util/date/date.moment";
import { MockWorkupService } from "../../../../../shared/mock/mock.workupservice";
import { WorkupService } from "../../../workup/workup.service";
import { FontesCelulas } from "../../../../../shared/enums/fontes.celulas";
import { activatedRoute, workupService, doadorService } from "../../../../../export.mock.spec";
import { Doador } from "../../../../doador";
import { DoadorNacional } from "../../../../doador.nacional";

describe('DetalheCancelarAgendamentoPedidoColetaComponent', () => {
    let fixtureApp: ComponentFixture<AppComponent>;
    let app: AppComponent;
    let fixture: ComponentFixture<DetalheCancelarAgendamentoPedidoColetaComponent>;
    let component: DetalheCancelarAgendamentoPedidoColetaComponent
 
    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;

    const createComponent = () => {        
        fixture = TestBed.createComponent(DetalheCancelarAgendamentoPedidoColetaComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();        
    };
    
    beforeEach(() => { 
        activatedRoute.testQueryParams = {'processoId': 1, 'tarefaId': 1, 'tipo': 56};
        activatedRoute.testParams = {'pedidoId': 1};

        workupService.obterPedidoWorkup = function (idPedido: number): Promise<any> {

            let pedidoWorkup: PedidoWorkup = new PedidoWorkup();
            pedidoWorkup.id = 1;
            pedidoWorkup.centroColeta = new CentroTransplante();
            pedidoWorkup.centroColeta.id = 1;
            pedidoWorkup.centroColeta.nome = 'HOSPITAL DAS CLÍNICAS DE PORTO ALEGRE - UFRGS';
            pedidoWorkup.dataColeta = new Date();
            pedidoWorkup.dataColeta.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            pedidoWorkup.dataPrevistaDisponibilidadeDoador = new Date();
            pedidoWorkup.dataPrevistaDisponibilidadeDoador.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            pedidoWorkup.dataPrevistaLiberacaoDoador = new Date();
            pedidoWorkup.dataPrevistaLiberacaoDoador.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }

            let solicitacao: Solicitacao = new Solicitacao();
            solicitacao.doador = new DoadorNacional();
            solicitacao.doador.id = 1;
            (<DoadorNacional>solicitacao.doador).dmr = 1;

            pedidoWorkup.solicitacao = solicitacao;            
            pedidoWorkup.fonteCelula = new FonteCelula(1, "MO", "MEDULA OSSEA");
            
            let res = JSON.parse(JSON.stringify(pedidoWorkup));

            return Observable.of(res).toPromise();
        }

        doadorService.obterIdentificacaoDoadorPorId = function (id: number) {
            let retorno = {
                "id": id,
                "dmr": 1,
                "nome": "teste",
                "sexo": "M",
                "statusDoador": {
                    "descricao": "Ativo",
                    "id": 1
                },
                "dataNascimento": "1975-02-01",
                "tipoDoador": "0"
            }
            return Observable.of(retorno).toPromise();
        }

        createComponent(); 
    });

    afterAll(() => {
        fixtureApp = undefined;
        app = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente foi instanciado com sucesso
     * @author Bruno Sousa
     */
    it('[SUCESSO] deveria criar o component DetalheCancelarAgendamentoPedidoColetaComponent', () => {        
        expect(component).toBeDefined();
    });

});
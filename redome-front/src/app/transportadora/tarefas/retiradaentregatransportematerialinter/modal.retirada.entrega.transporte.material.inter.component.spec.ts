import { ComponentFixture, TestBed } from "@angular/core/testing";
import { TiposTarefa } from "../../../shared/enums/tipos.tarefa";
import { DateMoment } from "../../../shared/util/date/date.moment";
import { PedidoTransporteDTO } from "../pedido.transporte.dto";
import { StatusPedidoTransporte } from "../status.pedido.transporte";
import {  ModalRetiradaEntregaTransporteMaterialInternacionalComponent } from "./modal.retirada.entrega.transporte.material.inter.component";

describe('ModalRetiradaEntregaTransporteMaterialInternacionalComponent', () => {
    let fixture: ComponentFixture<ModalRetiradaEntregaTransporteMaterialInternacionalComponent>;
    let component: ModalRetiradaEntregaTransporteMaterialInternacionalComponent
    
    beforeEach(() => {
        fixture = TestBed.createComponent(ModalRetiradaEntregaTransporteMaterialInternacionalComponent);
        component = fixture.debugElement.componentInstance;
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    it('deveria criar o ModalRetiradaEntregaTransporteMaterialComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deve validar a data de retirada com a data prevista de retirada', (done) => (async () => {

        let pedidoTransporteDTO: PedidoTransporteDTO = new PedidoTransporteDTO();
        pedidoTransporteDTO.idPedidoTransporte = 1;
        pedidoTransporteDTO.idTarefa = 1;
        pedidoTransporteDTO.tipoTarefa = TiposTarefa.RETIRADA_MATERIAL_INTERNACIONAL;
        
        pedidoTransporteDTO.horaPrevistaRetirada = DateMoment.getInstance().parse("2018-04-27T15:00:00.00");

        pedidoTransporteDTO.identificacao = '1';
        pedidoTransporteDTO.rmr = 1;
        pedidoTransporteDTO.nomeCentroTransplante = "nome do centro de transplante";
        pedidoTransporteDTO.nomeLocalRetirada = "nome do centro de controle";
        
        pedidoTransporteDTO.status = new StatusPedidoTransporte();
        pedidoTransporteDTO.status.descricao = "Aguardando Retirada";
        
        component.data = {
            '_pedidoTransporteDTOSelecionado': pedidoTransporteDTO,
            'fechar': ()=> {

            }
        }
        await component.ngOnInit();        
        component.form().get("dataRetirada").setValue("26/04/2018 15:00");
        component.confirmar();

        expect(component.formErrors["dataRetirada"]).toEqual("Data Retirada deve ser maior ou igual à Data Prevista de Retirada");
        
    })().then(done).catch(done.fail));

    it('deve validar a data de entrega com a data de retirada', (done) => (async () => {
        
        let pedidoTransporteDTO: PedidoTransporteDTO = new PedidoTransporteDTO();
        pedidoTransporteDTO.idPedidoTransporte = 1;
        pedidoTransporteDTO.idTarefa = 1;
        pedidoTransporteDTO.tipoTarefa = TiposTarefa.ENTREGA_MATERIAL_INTERNACIONAL;
        
        pedidoTransporteDTO.horaPrevistaRetirada = DateMoment.getInstance().parse("2018-04-27T15:00:00.00");
        pedidoTransporteDTO.dataRetirada = DateMoment.getInstance().parse("2018-04-27T15:00:00.00");
        
        pedidoTransporteDTO.identificacao = '1';
        pedidoTransporteDTO.rmr = 1;
        pedidoTransporteDTO.nomeCentroTransplante = "nome do centro de transplante";
        pedidoTransporteDTO.nomeLocalRetirada = "nome do centro de controle";
        
        pedidoTransporteDTO.status = new StatusPedidoTransporte();
        pedidoTransporteDTO.status.descricao = "Aguardando Retirada";
        
        component.data = {
            '_pedidoTransporteDTOSelecionado': pedidoTransporteDTO,
            'fechar': ()=> {
            }
        };

        await component.ngOnInit();                
        component.form().get("dataEntrega").setValue("26/04/2018 15:00");
        await component.confirmar();

        expect(component.formErrors["dataEntrega"]).toEqual("Data Entrega deve ser maior ou igual à Data de Retirada");
        
    })().then(done).catch(done.fail)); 
 
});
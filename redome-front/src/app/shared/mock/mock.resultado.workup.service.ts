import { Observable } from 'rxjs';

/**
 * Classe de serviço mocados para interação com a tabela de genotipo
 * 
 * @author Queiroz
 * @export
 * @class MockResultadoWorkupService
 
 */
export class MockResultadoWorkupService  {

    
    carregarDoadorPorResultadoWorkup(dmr: number, idCentro: number, aberto:true): Promise<any> {
        
        return Observable.of(null).toPromise();
    }

    // dmr, idCentro, true)
    //             .then(res => {
    //                 this.headerDoador.clearCache();
    //                 this.resultadoWorkup = new ResultadoWorkup();
    //                 this.resultadoWorkup.id = res.id;
    //                 this.resultadoWorkup.dataCriacao = new Date(res.dataCriacao + 'T00:00:00');
    //                 this.resultadoWorkup.pedidoWorkup = new PedidoWorkup();
    //                 this.resultadoWorkup.pedidoWorkup.id = res.pedidoWorkup.id;
    //                 this.resultadoWorkup.pedidoWorkup.solicitacao = res.pedidoWorkup.solicitacao;
    //                 this.resultadoWorkup.pedidoWorkup.centroColeta = res.pedidoWorkup.centroColeta;
    //                 this.resultadoWorkup.pedidoWorkup.dataLimiteWorkup = res.pedidoWorkup.dataLimiteWorkup;
    //                 let doador: DoadorNacional = <DoadorNacional>this.resultadoWorkup.pedidoWorkup.solicitacao.doador;
    //                 doador.dataNascimento = new Date(doador.dataNascimento + 'T00:00:00');
    //                 let centroColeta: CentroTransplante = this.resultadoWorkup.pedidoWorkup.centroColeta;
    //                 let dataLimite = this.resultadoWorkup.pedidoWorkup.dataLimiteWorkup;
    //                 this.headerDoador.popularCabecalhoIdentificacaoParaCentroDeColeta(doador, centroColeta, dataLimite);
    

}
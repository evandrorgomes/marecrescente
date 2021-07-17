import { Injectable } from '@angular/core';

/**
 * @author Bruno Souza
 * 
 * Classe que armazena o caminho acessado na aplicação, guardando o 
 * passo a passo realizado pelo usuário (bread crumbs).
 */
@Injectable()
export class HistoricoNavegacao {

    private static _caminhos: string[] = [];

    public static adicionarCaminho(caminho: string): void {
        if (HistoricoNavegacao.verificarSeDeveAdicionarUrl(caminho)) {
            let index = HistoricoNavegacao.retornarIndice(caminho);
            if(index != -1){
                HistoricoNavegacao._caminhos = HistoricoNavegacao._caminhos.slice(0, index);
            }
            HistoricoNavegacao._caminhos.push(caminho);
        }
    }

    public static urlRetorno(): string {
        if (HistoricoNavegacao._caminhos.length == 0) {
            return "/home";
        }
        HistoricoNavegacao.removerUltimoAcesso();
        return HistoricoNavegacao._caminhos[HistoricoNavegacao._caminhos.length - 1];
    }

    /**
     * Verifica se a URL informada deve ser adiciona a lista de caminhos ou não.
     * Condições em que deverá ser:
     * 1. Não ter sido registrada na lista.
     * 2. Não ser a última URL da lista (considerando que a navegação pode evoluir .
     *    e a mesma tela ser acessada em dois pontos direrentes do sistema).
     * 
     * @param caminho URL a ser verificada.
     */
    private static verificarSeDeveAdicionarUrl(caminho:string): boolean{
        let index = HistoricoNavegacao.retornarIndice(caminho);
        return (index == -1 || index < HistoricoNavegacao._caminhos.length - 1);
    }

    /**
     * Retorna o índice do caminho na lista de caminhos.
     * 
     * @param caminho URL a ser pesquisada.
     */
    private static retornarIndice(caminho: string): number{
        return HistoricoNavegacao._caminhos.findIndex(c => {
            let temParametroUrl:boolean = caminho.indexOf("?") != -1;
            if(temParametroUrl){
                return (c == caminho.slice(0, caminho.indexOf("?")) || c == caminho);
            }
            return (c == caminho);
        });
    }

    /**
     * Apaga o último acesso no histórico de navegação.
     * Isso é necessário quando uma tela chama a outra e não será
     * mais permitida o retorno a esta tela.
     * Por exemplo: O resultado de CT, ao ser cadastrado, poderá chamar a tela de 
     * Resultado de IDM se esta ainda estiver em aberto, mas esta tela não deverá 
     * retornar para tela de CT, pois ele já foi cadastrado. Deverá voltar para lista
     * de pedidos de exame em aberto, neste caso.
     */
    public static removerUltimoAcesso(): void{
        HistoricoNavegacao._caminhos = HistoricoNavegacao._caminhos.slice(0, HistoricoNavegacao._caminhos.length - 1);
    }

}
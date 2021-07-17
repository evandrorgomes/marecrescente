/**
 * Fornece método facilitadores para lidar com arrays. * 
 */
export class ArrayUtil {

    public static isEmpty(array:any[]): boolean{
        return array == null || array.length == 0;
    }

    public static isNotEmpty(array:any[]): boolean{
        return !ArrayUtil.isEmpty(array);
    }

    /**
     * @description Verifica se o item existe na lista informada.
     * Se a propriedade for informada, ela será considerada como chave para identificar
     * o item como encontrado ou não.
     * @author Pizão
     * @static
     * @template T
     * @param {T[]} array lista a ser procurada.
     * @param {T} item item a ser procurado na lista.
     * @param {string} [property=null] propriedade chave do item.
     * @returns {boolean} TRUE se o item for encontrado.
     */
    public static contains<T>(array: T[], item: T, property: string = null): boolean{
        let locusEncontrado: boolean = false;

        if(ArrayUtil.isNotEmpty(array)){
            array.forEach(itemArray => {
                let encontrado: boolean = false;

                if(property == null){
                    encontrado = itemArray == item;
                }
                else {
                    encontrado = itemArray[property] == item[property];
                }
                
                if(encontrado){
                    locusEncontrado = true;
                }
            });
        }
        return locusEncontrado;
    }

    /**
     * Adiciona todos os elementos do array no mesmo texto utilizando o separador entre eles.
     * Se o último separador for informado, ele será utilizado para separar o último elemento
     * dos demais na string final.
     * 
     * @param array array a ser agrupado no texto.
     * @param separator separador comum a todos os itens.
     * @param lastSeparator separador do último item.
     */
    public static join(array: any[], separator: string, lastSeparator?: string): string {
        if(array.length == 1){
            return array[0];
        }

        if(lastSeparator == null){
            return array.join(separator);
        }
        let copyArray: any[] = Array.from(array);
        let lastItem: any = copyArray.pop();
        return (copyArray.join(separator) + lastSeparator + lastItem);
    }

    /**
     * Retorna um array com os valores sem repetição.
     * 
     * @param array arrays a ser manipulado.
     * @return array com os valores únicos.
     */
    public static distinct(array: any[]): any[]{
        return Array.from(new Set(array));
    }

    /**
     * Remove o valor da lista passada no parâmetro.
     * 
     * @param array lista de valores.
     * @param value valor a ser removido.
     */
    public static remove<T>(array: T[], value: T): T{
        if(this.isEmpty(array) || value == null){
            return null;
        }
        let deleteValues: T[] = array.splice(array.indexOf(value), 1);
        return deleteValues[0];
    }

    /**
     * Remove o valor da lista passada no parâmetro.
     * 
     * @param array lista de valores.
     * @param value valor a ser removido.
     */
    public static removeByProperty<T>(array: T[], value: any, property: string): T{
        if(this.isEmpty(array) || value == null){
            return null;
        }
        let removeIndex: number = array.findIndex(item => {
            return (item[property] == value);
        });
        let deleteValues: T[] = array.splice(removeIndex, 1);
        return deleteValues[0];
    }

    /**
     * @description Pesquisa se o item está na lista, ambos informados como parâmetro.
     * @author Pizão
     * @static
     * @template T
     * @param {T[]} array lista a ser pesquisada.
     * @param {string} property propriedade a ser considerada como chave, para dizer se o item está contido ou não.
     * @param {any} value valor da propriedade que deve ser buscada na lista.
     * @returns {T} o item encontrado ou nulo, caso não esteja contido na lista.
     */
    public static get<T>(array: T[], property: string, value: any): T{
        let encontrado: T = null;

        if(ArrayUtil.isNotEmpty(array)){
            array.forEach(itemArray => {
                let itemEncontrado: T = null;

                let hasProperty: boolean = itemArray.hasOwnProperty(property);
                itemEncontrado = (hasProperty && itemArray[property] == value) ? itemArray : null;
                
                if(itemEncontrado){
                    encontrado = itemEncontrado;
                }
            });
        }
        return encontrado;
    }

    /**
     * @description Realiza a ordenação do array de acordo com o
     * parâmetro e ordem informadas.
     * @author Pizão
     * @static
     * @template T
     * @param {T[]} array
     * @param {string} property
     * @param {OrderSort} [orderSort=OrderSort.ASC]
     */
    public static sort<T>(array: T[], property: string, orderSort: OrderSort = OrderSort.ASC): void{
        if(this.isNotEmpty(array)){
            array.sort((left, right) => {
                if(left[property] > right[property]){
                    return 1;
                }
                else if(left[property] < right[property]){
                    return -1;
                }
                return 0;
            });
        }
    }

    /**
     * @description Clona a lista passada em uma nova.
     * @author Pizão
     * @static
     * @template T
     * @param {T[]} array
     * @returns {T[]}
     */
    public static clone<T>(array: T[]): T[]{
        if(this.isEmpty(array)){
            return null;
        }
        return array.map(item => item);
    }

    /**
     * @description Filtra somente com um resultado, obrigatoriamente.
     * @author Pizão
     * @static
     * @template T
     * @param {T[]} array
     * @param {string} property
     * @param {*} value
     * @returns {T}
     */
    public static filterOne<T>(array: T[], property: string, value: any): T{
        if(this.isEmpty(array)){
            return null;
        }

        let selected: T[] = array.filter(item => item[property] == value);
        if(this.isNotEmpty(selected)){
            if(selected.length > 1){
                throw new Error("Filtro por resultado único falho trazendo " + selected.length + " resultados.");
            }
            return selected[0];
        }
        return null;
    }

    /**
     * @description Força que a lista, após ser filtrada, volte a considerar todos os itens possíveis.
     * @author Pizão
     * @static
     * @template T
     * @param {T[]} array
     * @returns {T[]}
     */
    public static clearFilter<T>(array: T[]): T[]{
        if(this.isEmpty(array)){
            return null;
        }
        return array.filter(item => {return item});
    }
}

/**
 * @description Define a ordenação para o método
 * sort.
 * @enum {number}
 */
enum OrderSort {
    DESC, ASC
}
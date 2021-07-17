/**
 * @description Classe que guardar as operações matemáticas
 * utilizadas no front-end do projeto.
 * 
 * @author Pizão
 * @export
 * @class MathUtils
 */
export class MathUtils{

    /**
     * Transforma o valor passado como parâmetro em positivo.
     * @param valor valor a ser transformado.
     * 
     * @return valor positivo.
     */
    public static positivo(valor: number): number {
        return (valor < 0) ? (valor * -1) : valor;
    }

}
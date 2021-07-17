export class NumberUtil {
    /**
     * Arredonda o valor para precisão informada.
     * 
     * @param value 
     * @param precision 
     */
    static round(value: number, precision: number): number{
        var factor = Math.pow(10, precision);
        var tempNumber = value * factor;
        var roundedTempNumber = Math.round(tempNumber);
        return roundedTempNumber / factor;
    }
}
/**
 * Formatações possíveis para a data.
 * @author Pizão
 */
export class DateTypeFormats {

    public static DATE_TIME: DateTypeFormats = new DateTypeFormats("L LT");
    public static DATE_ONLY: DateTypeFormats = new DateTypeFormats("L");
    public static TIME_ONLY: DateTypeFormats = new DateTypeFormats("LT");

    private _pattern: string;

    private constructor(pattern: string){
        this._pattern = pattern;
    }

    
    public toString(): string{
        return this._pattern;
    }

    public get pattern(): string{
        return this._pattern;
    }

}
import { OnInit, Type } from "@angular/core";

export class ParserJson {
    
    private parsers: any[] = [];

    constructor() {
        this.parsers.push(this.parserDate);
    }

    public parser(json:any):Object{
        let jsonParsed:any = new Object(json);
        if(json){
            json.forEach(atributo => {
                jsonParsed[atributo] = this.parserDate(atributo);
            });
        }
        return jsonParsed;
    }

    private parserDate(value):Date{
        if(this.isDateValido(value)){
            try{
                let dt = /^(\d{4})-(\d{2})-(\d{2})$/.exec(value);
                if (dt) {
                    return new Date(Date.UTC(+dt[1], +dt[2] - 1, +dt[3]));
                }
            } catch(error){
                console.error("Formato inesperado para data: " + value + "\n" + error);
            }
        }
        return value;
    }

    private isDateValido(value:string):boolean{
        return !isNaN(Date.parse(value));
    }

}
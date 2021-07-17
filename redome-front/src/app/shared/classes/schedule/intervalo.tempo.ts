import {RecurrenceRule, Range} from 'node-schedule';

export class IntervaloTempo extends RecurrenceRule{
    constructor(segundos:number){
        super();
        this.second = new Range(0, 59, segundos);
    }
}
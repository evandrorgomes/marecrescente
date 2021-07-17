import { IEvent } from "./i.event";

export abstract class AbstractEvent implements IEvent{
    private _key: string;
    public thisReference: any;

    protected constructor(key: string){
        this._key = key;
    }

    public get key(): string{
        return this._key;
    }
}
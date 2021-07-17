import { Injector, Type } from '@angular/core';

export class Injetor { 
  /**
     * Allows for retrieving singletons using `AppModule.injector.get(MyService)`
     * This is good to prevent injecting the service as constructor parameter.
     */
    static instance: Injector;
    constructor(instance: Injector) {
        Injetor.instance = instance;
    }

    public static get(clazz:Type<any>):any{
        return Injetor.instance.get(clazz);
    }
}
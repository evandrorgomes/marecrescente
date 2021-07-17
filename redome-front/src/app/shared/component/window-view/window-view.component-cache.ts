import { Type, ComponentFactory } from '@angular/core';

export class WindowViewComponentCache extends Map<Type<any>, ComponentFactory<any>> {
    private static instance:any;
    private static isCreating:Boolean = false;

    constructor() {
        if (!WindowViewComponentCache.isCreating) {
            throw new Error("You can't call new in Singleton instances! Call WindowViewComponentCache.getInstance() instead.");
        }
        super();
    }

    public static getInstance(): WindowViewComponentCache {
        if (WindowViewComponentCache.instance == null) {
            WindowViewComponentCache.isCreating = true;
            WindowViewComponentCache.instance = new Map();
            WindowViewComponentCache.instance['__proto__'] = WindowViewComponentCache.prototype;
            WindowViewComponentCache.isCreating = false;
        }

        return WindowViewComponentCache.instance as WindowViewComponentCache;
    }
}
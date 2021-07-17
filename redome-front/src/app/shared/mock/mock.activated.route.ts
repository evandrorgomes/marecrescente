import { ParamMap } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

export class MockActivatedRoute {
    private queryParamsSubject = new BehaviorSubject(this.testQueryParams);
    private _testQueryParams: {}; 

    private queryParams  = this.queryParamsSubject.asObservable();

	public get testQueryParams() {
		return this._testQueryParams;
    }
    
	public set testQueryParams(value: any) {
        this._testQueryParams = value;
        this.queryParamsSubject.next(value);
	}
    
    private paramsSubject = new BehaviorSubject(this.testParams);
    private _testParams: {};

    private params  = this.paramsSubject.asObservable();

    public get testParams() {
        return this._testParams;
    }
    public set testParams(newParams: any) {
        this._testParams = newParams;
        this.paramsSubject.next(newParams);
    }

}
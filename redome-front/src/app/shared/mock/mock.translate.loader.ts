import * as pt from 'assets/i18n/pt.json';
import * as en from 'assets/i18n/en.json';
import * as es from 'assets/i18n/es.json';

import * as assert from 'assert';
import { TranslateLoader } from '@ngx-translate/core';
import { Observable } from 'rxjs/Observable';

export class MockTranslateLoader implements TranslateLoader {

    private TRANSLATIONS = {
        PT: pt,
        EN: en,
        ES: es
    };
    
    getTranslation(code: string = ''): Observable<object> {
        const uppercased = code.toUpperCase();

        return Observable.of(this.TRANSLATIONS[uppercased]);
    }
}
 
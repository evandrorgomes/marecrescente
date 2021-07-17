import { browser, element, by } from 'protractor';
import { RedomeFrontEndPage } from './app.po';

//jasmine.DEFAULT_TIMEOUT_INTERVAL = 10000;

describe('redome-front-end App', () => {

  let page: RedomeFrontEndPage;
 
  beforeAll(() => {
    page = new RedomeFrontEndPage();
  });

  it('should redirect to login page', () => {
    page.navigateTo();
    expect(page.getCurrentUrl()).toEqual('http://localhost:49157/redome/login');
  });
});


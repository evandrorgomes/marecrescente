// Protractor configuration file, see link for more information
// https://github.com/angular/protractor/blob/master/lib/config.ts

const { SpecReporter } = require('jasmine-spec-reporter');

exports.config = {
  seleniumAddress: 'http://localhost:4444/wd/hub',
  allScriptsTimeout: 5000000,
  chromeOnly: true,
  directConnect: true,
  //getPageTimeout: 300 * 10000,
  specs: [
    './e2e/paciente/cadastro/paciente.e2e-spec.ts',
    './e2e/paciente/avaliacao/avaliacao.e2e-spec.ts',
    './e2e/paciente/evolucao/nova/nova.evolucao.e2e-spec.ts',
    './e2e/paciente/avaliacao/avaliacao.fecha.pendencia.e2e-spec.ts',
    './e2e/paciente/exames/novo/novo.exame.e2e-spec.ts',
    './e2e/paciente/avaliacao/medico/avaliacao.responde.pendencia.e2e-spec.ts',
    //,'./e2e/**/*.e2e-spec.ts'
  ],
  multiCapabilities: [{
    'browserName': 'chrome'
  }/*, {
    'browserName': 'internet explorer',
    'platform': 'ANY',
    'version': '11',
    'nativeEvents': false,
    'unexpectedAlertBehaviour': 'accept',
    'ignoreProtectedModeSettings': true,
    'enablePersistentHover': true,
    'disable-popup-blocking': true
  }*/],
  directConnect: true,
  baseUrl: 'http://localhost:4200/',
  framework: 'jasmine',
  jasmineNodeOpts: {
    showColors: true,
    defaultTimeoutInterval: 30000,
    print: function() {}
  },
  beforeLaunch: function() {    
  },
  onPrepare() {
    /* este método foi retirado da função acima para compatibilidade do firefox*/
    require('ts-node').register({
      project: 'e2e/tsconfig.e2e.json'
    });
    jasmine.getEnv().addReporter(new SpecReporter({ spec: { displayStacktrace: true } }));
  }
};

import { LOCALE_BR } from "environments/export.locale";

// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.


export const environment = {
  production: false,
  authEndpoint: "http://localhost:9000/redome-auth/",
  endpoint: "http://localhost:9080/redome/api/",
  publicEndpoint: "http://localhost:9080/redome/public/",
  locale: LOCALE_BR,
  recaptcha_v2_sitekey: "6Lfg6m8UAAAAAF52wiLbAHHj2teANbZ_M9-aRdDE",
  tarefaEndpoint: "http://localhost:9001/redome-tarefa/api/",
  notificacaoEndpoint: "http://localhost:9002/redome-notificacao/api/",
  invoiceEndpoint: "http://localhost:9003/redome-invoice/api/",
  workupEndpoint: "http://localhost:9004/redome-workup/api/",
  courierEndpoint: "http://localhost:9005/redome-courier/api/"
};



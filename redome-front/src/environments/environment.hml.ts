import { LOCALE_BR } from "environments/export.locale";

const uri = 'http://10.46.10.23';

export const environment = {
  production: true,
  authEndpoint: `${uri}/redome-auth/`,
  endpoint: `${uri}/redome/api/`,
  publicEndpoint: `${uri}/redome/public/`,
  locale: LOCALE_BR,
  recaptcha_v2_sitekey: "6Lesg9YUAAAAABdcCegHjOU2CoFne-nKjhuld3Ke",
  tarefaEndpoint: `${uri}/redome-tarefa/api/`,
  notificacaoEndpoint: `${uri}/redome-notificacao/api/`,
  invoiceEndpoint: `${uri}/invoice/api/`,
  workupEndpoint: `${uri}/redome-workup/api/`,
  courierEndpoint: `${uri}/redome-courier/api/`

};

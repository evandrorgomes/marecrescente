import { TestBed } from '@angular/core/testing';
import { CadastroComponent } from '../paciente/cadastro/cadastro.component';
import { MockBackend, MockConnection } from '@angular/http/testing';
import {
    Response,
    ResponseOptions,
    ResponseType,
    XHRBackend
} from '@angular/http';

/**
 * Classe para auxiliar as classes de teste
 * @author Fillipe Queiroz
 */
export class HelperTest {

    RES_PAIS = [
        {
            "id": 12,
            "nome": "AFEGANISTAO"
        },
        {
            "id": 2,
            "nome": "AFRICA DO SUL"
        },
        {
            "id": 30,
            "nome": "BOTSWANA"
        },
        {
            "id": 1,
            "nome": "BRASIL"
        }
    ];

    RES_RACA = [
        {
            "id": "1",
            "nome": "BRANCA"
        },
        {
            "id": "2",
            "nome": "PRETA"
        },
    ];

    RES_LOCUS = [
        {
            "codigo": "A"
        },
        {
            "codigo": "B"
        },
        {
            "codigo": "C"
        },
        {
            "codigo": "DRB1"
        },
        {
            "codigo": "DRB3"
        },
        {
            "codigo": "DRB4"
        },
        {
            "codigo": "DRB5"
        },
        {
            "codigo": "DPB1"
        },
        {
            "codigo": "DQA1"
        },
        {
            "codigo": "DQB1"
        }
    ];

    RES_METODOLOGIA = [];

    RES_CONFIGURACAO = [
        {
            chave: 'extensaoArquivoLaudo',
            valor: 'image/png,image/jpeg,image/bmp,image/tiff,image/jpg'
        },
        {
            chave: 'tamanhoArquivoLaudoEmByte',
            valor: '5242880'
        },
        {
            chave: 'qtdArquivosLaudo',
            valor: '5'
        }
    ];

    connection: any;
    
    /**
     * Método para criar o mock para os dominios de dados pessoais
     * @param MockBackend - classe para Criar o mock
     * @author Fillipe Queiroz
     */
    criarMockDominio(backend: MockBackend) {

        backend.connections.subscribe((connection) => {
            this.connection = connection;
            
            if (connection.request.url.endsWith('/pais')) {
                connection.mockRespond(new Response(new ResponseOptions({
                    status: 200,
                    body: this.RES_PAIS
                }), ));
            } else if (connection.request.url.endsWith('/raca')) {
                connection.mockRespond(new Response(new ResponseOptions({
                    status: 200,
                    body: this.RES_RACA
                }), ));
            }else if (connection.request.url.endsWith('/locus')) {
                connection.mockRespond(new Response(new ResponseOptions({
                    status: 200,
                    body: this.RES_LOCUS
                }), ));
            }
            else if (connection.request.url.endsWith('/metodologia')) {
                connection.mockRespond(new Response(new ResponseOptions({
                    status: 200,
                    body: this.RES_METODOLOGIA
                }), ));
            }
            else if (connection.request.url.indexOf('/configuracao') != 0 ) {
                connection.mockRespond(new Response(new ResponseOptions({
                    status: 200,
                    body: this.RES_CONFIGURACAO
                }), ));
            }


        });

    }


}
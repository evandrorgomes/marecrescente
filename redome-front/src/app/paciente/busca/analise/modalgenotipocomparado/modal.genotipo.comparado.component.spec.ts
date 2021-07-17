import { ModalGenotipoComparadoComponent } from './modal.genotipo.comparado.component';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { genotipoService } from '../../../../export.mock.spec';
import { Observable } from 'rxjs';
import {GenotipoComparadoDTO} from "../../../genotipo.comparado.dto";


describe('ModalGenotipoComparadoComponent', () => {
    let fixture: ComponentFixture<ModalGenotipoComparadoComponent>;
    let component: ModalGenotipoComparadoComponent;

    beforeEach(() => {
        fixture = TestBed.createComponent(ModalGenotipoComparadoComponent);
        component = fixture.debugElement.componentInstance;
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de ModalGenotipoComparadoComponent foi instanciado com sucesso
     * @author bruno.sousa
     */
     it('deveria criar o ModalGenotipoComparadoComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deve exibir genotipo doador nacional', (done) => (async () => {

        genotipoService.listarGenotiposComparados = function(rmr: number, listaIdsDoadores: number[]): Promise<any> {
            let res = {
                abo: "B-",
                dataNascimento: "1987-03-31",
                genotipoPaciente: [
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "A",
                        ordem: 1,
                        primeiroAlelo: "01:01",
                        segundoAlelo: "01:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "B",
                        ordem: 2,
                        primeiroAlelo: "13:73",
                        segundoAlelo: "13:73",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "C",
                        ordem: 3,
                        primeiroAlelo: "08:01",
                        segundoAlelo: "08:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "DRB1",
                        ordem: 4,
                        primeiroAlelo: "13:01",
                        segundoAlelo: "13:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "DQB1",
                        ordem: 11,
                        primeiroAlelo: "02:02",
                        segundoAlelo: "02:02",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    }
                ],
                genotiposDoadores: [
                    {
                        abo: "AB+",
                        classificacao: "5/6",
                        dataNascimento: "1986-02-24",
                        dmr: 1,
                        genotipoDoador: [
                            {
                                locus: "A",
                                ordem: 1,
                                primeiroAlelo: "01:01",
                                qualificacaoPrimeiroAlelo: "A",
                                qualificacaoSegundoAlelo: "M",
                                segundoAlelo: "01:01:01",
                                tipoPrimeiroAlelo: 5,
                                tipoSegundoAlelo: 5
                            },
                            {
                                locus: "B",
                                ordem: 2,
                                primeiroAlelo: "38:NOVO",
                                qualificacaoPrimeiroAlelo: "L",
                                qualificacaoSegundoAlelo: "P",
                                segundoAlelo: "18:NOVO",
                                tipoPrimeiroAlelo: 5,
                                tipoSegundoAlelo: 5
                            },
                            {
                                locus: "DRB1",
                                ordem: 4,
                                primeiroAlelo: "01:01",
                                qualificacaoPrimeiroAlelo: "A",
                                qualificacaoSegundoAlelo: "L",
                                segundoAlelo: "01:01:01",
                                tipoPrimeiroAlelo: 5,
                                tipoSegundoAlelo: 5
                            },
                            {
                                locus: "C"
                            },
                            {
                                locus: "DQB1"
                            }
                        ],
                        id: 1,
                        mismatch: "A",
                        peso: 60,
                        registroOrigem: "REDOME",
                        sexo: "M",
                        fase: "EX",
                        tipoDoador: 0
                    }
                ],
                listaLocus: [
                    {codigo: "A", ordem: 1},
                    {codigo: "B", ordem: 2},
                    {codigo: "C", ordem: 3},
                    {codigo: "DRB1", ordem: 4},
                    {codigo: "DRB3", ordem: 5},
                    {codigo: "DRB4", ordem: 6},
                    {codigo: "DRB5", ordem: 7},
                    {codigo: "DPA1", ordem: 8},
                    {codigo: "DPB1", ordem: 9},
                    {codigo: "DQA1", ordem: 10},
                    {codigo: "DQB1", ordem: 11}
                ],
                nomePaciente: "teste nome",
                peso: 80,
                rmr: 3005996,
                sexo: "F"
            }
            return Observable.of(res).toPromise();
        };

        component.data = {
            "_rmr": 3005996,
            "listaIdsDoadores": [317],
            ordernarLista: (genotipoComparadoDTO: GenotipoComparadoDTO) => {}

        }
        await component.ngOnInit();
        expect(component.genotipoComparadoDTO).toBeDefined();

    })().then(done).catch(done.fail));

    it('deve exibir genotipo doador intenacional', (done) => (async () => {

        genotipoService.listarGenotiposComparados = function(rmr: number, listaIdsDoadores: number[]): Promise<any> {
            let res = {
                abo: "B-",
                dataNascimento: "1987-03-31",
                genotipoPaciente: [
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "A",
                        ordem: 1,
                        primeiroAlelo: "01:01",
                        segundoAlelo: "01:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "B",
                        ordem: 2,
                        primeiroAlelo: "13:73",
                        segundoAlelo: "13:73",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "C",
                        ordem: 3,
                        primeiroAlelo: "08:01",
                        segundoAlelo: "08:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "DRB1",
                        ordem: 4,
                        primeiroAlelo: "13:01",
                        segundoAlelo: "13:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "DQB1",
                        ordem: 11,
                        primeiroAlelo: "02:02",
                        segundoAlelo: "02:02",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    }
                ],
                genotiposDoadores: [
                    {
                        abo: "A-",
                        classificacao: "4/4",
                        dataNascimento: "1973-01-01",
                        genotipoDoador: [
                            {
                                locus: "A",
                                ordem: 1,
                                primeiroAlelo: "01:01",
                                qualificacaoPrimeiroAlelo: "P",
                                qualificacaoSegundoAlelo: "P",
                                segundoAlelo: "01:01",
                                tipoPrimeiroAlelo: 5,
                                tipoSegundoAlelo: 5
                            },
                            {
                                locus: "B",
                                ordem: 2,
                                primeiroAlelo: "13:73",
                                qualificacaoPrimeiroAlelo: "P",
                                qualificacaoSegundoAlelo: "P",
                                segundoAlelo: "13:73",
                                tipoPrimeiroAlelo: 5,
                                tipoSegundoAlelo: 5
                            },
                            {locus: "C"},
                            {locus: "DRB1"},
                            {locus: "DQB1"}
                        ],
                        id: 317,
                        idRegistro: "US-654654",
                        peso: 80,
                        registroOrigem: "NMPD",
                        sexo: "M",
                        fase: "F1",
                        tipoDoador: 1
                    }
                ],
                listaLocus: [
                    {codigo: "A", ordem: 1},
                    {codigo: "B", ordem: 2},
                    {codigo: "C", ordem: 3},
                    {codigo: "DRB1", ordem: 4},
                    {codigo: "DRB3", ordem: 5},
                    {codigo: "DRB4", ordem: 6},
                    {codigo: "DRB5", ordem: 7},
                    {codigo: "DPA1", ordem: 8},
                    {codigo: "DPB1", ordem: 9},
                    {codigo: "DQA1", ordem: 10},
                    {codigo: "DQB1", ordem: 11}
                ],
                nomePaciente: "teste nome",
                peso: 80,
                rmr: 3005996,
                sexo: "F"
            }
            return Observable.of(res).toPromise();
        };

        component.data = {
            "_rmr": 3005996,
            "listaIdsDoadores": [317],
            ordernarLista: (genotipoComparadoDTO: GenotipoComparadoDTO) => {}
        }
        await component.ngOnInit();
    })().then(done).catch(done.fail));

    it('deve exibir genotipo cordão intenacional', (done) => (async () => {

        genotipoService.listarGenotiposComparados = function(rmr: number, listaIdsDoadores: number[]): Promise<any> {
            let res = {
                abo: "B-",
                dataNascimento: "1987-03-31",
                genotipoPaciente: [
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "A",
                        ordem: 1,
                        primeiroAlelo: "01:01",
                        segundoAlelo: "01:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "B",
                        ordem: 2,
                        primeiroAlelo: "13:73",
                        segundoAlelo: "13:73",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "C",
                        ordem: 3,
                        primeiroAlelo: "08:01",
                        segundoAlelo: "08:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "DRB1",
                        ordem: 4,
                        primeiroAlelo: "13:01",
                        segundoAlelo: "13:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "DQB1",
                        ordem: 11,
                        primeiroAlelo: "02:02",
                        segundoAlelo: "02:02",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    }
                ],
                genotiposDoadores: [
                    {
                        abo: "AB-",
                        classificacao: "4/4",
                        dataNascimento: "1984-01-01",
                        genotipoDoador: [
                            {
                                locus: "A",
                                ordem: 1,
                                primeiroAlelo: "01:01",
                                qualificacaoPrimeiroAlelo: "P",
                                qualificacaoSegundoAlelo: "P",
                                segundoAlelo: "01:01",
                                tipoPrimeiroAlelo: 5,
                                tipoSegundoAlelo: 5
                            },
                            {
                                locus: "B",
                                ordem: 2,
                                primeiroAlelo: "13:73",
                                qualificacaoPrimeiroAlelo: "P",
                                qualificacaoSegundoAlelo: "P",
                                segundoAlelo: "13:73",
                                tipoPrimeiroAlelo: 5,
                                tipoSegundoAlelo: 5
                            },
                            {locus: "C"},
                            {locus: "DRB1"},
                            {locus: "DQB1"}
                        ],
                        id: 328,
                        idRegistro: "us-7847578",
                        quantidadeCD34PorKilo: 2.5,
                        quantidadeTCNPorKilo: 6.25,
                        registroOrigem: "NMPD",
                        sexo: "M",
                        fase: "F1",
                        tipoDoador: 3
                    }
                ],
                listaLocus: [
                    {codigo: "A", ordem: 1},
                    {codigo: "B", ordem: 2},
                    {codigo: "C", ordem: 3},
                    {codigo: "DRB1", ordem: 4},
                    {codigo: "DRB3", ordem: 5},
                    {codigo: "DRB4", ordem: 6},
                    {codigo: "DRB5", ordem: 7},
                    {codigo: "DPA1", ordem: 8},
                    {codigo: "DPB1", ordem: 9},
                    {codigo: "DQA1", ordem: 10},
                    {codigo: "DQB1", ordem: 11}
                ],
                nomePaciente: "teste nome",
                peso: 80,
                rmr: 3005996,
                sexo: "F"
            }
            return Observable.of(res).toPromise();
        };

        component.data = {
            "_rmr": 3005996,
            "listaIdsDoadores": [317],
            ordernarLista: (genotipoComparadoDTO: GenotipoComparadoDTO) => {}
        }
        await component.ngOnInit();
    })().then(done).catch(done.fail));

    it('deve exibir genotipo cordão nacional', (done) => (async () => {

        genotipoService.listarGenotiposComparados = function(rmr: number, listaIdsDoadores: number[]): Promise<any> {
            let res = {
                abo: "B-",
                dataNascimento: "1987-03-31",
                genotipoPaciente: [
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "A",
                        ordem: 1,
                        primeiroAlelo: "01:01",
                        segundoAlelo: "01:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "B",
                        ordem: 2,
                        primeiroAlelo: "13:73",
                        segundoAlelo: "13:73",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "C",
                        ordem: 3,
                        primeiroAlelo: "08:01",
                        segundoAlelo: "08:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "DRB1",
                        ordem: 4,
                        primeiroAlelo: "13:01",
                        segundoAlelo: "13:01",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    },
                    {
                        examePrimeiroAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        exameSegundoAlelo: "01/06/2018 - DQB1*, DRB1*, C*, B*, A*",
                        locus: "DQB1",
                        ordem: 11,
                        primeiroAlelo: "02:02",
                        segundoAlelo: "02:02",
                        tipoPrimeiroAlelo: 5,
                        tipoSegundoAlelo: 5
                    }
                ],
                genotiposDoadores: [
                    {
                        abo: "O-",
                        classificacao: "6/6",
                        dataNascimento: "1993-01-01",
                        genotipoDoador: [
                            {
                                locus: "A",
                                ordem: 1,
                                primeiroAlelo: "01:01",
                                qualificacaoPrimeiroAlelo: "P",
                                qualificacaoSegundoAlelo: "P",
                                segundoAlelo: "01:01",
                                tipoPrimeiroAlelo: 5,
                                tipoSegundoAlelo: 5
                            },
                            {
                                locus: "B",
                                ordem: 2,
                                primeiroAlelo: "13:73",
                                qualificacaoPrimeiroAlelo: "P",
                                qualificacaoSegundoAlelo: "P",
                                segundoAlelo: "13:73",
                                tipoPrimeiroAlelo: 5,
                                tipoSegundoAlelo: 5
                            },
                            {
                                locus: "C",
                                ordem: 3,
                                primeiroAlelo: "01:02",
                                qualificacaoPrimeiroAlelo: "P",
                                qualificacaoSegundoAlelo: "P",
                                segundoAlelo: "01:02",
                                tipoPrimeiroAlelo: 5,
                                tipoSegundoAlelo: 5
                            },
                            {locus: "DRB1"},
                            {locus: "DQB1"}
                        ],
                        id: 353,
                        idBscup: "BSCA25",
                        quantidadeCD34PorKilo: 3.125,
                        quantidadeTCNPorKilo: 3.75,
                        registroOrigem: "REDOME",
                        sexo: "F",
                        fase: "F2",
                        tipoDoador: 2
                    }
                ],
                listaLocus: [
                    {codigo: "A", ordem: 1},
                    {codigo: "B", ordem: 2},
                    {codigo: "C", ordem: 3},
                    {codigo: "DRB1", ordem: 4},
                    {codigo: "DRB3", ordem: 5},
                    {codigo: "DRB4", ordem: 6},
                    {codigo: "DRB5", ordem: 7},
                    {codigo: "DPA1", ordem: 8},
                    {codigo: "DPB1", ordem: 9},
                    {codigo: "DQA1", ordem: 10},
                    {codigo: "DQB1", ordem: 11}
                ],
                nomePaciente: "teste nome",
                peso: 80,
                rmr: 3005996,
                sexo: "F"
            }
            return Observable.of(res).toPromise();
        };

        component.data = {
            "_rmr": 3005996,
            "listaIdsDoadores": [317],
            ordernarLista: (genotipoComparadoDTO: GenotipoComparadoDTO) => {}
        }
        await component.ngOnInit();
    })().then(done).catch(done.fail));

});

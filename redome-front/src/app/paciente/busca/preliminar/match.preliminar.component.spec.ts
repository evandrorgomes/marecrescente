import { async, ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { AnaliseMatchPreliminarComponent } from 'app/paciente/busca/preliminar/match.preliminar.component';
import { AnaliseMatchPreliminarDTO } from 'app/shared/dto/analise.match.preliminar.dto';
import { FiltroMatch } from 'app/shared/enums/filtro.match';
import { TiposDoador } from 'app/shared/enums/tipos.doador';
import { BuscaPreliminar } from 'app/shared/model/busca.preliminar';
import { Observable } from 'rxjs';
import { activatedRoute, buscaPreliminarService, dominioService, classificacaoABOServiceService } from '../../../export.mock.spec';
import { MatchDTO } from '../../../shared/component/listamatch/match.dto';
import { DateMoment } from '../../../shared/util/date/date.moment';

describe('AnaliseMatchPreliminarComponent', () => {
    let fixture: ComponentFixture<AnaliseMatchPreliminarComponent>;
    let component: AnaliseMatchPreliminarComponent;
    
    beforeEach((done) => (async () => {
        activatedRoute.testParams = {'idBuscaPreliminar': 1};

        dominioService.obterConfiguracao = function(chave:string){
            return Observable.of(dominioService._RES_CONFIGURACAO).toPromise();
        }

        fixture = TestBed.createComponent(AnaliseMatchPreliminarComponent);
        component = fixture.debugElement.componentInstance;

        buscaPreliminarService.obterAvaliacoesMatch = function(id: number, filtroMatch: FiltroMatch): Promise<any> {
            let analiseMatchDTO: AnaliseMatchPreliminarDTO = new AnaliseMatchPreliminarDTO();
            analiseMatchDTO.buscaPreliminar = new BuscaPreliminar();
            analiseMatchDTO.buscaPreliminar.id = 1;
            analiseMatchDTO.buscaPreliminar.dataNascimento = new Date();
            analiseMatchDTO.buscaPreliminar.dataNascimento.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            analiseMatchDTO.buscaPreliminar.abo = "A+";
            analiseMatchDTO.buscaPreliminar.peso = 70.0;
            analiseMatchDTO.buscaPreliminar.nomePaciente = "Teste de match";

            let match: MatchDTO = new MatchDTO();
            match.idDoador = 1;
            match.dmr = 1;
            match.nome = "João da Silva";
            match.sexo = "M";
            match.dataNascimento = new Date();
            match.dataNascimento.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            match.peso = 80.0;
            match.abo = "O+";
            match.mismatch = "";
            match.classificacao = "6/6";
            match.dataAtualizacao = new Date();
            match.dataAtualizacao.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            match.tipoDoador = TiposDoador.NACIONAL

            let match2: MatchDTO = new MatchDTO();
            match2.idDoador = 2;
            match2.dmr = 2;
            match2.nome = "Vilma de Souza";
            match2.sexo = "F";
            match2.dataNascimento = new Date();
            match2.dataNascimento.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            match2.peso = 80.0;
            match2.abo = "O+";
            match2.mismatch = "";
            match2.classificacao = "6/6";
            match2.dataAtualizacao = new Date();
            match2.dataAtualizacao.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            };
            match2.tipoDoador = TiposDoador.NACIONAL

            analiseMatchDTO.listaFase1 = [match, match2];

            let match3: MatchDTO = new MatchDTO();
            match3.idDoador = 3;
            match3.dmr = 3;
            match3.nome = "Roberto Oliveira";
            match3.sexo = "M";
            match3.dataNascimento = new Date();
            match3.dataNascimento.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            match3.peso = 80.0;
            match3.abo = "O+";
            match3.mismatch = "";
            match3.classificacao = "6/6";
            match3.dataAtualizacao = new Date();
            match3.dataAtualizacao.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            match3.tipoDoador = TiposDoador.NACIONAL

            analiseMatchDTO.listaFase2 = [match3];

            let match4: MatchDTO = new MatchDTO();
            match4.idDoador = 4;
            match4.dmr = 4;
            match4.nome = "Roberto Oliveira";
            match4.sexo = "M";
            match4.dataNascimento = new Date();
            match4.dataNascimento.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            match4.peso = 80.0;
            match4.abo = "O+";
            match4.mismatch = "";
            match4.classificacao = "6/6";
            match4.dataAtualizacao = new Date();
            match4.dataAtualizacao.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            match4.tipoDoador = TiposDoador.NACIONAL
            
            analiseMatchDTO.listaFase3 = [match4];

            analiseMatchDTO.totalMedula = 4;
            analiseMatchDTO.totalCordao = 1; 

            let analiseMatchDTOString: String = JSON.stringify(analiseMatchDTO);            
            while (analiseMatchDTOString.search("\"tipoDoador\":0") != -1) {
                analiseMatchDTOString = analiseMatchDTOString.replace("\"tipoDoador\":0", "\"tipoDoador\": \"NACIONAL\"" );
            }
            let res = JSON.parse(analiseMatchDTOString.toString());

            return Observable.of(res).toPromise();
        }

        classificacaoABOServiceService.listarClassificacaoABO = function():Promise<any>{
            return Observable.of('').toPromise();
        }

        await component.ngOnInit();        
        
    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de evalocao foi instanciado com sucesso
     * @author Fillipe Queiroz
     */
    it('deveria criar o AnaliseMatchPreliminarComponent', () => {
        expect(component).toBeTruthy();
    });

    it("deveria retornar AnaliseMatchPreliminarDTO para um determinada busca preliminar de um paciente", (done) => (async () => {
        fixture.detectChanges();
        var timeoutPromise = new Promise(function(resolve, reject) {
            setTimeout(function() {
                expect(component.analiseMatchPreliminarDTO.buscaPreliminar.id).toEqual(1);    
                resolve();
            }, 300);
        });
        await timeoutPromise;
        
    })().then(done).catch(done.fail));

    

});
import { Observable } from 'rxjs/Observable';
import { evolucaoService, buscaService } from './../../../export.mock.spec';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { activatedRoute, classificacaoABOServiceService, dominioService, pacienteService } from '../../../export.mock.spec';
import { MatchDTO } from '../../../shared/component/listamatch/match.dto';
import { Cid } from '../../../shared/dominio/cid';
import { Raca } from '../../../shared/dominio/raca';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { Diagnostico } from '../../cadastro/diagnostico/diagnostico';
import { Paciente } from '../../paciente';
import { AnaliseMatchDTO } from './analise.match.dto';
import { AnaliseMatchComponent } from './match.component';
import { FiltroMatch } from 'app/shared/enums/filtro.match';
import { Evolucao } from '../../cadastro/evolucao/evolucao';
import { Busca } from '../busca';
import { StatusPaciente } from 'app/paciente/paciente.status';


describe('AnaliseMatchComponent', () => {
    let fixture: ComponentFixture<AnaliseMatchComponent>;
    let component: AnaliseMatchComponent;
    
    beforeEach((done) => (async () => {
        activatedRoute.testParams = {'idPaciente': 1};

        pacienteService.obterMatchsSobAnalise = function(rmr: number, filtro: FiltroMatch): Promise<any> {
            let analiseMatchDTO: AnaliseMatchDTO = new AnaliseMatchDTO();
            analiseMatchDTO.rmr = rmr;
            analiseMatchDTO.temPrescricao = false;
            
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
            match.possuiGenotipoDivergente = false;

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
            match2.possuiGenotipoDivergente = false;

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
            match3.possuiGenotipoDivergente = false;

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
            match4.possuiGenotipoDivergente = false;
            
            analiseMatchDTO.listaFase3 = [match4];

            let res = JSON.parse(JSON.stringify(analiseMatchDTO));
            return Observable.of(res).toPromise();
        }

        pacienteService.obterIdentificacaoPacientePorRmr = function (rmr: number) {
            let paciente:Paciente = new Paciente();
            paciente.rmr = rmr;
            paciente.sexo = 'M';

            let diagnostico:Diagnostico=new Diagnostico();
            let cid:Cid=new Cid(1);
            cid.transplante = true;
            cid.codigo ="X80.0";
            cid.descricao = "descricao";
            diagnostico.cid = cid;
            paciente.status = new StatusPaciente();
            paciente.status.descricao = "teste";
            paciente.diagnostico = diagnostico;
            paciente.dataNascimento = new Date();
            let evolucao: Evolucao = new Evolucao();
            evolucao.peso = 80;
            paciente.evolucoes = [];
            paciente.evolucoes.push(evolucao);

            console.log(paciente);
            paciente.dataNascimento.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            
            let res = JSON.parse(JSON.stringify(paciente));
            return Observable.of(res).toPromise();
        }

        

        dominioService.obterConfiguracao = function(chave:string){
            return Observable.of(dominioService._RES_CONFIGURACAO).toPromise();
        }

        
        buscaService.obterBuscaPorRMR = function(rmr:number){
            return Observable.of(new Busca()).toPromise();
        }

        classificacaoABOServiceService.listarClassificacaoABO = function():Promise<any>{
            return Observable.of('').toPromise();
        }
        fixture = TestBed.createComponent(AnaliseMatchComponent);
        component = fixture.debugElement.componentInstance;
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
    it('deveria criar o AnaliseMatchComponent', () => {
        expect(component).toBeTruthy();
    });

    it("deve retornar AvaliacaoMatchDTO para um determinado paciente", async(() => {
        expect(component.analiseMatchDTO.rmr).toEqual(1);            
    }));

    

});
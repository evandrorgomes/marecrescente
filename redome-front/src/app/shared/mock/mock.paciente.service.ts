import { UF } from '../dominio/uf';
import { Pais } from '../dominio/pais';
import { Raca } from '../dominio/raca';
import { Cid } from '../dominio/cid';
import { Diagnostico } from '../../paciente/cadastro/diagnostico/diagnostico';
import { Paciente } from '../../paciente/paciente';
import { Avaliacao } from '../../paciente/avaliacao/avaliacao';
import { CampoMensagem } from '../campo.mensagem';
import { ErroMensagem } from '../erromensagem';
import { Observable } from 'rxjs';
import { FiltroMatch } from 'app/shared/enums/filtro.match';
import { Evolucao } from '../../paciente/cadastro/evolucao/evolucao';
import { StatusPaciente } from 'app/paciente/paciente.status';
export class MockPacienteService {

    erroMensagem = {
        listaCampoMensagem: [{            
        }],
        statusCode: 422,
        statusText: "Undefined"
    }

    /**
     * Simula a chamada para o back, por isso os testes de erro também deveram ser mocados aqui
     * 
     * @param {number} rmr 
     * @param {string} nome 
     * @param {number} pagina 
     * @param {number} quantidadeRegistros 
     * @returns 
     * 
     * @memberOf MockPacienteService
     */
    listarPacientePorRmrOuNome(rmr: number, nome: string, pagina: number, quantidadeRegistros: number) {
        if (!rmr && !nome) {            
            return Promise.reject(this.criarMensagemErroBack("Parâmetros mínimos não fornecidos."));
        }
        //simula o paciente não encontrado por rmr
        if (rmr && rmr == 123456) {
            return Promise.reject(this.criarMensagemErroBack("Nennhum registro encontrado."));
        }
        if (nome && nome == 'ave') {
            return Promise.reject(this.criarMensagemErroBack("Nennhum registro encontrado."));            
        }
        return Observable.of().toPromise();
    }

    private criarMensagemErroBack(mensagem: string): ErroMensagem {
        let erromensagem: ErroMensagem = new ErroMensagem(422, '', 'Undefined');
        let campoMensagem : CampoMensagem = new CampoMensagem();
        campoMensagem.campo = "error";
        campoMensagem.mensagem = mensagem;
        erromensagem.addCampoMensagem(campoMensagem);
        return erromensagem;
    }

    obterAvaliacaoAtual(): Promise<any> {
        
        return Promise.resolve(new Avaliacao(1));
    }

    obterIdentificacaoPacientePorRmr(rmr): Promise<any> {
        let paciente:Paciente = new Paciente();
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
        let raca:Raca = new Raca(1);
        raca.nome = "BRANCA";
        paciente.raca = raca;
        let evolucao: Evolucao = new Evolucao();
        evolucao.peso = 80;
        paciente.evolucoes = [];
        paciente.evolucoes.push(evolucao);

        return Observable.of(paciente.toJSON()).toPromise(); //   Promise.resolve(paciente.toJSON());
    }

    obterIdentificaoEDadosPessoais(rmr: number) {
        let paciente: Paciente = new Paciente();
        paciente.rmr = 1;
        paciente.dataNascimento = new Date("10/10/1980");
        paciente.cpf = '62455588327';
        paciente.cns = '';
        paciente.nome = 'Nome do paciente';
        paciente.nomeMae = '';
        paciente.sexo = 'M';
        paciente.pais = new Pais(1, "BRASIL");
        paciente.naturalidade = new UF('RJ');
        paciente.abo = 'O-';
        paciente.raca = new Raca(1);        

        return Observable.of(paciente.toJSON()).toPromise(); //Promise.resolve(paciente.toJSON());
    }

    verificarDuplicidade(paciente: Paciente) {}

    salvarIdentificacaoEDadosPessoais(paciente: Paciente) {}

    obterMatchsSobAnalise(rmr: number, filtro: FiltroMatch): Promise<any> {
        return null;
    }

    verificarUltimaEvolucaoAtualizada(rmr: number): Promise<any> {
        return null;
    }

    obterStatusPacientePorRmr(rmr: number):Promise<any> {
        return Promise.resolve({descricao: 'Aprovado'});
    }

}


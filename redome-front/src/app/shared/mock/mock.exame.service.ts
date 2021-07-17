import { ExamePaciente } from '../../paciente/cadastro/exame/exame.paciente';
import { Observable } from 'rxjs';
import { ArquivoExame } from '../../paciente/cadastro/exame/arquivo.exame';
import { Locus } from '../../paciente/cadastro/exame/locus';
import { LocusExame } from '../../paciente/cadastro/exame/locusexame';
import { LocusExamePk } from '../../paciente/cadastro/exame/locusexamepk';
import { Metodologia } from '../../paciente/cadastro/exame/metodologia';
import { MotivoDescarte } from '../dominio/motivo.descarte';
import { Paciente } from '../../paciente/paciente';
export class MockExameService {

    obterExamePrioritario() {
        
        let exame: ExamePaciente = new ExamePaciente();
        
        exame.arquivosExame = this.getArquivosExame();
        exame.metodologias = this.getMetodologias();
        exame.locusExames = this.getLocusExames();
        exame.dataExame = new Date();
        let jsonRetorno = {
            'exameSelecionado':exame.toJSON(),
            'rmrPaciente':1
        };

        return Observable.of(jsonRetorno).toPromise();
    }

    getLocusExames():LocusExame[]{
        let listLocusExame:LocusExame[] = [];
        let locusExame:LocusExame =new LocusExame();
        locusExame.primeiroAlelo="teste";
        locusExame.segundoAlelo="teste";

        let locusPK:LocusExamePk= new LocusExamePk();
        let locus:Locus = new Locus();
        locus.codigo = "A";
        locusPK.locus = locus;
        locusExame.id=locusPK;

        listLocusExame.push(locusExame);
        return listLocusExame;
    }

    getMetodologias():Metodologia[]{
        let metodologias:Metodologia[] = [];
        let metodologia:Metodologia = new Metodologia();
        metodologia.id = 1;
        metodologia.sigla = "SSP";
        metodologia.descricao = "Sequence Specific Primers";
        metodologias.push(metodologia);

        return metodologias;
    }

    getArquivosExame():ArquivoExame[]{
        let arquivos:ArquivoExame[] = [];
        let arquivo:ArquivoExame = new ArquivoExame();
        arquivo.caminhoArquivo = "GYzj2/1506520967751_laudo_teste.png";
        arquivos.push(arquivo);
        return arquivos;
    }

    listarMotivosDescarte(){
        let motivos:MotivoDescarte[] = []
        let motivo:MotivoDescarte = new MotivoDescarte();
        motivo.id = 1;
        motivo.descricao = "teste";
        return Observable.of(motivos).toPromise();
        // return motivos;

    }

    obterExame(id: number): Promise<any> {
        let exame: ExamePaciente = new ExamePaciente();
        
        exame.arquivosExame = this.getArquivosExame();
        exame.metodologias = this.getMetodologias();
        exame.locusExames = this.getLocusExames();
        exame.dataExame = new Date();
        exame.paciente = new Paciente();
        exame.paciente.rmr = 1;
        return Observable.of(exame.toJSON()).toPromise();
    }

}
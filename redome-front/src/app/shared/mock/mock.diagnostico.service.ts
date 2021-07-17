import { Diagnostico } from "app/paciente/cadastro/diagnostico/diagnostico";

export class MockDiagnosticoService {
    
    public alterarDiagnostico(rmr: number, diagnostico: Diagnostico): Promise<Object> {
        return Promise.resolve(null);
    }
}

export class MockArquivoExameService  {

    public visualizarArquivoExame(arquivoExameId: Number): void {
    }

    public baixarArquivoExame(arquivoExameId: Number, nomeArquivo:String):void{
    }

    public baixarArquivoExameZipado(arquivoExameId: Number, nomeArquivo:String):void{
    }

    public removerArquivo(usuarioId: number, nomeArquivo: string): Promise<Response> {
        return null;
    }

    salvarArquivo(data: FormData): Promise<Object> {        
        return null;
    }

}
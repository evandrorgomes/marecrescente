/**
 * Classe para encapsular todas as rotinas de formação de informações
 */
export class FormatterUtil {


    /**
     * Aplicar formatação da mascara de cpf
     * 
     * @private
     * @param {string} texto 
     * @param {string} mascara 
     * @returns {string} 
     * 
     * @memberOf DetalheComponent
     */
    public static aplicarMascaraCpf(texto: string): string {
        if (!texto) {
            return "";
        }
        
        //Coloca um ponto entre o terceiro e o quarto dígitos
        texto = texto.replace(/(\d{3})(\d)/,"$1.$2")
 
        //Coloca um ponto entre o terceiro e o quarto dígitos
        //de novo (para o segundo bloco de números)
        texto = texto.replace(/(\d{3})(\d)/,"$1.$2")
 
        //Coloca um hífen entre o terceiro e o quarto dígitos
        texto = texto.replace(/(\d{3})(\d{1,2})$/,"$1-$2")

        return texto;
    }


    /**
     * Aplicar formatação da mascara de cnpj
     * 
     * @private
     * @param {string} texto 
     * @param {string} mascara 
     * @returns {string} 
     * 
     * @memberOf DetalheComponent
     */
    public static aplicarMascaraCNPJ(texto: string): string {
        if (!texto) {
            return "";
        }
        
        //Remove tudo o que não é dígito
        texto = texto.replace( /\D/g , ""); 
        
        //Coloca ponto entre o segundo e o terceiro dígitos
        texto = texto.replace( /^(\d{2})(\d)/ , "$1.$2"); 
        
        //Coloca ponto entre o quinto e o sexto dígitos
        texto = texto.replace( /^(\d{2})\.(\d{3})(\d)/ , "$1.$2.$3"); 
        
        //Coloca uma barra entre o oitavo e o nono dígitos
        texto = texto.replace( /\.(\d{3})(\d)/ , ".$1/$2"); 
        
        //Coloca um hífen depois do bloco de quatro dígitos
        texto = texto.replace( /(\d{4})(\d)/ , "$1-$2"); 

        return texto;
    }

    public static aplicarMascaraData(data: Date): String {
        if (data) {
            if(!(data instanceof Date) || !data.getDate()){
                /**
                 * Tratamento para as datas que chegam no front como String.
                 * Deverá ser removido ou extraído, quando a situação for 
                 * tratada e definida uma implementação.
                 **/
                data = new Date(data.toString().replace(/-/g, '\/'));
            }
            
            let dia:string = data.getDate() + '';
            if (dia.length == 1) {
                dia = '0' + dia;
            }
            let mes:string = data.getMonth()+1 + '';
            if (mes.length == 1) {
                mes =  '0' + mes;
            }
            return dia + "/" + mes + "/" + data.getFullYear();
        }
        return "";
    }

    public static aplicarMascaraCep(texto: string): string {
        if (!texto) {
            return "";
        }

        //Remove tudo o que não é dígito
        texto = texto.replace( /\D/g , ""); 
        
        //Coloca - entre o quinto e o sexto dígitos
        texto = texto.replace( /^(\d{5})(\d)/ , "$1-$2");
    }

    public static obterCEPSemMascara(_cep: string): number {
        let cepSemMascara: number;
        if (_cep) {
            cepSemMascara = Number(_cep.replace('-', ''))
        }
        return cepSemMascara;
    }

    public static obterAlturaSemMascara(_altura: String): number {
        _altura = new String(_altura);
        if (_altura) {
            if(_altura.indexOf('.') >= 0){
                if(_altura.split('.')[1].length==1){
                    _altura = _altura + "0";
                }
                _altura = _altura.replace('.', '');
            }
            
            if(_altura.length === 1) {
                return Number(_altura);
            }
            return Number(_altura) / 100;
        }
    }

    deprecate
    public static obterPesoSemMascara(_peso: string): number {
        if (_peso) {
            return Number(_peso) / 10;
        }
    }

    public static obterPesoFormatado(peso:string, decimais:number): string {
        if(peso == null) {
            return null;
        }

        if(peso.indexOf(".") != -1){
            peso = peso.replace(".","");
        }

        let pesoInteiro:string;
        let pesoDecimais:string;


        if(peso.length > 2) { 
            pesoInteiro = peso.substring(0, peso.length - decimais);
            pesoDecimais = peso.substr(peso.length - decimais, decimais);
        } else {
            pesoInteiro = peso;
            pesoDecimais = "0";
        }
        return pesoInteiro + "," + pesoDecimais;
   }

    /**
     * Formata o valor, passada como parametro, para o formato em casa decimais solicitado.
     * 
     * @param valor string com o valor a ser formatado. 
     * Ex: 10.5, 105 (sem o separador), 10.52, com 1 casa decimal ficaria 10.5
     * @param decimais 
     */
    public static arredondar(valor:string, decimais:number): number {
        if(valor == null) {
            return null;
        }
        if(valor.indexOf(",") != -1){
            valor = valor.replace(",","");
        }

        valor = String(valor);

        let temSeparador:boolean = valor.indexOf(".") != -1;

        if(!temSeparador) {
            let valorInteiro:string;
            let valorDecimais:string;

            if(valor.length > 2) { 
                valorInteiro = valor.substring(0, valor.length - decimais);
                valorDecimais = valor.substr(valor.length - decimais, decimais);
            } else {
                valorInteiro = valor;
                valorDecimais = "0";
            }
            valor = valorInteiro + "." + valorDecimais;
        }

        return Number(Number(valor).toFixed(decimais));
    }

    public static obterCNPJSemMascara(_cnpjComMascara: any): string {
        let cnpj: string;

        if (_cnpjComMascara) {
            cnpj = _cnpjComMascara as string
            return cnpj.replace('.', '').replace('.', '').replace('/', '').replace('-', '');
        }
        return cnpj;
    }

    public static obterCPFSemMascara(_cpfComMascara: any): string {
        let cpf: string;

        if (_cpfComMascara) {
            cpf = _cpfComMascara as string
            return cpf.replace('.', '').replace('.', '').replace('-', '');
        }
        return cpf;
    }

    public static obterNomeArquivoSelecionado(caminhoArquivo: String): String {
        let indexDaBarra = caminhoArquivo.indexOf("/") + 1;
        caminhoArquivo = caminhoArquivo.substring(indexDaBarra, caminhoArquivo.length);
        let indexDoUnderline = caminhoArquivo.indexOf("_");
        caminhoArquivo = caminhoArquivo.substring(indexDoUnderline + 1);
        return caminhoArquivo;
    }

}
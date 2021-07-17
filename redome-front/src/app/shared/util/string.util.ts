export class StringUtil{

    /**
     * Verifica se o valor passado é nulo ou vazio.
     * TODO: Implementar caso a caso, sempre que necessário.
     * 
     * @param value valor a ser validado.
     */
    public static isNullOrEmpty(value: any): boolean{
        return !value || value == "";
    }

    public static formatBytes(bytes, decimals?) {
        if (bytes == 0) return '0 Bytes';
        const k = 1024,
            dm = decimals || 2,
            sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
            i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
    }

}
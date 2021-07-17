/**
 * Classe que representa os centros de transplante possíveis de serem
 * transplantadores, exibidos para o analista de busca no momento da 
 * confirmação de disponibilização do doador para prescrição.
 * 
 * @author Pizão
 */
export class ContatoCentroTransplantadorDTO{
    public id: number;
	public nome: string;
	public tipoLogradouro: string;
	public nomeLogradouro: string;
	public bairro: string;
	public municipio: string;
	public uf: string;
	public codigoInternacional: string;
	public codigoArea: string;
	public numero: string;
}
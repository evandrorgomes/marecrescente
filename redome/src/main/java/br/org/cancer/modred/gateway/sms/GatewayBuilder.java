package br.org.cancer.modred.gateway.sms;

/**
 * Builder responsável por criar o gateway de sms.
 * 
 * @author brunosousa
 *
 */
public class GatewayBuilder {
	
	/**
	 * Método responsável por criar o Gateway de sms.
	 * 
	 * @param clazz -- Class de gateway que deverá ser criada.
	 * @return Instacia do gateway referente a classe informada. 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static IGatewaySms build(Class<? extends IGatewaySms> clazz) throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}

}

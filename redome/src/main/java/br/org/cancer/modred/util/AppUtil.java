package br.org.cancer.modred.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.modred.exception.BusinessException;

/**
 * Classe utilitária.
 * 
 * @author Filipe Queiroz
 *
 */
public class AppUtil {

	private static final String PACOTE_ENTIDADE_JPA = "javax.persistence.Entity";
	private static MappingJackson2HttpMessageConverter customMappingJackson2HttpMessageConverter;

	/**
	 * Método para pegar as mensagens do arquivo de propriedades.
	 * 
	 * @param messageSource
	 * @param id
	 * @param parameters
	 * @return mensagem
	 * @author Fillipe Queiroz
	 */
	public static String getMensagem(MessageSource messageSource, String id, Object... parameters) {
		return messageSource.getMessage(id, parameters, LocaleContextHolder
				.getLocale());
	}

	/**
	 * Método que gera uma string aleatória alfanumerica de 5 caracteres.
	 * 
	 * @return
	 * @author Fillipe Queiroz
	 */
	public static String gerarStringAleatoriaAlfanumerica() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	/**
	 * Método que percorre o objeto enviado para nular uma entidade que tenha todos seus atributos nulos.
	 * 
	 * @author Fillipe Queiroz
	 * @param objeto obj
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	public static void percorrerObjetoNulandoEntidadesVazias(Object objeto) {

		try {
			percorrerObjetoNulandoEntidadesVazias(objeto, objeto.getClass(), null, false, null);
		}
		catch (Exception e) {
			throw new BusinessException("erro.percorrer.entidades.vazias");
		}
	}

	/**
	 * @param texto para tratamento
	 * @return retorna o texto sem acentuação.
	 */
	public static String removerAcentuacao(String texto) {
		texto = transformarCharsetParaUtf8(texto);
		return Normalizer.normalize(texto, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "");
	}

	private static <T> Object percorrerObjetoNulandoEntidadesVazias(Object objeto, Object objetoPai,
			String atributoParaNular, boolean isAtributoDeLista,
			List<String> listaClassesIteradas)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			IntrospectionException {
		if (objeto != null) {

			Class<? extends Object> classe = objeto.getClass();
			if (listaClassesIteradas == null) {
				listaClassesIteradas = new ArrayList<>();
			}
			if (!isAtributoDeLista) {
				listaClassesIteradas.add(classe.toString());
			}

			// Field[] fields = classe.getDeclaredFields();
			List<Field> campos = new ArrayList<Field>();
			campos = getAllFields(campos, classe);

			Field[] fields = new Field[campos.size()];
			fields = campos.toArray(fields);

			Boolean[] objetoNulo = new Boolean[Introspector.getBeanInfo(classe)
					.getPropertyDescriptors().length];
			for (int c = 0; c <= objetoNulo.length - 1; c++) {
				objetoNulo[c] = true;
			}
			int contador = 0;
			for (PropertyDescriptor atributoEntidade : Introspector.getBeanInfo(classe)
					.getPropertyDescriptors()) {
				if (temMetodoGet(atributoEntidade) && atributoEntidade.getWriteMethod() != null) {
					Object valorAtributo = atributoEntidade.getReadMethod().invoke(objeto);
					if (valorAtributo != null) {
						objetoNulo[contador] = verificarAtributoNuloPorTipoAtributo(objeto, objetoPai, classe,
								atributoEntidade, valorAtributo, listaClassesIteradas, fields,
								isAtributoDeLista);

					}

				}
				contador++;
			}

			boolean isObjetoNaoNulo = Stream.of(objetoNulo).anyMatch(valor -> valor == false);

			if (!isObjetoNaoNulo && !isAtributoDeLista && atributoParaNular != null) {
				BeanUtils.setProperty(objetoPai, atributoParaNular, null);
				return null;
			}
			else
				if (!isObjetoNaoNulo && isAtributoDeLista) {
					return null;
				}
		}
		return objeto;
	}

	/**
	 * Retorna os campos de classe pai também.
	 * 
	 * @param fields - atributos da classe
	 * @param type - classe a ser retornada os atributos
	 * @return List<Field> lista de campos
	 */
	public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
		fields.addAll(Arrays.asList(type.getDeclaredFields()));

		if (type.getSuperclass() != null) {
			getAllFields(fields, type.getSuperclass());
		}

		return fields;
	}

	private static boolean temMetodoGet(PropertyDescriptor atributoEntidade) {
		return atributoEntidade.getReadMethod() != null;
	}

	private static Boolean verificarAtributoNuloPorTipoAtributo(Object objeto, Object objetoPai,
			Class<? extends Object> classe, PropertyDescriptor atributoEntidade,
			Object valorAtributo, List<String> listaClassesIteradas,
			Field[] fields, boolean isAtributoDeLista)
			throws IllegalAccessException, InvocationTargetException,
			IntrospectionException {
		Boolean objetoNulo = true;
		if (temAtributoNaEntidade(classe, atributoEntidade.getName(), fields)) {
			if (isEntidade(valorAtributo.getClass())) {

				if (!valorAtributo.getClass().equals(objetoPai.getClass()) && !listaClassesIteradas
						.contains(valorAtributo.getClass().toString())) {
					objetoNulo = percorrerObjetoNulandoEntidadesVazias(valorAtributo, objeto,
							atributoEntidade
									.getName(), false, listaClassesIteradas) == null;
				}
			}
			else {
				objetoNulo = percorrerAtributosNaoEntidadeParaNular(objeto,
						atributoEntidade, valorAtributo, listaClassesIteradas, objetoPai,
						isAtributoDeLista);

			}
		}
		return objetoNulo;
	}

	private static Boolean percorrerAtributosNaoEntidadeParaNular(Object objeto,
			PropertyDescriptor atributoEntidade, Object valorAtributo,
			List<String> listaClassesIteradas, Object objetoPai, boolean isAtributoDeLista)
			throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		Boolean objetoNulo = true;
		if (!isAtributoDeLista && "List".equals(atributoEntidade.getPropertyType()
				.getSimpleName())) {
			percorrerListaParaNularObjetosVazios(objeto, atributoEntidade, valorAtributo,
					listaClassesIteradas, objetoPai);
		}
		valorAtributo = atributoEntidade.getReadMethod().invoke(objeto);
		objetoNulo = valorAtributo == null;
		return objetoNulo;
	}

	private static void percorrerListaParaNularObjetosVazios(Object objeto,
			PropertyDescriptor atributoEntidade, Object valorAtributo,
			List<String> listaClassesIteradas, Object objetoPai)
			throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		@SuppressWarnings("unchecked")
		List<Object> listaObjetos = (List<Object>) valorAtributo;
		boolean temObjetoPreenchido = false;
		List<Object> listaSemLixo = new ArrayList<>();
		for (Object object : listaObjetos) {
			Object retorno = percorrerObjetoNulandoEntidadesVazias(object,
					objeto, atributoEntidade.getName(), true, listaClassesIteradas);
			if (retorno != null) {
				listaSemLixo.add(retorno);
				temObjetoPreenchido = true;
			}
		}

		if (!temObjetoPreenchido) {
			BeanUtils.setProperty(objeto, atributoEntidade.getName(), null);
		}
		else {
			BeanUtils.setProperty(objeto, atributoEntidade.getName(), listaSemLixo);
		}
	}

	/**
	 * Método .
	 * 
	 * @param classe classe
	 * @param atributoParaVerificar atr
	 * @param fields f
	 * @return true false
	 */
	static boolean temAtributoNaEntidade(Class<? extends Object> classe,
			String atributoParaVerificar, Field[] fields) {

		return Stream.of(fields).anyMatch(atributo -> atributoParaVerificar.equals(
				atributo.getName())) && !"class".equals(atributoParaVerificar);
	}

	private static boolean isEntidade(Class<? extends Object> classe) {
		if (Optional.ofNullable(classe.getAnnotations()).isPresent()) {
			return Stream.of(classe.getAnnotations()).anyMatch(anotacao -> {
				return anotacao.toString().contains(PACOTE_ENTIDADE_JPA);
			});
		}
		return false;
	}

	/**
	 * @param texto
	 * @return texto no charset UTF-8.
	 */
	private static String transformarCharsetParaUtf8(String texto) {
		Charset charSet = StandardCharsets.UTF_8;
		return charSet.decode(StandardCharsets.ISO_8859_1.encode(texto)).toString();
	}

	/**
	 * @param nome para tratamento
	 * @return iniciais do nome
	 */
	public static String obterIniciais(String nome) {
		if (nome == null) {
			return "";
		}

		String[] textoSplit = nome.toUpperCase().replaceAll("( DA | DE | DI | DO | DU )", " ").split(" ");
		String nomeAbreviado = "";
		for (String palavra : textoSplit) {
			if (palavra.length() >= 2) {
				nomeAbreviado += palavra.charAt(0);
			}

		}

		return nomeAbreviado;
	}
	
	/**
	 * Realiza a validação do CPF.
	 * @param strCpf - número de CPF a ser validado
	 * @return true se o CPF é válido e false se não é válido
	 */
	public static boolean validarCPF(String strCpf) {
		if("99999999999".equals(strCpf) || "00000000000".equals(strCpf)){
			return false;
		}
		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String numeroDigitoResult;
		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;
		for (int numeroContador = 1; numeroContador < strCpf.length() - 1; numeroContador++) {
			digitoCPF = Integer.valueOf(strCpf.substring(numeroContador - 1, numeroContador)).intValue();
			d1 = d1 + (11 - numeroContador) * digitoCPF;
			d2 = d2 + (12 - numeroContador) * digitoCPF;
		}
		resto = (d1 % 11);
		if (resto < 2) {
			digito1 = 0;
		}
		else {
			digito1 = 11 - resto;
		}
		d2 += 2 * digito1;
		resto = (d2 % 11);
		if (resto < 2) {
			digito2 = 0;
		}
		else {
			digito2 = 11 - resto;
		}
		String digitoVerificador = strCpf.substring(strCpf.length() - 2, strCpf.length());
		numeroDigitoResult = String.valueOf(digito1) + String.valueOf(digito2);
		return digitoVerificador.equals(numeroDigitoResult);
	}

	/**
	 * Método para splitar por um caracter e retornar um ArrayList.
	 * 
	 * @param str - string a ser splitada
	 * @param caracter - caracter usado para splitar
	 * @return lista com strings separadas
	 */
	public static List<String> splitReturnString(String str, String caracter) {
		return Stream.of(str.split(caracter))
				.map(elem -> new String(elem))
				.collect(Collectors.toList());
	}
	
	/**
	 * Método para splitar por um caracter e retornar um ArrayList.
	 * 
	 * @param str - string a ser splitada
	 * @param caracter - caracter usado para splitar
	 * @return lista com strings separadas
	 */
	public static List<Long> splitReturnLong(String str, String caracter) {
		return Stream.of(str.split(caracter))
				.map(elem -> new Long(elem))
				.collect(Collectors.toList());
	}

	/**
	 * Retorna o valor de um método get de uma entidade por reflection.
	 * 
	 * @param obj - valor do objeto
	 * @param fieldName - nome do atributo da entidade
	 * @return valor do objeto
	 */
	public static Object callGetter(Object obj, String fieldName) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, obj.getClass());
			return pd.getReadMethod().invoke(obj);
		}
		catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Conversão de valor Float para Bigdecimal.
	 * 
	 * @param numero - parametro para conversão.
	 * @param scala - scala para número float.
	 * @return BigDecimal - retorna número formatado.
	 */
	public static BigDecimal floatToBigDecimal(Float numero, Integer scala){

    	BigDecimal big = null;

		if(numero == null || numero == 0.0F || numero.isInfinite() || numero.isNaN()){
			return big; 
	    }
	    try{
	    	big = new BigDecimal(numero);
	        return big.setScale(scala, BigDecimal.ROUND_HALF_EVEN); 
	    }
	    catch(Exception e){
	        return BigDecimal.ZERO;
	    }
	}

	/**
	 * Formata o número de celular com 8 dígidos e iniciado com 7, 8 e 9. 
	 * 
	 * @param telefone número de telefone
	 * @return o número formatado.
	 */
	public static Long formatarTelefoneCelular(Long telefone) {
		if(telefone != null) {
			String numeroTelefone = String.valueOf(telefone);
			if(numeroTelefone.length() == 8) {
				if(numeroTelefone.charAt(0) == '7' || numeroTelefone.charAt(0) == '8' || numeroTelefone.charAt(0) == '9') {
					return new Long ("9" + numeroTelefone);
				}
			}
		}
		return telefone;
	}
	
    public static String conversorBytesParaString(byte[] json) {
    	if(json == null) {
    		return null;
    	}
		ObjectMapper objectMapper = customMappingJackson2HttpMessageConverter.getObjectMapper();
    	try {
			return objectMapper.writeValueAsString(new String(json, StandardCharsets.ISO_8859_1));
			
		} catch (JsonProcessingException e) {
			return null;
		}
    }

    public static byte[] conversorStringParaBytes(String json) {
    	if(json == null) {
    		return null;
    	}
    	return json.getBytes(StandardCharsets.ISO_8859_1);
    }

}

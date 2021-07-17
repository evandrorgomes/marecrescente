package br.org.cancer.modred.util;

/**
 * Classe util para validação.
 * @author Filipe Paes
 *
 */

public class ValidacaoUtil {
	/**
	 * Realiza a validação do CPF.
	 *
	 * @param cpf
	 *            número de CPF a ser validado
	 * @return true se o CPF é válido e false se não é válido
	 */
	public static boolean validarCPF(String cpf) {
		if(cpf.equals("99999999999") || cpf.length() < 11) {
			return false;
		}
		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String ndigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		for (int ncount = 1; ncount < cpf.length() - 1; ncount++) {
			digitoCPF = Integer.valueOf(
					cpf.substring(ncount - 1, ncount)).intValue();

			// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
			// e assim por diante.
			d1 = d1 + (11 - ncount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo o primeiro
			// digito calculado no passo anterior.
			d2 = d2 + (12 - ncount) * digitoCPF;
		}
		;

		// Primeiro resto da divisão por 11.
		resto = (d1 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2) {
			digito1 = 0;
		}
		else {
			digito1 = 11 - resto;
		}

		d2 += 2 * digito1;

		// Segundo resto da divisão por 11.
		resto = (d2 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2) {
			digito2 = 0;
		}
		else {
			digito2 = 11 - resto;
		}

		// Digito verificador do CPF que está sendo validado.
		String digitoVerificador = cpf.substring(cpf.length() - 2, cpf.length());

		// Concatenando o primeiro resto com o segundo.
		ndigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// comparar o digito verificador do cpf com o primeiro resto + o segundo
		// resto.
		return digitoVerificador.equals(ndigResult);
	}
	/**
	 * Realiza a validação do CNS.
	 *
	 * @param stringCNS número de CNS a ser validado
	 * @return true se o CNS é válido e false se não é válido
	 */
	public static boolean validarCNS(String stringCNS) {
		if (stringCNS.matches("[1-2]\\d{10}00[0-1]\\d")
					|| stringCNS.matches("[7-9]\\d{14}")) {
			return somaPonderada(stringCNS) % 11 == 0;
		}
		return false;
	}

	private static int somaPonderada(String texto) {
		char[] cs = texto.toCharArray();
		int soma = 0;
		for (int i = 0; i < cs.length; i++) {
			soma += Character.digit(cs[i], 10) * (15 - i);
		}
		return soma;
	}
	/**
	 * Método que valida se o texto passado tem números ou pontos.
	 * @param String texto a ser validado
	 * @return boolean true se string está certa e false se está errada
	 */
	public static boolean validarTextoSemNumerosECaracteresEspeciais(String texto ){
		return texto.matches("^[^\\d\\.]+$");
	}
	
	
}

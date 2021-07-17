package br.org.cancer.modred.configuration;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe responsável pela implementação da conversão fonética utilizando o
 * Algoritmo DoubleMetaphone.
 * 
 * @author Cintia Oliveira
 *
 */
public class ConversaoFonetica {

    private static final String VOGAIS = "[aeiouy]";
    private static final String CONSOANTES = "[^aeiouy]";
    private static final String[] DUPLICADAS = { "c", "g", "l", "t", "p", "d", "f", "j", "k", "m",
            "v", "n", "z", "s",
            "r", "b" };
    private static final Map<Pattern, String> ACENTUACOES = new HashMap<>();
    private static final Map<Pattern, String> SUBSTITUICOES_REGEX = new LinkedHashMap<>();

    static {
        ACENTUACOES.put(Pattern.compile("[âãáàä]"), "a");
        ACENTUACOES.put(Pattern.compile("[éèêë]"), "e");
        ACENTUACOES.put(Pattern.compile("[íìîï]"), "i");
        ACENTUACOES.put(Pattern.compile("[óòôõö]"), "o");
        ACENTUACOES.put(Pattern.compile("[úùûü]"), "u");

        // tratamento para erro de digitação cao inves de ção
        SUBSTITUICOES_REGEX.put(Pattern.compile("chr"), "cr");
        SUBSTITUICOES_REGEX.put(Pattern.compile("^s(" + CONSOANTES + ".*)"), "es$1");
        SUBSTITUICOES_REGEX.put(Pattern.compile("ch$"), "k");
        SUBSTITUICOES_REGEX.put(Pattern.compile("y"), "i");

        // Como o W possui som de V ou V dependendo da palavra,
        // é necessário normalizar o V e o U,
        // para isso substituimos W por V
        SUBSTITUICOES_REGEX.put(Pattern.compile("w"), "v");
        // e substituimos o U seguindo de uma vogal por V
        SUBSTITUICOES_REGEX.put(Pattern.compile("^u(" + VOGAIS + ".*)"),
                "v$1");

        // Double metaphone portugues
        SUBSTITUICOES_REGEX.put(Pattern.compile("çao$"), "S");
        SUBSTITUICOES_REGEX.put(Pattern.compile("cao$"), "S");
        SUBSTITUICOES_REGEX.put(Pattern.compile("ph"), "F");
        SUBSTITUICOES_REGEX.put(Pattern.compile("ct"), "T");
        SUBSTITUICOES_REGEX.put(Pattern.compile("th"), "T");
        SUBSTITUICOES_REGEX.put(Pattern.compile("gt"), "T");
        SUBSTITUICOES_REGEX.put(Pattern.compile("lh"), "L");
        SUBSTITUICOES_REGEX.put(Pattern.compile("l"), "L");
        SUBSTITUICOES_REGEX.put(Pattern.compile("g[ei]"), "J");
        SUBSTITUICOES_REGEX.put(Pattern.compile("g[ao]"), "G");
        SUBSTITUICOES_REGEX.put(Pattern.compile("gu[ei]"), "G");
        SUBSTITUICOES_REGEX.put(Pattern.compile("g"), "G");
        SUBSTITUICOES_REGEX.put(Pattern.compile("ch"), "X");
        SUBSTITUICOES_REGEX.put(Pattern.compile("sh"), "X");
        SUBSTITUICOES_REGEX.put(Pattern.compile("ck"), "K");
        SUBSTITUICOES_REGEX.put(Pattern.compile("c[ei]"), "S");
        SUBSTITUICOES_REGEX.put(Pattern.compile("c[aou]"), "K");
        SUBSTITUICOES_REGEX.put(Pattern.compile("c"), "K");
        SUBSTITUICOES_REGEX.put(Pattern.compile("ç"), "S");
        SUBSTITUICOES_REGEX.put(Pattern.compile("chior"), "KIOR");
        SUBSTITUICOES_REGEX.put(Pattern.compile("cq"), "K");
        SUBSTITUICOES_REGEX.put(Pattern.compile("^r"), "R");
        SUBSTITUICOES_REGEX.put(Pattern.compile("r"), "R");
        SUBSTITUICOES_REGEX.put(Pattern.compile("z$"), "S");
        SUBSTITUICOES_REGEX.put(Pattern.compile("^z"), "Z");
        SUBSTITUICOES_REGEX.put(Pattern.compile("z"), "S");
        SUBSTITUICOES_REGEX.put(Pattern.compile("nh"), "N");
        SUBSTITUICOES_REGEX.put(Pattern.compile("n"), "N");
        SUBSTITUICOES_REGEX.put(Pattern.compile("s"), "S");
        SUBSTITUICOES_REGEX.put(Pattern.compile("q"), "K");
        SUBSTITUICOES_REGEX.put(Pattern.compile("b"), "B");
        SUBSTITUICOES_REGEX.put(Pattern.compile("t"), "T");
        SUBSTITUICOES_REGEX.put(Pattern.compile("p"), "P");
        SUBSTITUICOES_REGEX.put(Pattern.compile("d$"), "");
        SUBSTITUICOES_REGEX.put(Pattern.compile("d"), "D");
        SUBSTITUICOES_REGEX.put(Pattern.compile("f"), "F");
        SUBSTITUICOES_REGEX.put(Pattern.compile("j"), "J");
        SUBSTITUICOES_REGEX.put(Pattern.compile("k"), "K");
        SUBSTITUICOES_REGEX.put(Pattern.compile("m"), "M");
        SUBSTITUICOES_REGEX.put(Pattern.compile("v"), "V");
        SUBSTITUICOES_REGEX.put(Pattern.compile("k"), "K");
        // abacaxi, Aleixo, Alexandre
        SUBSTITUICOES_REGEX.put(Pattern.compile("[ckglrxaeiou][aeiou]x"), "X");
        SUBSTITUICOES_REGEX.put(Pattern.compile("^x"), "X");
        SUBSTITUICOES_REGEX.put(Pattern.compile("x$"), "KS");
        SUBSTITUICOES_REGEX.put(Pattern.compile("xc[ei]"), "S");
        SUBSTITUICOES_REGEX.put(Pattern.compile("^x(" + VOGAIS + ")"), "Z");
        SUBSTITUICOES_REGEX.put(Pattern.compile("^ex(" + CONSOANTES + ")"), "S");
        // mexe, mexido
        SUBSTITUICOES_REGEX.put(Pattern.compile("x[ei]"), "X");
        // sexo, anexo
        SUBSTITUICOES_REGEX.put(Pattern.compile("x[ou]"), "KS");
        SUBSTITUICOES_REGEX.put(Pattern.compile("x"), "S");
        SUBSTITUICOES_REGEX.put(Pattern.compile("l(" + CONSOANTES + ")"), "");
        SUBSTITUICOES_REGEX.put(Pattern.compile("h"), "");
        SUBSTITUICOES_REGEX.put(Pattern.compile("^(h?a)"), "A");
        SUBSTITUICOES_REGEX.put(Pattern.compile("^(h?[ei])"), "I");
        SUBSTITUICOES_REGEX.put(Pattern.compile("^(h?[ou])"), "U");

    }

    private ConversaoFonetica() {
        super();
    }

    /**
     * @return instância da classe
     */
    public static ConversaoFonetica getInstance() {
        return new ConversaoFonetica();
    }

    /**
     * Converte um palavra para a sua representação fonética.
     * 
     * @param palavra
     *            a ser convetida
     * @return palavra convertida
     */
    public String converter(String palavra) {
        String resultado = null;

        if (StringUtils.isNotBlank(palavra)) {
            resultado = palavra.toLowerCase();
            resultado = removerAcentos(resultado);
            resultado = removerLetrasDuplicadas(resultado);
            resultado = aplicarSubstituicoes(resultado);
            resultado = tratarVogaisIniciais(resultado);
            resultado = resultado.toUpperCase();
        }

        return resultado;
    }

    private String removerAcentos(String palavra) {
        for (Entry<Pattern, String> substituicao : ACENTUACOES.entrySet()) {
            String letraSemAcento = substituicao.getValue();
            Pattern acentos = substituicao.getKey();
            palavra = acentos.matcher(palavra).replaceAll(letraSemAcento);
        }

        return palavra;
    }

    private String aplicarSubstituicoes(String palavra) {
        String resultado = palavra;

        for (Entry<Pattern, String> substituicao : SUBSTITUICOES_REGEX.entrySet()) {
            Pattern removido = substituicao.getKey();
            String substituta = substituicao.getValue();
            resultado = removido.matcher(resultado).replaceAll(substituta);
        }

        return resultado;
    }

    private String removerLetrasDuplicadas(String palavra) {
        String resultado = palavra;

        for (String letra : DUPLICADAS) {
            Matcher matcher = compile(letra + letra + "+", CASE_INSENSITIVE).matcher(resultado);
            resultado = matcher.replaceAll(letra);
        }

        return resultado;
    }

    private String tratarVogaisIniciais(String palavra) {
        String resultado = palavra;

        if (resultado.length() > 1) {
            String inicial = resultado.substring(0, 1);
            if (inicial.matches(VOGAIS)) {
                resultado = resultado.replaceFirst(inicial, inicial.toUpperCase());
            }

            // removendo as demais vogais
            resultado = Pattern.compile(VOGAIS).matcher(resultado).replaceAll("");
        }

        return resultado;
    }

}
package br.org.cancer.redome.tarefa.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.tarefa.model.ValorNmdp;
import br.org.cancer.redome.tarefa.persistence.IValorNmdpRepository;
import br.org.cancer.redome.tarefa.service.IValorNmdpService;

/**
 * Classe de serviços para entidade ValorNmdp.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ValorNmdpService implements IValorNmdpService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValorNmdpService.class);
	//private static final Logger LOGGER = LoggerFactory.getLogger("log-job-importacao");

	@Autowired
	private IValorNmdpRepository valorNmdpRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ValorNmdp> recuperarValoresImportados(File arquivo) throws IOException {
		LOGGER.info("Início importação dos valores");

		if (!arquivo.exists()) {
			throw new FileNotFoundException("arquivo não encontrado");
		}

		LocalDate dataDoArquivo = obterVersao(arquivo);

		Stream<String> streamLine = Files.lines(arquivo.toPath());

		List<ValorNmdp> lista1 = streamLine
				.skip(3)
				.filter(linha -> !StringUtils.isBlank(linha))
				.map(linha -> obterValorNmdp(linha, dataDoArquivo))
				.collect(Collectors.toList());

		LOGGER.info("Início importação dos valores lista1 " + lista1.size());
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy"); 
		LocalDate data = LocalDate.parse("28/12/20", formato);

		List<ValorNmdp> lista2 = null; //valorNmdpRepository.buscarDiferencaTabelaValorNmdp();

		LOGGER.info("Início importação dos valores lista1 " + lista2.size());
		
		List<ValorNmdp> intersect = lista1.stream()
                .filter(lista2::contains)
                .collect(Collectors.toList());
		
		LOGGER.info("Lista nmdp: " + intersect.size());
		
		streamLine.close();

		return lista1;
		
	}

   public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
   
//   public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
//	    final Set<Object> seen = new HashSet<>();
//	    return t -> seen.add(keyExtractor.apply(t));
//	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void salvar(List<ValorNmdp> lista) {
		if (lista != null) {

			LOGGER.info("Salvando no banco atualização dos valores NMDP");

			int qtd = 0;
			for (ValorNmdp valor : lista) {
			//	valorNmdpRepository.salvar(valor);

//				if (qtd > 0 && qtd % ApplicationConfiguration.BATCH_SIZE == 0) {
					//LOGGER.info("Executando Flush, quantidade {}", qtd);
					valorNmdpRepository.flush();
				//	valorNmdpRepository.clear();
//				}
				
				qtd++;
			}

			LOGGER.info("Término da atualização dos valores NMDP");
		}

	}

	@Override
	public List<ValorNmdp> listarValorNmdpPorDataUltimaAtualizacaoArquivo(LocalDate dataUltimaAtualizacaoArquivo) {
		return valorNmdpRepository.findByDataUltimaAtualizacaoArquivo(dataUltimaAtualizacaoArquivo);
	}
	
	@Override
	public void desmarcarFlagSplitCriado() {
		valorNmdpRepository.updateFlagSplitCriado(0L);		
	}
	
	/**
	 * retorna a entidade populada com a linha do arquivo.
	 * 
	 * @param linha
	 * @return
	 */
	private ValorNmdp obterValorNmdp(String linha, LocalDate dataDoArquivo) {

		final ValorNmdp valorNmdp = new ValorNmdp();
		valorNmdp.setDataUltimaAtualizacaoArquivo(dataDoArquivo);

		final Spliterator<String> campos = Arrays.spliterator(linha.split("	"));
		campos.tryAdvance(campo -> {
			valorNmdp.setAgrupado(StringUtils.isBlank(campo) ? 0L : 1L);
		});
		campos.tryAdvance(campo -> {
			valorNmdp.setId(campo);
		});
		campos.tryAdvance(campo -> {
			valorNmdp.setSubtipo(campo);
			valorNmdp.setQuantidadeSubTipo(new Long(campo.split("/").length));
		});

		return valorNmdp;
	}

	public LocalDate obterVersao(File arquivo) throws IOException {
		final LocalDate[] dataDoArquivo = { null };
		Stream<String> streamLine = Files.lines(arquivo.toPath());

		streamLine.findFirst().ifPresent(linha -> {
			LOGGER.info("obterVersao LINHA: {}", linha);
			String[] valores = linha.replaceAll("\"", "").split(":");
			String[] dataSplit = valores[1].trim().split("/");

			LOGGER.info("obterVersao DATA SPLIT: {}", dataSplit.toString());
			LOGGER.info("obterVersao DATA SPLIT SIZE: {}", dataSplit.length);
			dataDoArquivo[0] = LocalDate.of(Integer.parseInt("20" + dataSplit[2]),
					Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]));
		});

		streamLine.close();
		return dataDoArquivo[0];
	}

}

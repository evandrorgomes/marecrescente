package br.org.cancer.redome.tarefa.schedule;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.tarefa.service.IValorNmdpService;
import br.org.cancer.redome.tarefa.util.ArquivoUtil;

/**
 * Schedule para atualizar todos os valores para serem importados.
 * 
 * @author Filipe Paes
 *
 */
@Component
public class AtualizarValoresGeraisSchedule {

	private static final long DEZ_MINUTOS = 600000L;
	private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarValoresGeraisSchedule.class);

	private static final String CAMINHO_PARA_DOWNLOAD_NMDP = "https://bioinformatics.bethematchclinical.org/HLA/alpha.v3.zip";
	private static final String CAMINHO_PARA_DOWNLOAD_DNA = "https://raw.githubusercontent.com/ANHIG/IMGTHLA/Latest/wmda/rel_dna_ser.txt";
	private static final String CAMINHO_PARA_DOWNLOAD_G = " https://raw.githubusercontent.com/ANHIG/IMGTHLA/Latest/wmda/hla_nom_g.txt";
	private static final String CAMINHO_PARA_DOWNLOAD_P = " https://raw.githubusercontent.com/ANHIG/IMGTHLA/Latest/wmda/hla_nom_p.txt";

	private static final String ARQUIVO_COMPACTADO_NMDP = "nmdp.alpha.v3.zip";
	private static final String NOME_ARQUIVO_DNA = "dna.rel_dna_ser.txt";
	private static final String NOME_ARQUIVO_G = "dna.hla_nom_g.txt";
	private static final String NOME_ARQUIVO_P = "dna.hla_nom_p.txt";

	@Autowired
	private IValorNmdpService valorNmdpService;

	/**
	 * Método anotado para ser executado com um intervalo de 10 minutos entre o fim de uma execução e o início da próxima.
	 */
	//@Scheduled(fixedDelay = DEZ_MINUTOS)
	@Scheduled(cron = "0 14 17 * * *")
	@Transactional
	public void task() {
		LOGGER.info("Inicio da execução do job para atualização de valores de loci válidos.");

		try {
			LocalDateTime start = LocalDateTime.now();
			LOGGER.info("Início do Download");

			File arquivoBaixadoNMDP =  new File("/home/evandro/Downloads/" + ARQUIVO_COMPACTADO_NMDP );    
			if (arquivoBaixadoNMDP != null) {
				File arquivoDescompactadoNMDP = ArquivoUtil.descompctarArquivo(arquivoBaixadoNMDP);
	
				valorNmdpService.recuperarValoresImportados(arquivoDescompactadoNMDP);
				
				LOGGER.info("Final da rotina! ");
			}
			LOGGER.info("Tempo total de execução: {} minutos", ChronoUnit.MINUTES.between(start, LocalDateTime.now()));

		}
		catch (Exception e) {
			LOGGER.error("Erro ao rodar a aplicação", e);
		}

		LOGGER.info("Fim da execução do job para atualização de valores de loci válidos.");
	}
}

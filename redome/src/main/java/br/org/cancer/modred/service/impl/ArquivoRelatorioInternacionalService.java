package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ArquivoRelatorioInternacional;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.persistence.IArquivoRelatorioInternacionalRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IArquivoRelatorioInternacionalService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de negócios para arquivo de relatorio internacional.
 * @author Filipe Paes
 *
 */
@Service
public class ArquivoRelatorioInternacionalService extends   AbstractService<ArquivoRelatorioInternacional, Long> implements IArquivoRelatorioInternacionalService   {

	@Autowired
	private IArquivoRelatorioInternacionalRepository arquivoRelatorioInternacionalRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IStorageService storageService;

	@Autowired
	private IConfiguracaoService configuracaoService;
	
	@Autowired
	private IBuscaService buscaService;
	
	
	@Override
	public IRepository<ArquivoRelatorioInternacional, Long> getRepository() {
		return arquivoRelatorioInternacionalRepository;
	}


	@Override
	public String salvarArquivo(MultipartFile arquivo, ArquivoRelatorioInternacional arquivoRelatorio) {
		if (arquivo == null) {
			throw new BusinessException("arquivo.erro.nao.enviado");
		}
		
		if (arquivoRelatorio == null) {
			throw new BusinessException("arquivo_relatorio.erro.nao.enviado");
		}
		else if (arquivoRelatorio.getBusca() == null || arquivoRelatorio.getBusca().getId() == null ) {
			throw new BusinessException("arquivo_relatorio.busca.erro.nao.enviado");
		}

		try {
			
			
			ArrayList<CampoMensagem> resultadoValidacao = new ArrayList<CampoMensagem>();
			List<Configuracao> configuracoes = configuracaoService.listarConfiguracao(Configuracao.EXTENSAO_ARQUIVO_RELATORIO_INTERNACIONAL, Configuracao.TAMANHO_MAXIMO_RELATORIO_INTERNACIONAL);
			ArquivoUtil.validarArquivo(arquivo, resultadoValidacao, messageSource, configuracoes.get(0), configuracoes.get(1));


			String diretorio = enviarArquivoParaStorage(arquivoRelatorio.getBusca(), arquivo);
			
			arquivoRelatorio.setBusca(buscaService.findById(arquivoRelatorio.getBusca().getId()));
			arquivoRelatorio.setCaminho(diretorio);
			this.save(arquivoRelatorio);

			
			return diretorio;

		}
		catch (IOException e) {
			throw new BusinessException("arquivo.erro.upload.arquivo", e);
		}

	}
	
	
	
	/**
	 * Método para fazer o upload dos arquivos de laudo do drafft para o storage.
	 * 
	 * @param listaArquivos
	 * @param timeStampMillis
	 * @return lista com nome dos arquivos
	 * @throws IOException
	 */
	private String enviarArquivoParaStorage(Busca busca, MultipartFile arquivo) throws IOException {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();

		
		String diretorio = StorageService.DIRETORIO_RELATORIO_INTERNACIONAL_STORAGE + "/" + busca.getId();
		String nomeArquivo = ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo);
		storageService.upload(nomeArquivo, diretorio, arquivo);
		
		return diretorio + "/" + nomeArquivo;
	}


	@Override
	public List<ArquivoRelatorioInternacional> listarArquivoPorBusca(Long id) {
		final Busca busca = buscaService.obterBusca(id);
				
		return find(new Equals<Long>("busca.id", busca.getId()));
	}

	@Override
	public void excluirArquivo(Long idArquivoRelatorioInternacional) {
		ArquivoRelatorioInternacional relatorioInternacional = this.findById(idArquivoRelatorioInternacional);
		this.storageService.removerArquivo(relatorioInternacional.getCaminho());
		this.getRepository().delete(relatorioInternacional);
	}


	@Override
	public File obterZip(Long idArquivoRelatorioInternacional) throws BusinessException {
		ArquivoRelatorioInternacional arquivoRelatorioInternacional = this.findById(idArquivoRelatorioInternacional);
		File arquivoZip = null;
		try {
			arquivoZip = storageService.obterArquivoZip( StorageService.DIRETORIO_RELATORIO_INTERNACIONAL_STORAGE + "/" + arquivoRelatorioInternacional.getBusca().getId()
					, ArquivoUtil.obterNomeArquivoComTimestampPorCaminhoArquivo(arquivoRelatorioInternacional.getCaminho()));
			
		}
		catch (IOException e) {
			throw new BusinessException(AppUtil.getMensagem(messageSource,
					"arquivo.exame.erroaogerararquivo"));
		}
		return arquivoZip;
	}


	@Override
	public File obterArquivo(Long idArquivoRelatorioInternacional) throws BusinessException {
		ArquivoRelatorioInternacional arquivoRelatorioInternacional = this.findById(idArquivoRelatorioInternacional);
		File arquivo = storageService.download(arquivoRelatorioInternacional.getCaminho());
		return arquivo;
	}


	@Override
	public File obterTodosArquivosZipadosPorBusca(Long idBusca) throws BusinessException {
		Filter<Long> filtroBusca = new Equals<Long>("busca.id", idBusca);
		List<ArquivoRelatorioInternacional> arquivosPorBusca = this.find(filtroBusca);
		
		if(!arquivosPorBusca.isEmpty()){
			File arquivo = null;
			String[] arquivos = new String[arquivosPorBusca.size()];
			for (int i = 0; i < arquivosPorBusca.size(); i++) {
				ArquivoRelatorioInternacional arquivoRelatorio = arquivosPorBusca.get(i);
				arquivos[i] = ArquivoUtil.obterNomeArquivoComTimestampPorCaminhoArquivo(arquivoRelatorio.getCaminho());
			}
			try {
				arquivo = storageService.obterArquivoZip(StorageService.DIRETORIO_RELATORIO_INTERNACIONAL_STORAGE + "/" + arquivosPorBusca.get(0).getBusca().getId(), arquivos);
			}
			catch (IOException e) {
				throw new BusinessException(AppUtil.getMensagem(messageSource,
						"arquivo.exame.erroaogerararquivo"));
			}
			
			return arquivo;
		}
		else{
			return null;
		}
	}

	
}

package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ArquivoExame;
import br.org.cancer.modred.persistence.IArquivoExameRepository;
import br.org.cancer.modred.service.IArquivoExameService;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;

/**
 * Classe de serviço para Arquivo Exame.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class ArquivoExameService implements IArquivoExameService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoExameService.class);

	@Autowired
	private IArquivoExameRepository arquivoExameRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IStorageService storageService;

	@Autowired
	private IConfiguracaoService configuracaoService;

	@Autowired
	private IUsuarioService usuarioService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArquivoExame obterArquivoExameParaDownload(Long arquivoExameId) {
		return arquivoExameRepository.obterArquivoExameParaDownload(arquivoExameId);
	}

	@Override
	public ArquivoExame obterArquivoExame(Long id) {
		return arquivoExameRepository.obterArquivoExameParaDownload(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File obterArquivoDoStorage(Long id) throws BusinessException {
		ArquivoExame arquivoExame = obterArquivoExame(id);
		if (arquivoExame == null) {
			throw new BusinessException("arquivo.exame.nao.encontrado");
		}
		File arquivo = storageService.download(arquivoExame.obterNomeArquivo(), arquivoExame
				.obterCaminhoCompletoArquivo());
		return arquivo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File obterArquivoZipadoDoStorage(Long id) throws BusinessException {
		ArquivoExame arquivoExame = obterArquivoExame(id);
		File arquivo = null;
		if (arquivoExame == null) {
			throw new BusinessException(AppUtil.getMensagem(messageSource,
					"arquivo.exame.nao.encontrado"));
		}
		try {
			arquivo = storageService.obterArquivoZip(arquivoExame.obterCaminhoCompletoArquivo(),
					arquivoExame.obterNomeArquivo());
		}
		catch (IOException e) {
			throw new BusinessException("arquivo.exame.erroaogerararquivo");
		}

		return arquivo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IPacienteService#salvarArquivoNoDraft(java. util.List)
	 */
	@Override
	public String salvarArquivo(MultipartFile arquivo) {
		if (arquivo == null) {
			throw new BusinessException("arquivo.erro.nao.enviado");
		}

//		try {
			LOGGER.info("Inicio do upload de arquivos no storage.");
			ArquivoUtil.validarArquivoExame(Arrays.asList(arquivo), messageSource, configuracaoService);

			long timeStampMillis = Instant.now().toEpochMilli();

			String nomeCompletoDoArquivo = AppUtil.removerAcentuacao(
					ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo));

			
			
			
//			String diretorio = StorageService.DIRETORIO_USUARIO_STORAGE + "/" + usuarioService.obterUsuarioLogadoId() + "/"
//					+ StorageService.DIRETORIO_RASCUNHO_STORAGE;
//			enviarArquivoParaStorage(arquivo, diretorio, nomeCompletoDoArquivo);

			return nomeCompletoDoArquivo;

//		}
//		catch (IOException e) {
//			throw new BusinessException("arquivo.erro.upload.arquivo", e);
//		}
//		finally {
//			LOGGER.info("Fim do upload de arquivos no storage.");
//		}

	}

	/**
	 * Método para fazer o upload dos arquivos de laudo do drafft para o storage.
	 * 
	 * @param listaArquivos
	 * @param timeStampMillis
	 * @return lista com nome dos arquivos
	 * @throws IOException
	 */
	private void enviarArquivoParaStorage(MultipartFile arquivo, String diretorio, String nomeCompletoDoArquivo)
			throws IOException {

		storageService.upload(nomeCompletoDoArquivo, diretorio, arquivo);
	}
	
}

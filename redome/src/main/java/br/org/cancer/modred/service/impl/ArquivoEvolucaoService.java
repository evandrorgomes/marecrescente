package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.cloud.objectstorage.services.s3.model.ObjectMetadata;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ArquivoEvolucao;
import br.org.cancer.modred.persistence.IArquivoEvolucaoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IArquivoEvolucaoService;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;


/**
 * Interface para métodos de negócio de Arquivos de Evolução. 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class ArquivoEvolucaoService  extends AbstractService<ArquivoEvolucao, Long> implements IArquivoEvolucaoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoEvolucaoService.class);
	
	@Autowired
	private IArquivoEvolucaoRepository arquivoEvolucaoRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IStorageService manipulacaoArquivoService;

	@Autowired
	private IConfiguracaoService configuracaoService;

	@Autowired
	private IUsuarioService usuarioService;
	
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

//			ArquivoUtil.obterCaminhoArquivoRascunho(usuarioService.obterUsuarioLogadoId(), nomeCompletoDoArquivo);
			
			String diretorio = "" + "/" + StorageService.DIRETORIO_USUARIO_STORAGE + "/" + usuarioService.obterUsuarioLogadoId() + "/"
					+ StorageService.DIRETORIO_RASCUNHO_STORAGE;
			
//			File transferFile = new File(diretorio + "/" + arquivo.getOriginalFilename()); 
//			try {
//				if(transferFile.mkdirs()) {
//					arquivo.transferTo(transferFile);
//					System.out.println(transferFile.mkdirs());
//				}
//				
//			} catch (IllegalStateException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			try {
				enviarArquivoParaStorage(arquivo, diretorio, nomeCompletoDoArquivo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return nomeCompletoDoArquivo;

//		}
//		catch (IOException e) {
//			throw new BusinessException("arquivo.erro.upload.arquivo", e);
//		}
//		finally {
//			LOGGER.info("Fim do upload de arquivos no storage.");
//		}
	}

	@Override
	public IRepository<ArquivoEvolucao, Long> getRepository() {
		return arquivoEvolucaoRepository;
	}

	
	private void enviarArquivoParaStorage(MultipartFile arquivo, String diretorio, String nomeCompletoDoArquivo)
			throws IOException {
		
		if (StringUtils.isBlank(arquivo.getName())) {
			throw new BusinessException("erro.arquivo.nome.invalido");
		}

		if (StringUtils.isBlank(diretorio)) {
			throw new BusinessException("erro.arquivo.diretorio.invalido");
		}

		ObjectMetadata metadata = new ObjectMetadata();        
	    metadata.setContentLength(arquivo.getSize());
	    metadata.setContentType(arquivo.getContentType());
	    
	    	    
	    	    
//	    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, diretorio + "/" + nome, arquivo.getInputStream(), metadata);
//	    PutObjectResult resultado = storageConnection.getCliente().putObject(putObjectRequest);

//		if (resultado.getETag() == null) {
//			throw new BusinessException("arquivo.erro.nao.enviado");
//		}

		
//		manipulacaoArquivoService.upload(nomeCompletoDoArquivo, diretorio, arquivo);
	}

	@Override
	public File obterArquivoDoStorage(Long id) throws BusinessException {
		ArquivoEvolucao arquivoEvolucao = this.findById(id);
		arquivoEvolucao.setRmr(arquivoEvolucao.getEvolucao().getPaciente().getRmr());
		File arquivo = manipulacaoArquivoService.download(arquivoEvolucao.obterNomeArquivo(), arquivoEvolucao.obterCaminhoCompletoArquivo());
		return arquivo;
	}
	
	

}

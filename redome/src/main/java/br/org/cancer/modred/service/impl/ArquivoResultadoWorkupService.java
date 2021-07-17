package br.org.cancer.modred.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.model.ResultadoWorkup;
import br.org.cancer.modred.service.IArquivoResultadoWorkupService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Implementação da classe de negócios de arquivo de laudo de resultado de workup.
 * @author Filipe Paes
 *
 */
@Service
public class ArquivoResultadoWorkupService implements IArquivoResultadoWorkupService {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;
	
	@Autowired
	private ConfiguracaoService configuracaoService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@Override
	public String salvarArquivoWorkup(ResultadoWorkup resultado, MultipartFile arquivo){
		if(resultado == null){
			throw new BusinessException("erro.resultado.workup.arquivo_exame_workup_obrigatorio");
		}
		
		if(arquivo ==null){
			throw new BusinessException("erro.resultado.workup.arquivo_laudo_workup_obrigatorio");
		}
		
		ArrayList<CampoMensagem> resultadoValidacao = new ArrayList<CampoMensagem>();
		List<Configuracao> configuracoes = configuracaoService.listarConfiguracao(Configuracao.EXTENSAO_ARQUIVO_RESULTADO_WORKUP, Configuracao.TAMANHO_ARQUIVO_RESULTADO_WORKUP);
		ArquivoUtil.validarArquivo(arquivo, resultadoValidacao, messageSource, configuracoes.get(0), configuracoes.get(1));
		
		if(!resultadoValidacao.isEmpty()){
			throw new ValidationException("erro.resultado.workup.arquivo_invalido", resultadoValidacao);
		}
		
		String caminhoArquivo = null;
		try{
			caminhoArquivo = enviarArquivoParaStorage(resultado, arquivo);
		}
		catch(IOException e){
			throw new BusinessException("erro.resultado.workup.arquivo_nao_enviao_storage");
		}
		return caminhoArquivo;
	}
	
	private String enviarArquivoParaStorage(ResultadoWorkup resultado, MultipartFile arquivo) throws IOException {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();

		Long idPedido = resultado.getPedidoWorkup();
		Long idResultado = resultado.getId();
		
		String diretorio = StorageService.DIRETORIO_PEDIDO + "/" + idPedido + "/" + StorageService.DIRETORIO_RESULTADO_WORKUP + "/" + idResultado;
		String nomeArquivo = ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo);
		storageService.upload(nomeArquivo, diretorio, arquivo);
		
		return diretorio + "/" + nomeArquivo;
	}

	@Override
	public void excluirArquivoWorkup(String caminho) {
		if(StringUtils.isBlank(caminho)){
			throw new BusinessException("erro.resultado.workup.arquivo_exame_workup_obrigatorio");
		}
		if (storageService.verificarExistenciaArquivo(caminho)) {
			storageService.removerArquivo(caminho);
		}
	}
}

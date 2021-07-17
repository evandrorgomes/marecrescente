package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openstack4j.api.storage.ObjectStorageObjectService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.model.storage.object.options.ObjectListOptions;
import org.openstack4j.model.storage.object.options.ObjectLocation;
import org.openstack4j.model.storage.object.options.ObjectPutOptions;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.configuration.IStorageConnection;
import br.org.cancer.modred.configuration.StorageConnection;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.service.IStorageService;

/**
 * Serviço para execução das operações no Storage.
 * 
 * @author Cintia Oliveira
 *
 */
public class StorageService implements IStorageService<SwiftObject> {

	/**
	 * Variável que representa o diretório de armazenamento dos exames do paciente no storage do usuario para o draft.
	 */
	public static String DIRETORIO_RASCUNHO_STORAGE = "RASCUNHO";
	
	/**
	 * Variável que representa o diretório de armazenamento do usuario.
	 */
	public static String DIRETORIO_USUARIO_STORAGE = "USUARIO";
	
	/**
	 * Variável que representa o diretório de armazenamento dos exames do paciente.
	 */
	public static String DIRETORIO_PACIENTE_EXAME_STORAGE = "EXAMES";
	
	
	/**
	 * Variável que representa o diretório de armazenamento das evoluções do paciente.
	 */
	public static String DIRETORIO_PACIENTE_EVOLUCAO_STORAGE = "EVOLUCAO";
	
	/**
	 * Variável que representa o diretório de armazenamento do paciente.
	 */
	public static String DIRETORIO_PACIENTE_STORAGE = "PACIENTE";
	
	/**
	 * Variável que representa o diretório de armazenamento de resultado de workup.
	 */
	public static String DIRETORIO_RESULTADO_WORKUP = "RESULTADO";
	
	/**
	 * Variável que representa o diretório de armazenamento de pedido de workup.
	 */
	public static String DIRETORIO_PEDIDO = "PEDIDO_WORKUP";
	
	/**
	 * Variável que representa o diretório de armazenamento de pedido de logistica.
	 */
	public static String DIRETORIO_PEDIDO_LOGISTICA = "PEDIDO_LOGISTICA";
	
	
	/**
	 * Variável que representa o diretório de armazenamento de avaliacao camara técnica.
	 */
	public static String DIRETORIO_AVALIACAO_CAMARA_TECNICA = "DIRETORIO_AVALIACAO_CAMARA_TECNICA";
	
	/**
	 * Variável que representa o diretório de armazenamento de pedido de logistica.
	 */
	public static String DIRETORIO_VOUCHER_PEDIDO_LOGISTICA = "VOUCHER";
	
	/**
	 * Variável que representa o diretório de armazenamento das prescrições do paciente.
	 */
	public static String DIRETORIO_PACIENTE_PRESCRICAO = "PRESCRICOES";
	
	/**
	 * Variável que representa o diretório de armazenamento de pedidos de transporte.
	 */
	public static String DIRETORIO_PEDIDO_TRANSPORTE = "TRANSPORTES";
	
	/**
	 * Variável que representa o diretório de armazenamento de resultado de pedido IDM.
	 */
	public static String DIRETORIO_RESULTADO_IDM = "RESULTADO_IDM";
	
	/**
	 * Variável que representa o diretório de armazenamento dos pedidos de IDM.
	 */
	public static String DIRETORIO_LAUDO_IDM = "LAUDOS";
	
	/**
	 * Variável que representa o diretório de armazenamento dos exames do doador. 
	 * (NACIONAL, INTERNACIONAL, CORDÃO_INTERNACIONAL, CORDÃO_NACIONAL).
	 */
	public static String DIRETORIO_DOADOR_EXAME_STORAGE = "EXAMES";
	
	/**
	 * Variável que representa o diretório de armazenamento dos doadores.
	 */
	public static String DIRETORIO_DOADORES_STORAGE = "DOADORES";
	
	/**
	 * Variável que representa o diretório de armazenamento dos CRMs dos médicos.
	 */
	public static String DIRETORIO_MEDICO_CRM_STORAGE = "CRM_MEDICO";
	
	/**
	 * Variável que representa o diretório de armazenamento de relatórios internacionais.
	 */
	public static String DIRETORIO_RELATORIO_INTERNACIONAL_STORAGE = "DIRETORIO_RELATORIO_INTERNACIONAL_STORAGE";
	

	private StorageConnection storageConnection;	

	@SuppressWarnings("rawtypes")
	public StorageService(IStorageConnection storageConnection) {
		this.storageConnection = (StorageConnection) storageConnection;		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void upload(String nome, String diretorio, MultipartFile arquivo) throws IOException {

		if (StringUtils.isBlank(nome)) {
			throw new BusinessException("erro.arquivo.nome.invalido");
		}

		if (StringUtils.isBlank(diretorio)) {
			throw new BusinessException("erro.arquivo.diretorio.invalido");
		}

		if (arquivo == null) {
			throw new BusinessException("erro.arquivo.nulo");
		}

		String etag = obterObjectService().put(obterNomeContainer(), nome,
				Payloads.create(arquivo.getInputStream()),
				ObjectPutOptions.create().contentType(arquivo.getContentType()).path(diretorio));

		if (etag == null) {
			throw new BusinessException("arquivo.erro.nao.enviado");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moverArquivoExame(Long usuarioId, Long rmr, String caminhoArquivo)
			throws BusinessException {
		this.moverArquivo(usuarioId, rmr, caminhoArquivo, StorageService.DIRETORIO_PACIENTE_EXAME_STORAGE);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moverArquivoEvolucao(Long usuarioId, Long rmr, String caminhoArquivo)
			throws BusinessException {
		this.moverArquivo(usuarioId, rmr, caminhoArquivo, StorageService.DIRETORIO_PACIENTE_EVOLUCAO_STORAGE);
	}
	
	private void moverArquivo(Long usuarioId, Long rmr, String caminhoArquivo, String diretorioDestino){
		if (usuarioId == null) {
			throw new BusinessException("erro.arquivo.identificacaousuario.invalida");
		}

		if (rmr == null) {
			throw new BusinessException("erro.arquivo.identificacaopaciente.invalida");
		}

		if (StringUtils.isEmpty(caminhoArquivo)) {
			throw new BusinessException("erro.arquivo.caminhoarquivo.invalido");
		}

		String caminhoArquivoOrigem = obterCaminhoArquivoCompleto(StorageService.DIRETORIO_USUARIO_STORAGE, usuarioId,
				StorageService.DIRETORIO_RASCUNHO_STORAGE,
				obterNomeArquivo(caminhoArquivo));

		String caminhoArquivoDestino = obterCaminhoArquivoCompleto(StorageService.DIRETORIO_PACIENTE_STORAGE, rmr,
				diretorioDestino,
				caminhoArquivo);

		ObjectLocation arquivoOrigem = ObjectLocation.create(obterNomeContainer(),
				caminhoArquivoOrigem);

		ObjectLocation arquivoDestino = ObjectLocation.create(obterNomeContainer(),
				caminhoArquivoDestino);

		String etag = obterObjectService().copy(arquivoOrigem, arquivoDestino);

		if (etag != null) {
			Boolean arquivoDeletadoComSucesso = removerArquivo(arquivoOrigem);
			if (!arquivoDeletadoComSucesso) {
				throw new BusinessException("erro.arquivo.ao.deletar");
			}
		}
		else {
			throw new BusinessException("erro.arquivo.ao.mover");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removerArquivosPorIdUsuario(Long usuarioId) {
		if (usuarioId == null) {
			throw new BusinessException("erro.arquivo.identificacaousuario.invalida");
		}
		String caminhoPasta = obterCaminhoArquivoCompleto(StorageService.DIRETORIO_USUARIO_STORAGE, usuarioId,
				StorageService.DIRETORIO_RASCUNHO_STORAGE, "");
		List<? extends SwiftObject> files = localizarArquivosPorDiretorio(caminhoPasta);
		files.forEach(f -> {
			removerArquivo(f.getName());
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SwiftObject> localizarArquivosPorDiretorio(String caminhoPasta) {
		return (List<SwiftObject>) storageConnection.getCliente()
				.objectStorage()
				.objects()
				.list(obterNomeContainer(), ObjectListOptions.create().startsWith(caminhoPasta));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SwiftObject> localizarArquivosPorIdUsuario(Long idUsuario) {
		return localizarArquivosPorDiretorio(obterCaminhoArquivoCompleto(StorageService.DIRETORIO_USUARIO_STORAGE, idUsuario,
				StorageService.DIRETORIO_RASCUNHO_STORAGE, ""));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removerArquivo(String caminhoArquivo) {

		ObjectLocation arquivoOrigem = ObjectLocation.create(obterNomeContainer(), caminhoArquivo);
		ActionResponse response = storageConnection.getCliente().objectStorage().objects()
				.delete(arquivoOrigem);

		if (!response.isSuccess()) {			
			throw new BusinessException("erro.arquivo.ao.deletar", response.getFault());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File download(String nome, String diretorio) {
		if (StringUtils.isBlank(nome)) {
			throw new BusinessException("erro.arquivo.nome.invalido");
		}
		if (StringUtils.isBlank(diretorio)) {
			throw new BusinessException("erro.arquivo.diretorio.invalido");
		}

		if (verificarExistenciaArquivo(diretorio + "/" + nome)) {
			SwiftObject arquivo = obterObjectService().get(
					obterNomeContainer(), diretorio + "/" + nome);
			File file = new File(nome);
			try {
				arquivo.download().writeToFile(file);
				return file;
			} 
			catch (IOException e) {
				throw new BusinessException("arquivo.exame.erroaogerararquivo", e);
			}

		}
		else {
			throw new BusinessException("erro.arquivo.naoencontrado");
		}


	}
	
	@Override
	public File download(String caminhoArquivo) {
		return download(obterNomeArquivo(caminhoArquivo), obterDiretorioArquivo(caminhoArquivo));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File obterArquivoZip(String diretorio, String... arquivos) throws BusinessException,
			IOException {
		if (arquivos == null || arquivos.length < 1) {
			throw new BusinessException("erro.arquivo.aomenosum");
		}
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		String fileZipName = timeStampMillis + ".zip";
		File arquivo = new File(fileZipName);
		FileOutputStream fileOutput = new FileOutputStream(arquivo);
		ZipOutputStream zipOutput = new ZipOutputStream(fileOutput);
		byte[] buffer = new byte[1024];
		for (int i = 0; i < arquivos.length; i++) {
			String fileName = arquivos[i];
			File download = this.download(fileName, diretorio);
			ZipEntry ze = new ZipEntry(fileName);
			zipOutput.putNextEntry(ze);
			InputStream input = new FileInputStream(download);
			int len;
			while (( len = input.read(buffer) ) > 0) {
				zipOutput.write(buffer, 0, len);
			}
			input.close();
		}
		zipOutput.closeEntry();
		zipOutput.close();
		return arquivo;
	}
	
	@Override
	public File obterArquivoZip(List<File> arquivos) throws BusinessException, IOException {
		if (CollectionUtils.isEmpty(arquivos)) {
			throw new BusinessException("erro.requisicao");
		}
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		String fileZipName = timeStampMillis + ".zip";
		File arquivo = new File(fileZipName);
		
		FileOutputStream fileOutput = new FileOutputStream(arquivo);
		ZipOutputStream zipOutput = new ZipOutputStream(fileOutput);
		
		for (File download : arquivos) {
			byte[] buffer = new byte[1024];
			ZipEntry ze = new ZipEntry(download.getName());
			zipOutput.putNextEntry(ze);
			InputStream input = new FileInputStream(download);
			int len;
			while (( len = input.read(buffer) ) > 0) {
				zipOutput.write(buffer, 0, len);
			}
			input.close();
		}
		
		zipOutput.closeEntry();
		zipOutput.close();
		return arquivo;
	}

	@Override
	public Boolean verificarExistenciaArquivo(String caminhoArquivo) {		
		return obterObjectService().get(
				obterNomeContainer(), caminhoArquivo) != null;
	}

	@Override
	public Boolean verificarExistenciaArquivoPorIdUsuario(Long idUsuario,
			String caminhoArquivo) {
		return verificarExistenciaArquivo(obterCaminhoArquivoCompleto(StorageService.DIRETORIO_USUARIO_STORAGE, idUsuario,
				StorageService.DIRETORIO_RASCUNHO_STORAGE, caminhoArquivo));
	}

	@Override
	public void removerArquivoPorIdUsuario(Long idUsuario, String caminhoArquivo) {
		removerArquivo(obterCaminhoArquivoCompleto(StorageService.DIRETORIO_USUARIO_STORAGE, idUsuario,
				StorageService.DIRETORIO_RASCUNHO_STORAGE,
				caminhoArquivo));
	}

	private String obterNomeArquivo(String caminhoArquivo) {
		if (caminhoArquivo.contains("/")){
			return caminhoArquivo.substring(caminhoArquivo.lastIndexOf("/") + 1);
		}
		throw new IllegalArgumentException("");
	}
	
	private String obterDiretorioArquivo(String caminhoArquivo) {
		if (caminhoArquivo.contains("/")) {
			return caminhoArquivo.substring(0, caminhoArquivo.lastIndexOf("/"));
		}
		throw new IllegalArgumentException("");
	}

	private boolean removerArquivo(ObjectLocation arquivoOrigem) {
		return obterObjectService().delete(arquivoOrigem).isSuccess();
	}

	private String obterCaminhoArquivoCompleto(String donoArquivo, Long identificacao,
			String tipoArquivo, String nomeArquivo) {
		return String.join("/", donoArquivo, identificacao.toString(), tipoArquivo,
				nomeArquivo);
	}

	
	private String obterNomeContainer() {
		return null; //cloudConfig.getValue("storage_container_name");
	}

	private ObjectStorageObjectService obterObjectService() {
		return storageConnection.getCliente().objectStorage().objects();
	}

	public static String obterCaminhoArquivoRascunho(Long usuarioId, String nomeArquivo) {
		return String.join("/", StorageService.DIRETORIO_USUARIO_STORAGE, usuarioId.toString(),
				StorageService.DIRETORIO_RASCUNHO_STORAGE, nomeArquivo);
	}

	@Override
	public String[] obterNomeEDiretorioPorCaminho(String caminho) {
		String[] nomeArquivoArray = caminho.split("/");
		String nomeDoArquivo = nomeArquivoArray[nomeArquivoArray.length - 1];
		String diretorio = caminho.substring(0, caminho.indexOf(nomeDoArquivo) -1);
		return new String[]{nomeDoArquivo, diretorio};
	}

	@Override
	public void moverArquivo(String origem, String destino) {
		
		if (StringUtils.isEmpty(origem)) {
			throw new BusinessException("erro.arquivo.caminhoarquivo.invalido");
		}
		if (StringUtils.isEmpty(destino)) {			
			throw new BusinessException("erro.arquivo.caminhoarquivo.invalido");
		}

		ObjectLocation arquivoOrigem = ObjectLocation.create(obterNomeContainer(),
				origem);

		ObjectLocation arquivoDestino = ObjectLocation.create(obterNomeContainer(),
				destino);

		String etag = obterObjectService().copy(arquivoOrigem, arquivoDestino);

		if (etag != null) {
			Boolean arquivoDeletadoComSucesso = removerArquivo(arquivoOrigem);
			if (!arquivoDeletadoComSucesso) {
				throw new BusinessException("erro.arquivo.ao.deletar");
			}
		}
		else {
			throw new BusinessException("erro.arquivo.ao.mover");
		}
		
	}
}
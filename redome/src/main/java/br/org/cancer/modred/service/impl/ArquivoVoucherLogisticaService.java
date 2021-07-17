package br.org.cancer.modred.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ArquivoVoucherLogistica;
import br.org.cancer.modred.persistence.IArquivoVoucherLogisticaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IArquivoVoucherLogisticaService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.ArquivoUtil;

/**
 * Classe de funcionalidades envolvendo a entidade ArquivoVoucherLogistica.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ArquivoVoucherLogisticaService extends AbstractService<ArquivoVoucherLogistica, Long> implements IArquivoVoucherLogisticaService {

	@Autowired
	private IArquivoVoucherLogisticaRepository arquivoVoucherLogisticaRepository;

	@Override
	public IRepository<ArquivoVoucherLogistica, Long> getRepository() {
		return arquivoVoucherLogisticaRepository;
	}
	
	@Autowired
	private IStorageService storageService;
	
	@Override
	public void excluir(Long id) {
		ArquivoVoucherLogistica voucher = arquivoVoucherLogisticaRepository.findById(id).get();
		if (voucher == null){
			throw new BusinessException("erro.arquivoVoucherLogistica.nao_localizado");
		}
		voucher.setExcluido(true);
		arquivoVoucherLogisticaRepository.save(voucher);
	}

	@Override
	public void excluirVoucherDoStorage(Long pedidoLogisticaId, String nomeArquivo) {
		String caminho = ArquivoUtil.obterDiretorioArquivosVoucherPedidoLogistica(pedidoLogisticaId);		
		storageService.removerArquivo(caminho + "/" + nomeArquivo);
	}

	@Override
	public File obterArquivoNoStorage(Long idArquivo) {
		ArquivoVoucherLogistica arquivoVoucher = arquivoVoucherLogisticaRepository.findById(idArquivo).get();
		
		return storageService.download(arquivoVoucher.getCaminho());
	}
	
	@Override
	public List<ArquivoVoucherLogistica> listarPorPedidoLogistica(Long idPedidoLogistica) {
		return arquivoVoucherLogisticaRepository.findAllByPedidoLogisticaId(idPedidoLogistica);
	}

}

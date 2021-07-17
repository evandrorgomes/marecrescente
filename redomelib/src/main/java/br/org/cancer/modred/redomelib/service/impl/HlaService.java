package br.org.cancer.modred.redomelib.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.redomelib.exception.AleloException;
import br.org.cancer.modred.redomelib.exception.BlankException;
import br.org.cancer.modred.redomelib.persistence.IHlaRepository;
import br.org.cancer.modred.redomelib.service.IHlaService;
import br.org.cancer.modred.redomelib.vo.ValorDnaNmdpVo;
import br.org.cancer.modred.redomelib.vo.ValorNmdpVo;

@Service
@Transactional(dontRollbackOn= {BlankException.class, AleloException.class})
public class HlaService implements IHlaService {
	
	@Autowired
	private IHlaRepository hlaRepository;
	
	public Boolean validarHla(String codigoLocusId, String alelo, Boolean revalidar) {

		if (!validarLocus(codigoLocusId, alelo)) {
			return false; 
		}
		else {
			if (!"-".equals(alelo)) {
				ValorDnaNmdpVo valorDnaNmdpVo = hlaRepository.findByLocusAndValor(codigoLocusId, alelo); 
				if (valorDnaNmdpVo != null && valorDnaNmdpVo.estaAtivo()) {
					return true;
				}
				else if (valorDnaNmdpVo == null && !revalidar) {
					String valor = alelo.indexOf(":") > -1? alelo.split(":")[1]:alelo;
					if (isNmdp(valor)) {
						//verifica se o valor nmdp é valido
						criarValoresNmdpValidos(valor);
						return validarHla(codigoLocusId, alelo, true);
					}
				}
				else if (valorDnaNmdpVo == null) {
					hlaRepository.inserirValorInvalido(codigoLocusId, alelo);
				}
				
				return false;
			}
			return true;
		}		
	}
	
	public Boolean validarHla(String codigoLocusId, String alelo1, String alelo2) {
		
		if (!isApenasUmAleloBlank(alelo1, alelo2)) {
			throw new BlankException();		
		}
		
		final AleloException aleloException = new AleloException();
		aleloException.setAlelo1Valido(validarHla(codigoLocusId, alelo1, false));
		aleloException.setAlelo2Valido(validarHla(codigoLocusId, alelo2, false));
		
		if (!aleloException.isAlelo1Valido() || !aleloException.isAlelo2Valido())
			throw aleloException;
			
		return true;
	}
	
	private boolean isApenasUmAleloBlank(String alelo1, String alelo2) {
		return !( "-".equals(alelo1) && "-".equals(alelo2) );
	}
	
	
	private boolean validarLocus(String codigoLocusId, String alelo) {
		return ( !StringUtils.isEmpty(codigoLocusId)
				&& !StringUtils.isEmpty(alelo) );
	}
	
	private Boolean isNmdp(String valor) {
		return StringUtils.isAlpha(valor);
	}
	
	private void criarValoresNmdpValidos(String codigo) {
		ValorNmdpVo valorNmdpVo = hlaRepository.findByNmdpCodigo(codigo);
		if (valorNmdpVo != null && !valorNmdpVo.getSplitCriado()) {
			hlaRepository.criarValorDnaNmdpValido(valorNmdpVo);
			hlaRepository.marcarNmdpComSplitCriado(codigo);
			
		}
	}

}

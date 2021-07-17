package br.org.cancer.modred.service.impl;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.persistence.IConfiguracaoRepository;
import br.org.cancer.modred.service.IConfiguracaoService;

/**
 * Implementacao da classe de negocio para Configuracao.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ConfiguracaoService implements IConfiguracaoService{
    
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracaoService.class);
	
    @Autowired
    private IConfiguracaoRepository configuracaoRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Configuracao obterConfiguracao(String chave) {
        if (chave == null || StringUtils.isEmpty(chave)) {
            throw new BusinessException("erro.configuracao.chave.nao.informada");
        }
        
        Configuracao configuracao = configuracaoRepository.findById(chave).orElse(null);
        if (configuracao == null) {
            throw new BusinessException("erro.configuracao.chave.inexistente");
        }
        return configuracao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Configuracao> listarConfiguracao(String... chaves) {
        if (chaves == null) {
            throw new BusinessException("erro.configuracao.chave.nao.informada");
        }
        else {
            for (String chave: chaves) {
                if (StringUtils.isEmpty(chave)) {
                    throw new BusinessException("erro.configuracao.chave.nao.informada");
                }
            }
        }
        
        List<Configuracao> configuracoes = configuracaoRepository.findByChaveIn(chaves);
        if (configuracoes.isEmpty() || configuracoes.size() != chaves.length) {
            throw new BusinessException("erro.configuracao.chave.inexistente");
        }
        
        return configuracoes;
    }

	@Override
	public boolean verificarDentroPrazo(LocalDateTime dataOcorrencia, String chaveLimiteTempo) {
		Configuracao configuracao = obterConfiguracao(chaveLimiteTempo);
		Long intervaloOcorrencia = SECONDS.between(dataOcorrencia, LocalDateTime.now());
		
		if(!NumberUtils.isNumber(configuracao.getValor())){
			LOGGER.error("Chave " + chaveLimiteTempo + " não é um valor numérico.");
			throw new BusinessException("erro.interno");
		}
		
		Long valorLimite = Long.valueOf(configuracao.getValor());
		return (intervaloOcorrencia <= valorLimite);
	}
 }
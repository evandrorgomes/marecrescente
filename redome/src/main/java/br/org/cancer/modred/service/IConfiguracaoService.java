package br.org.cancer.modred.service;

import java.time.LocalDateTime;
import java.util.List;

import br.org.cancer.modred.model.Configuracao;

/**
 * Interface do serviço de configuração.
 * 
 * @author bruno.sousa
 *
 */
public interface IConfiguracaoService {
    
    /**
     * Buscar configuração por chave.
     * @param String identicacao da configuração
     * @return Configuracao - objeto de configuração da aplicação
     */
    Configuracao obterConfiguracao(String chave);
    
    /**
     * Método para listar todas as configurações ou de chaves específicas. 
     * @param String[] - lista de chaves
     * @return List<Configuracao> listagem de configurações
     */    
    List<Configuracao> listarConfiguracao(String... chaves);
    
    /**
     * Verifica se a data da ocorrência está dentro dos limites
     * estabelecidos pelo parâmetro chave que informa o limite
     * para validade.
     * Utilizado para verificar se datas de inclusão da última evolução
     * está dentro do prazo a considerá-la uma atualização para o paciente,
     * por exemplo.
     * 
     * @param dataOcorrencia data em que ocorreu o evento. 
     * @param chaveLimite chave que identifica o limite de tempo para ser validado.
     * @return TRUE, caso este dentro do prazo, FALSE caso contrário.
     */
    boolean verificarDentroPrazo(LocalDateTime dataOcorrencia, String chaveLimite);
}

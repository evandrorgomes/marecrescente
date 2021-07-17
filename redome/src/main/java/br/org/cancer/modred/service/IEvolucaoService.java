package br.org.cancer.modred.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.EvolucaoDto;
import br.org.cancer.modred.model.Cid;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Interface para disponibilizar métodos para classe de negócio da entidade.
 * Evolucao
 * 
 * @author Rafael Pizão
 *
 */
public interface IEvolucaoService extends IService<Evolucao, Long>{

    /**
     * Valida as evoluções de forma a garantir a integridade das informações.
     * contidas na entidade.
     * 
     * @param evolucao
     * @param campos
     */
    void validar(Cid cid, List<Evolucao> evolucoes, List<CampoMensagem> campos);

    /**
     * Valida a evolução INICIAL de forma a garantir a integridade das.
     * informações contidas na entidade.
     * 
     * @param evolucao
     * @param campos
     */
    void validarEvolucaoInicial(Cid cid, Evolucao evolucao, List<CampoMensagem> campos);

    /**
     * Busca a evolução que possua o ID informado.
     * 
     * @param evolucaoId
     *            a ID a ser buscado no banco
     * @return uma evolução com o mesmo ID informado
     */
    Evolucao obterEvolucaoPorId(Long evolucaoId);

    /**
     * Método para persistir uma evolução.
     * 
     * @param Evolucao a ser persistido
     * @param arquivos arquivos de evolução
     * @return evolução após inclusão no banco.
     */
    Evolucao salvar(Evolucao evolucao, List<MultipartFile> arquivos) throws IOException;

    /**
     * Devolve uma listagem ordenada pela data da evolução de forma decrescente.
     * 
     * @param rmr
     *            do paciente
     * @return List<Evolucao> listagem de estagios
     */
    List<Evolucao> buscarEvolucaoPorRMR(Long rmr);

    /**
     * Carrega VO para tela de evoluções.
     * 
     * @param rmr
     * @return EvolucaoVo
     */
    EvolucaoDto carregarEvolucaoPorRMR(Long rmr);

    /**
     * Obtem a ultima evolucao do paciente.
     * 
     * @param rmr
     * @return Evolucao
     */
    Evolucao obterUltimaEvolucaoDoPaciente(Long rmr);

    /**
     * Método para persistir uma evolução anexada a uma resposta de pendência.
     * 
     * @param evolucao
     *            - Evolução a ser persistido
     * @param pendencias
     *            - lista de pendências a ser respondida
     * @param resposta
     *            - texto opcional da resposta
     * @param respondePendencia
     *            - flag que diz se a pendência será respondida ou não
     */
    void salvar(Evolucao evolucao, List<Pendencia> pendencias, String resposta,
            Boolean respondePendencia, List<MultipartFile> arquivos) throws IOException;
        
    
    /**
     * Método para verificar se a ultima evolução cadastrada está dentro do prazo mínimo para inclusão da prescrição.
     * 
     * @param rmr
     * @return True - Se a evolução estiver dentro do prazo;
     */
    Boolean isUltimaEvolucaoAtualizada(Long rmr);

    
    /**
     * Envia o arquivo de evolução para o storage e o relaciona com uma evolução.
     * @param listaArquivos arquivos de evolução.
     * @param evolucao - evolução a ser relcaionada com o arquivo.
     */
    void salvarArquivoEvolucao(List<MultipartFile> listaArquivos, Evolucao evolucao);
    
    /**
     * Verifica se a evolução está atualizada, levando em consideração o parâmetro
     * informado.
     * 
     * @param paciente paciente a ser verificado se existe evolução atualizada.
     * @return TRUE, caso exista.
     */
    boolean verificarSeEvolucaoAtualizada(final Paciente paciente);
    
    /**
     * Obtém a última evolução do paciente caso a evolução esteja atualizada 
     * dentro do período máximo permitido. 
     * @param rmr identificação do paciente.
     * @return caso a evolução do paciente esteja atulizada retorna a última evolução senão retorna null.
     */
    Evolucao obterUltimaEvolucaoAtualizadaComVerificacaoDePeriodoMaximoSemAtualizacao(Long rmr);
}

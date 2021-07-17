package br.org.cancer.modred.controller.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.EvolucaoView;

/**
 * Classe para DTO para somente id, data e motivo de evolução.
 * @author Filipe Paes
 *
 */
public class EvolucaoListaDto {
    /**
     * id da evolucao.
     */
    @JsonView(EvolucaoView.ListaEvolucao.class)
    private Long id;
    /**
     * Data da evolução.
     */
    @JsonView(EvolucaoView.ListaEvolucao.class)
    private LocalDateTime dataCriacao;
    /**
     * Descrição do motivo.
     */
    @JsonView(EvolucaoView.ListaEvolucao.class)
    private String motivo;
    
    @JsonView(EvolucaoView.ListaEvolucao.class)
    private String condicaoPaciente;
    
    
    /**
     * Construtor padrão.
     */
    public EvolucaoListaDto() {}

    /**
     * Construtor sobrecarregado.
     * @param id id
     * @param dataCriacao dat
     * @param motivo moti
     */
    public EvolucaoListaDto(Long id, LocalDateTime dataCriacao, String motivo) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.motivo = motivo;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the dataCriacao
     */
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    /**
     * @param dataCriacao the dataCriacao to set
     */
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }
    
    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getCondicaoPaciente() {
        return condicaoPaciente;
    }

    public void setCondicaoPaciente(String condicaoPaciente) {
        this.condicaoPaciente = condicaoPaciente;
    }
}

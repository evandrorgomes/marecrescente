package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PacienteView;

/**
 * Classe de persistencia para a tabela Responsavel.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@SequenceGenerator(name = "RESPONSAVEL_RESPID_GENERATOR", sequenceName = "SQ_RESP_ID",
                   allocationSize = 1)
@Table(name = "RESPONSAVEL")
@Audited
public class Responsavel implements Serializable {

    private static final long serialVersionUID = 6217828144484361646L;
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESPONSAVEL_RESPID_GENERATOR")
    @Column(name = "RESP_ID")
    @JsonView(PacienteView.DadosPessoais.class)
    private Long id;
    /**
     * CPF do responsável pelo paciente.
     */
    @Column(name = "RESP_TX_CPF")
    @CPF
    @JsonView({PacienteView.Detalhe.class, PacienteView.DadosPessoais.class, PacienteView.Rascunho.class})
    private String cpf;
    /**
     * Nome do responsável pelo paciente.
     */
    @Column(name = "RESP_TX_NOME")
    @Pattern(regexp = "^[^\\d\\.]+$", message = "{nome.caracteresInvalidos}")
    @JsonView({PacienteView.Detalhe.class, PacienteView.DadosPessoais.class, PacienteView.Rascunho.class})
    private String nome;
    /**
     * Nível de parentesco que o responsável tem com o paciente.
     */
    @Column(name = "RESP_TX_PARENTESCO")
    @Pattern(regexp = "^[^\\d\\.]+$", message = "{nome.caracteresInvalidos}")
    @JsonView({PacienteView.Detalhe.class, PacienteView.DadosPessoais.class, PacienteView.Rascunho.class})
    private String parentesco;

    public Responsavel() {}

    /**
     * Construtor sobrecarregado.
     * 
     * @param id
     *            id
     * @param cpf
     *            cpf
     * @param nome
     *            nome
     * @param parentesco
     *            pa
     */
    public Responsavel(Long id, String cpf, String nome, String parentesco) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.parentesco = parentesco;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf
     *            the cpf to set
     */
    public void setCpf(String cpf) {
        if ("".equals(cpf)) {
            this.cpf = null;
        }
        else {
            this.cpf = cpf;
        }
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome
     *            the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
        if (this.nome != null) {
        	this.nome = this.nome.toUpperCase();
        }
    }

    /**
     * @return the parentesco
     */
    public String getParentesco() {
        return parentesco;
    }

    /**
     * @param parentesco
     *            the parentesco to set
     */
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( cpf == null ) ? 0 : cpf.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Responsavel other = (Responsavel) obj;
        if (cpf == null) {
            if (other.cpf != null) {
                return false;
            }
        }
        else
            if (!cpf.equals(other.cpf)) {
                return false;
            }
        return true;
    }
}
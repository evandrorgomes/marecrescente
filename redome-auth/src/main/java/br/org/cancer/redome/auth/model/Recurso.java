/**
 * 
 */
package br.org.cancer.redome.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

/**
 * Classe que representa os recursos da aplicação. Utilizada para concessão de acesso as funcionalidades.
 * 
 * @author Cintia Oliveira
 *
 */
@Entity
@Table(name = "RECURSO")
public class Recurso implements Serializable {

	private static final long serialVersionUID = 7577504195547854098L;
	public static final String VISUALIZAR_FICHA_PACIENTE = "VISUALIZAR_FICHA_PACIENTE";
	public static final String CONSULTAR_EVOLUCOES_PACIENTE = "CONSULTAR_EVOLUCOES_PACIENTE";
	public static final String CONSULTAR_EXAMES_PACIENTE = "CONSULTAR_EXAMES_PACIENTE";
	public static final String VISUALIZAR_IDENTIFICACAO_COMPLETA = "VISUALIZAR_IDENTIFICACAO_COMPLETA";
	public static final String VISUALIZAR_IDENTIFICACAO_COMPLETA_DOADOR = "VISUALIZAR_IDENTIFICACAO_COMPLETA_DOADOR";
	public static final String VISUALIZAR_IDENTIFICACAO_PARCIAL = "VISUALIZAR_IDENTIFICACAO_PARCIAL";
	public static final String AVALIAR_PACIENTE = "AVALIAR_PACIENTE";
	public static final String CADASTRAR_EXAMES_PACIENTE = "CADASTRAR_EXAMES_PACIENTE";
	public static final String CADASTRAR_EVOLUCOES_PACIENTE = "CADASTRAR_EVOLUCOES_PACIENTE";
	public static final String CONFERIR_EXAME_HLA = "CONFERIR_EXAME_HLA";
	public static final String CADASTRAR_PACIENTE = "CADASTRAR_PACIENTE";
	public static final String CONSULTAR_AVALIACAO = "CONSULTAR_AVALIACOES";
	public static final String VISUALIZAR_NOTIFICACOES = "VISUALIZAR_NOTIFICACOES";
	public static final String VISUALIZAR_PENDENCIAS_AVALIACAO = "VISUALIZAR_PENDENCIAS_AVALIACAO";
	public static final String CONSULTAR_PACIENTE = "CONSULTAR_PACIENTE";
	public static final String CANCELAR_BUSCA = "CANCELAR_BUSCA";
	public static final String PACIENTES_PARA_PROCESSO_BUSCA = "PACIENTES_PARA_PROCESSO_BUSCA";
	public static final String ENRIQUECER_DOADOR = "ENRIQUECER_DOADOR";
	public static final String VISUALIZAR_AVALIACAO = "VISUALIZAR_AVALIACAO";
	public static final String EDITAR_DADOS_PESSOAIS = "EDITAR_DADOS_PESSOAIS";
	public static final String EDITAR_CONTATO_PACIENTE = "EDITAR_CONTATO_PACIENTE";
	public static final String CONTACTAR_FASE_2 = "CONTACTAR_FASE_2";
	public static final String INATIVAR_DOADOR_ENRIQUECIMENTO = "INATIVAR_DOADOR_ENRIQUECIMENTO";
	public static final String INATIVAR_DOADOR_FASE2 = "INATIVAR_DOADOR_FASE2";
	public static final String ADICIONAR_CONTATO_TELEFONICO_DOADOR = "ADICIONAR_CONTATO_TELEFONICO_DOADOR";
	public static final String ADICIONAR_ENDERECO_DOADOR = "ADICIONAR_ENDERECO_DOADOR";
	public static final String EXCLUIR_ENDERECO_CONTATO = "EXCLUIR_ENDERECO_CONTATO";
	public static final String EXCLUIR_TELEFONE_CONTATO = "EXCLUIR_TELEFONE_CONTATO";
	public static final String EXCLUIR_EMAIL_CONTATO = "EXCLUIR_EMAIL_CONTATO";
	public static final String ADICIONAR_EMAIL_DOADOR = "ADICIONAR_EMAIL_DOADOR";
	public static final String ATUALIZAR_IDENTIFICACAO_DOADOR = "ATUALIZAR_IDENTIFICACAO_DOADOR";
	public static final String ATUALIZAR_DADOS_PESSOAIS_DOADOR = "ATUALIZAR_DADOS_PESSOAIS_DOADOR";
	public static final String TRATAR_PEDIDO_WORKUP = "TRATAR_PEDIDO_WORKUP";
	public static final String CANCELAR_PEDIDO_WORKUP_CT = "CANCELAR_PEDIDO_WORKUP_CT";
	public static final String CONTATO_PASSIVO = "CONTATO_PASSIVO";
	public static final String CONSULTAR_DOADOR = "CONSULTAR_DOADOR";
	public static final String CADASTRAR_RESULTADO_WORKUP = "CADASTRAR_RESULTADO_WORKUP";
	public static final String CONSULTAR_RESULTADO_WORKUP = "CONSULTAR_RESULTADO_WORKUP";
	public static final String AVALIAR_RESULTADO_WORKUP = "AVALIAR_RESULTADO_WORKUP";
	public static final String EFETUAR_LOGISTICA = "EFETUAR_LOGISTICA";
	public static final String TRATAR_COLETA_DOADOR = "TRATAR_COLETA_DOADOR";
	public static final String AVALIAR_PEDIDO_COLETA = "AVALIAR_PEDIDO_COLETA";
	public static final String AVALIAR_WORKUP_DOADOR = "AVALIAR_WORKUP_DOADOR";
	public static final String EXCLUIR_RESSALVA_DOADOR = "EXCLUIR_RESSALVA_DOADOR";
	public static final String CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR = "CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR";
	public static final String CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA = "CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA";
	public static final String ANALISE_MATCH = "ANALISE_MATCH";
	public static final String VISUALIZAR_LOG_EVOLUCAO = "VISUALIZAR_LOG_EVOLUCAO";
	public static final String ADICIONAR_COMENTARIO_MATCH = "ADICIONAR_COMENTARIO_MATCH";
	public static final String VISUALIZAR_FAVORITO_MATCH = "VISUALIZAR_FAVORITO_MATCH";
	public static final String VISUALIZAR_RESSALVA_MATCH = "VISUALIZAR_RESSALVA_MATCH";	
	public static final String VISUALIZAR_COMENTARIO_MATCH = "VISUALIZAR_COMENTARIO_MATCH";
	public static final String VISUALIZAR_OUTROS_PROCESSOS = "VISUALIZAR_OUTROS_PROCESSOS";
	public static final String SOLICITAR_FASE_2_NACIONAL = "SOLICITAR_FASE_2_NACIONAL";
	public static final String SOLICITAR_FASE_3_NACIONAL = "SOLICITAR_FASE_3_NACIONAL";
	public static final String DISPONIBILIZAR_DOADOR = "DISPONIBILIZAR_DOADOR";
	public static final String CANCELAR_FASE_2 = "CANCELAR_FASE_2";
	public static final String CANCELAR_FASE_3 = "CANCELAR_FASE_3";
	public static final String CANCELAR_FASE_2_INTERNACIONAL = "CANCELAR_FASE_2_INTERNACIONAL";
	public static final String CADASTRAR_PRESCRICAO = "CADASTRAR_PRESCRICAO";
	public static final String MANTER_CENTRO_TRANSPLANTE = "MANTER_CENTRO_TRANSPLANTE";
	public static final String MANTER_USUARIO = "MANTER_USUARIO";
	public static final String MANTER_PERFIL = "MANTER_PERFIL";
	public static final String CONSULTAR_AVALIACOES = "CONSULTAR_AVALIACOES";
	public static final String CONTACTAR_FASE_3 = "CONTACTAR_FASE_3";
	public static final String VISUALIZAR_PENDENCIA_WORKUP = "VISUALIZAR_PENDENCIA_WORKUP";
	public static final String AVALIAR_PRESCRICAO = "AVALIAR_PRESCRICAO";
	public static final String CADASTRAR_RECEBIMENTO_COLETA = "CADASTRAR_RECEBIMENTO_COLETA";
	public static final String DIALOGO_BUSCA = "DIALOGO_BUSCA";
	public static final String CRIAR_PRESCRICAO = "CRIAR_PRESCRICAO";
	public static final String ENCONTRAR_CENTRO_TRANSPLANTADOR = "ENCONTRAR_CENTRO_TRANSPLANTADOR";
	public static final String SOLICITAR_LOGISTICA_MATERIAL = "SOLICITAR_LOGISTICA_MATERIAL";
	public static final String SOLICITAR_FASE3_PACIENTE = "SOLICITAR_FASE3_PACIENTE";
	public static final String RECUSAR_CT_BUSCA = "RECUSAR_CT_BUSCA";
	public static final String RECEBER_COLETA_LABORATORIO = "RECEBER_COLETA_LABORATORIO";
	public static final String CADASTRAR_TIPO_AMOSTRA_EXAME = "CADASTRAR_TIPO_AMOSTRA_EXAME";
	public static final String VISUALIZAR_MOTIVOS_CANCELAMENTO_BUSCA = "VISUALIZAR_MOTIVOS_CANCELAMENTO_BUSCA";
	public static final String CADASTRAR_DOADOR_INTERNACIONAL = "CADASTRAR_DOADOR_INTERNACIONAL";
	public static final String ALTERAR_DOADOR_INTERNACIONAL = "ALTERAR_DOADOR_INTERNACIONAL";
	public static final String SOLICITAR_FASE_2_INTERNACIONAL = "SOLICITAR_FASE_2_INTERNACIONAL";
	public static final String CADASTRAR_RESULTADO_PEDIDO_FASE_2_INTERNACIONAL = "CADASTRAR_RESULTADO_PEDIDO_FASE_2_INTERNACIONAL";
	public static final String SOLICITAR_FASE_3_INTERNACIONAL = "SOLICITAR_FASE_3_INTERNACIONAL";
	public static final String INATIVAR_DOADOR_INTERNACIONAL = "INATIVAR_DOADOR_INTERNACIONAL";
	public static final String CADASTRAR_RESULTADO_PEDIDO_CT = "CADASTRAR_RESULTADO_PEDIDO_CT";
	public static final String CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL = "CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL";
	public static final String TRATAR_PEDIDO_COLETA_INTERNACIONAL = "TRATAR_PEDIDO_COLETA_INTERNACIONAL";
	public static final String TRATAR_PEDIDO_WORKUP_INTERNACIONAL = "TRATAR_PEDIDO_WORKUP_INTERNACIONAL";
	public static final String CANCELAR_PEDIDO_COLETA_CT = "CANCELAR_PEDIDO_COLETA_CT";
	public static final String CADASTRAR_CORDAO_NACIONAL = "CADASTRAR_CORDAO_NACIONAL";
	public static final String AGENDAR_TRANSPORTE_MATERIAL = "AGENDAR_TRANSPORTE_MATERIAL";
	public static final String AUTORIZACAO_PACIENTE = "AUTORIZACAO_PACIENTE";
	public static final String CADASTRAR_BUSCA_PRELIMINAR = "CADASTRAR_BUSCA_PRELIMINAR";
	public static final String VISUALIZAR_MATCH_PRELIMINAR = "VISUALIZAR_MATCH_PRELIMINAR";
	public static final String ANALISE_MATCH_PRELIMINAR = "ANALISE_MATCH_PRELIMINAR";
	public static final String EFETUAR_LOGISTICA_INTERNACIONAL = "EFETUAR_LOGISTICA_INTERNACIONAL";
	public static final String EXCLUIR_ENDERECO_CONTATO_CENTRO_TRANSPLANTE = "EXCLUIR_ENDERECO_CONTATO_CENTRO_TRANSPLANTE";
	public static final String EXCLUIR_TELEFONE_CONTATO_CENTRO_TRANSPLANTE = "EXCLUIR_TELEFONE_CONTATO_CENTRO_TRANSPLANTE";
	public static final String CONSULTAR_PRE_CADASTRO_MEDICO = "CONSULTAR_PRE_CADASTRO_MEDICO";
	public static final String VISUALIZAR_DETALHE_PRE_CADASTRO_MEDICO = "VISUALIZAR_DETALHE_PRE_CADASTRO_MEDICO";
	public static final String REPROVAR_PRE_CADASTRO_MEDICO = "REPROVAR_PRE_CADASTRO_MEDICO";
	public static final String CONSULTAR_CADASTRO_MEDICO = "CONSULTAR_CADASTRO_MEDICO";
	public static final String VISUALIZAR_CADASTRO_MEDICO = "VISUALIZAR_CADASTRO_MEDICO";
	public static final String ALTERAR_CADASTRO_MEDICO = "ALTERAR_CADASTRO_MEDICO";
	public static final String APROVAR_PRE_CADASTRO_MEDICO = "APROVAR_PRE_CADASTRO_MEDICO";
	public static final String EXCLUIR_EMAIL_CONTATO_MEDICO = "EXCLUIR_EMAIL_CONTATO_MEDICO";
	public static final String EXCLUIR_TELEFONE_CONTATO_MEDICO = "EXCLUIR_TELEFONE_CONTATO_MEDICO";
	public static final String TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR = "TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR";
	public static final String CONSULTAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR = "CONSULTAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR";
	public static final String DETALHE_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR = "DETALHE_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR";
	public static final String RECUSAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR = "RECUSAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR";
	public static final String ACEITAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR = "ACEITAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR";
	public static final String AVALIAR_PACIENTE_CAMARA_TECNICA = "AVALIAR_PACIENTE_CAMARA_TECNICA";
	public static final String LISTAR_DISPONIBILIDADES = "LISTAR_DISPONIBILIDADES";
	public static final String CONSULTAR_AVALIAR_RESULTADO_WORKUP = "CONSULTAR_AVALIAR_RESULTADO_WORKUP";
	public static final String EDITAR_DIAGNOSTICO_PACIENTE = "EDITAR_DIAGNOSTICO_PACIENTE";
	public static final String EDITAR_MISMATCH_PACIENTE = "EDITAR_MISMATCH_PACIENTE";
	public static final String SOLICITAR_NOVA_BUSCA_PACIENTE = "SOLICITAR_NOVA_BUSCA_PACIENTE";
	public static final String AVALIAR_NOVA_BUSCA_PACIENTE = "AVALIAR_NOVA_BUSCA_PACIENTE";
	public static final String ALTERAR_USUARIO = "ALTERAR_USUARIO";
	public static final String CADASTRAR_USUARIO = "CADASTRAR_USUARIO";
	public static final String INATIVAR_USUARIO = "INATIVAR_USUARIO";
	public static final String VISTAR_CHECKLIST = "VISTAR_CHECKLIST";
	public static final String EDITAR_FASE2_INTERNACIONAL = "EDITAR_FASE2_INTERNACIONAL";
	public static final String LISTAR_ARQUIVOS_RELATORIO_BUSCA_INTERNACIONAL = "LISTAR_ARQUIVOS_RELATORIO_BUSCA_INTERNACIONAL";
	public static final String EXCLUIR_ARQUIVO_RELATORIO_BUSCA_INTERNACIONAL = "EXCLUIR_ARQUIVO_RELATORIO_BUSCA_INTERNACIONAL";
	public static final String CADASTRAR_ARQUIVO_RELATORIO_INTERNACIONAL = "CADASTRAR_ARQUIVO_RELATORIO_INTERNACIONAL";
	public static final String EXCLUSAO_TRANSPORTADORA = "EXCLUSAO_TRANSPORTADORA";
	public static final String CONSULTAR_TRANSPORTADORA = "CONSULTAR_TRANSPORTADORA";
	public static final String SALVAR_TRANSPORTADORA = "SALVAR_TRANSPORTADORA";
	public static final String SALVAR_LABORATORIO = "SALVAR_LABORATORIO";
	public static final String INATIVAR_COURIER = "INATIVAR_COURIER";
	public static final String CONSULTAR_COURIER = "CONSULTAR_COURIER";
	public static final String SALVAR_COURIER = "SALVAR_COURIER";
	public static final String VISUALIZAR_GENOTIPO_DIVERGENTE = "VISUALIZAR_GENOTIPO_DIVERGENTE";
	public static final String DESCARTAR_LOCUS_EXAME = "DESCARTAR_LOCUS_EXAME";
	public static final String INTEGRACAO_REDOME_WEB = "INTEGRACAO_REDOME_WEB";
	public static final String CONSULTAR_LABORATORIO = "CONSULTAR_LABORATORIO";	
	public static final String CONSULTA_PACIENTES_PARA_PROCESSO_BUSCA = "CONSULTA_PACIENTES_PARA_PROCESSO_BUSCA";
	public static final String CADASTRAR_ANALISE_MEDICA_DOADOR = "CADASTRAR_ANALISE_MEDICA_DOADOR";
	public static final String INTEGRACAO_JOB = "INTEGRACAO_JOB";
	public static final String CONSULTAR_CONTATO_SMS = "CONSULTAR_CONTATO_SMS";
	public static final String ATUALIZAR_STATUS_SMS_ENVIADO = "ATUALIZAR_STATUS_SMS_ENVIADO";
	public static final String VISUALIZAR_DASHBOARD = "VISUALIZAR_DASHBOARD";
	public static final String CRIAR_PEDIDO_ENVIO_PACIENTE_EMDIS = "CRIAR_PEDIDO_ENVIO_PACIENTE_EMDIS";
	public static final String CONSULTAR_PEDIDO_WORKUP_NACIONAL = "CONSULTAR_PEDIDO_WORKUP_NACIONAL";
	public static final String CONSULTAR_PESQUISA_WMDA = "CONSULTAR_PESQUISA_WMDA"; 
	
	@Id
	@Column(name = "RECU_ID")
	@NotNull
	private Integer id;

	@Column(name = "RECU_TX_SIGLA", length = 50)
	@NotNull
	private String sigla;

	@Column(name = "RECU_TX_DESCRICAO", length = 100)
	@NotNull
	private String descricao;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( sigla == null ) ? 0 : sigla.hashCode() );
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
		Recurso other = (Recurso) obj;
		if (sigla == null) {
			if (other.sigla != null) {
				return false;
			}
		}
		else {
			if (!sigla.equals(other.sigla)) {
				return false;
			}
		}
		return true;
	}

}

package br.org.cancer.modred.persistence.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.configuration.ConversaoFonetica;
import br.org.cancer.modred.controller.dto.ContatoPacienteDTO;
import br.org.cancer.modred.controller.dto.LogEvolucaoDTO;
import br.org.cancer.modred.controller.dto.PacienteTarefaDTO;
import br.org.cancer.modred.controller.dto.PedidosPacienteInvoiceDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.ContatoTelefonicoPaciente;
import br.org.cancer.modred.model.EnderecoContatoPaciente;
import br.org.cancer.modred.model.FuncaoTransplante;
import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Responsavel;
import br.org.cancer.modred.model.StatusPaciente;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.persistence.IPacienteRepositoryCustom;
import br.org.cancer.modred.util.AppUtil;

/**
 * @author bruno.sousa Classe de persistência para controlar a paginação da busca do paciente.
 */
public class IPacienteRepositoryImpl implements IPacienteRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IPacienteRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;

	private MessageSource messageSource;

	@Override
	public Page<Paciente> findByNomeContainingAndMedicoResponsavelUsuarioIdOrderByNome(String nome, Long idUsuario, 
			Pageable pageable) {
		LOGGER.info("inicio da busca de paciente no repositorio");

		String[] nomeFonetizado = {""};
		if (nome != null && !"".equals(nome)) {
			Stream.of(nome.split(" ")).forEach(palavra -> {
				nomeFonetizado[0] += ConversaoFonetica.getInstance().converter(palavra) + " ";
			});
			nomeFonetizado[0] = nomeFonetizado[0].trim();
		}

		String hqlCount = "select count(p.rmr) from Paciente p left outer join p.buscas b join p.status s "
				+ " where p.medicoResponsavel.usuario.id = " + idUsuario
				+ " and b in (select max(b1) from Busca b1 where b1.paciente = p)";
		if (!"".equals(nomeFonetizado[0])) {
			hqlCount += " and upper(p.nomeFonetizado) "
					+ " like '%" + nomeFonetizado[0].toUpperCase() + "%'";
		}

		String hql = "select p.rmr, p.nome, p.sexo, p.dataNascimento, p.medicoResponsavel, p.centroAvaliador, p.status, b from "
				+ " Paciente p left outer join p.buscas b join p.status s"
				+ " where p.medicoResponsavel.usuario.id = " + idUsuario
				+ " and b in (select max(b1) from Busca b1 where b1.paciente = p)";
		if (!"".equals(nomeFonetizado[0])) {
			hql += " and upper(p.nomeFonetizado)"
					+ " like '%" + nomeFonetizado[0].toUpperCase() + "%' ";					
		}
				
		hql += " order by p.nome";

		Long quantidadeRegistros = (Long) entityManager.createQuery(hqlCount).getSingleResult();

		LOGGER.info("inicio do select paginado");
		@SuppressWarnings("unchecked")
		List<Object[]> lista = entityManager.createQuery(hql)
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize())
				.getResultList();
		LOGGER.info("fim do select paginado");
		
		List<Paciente> pacientes = criarListaPaciente(lista);

		LOGGER.info("fim da busca de paciente no repositorio");

		return new PageImpl<Paciente>(pacientes, pageable, quantidadeRegistros);
	}
	
	@Override
	public Page<Paciente> findByNomeContainingAndCentroAvaliadorIdOrderByNome(String nome, Long idFuncaoTransplante, Long idCentroAvaliador,
			Pageable pageable) {
		
		LOGGER.info("inicio da busca de paciente no repositorio");

		String[] nomeFonetizado = {""};
		if (nome != null && !"".equals(nome)) {
			Stream.of(nome.split(" ")).forEach(palavra -> {
				nomeFonetizado[0] += ConversaoFonetica.getInstance().converter(palavra) + " ";
			});
			nomeFonetizado[0] = nomeFonetizado[0].trim();
		}

		String hqlCount = "select count(p.rmr) from Paciente p left outer join p.buscas b join p.status s "
				+ " where " 
				+ " b in (select max(b1) from Busca b1 where b1.paciente = p) ";
		
		if(idFuncaoTransplante == FuncaoTransplante.PAPEL_AVALIADOR){ 
			hqlCount += " and p.centroAvaliador.id = " + idCentroAvaliador ; 
		}
		if(idFuncaoTransplante == FuncaoTransplante.PAPEL_TRANSPLANTADOR){ 
			hqlCount += " and b.centroTransplante.id = "  + idCentroAvaliador ; 
		}
		if (!"".equals(nomeFonetizado[0])) {
			hqlCount += " and upper(p.nomeFonetizado)"
				+ " like '%" + nomeFonetizado[0].toUpperCase() + "%'";
		}				

		String hql = "select p.rmr, p.nome, p.sexo, p.dataNascimento, p.medicoResponsavel, p.centroAvaliador, p.status, b from "
				+ " Paciente p left outer join p.buscas b join p.status s "				
				+ " where " 
				+ " b in (select max(b1) from Busca b1 where b1.paciente = p) ";
		
		if(idFuncaoTransplante == FuncaoTransplante.PAPEL_AVALIADOR){ 
			hql += " and p.centroAvaliador.id = " + idCentroAvaliador ; 
		}
		if(idFuncaoTransplante == FuncaoTransplante.PAPEL_TRANSPLANTADOR){ 
			hql += " and b.centroTransplante.id = "  + idCentroAvaliador ; 
		}
		
		if (!"".equals(nomeFonetizado[0])) {
			hql += " and upper(p.nomeFonetizado)"
				+ " like '%" + nomeFonetizado[0].toUpperCase() + "%' ";
		}
				
		hql += " order by p.nome";

		Long quantidadeRegistros = (Long) entityManager.createQuery(hqlCount).getSingleResult();

		LOGGER.info("inicio do select paginado");
		@SuppressWarnings("unchecked")
		List<Object[]> lista = entityManager.createQuery(hql)
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize())
				.getResultList();
		LOGGER.info("fim do select paginado");
		
		List<Paciente> pacientes = criarListaPaciente(lista);

		LOGGER.info("fim da busca de paciente no repositorio");

		return new PageImpl<Paciente>(pacientes, pageable, quantidadeRegistros);
	}


	/**
	 * @param lista
	 * @return
	 */
	private List<Paciente> criarListaPaciente(List<Object[]> lista) {
		List<Paciente> pacientes;
		if (lista.isEmpty()) {
			pacientes = new ArrayList<Paciente>();
		}
		else {
			pacientes = lista.stream()
					.map(object -> {
						Paciente paciente = new Paciente(
								(Long)object[0],
								String.valueOf(object[1]),
								String.valueOf(object[2]),
								(LocalDate) object[3],
								(Medico) object[4]);
						paciente.setCentroAvaliador( (CentroTransplante) object[5] );
						paciente.setStatus((StatusPaciente) object[6]);
						paciente.setBuscas(new ArrayList<Busca>());
						
						return paciente;
					})
					//.distinct()
					.collect(Collectors.toList());
			lista.stream()
				.map(object -> {
					Object[] retorno = {object[0], object[7]};
					return retorno;
				})
				.forEach(object -> {
					pacientes
						.stream()
						.filter(paciente -> paciente.getRmr().equals(object[0]))
						.forEach(paciente -> {
							paciente.getBuscas().add((Busca)object[1]);
						});
				});			
		}
		return pacientes;
	}

	@Override
	public Paginacao<PacienteTarefaDTO> listarPacientesComAvaliacaoEmAberto(Long medicoId, Pageable paginacao) {
		StringBuffer hql = new StringBuffer();
		hql.append("select paci.rmr, paci.nome, aval.dataCriacao as dataCriacaoAvaliacao ")
				.append("from Avaliacao aval join aval.paciente paci ")
				.append("where paci.medicoResponsavel.id = :medicoId ")
				.append("and aval.status = 1 ");

		Query query = entityManager.createQuery(hql.toString()).setParameter("medicoId", medicoId);

		List<PacienteTarefaDTO> pacientesDTO = new ArrayList<PacienteTarefaDTO>();

		@SuppressWarnings("unchecked")
		List<Object[]> list = query
				.setFirstResult(paginacao.getPageNumber() * paginacao.getPageSize()).setMaxResults(paginacao.getPageSize())
				.getResultList();

		StringBuffer hqlCount = new StringBuffer("select count(aval) ")
				.append("from Avaliacao aval join aval.paciente paci ")
				.append("where paci.medicoResponsavel.id = :medicoId ")
				.append("and aval.status = 1 ");

		Long quantidadeRegistros = (Long) entityManager.createQuery(hqlCount.toString())
				.setParameter("medicoId", medicoId).getSingleResult();

		if (list != null) {
			for (Object[] itemList : list) {
				PacienteTarefaDTO dto = new PacienteTarefaDTO(
						Long.valueOf(String.valueOf(itemList[0])), String.valueOf(itemList[1]), (LocalDateTime) itemList[2]);
				pacientesDTO.add(dto);
			}
		}

		return new Paginacao<PacienteTarefaDTO>(pacientesDTO, paginacao, quantidadeRegistros);
	}

	@Override
	public Paginacao<PacienteTarefaDTO> listarPacientes(Long medicoId, Pageable paginacao) {
		StringBuffer hql = new StringBuffer();
		hql.append("select  paci.rmr, paci.nome, paci.dataCadastro ")
				.append("from Paciente paci join paci.medicoResponsavel medi ")
				.append("where medi.id = :medicoId ");

		Query query = entityManager.createQuery(hql.toString())
				.setParameter("medicoId", medicoId);

		List<PacienteTarefaDTO> pacientesDTO = new ArrayList<PacienteTarefaDTO>();

		@SuppressWarnings("unchecked")
		List<Object[]> list = query
				.setFirstResult(paginacao.getPageNumber() * paginacao.getPageSize())
				.setMaxResults(paginacao.getPageSize()).getResultList();

		if (list != null) {
			for (Object[] itemList : list) {
				PacienteTarefaDTO dto = new PacienteTarefaDTO(
						Long.valueOf(String.valueOf(itemList[0])), String.valueOf(itemList[1]), null);
				dto.setDataCadastro((LocalDateTime) itemList[2]);
				pacientesDTO.add(dto);
			}
		}

		StringBuffer hqlCount = new StringBuffer();
		hqlCount.append("select  count(paci) ")
				.append("from Paciente paci join paci.medicoResponsavel medi ")
				.append("where medi.id = :medicoId ");

		Long quantidadeRegistros = (Long) entityManager.createQuery(hqlCount.toString())
				.setParameter("medicoId", medicoId).getSingleResult();

		return new Paginacao<PacienteTarefaDTO>(pacientesDTO, paginacao, quantidadeRegistros);
	}

	@Override
	public List<PacienteTarefaDTO> listarPacientes(List<Long> rmrs) {
		StringBuffer hql = new StringBuffer();
		hql.append("select  paci.rmr, paci.nome, paci.dataCadastro ")
				.append("from Paciente paci ")
				.append("where paci.rmr in (:rmrs) ");

		Query query = entityManager.createQuery(hql.toString()).setParameter("rmrs", rmrs);

		List<PacienteTarefaDTO> pacientesDTO = new ArrayList<PacienteTarefaDTO>();

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();

		if (list != null) {
			for (Object[] itemList : list) {
				PacienteTarefaDTO dto = new PacienteTarefaDTO(
						Long.valueOf(String.valueOf(itemList[0])), String.valueOf(itemList[1]), null);
				dto.setDataCadastro((LocalDateTime) itemList[2]);
				pacientesDTO.add(dto);
			}
		}

		return pacientesDTO;
	}

	@Override
	public Paciente atualizarDadosPessoais(Paciente paciente) {

		if (paciente.getResponsavel() != null && paciente.getResponsavel().getId() == null) {
			Responsavel responsavel = new Responsavel();
			responsavel.setCpf(paciente.getResponsavel().getCpf());
			responsavel.setNome(paciente.getResponsavel().getNome());
			responsavel.setParentesco(paciente.getResponsavel().getParentesco());
			entityManager.persist(responsavel);
			paciente.setResponsavel(responsavel);
		}

		StringBuffer hql = new StringBuffer("UPDATE Paciente SET dataNascimento = :dataNascimento, ")
				.append("cpf = :cpf, ")
				.append("cns = :cns, ")
				.append("nome = :nome, ")
				.append("nomeMae = :nomeMae, ")
				.append("responsavel = :responsavel, ")
				.append("sexo = :sexo, ")
				.append("pais = :pais, ")
				.append("naturalidade = :naturalidade, ")
				.append("abo = :abo, ")
				.append("raca = :raca, ")
				.append("etnia = :etnia ")
				.append("where rmr = :rmr ");

		Query query = entityManager.createQuery(hql.toString())
				.setParameter("dataNascimento", paciente.getDataNascimento())
				.setParameter("cpf", paciente.getCpf())
				.setParameter("cns", paciente.getCns())
				.setParameter("nome", paciente.getNome())
				.setParameter("nomeMae", paciente.getNomeMae())
				.setParameter("responsavel", paciente.getResponsavel())
				.setParameter("sexo", paciente.getSexo())
				.setParameter("pais", paciente.getPais())
				.setParameter("naturalidade", paciente.getNaturalidade())
				.setParameter("abo", paciente.getAbo())
				.setParameter("raca", paciente.getRaca())
				.setParameter("etnia", paciente.getEtnia())
				.setParameter("rmr", paciente.getRmr());

		query.executeUpdate();
		// return entityManager.merge(paciente);

		return entityManager.find(Paciente.class, paciente.getRmr());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ContatoPacienteDTO obterContatoPaciente(Long rmr) {
		StringBuffer hql = new StringBuffer();
		hql.append("select paci.enderecosContato, paci.contatosTelefonicos ")
				.append("from Paciente paci ")
				.append("where paci.rmr = :rmr ");

		Query query = entityManager.createQuery(hql.toString()).setParameter("rmr", rmr);

		List<Object[]> result = (List<Object[]>) query.getResultList();
		ContatoPacienteDTO contatoDTO = null;

		if (CollectionUtils.isNotEmpty(result)) {
			String email = obterEmail(rmr);

			List<EnderecoContatoPaciente> enderecos = result
					.stream()
					.map(resultItem -> (EnderecoContatoPaciente) resultItem[0])
					.distinct()
					.collect(Collectors.toList());

			List<ContatoTelefonicoPaciente> contatos = result
					.stream()
					.map(resultItem -> (ContatoTelefonicoPaciente) resultItem[1])
					.distinct()
					.collect(Collectors.toList());

			contatoDTO = new ContatoPacienteDTO();
			contatoDTO.setRmr(rmr);
			contatoDTO.setEmail(email);
			contatoDTO.setEnderecosContato(enderecos);
			contatoDTO.setContatosTelefonicos(contatos);
		}

		return contatoDTO;
	}

	private String obterEmail(Long rmr) {
		StringBuffer hql = new StringBuffer();
		hql.append("select paci.email ")
				.append("from Paciente paci ")
				.append("where paci.rmr = :rmr ");

		Query query = entityManager.createQuery(hql.toString()).setParameter("rmr", rmr);
		return (String) query.getSingleResult();
	}

	@Override
	public void atualizarEmail(Paciente paciente) {
		StringBuffer hql = new StringBuffer("UPDATE Paciente p SET p.email = :email where p.rmr = :rmr ");

		Query query = entityManager.createQuery(hql.toString());
		query.setParameter("email", paciente.getEmail());
		query.setParameter("rmr", paciente.getRmr());

		query.executeUpdate();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Paginacao<LogEvolucaoDTO> listarLogEvolucao(Long rmr, List<Perfil> perfisExclusao, Pageable pageable) {

		Query query = entityManager.createQuery("select l from LogEvolucao l join l.paciente p where p.rmr = :rmr order by l.data asc");
		query.setParameter("rmr", rmr);

		List<LogEvolucao> logsEvolucao = (List<LogEvolucao>) query.getResultList();
		List<LogEvolucaoDTO> logs = converterLogParaLogDTO(logsEvolucao);

		if(CollectionUtils.isNotEmpty(logs)){
			
			Predicate<? super LogEvolucaoDTO> predicate = new Predicate<LogEvolucaoDTO>() {
				@Override
				public boolean test(LogEvolucaoDTO log) {
					final Boolean[] result = {true};
					if(log.getPefisExcluidos() != null){
						log.getPefisExcluidos().forEach(p ->{
							perfisExclusao.forEach(pe ->{
								if(pe.getId().equals(p)){
									result[0] = false;
								}
							});
						});						
					}
					return result[0];
				}
			};
			logs = logs.stream().filter(predicate).map(log -> {
				final String descricao = 
						AppUtil.getMensagem(messageSource, 
								log.getTipoLogEvolucao().getMensagem(), 
								(Object[]) log.getParametros());
				log.setDescricao(descricao);
				return log;
			}).collect(Collectors.toList());
		}

		StringBuffer hqlCount = new StringBuffer();
		hqlCount.append("select count(l) ")
				.append("from LogEvolucao l join l.paciente p ")
				.append("where p.rmr = :rmr order by l.data asc ");

		Long quantidadeRegistros = (Long) entityManager.createQuery(hqlCount.toString())
				.setParameter("rmr", rmr).getSingleResult();
		
		return new Paginacao<LogEvolucaoDTO>(logs, pageable, quantidadeRegistros);
	}
	
	private List<LogEvolucaoDTO> converterLogParaLogDTO(List<LogEvolucao> listaLogs){
		List<LogEvolucaoDTO> result = listaLogs.stream().map(l -> {
			LogEvolucaoDTO dto = new LogEvolucaoDTO();
			dto.setTipoLogEvolucao(l.getTipoEvento());
			dto.setParametros(l.getParametros());
			dto.setDataInclusao(l.getData());
			dto.setPefisExcluidos(l.getPerfisExcluidos().stream().map(p-> p.getId()).collect(Collectors.toList()));
			dto.setUsuarioResponsavel(l.getUsuario().getNome());
			dto.setRmr(l.getPaciente().getRmr());
            return dto;
        }).collect(Collectors.toList());
		return result;
	}

	@Override
	public Page<Paciente> listarPacientesPorRmrOuNome(Long rmr, String nome, Pageable pageable) {
		
		LOGGER.info("inicio da busca de paciente no repositorio");

		String hqlCount = "select count(p.rmr) from Paciente p left outer join p.buscas b join p.status s "
				+ " where b in (select max(b1) from Busca b1 where b1.paciente = p)";
		if(rmr != null) {
			hqlCount += " and p.rmr =" + rmr;
		}
		if (!"".equals(nome)) {
			hqlCount += " and upper(p.nomeFonetizado) like '%" + nome.toUpperCase() + "%'";
		}

		String hql = "select p "
				+ " from Paciente p left outer join p.buscas b join p.status s "
				+ " where b in (select max(b1) from Busca b1 where b1.paciente = p) ";
		if(rmr != null) {
			hql += " and p.rmr =" + rmr;
		}
		if (!"".equals(nome)) {
			hql += " and upper(p.nomeFonetizado) like '%" + nome.toUpperCase() + "%' ";					
		}
		hql += " order by p.nome";

		Long quantidadeRegistros = (Long) entityManager.createQuery(hqlCount).getSingleResult();

		LOGGER.info("inicio do select paginado");
		
		@SuppressWarnings("unchecked")
		List<Paciente> pacientes = entityManager.createQuery(hql)
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize())
				.getResultList();
		
		return new PageImpl<Paciente>(pacientes, pageable, quantidadeRegistros);
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public Paginacao<PedidosPacienteInvoiceDTO> listarPedidosPacienteInvoicePorRmrENomePaciente(Long rmr, String nomePaciente, Pageable pageable) {
		
		Long quantidadeRegistros = 0L;
		List<PedidosPacienteInvoiceDTO> pedidos = new ArrayList<>();

		StringBuilder sql = new StringBuilder(
		"SELECT DISTINCT COUNT(*) OVER(), PE.* FROM ( ")
		.append("SELECT PA.PACI_NR_RMR, PA.PACI_TX_NOME, RE.REGI_TX_NOME, DO.DOAD_ID, DO.DOAD_TX_ID_REGISTRO, ")
		.append("PE.PEEX_DT_CRIACAO AS DATA_CRIACAO, PE.PEEX_ID AS PEDIDO_ID, ")
		.append("( SELECT LISTAGG(LP.LOCU_ID, ',') WITHIN GROUP(ORDER BY LP.LOCU_ID) AS LOCU_ID ") 
		.append("FROM LOCUS_PEDIDO_EXAME LP WHERE LP.PEEX_ID = PE.PEEX_ID) AS LOCU_ID, ")				
		.append("PG.PAGA_ID, PG.STPA_ID, PG.TISE_ID, TS.TISE_TX_DESCRICAO ")
		.append("FROM ")
			 .append("PAGAMENTO PG ")
			 .append("INNER JOIN PEDIDO_EXAME PE ON (PG.PAGA_ID_OBEJTORELACIONADO = PE.PEEX_ID) ")
			 .append("INNER JOIN MATCH MT ON (PG.MATC_ID = MT.MATC_ID) ")
			 .append("INNER JOIN DOADOR DO ON (MT.DOAD_ID = DO.DOAD_ID) ")
			 .append("INNER JOIN BUSCA BU ON (MT.BUSC_ID = BU.BUSC_ID) ")
			 .append("INNER JOIN PACIENTE PA ON (PA.PACI_NR_RMR = BU.PACI_NR_RMR) ")
			 .append("INNER JOIN REGISTRO RE ON (RE.REGI_ID = DO.REGI_ID_ORIGEM) ")
			 .append("INNER JOIN TIPO_SERVICO TS ON (PG.TISE_ID = TS.TISE_ID) ")
		.append("WHERE ")
		    .append("PG.STPA_ID IN (1,4) AND PG.TISE_ID NOT IN (3,4,5,6,7) ");
			if(rmr != null){
				sql.append("AND PA.PACI_NR_RMR = :rmr ");
			}
			if(nomePaciente != null){
				sql.append("AND UPPER(PA.PACI_TX_NOME) LIKE UPPER(:nomePaciente) ");
			}
		sql.append("UNION ALL ")
		.append("SELECT PA.PACI_NR_RMR, PA.PACI_TX_NOME, RE.REGI_TX_NOME, DO.DOAD_ID, DO.DOAD_TX_ID_REGISTRO, ")
		.append("PI.PEID_DT_CRIACAO AS DATA_CRIACAO, PI.PEID_ID AS PEDIDO_ID, NULL AS LOCU_ID, ")				
		.append("PG.PAGA_ID, PG.STPA_ID, PG.TISE_ID, TS.TISE_TX_DESCRICAO ")
		.append("FROM ")
			 .append("PAGAMENTO PG ")
			 .append("INNER JOIN PEDIDO_IDM PI ON (PG.PAGA_ID_OBEJTORELACIONADO = PI.PEID_ID) ")
			 .append("INNER JOIN MATCH MT ON (PG.MATC_ID = MT.MATC_ID) ")
			 .append("INNER JOIN DOADOR DO ON (MT.DOAD_ID = DO.DOAD_ID) ")
			 .append("INNER JOIN BUSCA BU ON (MT.BUSC_ID = BU.BUSC_ID) ")
			 .append("INNER JOIN PACIENTE PA ON (PA.PACI_NR_RMR = BU.PACI_NR_RMR) ")
			 .append("INNER JOIN REGISTRO RE ON (RE.REGI_ID = DO.REGI_ID_ORIGEM) ")
			 .append("INNER JOIN TIPO_SERVICO TS ON (PG.TISE_ID = TS.TISE_ID) ")
		.append("WHERE ")
		    .append("PG.STPA_ID IN (1,4) AND PG.TISE_ID = 4 ");
			if(rmr != null){
				sql.append("AND PA.PACI_NR_RMR = :rmr ");
			}
			if(nomePaciente != null){
				sql.append("AND UPPER(PA.PACI_TX_NOME) LIKE UPPER(:nomePaciente) ");
			}
		sql.append("UNION ALL ")
		.append("SELECT PA.PACI_NR_RMR, PA.PACI_TX_NOME, RE.REGI_TX_NOME, DO.DOAD_ID, DO.DOAD_TX_ID_REGISTRO, ")
		.append("PC.PECL_DT_CRIACAO AS DATA_CRIACAO, PC.PECL_ID AS PEDIDO_ID, NULL AS LOCU_ID, ")				
		.append("PG.PAGA_ID, PG.STPA_ID, PG.TISE_ID, TS.TISE_TX_DESCRICAO ")
		.append("FROM ")
			 .append("PAGAMENTO PG ")
			 .append("INNER JOIN PEDIDO_COLETA PC ON (PG.PAGA_ID_OBEJTORELACIONADO = PC.PECL_ID) ")
			 .append("INNER JOIN MATCH MT ON (PG.MATC_ID = MT.MATC_ID) ")
			 .append("INNER JOIN DOADOR DO ON (MT.DOAD_ID = DO.DOAD_ID) ")
			 .append("INNER JOIN BUSCA BU ON (MT.BUSC_ID = BU.BUSC_ID) ")
			 .append("INNER JOIN PACIENTE PA ON (PA.PACI_NR_RMR = BU.PACI_NR_RMR) ")
			 .append("INNER JOIN REGISTRO RE ON (RE.REGI_ID = DO.REGI_ID_ORIGEM) ")
			 .append("INNER JOIN TIPO_SERVICO TS ON (PG.TISE_ID = TS.TISE_ID) ")
		.append("WHERE ")
		    .append("PG.STPA_ID IN (1,4) AND PG.TISE_ID IN (3,5,6,7) ");
			if(rmr != null){
				sql.append("AND PA.PACI_NR_RMR = :rmr ");
			}
			if(nomePaciente != null){
				sql.append("AND UPPER(PA.PACI_TX_NOME) LIKE UPPER(:nomePaciente) ");
			}
		sql.append(") PE ORDER BY PEDIDO_ID DESC, DATA_CRIACAO DESC ");	
		
		Query query = (Query)entityManager.createNativeQuery(sql.toString());
		
		if (pageable != null) {
			query.setFirstResult(new Long(pageable.getOffset()).intValue());
			query.setMaxResults(pageable.getPageSize());
		}

		if(rmr != null) {
			query.setParameter("rmr", rmr);
		}
		
		if(nomePaciente != null) {
			query.setParameter("nomePaciente", "%" + nomePaciente + "%");
		}

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
	
		if (list != null && list.size() > 0) {
			for (Object[] data : list) {
				PedidosPacienteInvoiceDTO voData = popularPedidosPacienteInvoiceDTO(data);
				pedidos.add(voData);
			}
			quantidadeRegistros = pedidos.get(0).getQtdRegistros(); 
		}
	
		return new Paginacao<PedidosPacienteInvoiceDTO>(pedidos, pageable == null? new PageRequest(0, 1):pageable, quantidadeRegistros);
	}

	private PedidosPacienteInvoiceDTO popularPedidosPacienteInvoiceDTO(Object[] data) {
		
		PedidosPacienteInvoiceDTO voConsulta = new PedidosPacienteInvoiceDTO();  
		
		if (data[0] != null) {
			voConsulta.setQtdRegistros(Long.parseLong(data[0].toString()));
		}
		
		if (data[1] != null) {
			voConsulta.setRmr(Long.parseLong(data[1].toString()));
		}
		if (data[2] != null) {
			voConsulta.setNomePaciente(data[2].toString());
		}
		if (data[3] != null) {
			voConsulta.setOrigem(data[3].toString());
		}
		if (data[4] != null) {
			voConsulta.setIdDoador(Long.parseLong(data[4].toString()));
		}
		if (data[5] != null) {
			voConsulta.setIdRegistroDoador(data[5].toString());
		}
		if (data[6] != null) {
			voConsulta.setDataCriacao(((Timestamp) data[6]).toLocalDateTime());
		}
		if (data[7] != null) {
			voConsulta.setIdPedido(Long.parseLong(data[7].toString()));
		}
		if (data[8] != null) {
			voConsulta.setLocus(data[8].toString());
		}
		if (data[9] != null) {
			voConsulta.setIdPagamento(Long.parseLong(data[9].toString()));
		}
		if (data[10] != null) {
			voConsulta.setIdStatusPagamento(Long.parseLong(data[10].toString()));
		}
		if (data[11] != null) {
			voConsulta.setIdTipoServico(Long.parseLong(data[11].toString()));
		}
		if (data[12] != null) {
			voConsulta.setDescricaoServico(data[12].toString());
		}
		
		return voConsulta;
	}


    /**
	 * @param messageSource the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}

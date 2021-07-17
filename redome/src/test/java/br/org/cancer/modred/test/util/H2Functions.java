package br.org.cancer.modred.test.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2Functions {
	
	public static float getScore(Connection connection,  int paciente_id) throws SQLException {
	   //int pontos_paciente_q_const = 0;    
	   return 0;
	}
	
	public static int procCriarMatchFake(Connection connection, final int rmr, final int idDoador) throws SQLException {
		connection.setAutoCommit(true);
		Long idMatch = obterIdMatch(connection, rmr, idDoador);
		if (idMatch == null) {			
			Long idBusca = obterIdBusca(connection, rmr);
			if (idBusca == null) {
				return 0;
			}
			idMatch = criaMatch(connection, idBusca, idDoador);
		}
		else {
			removeQualifiacaoMatch(connection, idDoador);
		}
		
		criaQualificacaoMatch(connection, idMatch, idDoador);
		
		return 0;
		
	}
	
	public static int proc_match_doador(Connection connection, final int pDOAD_ID) throws SQLException {
		
		connection.setAutoCommit(true);
		
		StringBuilder sb = new StringBuilder("select paci_nr_rmr from paciente");
		PreparedStatement ps = connection.prepareStatement(sb.toString());
		ResultSet results = ps.executeQuery();
		
		while (results.next()) {			
			procCriarMatchFake(connection, results.getInt("paci_nr_rmr"), pDOAD_ID);
		}
		
		return 0;
	} 
	
		
	private static void criaQualificacaoMatch(Connection connection, Long idMatch, int idDoador) throws SQLException {
		StringBuilder sb = new StringBuilder("select vg.* from VALOR_GENOTIPO_DOADOR vg WHERE vg.GEDO_ID IN (SELECT GEDO_ID FROM GENOTIPO_DOADOR WHERE DOAD_ID = ?)");
		PreparedStatement ps = connection.prepareStatement(sb.toString());
		ps.setLong(1, idDoador);
		ResultSet rs = ps.executeQuery();
		
		sb = new StringBuilder("insert into qualificacao_match (QUMA_ID, LOCU_ID, QUMA_TX_QUALIFICACAO, QUMA_NR_POSICAO, MATC_ID, DOAD_ID, QUMA_TX_GENOTIPO, QUMA_NR_TIPO) ") 
				.append("values (SQ_QUMA_ID.nextval, ?, 'P', ?, ?, ?, ?, ?)");
		
		while (rs.next()) {			
			ps = connection.prepareStatement(sb.toString());
			ps.setString(1, rs.getString("locu_id") );
			ps.setLong(2, rs.getLong("VAGD_NR_POSICAO_ALELO") );
			ps.setLong(3, idMatch );
			ps.setLong(4, idDoador );
			ps.setString(5, rs.getString("VAGD_TX_ALELO") );
			ps.setLong(6, rs.getLong("VAGD_NR_TIPO"));
			ps.execute();			
		}
		
	}

	private static void removeQualifiacaoMatch(Connection connection, int idDoador) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("delete from qualificacao_match where doad_id = ?");
		ps.setLong(1, idDoador);
		ps.execute();
		
	}

	private static Long criaMatch(Connection connection, Long idBusca, int idDoador) throws SQLException {
		StringBuilder sb = new StringBuilder("select SQ_MATC_ID.nextval as matc_id from dual");
		PreparedStatement ps = connection.prepareStatement(sb.toString());
		ResultSet results = ps.executeQuery();
		results.next();
		Long idMatch = results.getLong("matc_id");
		
		sb = new StringBuilder("INSERT INTO MATCH(MATC_ID, MATC_IN_STATUS, BUSC_ID, DOAD_ID) " ) 
		        .append("values (?, 1, ?, ?)");		
		PreparedStatement ps1 = connection.prepareStatement(sb.toString());
		ps1.setLong(1, idMatch);
		ps1.setLong(2, idBusca);
		ps1.setLong(3, idDoador);
		ps1.execute();
		         
		return idMatch;
	}

	private static Long obterIdMatch(Connection connection, final int rmr, final int idDoador) throws SQLException {
		StringBuilder sb = new StringBuilder("select distinct ma.matc_id  from match ma ")
				.append("            inner join busca b on ma.BUSC_ID = ma.BUSC_ID ") 
				.append("            inner join status_busca sb on sb.stbu_id = b.stbu_id ") 
				.append("            where ") 
				.append("            ma.doad_id = ? and b.paci_nr_rmr = ? " )  
				.append("            and sb.stbu_in_busca_ativa = 1 ") 
				.append("            and ma.matc_in_status = 1");		
			
			PreparedStatement ps = connection.prepareStatement(sb.toString());
			ps.setLong(1, idDoador);
			ps.setLong(2, rmr);
			ResultSet results = ps.executeQuery();
			//results.next();
			if (results.getRow() != 0) {
				return results.getLong("matc_id");
			}
			return null;
	}
	
	private static Long obterIdBusca(Connection connection, final int rmr) throws SQLException {
		StringBuilder sb = new StringBuilder("select b.busc_id from busca b ") 
				.append("            inner join status_busca sb on sb.stbu_id = b.stbu_id ") 
				.append("            where b.paci_nr_rmr = ? " ) 
				.append("            and sb.stbu_in_busca_ativa = 1");
		PreparedStatement ps = connection.prepareStatement(sb.toString());
		ps.setLong(1, rmr);
		ResultSet results = ps.executeQuery();
		if (results.next()) {		
			return results.getLong("busc_id");
		}
		
		return null;
		
	}

}


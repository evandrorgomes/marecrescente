package br.org.cancer.redome.feign.client;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.cancer.redome.feign.client.dto.SolicitacaoDTO;
import br.org.cancer.redome.feign.client.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.feign.client.util.CustomPageImpl;

public interface ISolicitacaoFeign {
			
	@PostMapping("api/solicitacoes/workup/doadornacionalpacientenacional")	
	public SolicitacaoDTO criarSolicitacaoWorkupDoadorNacionalPacienteNacioanl(@RequestBody(required=true) Long idMatch);
	
	@PostMapping("api/solicitacoes/workup/doadorinternacionalpacientenacional")
	public SolicitacaoDTO criarSolicitacaoWorkupDoadorInternacionalPacienteNacioanl(@RequestBody(required=true) Long idMatch);
	
	@PostMapping("api/solicitacoes/workup/cordaonacionalpacientenacional")
	public SolicitacaoDTO criarSolicitacaoWorkupCordaoNacionalPacienteNacioanl(@RequestBody(required=true) Long idMatch);
	
	@PostMapping("api/solicitacoes/workup/cordaointernacionalpacientenacional")
	public SolicitacaoDTO criarSolicitacaoWorkupCordaoInternacionalPacienteNacioanl(@RequestBody(required=true) Long idMatch);

	@GetMapping(value = "api/solicitacoes/{id}")
	public SolicitacaoDTO obterSolicitacao(@PathVariable(required = true) Long id);
	
	@GetMapping(value = "api/solicitacoes/{id}")
	public SolicitacaoWorkupDTO obterSolicitacaoWorkup(@PathVariable(required = true) Long id);

	@GetMapping(value = "api/solicitacoes/workup/solicitacoescentrotransplantestatussolicitacao")
	public CustomPageImpl<SolicitacaoDTO> listarSolicitacaoPorCentroTransplanteEStatus(
			@RequestParam(required = true, name= "idCentroTransplante") Long idCentroTransplante,
			@RequestParam(required = true, name= "statusSolicitacao") String[] statusSolicitacao,
			@RequestParam(required = true, name= "pagina") int pagina,
			@RequestParam(required = true, name= "quantidadeRegistros") int quantidadeRegistros);
	
	@PostMapping(value = "api/solicitacoes/{id}/cancelar/workup")
	public SolicitacaoDTO cancelarSolicitacaoWorkup(@PathVariable(name = "id") Long id); 

	@GetMapping(value = "api/solicitacoes")
	public List<SolicitacaoWorkupDTO> listarSolicitacoes(@RequestParam(required = true) String[] tiposSolicitacao,
			@RequestParam(required = true) String[] statusSolicitacao);
	
	@PutMapping(value = "api/solicitacoes/{id}/atribuirusuarioresponsavel")
	public SolicitacaoWorkupDTO atribuirUsuarioResponsavel(@PathVariable(required = true) Long id,
			@RequestBody(required = true) Long idUsuario);
	
	@PutMapping(value = "api/solicitacoes/{id}/atualizarfaseworkup")
	public SolicitacaoWorkupDTO atualizarFaseWorkup(@PathVariable(required = true) Long id,
			@RequestBody(required = true) Long idFaseWorkup);

	@GetMapping(value = "api/solicitacoes/solicitacaofasesworkup")
	public List<SolicitacaoWorkupDTO> listarSolicitacoesPortiposSolicitacaoPorstatusEFasesWorkup(
			@RequestParam(required = true) String[] tiposSolicitacao,
			@RequestParam(required = true) String[] statusSolicitacao,
			@RequestParam(required = true) String[] fasesWorkup);
	
	@PutMapping(value = "api/solicitacoes/{id}/atribuircentrocoleta")
	public SolicitacaoWorkupDTO atribuirCentroDeColeta(@PathVariable(required = true) Long id,
			@RequestBody(required = true) Long idCentroColeta);
	
}

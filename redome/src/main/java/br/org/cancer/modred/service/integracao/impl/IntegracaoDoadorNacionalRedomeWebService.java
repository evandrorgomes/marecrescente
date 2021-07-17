package br.org.cancer.modred.service.integracao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.doador.DoadorNacionalDTO;
import br.org.cancer.modred.controller.dto.doador.MensagemErroIntegracao;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.ContatoTelefonicoDoador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.EmailContatoDoador;
import br.org.cancer.modred.model.EnderecoContatoDoador;
import br.org.cancer.modred.model.EstadoCivil;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.LocusExamePk;
import br.org.cancer.modred.model.Metodologia;
import br.org.cancer.modred.model.Municipio;
import br.org.cancer.modred.model.Pais;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.domain.Metodologias;
import br.org.cancer.modred.model.domain.TipoBoolean;
import br.org.cancer.modred.persistence.IHemoEntidadeRepository;
import br.org.cancer.modred.persistence.ILaboratorioRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IEtniaService;
import br.org.cancer.modred.service.IMunicipioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.service.integracao.IIntegracaoDoadorNacionalRedomeWebService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade DoadorNacional.
 * 
 * @author ergomes
 * 
 */
@Service
@Transactional
public class IntegracaoDoadorNacionalRedomeWebService extends AbstractService<DoadorNacional, Long> implements IIntegracaoDoadorNacionalRedomeWebService {

	private static final Long ID_PAIS_BRASIL = 1L;
	private static final Long ID_REGISTRO_REDOME = 1L;
	private static final String TIPO_ABO_NAO_INFORMADO = "NI"; 
	
	@Autowired
	private IDoadorNacionalService doadorNacionalService;

	@Autowired
	private ILaboratorioRepository laboratorioRepository;
	
	@Autowired
	private IHemoEntidadeRepository hemoEntidadeRepository; 

	@Autowired
	private IEtniaService etniaService;
	
	@Autowired
	private IMunicipioService municipioService;
	
	@Override
	@Transactional
	public List<MensagemErroIntegracao> atualizarDadosIntegracaoDoadorNacional(List<DoadorNacionalDTO> doadores) {
		
		List<MensagemErroIntegracao> listDmrRetorno = new ArrayList<>();
		
		doadores.stream().forEach(dto ->{
			
			DoadorNacional doadorNacional = new DoadorNacional();
			DoadorNacional doadorNacionalRef = new DoadorNacional();

			doadorNacional.setDataAtualizacao(LocalDateTime.now());
			doadorNacionalRef.setDmr(dto.getDmr());
			
			if(dto.getAbo() != null){
				doadorNacional.setAbo(dto.getAbo());
			}
			else {
				doadorNacional.setAbo(TIPO_ABO_NAO_INFORMADO);
			}
			
			doadorNacional.setHemoEntidade(hemoEntidadeRepository.findByIdHemocentroRedomeWeb(dto.getHemoEntidade().getIdHemocentroRedomeWeb()));
			doadorNacional.setLaboratorio(laboratorioRepository.findByIdRedomeWeb(dto.getLaboratorio().getIdRedomeWeb()));

			doadorNacional.setAltura(AppUtil.floatToBigDecimal(dto.getAltura(),2));
			doadorNacional.setPeso(dto.getPeso());

			doadorNacional.setNumeroHemocentro(dto.getNumeroHemocentro());
			doadorNacional.setFumante(TipoBoolean.valueOf(dto.getFumante()).isTipo());
			doadorNacional.setEstadoCivil(new EstadoCivil(dto.getEstadoCivil()));
			doadorNacional.setCampanha(dto.getCampanha());
			doadorNacional.setDataColeta(dto.getDataColeta());
			
			doadorNacional.setCpf(dto.getCpf());
			
			doadorNacional.setDataCadastro(dto.getDataCadastro().atStartOfDay());
			doadorNacional.setDataNascimento(dto.getDataNascimento());
			doadorNacional.setDmr(dto.getDmr());

			if(dto.getEtnia() != null) {
				doadorNacional.setEtnia(etniaService.getEtnia(dto.getEtnia().getId()));
			}

			doadorNacional.setNaturalidade(new Municipio(dto.getNaturalidade()));
			
			doadorNacional.setNome(dto.getNome());
			doadorNacional.setNomeMae(dto.getNomeMae());
			doadorNacional.setNomePai(dto.getNomePai());
			doadorNacional.setNomeSocial(dto.getNomeSocial());
			doadorNacional.setRg(dto.getRg());

			Pais brasil = new Pais();
			brasil.setId(ID_PAIS_BRASIL);
			brasil.setNome(dto.getPais().getNome());
			doadorNacional.setPais(brasil);
			doadorNacional.setSexo(dto.getSexo());
			
			StatusDoador statusDoador = new StatusDoador();
			statusDoador.setId(dto.getStatusDoador().getId());
			doadorNacional.setStatusDoador(statusDoador);

			popularTelefones(dto, doadorNacional, doadorNacionalRef);
			popularEmails(dto, doadorNacional, doadorNacionalRef);
			popularEnderecos(dto, doadorNacional, doadorNacionalRef);
			popularExames(dto, doadorNacional, doadorNacionalRef);
			
			Registro registroOrigem = new Registro();
			registroOrigem.setId(ID_REGISTRO_REDOME);
			doadorNacional.setRegistroOrigem(registroOrigem );
			
			try {
			
				doadorNacionalService.criarDoadorNacional(doadorNacional);
				
			}
			catch (Exception erro) {

				MensagemErroIntegracao mensagem = new MensagemErroIntegracao();
				
				if(erro instanceof ValidationException) {
					ValidationException validation = (ValidationException) erro;
					String errosValidations = validation.getErros().stream().map(e -> "Campo " + e.getCampo() + " - " + e.getMensagem()).collect(Collectors.joining(","));
					mensagem.setDescricaoErro(errosValidations);
				}
				else if(erro instanceof NullPointerException) {
					mensagem.setDescricaoErro("NullPointerException - Ocorreu um erro ao salvar objeto no banco de dados.");
				}
				else {
					mensagem.setDescricaoErro(erro.getCause().getCause().getMessage());
				}
				mensagem.setDmr(dto.getDmr());

				listDmrRetorno.add(mensagem);
			}
		});
		
		return listDmrRetorno;
	}

	private void popularTelefones(DoadorNacionalDTO dto, DoadorNacional doadorNacional, DoadorNacional doadorNacionalRef) {
		if(dto.getContatosTelefonicos() != null && !dto.getContatosTelefonicos().isEmpty()){
			List<ContatoTelefonicoDoador> contatosTelefonicos = new ArrayList<ContatoTelefonicoDoador>();
			dto.getContatosTelefonicos().stream().forEach(t ->{
				ContatoTelefonicoDoador cont = new ContatoTelefonicoDoador();
				cont.setPrincipal(t.getPrincipal());
				cont.setTipo(t.getTipo());
				cont.setCodArea(t.getCodArea());
				cont.setNumero(t.getNumero());
				cont.setComplemento(t.getComplemento());
				cont.setDoador(doadorNacional);
				cont.setNome(t.getNome());
				contatosTelefonicos.add(cont);
			});
			doadorNacional.setContatosTelefonicos(contatosTelefonicos);
		}
	}

	private void popularEmails(DoadorNacionalDTO dto, DoadorNacional doadorNacional, DoadorNacional doadorNacionalRef) {
		if(dto.getEmailsContato() != null && !dto.getEmailsContato().isEmpty()){
			List<EmailContatoDoador> emails = new ArrayList<EmailContatoDoador>();
			dto.getEmailsContato().stream().forEach(e->{
				EmailContatoDoador email = new EmailContatoDoador();
				email.setEmail(e.getEmail());
				email.setPrincipal(e.getPrincipal());
				email.setDoador(doadorNacional);
				emails.add(email);
			});
			doadorNacional.setEmailsContato(emails);
		}
	}

	private void popularEnderecos(DoadorNacionalDTO dto, DoadorNacional doadorNacional, DoadorNacional doadorNacionalRef) {
		if(dto.getEnderecosContato() != null && !dto.getEnderecosContato().isEmpty()){
			List<EnderecoContatoDoador> enderecos = new ArrayList<EnderecoContatoDoador>();
			dto.getEnderecosContato().stream().forEach(e->{
				EnderecoContatoDoador endereco = new EnderecoContatoDoador();

				Pais pais = new Pais();
				pais.setId(e.getPais().getId());
				pais.setNome(e.getPais().getNome());
				endereco.setPais(pais );
				
				endereco.isPermitidoEnderecoEstrangeiroNulo();
				
				endereco.setBairro(e.getBairro());
				endereco.setCep(e.getCep());
				endereco.setComplemento(e.getComplemento());
				endereco.setExcluido(false);
				endereco.setMunicipio(municipioService.obterMunicipioPorDescricao(e.getMunicipio()));
				endereco.setNomeLogradouro(e.getNomeLogradouro());
				endereco.setNumero(e.getNumero());
				endereco.setTipoLogradouro(e.getTipoLogradouro());
				
				endereco.setDoador(doadorNacional);
				enderecos.add(endereco);
			});
			doadorNacional.setEnderecosContato(enderecos);
		}
	}

	private void popularExames(DoadorNacionalDTO dto, DoadorNacional doadorNacional, DoadorNacional doadorNacionalRef) {
		if(dto.getExames() != null && !dto.getExames().isEmpty()){
			List<ExameDoadorNacional> exames = new ArrayList<ExameDoadorNacional>();
			
			dto.getExames().stream().forEach(e -> {
				
				ExameDoadorNacional exame = new ExameDoadorNacional();
				exame.setLaboratorio(laboratorioRepository.findByIdRedomeWeb(e.getLaboratorio().getIdRedomeWeb()));
				exame.setDataExame(e.getDataExame());
				exame.setStatusExame(e.getStatusExame());
				exame.setDoador(doadorNacional);
				
				List<Metodologia> listMetodologias = new ArrayList<Metodologia>();
				e.getMetodologias().stream().forEach(m->{
					Metodologias metodologiaEnum = Metodologias.getMetodoLogiaPorIdLegado(m.getId());
					Metodologia metodologia = new Metodologia();
					metodologia.setId(metodologiaEnum.getIdModred());
					listMetodologias.add(metodologia);
				});
				exame.setMetodologias(listMetodologias);

				List<LocusExame> locusExameList = new ArrayList<LocusExame>();
				e.getLocusExames().stream().forEach(l ->{
					
					Locus locus = new Locus();
					locus.setCodigo(l.getId().getLocus().getCodigo());
					
					LocusExame locusExame = new LocusExame();
					locusExame.setPrimeiroAlelo(l.getPrimeiroAlelo());
					locusExame.setSegundoAlelo(l.getSegundoAlelo());
					
					LocusExamePk id = new LocusExamePk();
					id.setExame(exame);
					id.setLocus(locus);
					
					locusExame.setId(id);
					
					locusExameList.add(locusExame);
				});

				exame.setLocusExames(locusExameList);
				exames.add(exame);
			});	

			doadorNacional.setExames(exames);
		}
	}

	@Override
	public IRepository<DoadorNacional, Long> getRepository() {
		return null;
	}
}

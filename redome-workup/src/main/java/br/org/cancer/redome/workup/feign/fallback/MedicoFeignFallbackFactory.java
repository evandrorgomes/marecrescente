package br.org.cancer.redome.workup.feign.fallback;

import br.org.cancer.redome.workup.dto.MedicoDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.IMedicoFeign;

//@Component
public class MedicoFeignFallbackFactory extends DefaultFallbackFactory<IMedicoFeign> {
	
	@Override
	public IMedicoFeign create(Throwable cause) {		
		super.create(cause);
		
		return new IMedicoFeign() {
			
			@Override
			public MedicoDTO obterMedicoAssociadoUsuarioLogado() {
				throw new BusinessException("");				
			}
			
			@Override
			public MedicoDTO obterMedico(Long idMedico) {
				throw new BusinessException("");
			}
		};
		
	}

}

package br.org.cancer.modred.persistence.impl;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.persistence.IGenericRepository;

/**
 * Classe para busca de repositórios utilizados na aplicação.
 * 
 * @author Cintia Oliveira
 *
 */
@Service
@SuppressWarnings("rawtypes")
public class GenericRepository implements IGenericRepository {

    Repositories repositories = null;

    private ListableBeanFactory listableBeanFactory;
    
    //@Autowired
    //@Lazy
    public GenericRepository() {        
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * br.org.cancer.modred.persistence.IGenericRepository#getRepository(java.
     * lang.Class)
     */
    @Override
    public JpaRepository getRepository(Class classe) {
    	if (repositories == null) {
    		repositories = new Repositories(listableBeanFactory);
    	}
        return (JpaRepository) repositories.getRepositoryFor(classe).get();
    }

	/**
	 * @param listableBeanFactory the listableBeanFactory to set
	 */
    @Autowired
	public void setListableBeanFactory(ListableBeanFactory listableBeanFactory) {
		this.listableBeanFactory = listableBeanFactory;
		//repositories = new Repositories(listableBeanFactory);
	}

}

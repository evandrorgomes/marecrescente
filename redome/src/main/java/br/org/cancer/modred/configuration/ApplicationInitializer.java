package br.org.cancer.modred.configuration;

/**
 * Classe de inicialização da aplicação. Substitui o arquivo web.xml.
 * 
 * @author Cintia Oliveira
 *
 */
public class ApplicationInitializer {
	
}

/*public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.support.
     * AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses
     * ()
     
    @Override
    protected Class<?>[] getRootConfigClasses() {
    	
        return new Class[] { ApplicationConfiguration.class, JPAConfiguration.class,
                SecurityWebApplicationInitializer.class };
    }

    
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.support.
     * AbstractAnnotationConfigDispatcherServletInitializer#
     * getServletConfigClasses()
     
    @Override
    protected Class<?>[] getServletConfigClasses() {
    	return null;
    }

    
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.support.
     * AbstractDispatcherServletInitializer#getServletMappings()
     
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.support.
     * AbstractDispatcherServletInitializer#getServletFilters()
     
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new OpenEntityManagerInViewFilter(), new CustomCorsFilter() };
    }

    *//**
     * Classe que extende a configuração de segurança do spring security.
     * 
     * @author Cintia Oliveira
     *
     *//*
    public static class SecurityWebApplicationInitializer
            extends AbstractSecurityWebApplicationInitializer {
    	
    	  @Override
    	    protected String getDispatcherWebApplicationContextSuffix() {
    	        return AbstractDispatcherServletInitializer.DEFAULT_SERVLET_NAME;
    	    }
    }

}*/
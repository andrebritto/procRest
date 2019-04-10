package procuracoes.common;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class CorsFilter implements ContainerResponseFilter {

	Logger logger = LoggerFactory.getLogger(CorsFilter.class);
	
	@Override
	public void filter(ContainerRequestContext request,
	        ContainerResponseContext response) throws IOException {
		logger.info("Metodo: {}", request.getMethod());
		response.getHeaders().add("Access-Control-Allow-Headers","access-control-allow-headers, Access-Control-Allow-Origin, Access-Control-Allow-Methods, PROCREST_KEY, PROCREST_AMBIENTE, origin, content-type, accept, authorization");
	    response.getHeaders().add("Access-Control-Allow-Origin", "*");	    
	    response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	    
	    logger.info("Request Headers: {}",request.getHeaders());
	    logger.info("Response Headers: {}",response.getHeaders());
	    
	}
}
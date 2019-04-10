package procuracoes.filter;


import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Provider
public class JWTTokenNeededFilter implements ContainerRequestFilter {
	private static final String CHAVE_DE_ACESSO = "%GERENCIAL";

	Logger logger = LoggerFactory.getLogger(JWTTokenNeededFilter.class);

    @Inject
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Acesso não Autorizado", 401, new Headers<Object>());;
    @Inject
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {	
		MultivaluedMap<String, String> headers = requestContext.getHeaders();		
		
		logger.info("Acesso: {}",headers.getFirst("PROCREST_KEY"));
		if(!CHAVE_DE_ACESSO.equals(headers.getFirst("PROCREST_KEY"))){
			requestContext.abortWith(ACCESS_DENIED);
			return;
		}
		
//		logger.info("Authorization Header: {}",requestContext.getHeaderString(HttpHeaders.AUTHORIZATION));
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		
		if (StringUtils.isEmpty(authorizationHeader)|| !authorizationHeader.startsWith("Basic ")){
			logger.error("Invalido : " + authorizationHeader);
			requestContext.abortWith(ACCESS_DENIED);
			return;
		}
		 
		if (!isAutenticado(requestContext, authorizationHeader)){			
			requestContext.abortWith(ACCESS_DENIED);
			return;
		}

		logger.info("Autenticação completa ...");

	}
	private boolean isAutenticado(ContainerRequestContext requestContext,
			String authorizationHeader) throws IOException {
		String token = authorizationHeader.substring("Basic".length()).trim();
		boolean autenticado = false;			
		
		
		byte[] valueDecoded = Base64.decode(token.getBytes());
		
//		logger.info("Token decoded {}", new String(valueDecoded));
		String credencial = new String(valueDecoded);		
		String cred[] = StringUtils.split(credencial,":");		
		try {
			logger.info("Usuário autenticado: {}", cred[0].trim());
			logger.info("Usuário autenticado: {}", cred[1].trim());
			//autenticado = AutenticacaoSqlAda.getInstance().autenticar(cred[0].trim(), cred[1].trim());
			autenticado = true;
			logger.info("Usuário autenticado: {}", autenticado);
		} catch (Exception e) {
			e.printStackTrace();			
		}		
		return autenticado;
	}
	
	public static void main(String[] args) {
						
		String basic = "Basic NjgyODE0NTU1MDA6c3g3ODk0NTY=";
		String token = basic.substring("Basic".length()).trim();
		byte[] valueDecoded;
		try {
			valueDecoded = Base64.decode(token.getBytes());
			String credencial = new String(valueDecoded);		
			String cred[] = StringUtils.split(credencial,":");
			System.out.println("usuario " + cred[0]);
			System.out.println("senha " + cred[1]);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
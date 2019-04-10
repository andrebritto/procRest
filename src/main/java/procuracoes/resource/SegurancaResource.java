/**
 * 
 */
package procuracoes.resource;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author allen
 *         http://localhost:8080/procRest/procuracoes/68281455500/19007809334
 *         http://localhost:8080/ProcRest/resource/sistemas
 * 
 */
@Path("/seguranca")
public class SegurancaResource {
	Logger logger = LoggerFactory.getLogger(SegurancaResource.class);
	@Context
	private UriInfo uriInfo;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response autenticar(){		
			return Response.ok().build();
		
	}

	private String issueToken(String login) {
		Key key = generateKey();
		String jwtToken = Jwts.builder().setSubject(login)
				.setIssuer(uriInfo.getAbsolutePath().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(SignatureAlgorithm.HS512, key).compact();
		System.out.println("#### generating token for a key : " + jwtToken + " - " + key);
		return jwtToken;

	}

	private Key generateKey() {
		String keyString = "simplekey";
		Key key = new SecretKeySpec(keyString.getBytes(), 0,
				keyString.getBytes().length, "DES");
		return key;
	}	
}

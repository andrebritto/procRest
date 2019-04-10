/**
 * 
 */
package procuracoes.resource;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import procuracoes.business.ProcuracaoBC;
import procuracoes.dao.AutenticacaoSqlAda;
import procuracoes.model.Procuracao;
import procuracoes.model.RequisicaoProcuracao;
import procuracoes.model.Usuario;


/**
 * @author allen
 *         http://localhost:8080/procRest/procuracoes/68281455500/19007809334
 *         http://localhost:8080/ProcRest/resource/sistemas
 * 
 */
@Path("/procuracao")
public class ProcuracoesResource {
	Logger logger = LoggerFactory.getLogger(ProcuracoesResource.class);
	@Context
	private UriInfo uriInfo;
	@GET
	@Path("/{titular}/{procurador}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProcuracoes(@PathParam("titular") String ni_titular,
			@PathParam("procurador") String ni_procurador,
			@HeaderParam("PROCREST_AMBIENTE") int cdAmbiente) {
		logger.info("Acessando recurso procuração por titular/procurador");
		Response ret = null;
		ProcuracaoBC procuracaoBC = ProcuracaoBC.getInstance();
		try {
			procuracaoBC.setAmbiente(cdAmbiente);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String(e.getMessage())).build();
		}

		try {
			ret = Response
					.ok()
					.entity(procuracaoBC.getProcuracaoTitularProcurador(
							ni_titular, ni_procurador)).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.status(Status.NOT_FOUND)
					.entity(new String(e.getMessage())).build();
		}

		return ret;
	}

	@GET
	@Path("/niTitular={titular}")
	@Produces(MediaType.APPLICATION_JSON)
	// @JWTTokenNeeded
	public Response getProcuracoesPorTitular(
			@PathParam("titular") String ni_titular,
			@HeaderParam("PROCREST_AMBIENTE") int cdAmbiente) {
		Response ret = null;
		logger.info("getProcuracoesPorTitular : {}", ni_titular);
		try {
			ProcuracaoBC procuracaoBC = ProcuracaoBC.getInstance();
			procuracaoBC.setAmbiente(cdAmbiente);
			ArrayList<Procuracao> retorno = procuracaoBC
					.getProcuracoesPorTitular(ni_titular);
			ret = Response.ok().entity(retorno).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.status(Status.NOT_FOUND)
					.entity(new String(e.getMessage())).build();
		}

		return ret;
	}

	@GET
	@Path("/niProcurador={procurador}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProcuracoesPorprocurador(
			@PathParam("procurador") String niProcurador,
			@HeaderParam("PROCREST_AMBIENTE") int cdAmbiente) {

		ArrayList<Procuracao> retorno = new ArrayList<Procuracao>();
		StatusType responsestatus = null;

		try {
			ProcuracaoBC procuracaoBC = ProcuracaoBC.getInstance();
			procuracaoBC.setAmbiente(cdAmbiente);
			retorno = procuracaoBC.getProcuracoesPorProcurador(niProcurador);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String(e.getMessage())).build();
		}

		if (retorno.isEmpty()) {
			responsestatus = Response.Status.NOT_FOUND;

		} else {
			responsestatus = Response.Status.OK;
		}
		System.out.println("Status: " + responsestatus.getStatusCode());

		return Response.status(responsestatus).entity(retorno).build();

	}	

	@PUT
	@Consumes({ "application/json" })
	@Path("lote/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response criarProcuracaoLote(RequisicaoProcuracao requisicao, 
			@HeaderParam("PROCREST_AMBIENTE") int cdAmbiente) {		
		
		try {
			ProcuracaoBC procuracaoBC = ProcuracaoBC.getInstance();
			procuracaoBC.setAmbiente(cdAmbiente);
			procuracaoBC.criarProcuracaoEmLote(requisicao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String(e.getMessage())).build();
		}
		
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "PUT").build();
	}
	

	@GET
	@Path("/auth/usr={usuario}&pwd={senha}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response autenticar(@PathParam("usuario") String usuario,
			@PathParam("senha") String senha) {
		try {

			String token = issueToken(usuario);

			return Response
					.ok()
					.entity(AutenticacaoSqlAda.getInstance().autenticar(
							usuario, senha))
					.header("Access-Control-Allow-Origin", "*")
					.header(AUTHORIZATION, "Bearer " + token)
					.header("Access-Control-Allow-Methods", "GET").build();
		} catch (Exception e) {
			return Response.status(UNAUTHORIZED).build();
		}
	}

	private String issueToken(String login) {
		Key key = generateKey();
		String jwtToken = Jwts.builder().setSubject(login)
				.setIssuer(uriInfo.getAbsolutePath().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(SignatureAlgorithm.HS512, key).compact();
		System.out.println("#### generating token for a key : " + jwtToken
				+ " - " + key);
		return jwtToken;

	}

	private Key generateKey() {
		String keyString = "simplekey";
		Key key = new SecretKeySpec(keyString.getBytes(), 0,
				keyString.getBytes().length, "DES");
		return key;
	}

	@POST
	@Consumes({ "application/json" })
	@Path("autent/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logar(final Usuario input) {
		System.out.println("authObj: " + input.getNome());

		return Response.ok().entity(null)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST").build();
	}
	
	
	/*
	 * TODO: implementar criar procuração
	 * */

	/* 
	 * TODO: Implementar criação de procuração em lote
	 * {"dataInicioVigencia":"","dataFimVigencia":"","niTitular":"","niProcurador":["","",""],"sistema":["",""]}
	 * */

	
	/*
	 * TODO: implementar cancelar procuração
	 * */

	
		
	
}

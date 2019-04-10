/**
 * 
 */
package procuracoes.resource;

import java.util.ArrayList;

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
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import procuracoes.business.SistemaBC;
import procuracoes.model.Mensagem;
import procuracoes.model.Sistema;

import com.google.gson.Gson;

/**
 * @author allen
 *         http://localhost:8080/procRest/resource/procuracoes/68281455500/19007809334
 *         http://localhost:8080/ProcRest/resource/sistemas
 * 
 */
@Path("/sistema")
public class SistemaResource {

	
	Logger logger = LoggerFactory.getLogger(SistemaResource.class);
	
	SistemaBC sistemaBC;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getSistemas(@HeaderParam("PROCREST_AMBIENTE") int cdAmbiente) {		
		logger.info("Param Header: {}" ,cdAmbiente);		
		sistemaBC = SistemaBC.getInstance();
		
		try {
			sistemaBC.setAmbiente(cdAmbiente);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String(e.getMessage())).build();
		}
		ArrayList<Sistema> sistemas = sistemaBC.getSistemas();
		if (ArrayUtils.isEmpty(sistemas.toArray())) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String(new String("Falha ao buscas sistemas")))
					.build();
		}
		Gson gson = new Gson();
		for (Sistema sistema : sistemas) {			
			logger.info("Sistema: {}", gson.toJson(sistema));
		}
		
		return Response
				.ok()
				.entity(sistemas).build();

	}
	
	@GET
	@Path("/{codSistema}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getSistema(@PathParam("codSistema") String codSistema, @HeaderParam("PROCREST_AMBIENTE") int cdAmbiente) {
		logger.info("Resource: Recuperando sistema: {}", codSistema);
		sistemaBC = SistemaBC.getInstance();
		try {
			sistemaBC.setAmbiente(cdAmbiente);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String(e.getMessage())).build();
		}
		Sistema retorno = sistemaBC.getSistema(codSistema);
		if (retorno == null) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String(new String("Falha ao buscas sistemas")))
					.build();
		}
		return Response
				.ok()
				.entity(retorno).build();

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response novoSistema(String sistema, @HeaderParam("PROCREST_AMBIENTE") int cdAmbiente) {
		Sistema sistemaNovo = null;
		 
		Gson json = new Gson();
		Sistema entrada = json.fromJson(sistema, Sistema.class);
		logger.info("Resource: Cadastrando sistema: {}", entrada.toString());
		sistemaBC = SistemaBC.getInstance();
		try {
			sistemaBC.setAmbiente(cdAmbiente);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String(e.getMessage())).build();
		}
		try {
			sistemaNovo = sistemaBC.insertSistema(entrada);
		} catch (Exception e) {
			 Mensagem m = new Mensagem(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(m).build();
		}
		return Response.ok().entity(sistemaNovo).build();

	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response atualizaSistema(String sistema, @HeaderParam("PROCREST_AMBIENTE") int cdAmbiente) {
		 
		Gson json = new Gson();
		Sistema entrada = json.fromJson(sistema, Sistema.class);
		logger.info("Resource: Cadastrando sistema: {}", entrada.toString());
		
		sistemaBC = SistemaBC.getInstance();
		try {
			sistemaBC.setAmbiente(cdAmbiente);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new String(e.getMessage())).build();
		}
		
		try {
			sistemaBC.updateSistema(entrada);
		} catch (Exception e) {
			 Mensagem m = new Mensagem(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(m).build();
		}
		return Response.ok().build();

	}

}

/**
 * 
 */
package todo.resource;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import todo.dao.ProcuracaoDAO;
import todo.dao.TodoDao;
import todo.model.Todo;

/**
 * @author allen
 *         http://localhost:8080/procRest/procuracoes/68281455500/19007809334
 * 
 */
@Path("/procuracoes")
public class ProcuracoesResource {

	@GET
	@Path("{titular}/{procurador}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getProcuracoes(@PathParam("titular") String ni_titular,
			@PathParam("procurador") String ni_procurador) {
		return ProcuracaoDAO.getInstance().getProcuracao(ni_titular,
				ni_procurador);
	}

	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Todo getTodo(@PathParam("id") String id) {
		return TodoDao.getTodo(Integer.valueOf(id));
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Todo getTodos(Todo todo) {
		return TodoDao.addTodo(todo);
	}

	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateTodos(Todo todo) {
		TodoDao.updateTodo(todo);
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateTodos(@PathParam("id") String id) {
		TodoDao.deleteTodo(Integer.valueOf(id));
	}
}

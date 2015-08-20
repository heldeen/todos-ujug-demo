package net.eldeen.todos.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import com.codahale.metrics.annotation.Timed;
import net.eldeen.todos.api.Todo;
import net.eldeen.todos.jdbi.TodoDAO;
import org.skife.jdbi.v2.DBI;

/**
 *
 */
@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodosResource {
  private final DBI dbi;

  public TodosResource(final DBI dbi) {
    this.dbi = dbi;
  }

  @POST
  public Response create(Todo todo) {
    final Todo created;
    try (TodoDAO dao = dbi.open(TodoDAO.class)) {

      Long createdId = dao.create(todo);

      created = dao.getById(createdId);
    }
    URI relativeLocation = URI.create(created.getId().toString());

    return Response.created(relativeLocation)
                   .entity(created)
                   .build();
  }

  @GET
  @Timed(name = "list")
  public List<Todo> list(@QueryParam("all") boolean all) {
    try (TodoDAO dao = dbi.open(TodoDAO.class)) {
      return dao.list(all);
    }
  }

  @GET
  @Path("{id}")
  public Todo getById(@PathParam("id") Long id) {
    try (TodoDAO dao = dbi.open(TodoDAO.class)) {
      return dao.getById(id);
    }
  }

  @PUT
  @Path("{id}")
  public Todo update(@PathParam("id") Long id, Todo todo) {
    try (TodoDAO dao = dbi.open(TodoDAO.class)) {
      if (dao.update(todo) == 1) {
        return todo;
      }
      else {
        dao.create(todo);
      }
    }
    return todo;
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Long id) {
    try (TodoDAO dao = dbi.open(TodoDAO.class)) {
      dao.deleteById(id);
    }
  }
}

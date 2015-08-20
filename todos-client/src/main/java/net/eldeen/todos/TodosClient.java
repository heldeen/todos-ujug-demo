package net.eldeen.todos;

import static javax.ws.rs.client.Entity.entity;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import net.eldeen.todos.api.Todo;

/**
 *
 */
public class TodosClient {

  private final Client client;
  private final String apiRoot;

  public TodosClient(final Client client, final String apiRoot) {
    this.client = client;
    this.apiRoot = apiRoot;
  }

  public List<Todo> list(boolean all) {
    WebTarget webTarget = client.target(collectionUri());
    if (all) {
      webTarget = webTarget.queryParam("all", String.valueOf(all));
    }

    return webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .get(new GenericType<List<Todo>>() {
                    });
  }

  public Todo create(Todo todo) {
    return client.target(collectionUri())
                 .request(MediaType.APPLICATION_JSON_TYPE)
                 .post(entity(todo, MediaType.APPLICATION_JSON_TYPE), Todo.class);
  }

  public Todo getById(Long id) {
    return client.target(itemUri(id))
                 .request(MediaType.APPLICATION_JSON_TYPE)
                 .get(Todo.class);
  }

  public Todo update(Todo todo) {
    return client.target(itemUri(todo.getId()))
                 .request(MediaType.APPLICATION_JSON_TYPE)
                 .put(entity(todo, MediaType.APPLICATION_JSON_TYPE), Todo.class);
  }

  public void deleteById(Long id) {
    Response response = null;
    try {
      response = client.target(itemUri(id))
            .request()
            .delete();
      if (response.getStatus() >= 400) {
        throw new WebApplicationException(response.getStatus());
      }
    }
    finally {
      if (response != null) {
        response.close();
      }
    }
  }

  private String collectionUri() {
    return apiRoot + "/todos";
  }

  private String itemUri(Long id) {
    return collectionUri() + "/" + id;
  }
}

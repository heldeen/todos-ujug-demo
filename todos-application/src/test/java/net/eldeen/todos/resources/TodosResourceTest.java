package net.eldeen.todos.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.dropwizard.testing.junit.ResourceTestRule;
import net.eldeen.todos.api.Todo;
import net.eldeen.todos.jdbi.TodoDAO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

/**
 *
 */
public class TodosResourceTest {

  private TodoDAO dao = mock(TodoDAO.class);
  private DBI dbi = mock(DBI.class);

  //1) add TodosResourceTestRule
  @Rule
  public final ResourceTestRule resources = ResourceTestRule.builder()
                                                                   .addResource(new TodosResource(dbi))
                                                                   .build();


  @Before
  public void setup() {
    when(dbi.open(eq(TodoDAO.class))).thenReturn(dao);
  }

  @Test
  public void testGetById() throws Exception {
    Todo todo = new Todo();
    final Long id = Long.valueOf(1);
    todo.setId(id);

    when(dao.getById(eq(id))).thenReturn(todo);

    //2) make the request and assert the response - todosResourceAssert
    assertThat(resources.client().target("/todos/1").request().get(Todo.class)).isEqualTo(todo);

    verify(dao).getById(eq(id));
  }

  @Test
  public void testCreate() throws Exception {

  }

  @Test
  public void testList() throws Exception {

  }

  @Test
  public void testUpdate() throws Exception {

  }

  @Test
  public void testDelete() throws Exception {

  }
}
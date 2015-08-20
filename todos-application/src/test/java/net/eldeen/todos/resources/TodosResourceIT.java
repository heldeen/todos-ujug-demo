package net.eldeen.todos.resources;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;
import net.eldeen.todos.TodosApplication;
import net.eldeen.todos.TodosClient;
import net.eldeen.todos.TodosConfig;
import net.eldeen.todos.api.Todo;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 *
 */
public class TodosResourceIT {

  //1) create a DropwizardAppRule
  @ClassRule
  public static final DropwizardAppRule<TodosConfig> RULE =
    new DropwizardAppRule<>(TodosApplication.class, "config.yml");

  public static TodosClient client;

  @BeforeClass
  public static void createClient() {
    //2) setup test client based on DropwizardAppRule - todosTestClient
    client = new TodosClient(new JerseyClientBuilder(RULE.getEnvironment()).build("test client"),
                             format("http://localhost:%d/", RULE.getLocalPort()));
  }

  @Test
  public void testCRUD() throws Exception {

    assertThat(client.list(true)).isEmpty();

    Todo create = new Todo();
    create.setName("Present at UJUG");
    create.setDone(true);
    Todo created = client.create(create);

    assertThat(created.getName()).isEqualTo(create.getName());
    assertThat(created.getId()).isNotNull();

    assertThat(client.list(false)).isEmpty();
    assertThat(client.list(true)).contains(created);

    //everything else
  }

}

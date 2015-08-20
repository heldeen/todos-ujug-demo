package net.eldeen.todos;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.DBIHealthCheck;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.eldeen.todos.resources.TodosResource;
import org.skife.jdbi.v2.DBI;

/**
 * 1) extend application
 * 8) add main method - todosMain
 */
public class TodosApplication extends Application<TodosConfig> {

  public static void main(String... args) throws Exception {
    new TodosApplication().run(args);
  }

  //2) override getName()
  @Override
  public String getName() {
    return "todos";
  }

  //6) override initialize()
  //7) add migrationsBundle

  @Override
  public void initialize(final Bootstrap<TodosConfig> bootstrap) {
    /*
    java -jar todos.jar db migrate config.yml
    java -jar todos.jar db status config.yml
     */
    bootstrap.addBundle(new MigrationsBundle<TodosConfig>() {
      public DataSourceFactory getDataSourceFactory(final TodosConfig configuration) {
        return configuration.getDataSourceFactory();
      }
    });
  }

  //3) implement run
  //4) setup JDBI - dbiRun
  //5) register Todos jersey resource
  @Override
  public void run(final TodosConfig configuration, final Environment environment) throws Exception {
    final DBI dbi = new DBIFactory().build(environment, configuration.getDataSourceFactory(), "postgresql");
    final HealthCheck dbiHealthCheck = new DBIHealthCheck(dbi,
                                                          configuration.getDataSourceFactory().getValidationQuery());
    //curl http://localhost:8081/healthcheck
    environment.healthChecks().register("db", dbiHealthCheck);

    environment.jersey().register(new TodosResource(dbi));
  }
}
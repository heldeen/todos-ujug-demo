package net.eldeen.todos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 *
 */
public class TodosConfig extends Configuration {

  //1) add dataSourceFactoryConfig
  @Valid
  @NotNull
  @JsonProperty
  private DataSourceFactory dataSourceFactory = new DataSourceFactory();

  public DataSourceFactory getDataSourceFactory() {
    return dataSourceFactory;
  }
}

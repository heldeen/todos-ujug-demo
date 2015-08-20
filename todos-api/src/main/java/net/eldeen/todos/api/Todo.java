package net.eldeen.todos.api;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.Objects;

public class Todo {
  private Long id;
  private String name;
  private boolean done;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Todo that = (Todo) obj;

    return Objects.equals(this.id, that.id)
      && Objects.equals(this.name, that.name)
      && Objects.equals(this.done, that.done);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, done);
  }

  @Override
  public String toString() {
    return toStringHelper(this).add("id", id)
                                             .add("name", name)
                                             .add("done", done)
                                             .toString();
  }
}
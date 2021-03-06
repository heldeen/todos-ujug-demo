package net.eldeen.todos.jdbi;

import java.io.Closeable;
import java.util.List;

import net.eldeen.todos.api.Todo;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

/**
 *
 */
@RegisterMapperFactory(BeanMapperFactory.class)
public abstract class TodoDAO implements GetHandle, Closeable {

  public List<Todo> list(boolean all) {
    String sql = all ? "select * from todos" : "select * from todos where done = false";

    return getHandle().createQuery(sql)
                      .map(Todo.class)
                      .list();
  }

  @SqlUpdate("insert into todos (name, done) values (:name, :done)")
  @GetGeneratedKeys
  public abstract long create(@BindBean Todo todo);

  @SqlQuery("select * from todos where id = :id")
  public abstract Todo getById(@Bind("id") Long id);

  @SqlUpdate("update todos set name = :name, done = :done where id = :id")
  public abstract int update(@BindBean Todo todo);

  @SqlUpdate("delete from todos where id = :id")
  public abstract int deleteById(@Bind("id") Long id);

  public abstract void close();
}

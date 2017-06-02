package kr.or.connect.todo.persistence;

public class TodoSqls {
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	static final String SELECT_ALL = "SELECT * FROM todo ORDER BY date DESC";

    static final String UPDATE = "UPDATE todo SET completed = :completed WHERE id = :id";

    static final String DELETE_BY_COMPLETED="DELETE FROM todo WHERE completed=:completed";

    static final String SELECT_BY_ID="SELECT * FROM todo where id=:id";

}

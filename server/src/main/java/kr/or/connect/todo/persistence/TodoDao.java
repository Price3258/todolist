package kr.or.connect.todo.persistence;

import javax.sql.DataSource;

import kr.or.connect.todo.domain.Todo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.or.connect.todo.persistence.TodoSqls.*;

@Repository
public class TodoDao{
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Todo> rowMapper= BeanPropertyRowMapper.newInstance(Todo.class);

	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingColumns("todo")
				.usingGeneratedKeyColumns("id");
	}

	public List<Todo> selectAll(){
        Map<String,Object> params=Collections.emptyMap();
		return jdbc.query(SELECT_ALL,params,rowMapper);
    }

	public Integer insert(Todo todo){
		SqlParameterSource params=new BeanPropertySqlParameterSource(todo);

		return insertAction.executeAndReturnKey(params).intValue();

	}
	public int update(Todo todo){
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return jdbc.update(UPDATE,params);
	}
	public int deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(DELETE_BY_ID, params);
	}

	public int deleteByCompleted(Integer completed) {
		Map<String, ?> params = Collections.singletonMap("completed", completed);
		return jdbc.update(DELETE_BY_COMPLETED, params);
	}

	public Todo selectById(Integer id){
		Map<String, Object> params = new HashMap<>();
		params.put("id",id);
		return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
	}
	
	public Integer countAllTodo(){
		Integer count=0;
		return jdbc.query(COUNT_ALL_TODO,count,rowMapper);
	}
	

}


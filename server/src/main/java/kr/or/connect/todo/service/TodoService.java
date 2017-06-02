package kr.or.connect.todo.service;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;
import org.springframework.stereotype.Service;

import java.util.Collection;


/**
 * Created by wontae on 2017. 5. 26..
 */

@Service
public class TodoService {

    private final TodoDao todoDao;
    public TodoService (TodoDao todoDao){
        this.todoDao=todoDao;
    }



    public Collection<Todo> findAll(){
        return todoDao.selectAll();
    }

    public Todo create(Todo todo){
        Integer id=todoDao.insert(todo);
        todo.setId(id);
        return todo;
    }

    public boolean updateCompleted(Todo todo){
        int affected =todoDao.update(todo);
        return affected==1;
    }
    public boolean delete(Integer id) {
        int affected = todoDao.deleteById(id);
        return affected == 1;
    }
    public boolean deleteByCompleted(Integer completed){
        int affected=todoDao.deleteByCompleted(completed);
        return affected==1;
    }
    public Todo findById(Integer id){
        return todoDao.selectById(id);
    }



}

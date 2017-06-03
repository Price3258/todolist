package kr.or.connect.todo.api;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

/**
 * Created by wontae on 2017. 5. 26..
 */

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;
    private final Logger log = LoggerFactory.getLogger(TodoController.class);


    @Autowired
    public TodoController(TodoService todoService){
        this.todoService=todoService;
    }

    @GetMapping
    Collection<Todo> readList(){
        return todoService.findAll();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Todo create(@RequestBody Todo todo){
        Todo newTodo = todoService.create(todo);
        log.info("todo created : {}" , newTodo);
        return todo;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable Integer id,@RequestBody Todo todo){
        todo.setId(id);
        todoService.updateCompleted(todo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        todoService.delete(id);
    }


    @DeleteMapping("/completed/{completed}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteByCompleted(@PathVariable Integer completed) {

        todoService.deleteByCompleted(completed);
    }
}

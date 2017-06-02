package kr.or.connect.todo.domain;

import java.util.Date;

/**
 * Created by wontae on 2017. 5. 26..
 */


public class Todo {

    private Integer id;
    private String todo;
    private Integer completed;
    private Date date;

    public Todo() {}

    public Todo(Integer id, String todo, Integer completed, Date date) {
        this.id = id;
        this.todo = todo;
        this.completed = completed;
        this.date = date;
    }

    public Todo(String todo) {
        this.todo = todo;
    }
    public Todo(String todo,Integer completed){
        this.todo=todo;
        this.completed=completed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }


    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", todo='" + todo + '\'' +
                ", completed=" + completed +
                '}';
    }
}

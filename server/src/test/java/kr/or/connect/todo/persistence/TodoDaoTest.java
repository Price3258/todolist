package kr.or.connect.todo.persistence;

import kr.or.connect.todo.domain.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
/**
 * Created by wontae on 2017. 6. 2..
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TodoDaoTest {

    @Autowired
    private TodoDao todoDao;

    @Test
    public void shouldSelectAll(){
        List<Todo> todoList=todoDao.selectAll();
        assertThat(todoList, is(notNullValue()));
        System.out.println(todoList);
    }

    @Test
    public void shouldInsertAndSelect(){
        //given
        Todo todo=new Todo("와우 황금 전설 카드");

        //when
        Integer id=todoDao.insert(todo);

        //then
        Todo selected =todoDao.selectById(id);
        System.out.println(selected);
        assertThat(selected.getTodo(),is("와우 황금 전설 카드"));
    }

    @Test
    public void shouldDelete(){
        //given
        Todo todo =new Todo("와우우 전설카드");
        Integer id=todoDao.insert(todo);

        //when
        int affected=todoDao.deleteById(id);

        //Then
        assertThat(affected,is(1));

    }
    @Test
    public void shouldUpdate(){

        //Given
        Todo todo=new Todo("가로쉬 헬스크림");
        Integer id=todoDao.insert(todo);

        //When
        todo.setId(id);
        todo.setCompleted(1);
        int affected=todoDao.update(todo);
        //Then
        assertThat(affected,is(1));
        Todo updated=todoDao.selectById(id);
        System.out.println(updated);
        assertThat(updated.getCompleted(),is(1));




    }
}

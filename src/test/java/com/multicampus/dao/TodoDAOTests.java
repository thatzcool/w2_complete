package com.multicampus.dao;

import com.multicampus.w2.dao.TodoDAO;
import domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {
    private TodoDAO todoDAO;

    @BeforeEach
    public void ready(){
        todoDAO = new TodoDAO();
    }

    @Test
    public void testTime() throws Exception{

        System.out.println(todoDAO.getTime2() );

    }

    @Test
    public void testInsert() throws Exception{
        TodoVO todoVO = TodoVO.builder().title("Sample Title 7...")
                .dueDate(LocalDate.of(2023,03,10))
                .build();

        todoDAO.insert(todoVO);

    }

    @Test
    public void testList() throws Exception{
        List<TodoVO> list = todoDAO.selectAll();
        list.forEach(vo -> System.out.println(vo));


    }
    @Test
    public void testSelectOne() throws  Exception{
     Long tno = 1L; //tbl_todo 테이블에 있는 tno 주기
     TodoVO vo = todoDAO.selectOne(tno);
        System.out.println(vo);

    }

    @Test
    public void testUpdateOne() throws Exception{
        TodoVO todoVO = TodoVO.builder()
                .tno(1L)
                .title("Sample Title 9...")
                .dueDate(LocalDate.of(2023,03,11))
                .finished(true)
                .build();

        todoDAO.updateOne(todoVO);

    }


    @Test
    public void testDeleteOne() throws Exception{
        Long tno = 1L;

        todoDAO.deleteOne(tno);

    }
}

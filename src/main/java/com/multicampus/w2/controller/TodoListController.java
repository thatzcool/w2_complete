package com.multicampus.w2.controller;

import com.multicampus.w2.dto.TodoDTO;
import com.multicampus.w2.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name="todoListController", urlPatterns ="/todo/list" )
@Log4j2   //로그를 기록할 수 있도록
public class TodoListController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;  //service injection

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       log.info("todoList .........");
    //HttpServletRequest 의 SetAttribute() 이용해서 TodoService객체가 반환한 데이터를 저장하고 RequestDispatcher를 이요해서 list.jsp 로 전달한다.
    try{
        List<TodoDTO> dtoList = todoService.listAll();
        request.setAttribute("dtoList",dtoList);
        request.getRequestDispatcher("/WEB-INF/todo/list.jsp").forward(request,response);
    }catch (Exception e){
        log.error(e.getMessage());
        throw new ServletException("list error");
    }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}


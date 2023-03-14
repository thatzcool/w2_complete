package com.multicampus.w2.controller;

import com.multicampus.w2.dto.TodoDTO;
import com.multicampus.w2.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "todoRegisterController", value = "/todo/register")
@Log4j2
public class TodoRegisterController extends HttpServlet {
    private TodoService todoService = TodoService.INSTANCE;
    private  final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         log.info("----/todo/register GET------");

         HttpSession session = request.getSession();  // 새로운 값이 생성되어 브라우저로 전송된다.
         if(session.isNew()){   //기존에 JSESSIONID 가 없는 새로운 생성자라면
             log.info("JESSIONID new user");
             response.sendRedirect("/login");
             return;
         }
         if(session.getAttribute("logininfo") == null){
             log.info("longin info not found");
             response.sendRedirect("/login");
             return;
         }



         request.getRequestDispatcher("/WEB-INF/todo/register.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TodoDTO todoDTO = TodoDTO.builder()
                .title(request.getParameter("title"))
                .dueDate(LocalDate.parse(request.getParameter("dueDate"),DATETIMEFORMATTER))
                .build();

        log.info("-------todo/register----POST----");
        log.info(todoDTO);
        try{
            todoService.register(todoDTO);
              }catch(Exception e){
            e.printStackTrace();
        }
        response.sendRedirect("/todo/list");
    }
}

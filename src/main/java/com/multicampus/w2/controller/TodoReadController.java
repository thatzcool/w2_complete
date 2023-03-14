package com.multicampus.w2.controller;

import com.multicampus.w2.dto.TodoDTO;
import com.multicampus.w2.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "todoReadController", value = "/todo/read")
@Log4j2
public class TodoReadController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            Long tno = Long.parseLong(request.getParameter("tno"));
            TodoDTO todoDTO = todoService.get(tno);

            //모델 담기
            request.setAttribute("dto",todoDTO);

            //쿠키 찾기
            /*"viewTodos" 이름의 쿠키를 찾고(findCookie()) , 쿠키의 내용물을 검사한 후 , 만약 조회한 적이 없는 번호라면 쿠키의 내용물을 갱신해서
 브라우저로 보내준다. 쿠키를 변경할때는 다시 경로, 유효시간을 셋팅해준다. */
            Cookie viewTodoCookie = findCookie(request.getCookies(),"viewTodos");
            String todoListStr = viewTodoCookie.getValue();
            boolean exist = false;
            if(todoListStr != null && todoListStr.indexOf(tno+"-")>=0){
                exist = true;
            }
            log.info("exist : " + exist);
            if(!exist){
                todoListStr += tno+"-";
                viewTodoCookie.setValue(todoListStr);
                viewTodoCookie.setMaxAge(60*60*24);
                viewTodoCookie.setPath("/");
                response.addCookie(viewTodoCookie);
            }

            request.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(request,response);


        }catch (Exception e){
            log.error(e.getMessage());
            throw new ServletException("read error");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String cookieName) {
        Cookie targetCookie = null;

        if(cookies != null && cookies.length > 0){

            for(Cookie ck: cookies){
                if(ck.getName().equals(cookieName)) {
                    targetCookie = ck;
                    break;
                }
            }
        }
        if(targetCookie == null){
            targetCookie = new Cookie(cookieName,"");
            targetCookie.setPath("/");
            targetCookie.setMaxAge(60*60*24);
        }
        return targetCookie;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

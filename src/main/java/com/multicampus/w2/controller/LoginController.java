package com.multicampus.w2.controller;

import com.multicampus.w2.dto.MemberDTO;
import com.multicampus.w2.service.MemberService;
import lombok.extern.java.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Callable;

@WebServlet("/login")
@Log
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             log.info("login get...........");
             request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("login post...........");

        String mid = request.getParameter("mid");
        String mpw = request.getParameter("mpw");

        //자동로그인 여부 기능
        String auto = request.getParameter("auto");
        boolean rememberMe = auto != null && auto.equals("on");
        log.info("---------");
        log.info(String.valueOf(rememberMe));


       // MemberVO 와 MemberDTO 수정해야 한다.  --  uuid 추가되었으므로
        try{
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid,mpw);

            //java.util 의 UUID를 이용하여 임의의 번호 생성

            if(rememberMe) {
                String uuid = UUID.randomUUID().toString();
                MemberService.INSTANCE.updateUuid(mid, uuid);
                memberDTO.setUuid(uuid);

                //브라우저에  쿠키를 생성해서 전송
                 Cookie rememberCookie = new Cookie("remember-me",uuid);
                 rememberCookie.setMaxAge(60*60*24*7);
                 rememberCookie.setPath("/");
                 response.addCookie(rememberCookie);
            }


            HttpSession session = request.getSession();
            session.setAttribute("loginInfo" , memberDTO);
            response.sendRedirect("/todo/list");


        } catch (Exception e){
             response.sendRedirect("/login?result=error");

        }




    }
}

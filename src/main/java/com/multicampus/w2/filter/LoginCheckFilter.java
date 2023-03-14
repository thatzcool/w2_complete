package com.multicampus.w2.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import com.multicampus.w2.dto.MemberDTO;
import com.multicampus.w2.service.MemberService;
@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         log.info("Login check filter...");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        if(session.getAttribute("loginInfo") != null){
            chain.doFilter(request,response);
            return;
        }

        //session에 loginInfo 값이 없다면
        //쿠키를 체크
        Cookie cookie = findCookie(((HttpServletRequest) request).getCookies(),"remember-me");
        //세션에도 없고 쿠키에도 없다면 그냥 로그인으로
        if(cookie == null) {
            resp.sendRedirect("/login");
            return;
        }
        //쿠키가 존재하는 상황이라면
        log.info("------cookie 존재하면--------");
        String uuid = cookie.getValue();

        try{  //데이터베이스 확인
            //쿠기 uuid 값으로 사용자 정보 조회  ==> MemberDTO 저장
             MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);
             if(memberDTO == null){
                 throw  new Exception("cookie value is not valid");
             }
             session.setAttribute("loginInfo",memberDTO);
             chain.doFilter(request,response);

            //MemberDTO 를 세션에 저장 (추가)  ==>  TodoReadController

        }catch (Exception e){
            e.printStackTrace();
            ((HttpServletResponse) response).sendRedirect("/login");

        }










    }

    private Cookie findCookie(Cookie[] cookies, String name) {
        if(cookies == null  || cookies.length == 0) {
            return null;
        }

        Optional<Cookie> result = Arrays.stream(cookies).filter(ck -> ck.getName().equals(name)).findFirst();
         return result.isPresent()? result.get():null;
    }
}
//Filter 인터페이스를 import javax.servlet.Filter   : doFilter()필터링이 필요한 로직을 구현하는 메소드
//@WebFilter 어너테이션 추가
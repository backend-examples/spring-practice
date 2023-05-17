package com.example.interceptor;

import com.example.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PrivilegeInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 逻辑：判断用户是否登录，本质是判断session中是否存在当前的user
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            // 没有登录, 重定向到登录页
            response.sendRedirect(request.getContextPath()+ "/login.jsp");
            return false;
        }

        // 放行、访问目标资源
        return true;
    }
}

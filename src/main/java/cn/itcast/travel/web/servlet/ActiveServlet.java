package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserSericeImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        //2.调用service
        UserService userService = new UserSericeImpl();
        boolean active = userService.active(code);
        //3.处理结果，跳转页面
        if(active){
            response.sendRedirect(request.getContextPath()+"/active_ok.html");
        } else {
            response.sendRedirect(request.getContextPath()+"/active_lose.html");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

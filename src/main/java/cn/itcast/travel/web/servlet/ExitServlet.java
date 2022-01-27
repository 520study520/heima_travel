package cn.itcast.travel.web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/exitServlet")
public class ExitServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //删除当前服务器中的user对象数据
        request.getSession().removeAttribute("user");
        //重定向到index.html页面
        response.sendRedirect(request.getContextPath() + "/login.html");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

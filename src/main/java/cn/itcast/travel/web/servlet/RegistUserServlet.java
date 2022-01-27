package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.TrunJson;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserSericeImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //因为有了过滤器CharchaterFilter处理了页面字符集，所以这里就需要指定页面字符集了
        //1.判断返回回来的验证码数据是否正确
        String check = request.getParameter("check");
        //1.1获取和session里存储的验证码
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");  //无论是否注册成功都要删除掉验证码，保证用户不调皮
        //1.2判断验证码们是否匹配
        //创建数据转json对象
        TrunJson trunJson = new TrunJson();
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //1.3验证码错误给服务器传异常信息，并且结束掉当前方法
            String json = trunJson.getResultInfoJson(false, "验证码错误！！！", null);
            //1.5设置响应头
            response.setContentType("application/json;charset=utf-8");
            //1.6向前台发送数据
            response.getWriter().write(json);
            return;
        }
        //创建用户信息对象
        User user = new User();
        //获取前台发送过来的数据map集合
        Map<String, String[]> map = request.getParameterMap();
        try {
            //2.封装数据到user对象中
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service层处理用户对象数据
        UserService userService = new UserSericeImpl();
        boolean flag = userService.regist(user);
        //4.判断flag是否为true
        if(flag){
            //4.2用户已存在
            String json = trunJson.getResultInfoJson(flag, "登录成功！！！", null);
            //4.2.2发送数据到前台
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        } else {
            //4.2用户已存在
            String json = trunJson.getResultInfoJson(flag, "用户名已存在！！！", null);
            //4.2.2发送数据到前台
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }


}

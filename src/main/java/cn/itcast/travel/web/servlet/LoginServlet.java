package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.TrunJson;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserSericeImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

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
        //2.获取客户端返回的用户信息
        Map<String, String[]> userMap = request.getParameterMap();
        User user = new User();    //用于存储客户端返回回来的用户信息
        try {
            BeanUtils.populate(user,userMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service层查询用户信息
        UserService userService = new UserSericeImpl();
        User login = userService.login(user);
        //用户不存在或密码错误验证
        if(login == null){
            //如果返回的用户信息为null，就证明用户不存在或密码错误
            //存储错误信息
            String json = trunJson.getResultInfoJson(false, "用户不存在或密码错误", null);
            //1.5设置响应头
            response.setContentType("application/json;charset=utf-8");
            //1.6向前台发送数据
            response.getWriter().write(json);
            return;
        }
        //状态未激活
        if(login != null && !"Y".equals(login.getStatus())){
            //如果返回的用户状态不为Y，就证明用户邮箱未激活
            //存储错误信息
            String json = trunJson.getResultInfoJson(false, "邮箱未激活", null);
            //1.5设置响应头
            response.setContentType("application/json;charset=utf-8");
            //1.6向前台发送数据
            response.getWriter().write(json);
            return;
        }
        //登录成功
        if(login != null && "Y".equals(login.getStatus())){
            //如果返回的用户状态不为Y，就证明用户邮箱未激活
            //存储错误信息
            String json = trunJson.getResultInfoJson(true, "登录成功", null);
           //将用户在服务器共享
            request.getSession().setAttribute("user",login);
            //1.5设置响应头
            response.setContentType("application/json;charset=utf-8");
            //1.6向前台发送数据
            response.getWriter().write(json);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

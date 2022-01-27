package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserSericeImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")   // /user：虚拟目录 /* ：方法名称
public class UserServlet extends BaserServlet {
        private  UserService userService = new UserSericeImpl();  //用户服务类

    /**
     * 注册方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
            /*//1.5设置响应头
            response.setContentType("application/json;charset=utf-8");
            //1.6向前台发送数据
            response.getWriter().write(json);*/
            writeValue(json,response);
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
        String json ="";
        //3.调用service层处理用户对象数据
        //UserService userService = new UserSericeImpl();
        boolean flag = userService.regist(user);
        //4.判断flag是否为true
        if(flag){
            //4.2用户已存在
           json = trunJson.getResultInfoJson(flag, "登录成功！！！", null);
            /*//4.2.2发送数据到前台
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);*/
        } else {
            //4.2用户已存在
           json = trunJson.getResultInfoJson(flag, "用户名已存在！！！", null);
           /* //4.2.2发送数据到前台
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);*/
        }
        writeValue(json,response);
    }

    /**
     * 登录方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            /*//1.5设置响应头
            response.setContentType("application/json;charset=utf-8");
            //1.6向前台发送数据
            response.getWriter().write(json);*/
            writeValue(json,response);
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
        String json ="";
                //3.调用service层查询用户信息
        //UserService userService = new UserSericeImpl();
        User login = userService.login(user);
        //用户不存在或密码错误验证
        if(login == null){
            //如果返回的用户信息为null，就证明用户不存在或密码错误
            //存储错误信息
           json = trunJson.getResultInfoJson(false, "用户不存在或密码错误", null);

        }
        //状态未激活
        if(login != null && !"Y".equals(login.getStatus())){
            //如果返回的用户状态不为Y，就证明用户邮箱未激活
            //存储错误信息
           json = trunJson.getResultInfoJson(false, "邮箱未激活", null);

        }
        //登录成功
        if(login != null && "Y".equals(login.getStatus())){
            //如果返回的用户状态不为Y，就证明用户邮箱未激活
            //存储错误信息
            json = trunJson.getResultInfoJson(true, "登录成功", null);
            //将用户在服务器共享
            request.getSession().setAttribute("user",login);

        }
        writeValue(json,response);
    }

    /**
     * 获取单个用户信息方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //发送用户信息
        /*ObjectMapper ob = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        ob.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);
    }

    /**
     * 退出方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //删除当前服务器中的user对象数据
        request.getSession().removeAttribute("user");
        //重定向到index.html页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 激活方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        //2.调用service
       // UserService userService = new UserSericeImpl();
        boolean active = userService.active(code);
        //3.处理结果，跳转页面
        if(active){
            response.sendRedirect(request.getContextPath()+"/active_ok.html");
        } else {
            response.sendRedirect(request.getContextPath()+"/active_lose.html");
        }

    }

    /**
     * 分页查询收藏数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favoritePageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取uid，currentPage，PageSzie
        String uidStr = request.getParameter("uid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        //非空验证
        int uid ;
        if(uidStr != null && uidStr.length() > 0 && !"null".equals(uidStr)){
            uid = Integer.parseInt(uidStr);
        } else {    //没有uid说明用户没有登录，则返回false
            writeValue(new ResultInfo(false,null,"您还未登录请先登录！！！"),response);
            return;
        }

        int currentPage ;
        if(currentPageStr != null && currentPageStr.length() > 0 ){
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        int pageSize ;
        if(pageSizeStr != null && pageSizeStr.length() > 0 ){
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 12;
        }
        PageBean<Route> pageBean = userService.myFavorite(uid,currentPage,pageSize);
        writeValue(pageBean,response);
    }
}

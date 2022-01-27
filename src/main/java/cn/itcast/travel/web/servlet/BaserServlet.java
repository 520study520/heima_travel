package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaserServlet extends HttpServlet {

    //重写HttpServlet中的服务方法(service方法),用于接收用户的请求用反射技术分发不同功能的方法
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("BaseServlet被执行了！！！");

        //1.获取请求路径
        String requestURI = req.getRequestURI();    ///travel/user/add
        //System.out.println(requestURI);
        //2.获取方法名称      /user/add
        String methodName = requestURI.substring(requestURI.lastIndexOf("/") + 1);  //add
        //System.out.println(methodName);
        try {
            //谁调用的当前方法this就是谁
           // System.out.println(this);   //cn.itcast.travel.web.servlet.UserServlet@7dd86009
            //3.使用反射技术调用方法，不要使用忽略权限修饰符的方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法，不要使用暴力反射
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 序列化为json数据并响应给客户端
     * @param obj
     * @param response
     * @throws IOException
     */
    protected void writeValue(Object obj , HttpServletResponse response) throws IOException {
        ObjectMapper ob = new ObjectMapper();
        //设置响应页面类型
        response.setContentType("application/json;charset=utf-8");
       // response.getWriter().write(ob.writeValueAsString(obj));
        //响应数据
        ob.writeValue(response.getOutputStream(),obj);
    }
    /**
     * 序列化为json数据并响应给客户端
     * @param obj 要序列化的对象
     * @throws IOException
     */
    protected String writeValue(Object obj ) throws IOException {
        ObjectMapper ob = new ObjectMapper();
       return ob.writeValueAsString(obj);
    }
    /**
     * 序列化为json数据并响应给客户端
     * @param response
     * @throws IOException
     */
    protected void writeValue(String json , HttpServletResponse response) throws IOException {
        //设置响应页面类型
        response.setContentType("application/json;charset=utf-8");
        //响应数据
         response.getWriter().write(json);
    }
}

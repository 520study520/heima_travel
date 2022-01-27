package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")   // /user：虚拟目录 /* ：方法名称
public class CategoryServlet extends BaserServlet {
    //创建分类展示数据服务层对象
    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 查询全部用户信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        List<Category> categories = categoryService.queryAll();
        //序列化为json发送的客户端
        writeValue(categories,response);
    }



}

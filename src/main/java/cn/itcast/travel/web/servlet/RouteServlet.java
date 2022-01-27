package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@WebServlet("/route/*")
public class RouteServlet extends BaserServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    /**
     * 分页展示数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取客户端传递过来的参数对应值
        String pageSizeStr = request.getParameter("pageSize");
        String currentPageStr = request.getParameter("currentPage");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        if(rname!=null && !"null".equals(rname)){
            if(!(Charset.forName("GBK").newEncoder().canEncode(rname))){  //如果该字符串乱码就重新编码该字符串
                rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
            }
        } else {
            rname = null;
        }
        //2.将获取到的值转为int类型
        int pageSzie;
        if(pageSizeStr != null && pageSizeStr.length() > 0){   //非空验证
            pageSzie = Integer.parseInt(pageSizeStr);
        } else {
            pageSzie = 5;
        }
        int currentPage;
        if(currentPageStr != null && currentPageStr.length() > 0 ){   //非空验证
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        int cid;
        if(cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)){   //非空验证
            cid = Integer.parseInt(cidStr);
        } else {
            cid = 0;
        }
        //3.调用routeQuery方法,获取数据对象PageBean
        PageBean<Route> pageBean = routeService.routeQuery(cid, currentPage, pageSzie,rname);
        //4.序列化为json并发送的页面
        writeValue(pageBean,response);


    }

    /**
     * 查询详情
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");  //获取rid
        int rid = 0;
        if(ridStr!=null){  //将rid转为int类型
           rid = Integer.parseInt(ridStr);
        }

        //调用service中findOne方法，获得Route对象
        Route route = routeService.findOne(rid);
        System.out.println(route);
        //将Route对象序列化为json数据并输出
        writeValue(route,response);
    }

    /**
     * 查询线路是否被收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");   //获取rid
        int rid = 0;
        if(ridStr != null){
            rid = Integer.parseInt(ridStr);
        }
        //获取uid
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if(user != null){
            uid = user.getUid();
        } else {
            uid = 0;
        }
        //调用FavoriteService中的isFavorite方法
        boolean flag = favoriteService.isFavorite(rid, uid);

        //写出到前台页面
        writeValue(flag,response);
    }

    /**
     * 点击收藏方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");   //获取rid
        int rid = 0;
        if(ridStr != null){
            rid = Integer.parseInt(ridStr);
        }
        //获取uid
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if(user != null){
            uid = user.getUid();
        } else {
            return;
        }
        boolean flag = favoriteService.updateFavorite(rid,uid);   //更新收藏表
        routeService.updateRouteCount();  //更新route表
        writeValue(flag,response);
    }

    /**
     * 收藏排行榜
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        //2.将获取到的值转为int类型
        int pageSzie;
        if(pageSizeStr != null && pageSizeStr.length() > 0){   //非空验证
            pageSzie = Integer.parseInt(pageSizeStr);
        } else {
            pageSzie = 8;
        }
        int currentPage;
        if(currentPageStr != null && currentPageStr.length() > 0 ){   //非空验证
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        Map<String, String[]> parameterMap = request.getParameterMap();  //获取模糊查询参数
        parameterMap.forEach(new BiConsumer<String, String[]>() {
            @Override
            public void accept(String s, String[] strings) {

            }
        });
        PageBean<Route> pageBean = routeService.findRouteByCount(currentPage,pageSzie,parameterMap);
        //将获取到的pageBean对象数据序列化为json发送到前台
        writeValue(pageBean,response);
    }

    /**
     * 热门推荐
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findHot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取cid
        String cidStr = request.getParameter("cid");
        int cid = 0;
        if(cidStr != null && !"".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        //调用RouteService中的findHot方法得到数据集合
        List<Route> routes = routeService.findHot(cid);
        //将查询到的数据写入到前台
        writeValue(routes,response);

    }

    /**
     * 主题旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findTheme(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.findTheme();
        writeValue(routes,response);
    }

    /**
     * 最新旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findNewest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.findNewest();
        writeValue(routes,response);
    }
    /**
     * 人气旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findPopularity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.findPopularity();
        writeValue(routes,response);
    }

    /**
     * 国内旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findHeima_gn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.findHeima_gn();
        writeValue(routes,response);
    }
    /**
     * 境外旅游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findHeima_gw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> routes = routeService.findHeima_gw();
        writeValue(routes,response);
    }

}


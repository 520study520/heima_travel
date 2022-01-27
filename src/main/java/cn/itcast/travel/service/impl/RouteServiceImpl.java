package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;
import java.util.Map;

/**
 * @title: RouteServiceImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/8 下午 4:56
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();  //线路数据访问
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();  //详情页图片数据
    private SellerDao sellerDao = new SellerDaoImpl();  //商家数据
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> routeQuery(int cid, int currentPage, int pageSize,String rname) {
        //1.创建pageBean对象
        PageBean<Route> pageBean = new PageBean<>();
        //1.1添加现有数据到pageBean中
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        //2.获取总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        //3.计算分页查询开始位置
        int start = (currentPage-1) * pageSize;
        //4.获取数据集合
        List<Route> dataByPage = routeDao.findDataByPage(cid,start, pageSize,rname);
        //5.计算总页码
        int totalPage = (totalCount % pageSize == 0) ? totalCount / pageSize : (totalCount / pageSize) + 1;
        //6.向pageBean中添加dataBypage、总页码、总记录数
        pageBean.setList(dataByPage);
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }

    @Override
    public Route findOne(int rid) {
        //调用RouteDao中gainDao方法获取Route对象
        Route route = routeDao.gainRoute(rid);
        //调用RouteImgDao中的findImg方法获取图片数据集合
        List<RouteImg> routeImgs = routeImgDao.findImg(rid);
        //调用sellerDao中的findSeller方法得到商家数据
        Seller seller = sellerDao.findSeller(route.getSid());
        /*//调用favorite中的findCount方法
        int count = favoriteDao.findCount(rid);*/
        //向Route对象中添加数据
        route.setRouteImgList(routeImgs);
        route.setSeller(seller);
       /* route.setCount(count);*/
        return route;
    }

    @Override
    public void updateRouteCount() {
        //获取收藏表的rid和count
        List<Route> routes = favoriteDao.groupRid();
        //更新route表
        routeDao.updateRouteCount(routes);
    }

    @Override
    public PageBean<Route> findRouteByCount(int currentPage, int pageSzie, Map<String, String[]> parameterMap) {
        //创建PageBean对象
        PageBean<Route> pageBean = new PageBean<>();
        //向pageBean添加现有数据
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSzie);
        //获取总记录数
        int totalCount = routeDao.findTotalCount(parameterMap);
        //计算开始索引
        int start = (currentPage -1) * pageSzie;
        List<Route> dataByPage = routeDao.findDataByPage(start, pageSzie, parameterMap);
        //计算总页码
        int totalPage = totalCount % pageSzie == 0 ? totalCount / pageSzie : (totalCount / pageSzie) + 1;
        //向pageBean中添加获取到的数据
        pageBean.setTotalPage(totalPage);
        pageBean.setTotalCount(totalCount);
        pageBean.setList(dataByPage);
        return pageBean;
    }

    @Override
    public List<Route> findHot(int cid) {
        //调用routeDao中的findRouteByCount方法，得到Route对象集合
        List<Route> routes = routeDao.findRouteByCid(cid);
        return routes;
    }

    @Override
    public List<Route> findTheme() {
        List<Route> routes = routeDao.findThemeTop4();
        return routes;
    }

    @Override
    public List<Route> findNewest() {
        List<Route> routes = routeDao.findNewestTop4();
        return routes;
    }

    @Override
    public List<Route> findPopularity() {
        List<Route> routes = routeDao.findPopularityTop4();
        return routes;
    }

    @Override
    public List<Route> findHeima_gn() {
        List<Route> routes = routeDao.findHeima_gnTop4();
        return routes;
    }

    @Override
    public List<Route> findHeima_gw() {
        List<Route> routes = routeDao.findHeima_gwTop4();
        return routes;
    }


}

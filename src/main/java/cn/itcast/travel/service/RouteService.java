package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;
import java.util.Map;

/**
 * @title: RouteService 分类展示，业务逻辑
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/8 下午 4:54
 */
public interface RouteService {

    /**
     * 分类数据查询
     * @param cid 当前类别编号
     * @param currentPage 当前页码
     * @param pageSize 每页显示的条数
     * @return
     */
    PageBean<Route> routeQuery(int cid,int currentPage,int pageSize,String rname);


    /**
     * 根据rid获取Route对象
     * @param rid 线路id
     * @return
     */
    Route findOne(int rid);

    /**
     * 用于更新route表中的count数据
     */
    void updateRouteCount();

    /**
     * 收藏排行榜分页数据
     * @param currentPage
     * @param pageSzie
     * @param parameterMap 模糊查询数据
     * @return
     */
    PageBean<Route> findRouteByCount(int currentPage, int pageSzie, Map<String, String[]> parameterMap);

    /**
     * 根据cid获取热门的前5条数据
     * @param cid
     * @return
     */
    List<Route> findHot(int cid);

    /**
     * 主题旅游
     * @return
     */
    List<Route> findTheme();

    /**
     * 最新旅游
     * @return
     */
    List<Route> findNewest();

    /**
     * 人气旅游
     * @return
     */
    List<Route> findPopularity();

    /**
     * 国内游
     * @return
     */
    List<Route> findHeima_gn();

    /**
     * 境外游
     * @return
     */
    List<Route> findHeima_gw();
}

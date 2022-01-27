package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;
import java.util.Map;

/**
 * @title: RouteDao 分类展示，数据访问
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/8 下午 4:45
 */
public interface RouteDao {

    /**
     * 查询总记录数
     * @param cid 分类展示对应的一些数据共有的外键值
     * @return
     */
    int findTotalCount(int cid,String rname);

    /**
     * 查询分类数据
     * @param start 开始查询位置
     * @param pageSize 每页显示的记录数
     * @return
     */
    List<Route> findDataByPage(int cid,int start,int pageSize,String rname);

    /**
     * 根据rid获取Route对象
     * @param rid
     * @return
     */
    Route gainRoute(int rid);


    /**
     * 根据线路id集合，分页查询线路
     * @param rids
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> gainRoutes(List<Favorite> rids, int start, int pageSize);

    /**
     * 根据rids获取总记录数
     * @param rids
     * @return
     */
    int findTotalCount(List<Favorite> rids);

    /**
     * 更新route表中count数据
     */
    void updateRouteCount(List<Route> routes);

    /**
     * 查询count>0的数据
     * @return
     */
    int findTotalCount(Map<String, String[]> parameterMap);

    /**
     * 分页查询数据加模糊查询
     * @param start
     * @param pageSzie
     * @param parameterMap 模糊查询数据集合
     * @return
     */
    List<Route> findDataByPage(int start, int pageSzie, Map<String, String[]> parameterMap);

    /**
     * 根据cid降序查询前五条记录
     * @param cid
     * @return
     */
    List<Route> findRouteByCid(int cid);

    /**
     * 佛系主题，查询前4条记录
     * @return
     */
    List<Route> findThemeTop4();

    /**
     * 最新旅游，查询前4条记录
     * @return
     */
    List<Route> findNewestTop4();

    /**
     * 人气旅游，查询前4条记录
     * @return
     */
    List<Route> findPopularityTop4();

    /**
     * 国内旅游，查询前6条记录
     * @return
     */
    List<Route> findHeima_gnTop4();

    /**
     * 国内旅游，查询前6条记录
     * @return
     */
    List<Route> findHeima_gwTop4();
}

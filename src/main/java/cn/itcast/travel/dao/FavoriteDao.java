package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @title: FavoriteDao
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/24 下午 1:34
 */
public interface FavoriteDao {


    /**
     * 根据线路id和用户id获得Favorite对象
     * @param rid
     * @param uid
     * @return
     */
    Favorite findFavoriteByRidAndUid(int rid,int uid);

    /**
     * 查询收藏次数
     * @param rid
     * @return
     */
    int findCount(int rid);

    /**
     * 更新收藏表
     * @param rid
     * @param uid
     * @return
     */
    boolean updateByRidAndUid(int rid, int uid);

    /**
     * 根据uid获得rids
     * @param uid
     * @return
     */
    List<Favorite> findRidByUid(int uid);

    /**
     * 分组查询rid，并获得count数量
     * @return
     */
    List<Route> groupRid();
}

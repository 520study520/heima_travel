package cn.itcast.travel.service;

/**
 * @title: FavoriteService
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/24 下午 1:33
 */
public interface FavoriteService {


    /**
     * 查询当前线路是否被收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(int rid , int uid);

    /**
     * 更新收藏信息
     * @param rid
     * @param uid
     * @return
     */
    boolean updateFavorite(int rid, int uid);
}

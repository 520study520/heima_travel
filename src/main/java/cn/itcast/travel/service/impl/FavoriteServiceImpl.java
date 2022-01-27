package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

/**
 * @title: FavoriteServiceImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/24 下午 1:34
 */
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.findFavoriteByRidAndUid(rid, uid);  //获得Favorite
        return favorite != null;  //favorite为null返回false，不为null返回true
    }

    @Override
    public boolean updateFavorite(int rid, int uid) {
        boolean flag = favoriteDao.updateByRidAndUid(rid,uid);
        return flag;
    }
}

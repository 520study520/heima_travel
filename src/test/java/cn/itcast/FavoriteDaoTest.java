package cn.itcast;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import org.junit.Test;

import java.util.List;

/**
 * @title: FavoriteDaoTest
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/25 下午 4:20
 */
public class FavoriteDaoTest {
    @Test
    public void test1(){
        FavoriteDao dao = new FavoriteDaoImpl();
        List<Favorite> ridByUid = dao.findRidByUid(15);
        System.out.println(ridByUid.get(0).getRid());
    }
    @Test
    public void test2(){
        FavoriteDao dao = new FavoriteDaoImpl();
        List<Route> routes = dao.groupRid();
        System.out.println(routes.get(2).getCount());
    }

}

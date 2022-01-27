package cn.itcast;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: RouteDaoTest
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/25 下午 3:59
 */
public class RouteDaoTest {
    @Test
    public void test1(){
        RouteService routeService = new RouteServiceImpl();
        routeService.updateRouteCount();

    }
    @Test
    public void test2(){

    }

}

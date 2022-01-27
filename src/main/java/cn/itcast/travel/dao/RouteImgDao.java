package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

/**
 * @title: RouteImgDao
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/20 下午 8:13
 */
public interface RouteImgDao {


    /**
     * 根据线路id获取图片数据集合
     * @param rid
     * @return
     */
    List<RouteImg> findImg(int rid);

}

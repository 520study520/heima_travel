package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @title: SellerDao
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/20 下午 8:14
 */
public interface SellerDao {


    /**
     * 根据商家id查询商家信息
     * @param sid
     * @return
     */
    Seller findSeller(int sid);
}

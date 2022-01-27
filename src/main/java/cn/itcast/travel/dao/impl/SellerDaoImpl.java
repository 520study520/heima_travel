package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @title: SellerDaoImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/20 下午 8:18
 */
public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findSeller(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        Seller query = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
        return query;
    }
}

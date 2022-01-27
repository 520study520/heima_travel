package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @title: RouteImgDaoImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/20 下午 8:17
 */
public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findImg(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        List<RouteImg> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return query;
    }
}

package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @title: FavoriteDaoImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/24 下午 1:34
 */
public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findFavoriteByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = " select * from tab_favorite where rid = ? and uid = ?";
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {

        }
        return favorite;
    }

    @Override
    public int findCount(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public boolean updateByRidAndUid(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        int update = jdbcTemplate.update(sql, rid, new Date(), uid);
        return update > 0;   //>0返回true，<= 0返回false
    }

    @Override
    public List<Favorite> findRidByUid(int uid) {
        List<Favorite> rids = null;
        try {
            String sql = "select rid from tab_favorite where uid = ? ";
            rids = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid);
        } catch (DataAccessException e) {
            //如果一条记录也没就会报错，这里抓一下异常就不打印了
        }
        return rids;
    }

    @Override
    public List<Route> groupRid() {
        String sql = "SELECT f.`rid`,COUNT(*) count FROM tab_favorite f WHERE f.`rid` = rid GROUP BY rid";
        List<Route> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return query;
    }
}

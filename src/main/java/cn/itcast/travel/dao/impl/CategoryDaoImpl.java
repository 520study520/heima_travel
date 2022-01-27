package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @title: CategoryDaoImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/6 下午 4:27
 */
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        List<Category> categoryList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        //由于第一次获取是从数据库获取数据的，数据是无序的这里为了首次也能有序，这里就使用了集合工具类的排序方法排个序
        Collections.sort(categoryList, (Category o1, Category o2) ->   o1.getCid()- o2.getCid() );
        return categoryList;
    }
}

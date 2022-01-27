package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @title: UserDaoImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/4 下午 9:07
 */
public class UserDaoImpl implements UserDao {
    //创建一个jdbc模板JdbcTemplet,让本类成员共用
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findUserBYName(String username) {
        User user = null;      //创建一个空的user对象
        try {
            String sql = "select * from tab_user where username = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
            //因为该查询方法如果没查到就会报错这里抓一个大个儿的异常
        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into " +
                            "tab_user" +
                            "(username,password,name,birthday,sex,telephone,email,status,code) " +
                    "values" +
                            "(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                    user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),
                    user.getSex(),user.getTelephone(),user.getEmail(),
                    user.getStatus(),user.getCode());
    }

    @Override
    public User findUserByCode(String code) {
        User user = null;
        try {  //为了防止用户不存在jdbctemplet报错这里抓一下异常，不需要打印
            String sql = "select * from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        jdbcTemplate.update(sql,user.getUid());
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (Exception e) {

        }
        return user;
    }
}

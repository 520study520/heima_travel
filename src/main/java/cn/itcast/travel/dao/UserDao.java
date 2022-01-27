package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * 客户端用户信息，访问数据库接口
 */
public interface UserDao {

    /**
     * 在数据库查询该用户名是否存在
     * @param name 用户名
     * @return 存在返回null，不存在返回User对象
     */
    User findUserBYName(String name);

    /**
     * 存储用户信息
     * @param user 用户信息对象
     */
    void save(User user);

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    User findUserByCode(String code);

    /**
     * 修改用户对象的状态
     * @param user 用户对象
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User findUserByUsernameAndPassword(String username , String password);
}

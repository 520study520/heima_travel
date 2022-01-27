package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;

/**
 * @title: UserService
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/4 下午 9:04
 */
public interface UserService {

    /**
     * 用户注册检查，如果不存在就存储
     * @param user 用户对象
     * @return 注册成功返回true，注册失败返回false
     */
    boolean regist(User user);

    /**
     * 邮箱激活方法
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 登录方法
     * @param user 用户对象
     * @return
     */
    User login(User user);

    /**
     * 分页查询我的收藏数据
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> myFavorite(int uid, int currentPage, int pageSize);
}

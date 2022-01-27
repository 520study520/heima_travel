package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.List;

/**
 * @title: UserSericeImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/4 下午 9:06
 */
public class UserSericeImpl implements UserService {
    //创建数据访问层对象
    private UserDao dao = new UserDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();
    @Override
    public boolean regist(User user) {
        //调用dao的findSUerByName方法
        User userBYName = dao.findUserBYName(user.getUsername());
        if(userBYName != null) {  //如果查询到该user对象，则不存储 并返回false
            return false;
        }

        //设置用户当前状态
        user.setStatus("N");    //默认为未激活N
        //设置用户激活码,这里直接用生成随机字符串工具类UuidUtil
        //激活码由于是利用毫秒值和计算机的一些信息随机产生的，所以理论上不会重复
        user.setCode(UuidUtil.getUuid());
        //程序走到这就证明不存在可以存储
        dao.save(user);
        //发送邮件
        //1.编写邮件内容字符串
        String email = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点我激活【黑马旅游网】</a>";
        //2.调用MailUtils工具类发送邮件方法
        MailUtils.sendMail(user.getEmail(),email,"【黑马旅游网】");
        return true;
    }

    @Override
    public boolean active(String code) {
        if(code!=null){  //首先判断激活码不能为空
            //1.调用dao的findUserByCode
            User user = dao.findUserByCode(code);
            if(user!=null){
                //2.调用updateStatus更新状态
                dao.updateStatus(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public User login(User user) {
        User userByUsernameAndPassword = null;
        if(user != null){   //非空验证
            //1.调用dao里的findUserByUsernameAndPassword方法查询用户是否存在
            userByUsernameAndPassword = dao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        }
        return userByUsernameAndPassword;
    }

    @Override
    public PageBean<Route> myFavorite(int uid, int currentPage, int pageSize) {
        //1.创建PageBean对象
        PageBean<Route> pageBean = new PageBean<>();
        //添加现有数据到pageBean中
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        //2.调用FavoriteDao中的findRidByUid方法
        List<Favorite> rids = favoriteDao.findRidByUid(uid);
        if(rids == null){  //如果线路id集合为空，则直接返回null
            return null;
        }
        int totalCount = routeDao.findTotalCount(rids);
        //计算开始位置
        int start = (currentPage - 1) * pageSize;
        //3.获得route对象数据集合
        List<Route> routes = routeDao.gainRoutes(rids,start,pageSize);
        //4.计算总页码
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        //5.向PageBean中添加数据
        pageBean.setTotalPage(totalPage);
        pageBean.setList(routes);
        pageBean.setTotalCount(totalCount);

        return pageBean;
    }
}

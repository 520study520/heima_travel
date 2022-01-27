package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @title: CategoryServiceImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/6 下午 4:34
 */
public class CategoryServiceImpl implements cn.itcast.travel.service.CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();
    @Override
    public List<Category> queryAll() {
        //1.从redis中读取数据
        //1.1创建访问redis的类Jedis的对象,通过工具类创建
        Jedis jedis = JedisUtil.getJedis();
        //使用sortedset查询
       //Set<String> categorys = jedis.zrange("category", 0, -1);
        //查询sortedset中的值和分数
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        //2.判断该集合是否为null或者长度是否为0
        List<Category> all = null;
        if(categorys == null || categorys.size() == 0){
            //redis中没数据
            System.out.println("从数据库中查询");
            //2.查询数据库
            all = dao.findAll();
            //3.存储到redis中
            //3.1遍历all
            for (int i = 0; i < all.size(); i++) {
                //存储jedis数据
                jedis.zadd("category",all.get(i).getCid(),all.get(i).getCname());
            }
            jedis.close();  //归还连接
        } else {
            //redis中有数据
            System.out.println("从redis中查询");
            all = new ArrayList<>();
            //遍历set集合将数据存储到list集合中
            for (Tuple tuple : categorys) {
                Category ct = new Category();
                ct.setCname(tuple.getElement());
                ct.setCid((int)tuple.getScore());
                all.add(ct);
            }
            jedis.close();
        }
        return all;
    }
}

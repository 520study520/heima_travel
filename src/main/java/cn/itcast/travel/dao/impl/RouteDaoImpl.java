package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @title: RouteDaoImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/8 下午 4:50
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public int findTotalCount(int cid,String rname) {
        //String sql = "select count(*) from tab_route where cid = ?";
        String sql = " select count(*) from tab_route where 1 = 1";  //普通sql模板
        StringBuilder sb = new StringBuilder(sql);   //用于动态拼接sql语句
        List params = new ArrayList();  //用于存储参数
        if(cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);  //添加 ？ 对应的参数
        }
        if(rname != null){
            sb.append(" and rname like ?");  //根据旅游名称模糊查询
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        return jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findDataByPage(int cid,int start, int pageSize,String rname) {
        //String sql = "select * from tab_route where cid= ? limit ?,?";
        String sql = "select * from tab_route where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid != 0){  //非空验证
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ?");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        System.out.println(params);
        List<Route> routeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return routeList;
    }

    @Override
    public Route gainRoute(int rid) {
        String sql = "select * from tab_route where rid = ?";
        Route route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        return route;
    }

    @Override
    public int findTotalCount(List<Favorite> rids) {
        //来一个动态SQL
        String sql = "select count(*) from tab_route where 1=1";
        StringBuilder stringBuilder = new StringBuilder(sql);  //用于动态拼接SQL
        List list = new ArrayList();  //用于存储参数值
        if (rids != null) {
            for (int i = 0; i < rids.size(); i++) {
                if(i==0){
                    stringBuilder.append(" and rid in(0");

                }
                list.add(rids.get(i).getRid());
                stringBuilder.append(",?");
            }
            stringBuilder.append(") ");
        }
        sql = stringBuilder.toString();
        return jdbcTemplate.queryForObject(sql,Integer.class,list.toArray());
    }

    @Override
    public List<Route> gainRoutes(List<Favorite> rids, int start, int pageSize) {
        //来一个动态SQL
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder stringBuilder = new StringBuilder(sql);  //用于动态拼接SQL
        List list = new ArrayList();
        if(rids != null){
            for (int i = 0; i < rids.size(); i++) {
                if(i==0){
                    stringBuilder.append(" and rid in(0");
                }
                list.add(rids.get(i).getRid());
                stringBuilder.append(",?");
            }
            stringBuilder.append(") ");
        }
        stringBuilder.append(" limit ?,?");
        list.add(start);
        list.add(pageSize);
        sql = stringBuilder.toString();
        List<Route> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), list.toArray());
        return query;
    }

    @Override
    public void updateRouteCount(List<Route> routes) {
        String sql = "update tab_route set count = ?  where rid = ?";
        for (int i = 0; i < routes.size(); i++) {
            jdbcTemplate.update(sql,routes.get(i).getCount(),routes.get(i).getRid());
        }
    }

    @Override
    public int findTotalCount(Map<String, String[]> parameterMap) {
        String sql = "select count(*) from tab_route where count > 0";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> parameter = creteSql(sb, parameterMap);
        sql = sb.toString();
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,parameter.toArray());
        return count;
    }
    private List<Object> creteSql(StringBuilder sb ,Map<String, String[]> parameterMap){
        List<Object> parameter = new ArrayList<>();  //用于存储参数
        parameterMap.forEach(new BiConsumer<String, String[]>() {
            @Override
            public void accept(String key, String[] values) {
                if("currentPage".equals(key) || "".equals(key)){ //过滤当前页码参数
                    return;
                }

                if("rname".equals(key)){  //线路名称

                    String value = values[0];

                    if(value != null && !"".equals(value)){   //参数必须部位空
                        if(!(Charset.forName("GBK").newEncoder().canEncode(value))){  //如果该字符串乱码就重新编码该字符串
                            try {
                                value = new String(value.getBytes("iso-8859-1"),"utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        sb.append(" and "+key+" like ? ");
                        if (parameter != null) {    //防止空指针
                            parameter.add("%"+value+"%");
                        }
                    }
                }
                if("priceBefore".equals(key)){  //开始金额
                    String value = values[0];
                    if(value != null && !"".equals(value)){   //参数必须部位空
                        sb.append(" and price between ? and ? ");
                        if (parameter != null) {    //防止空指针
                            parameter.add(value);
                        }
                    }
                }
                if("priceEnd".equals(key)){   //结束金额
                    String value = values[0];
                    if(value != null && !"".equals(value)){   //参数必须部位空
                        if (parameter != null) {    //防止空指针
                            parameter.add(value);
                        }
                    }
                }
            }
        });
        return parameter;
    }


    @Override
    public List<Route> findDataByPage(int start, int pageSzie, Map<String, String[]> parameterMap) {
       String sql = "select * from tab_route where count>0 ";
       StringBuilder sb = new StringBuilder(sql);
       //调用createSql动态拼接模糊查询参数
        List<Object> parameter = creteSql(sb, parameterMap);
        sb.append(" order by count desc");
        sb.append(" limit ? , ?");
        parameter.add(start);
        parameter.add(pageSzie);
        sql = sb.toString();
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),parameter.toArray());
    }

    @Override
    public List<Route> findRouteByCid(int cid) {
        List<Route> routes = null;
        try {
            String sql = "SELECT * FROM tab_route WHERE cid = ? ORDER BY COUNT DESC LIMIT 5";
            routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid);
        } catch (DataAccessException e) {

        }
        return routes;
    }

    @Override
    public List<Route> findThemeTop4() {
        String parameter = "%佛%";
        List<Route> routes = null;
        try {
            String sql = "SELECT * FROM tab_route WHERE rname like ? ORDER BY COUNT DESC LIMIT 4";
            routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), parameter);
        } catch (DataAccessException e) {

        }
        return routes;
    }

    @Override
    public List<Route> findNewestTop4() {
        List<Route> routes = null;
        try {
            String sql = "SELECT * FROM tab_route  ORDER BY rdate DESC LIMIT 4";
            routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        } catch (DataAccessException e) {

        }
        return routes;

    }

    @Override
    public List<Route> findPopularityTop4() {
        List<Route> routes = null;
        try {
            String sql = "SELECT * FROM tab_route  ORDER BY count DESC LIMIT 4";
            routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        } catch (DataAccessException e) {

        }
        return routes;
    }

    @Override
    public List<Route> findHeima_gnTop4() {
        List<Route> routes = null;
        try {
            String sql = "SELECT * FROM tab_route WHERE cid = 5 ORDER BY count DESC LIMIT 6";
            routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        } catch (DataAccessException e) {

        }
        return routes;
    }

    @Override
    public List<Route> findHeima_gwTop4() {
        List<Route> routes = null;
        try {
            String sql = "SELECT * FROM tab_route WHERE cid = 4 ORDER BY count DESC LIMIT 6";
            routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        } catch (DataAccessException e) {

        }
        return routes;
    }
}

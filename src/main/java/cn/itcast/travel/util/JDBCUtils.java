package cn.itcast.travel.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
    //数据源
    private static DataSource dataSource;

    //加载配置文件为数据源赋值
    static {
        try {
            Properties pro = new Properties();
            InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            System.out.println(inputStream);
            pro.load(inputStream);
            dataSource = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取连接方法
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    //获取数据源方法
    public static DataSource getDataSource(){
        return dataSource;
    }

    //关闭资源方法
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {}
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {}
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {}
        }
    }
    //重载关闭资源方法
    public static void close(Connection conn, Statement stmt){
       close(conn, stmt,null);
    }
}

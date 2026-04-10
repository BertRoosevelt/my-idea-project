package com.fruitDayDB.db;


import java.sql.*;

/**
 * Created by soso.
 */
public class DBUtils {
    //数据库连接地址
    public static String URL;
    //用户名
    public static String USERNAME;
    //密码
    public static String PASSWORD;
    //mysql的驱动类
    public static String DRIVER;

    private DBUtils(){}

    //使用静态块加载驱动程序
    static{
        URL = readConfig("DB_URL", "db.url", "jdbc:mysql://127.0.0.1:3306/fruitday?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8");
        USERNAME = readConfig("DB_USERNAME", "db.username", "root");
        PASSWORD = readConfig("DB_PASSWORD", "db.password", "");
        DRIVER = readConfig("DB_DRIVER", "db.driver", "com.mysql.cj.jdbc.Driver");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //定义一个获取数据库连接的方法
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取连接失败");
            return null;
        }
    }

    private static String readConfig(String envKey, String propertyKey, String defaultValue) {
        String value = System.getenv(envKey);
        if (value == null || value.trim().isEmpty()) {
            value = System.getProperty(propertyKey);
        }
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return value.trim();
    }

    /**
     * 关闭数据库连接
     * @param rs
     * @param stat
     * @param conn
     */
    public static void close(ResultSet rs,Statement stat,Connection conn){
        try {
            if(rs!=null)rs.close();
            if(stat!=null)stat.close();
            if(conn!=null)conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

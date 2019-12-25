package util;

import java.sql.*;

/**
 * 提供JDBC连接对象和释放资源
 */
public final class JdbcHelper {

    private static String url = "jdbc:mysql://localhost:3306/kcsj" +
            "?useUnicode=true&characterEncoding=utf8" + //?后面是指定编码utf8
            "&serverTimezone=Asia/Shanghai";

    //用户名
    private static String user = "root";
    //用户密码
    private static String password = "19990623";

    // 不允许创建本类对象
    private JdbcHelper() {
    }

    //注册驱动
    static {
        try {
            //注册驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("未找到驱动程序类");
        }
    }

    /**
     * @return 连接对象
     * @throws SQLException
     */
    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    //关闭、释放资源
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //关闭、释放资源
    public static void close(Statement stmt, Connection conn) {
        JdbcHelper.close(null, stmt, conn);
    }
}
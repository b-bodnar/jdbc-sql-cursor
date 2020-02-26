package cursor.jdbc;

import cursor.jdbc.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection c = DbUtil.getConnection();
            if(!c.isClosed()){
                System.out.println("Connection is created!");
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

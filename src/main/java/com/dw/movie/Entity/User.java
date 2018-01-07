package com.dw.movie.Entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.*;


public class User {
    private static String dbDriverName = "com.mysql.jdbc.Driver";
    private static String dbConn = "jdbc:mysql://127.0.0.1:3306/sys?user=root&password=";

    private String user_id;
    private String profilename;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public void getUserbyId(String Id){
        try{
            Class.forName(dbDriverName).newInstance();

            Connection conn = DriverManager.getConnection(dbConn);

            if(conn!=null) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT * FROM t_uer WHERE user_id = \'" + Id+ "\'";

                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    this.user_id = rs.getString("user_id");
                    this.profilename = rs.getString("profilename");
                }

                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}

package com.dw.movie.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.*;


public class Director {

//    private int director_id;
    private String director_name;

    private static String dbDriverName = "com.mysql.jdbc.Driver";
    private static String dbConn = "jdbc:mysql://127.0.0.1:3306/sys?user=root&password=root";

//    public int getDirector_id() {
//        return director_id;
//    }
//
//    public void setDirector_id(int director_id) {
//        this.director_id = director_id;
//    }

    public String getDirector_name() {
        return director_name;
    }

    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }

//    public void getDirectorbyId(String Id){
//        try{
//            Class.forName(dbDriverName).newInstance();
//
//            Connection conn = DriverManager.getConnection(dbConn);
//
//            if(conn!=null) {
//                Statement stmt = conn.createStatement();
//                String sql = "SELECT * FROM t_director WHERE director_id = \'" + Id+ "\'";
//
//                ResultSet rs = stmt.executeQuery(sql);
//                while(rs.next()) {
//                    this.director_id = rs.getInt("director_id");
//                    this.director_name = rs.getString("director_name");
//                }
//
//                rs.close();
//                stmt.close();
//                conn.close();
//            }
//        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e){
//            e.printStackTrace();
//        }
//    }
}

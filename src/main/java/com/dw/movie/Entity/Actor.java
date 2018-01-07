package com.dw.movie.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.*;


public class Actor {

//    private String actor_id;
    private String actor_name;
    private int star_times;
    private int act_times;

    private static String dbDriverName = "com.mysql.jdbc.Driver";
    private static String dbConn = "jdbc:mysql://127.0.0.1:3306/sys?user=root&password=root";

//    public int getActor_id() {
//        return actor_id;
//    }
//
//    public void setActor_id(int actor_id) {
//        this.actor_id = actor_id;
//    }
    public Actor(){
        actor_name="";
        star_times=0;
        act_times=0;
    }

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }

    public int getStar_times() {
        return star_times;
    }

    public void setStar_times(int star_times) {
        this.star_times = star_times;
    }

    public int getAct_times() {
        return act_times;
    }

    public void setAct_times(int act_times) {
        this.act_times = act_times;
    }

    //    public void getActorbyId(String Id){
//        try{
//            Class.forName(dbDriverName).newInstance();
//
//            Connection conn = DriverManager.getConnection(dbConn);
//
//            if(conn!=null) {
//                Statement stmt = conn.createStatement();
//                String sql = "SELECT * FROM t_actor WHERE actor_id = \'" + Id+ "\'";
//
//                ResultSet rs = stmt.executeQuery(sql);
//                while(rs.next()) {
//                    this.actor_id = rs.getInt("actor_id");
//                    this.actor_name = rs.getString("actor_name");
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

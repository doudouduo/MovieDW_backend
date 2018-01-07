package com.dw.movie.Entity;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.*;


public class Review {

    private static String dbDriverName = "com.mysql.jdbc.Driver";
    private static String dbConn = "jdbc:mysql://127.0.0.1:3306/sys?user=root&password=root";

    private int review_id;
    private String user_id;
    private String helpfulness;
    private float score;
    private String summary;
    private String text;
    private long timestamp;

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHelpfulness() {
        return helpfulness;
    }

    public void setHelpfulness(String helpfulness) {
        this.helpfulness = helpfulness;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void getReviewbyId(String Id){
        try{
            Class.forName(dbDriverName).newInstance();

            Connection conn = DriverManager.getConnection(dbConn);

            if(conn!=null) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT * FROM t_review WHERE review_id = \'" + Id+ "\'";

                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    this.review_id = rs.getInt("review_id");
                    this.user_id = rs.getString("user_id");
                    this.helpfulness=rs.getString("helpfulness");
                    this.score=rs.getFloat("score");
                    this.summary=rs.getString("summary");
                    this.text=rs.getString("text");
                    this.timestamp=rs.getLong("timestamp");
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

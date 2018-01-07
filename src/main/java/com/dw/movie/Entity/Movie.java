package com.dw.movie.Entity;

import java.sql.*;

public class Movie {

    private static String dbDriverName = "com.mysql.jdbc.Driver";
    private static String dbConn = "jdbc:mysql://127.0.0.1:3306/sys?user=root&password=root";

    private String movie_id;
    private String movie_name;
    private String genre_name;
    private String language_name;
    private String mpaa_rating;
    private String description;
    private int runtime;
    private float average_rating;
    private String studio;
    private int rank;
    private String isMainActor;

    private String director_name;
    private String actor_name;
    private String review_id;
    private int year;
    private int month;
    private int day;
    private int season;
    private int weekday;

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public String getMpaa_rating() {
        return mpaa_rating;
    }

    public void setMpaa_rating(String mpaa_rating) {
        this.mpaa_rating = mpaa_rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public float getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(float average_rating) {
        this.average_rating = average_rating;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getIsMainActor() {
        return isMainActor;
    }

    public void setIsMainActor(String isMainActor) {
        this.isMainActor = isMainActor;
    }

    public String getDirector_name() {
        return director_name;
    }

    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public void getMoviebyId(String Id){
        try{
            Class.forName(dbDriverName).newInstance();

            Connection conn = DriverManager.getConnection(dbConn);

            if(conn!=null) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT * FROM t_movie WHERE movie_id = \'" + Id+ "\'";

                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    this.movie_id = rs.getString("movie_id");
                    this.movie_name = rs.getString("movie_name");
                    this.genre_name=rs.getString("genre_name");
                    this.language_name=rs.getString("language_name");
                    this.mpaa_rating=rs.getString("mpaa_rating");
                    this.description=rs.getString("description");
                    this.runtime=rs.getInt("runtime");
                    this.average_rating=rs.getFloat("average_rating");
                    this.studio=rs.getString("studio");
                    this.rank=rs.getInt("rank");
                    this.isMainActor=rs.getString("isMainActor");

                    this.director_name=rs.getString("director_name");
                    this.actor_name=rs.getString("actor_name");
                    this.review_id=rs.getString("review_id");
                    this.year=rs.getInt("year");
                    this.month=rs.getInt("month");
                    this.day=rs.getInt("day");
                    this.season=rs.getInt("season");
                    this.weekday=rs.getInt("weekday");
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

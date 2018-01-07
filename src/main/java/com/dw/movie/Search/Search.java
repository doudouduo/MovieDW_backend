package com.dw.movie.Search;

import com.dw.movie.Entity.Actor;
import com.dw.movie.Entity.Movie;

import java.sql.*;
import java.util.ArrayList;

public class Search {

    private static String dbDriverName = "com.mysql.jdbc.Driver";
    private static String dbConn = "jdbc:mysql://127.0.0.1:3306/sys?user=root&password=123456";
    private static String dwDriverName = "com.cloudera.hive.jdbc4.HS2Driver";
    private static String dwConn = "jdbc:hive2://192.168.44.134:10000/default";

    public static Result searchByTime(String Year, String Month, String Day,String Season,String Weekday){

        ArrayList<Movie> movie = new ArrayList<Movie>();
        long dbstart = 0, dbend = 0, dwstart = 0, dwend = 0;
        int count = 0;

        // search in db
        try{
            Class.forName(dbDriverName).newInstance();

            Connection conn = DriverManager.getConnection(dbConn);

            if(conn!=null) {
                String attribute="movie_id,movie_name,mpaa_rating,description,runtime, average_rating,studio,rank,year,month,day";
                String table="t_movie";
                String where="";

                // get movies from t_movie directly
                if (Season.equals("")&&Weekday.equals("")) {
                    if (!Year.equals("")) {
                        if (where.equals("")) where = "year=?";
                        else where = where + " AND year=?";
                    }
                    if (!Month.equals("")) {
                        if (where.equals("")) where = "month=?";
                        else where = where + " AND month=?";
                    }
                    if (!Day.equals("")) {
                        if (where.equals("")) where = "day=?";
                        else where = where + " AND day=?";
                    }
                    String sql="SELECT * FROM t_movie WHERE "+where;
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    int num=0;

                    if (!Year.equals("")){
                        ++num;
                        stmt.setString(num,Year);
                    }
                    if (!Month.equals("")){
                        ++num;
                        stmt.setString(num,Month);
                    }
                    if (!Day.equals("")){
                        ++num;
                        stmt.setString(num,Day);
                    }
                    // execute the query & calculate the time
                    dbstart = System.nanoTime();
                    ResultSet rs = stmt.executeQuery();
                    dbend = System.nanoTime();

                    while(rs.next()) {
                        Movie m=new Movie();
                        m.setMovie_id(rs.getString("movie_id"));
                        m.setYear(rs.getInt("year"));
                        m.setMonth(rs.getInt("month"));
                        m.setDay(rs.getInt("day"));
                        m.setMovie_name(rs.getString("movie_name"));
                        m.setMpaa_rating(rs.getString("mpaa_rating"));
                        m.setDescription(rs.getString("description"));
                        m.setRuntime(rs.getInt("runtime"));
                        m.setAverage_rating(rs.getFloat("average_rating"));
                        m.setStudio(rs.getString("studio"));
                        m.setRank(rs.getInt("rank"));
                        movie.add(m);
                    }

                    rs.close();
                    stmt.close();
                }
                // get time from t_time then get movies from t_movie
                else {
                    if (!Year.equals("")) {
                        if (where.equals("")) where = "year=?";
                        else where = where + " AND year=?";
                    }
                    if (!Month.equals("")) {
                        if (where.equals("")) where = "month=?";
                        else where = where + " AND month=?";
                    }
                    if (!Day.equals("")) {
                        if (where.equals("")) where = "day=?";
                        else where = where + " AND day=?";
                    }
                    if (!Season.equals("")) {
                        if (where.equals("")) where = "season=?";
                        else where = where + " AND season=?";
                    }
                    if (!Weekday.equals("")) {
                        if (where.equals("")) where = "weekday=?";
                        else where = where + " AND weekday=?";
                    }
                    String sql="SELECT year,month,day FROM t_time WHERE "+where;
                    int num=0;
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    if (!Year.equals("")){
                        ++num;
                        stmt.setString(num,Year);
                    }
                    if (!Month.equals("")){
                        ++num;
                        stmt.setString(num,Month);
                    }
                    if (!Day.equals("")){
                        ++num;
                        stmt.setString(num,Day);
                    }
                    if (!Season.equals("")){
                        ++num;
                        stmt.setString(num,Season);
                    }
                    if (!Weekday.equals("")){
                        ++num;
                        stmt.setString(num,Weekday);
                    }
                    // execute the query & calculate the time
                    dbstart = System.nanoTime();
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String sql1 = "SELECT * FROM t_movie WHERE year = \'" + rs.getString("year") + "\' AND month = \'" + rs.getString("month") + "\' AND day = \'" + rs.getString("day") + "\'";
                        Statement stmt1 = conn.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(sql1);
                        while (rs1.next()) {
                            Movie m = new Movie();
                            m.setMovie_id(rs.getString("movie_id"));
                            m.setYear(rs.getInt("year"));
                            m.setMonth(rs.getInt("month"));
                            m.setDay(rs.getInt("day"));
                            m.setMovie_name(rs.getString("movie_name"));
                            m.setMpaa_rating(rs.getString("mpaa_rating"));
                            m.setDescription(rs.getString("description"));
                            m.setRuntime(rs.getInt("runtime"));
                            m.setAverage_rating(rs.getFloat("average_rating"));
                            m.setStudio(rs.getString("studio"));
                            m.setRank(rs.getInt("rank"));
                            m.setGenre_name(rs.getString("genre_name"));
                            m.setLanguage_name(rs.getString("language_name"));
                            movie.add(m);
                        }
                        rs1.close();
                        stmt1.close();
                    }
                    dbend = System.nanoTime();
                    rs.close();
                    stmt.close();
                }


                conn.close();
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //search in dw
        try {
            Class.forName(dwDriverName);
            Connection conn = DriverManager.getConnection(dwConn);

            if(conn != null){
                String attribute="movie_id,movie_name,mpaa_rating,description,runtime, average_rating,studio,rank,year,month,day";
                String table="Movie";
                String where="";

                // get movies from t_movie directly
                if (Season.equals("")&&Weekday.equals("")) {
                    if (!Year.equals("")) {
                        if (where.equals("")) where = "year=?";
                        else where = where + " AND year=?";
                    }
                    if (!Month.equals("")) {
                        if (where.equals("")) where = "month=?";
                        else where = where + " AND month=?";
                    }
                    if (!Day.equals("")) {
                        if (where.equals("")) where = "day=?";
                        else where = where + " AND day=?";
                    }
                    String sql="SELECT * FROM t_movie WHERE "+where;
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    int num=0;

                    if (!Year.equals("")){
                        ++num;
                        stmt.setString(num,Year);
                    }
                    if (!Month.equals("")){
                        ++num;
                        stmt.setString(num,Month);
                    }
                    if (!Day.equals("")){
                        ++num;
                        stmt.setString(num,Day);
                    }
                    // execute the query & calculate the time
                    dbstart = System.nanoTime();
                    ResultSet rs = stmt.executeQuery();
                    dbend = System.nanoTime();

                    rs.close();
                    stmt.close();
                }
                // get time from t_time then get movies from t_movie
                else {
                    if (!Year.equals("")) {
                        if (where.equals("")) where = "year=?";
                        else where = where + " AND year=?";
                    }
                    if (!Month.equals("")) {
                        if (where.equals("")) where = "month=?";
                        else where = where + " AND month=?";
                    }
                    if (!Day.equals("")) {
                        if (where.equals("")) where = "day=?";
                        else where = where + " AND day=?";
                    }
                    if (!Season.equals("")) {
                        if (where.equals("")) where = "season=?";
                        else where = where + " AND season=?";
                    }
                    if (!Weekday.equals("")) {
                        if (where.equals("")) where = "weekday=?";
                        else where = where + " AND weekday=?";
                    }
                    String sql="SELECT year,month,day FROM t_time WHERE "+where;
                    int num=0;
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    if (!Year.equals("")){
                        ++num;
                        stmt.setString(num,Year);
                    }
                    if (!Month.equals("")){
                        ++num;
                        stmt.setString(num,Month);
                    }
                    if (!Day.equals("")){
                        ++num;
                        stmt.setString(num,Day);
                    }
                    if (!Season.equals("")){
                        ++num;
                        stmt.setString(num,Season);
                    }
                    if (!Weekday.equals("")){
                        ++num;
                        stmt.setString(num,Weekday);
                    }
                    // execute the query & calculate the time
                    dbstart = System.nanoTime();
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String sql2 = "SELECT * FROM Movie WHERE year = \'" + rs.getString("year") + "\' AND month = \'" + rs.getString("month") + "\' AND day = \'" + rs.getString("day") + "\'";
                        Statement stmt2 = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(sql2);
                        rs2.close();
                        stmt2.close();
                    }
                    dbend = System.nanoTime();
                    rs.close();
                    stmt.close();
                }
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

        return new Result(dbend-dbstart,dwend-dwstart,count,movie);
    }

    public static Result searchByName(String MovieName) {

        ArrayList<Movie> movie = new ArrayList<Movie>();
        long dbstart = 0, dbend = 0, dwstart = 0, dwend = 0;
        int count = 0;

        // search in db
        try{
            Class.forName(dbDriverName).newInstance();

            Connection conn = DriverManager.getConnection(dbConn);

            if(conn!=null) {
                String sql = "SELECT * FROM t_movie WHERE movie_name like ? ";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,"%"+MovieName+"%");


                // execute the query & calculate the time
                dbstart = System.nanoTime();
                ResultSet rs = stmt.executeQuery();
                dbend = System.nanoTime();

                while(rs.next()) {
                    Movie m=new Movie();
                    m.setMovie_id(rs.getString("movie_id"));
                    m.setYear(rs.getInt("year"));
                    m.setMonth(rs.getInt("month"));
                    m.setDay(rs.getInt("day"));
                    m.setMovie_name(rs.getString("movie_name"));
                    m.setMpaa_rating(rs.getString("mpaa_rating"));
                    m.setDescription(rs.getString("description"));
                    m.setRuntime(rs.getInt("runtime"));
                    m.setAverage_rating(rs.getFloat("average_rating"));
                    m.setStudio(rs.getString("studio"));
                    m.setRank(rs.getInt("rank"));
                    m.setGenre_name(rs.getString("genre_name"));
                    m.setLanguage_name(rs.getString("language_name"));
                    movie.add(m);
                }

                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //search in dw
        try {
            Class.forName(dwDriverName);
            Connection conn = DriverManager.getConnection(dwConn);

            if(conn != null){
                String sql = "SELECT * FROM Movie WHERE movie_name like ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,"%"+MovieName+"%");

                // execute the query & calculate the time
                dwstart = System.nanoTime();
                ResultSet rs = stmt.executeQuery(sql);
                dwend = System.nanoTime();

                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

        return new Result(dbend-dbstart,dwend-dwstart,count,movie);

    }

    public static Result searchByDirector(String DirectorName) {

        ArrayList<Movie> movie = new ArrayList<Movie>();
        ArrayList<Actor> actor = new ArrayList<Actor>();
        long dbstart = 0, dbend = 0, dwstart = 0, dwend = 0;
        int count = 0;

        // search in db
        try{
            Class.forName(dbDriverName).newInstance();

            Connection conn = DriverManager.getConnection(dbConn);

            if(conn!=null) {
                // search all the directors whose name contains DirectorName
                String sql = "SELECT director_name FROM t_director WHERE director_name like ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,"%"+DirectorName+"%");

                // execute the query & calculate the time
                dbstart = System.nanoTime();
                ResultSet rs = stmt.executeQuery();

                while(rs.next()) {
                    // search movies that the director directs
                    Statement stmt1 = conn.createStatement();
                    String sql1 = "SELECT movie_id FROM tr_direct WHERE director_name = \'" + rs.getString("director_name") + "\'";
                    ResultSet rs1 = stmt1.executeQuery(sql1);
                    while (rs1.next()) {
                        Statement stmt2 = conn.createStatement();
                        String sql2="SELECT * FROM t_movie WHERE movie_id = \'" + rs1.getString("movie_id") + "\'";
                        ResultSet rs2=stmt2.executeQuery(sql2);
                        while (rs2.next()) {
                            Movie m = new Movie();
                            m.setMovie_id(rs2.getString("movie_id"));
                            m.setYear(rs2.getInt("year"));
                            m.setMonth(rs2.getInt("month"));
                            m.setDay(rs2.getInt("day"));
                            m.setMovie_name(rs2.getString("movie_name"));
                            m.setMpaa_rating(rs2.getString("mpaa_rating"));
                            m.setDescription(rs2.getString("description"));
                            m.setRuntime(rs2.getInt("runtime"));
                            m.setAverage_rating(rs2.getFloat("average_rating"));
                            m.setStudio(rs2.getString("studio"));
                            m.setRank(rs2.getInt("rank"));
                            m.setGenre_name(rs.getString("genre_name"));
                            m.setLanguage_name(rs.getString("language_name"));
                            movie.add(m);
                        }
                        rs2.close();
                    }
                    rs1.close();

                    // search actors that the director cooperates
                    sql1="SELECT * FROM tmp_cooperate WHERE director_name=\'" + rs.getString("director_name") + "\'";
                    rs1 = stmt1.executeQuery(sql1);
                    int max_cooperate_time=rs1.getInt("cooperate_times");
                    Actor a=new Actor();
                    a.setActor_name(rs1.getString("actor_name"));
                    actor.add(a);
                    while(rs1.next()) {
                        int cooperate_time = rs1.getInt("cooperate_times");
                        if (cooperate_time == max_cooperate_time) {
                            a.setActor_name(rs1.getString("actor_name"));
                            actor.add(a);
                        } else break;
                    }
                }

                dbend = System.nanoTime();
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //search in dw
        try {
            Class.forName(dwDriverName);
            Connection conn = DriverManager.getConnection(dwConn);

            if(conn != null){
                // search all the directors whose name contains DirectorName
                String sql="SELECT DISTINCT director_name FROM t_movie WHERE director_name like ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,"%"+DirectorName+"%");

                // execute the query & calculate the time
                dwstart = System.nanoTime();
                ResultSet rs=stmt.executeQuery();
                while (rs.next()) {
                    // search movies that the director directs
                    Statement stmt1=conn.createStatement();
                    String sql1="SELECT * FROM t_movie WHERE director_name=\'" + rs.getString("director_name") + "\'";
                    ResultSet rs1 = stmt1.executeQuery(sql1);
                    rs1.close();

                    // search actors that the director cooperates
                    sql1 = "SELECT actor_name, count(*) AS cooperate_times FROM t_movie WHERE director_name =\'"+rs.getString("director_name") + "\' GROUP BY actor_name";
                    stmt1 = conn.createStatement();
                    rs1 = stmt1.executeQuery(sql1);
                    rs1.close();
                }

                dwend = System.nanoTime();
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

        return new Result(dbend-dbstart,dwend-dwstart,count,movie);

    }

    public static Result searchByActor(String ActorName1,String ActorName2) {

        ArrayList<Movie> movie = new ArrayList<Movie>();
        Actor a=new Actor();
        a.setActor_name(ActorName);
        long dbstart = 0, dbend = 0, dwstart = 0, dwend = 0;
        int count = 0;

        // search in db
        try{
            Class.forName(dbDriverName).newInstance();

            Connection conn = DriverManager.getConnection(dbConn);

            if(conn!=null) {
                // search all the actors whose name contains ActorName
                String sql="SELECT actor_name FROM t_actor WHERE actor_name like ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,"%"+ActorName+"%");

                // execute the query & calculate the time
                dbstart = System.nanoTime();
                // search movies that the actor acts
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    String sql1 = "SELECT movie_id,is_star FROM tr_act WHERE actor_name = \'" + rs.getString("actor_name") + "\'";
                    Statement stmt1=conn.createStatement();
                    ResultSet rs1=stmt1.executeQuery(sql1);
                    while (rs1.next()) {
                        if (rs1.getInt("is_star") == 1) a.setStar_times(a.getStar_times() + 1);
                        else a.setAct_times(a.getAct_times() + 1);
                        String sql2 = "SELECT * FROM t_movie WHERE movie_id = \'" + rs.getString("movie_id") + "\'";
                        Statement stmt2 = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(sql2);
                        while (rs2.next()) {
                            Movie m = new Movie();
                            m.setMovie_id(rs2.getString("movie_id"));
                            m.setYear(rs2.getInt("year"));
                            m.setMonth(rs2.getInt("month"));
                            m.setDay(rs2.getInt("day"));
                            m.setMovie_name(rs2.getString("movie_name"));
                            m.setMpaa_rating(rs2.getString("mpaa_rating"));
                            m.setDescription(rs2.getString("description"));
                            m.setRuntime(rs2.getInt("runtime"));
                            m.setAverage_rating(rs2.getFloat("average_rating"));
                            m.setStudio(rs2.getString("studio"));
                            m.setRank(rs2.getInt("rank"));
                            m.setGenre_name(rs.getString("genre_name"));
                            m.setLanguage_name(rs.getString("language_name"));
                            movie.add(m);
                        }
                        rs2.close();
                        stmt2.close();
                    }
                    rs1.close();
                    stmt1.close();
                }
                dbend = System.nanoTime();
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //search in dw
        try {
            Class.forName(dwDriverName);
            Connection conn = DriverManager.getConnection(dwConn);

            if(conn != null){
                // search all the actors whose name contains ActorName
                String sql = "SELECT actor_name FROM t_movie WHERE actor_name like ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,"%"+ActorName+"%");

                // execute the query & calculate the time
                dwstart = System.nanoTime();
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    String sql1="SELECT * FROM t_movie WHERE actor_name=\'" + rs.getString("actor_name") + "\'";
                    Statement stmt1=conn.createStatement();
                    ResultSet rs1=stmt1.executeQuery(sql1);
                    while (rs1.next()){
                        Movie m = new Movie();
                        m.setMovie_id(rs1.getString("movie_id"));
                        m.setYear(rs1.getInt("year"));
                        m.setMonth(rs1.getInt("month"));
                        m.setDay(rs1.getInt("day"));
                        m.setMovie_name(rs1.getString("movie_name"));
                        m.setMpaa_rating(rs1.getString("mpaa_rating"));
                        m.setDescription(rs1.getString("description"));
                        m.setRuntime(rs1.getInt("runtime"));
                        m.setAverage_rating(rs1.getFloat("average_rating"));
                        m.setStudio(rs1.getString("studio"));
                        m.setRank(rs1.getInt("rank"));
                        movie.add(m);
                    }
                }

                dwend = System.nanoTime();

                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

        return new Result(dbend-dbstart,dwend-dwstart,count,movie);

    }

    public static Result searchByGenre(String GenreName) {

        ArrayList<Movie> movie = new ArrayList<Movie>();
        long dbstart = 0, dbend = 0, dwstart = 0, dwend = 0;
        int count = 0;

        // search in db
        try{
            Class.forName(dbDriverName).newInstance();

            Connection conn = DriverManager.getConnection(dbConn);

            if(conn!=null) {
                String sql = "SELECT movie_id FROM tr_movie_genre WHERE genre_name like ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,"%"+GenreName+"%");

                // execute the query & calculate the time
                dbstart = System.nanoTime();
                ResultSet rs = stmt.executeQuery();

                while(rs.next()) {
                    Statement stmt2 = conn.createStatement();
                    String sql2 = "SELECT * FROM t_movie WHERE movie_id = \'" + rs.getString("movie_id") + "\'";
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    while (rs2.next()) {
                        Movie m = new Movie();
                        m.setMovie_id(rs.getString("movie_id"));
                        m.setYear(rs.getInt("year"));
                        m.setMonth(rs.getInt("month"));
                        m.setDay(rs.getInt("day"));
                        m.setMovie_name(rs.getString("movie_name"));
                        m.setMpaa_rating(rs.getString("mpaa_rating"));
                        m.setDescription(rs.getString("description"));
                        m.setRuntime(rs.getInt("runtime"));
                        m.setAverage_rating(rs.getFloat("average_rating"));
                        m.setStudio(rs.getString("studio"));
                        m.setRank(rs.getInt("rank"));
                        m.setGenre_name(rs.getString("genre_name"));
                        m.setLanguage_name(rs.getString("language_name"));
                        movie.add(m);
                    }
                    rs2.close();
                }
                dbend = System.nanoTime();
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //search in dw
        try {
            Class.forName(dwDriverName);
            Connection conn = DriverManager.getConnection(dwConn);

            if(conn != null){

                String sql = "SELECT * FROM t_movie WHERE genres like ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,"%"+GenreName+"%");

                // execute the query & calculate the time
                dwstart = System.nanoTime();
                ResultSet rs = stmt.executeQuery();
                dwend = System.nanoTime();

                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

        return new Result(dbend-dbstart,dwend-dwstart,count,movie);

    }

    public static Result searchByLanguage(String LanguageName) {

        ArrayList<Movie> movie = new ArrayList<Movie>();
        long dbstart = 0, dbend = 0, dwstart = 0, dwend = 0;
        int count = 0;

        // search in db
        try{
            Class.forName(dbDriverName).newInstance();

            Connection conn = DriverManager.getConnection(dbConn);

            if(conn!=null) {
                String sql = "SELECT movie_id FROM tr_movie_language WHERE language_name like ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,"%"+LanguageName+"%");

                // execute the query & calculate the time
                dbstart = System.nanoTime();
                ResultSet rs = stmt.executeQuery();

                while(rs.next()) {
                    Statement stmt2 = conn.createStatement();
                    String sql2 = "SELECT * FROM Movie WHERE movie_id = \'" + rs.getString("movie_id") + "\'";
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    while (rs2.next()) {
                        Movie m = new Movie();
                        m.setMovie_id(rs.getString("movie_id"));
                        m.setYear(rs.getInt("year"));
                        m.setMonth(rs.getInt("month"));
                        m.setDay(rs.getInt("day"));
                        m.setMovie_name(rs.getString("movie_name"));
                        m.setMpaa_rating(rs.getString("mpaa_rating"));
                        m.setDescription(rs.getString("description"));
                        m.setRuntime(rs.getInt("runtime"));
                        m.setAverage_rating(rs.getFloat("average_rating"));
                        m.setStudio(rs.getString("studio"));
                        m.setRank(rs.getInt("rank"));
                        m.setGenre_name(rs.getString("genre_name"));
                        m.setLanguage_name(rs.getString("language_name"));                        movie.add(m);
                    }
                    rs2.close();
                }
                dbend = System.nanoTime();
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //search in dw
        try {
            Class.forName(dwDriverName);
            Connection conn = DriverManager.getConnection(dwConn);

            if(conn != null){

                String sql = "SELECT * FROM t_movie WHERE languages like ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1,"%"+LanguageName+"%");

                // execute the query & calculate the time
                dwstart = System.nanoTime();
                ResultSet rs = stmt.executeQuery();
                dwend = System.nanoTime();

                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

        return new Result(dbend-dbstart,dwend-dwstart,count,movie);

    }
}


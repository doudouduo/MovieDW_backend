package com.dw.movie.Search;

import com.dw.movie.Entity.Movie;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;

public class SearchComprehensive {

    private static String dbDriverName = "com.mysql.jdbc.Driver";
    private static String dbConn = "jdbc:mysql://127.0.0.1:3306/sys?user=root&password=root";
    private static String dwDriverName = "com.cloudera.hive.jdbc4.HS2Driver";
    private static String dwConn = "jdbc:hive2://192.168.44.134:10000/default";


    public static Result SearchComprehensive(String MovieName,String DirectorName,String ActorName,String GenreName,String LanguageName,String Year,String Month,String Day,String Season,String Week){

        ArrayList<Movie> movie = new ArrayList<Movie>();
        long dbstart = 0, dbend = 0, dwstart = 0, dwend = 0;
        int count = 0;

        // search in db
        try{
            Class.forName(dbDriverName).newInstance();

            Connection conn = DriverManager.getConnection(dbConn);

            if(conn!=null) {
                String  attribute="movie_id,movie_name,mpaa_rating,description,runtime, average_rating,studio,rank,year,month,day";
                String table="t_movie";
                String where="";
                if (!MovieName.equals(""))where="movie_name like ?";
                if (!DirectorName.equals("")){
                    table=table+" NATURAL JOIN t_director NATURAL JOIN tr_direct";
                    attribute=attribute+",director_name";
                    if (where.equals(""))where="director_name like ?";
                    else where=where+" AND director_name like ?";
                }
                if (!ActorName.equals("")){
                    table=table+" NATURAL JOIN t_actor NATURAL JOIN tr_act";
                    attribute=attribute+",actor_name,is_star";
                    if (where.equals(""))where="actor_name like ?";
                    else where=where+" AND actor_name like ?";
                }
                if (!GenreName.equals("")){
                    table=table+" NATURAL JOIN tr_movie_genre";
                    attribute=attribute+",genres";
                    if (where.equals(""))where="genres like ?";
                    else where=where+" AND genres like ?";
                }
                if (!LanguageName.equals("")){
                    table=table+" NATURAL JOIN tr_movie_language";
                    attribute=attribute+",languages";
                    if (where.equals(""))where="languages like ?";
                    else where=where+" AND languages like ?";
                }
                if (!Year.equals("")){
                    if (where.equals(""))where="year=?";
                    else where=where+" AND year=?";
                }
                if (!Month.equals("")){
                    if (where.equals(""))where="month=?";
                    else where=where+" AND month=?";
                }
                if (!Day.equals("")){
                    if (where.equals(""))where="day=?";
                    else where=where+" AND day=?";
                }
                if (!Season.equals("")){
                    attribute=attribute+",season";
                    table=table+" NATURAL JOIN t_time";
                    if (where.equals(""))where="season=?";
                    else where=where+" AND season=?";
                }
                if (!Week.equals("")){
                    attribute=attribute+",week";
                    if (!table.contains("t_time"))table=table+" NATURAL JOIN t_time";
                    if (where.equals(""))where="week=?";
                    else where=where+" AND week=?";
                }

                String sql = "SELECT "+attribute+" FROM "+table+" WHERE "+where;
                PreparedStatement stmt = conn.prepareStatement(sql);
                int num=0;

                if (!MovieName.equals("")){
                    ++num;
                    stmt.setString(num,MovieName);
                }
                if (!ActorName.equals("")){
                    ++num;
                    stmt.setString(num,ActorName);
                }
                if (!GenreName.equals("")){
                    ++num;
                    stmt.setString(num,GenreName);
                }
                if (!LanguageName.equals("")){
                    ++num;
                    stmt.setString(num,LanguageName);
                }
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
                if (!Week.equals("")){
                    ++num;
                    stmt.setString(num,Week);
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
                String  attribute="movie_id,movie_name,genres,languages,director_name,actor_name,mpaa_rating,description,runtime, average_rating,studio,rank,isMainActor,year,month,day";
                String where="";
                String table="t_movie";
                if (!MovieName.equals(""))where="movie_name like ?";
                if (!DirectorName.equals("")){
                    if (where.equals(""))where="director_name like ?";
                    else where=where+" AND director_name like ?";
                }
                if (!ActorName.equals("")){
                    if (where.equals(""))where="actor_name like ?";
                    else where=where+" AND actor_name like ?";
                }
                if (!GenreName.equals("")){
                    if (where.equals(""))where="genres like ?";
                    else where=where+" AND genres like ?";
                }
                if (!LanguageName.equals("")){
                    if (where.equals(""))where="languages like ?";
                    else where=where+" AND languages like ?";
                }
                if (!Year.equals("")){
                    if (where.equals(""))where="year=?";
                    else where=where+" AND year=?";
                }
                if (!Month.equals("")){
                    if (where.equals(""))where="month=?";
                    else where=where+" AND month=?";
                }
                if (!Day.equals("")){
                    if (where.equals(""))where="day=?";
                    else where=where+" AND day=?";
                }
                if (!Season.equals("")){
                    table=table+" NATURAL JOIN t_time";
                    attribute=attribute+",season";
                    if (where.equals(""))where="season=?";
                    else where=where+" AND season=?";
                }
                if (!Week.equals("")){
                    attribute=attribute+",week";
                    if (!table.contains("t_time"))table=table+" NATURAL JOIN t_time";
                    if (where.equals(""))where="week=?";
                    else where=where+" AND week=?";
                }

                String sql = "SELECT "+attribute+" FROM "+table+" WHERE "+where;
                PreparedStatement stmt = conn.prepareStatement(sql);
                int num=0;

                if (!MovieName.equals("")){
                    ++num;
                    stmt.setString(num,MovieName);
                }
                if (!ActorName.equals("")){
                    ++num;
                    stmt.setString(num,ActorName);
                }
                if (!GenreName.equals("")){
                    ++num;
                    stmt.setString(num,GenreName);
                }
                if (!LanguageName.equals("")){
                    ++num;
                    stmt.setString(num,LanguageName);
                }
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
                if (!Week.equals("")){
                    ++num;
                    stmt.setString(num,Week);
                }
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


}

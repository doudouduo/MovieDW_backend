package com.dw.movie.Search;


import com.dw.movie.Entity.Movie;

import java.util.ArrayList;

public class Result {
    public long DBTime;
    public long DWTime;
    public int Count;
    public ArrayList<Movie> movie;

    public Result(long ResDBTime, long ResDWTime,int ResCount, ArrayList<Movie> ResMovie) {
        this.movie = new ArrayList<Movie>(ResMovie);
        this.DBTime = ResDBTime;
        this.DWTime = ResDWTime;
        this.Count = ResCount;
    }

    public Result(Result res) {
        this(res.DBTime, res.DWTime, res.Count, res.movie);
    }
}

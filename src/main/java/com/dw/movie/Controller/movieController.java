package com.dw.movie.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class movieController {

    @RequestMapping(value = "time", method = RequestMethod.POST)
    public String time(HttpServletRequest request) {
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String season = request.getParameter("season");
        String weekday = request.getParameter("weekday");


        return "redirect:/time";
    }

    @RequestMapping(value = "name", method = RequestMethod.POST)
    public String name(HttpServletRequest request) {
        String name = request.getParameter("name");

        return "redirect:/name";
    }

    @RequestMapping(value = "director", method = RequestMethod.POST)
    public String director(HttpServletRequest request) {
        String directorName = request.getParameter("director");

        return "redirect:/director";
    }

    @RequestMapping(value = "actor", method = RequestMethod.POST)
    public String actor(HttpServletRequest request) {
        String actorName = request.getParameter("actor");

        return "redirect:/actor";
    }

    @RequestMapping(value = "type", method = RequestMethod.POST)
    public String type(HttpServletRequest request) {
        String type = request.getParameter("type");

        return "redirect:/type";
    }
}

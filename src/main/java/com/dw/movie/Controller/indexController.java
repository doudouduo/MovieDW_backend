package com.dw.movie.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class indexController {

    @RequestMapping(value = "")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "actor", method = RequestMethod.GET)
    public String actor() {
        return "actor";
    }

    @RequestMapping(value = "combine", method = RequestMethod.GET)
    public String combine() {
        return "combine";
    }

    @RequestMapping(value = "director", method = RequestMethod.GET)
    public String director() {
        return "director";
    }

    @RequestMapping(value = "name", method = RequestMethod.GET)
    public String name() {
        return "name";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search() {
        return "search";
    }

    @RequestMapping(value = "time", method = RequestMethod.GET)
    public String time() {
        return "time";
    }

    @RequestMapping(value = "type", method = RequestMethod.GET)
    public String type() {
        return "type";
    }
}

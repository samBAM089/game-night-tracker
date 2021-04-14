package de.sambam.gamenighttracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ReactRoutingController {

    @RequestMapping(value = "/**/{path:[^\\.]*}")
    public String forwardToRoutUrl() {
        return "forward:/";
    }
}




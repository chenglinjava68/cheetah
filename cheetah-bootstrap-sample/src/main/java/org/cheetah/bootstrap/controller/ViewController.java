package org.cheetah.bootstrap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by maxhuang on 2016/8/8.
 */
@Controller
@RequestMapping("/")
public class ViewController {
    @RequestMapping(method = RequestMethod.GET)
    public String getView() {
        return "index";
    }
}

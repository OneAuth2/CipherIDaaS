package com.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO:
 * create by shizhao at 2020-2-10
 *
 * @author: shizhao
 * @since: 2020-2-10 15:19
 */
@Controller
@RequestMapping(value = "/demo")
public class DemoController {
    @RequestMapping(value = "/geturi")
    public void getUrl(HttpServletRequest request, HttpServletResponse response){
       String url= request.getServerName();
       System.out.println(url);
    }
}

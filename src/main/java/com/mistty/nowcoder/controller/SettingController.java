package com.mistty.nowcoder.controller;

import com.mistty.nowcoder.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
@Controller
public class SettingController {
    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/", "/setting"}, method = {RequestMethod.GET})
    @ResponseBody
    public String setting(HttpSession httpSession) {  //使用session将跳转的message传递过来
        return "Setting Finished "+ wendaService.getMessage(1);
    }
}

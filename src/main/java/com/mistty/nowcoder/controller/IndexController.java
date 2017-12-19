package com.mistty.nowcoder.controller;

import com.mistty.nowcoder.aspect.LogAspect;
import com.mistty.nowcoder.model.User;
import com.mistty.nowcoder.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    WendaService wendaService;
    //WendaService wendaService = new WendaService();//变量初始化交给依赖注入完成，不用每次自己来反复做。

    //@GetMapping("/index")
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession httpSession) {  //使用session将跳转的message传递过来
        logger.info("VISIT HOMEPAGE ");
        return wendaService.getMessage(2) + " Hello, World. " + httpSession.getAttribute("msg");
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam("type") int type,
                          @RequestParam(value = "key", defaultValue = "zz", required = false) String key){//首页默认参数设置可用
        return String.format("Profile Page of %s / %d, t:%d k: %s", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/home"}, method = {RequestMethod.GET})
    public String home(Map<String,Object> map){
        map.put("hello","from indexController.home");
        return"/home";
    }

    @RequestMapping(path = {"/shit"}, method = {RequestMethod.GET})
    public String shit(Model model){
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        model.addAttribute("colors", colors);

        Map<String, String> map = new HashMap<>();
        for(int i = 0; i<5; i++){
            map.put(String.valueOf(i), String.valueOf(i*i));
        }
        model.addAttribute("map", map);
        model.addAttribute("user", new User("Feng"));
        return "shit";
    }

    @RequestMapping(path = {"/request"}, method = {RequestMethod.GET})
    @ResponseBody //返回只有文本，不是一个模板
    public String request(Model model, HttpServletResponse response,
                       HttpServletRequest request, HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId){
        StringBuilder sb = new StringBuilder();
        sb.append("COOKIEVALUE:" + sessionId);
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        if(request.getCookies()!= null){
            for(Cookie cookie : request.getCookies()){
                sb.append("Cookie : " + cookie.getName() + " value: " + cookie.getValue());
            }
        }
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");  //http://localhost:9090/request?type=2
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURI() + "<br>");

        response.addHeader("nowcoderId", "hello");//返回给用户的信息中
        response.addCookie(new Cookie("username", "mist"));
        try {
            response.sendRedirect("/home");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        response.getOutputStream().write(); 写入验证码流比如说

        return sb.toString();
    }

    //网页重定向
    @RequestMapping(path = {"/redirect/{code}"}, method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code,
                           HttpSession httpSession){
        httpSession.setAttribute("msg", "jump from redirect");
        //return "redirect:/";
        RedirectView red = new RedirectView("/index", true);
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY); //301跳转
        }// Failed to bind request element: org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: "null"
        //强制跳转出现如上问题
        return red;
    }

    @RequestMapping(path = {"/admin"}, method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error: " + e.getMessage();
    }




}

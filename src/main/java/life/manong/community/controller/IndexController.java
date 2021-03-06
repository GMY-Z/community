package life.manong.community.controller;

import life.manong.community.Mapper.UserMapper;
import life.manong.community.dto.PaginationDTO;
import life.manong.community.dto.QuestionDTO;
import life.manong.community.model.Question;
import life.manong.community.model.User;
import life.manong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 *
 * @authon GMY
 * @create 2020-05-22 19:09
 */
@Controller
public class IndexController {

    //注入UserMapper，为了访问数据库
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    /*
    18实现持久化登录状态获取。给了用户一个token，前端根据token检验是否为登录状态，后端服务可以重启

    当访问这个首页的时候
    循环看所有的cookie，找到token，去数据库里查，是不是有这条记录，有就把user放到session里面，
    前端根据有没有判断显示‘我’还是‘登录’
    作用：用户使用网站时。request.getSession().setAttribute("user",user);放controller里时
    服务端重启，如果每次重启都要用户重新登录，用户相当麻烦，
    给用户一个cookie。
    用户量高时，用数据库验证访问慢。
    以后用redis等机制
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size) {

        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination", pagination);

        return "index";
    }

}

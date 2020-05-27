package life.manong.community.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import life.manong.community.Mapper.UserMapper;
import life.manong.community.dto.GIthubUser;
import life.manong.community.dto.GiteeUser;
import life.manong.community.model.User;
import life.manong.community.provider.GiteeProvider;
import life.manong.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @authon GMY
 * @create 2020-05-23 13:24
 */

@Controller
public class GiteeController {
    /**
     * gitee授权中提供的 appid 和 appkey
     */
    @Value("387e33a6d460cd667d194d0a64a377f4c4ec33d44f41a90a9e124ff02939acbb")
    public String CLIENTID;
    @Value("e0617287b7b8984d2332f39f35c0463d8db2315c3356a3093144155ef5e9534c")
    public String CLIENTSECRET;
    @Value("http://localhost:8081/callback")
    public String URL;

    @Autowired
    private UserService userService;

    /**
     * 请求授权页面
     */
    @GetMapping(value = "/auth")
    public String qqAuth(HttpSession session) {
        // 用于第三方应用防止CSRF攻击
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        session.setAttribute("state", uuid);

        // Step1：获取Authorization Code
        String url = "https://gitee.com/oauth/authorize?response_type=code" +
                "&client_id=" + CLIENTID +
                "&redirect_uri=" + URLEncoder.encode(URL) +
                "&state=" + uuid +
                "&scope=user_info";

        return "redirect:" + url;

    }

    /**
     * 授权回调
     */
    @GetMapping(value = "/callback")
    public String qqCallback(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        // 得到Authorization Code
        String code = request.getParameter("code");
        // 我们放在地址中的状态码
        String state = request.getParameter("state");
        String uuid = (String) session.getAttribute("state");

        // 验证信息我们发送的状态码
        if (null != uuid) {
            // 状态码不正确，直接返回登录页面
            if (!uuid.equals(state)) {
                return "redirect:/";
            }
        }

        // Step2：通过Authorization Code获取Access Token
        String url = "https://gitee.com/oauth/token?grant_type=authorization_code" +
                "&client_id=" + CLIENTID +
                "&client_secret=" + CLIENTSECRET +
                "&code=" + code +
                "&redirect_uri=" + URL;
        JSONObject accessTokenJson = GiteeProvider.getAccessToken(url);

        // Step3: 获取用户信息
        url = "https://gitee.com/api/v5/user?access_token=" + accessTokenJson.get("access_token");
        JSONObject jsonObject = GiteeProvider.getUserInfo(url);
        /**
         * 获取到用户信息之后，就该写你自己的业务逻辑了
         */

        GiteeUser giteeUser = JSON.parseObject(jsonObject.toString(), GiteeUser.class);
        System.out.println(giteeUser);

        User user = new User();
        user.setAccountId(String.valueOf(giteeUser.getId()));
        user.setName(giteeUser.getName());
        String token = UUID.randomUUID().toString();
        user.setToken(token);//生成token放到user对象中
        user.setAvatarUrl(giteeUser.getAvatarUrl());
        //存到数据库中,还是更新用户信息
        userService.createOrUpdate(user);
        //并且把token放到cookie中
        request.getSession().setAttribute("user", user);
        System.out.println(user);
        response.addCookie(new Cookie("token", token));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {

        request.getSession().removeAttribute("user");
        Cookie token = new Cookie("token", null);
        token.setMaxAge(0);
        response.addCookie(token);
        return "redirect:/";
    }
}

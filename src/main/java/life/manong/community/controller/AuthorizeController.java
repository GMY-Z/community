package life.manong.community.controller;

import life.manong.community.Mapper.UserMapper;
import life.manong.community.dto.AccessGiteeTokenDTO;
import life.manong.community.dto.AccessTokenDTO;
import life.manong.community.dto.GIthubUser;
import life.manong.community.model.User;
import life.manong.community.provider.GiteeProvider;
import life.manong.community.provider.GithubProvider;
import life.manong.community.service.UserService;
import org.apache.ibatis.javassist.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @authon GMY
 * @create 2020-05-22 19:52
 */
@PropertySource({"classpath:application.properties"})
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.url}")
    private String redirectUrl;

    @Autowired
    private UserService userService;

   // @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
//        AccessGiteeTokenDTO accessGiteeTokenDTO = new AccessGiteeTokenDTO();
//        accessGiteeTokenDTO.setClient_id(clientId);
//        accessGiteeTokenDTO.setClient_secret(clientSecret);
//        accessGiteeTokenDTO.setCode(code);
//        accessGiteeTokenDTO.setRedirect_uri(redirectUrl);
//        accessGiteeTokenDTO.setState(state);
//        String token = giteeProvider.getAccessToken(accessGiteeTokenDTO);
//
//        return "index";

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GIthubUser gIthubUser = githubProvider.gIthubUser(accessToken);
        if (gIthubUser != null) {
            //登录成功，写cookie和session
            request.getSession().setAttribute("user", gIthubUser);
            //
            User user = new User();
            user.setAccountId(String.valueOf(gIthubUser.getId()));
            user.setName(gIthubUser.getName());
            user.setToken(UUID.randomUUID().toString());

            userService.createOrUpdate(user);
            return "redirect:/";
        }
        else {
            //登录失败，重写登录
            return "redirect:/";
        }
    }
}

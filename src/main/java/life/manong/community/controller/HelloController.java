package life.manong.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @authon GMY
 * @create 2020-05-22 10:07
 */
@Controller
public class HelloController {

    @GetMapping("hello")
    public String Hello(@RequestParam(name = "name",defaultValue="World") String name, Model model) {
        model.addAttribute("name1", name);
        return "hello";
    }

}

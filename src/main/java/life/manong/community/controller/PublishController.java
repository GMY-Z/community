package life.manong.community.controller;

import life.manong.community.Mapper.QuestionMapper;
import life.manong.community.Mapper.UserMapper;
import life.manong.community.dto.QuestionDTO;
import life.manong.community.model.Question;
import life.manong.community.model.User;
import life.manong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.events.Event;

import javax.servlet.http.HttpServletRequest;

/**
 * @authon GMY
 * @create 2020-05-23 18:56
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model){
        //通过id找到question，返回页面
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());
        return "publish";
    }




    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag" ,required = false) String tag,
                            @RequestParam(value = "id" ,required = false) Integer id,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag",tag);
        //校验前后端都做
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);//保存问题
        return "redirect:/";
    }

}

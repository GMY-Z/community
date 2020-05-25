package life.manong.community.controller;

import life.manong.community.Mapper.QuestionMapper;
import life.manong.community.Mapper.UserMapper;
import life.manong.community.dto.QuestionDTO;
import life.manong.community.model.Question;
import life.manong.community.model.User;
import life.manong.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @authon GMY
 * @create 2020-05-25 16:20
 */
@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String Question(@PathVariable(name = "id")Integer id,
                           Model model){

        QuestionDTO questionDTO = questionService.getById(id);

        if ("1".equals(id)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");

        }
        //返回给页面
        model.addAttribute("question",questionDTO);
        return "question";
    }
}

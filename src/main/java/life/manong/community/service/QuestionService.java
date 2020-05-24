package life.manong.community.service;

import life.manong.community.Mapper.QuestionMapper;
import life.manong.community.Mapper.UserMapper;
import life.manong.community.dto.PaginationDTO;
import life.manong.community.dto.QuestionDTO;
import life.manong.community.model.Question;
import life.manong.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @authon GMY
 * @create 2020-05-24 09:30
 */

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }


        Integer offset = size * (page - 1);
        List<QuestionDTO> list = new ArrayList<>();
        List<Question> questions = questionMapper.list(offset,size);
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//快速拷贝属性
            questionDTO.setUser(user);
            list.add(questionDTO);
        }
        paginationDTO.setData(list);

        return paginationDTO;
    }
}

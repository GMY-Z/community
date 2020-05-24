package life.manong.community.dto;

import life.manong.community.model.User;
import lombok.Data;

/**
 * @authon GMY
 * @create 2020-05-24 09:28
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}

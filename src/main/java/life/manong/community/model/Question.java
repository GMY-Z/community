package life.manong.community.model;

import lombok.Data;

/**
 * @authon GMY
 * @create 2020-05-23 19:56
 */
@Data
public class Question {

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
}

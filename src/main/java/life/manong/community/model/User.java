package life.manong.community.model;

import lombok.Data;

/**
 * @authon GMY
 * @create 2020-05-22 23:44
 */


@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
}

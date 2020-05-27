package life.manong.community.Mapper;

import life.manong.community.model.User;
import org.apache.ibatis.annotations.*;

import javax.websocket.server.ServerEndpoint;


/**
 * @authon GMY
 * @create 2020-05-22 23:30
 */

@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer creator);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);

}

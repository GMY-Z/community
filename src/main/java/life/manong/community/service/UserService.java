package life.manong.community.service;

import life.manong.community.Mapper.UserMapper;
import life.manong.community.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @authon GMY
 * @create 2020-05-25 22:10
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //用户不存在就创建，存在则更新用户信息
    public void createOrUpdate(User user) {
        User user1 = userMapper.findByAccountId(user.getAccountId());
        if (user1 == null) {
            user1.setName(user.getName());
            user1.setToken(user.getToken());
            user1.setAccountId(user.getAccountId());
            user1.setGmtCreate(System.currentTimeMillis());
            user1.setGmtModified(user.getGmtCreate());
            user1.setAvatarUrl(user.getAvatarUrl());
            userMapper.insert(user1);
            System.out.println(user1);
        } else {
            user1.setGmtModified(System.currentTimeMillis());
            user1.setToken(user.getToken());
            user1.setName(user.getName());
            userMapper.update(user1);
            System.out.println(user1);
        }
    }
}

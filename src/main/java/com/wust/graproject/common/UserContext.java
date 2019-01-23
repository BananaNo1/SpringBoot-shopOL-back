package com.wust.graproject.common;

import com.wust.graproject.entity.User;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserContext
 * @Description TODO
 * @Author leis
 * @Date 2019/1/23 17:03
 * @Version 1.0
 **/
@Component
public class UserContext {

    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }

}

package com.yxf.linkcommunity.interception;

import com.yxf.linkcommunity.mapper.UserMapper;
import com.yxf.linkcommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserSignInterceptor implements HandlerInterceptor {

    @Autowired(required = false)
    private UserMapper UserMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        for (Cookie target : cookies) {
            if (target.getName().equals("token")) {
                String token = target.getValue();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andTokenEqualTo(token);

                List<User> users = UserMapper.selectByExample(userExample);
                if (users.size()!=0) {
                    request.getSession().setAttribute("githubUser", users.get(0));
                    break;
                }
                }

            }

        return true;
    }
}

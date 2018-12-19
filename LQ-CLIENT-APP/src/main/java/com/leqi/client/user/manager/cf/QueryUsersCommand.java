/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.user.manager.cf;

import com.leqi.client.user.manager.uf.UserManagerModel;
import com.leqi.client.word.shortword.uf.ShortWordModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.cf.PageCommand;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.user.User;

import javax.annotation.Resource;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("QueryUsersCommand")
public class QueryUsersCommand extends PageCommand {

    @Resource(name = "UserManagerModel")
    private UserManagerModel userManagerModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        String text = (String) param.get(DATA);

        MultiValueMap<String, String> parameter = this.getPageMultiValueMap();

        parameter.add("userName", text);
        parameter.add("password", Consistent.PASSWORD);


        User[] users = this.restClient.get("/user/findByUserName", parameter, User[].class);

        this.putParameters(DATA, users);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {

        User[] users = (User[]) this.getParameters(DATA);


        this.userManagerModel.getUserlist().setAll(users);

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.cf;

import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.fw.cf.QueryCommand;
import com.leqienglish.client.util.alert.AlertUtil;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Content;

/**
 * 根据catalogId查询文章列表
 *
 * @author zhuqing
 */
@Lazy
@Component("DeleteArticlesCommand")
public class DeleteArticlesCommand extends QueryCommand {

    @Resource(name = "ArticleModel")
    private ArticleModel articleModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        Content content = (Content) param.get(DATA);

        parameter.add("id", content.getId());

        this.restClient.delete("/english/content/delete", null,parameter, String.class);
        this.putParameters(DATA, DATA);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Object data = this.getParameters(DATA);
        if (data != null) {
            AlertUtil.showInformation(AlertUtil.DELETE_SUCCESS);
            articleModel.getArticles().remove(param.get(DATA));
            return;
        }

    }

}

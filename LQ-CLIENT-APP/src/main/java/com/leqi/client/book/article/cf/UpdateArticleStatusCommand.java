/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.cf;

import com.google.common.base.Objects;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.common.cf.updatestatus.UpdateStatusByIdCommand;
import static com.leqienglish.client.fw.cf.Command.DATA;
import static com.leqienglish.client.fw.cf.Command.ID;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Content;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("UpdateArticleStatusCommand")
public class UpdateArticleStatusCommand extends UpdateStatusByIdCommand {

    @Resource(name = "ArticleModel")
    protected ArticleModel articleModel;

    @Override
    protected String getUpdatePath() {
        return "english/content/updateStatusById";
    }

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        String id = (String) param.get(ID);
        int status = (int) param.get(DATA);

        this.updateStatus(id, status + "");
        Content book = articleModel.getArticles().stream().filter(c -> Objects.equal(c.getId(), id)).findFirst().orElse(null);
        book.setStatus(status);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Content[] cs = articleModel.getArticles().toArray(new Content[0]);
        articleModel.getArticles().clear();
        this.articleModel.getArticles().setAll(cs);
    }

}

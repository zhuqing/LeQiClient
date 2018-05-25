/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.content.list.cf;

import com.google.common.base.Objects;
import com.leqienglish.client.control.button.LQButton;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Callback;
import xyz.tobebetter.entity.english.Content;

/**
 *
 * @author zhuqing
 */
public class ShowLunchButtonCallback implements Callback<List<LQButton>, List<LQButton>> {

    private final static String LUNCH = "lunch";

    @Override
    public List<LQButton> call(List<LQButton> param) {
        List<LQButton> showButtons = new ArrayList<>(1);
        for (LQButton button : param) {
            Content content = (Content) button.getUserData();
            if (content.getStatus() == -1 && Objects.equal(button.getId(), LUNCH)) {
                showButtons.add(button);
            } else {
                showButtons.add(button);
            }
        }
        return showButtons;
    }

}

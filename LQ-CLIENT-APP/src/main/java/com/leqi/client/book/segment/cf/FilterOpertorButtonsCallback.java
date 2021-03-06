/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.cf;

import com.leqienglish.client.control.button.LQButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javafx.util.Callback;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.Entity;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;

/**
 *
 * @author zhuqing
 */
public class FilterOpertorButtonsCallback implements Callback<List<LQButton>, List<LQButton>> {

    private final static String LUNCH = "lunch";

    private final static String CANCEL_LUNCH = "cancelLunch";

    @Override
    public List<LQButton> call(List<LQButton> lqButtons) {
        if (lqButtons == null || lqButtons.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<LQButton> hasFiltered = new ArrayList<>(1);

        Entity segment = (Entity) lqButtons.get(0).getUserData();
        String status = Objects.equals(segment.getStatus(), Consistent.HAS_LAUNCHED) ? "cancelLunch" : "lunch";

        for (LQButton lqButton : lqButtons) {
            if (!Objects.equals(LUNCH, lqButton.getId()) && !Objects.equals(CANCEL_LUNCH, lqButton.getId())) {
                hasFiltered.add(lqButton);
                continue;
            }
            if (Objects.equals(status, lqButton.getId())) {
                hasFiltered.add(lqButton);
            }

        }

        return hasFiltered;
    }

}

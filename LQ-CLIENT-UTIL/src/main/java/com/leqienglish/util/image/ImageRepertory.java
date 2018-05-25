/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.image;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

/**
 *
 * @author duyi
 */
public class ImageRepertory {

    private static Map<String, Image> imageMap = new HashMap<>();

    public static Image getImage(String url) {
        if (imageMap.containsKey(url)) {
            return imageMap.get(url);
        }
        Image image = new Image(url);
        imageMap.put(url, image);
        return image;
    }
}

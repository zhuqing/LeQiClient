/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.sf;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("HTTPService")
public class HTTPService {
    private String http  = "http://localhost:8080/";
    
    public void put(String path ){
        
    }
}

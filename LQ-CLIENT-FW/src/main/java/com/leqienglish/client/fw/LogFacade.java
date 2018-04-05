/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duyi
 */
public class LogFacade {

    private Logger logger;

    public LogFacade() {
    }

    public Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(getClass().getName());
        }
        return logger;
    }

}

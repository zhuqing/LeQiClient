/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.message;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 事务记录
 *
 * @author duyi
 */
@JsonInclude(Include.NON_NULL)
public class MessageEvent extends LQEvent {

}

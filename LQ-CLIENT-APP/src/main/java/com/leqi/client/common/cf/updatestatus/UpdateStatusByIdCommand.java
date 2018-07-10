/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.common.cf.updatestatus;

import com.leqienglish.client.fw.cf.Command;
import java.util.Map;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public abstract class UpdateStatusByIdCommand extends Command {

   

    protected abstract String getUpdatePath();

    protected void updateStatus(String id, String status) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("id", id);
        parameter.add("status", status);

        this.restClient.put(getUpdatePath(), null, parameter, null);
    }

}

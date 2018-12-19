/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.cf;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.Consistent;

/**
 *
 */
public abstract class PageCommand extends Command {

    private Integer page = 1;

    private Integer pageSize = 10;




    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    protected MultiValueMap<String, String> getPageMultiValueMap(){
        MultiValueMap<String, String> parameter = super.getPageMultiValueMap();
        parameter.add("page",page+"");
        parameter.add("pageSize",pageSize+"");

        return parameter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}

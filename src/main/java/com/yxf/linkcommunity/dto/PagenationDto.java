package com.yxf.linkcommunity.dto;

import lombok.Data;

import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class PagenationDto {
    private List<QuestionDto> questionDtoList;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirst;
    private boolean showLast;
    private Integer totalPage;

    private Integer page;
    private List<Integer> pagesOnShowing=new ArrayList<>();

    public void pageNation(Integer totalCount, Integer page, Integer size) {
        this.page=page;
        this.totalPage = (totalCount % size == 0) ? (totalCount / size) : (totalCount / size + 1);
        if (page < 1 || page > totalPage) {
            this.page = 1;
        }
        if (page != 1) {
            this.showPrevious = true;
        } else {
            this.showPrevious = false;
        }

        if (page != totalPage) {
            this.showNext = true;
        } else {
            this.showNext = false;
        }

        if (page > 3) {
            this.showFirst = true;
        }
        else{
            this.showFirst=false;
        }

        if (page < totalPage - 2) {
            this.showLast = true;
        }else{
            this.showLast=false;
        }
        pagesOnShowing.add(page);
        for(int i=1;i<=2;i++){
            if(page-i>0){
                pagesOnShowing.add(0,page-i);
            }
            if (page + i <= totalPage) {
                pagesOnShowing.add(page+i);
            }
        }

    }
}

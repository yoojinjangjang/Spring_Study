package com.example.restaurant.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchImageReq {

    private String query = "";
    private int display = 10;
    private int start = 1;
    private String sort = "sim";
    private String filter = "all";

    public MultiValueMap<String,String> toMultiValueMap(){
        var map = new LinkedMultiValueMap<String ,String>();
        map.set("query",query);
        map.set("display", String.valueOf(display));
        map.set("start", String.valueOf(start));
        map.set("sort",sort);
        map.set("filter",filter);

        return map;

    }
}

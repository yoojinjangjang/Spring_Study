package com.example.restaurant.naver.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchLocalReq {

    // local api 요청시 요청해야하는 값들
    private String query = "";
    private int display = 1;
    private int start = 1;
    private String sort = "random";

    public MultiValueMap<String,String> toMultiValueMap(){ //MultivalueMap객체 형태로 반환하며, key와 value값으로 해당 속성을 설정하여 보내준다.
        var map = new LinkedMultiValueMap<String, String>();
        map.add("query", query);
        map.add("display", String.valueOf(display));
        map.add("start", String.valueOf(start));
        map.add("sort", sort);

        return map;
    }
}

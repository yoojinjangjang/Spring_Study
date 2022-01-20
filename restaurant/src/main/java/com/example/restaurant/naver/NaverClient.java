package com.example.restaurant.naver;

import com.example.restaurant.naver.dto.SearchImageReq;
import com.example.restaurant.naver.dto.SearchImageRes;
import com.example.restaurant.naver.dto.SearchLocalReq;
import com.example.restaurant.naver.dto.SearchLocalRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NaverClient {

    //지정한 yaml값을 가져오기, lombok이 아닌 spring 의 어노테이션 --> 해당 yaml값을 가져온다.
    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.url.search.local}")
    private String naverLocalSearchUrl;

    @Value("${naver.url.search.image}")
    private String naverImageSearchUrl;


    public SearchLocalRes searchLocal(SearchLocalReq searchLocalReq) { //해당 객체 searchReq를 네이버 검색api로 요청을 보내는 메서드 ( 원하는 값을 검색하게 해주는 메서드 임 - 네이버 오픈api를 거쳐서 결과가져옴)
        //uri 설정 ( 요청하는 주소 생성 )
        URI uri= UriComponentsBuilder.fromUriString(naverLocalSearchUrl)
                .queryParams(searchLocalReq.toMultiValueMap()) //한번에 쿼리 파라미터를 넘겨주기 위하여 MultiValueMap객체를 사용한다.
                .build()
                .encode()
                .toUri();

        //헤더객체를 생성하여 헤더를 설정해준다.
        var headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        //request 객체로 httpEntity 객체 사용
        var httpEntity = new HttpEntity<>(headers);
        //response 객체 타입 지정
        var responseType = new ParameterizedTypeReference<SearchLocalRes>(){};

        //해당 uri로 실제 요청을 하는 부분 [ uri, 메소드방식, request 객체, response 객체타입 ] 을 매개변수로 받는 exchane 메소드 사용
        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );

        return responseEntity.getBody(); //리턴은 해당 response 객체의 body를 반환
    }

    public SearchImageRes searchImage(SearchImageReq searchImageReq){

        // 1. URI 만들기
        URI uri = UriComponentsBuilder.fromUriString(naverImageSearchUrl)
                .queryParams(searchImageReq.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        //2. header 만들기
        var headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3. request 객체 만들기
        var httpEntity = new HttpEntity<>(headers);

        // 4. response 타입 지정하기
        var responseType = new ParameterizedTypeReference<SearchImageRes>(){};

        // 5. restTemplate 요청
        var result = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );

        return result.getBody();
    }


}

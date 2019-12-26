package org.jarvis.kk.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * AdminController
 */
@CrossOrigin
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @PostMapping("/kakaoDelete")
    public void kakaoDelete(@RequestBody Integer kakaoId){
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String adminKey = "KakaoAK f7d9ce78880e668870ce0a18fa35d39c";
        headers.set("Authorization", adminKey);
        log.info(kakaoId+"================");
        String response = template.postForObject("https://kapi.kakao.com/v1/user/unlink", new HttpEntity<String>("target_id_type=user_id&target_id="+kakaoId, headers), String.class);
        log.info("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        log.info(response);
    }
    
}
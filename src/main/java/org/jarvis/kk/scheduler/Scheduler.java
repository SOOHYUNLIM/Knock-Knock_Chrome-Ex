package org.jarvis.kk.scheduler;

import org.springframework.stereotype.Component;

/**
 * Scheduler
 */
@Component
public class Scheduler {

    // @Setter(onMethod_ = { @Autowired })
    // private RestTemplate template;

    // @Scheduled(cron = "0 18 10,15,20 * * *")
    // private void rambdaTest() {
    //     // log.info(template.getRequestFactory().toString());
    //     HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    //     factory.setConnectTimeout(1000 * 5);
    //     factory.setReadTimeout(1000 * 60 * 5);
    //     RestTemplate template = new RestTemplate(factory);
    //     template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    //     template.getForObject("https://09wnfs0mt0.execute-api.ap-northeast-2.amazonaws.com/default/CommunityCrawling",
    //             String.class);
    //     FirebaseUtil.pushAllFcm();
    // }
}
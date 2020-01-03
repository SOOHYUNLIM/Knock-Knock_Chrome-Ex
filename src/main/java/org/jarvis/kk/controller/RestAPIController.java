package org.jarvis.kk.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.jarvis.kk.domain.ClickHistory;
import org.jarvis.kk.domain.CommunityCrawling;
import org.jarvis.kk.domain.ExecuteHistory;
import org.jarvis.kk.domain.Member;
import org.jarvis.kk.domain.Pick;
import org.jarvis.kk.domain.Token;
import org.jarvis.kk.dto.Category;
import org.jarvis.kk.dto.SessionMember;
import org.jarvis.kk.repositories.CategoryDTORepository;
import org.jarvis.kk.repositories.ClickHistoryRepository;
import org.jarvis.kk.repositories.CommunityCrawlingRepository;
import org.jarvis.kk.repositories.ExecuteHistoryRepository;
import org.jarvis.kk.repositories.MarketRepository;
import org.jarvis.kk.repositories.MemberRepository;
import org.jarvis.kk.repositories.PickRepository;
import org.jarvis.kk.repositories.TokenRepository;
import org.jarvis.kk.service.FCMService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RestController
 */
@CrossOrigin
@RestController
@RequestMapping("/kk")
@RequiredArgsConstructor
@Slf4j
public class RestAPIController {

    private final MemberRepository memberRepository;

    private final TokenRepository tokenRepository;

    private final CommunityCrawlingRepository communityCrawlingRepository;

    private final ClickHistoryRepository clickHistoryRepository;

    private final ExecuteHistoryRepository executeHistoryRepository;

    private final PickRepository pickRepository;

    private final CategoryDTORepository categoryDTORepository;
    private final MarketRepository marketRepository;

    private final FCMService fcmService;

    private final HttpSession session;

    private List<Category> categories;

    private Set<String> markets;

    @PostConstruct
    public void init() {
        this.categories = categoryDTORepository.findAll();
        this.markets = marketRepository.findAll().stream().map(market -> market.getUrlTitle())
                .collect(Collectors.toSet());
    }

    private Map<String, String> callCheckingLambda(String url) {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        Map<String, String> response = template.getForObject(
                "https://773hnhyh4j.execute-api.ap-northeast-2.amazonaws.com/Knock-Knock/check/" + url,
                LinkedHashMap.class);

        return response;
    }

    @DeleteMapping("/dropPick/{pno}")
    public Integer deletePick(@PathVariable Integer pno) {
        pickRepository.save(pickRepository.getOne(pno).updateReceipt(false));
        return HttpStatus.NO_CONTENT.value();
    }

    @GetMapping("/pickList")
    public ResponseEntity<List<Pick>> pickList() {
        SessionMember member = (SessionMember) session.getAttribute("member");
        List<Pick> pickList = pickRepository.getPickList(memberRepository.getOne(member.getMid()));
        pickList = pickList.stream()
                .map(pick -> Pick.builder().pno(pick.getPno()).product(
                        pick.getLowPrices().size() == 0 ? pick.getProduct() : pick.getLowPrices().get(0).getProduct())
                        .build())
                .collect(Collectors.toList());

        return new ResponseEntity<>(pickList, HttpStatus.OK);
    }

    @PostMapping("/pick")
    public Integer pick(@RequestBody Pick pick) {
        SessionMember member = (SessionMember) session.getAttribute("member");
        pick.setMember(memberRepository.getOne(member.getMid()));
        Integer result = HttpStatus.RESET_CONTENT.value();
        if (!pickRepository.duplicationCheck(pick).isPresent()) {
            pickRepository.save(pick);
            result = HttpStatus.CREATED.value();
        }
        return result;
    }

    @GetMapping("/navershopping/{title}")
    public ResponseEntity<Object> checkNaver(@PathVariable String title) {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        Object response = template.getForObject(
                "https://773hnhyh4j.execute-api.ap-northeast-2.amazonaws.com/Knock-Knock/navershopping/" + title,
                Object.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, String>> checkUrl(@RequestParam String url) {
        String decodeUrl = null;
        String mall = "";
        try {
            decodeUrl = URLDecoder.decode(url, "UTF-8");
            mall = decodeUrl.split("\\.")[1];
        } catch (UnsupportedEncodingException | ArrayIndexOutOfBoundsException e) {
        }

        Map<String, String> result = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (markets.contains(mall)) {
            result = callCheckingLambda(url);
            if (!result.get("title").equals(HttpStatus.NO_CONTENT.getReasonPhrase()))
                status = HttpStatus.OK;
        }

        return new ResponseEntity<>(result, status);
    }

    @Transactional
    @PutMapping("/updateInterest")
    public Integer updateInterest(@RequestBody String[] interests) {
        SessionMember member = (SessionMember) session.getAttribute("member");
        Member realMember = memberRepository.getOne(member.getMid());
        memberRepository.save(realMember.setInterestList(interests));
        return HttpStatus.OK.value();
    }

    @GetMapping("/interestChecking")
    public Integer interestChecking() {
        SessionMember member = (SessionMember) session.getAttribute("member");
        return member.isExistInterest() ? HttpStatus.OK.value() : HttpStatus.MOVED_PERMANENTLY.value();
    }

    @PostMapping("/token")
    public ResponseEntity<String> registerToken(@RequestBody String token) {
        log.info(token);
        SessionMember member = (SessionMember) session.getAttribute("member");
        fcmService.addAllTopics(token);
        tokenRepository.save(Token.builder().token(token).mid(member.getMid()).build());
        executeHistoryRepository.save(new ExecuteHistory(memberRepository.getOne(member.getMid())));
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CommunityCrawling>> getList() {
        SessionMember member = (SessionMember) session.getAttribute("member");
        Member realMember = memberRepository.findByMIdToInterest(member.getMid()).get();
        List<String> totalInterest = new ArrayList<>();
        List<String> analysisData = clickHistoryRepository.groupByCategoryCount(realMember);
        realMember.getInterests().forEach(interest -> {
            String category = interest.getKeyword();
            totalInterest.add(category);
            analysisData.remove(category);
        });

        totalInterest.addAll(analysisData);

        LocalDateTime from = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        LocalDateTime to = from.plusDays(1L);
        List<CommunityCrawling> result = new ArrayList<>();
        Map<String, List<CommunityCrawling>> codeToData = new HashMap<>();
        this.categories.forEach(category -> codeToData.put(category.getCode(), new ArrayList<>()));
        Set<String> totalCode = codeToData.keySet();
        communityCrawlingRepository.findByRegdateBetweenOrderByNoDesc(from, to).forEach(data -> {
            if (data.isLastCrawling() && codeToData.values().stream().mapToInt(list -> list.size()).sum() > 20)
                return;
            codeToData.get(data.getProduct().getCategory()).add(data);
        });

        totalInterest.forEach(code -> {
            result.addAll(codeToData.get(code));
            totalCode.remove(code);
        });
        totalCode.forEach(code -> result.addAll(codeToData.get(code)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/click")
    public void pileUpClickHistory(@RequestParam("no") Integer communityCrawlingNo) {
        SessionMember member = (SessionMember) session.getAttribute("member");

        clickHistoryRepository.save(ClickHistory.builder().member(memberRepository.getOne(member.getMid()))
                .communityCrawling(communityCrawlingRepository.getOne(communityCrawlingNo)).build());
    }

    @GetMapping("/msg")
    public void pushAllFcm() {
        fcmService.pushAllFcm();
    }

    @GetMapping("/lprice")
    public void pushLprice() {
        pickRepository.getAllPickList()
                .forEach(pick -> fcmService.pushOneFcm(
                        Pick.builder().pno(pick.getPno())
                                .product(pick.getLowPrices().size() == 0 ? pick.getProduct()
                                        : pick.getLowPrices().get(0).getProduct())
                                .build(),
                        pick.getMember().getTokens()));
    }

    @GetMapping("/moveMsg/{pno}")
    public void moveMsg(@PathVariable Integer pno) {
        pickRepository.save(pickRepository.getOne(pno).updateReceipt(false));
    }
}
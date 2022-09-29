package com.example.demo.src.sms;

import com.example.demo.src.sms.model.Message;
import com.example.demo.src.sms.model.SmsRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/sms/send")
    public ResponseEntity<SmsRes> sendSms(@RequestBody Message message) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        SmsRes response = smsService.sendSms(message);
        return ResponseEntity.ok().body(response);
    }
}

package com.example.demo.src.sms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsRes {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
}

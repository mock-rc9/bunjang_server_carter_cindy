package com.example.demo.src.event.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetEventRes {
    private String eventTItle;
    private String eventContent;
    private String eventOpening;
    private String eventEnding;
    private String eventImgUrl;

}

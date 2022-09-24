package com.example.demo.src.goods.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCategoryOptionRes {

    private int categoryIdx;
    private int categoryOptionIdx;
    private String categoryOptionName;
}

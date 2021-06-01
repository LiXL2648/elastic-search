package com.li.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shopping {

    private String title;

    private String category;

    private String images;

    private BigDecimal price;
}

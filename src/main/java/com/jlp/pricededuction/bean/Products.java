package com.jlp.pricededuction.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Products implements Serializable{
    private String productId;
    private ArrayList<ColorSwatches> colorSwatches;
    private List<PriceLabel> priceLabel;
    private String nowPrice;
    private String title;
    private Object price;
}

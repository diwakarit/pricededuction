package com.jlp.pricededuction.bean;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class Products implements Serializable{

    private String productId;
    private List<ColorSwatches> colorSwatches;
    private List<PriceLabel> priceLabel;
    private String nowPrice;
    private String title;
    private String price;
}

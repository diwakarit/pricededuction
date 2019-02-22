package com.jlp.www.pricededuction.Bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

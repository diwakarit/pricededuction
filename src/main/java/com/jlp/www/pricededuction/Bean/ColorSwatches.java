package com.jlp.www.pricededuction.Bean;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class ColorSwatches implements Serializable{
    private String color;
    private String rgbcolor;
    private String skuId;
}

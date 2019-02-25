package com.jlp.pricededuction.bean;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class PriceLabel implements Serializable{
    private String showWasNow;
}

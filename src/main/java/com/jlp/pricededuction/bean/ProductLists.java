package com.jlp.pricededuction.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductLists implements Serializable{
    private List<Products> products;
}

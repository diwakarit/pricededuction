package com.jlp.www.pricededuction.Bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Products {

String[] productId;
}

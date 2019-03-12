package com.jlp.pricededuction.service;

import com.jlp.pricededuction.bean.ProductLists;
import com.jlp.pricededuction.bean.Products;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadJsonDataService {
    List<Products> getProductsList(ProductLists jsonArray, String labelType);
    ProductLists readProductData(String  url);
}

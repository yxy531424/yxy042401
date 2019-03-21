package com.ssm.service;

import com.ssm.pojo.Category;
import com.ssm.pojo.Product;
import com.ssm.vo.ResultVO;

import java.util.List;

public interface ProductService {
    ResultVO queryAllProduct(Product product, Integer page, Integer limit);

    List<Category> queryCategories1();
    List<Category> queryCategories2(Integer id);

    boolean add(Product product);
    Product queryProductByPid(Long id);
}

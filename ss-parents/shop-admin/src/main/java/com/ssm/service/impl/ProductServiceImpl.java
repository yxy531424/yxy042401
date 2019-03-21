package com.ssm.service.impl;

import com.ssm.mapper.BrandMapper;
import com.ssm.mapper.CategoryMapper;
import com.ssm.mapper.ProductMapper;
import com.ssm.mapper.ProductPictureMapper;
import com.ssm.pojo.Category;
import com.ssm.pojo.Product;
import com.ssm.pojo.ProductPicture;
import com.ssm.pojo.ProductPictureExample;
import com.ssm.service.ProductService;
import com.ssm.vo.ProductVo;
import com.ssm.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductPictureMapper productPictureMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public ResultVO queryAllProduct(Product product, Integer page, Integer limit) {
        List<ProductVo> list=productMapper.queryAllProducts(product,(page-1)*limit,limit);
        for (ProductVo p:
             list) {
            ProductPictureExample example=new ProductPictureExample();
            example.createCriteria().andProductIdEqualTo(p.getProductId());
            List<ProductPicture> pc = productPictureMapper.selectByExample(example);
            if(pc!=null&&pc.size()>0){
                p.setPicUrl(pc.get(0).getPicUrl());
            }
        }
        Long count=productMapper.queryAllProductSize(product);
        return ResultVO.success(count,list);
    }

    @Override
    public List<Category> queryCategories1() {
        List<Category> list=categoryMapper.queryCategories1(1);
        return list;
    }
    @Override
    public List<Category> queryCategories2(Integer id) {
        List<Category> list=categoryMapper.queryCategories2(id);
        return list;
    }

    @Override
    public boolean add(Product product) {
        product.setProductionDate(new Date());
        product.setAuditStatus(0);
        product.setModifiedTime(new Date());
        product.setIndate(new Date());
        product.setPublishStatus(0);

        //调用 maper存储

        return  productMapper.insertSelective(product)>0;
    }
    @Override
    public Product queryProductByPid(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }


}

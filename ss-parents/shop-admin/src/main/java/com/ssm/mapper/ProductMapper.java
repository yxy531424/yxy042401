package com.ssm.mapper;

import com.ssm.pojo.Product;
import com.ssm.pojo.ProductExample;
import com.ssm.vo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    long countByExample(ProductExample example);

    int deleteByExample(ProductExample example);

    int deleteByPrimaryKey(Long productId);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Long productId);

    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);



    long queryAllProductSize(@Param("product") Product product);

    List<ProductVo> queryAllProducts(@Param("product") Product product, @Param("begin") int i,@Param("end") Integer limit);
}
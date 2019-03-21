package com.ssm.controller;

import com.ssm.pojo.Category;
import com.ssm.pojo.Product;
import com.ssm.service.ProductService;
import com.ssm.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @RequestMapping(value="/product",method = RequestMethod.GET)
    @ResponseBody
    public Object queryAllProducts(Product product,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue="10") Integer limit){
        ResultVO vo=productService.queryAllProduct(product,page,limit);
        return vo;

    }

    @RequestMapping(value="/categorys",method = RequestMethod.GET)
    @ResponseBody
    public Object queryCategories1(){
        List<Category> list=productService.queryCategories1();
        return list;
    }
    @RequestMapping(value="/categorys/parent/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Object queryCategories2(@PathVariable Integer id){
        List<Category> list=productService.queryCategories2(id);
        return list;
    }

    @RequestMapping(value = "/product/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadProductDescImg(MultipartFile editorFile, HttpServletRequest request) throws IOException {
        Map<String,Object> map=new HashMap<>();
        //吧图片上传到 static/imgs/pdesc
        String realPath = request.getServletContext().getRealPath("static/imgs/pdesc");
        File fileDir = new File(realPath);
        if (!fileDir.isDirectory()) {
            fileDir.delete();
            fileDir.mkdirs();
        }
        if(!editorFile.isEmpty()){

            //截取源文件的后缀名
            String endName = editorFile.getOriginalFilename().substring(editorFile.getOriginalFilename().lastIndexOf('.'));

            System.out.println("endName-====================>"+endName);

            //构建文件的名字
            String fileName= UUID.randomUUID().toString().replace("-","")+endName;

            //保存
            File dest=  new File(realPath+"/"+fileName);

            editorFile.transferTo(dest);
            //返回指定格式的字符串
            map.put("errno",0);
            map.put("data",new String[]{"http://localhost:81/shop-admin/static/imgs/pdesc/"+fileName});
            return map;
        }


        return null;

    }
    @RequestMapping(value="/product",method = RequestMethod.POST)
    public  String   addProduct(Product product){

        boolean f=  productService.add(product);
        if(f){
            return "product/productlist";
        }
        return "product/productadd";

    }



}

package com.innovation.manageShop.service;

import com.innovation.manageShop.DTO.ProductDTO;
import com.innovation.manageShop.entity.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private ModelMapper mapper;

    public ProductService(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ProductDTO create (ProductDTO productDTO){

        ProductEntity productEntity= mapper.map(productDTO, ProductEntity.class);
        //ubaciti productEntity u bazu
        return mapper.map(productEntity, ProductDTO.class);
    }

    public List<ProductDTO> allProducts (){
        List<ProductEntity> allProductEntities= new ArrayList<ProductEntity>(); // =query za bazu
        return mapper.map(allProductEntities, new ArrayList<ProductDTO>().getClass());
    }

    public ProductDTO edit (ProductDTO productDTO){
        ProductEntity productEntity= mapper.map(productDTO, ProductEntity.class);
        //ubaciti ovde u bazu
        return  mapper.map(productEntity, ProductDTO.class);
    }

    public void delete (String id){
        //brisanje u bazi
    }
}

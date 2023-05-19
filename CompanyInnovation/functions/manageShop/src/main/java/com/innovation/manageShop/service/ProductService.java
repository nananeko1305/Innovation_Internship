package com.innovation.manageShop.service;

import com.innovation.manageShop.DTO.ProductDTO;
import com.innovation.manageShop.entity.ProductEntity;
import com.innovation.manageShop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private ModelMapper mapper;
    @Autowired
    private ProductRepository productRepository;

    public ProductService(ModelMapper mapper , ProductRepository productRepository) {

        this.mapper = mapper;
        this.productRepository= productRepository;
    }

    public ProductDTO create (ProductDTO productDTO){

        ProductEntity productEntity= mapper.map(productDTO, ProductEntity.class);
        productRepository.save(productEntity);
        return mapper.map(productEntity, ProductDTO.class);
    }

    public List<ProductDTO> allProducts (){
        List<ProductEntity> allProductEntities= productRepository.allProducts();
        return mapper.map(allProductEntities, new ArrayList<ProductDTO>().getClass());
    }

    public ProductDTO edit (ProductDTO productDTO){
        ProductEntity productEntity= mapper.map(productDTO, ProductEntity.class);
        productRepository.save(productEntity);
        return  mapper.map(productEntity, ProductDTO.class);
    }

    public void delete (ProductDTO productDTO){
        productRepository.delete(mapper.map(productDTO, ProductEntity.class));
    }


}

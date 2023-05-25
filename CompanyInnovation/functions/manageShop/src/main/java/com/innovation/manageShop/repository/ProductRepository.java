package com.innovation.manageShop.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.innovation.manageShop.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final DynamoDBMapper dynamoDBMapper;


    public ProductRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }


    public void save(ProductEntity productEntity){
        dynamoDBMapper.save(productEntity);
    }
    public List<ProductEntity> allProducts() {
        return dynamoDBMapper.scan(ProductEntity.class,new DynamoDBScanExpression());
    }
    public void delete(ProductEntity productEntity){
        dynamoDBMapper.delete(productEntity);
    }


}

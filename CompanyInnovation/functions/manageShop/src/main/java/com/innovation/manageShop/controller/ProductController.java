package com.innovation.manageShop.controller;

import com.innovation.manageShop.DTO.ProductDTO;
import com.innovation.manageShop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping()
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    @CrossOrigin("*")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO productDTO , BindingResult result){
        if(result.hasErrors()){
            return  new ResponseEntity<String>(result.getAllErrors().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductDTO>(productService.create(productDTO), HttpStatus.OK);

    }

    @GetMapping()
    public List<ProductDTO> getAllProducts (){
        return productService.allProducts();
    }

    @PutMapping
    public ResponseEntity<?> edit (@RequestBody @Valid ProductDTO productDTO , BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>(result.getAllErrors().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  new ResponseEntity<ProductDTO>(productService.edit(productDTO), HttpStatus.OK);

    }

    @DeleteMapping()
    public void delete ( @PathVariable  String id){
        productService.delete(id);
    }
}

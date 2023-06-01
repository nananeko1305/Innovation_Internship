package com.innovation.manageShop.controller;

import com.innovation.manageShop.DTO.ProductDTO;
import com.innovation.manageShop.config.TokenUtils;
import com.innovation.manageShop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    private final TokenUtils tokenUtils;

    public ProductController(ProductService productService, TokenUtils tokenUtils) {
        this.productService = productService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping()
    @CrossOrigin("*")
    public ResponseEntity<?> createProduct(@RequestHeader("jwttoken") String token,@RequestBody @Valid ProductDTO productDTO , BindingResult result){
        if(result.hasErrors()){
            return  new ResponseEntity<String>(result.getAllErrors().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductDTO>(productService.create(productDTO), HttpStatus.OK);

    }

    @GetMapping()
    @CrossOrigin("*")
    public ResponseEntity<?> getAllProducts () {
        return new ResponseEntity<List<ProductDTO>>(productService.allProducts(), HttpStatus.OK);
    }

    @PutMapping
    @CrossOrigin("*")
    public ResponseEntity<?> edit (@RequestHeader("jwttoken") String token, @RequestBody @Valid ProductDTO productDTO , BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>(result.getAllErrors().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }else if(tokenUtils.getRoleFromToken(tokenUtils.getJWTClaimsSet(token)).equals("Admin")){
            return  new ResponseEntity<ProductDTO>(productService.edit(productDTO), HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(result.getAllErrors().toString(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping()
    @CrossOrigin("*")
    public void delete ( @RequestBody  ProductDTO productDTO){
        productService.delete(productDTO);
    }
}

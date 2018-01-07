package com.uek.etl.repository;

import com.uek.etl.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long>{

    public Product findByProductId(Long productId);

}
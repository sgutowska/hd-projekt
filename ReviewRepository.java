package com.uek.etl.repository;


import com.uek.etl.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review,Long>{

    public List<Review> findByProductId(Long productId);


    @Transactional
    List<Review> removeByProductId(Long productId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Review r WHERE r.author = :authorName AND r.date = :date AND r.productId = :productId")
    boolean reviewExist(@Param("authorName") String authorName, @Param("date") String date,@Param("productId") Long productId);

}

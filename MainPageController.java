package com.uek.etl.controller;

import com.uek.etl.Utilities.ReviewCSVGenerator;
import com.uek.etl.model.Product;
import com.uek.etl.model.Review;
import com.uek.etl.Utilities.ReviewParser;
import com.uek.etl.repository.ProductRepository;
import com.uek.etl.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;


    @RequestMapping("/")
    public String index(Model model) throws IOException {
        return "mainPage";
    }



    @RequestMapping(value = "/etl/{productId}",method = RequestMethod.GET)
    public String scrapeReviews(@PathVariable Long productId, Model model) throws IOException {

        int added = 0;
        int omitted = 0;

        ReviewParser rp = new ReviewParser(productId);
        productRepository.save(rp.getProductInfo());
        List<Review> reviews = rp.getReviewsList();

        for (Review r : reviews) {
            if(!reviewRepository.reviewExist(r.getAuthor(),r.getDate(),r.getProductId())){
                reviewRepository.save(r);
                added++;
            } else{
                omitted++;
            }
        }

        int scraped = reviews.size();

        model.addAttribute("productId",productId);
        model.addAttribute("added",added);
        model.addAttribute("omitted",omitted);
        model.addAttribute("scraped",scraped);

        Product p = productRepository.findByProductId(productId);
        model.addAttribute("product",p);

        return "etl";

    }

    @RequestMapping("/products")
    public String productList(Model model) throws IOException {
        List<Product> products = new ArrayList<Product>();
        productRepository.findAll().forEach(products::add);

        model.addAttribute("products",products);

        StringBuilder sb = new StringBuilder();

        return "productlist";

}

    @RequestMapping(value="/products/{productId}", method = RequestMethod.GET)
    public String productView(@PathVariable Long productId, Model model){
        int reviews_count = reviewRepository.findByProductId(productId).size();
        Product p = productRepository.findByProductId(productId);

        model.addAttribute("product",p);
        model.addAttribute("reviews_count",reviews_count);
        return "product";
    }


    @RequestMapping(value="/products/{productId}/reviews", method = RequestMethod.GET)
    public String reviewView(@PathVariable Long productId, Model model){

        Product p = productRepository.findByProductId(productId);
        List<Review> reviews = reviewRepository.findByProductId(productId);
        model.addAttribute("reviews",reviews);
        model.addAttribute("product",p);
        return "reviews";
    }

    @RequestMapping(value="/products/{productId}/reviews/exportcsv", produces = "text/csv", method = RequestMethod.GET)
    @ResponseBody
    public String exportToCsv(@PathVariable Long productId, Model model) throws IOException {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        ReviewCSVGenerator rcg = new ReviewCSVGenerator(reviews);

        return rcg.listToCSVString();

    }

    @RequestMapping(value="/products/{productId}/deleteReviews", method = RequestMethod.GET)
    public String deleteReviews(@PathVariable Long productId, Model model){

       int deleted =  reviewRepository.findByProductId(productId).size();
        Product p = productRepository.findByProductId(productId);
       reviewRepository.removeByProductId(productId);

       model.addAttribute("product",p);
       model.addAttribute("deleted",deleted);
       model.addAttribute("productId",productId);
       return "deleted";
    }

}

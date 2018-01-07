package com.uek.etl.Utilities;

import com.uek.etl.model.Product;
import com.uek.etl.model.Review;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.uek.etl.Utilities.EmojiRemover.clearString;

public class ReviewParser {

    public static final String NEXT_REVIEWS_BUTTON_CSS = "li[class='page-arrow arrow-next'] a";

    public static final String MODEL_CSS = "nav[class='breadcrumbs'] strong";
    public static final String CATEGORY_CSS = "span[class='breadcrumb']:last-of-type";
    public static final String MANUFACTURER_CSS = "meta[property='og:brand']";

    public static final String PROS_CSS = ".pros-cell";
    public static final String CONS_CSS = ".cons-cell";
    public static final String DESCRIPTION_CSS = ".product-review-body";
    public static final String STAR_RATING_CSS = ".review-score-count";
    public static final String AUTHOR_CSS = ".reviewer-name-line";
    public static final String TIME_CSS = "time";
    public static final String RECOMMEND_CSS = ".product-recommended";
    public static final String VOTE_YES_CSS = "button[class*='vote-yes']";
    public static final String VOTE_NO_CSS = "button[class*='vote-no']";

    private Long productId;

    public ReviewParser(Long productId) {
        this.productId = productId;
    }


    public Product getProductInfo() throws IOException {
        Document doc = Jsoup.connect("https://www.ceneo.pl/"+this.productId+"#tab=reviews").get();

        /**
         * Parsing and creating product
         */

        String model_before = doc.select(MODEL_CSS).text();

        String model = model_before.substring(model_before.indexOf(" "));
        String manufacturer = doc.select(MANUFACTURER_CSS).attr("content");
        String category = doc.select(CATEGORY_CSS).text();

       return new Product(this.productId,category,manufacturer,model,"");
    }

    public List<Review> getReviewsList() throws IOException {

        Document doc = Jsoup.connect("https://www.ceneo.pl/"+this.productId+"#tab=reviews").get();

        /**
         * Parsing reviews for previously created Product
         */
        int index = 0;
        List<Review> reviewsList = new ArrayList<Review>();


        while (true) {

            Elements reviews = doc.select("li[class='review-box js_product-review']");

            for (Element e : reviews) {

                String pros = clearString(trimConsAndPros(e.select(PROS_CSS).text()));
                String cons = clearString(trimConsAndPros(e.select(CONS_CSS).text()));
                String description = clearString(e.select(DESCRIPTION_CSS).text());
                double starRating = Double.parseDouble(e.select(STAR_RATING_CSS).text().split("/")[0].replace(',','.'));
                String author = clearString(e.select(AUTHOR_CSS).text());
                String time = clearString(e.select(TIME_CSS).attr(("datetime")));
                String recommend = clearString(e.select(RECOMMEND_CSS).text());
                int voteYes = Integer.parseInt(e.select(VOTE_YES_CSS).text());
                int voteNo = Integer.parseInt(e.select(VOTE_NO_CSS).text());

                reviewsList.add(
                        new Review(
                                productId,
                                cons,
                                pros,
                                description,
                                starRating,
                                author,
                                time,
                                recommend,
                                voteYes,
                                voteNo
                        )
                );

                index++;
            }

            System.out.println("SCRAPED REVIEWS: "+index);

            if(doc.select(NEXT_REVIEWS_BUTTON_CSS).text().equals("Następna")){
                doc = Jsoup.connect("https://www.ceneo.pl" + doc.select(NEXT_REVIEWS_BUTTON_CSS).attr("href")).get();
            } else {
                break;
            }
        }

        return reviewsList;
    }


    private String trimConsAndPros(String text){
        if(text.startsWith("Zalety ")){
            text = text.replaceFirst(Pattern.quote("Zalety "),"");
        }
        if(text.startsWith("Wady")){
           text = text.replaceFirst(Pattern.quote("Wady"),"");
        }
        return text;
    }

}

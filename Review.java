package com.uek.etl.model;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column
    private Long productId;

    @Column
    @Lob
    private String cons;

    @Column
    @Lob
    private String pros;

    @Column
    @Lob
    private String description;

    @Column
    private double starRating;

    @Column
    private String author;

    @Column
    private String date;

    @Column
    private String isRecommended;

    @Column
    private int voteYes;

    @Column
    private int voteNo;

    public Review(Long productId, String cons, String pros, String description, double starRating, String author, String date, String isRecommended, int voteYes, int voteNo) {
        this.productId = productId;
        this.cons = cons;
        this.pros = pros;
        this.description = description;
        this.starRating = starRating;
        this.author = author;
        this.date = date;
        this.isRecommended = isRecommended;
        this.voteYes = voteYes;
        this.voteNo = voteNo;
    }

    public Review() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStarRating() {
        return starRating;
    }

    public void setStarRating(double starRating) {
        this.starRating = starRating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(String isRecommended) {
        this.isRecommended = isRecommended;
    }

    public int getVoteYes() {
        return voteYes;
    }

    public void setVoteYes(int voteYes) {
        this.voteYes = voteYes;
    }

    public int getVoteNo() {
        return voteNo;
    }

    public void setVoteNo(int voteNo) {
        this.voteNo = voteNo;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
    @Override
    public String toString() {
        return "Review{" +
                "cons='" + cons + '\'' +
                ", pros='" + pros + '\'' +
                ", description='" + description + '\'' +
                ", starRating=" + starRating +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", isRecommended=" + isRecommended +
                ", voteYes=" + voteYes +
                ", voteNo=" + voteNo +
                '}';
    }


}

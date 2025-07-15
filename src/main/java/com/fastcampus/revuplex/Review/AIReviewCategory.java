package com.fastcampus.revuplex.Review;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;


//AI리뷰 요약기능에서 교통환경, 주거환경, 학군, 주변시설로 된 한 프레임

@Entity
public class AIReviewCategory {

    @Id
    private Integer categoryId;

    private String categoryName;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    private Float categoryRating;

    private String categoryContent;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryContent() {
        return categoryContent;
    }

    public void setCategoryContent(String categoryContent) {
        this.categoryContent = categoryContent;
    }

    public Float getCategoryRating() {
        return categoryRating;
    }

    public void setCategoryRating(Float categoryRating) {
        this.categoryRating = categoryRating;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "AIReviewCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                ", categoryRating=" + categoryRating +
                ", categoryContent='" + categoryContent + '\'' +
                '}';
    }
}

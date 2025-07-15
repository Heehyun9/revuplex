package com.fastcampus.shoplex.domain.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")       //dtype = "M"
@Getter
@Setter
public class Movie extends Item {

    private String director;
    private String actor;
}

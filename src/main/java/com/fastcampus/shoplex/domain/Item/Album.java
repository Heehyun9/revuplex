package com.fastcampus.shoplex.domain.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")     //dtype = "M"
@Getter
@Setter
public class Album extends Item {

    private String artist;
    private String etc;
}

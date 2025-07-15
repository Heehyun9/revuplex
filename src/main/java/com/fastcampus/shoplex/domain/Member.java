package com.fastcampus.shoplex.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    //pk를 지정 : column (name없이 지정하면 그냥 id로 저장되므로 member_id로 저장)

    private String name;

    @Embedded
    private Address address;


    //order테이블의 member 필드(변수)에 의해 매핑되었음을 뜻함 (연관관계주인이 아님)
    //컬렉션은 필드에서 바로 초기화 하는 것이 안전하다. 1.null문제와 2.hibernate 내장컬렉션으로 자동변경
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}

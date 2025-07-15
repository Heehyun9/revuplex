package com.fastcampus.shoplex.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

//임베디드타입 --> 다른 테이블의 키가 값으로 사용가능함
//참고: 값 타입은 변경 불가능하게 설계해야 한다.
// @Setter를 제거하고, 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스를 만들자
@Embeddable
@Getter
@Setter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {            // 기본생성자
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
package com.fastcampus.shoplex.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)    //STRING,ORDINAL(숫자로들어감.DEFAULT--0,1 이렇게 되는데 중간에 다른 상태 생기면 망함)
    private DeliveryStatus status;  //READY,COMP


}

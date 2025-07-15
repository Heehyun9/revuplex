package com.fastcampus.shoplex.domain;

import com.fastcampus.shoplex.domain.Item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;   //주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;   //주문

    private int orderPrice;   //주문 가격 /= 상품가격

    private int count;   //주문 수량


    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);    //주문 생성시 재고를 주문 수량만큼 감소 --> 단독 단위테스트 필요
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel(){getItem().addStock(count);  }
        //주문 취소 시 재고 수량을 주문 수량만큼 원상복구}


    //==조회 로직==//
    // 주문상품 전체 가격 조회
    public int getTotalPrice(){return getOrderPrice() * getCount();}
}



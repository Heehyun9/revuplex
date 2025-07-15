package com.fastcampus.shoplex.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


//테이블 이름 변경 @Table / order는 관례적으로 orders로 쓴다.
@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //직접 생성 불가. 생성 메서드 사용해야가능
public class Order {

    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;


    //양방향일때는 두 테이블중 fk가지고있는쪽(가까운곳)을 주인으로 --> Orders!
    // 그럼 Orders 테이블의 member_id(fk)와 order_id(pk)를 매핑해야한다.
    // 주인인쪽은 그냥 냅두고, 주인아닌쪽에 Mappedby 붙여줌
    @ManyToOne(fetch = FetchType.LAZY)   //member테이블과 연관 -- orders가 多인 양방향 연관관계
    @JoinColumn(name= "member_id")  //join컬럼
    private Member member;


    //cascade = ALL : order를 저장하면 orderItem도 cascade로 같이 저장/삭제됨 (persist)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();



    //일대일관계에서는 fk를 order, delivery중에 더 자주 쓰는곳에 놓는다. 그럼 연관관계 주인은 Order의 delivery
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")    //fk
    private Delivery delivery;   //배송정보


    private LocalDateTime orderdate;   //주문시간. DATE 쓰면 ANNOTATION매핑해야해서 사용


    @Enumerated(EnumType.STRING)
    private OrderStatus status;  //주문상태 [ORDER,CANCEL] ENUM

    //==연관관계 편의 메서드==// --> 양방향에만 해당 , 위치는 핵심적으로 control하는 쪽에 설정
    //로직상 주문할때 member, orderitem, delivery 도 다 셋팅
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);

        /*원래는
        Member member = new Member();
        Order order = new Order();

        member.getOrders().add(order);
        order.setMember(member); 로 해야함
         */
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }



    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderdate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//

    //1. 주문 취소
    /*1.주문 취소 불가능 체크 2. 주문 취소 3. 주문 취소시 재고 변경*/
     public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);   //주문 취소시 상품 재고 변경해야함
         for (OrderItem orderItem : orderItems){
             orderItem.cancel();
         }
     }

     //==조회 로직==//
    /* 전체 주문 가격을 조회*/
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}

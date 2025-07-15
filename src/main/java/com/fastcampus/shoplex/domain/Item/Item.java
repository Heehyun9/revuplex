package com.fastcampus.shoplex.domain.Item;

//Item : 추상클래스  --> 상속받는 book, 등이있기때문
// 상속관계일때 전략(@Inheritence)을 부모클래스에서 잡아줘야 한다. 여기에선 싱글테이블 전략 사용
// single_table : 한테이블에 상속되는테이블까지 다 넣음
// joined : 정규화됨.
// table_per_class : 나뉘어있음
//discriminatorColumn = 구분자 (전략)  dtype =book, album, movie

import com.fastcampus.shoplex.domain.Category;
import com.fastcampus.shoplex.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();


    //==비즈니스 로직==// 데이터를 가지고 있는 쪽에 있는것이 좋다.

    //1. 재고(stock) 증가--> setter 대신 사용
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    //2. 재고 감소  --> setter 대신 사용하기
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    //재고 감소는 단독으로 단위테스트가 꼭 필요하다.


}

package com.fastcampus.shoplex.Service;

import com.fastcampus.shoplex.Repository.OrderRepository;
import com.fastcampus.shoplex.domain.*;
import com.fastcampus.shoplex.domain.Item.*;

import com.fastcampus.shoplex.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Test
    public void 상품주문() throws Exception {
        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격, 재고
        int orderCount = 2;

        //When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확 해야 한다.");
        assertEquals(10000 * 2, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(8, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);

        int orderCount = 11;

        // when & then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        }, "재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Book item = createBook("시골 JPA",10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(),"주문 취소시 상태는 CANCEL이다.");
        assertEquals(10,item.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }


}
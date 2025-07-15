package com.fastcampus.revuplex.Repository;

import com.fastcampus.revuplex.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    //검색 기능--> 후에 동적 쿼리로 구현 :queryDsl
    public List<Order> findAll(OrderSearch orderSearch){


        return em.createQuery("select o from Order o join o.member m"+
                " where o.status = :status "+
                " and m.name like :name",Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setFirstResult(1)  //1부터 시작해서 1000개 가져오는 페이징
                .setMaxResults(1000)
                .getResultList();
    }
}

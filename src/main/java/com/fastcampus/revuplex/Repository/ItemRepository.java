package com.fastcampus.revuplex.Repository;


import com.fastcampus.revuplex.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //id 처음 생성시에는 persist
    //있으면 merge로 강제 업데이트
    public void save(Item item) {
        if(item.getId() == null){
            em.persist(item);
        } else{
            em.merge(item);
        }
    }

    //단건 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    //여러개 조회 : jpql 작성필요
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}

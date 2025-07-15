package com.fastcampus.revuplex.Repository;

import com.fastcampus.revuplex.domain.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository   //spring bean 으로 자동등록 by @SpringBootApplication
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext(@Autowired로도가능)    --> @RequiredArgsConstructor때문에 안달아도됨
    private final EntityManager em;


    //영속성에 넣으면 TX에 commit되는 시점에 db에 insert
    public void save(Member member){em.persist(member);}


    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }  //jpql은 sql(테이블기준) 과 다르게 멤버(객체) 기준

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name ", Member.class)
                .setParameter("name",name)
                .getResultList();
    }


}

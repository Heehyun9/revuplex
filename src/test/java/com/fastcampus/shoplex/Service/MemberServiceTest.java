package com.fastcampus.shoplex.Service;

import com.fastcampus.shoplex.Repository.MemberRepository;
import com.fastcampus.shoplex.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;



@SpringBootTest
@Transactional   //기본적으로 테스트케이스에서는 롤백 실행(db에있는걸 버림 이유 : 테스트는 반복됨. Db에 남으면 안됨 )함 --> insert 쿼리 안보임
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test  //given : 이렇게 주어졌을때 when:이렇게 실행하면 then: 결과가 이렇게 나온다
//    @Rollback(false)  이걸 넣으면 db에 바로들어감
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim2");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();  //영속성 컨텍스트에 있는 변경/등록을 DB에 반영 --> insert 문 볼 수 있다.


        assertEquals(member,memberRepository.findOne(savedId));

        em.clear();
    }


    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("kim3");
        Member member2 = new Member();
        member2.setName("kim3");
        //When
        memberService.join(member1);
        //Then
        //IllegalStateException 예외가 발생하지 않으면 테스트 실패
        assertThrows(IllegalStateException.class, () ->
                memberService.join(member2));


    }




}
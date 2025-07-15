package com.fastcampus.revuplex.Service;


import com.fastcampus.revuplex.Repository.MemberRepository;
import com.fastcampus.revuplex.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//기본적으로는 읽기용으로 tx설정하고, 작성, 수정, 삭제인 메서드에만 따로 @Transactional붙이기 (default : readOnly=false)
@RequiredArgsConstructor   //final있는 것들만 생성자 만듦
public class MemberService {


    private final MemberRepository memberRepository;   //final : 변경 x.생성자 넣은 여부를 컴파일 시점에 체크 가능해짐



//    @Autowired
//    //생성자Injection = 테스트에 유리 : 직접 주입 가능 / 생성자로 가짜 멤버 리파지토리 주입 가능
//    //원래는 레퍼지토리 위에 주입 애너테이션 달아야 하는데 이걸로 바꾸는게 좋음
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }  --> @RequiredArgsConstructor를 쓰면 이걸 대신 만들어준다.



    //회원 가입
    @Transactional       //
    public Long join(Member member){

        validateDuplicateMember(member);   //중복회원 검증 비즈니스 로직 --> 중복회원 있으면 예외 생성
        memberRepository.save(member);
        return member.getId();
    }

    //member.getName()는 List<Member> 가 반환값이므로 반환값형식으로 담아야 한다.
    private void validateDuplicateMember(Member member) {
        List<Member> findByMembers = memberRepository.findByName(member.getName());
        //DB에 member의 name을 꼭 unique로 설정하는걸 추천(이중 방어. 동시 가입이 있을 수 있으므로)
        if(!findByMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }



//    @Autowired
//    //setterInjection = 테스트에 유리 : 직접 주입 가능 / 가짜 멤버 리파지토리 주입 가능
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }   ---> 비추천


}

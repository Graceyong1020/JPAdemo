package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //JPA의 모든 데이터 변경이나 로직들은 트랜잭션 안에서 실행되어야 한다. readOnly = true는 데이터 변경이 없는 읽기 전용 메서드에 사용한다.
@RequiredArgsConstructor //final이 붙은 필드만 생성자를 만들어준다.
public class MemberService {

    private final MemberRepository memberRepository; //final로 선언된 필드는 생성자에서 초기화를 해주어야 한다.

    //회원 가입
    @Transactional // 데이터 변경이 일어나는 메서드에는 @Transactional을 붙여준다.
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member); // if there is no duplicate member, save the member
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 한명 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}

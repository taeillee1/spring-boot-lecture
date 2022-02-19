package jpabook.jpashop.Service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("zim");

        Long saveID = memberService.join(member);

        assertEquals(member, memberRepository.findOne(saveID));
    }

    @Test
    public void 중복_회원() throws Exception{
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("ki");

        memberService.join(member1);
        memberService.join(member2);

        fail("예외가 발생해야 한다.");
    }

}
package hello.servlet.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest {

  MemberRepository memberRepository = MemberRepository.getInstance();

  @AfterEach
  void afterEach() {
    memberRepository.clearStore();
  }

  @Test
  void save() {
    Member member = new Member("hello", 20);
    memberRepository.save(member);
    Member findMember = memberRepository.findById(member.getId());
    assertThat(findMember).isEqualTo(member);
  }


  @Test
  void findAll() {
    Member member1 = new Member("member1", 20);
    Member member2 = new Member("member2", 30);

    memberRepository.save(member1);
    memberRepository.save(member2);

    List<Member> result = memberRepository.findAll();

    assertThat(result.size()).isEqualTo(2);
    assertThat(result).contains(member1, member2);
  }
}
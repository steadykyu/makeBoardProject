package steadykyu.kyumarket.member;

import steadykyu.kyumarket.entity.member.Member;

public class MemberTest {

    static Member member = new Member("abc@naver.com", "abcd","steadykyu","kyuha");
    static String ps = member.getPassword();

    public static void main(String[] args) {
        System.out.println(ps);
    }
}

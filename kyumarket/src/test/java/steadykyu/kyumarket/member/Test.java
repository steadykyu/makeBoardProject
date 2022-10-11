package steadykyu.kyumarket.member;

import steadykyu.kyumarket.entity.member.Member;
import steadykyu.kyumarket.entity.member.Role;
import steadykyu.kyumarket.entity.member.RoleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        // 한번만 만들면 된다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 엔티티를 다루는 API 생성
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션 시작

        Role rol1 = new Role(RoleType.ROLE_NORMAL);
        Role rol2 = new Role(RoleType.ROLE_SPECIAL_SELLER);

        ArrayList<Role> roleArray = new ArrayList<>();
        roleArray.add(rol1);
        roleArray.add(rol2);
        Member member = new Member("rbgk@naver.com", "abc", "규하", "kim", roleArray);
        Member member2 = new Member("yuri@naver.com", "abc", "유리", "kim", roleArray);


        tx.commit();
        em.close();
        emf.close();
    }
}


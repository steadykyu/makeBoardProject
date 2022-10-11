package steadykyu.kyumarket.entity.user;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
public class UserMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{

            Users user1 = new Users(1L,"userA",31);
            Users user2 = new Users(2L,"userB",39);
            Users user3 = new Users(3L,"userC",17);
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);

            UsersId id2 = new UsersId(2L, "userB");
            final Users findUser = em.find(Users.class, id2);
            System.out.println("findUser = " + findUser);

            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally{
            em.close();
        }

        emf.close();
    }
}

# JPA Composite Primary Key

JPA에서 Primary Key가 단일 키 일때는 @ID annotation으로 쉽게 가능하였다.

하지만 실무에서는 복합키인 경우도 많고 JPA에서는 이 기능을 지원하고 있다.

크게 두 가지 방식이 있는데 @IdClass를 사용하는 방법과 @EmbeddedId를 사용하는 방법에 대해 알아보겠다.
# 복합키의 조건

복합키를 구성하기 위한 필수 조건이 있다.

1. 엔티티에 @EmbeddedId or @IdClass 의 annotation을 붙여야한다.

2. public 의 no-args constructor 가 있어야 한다.
    + 기본생성자!

3. Serializable interface를 implement 받아야 한다.

4. equals() 와 hashCode() method를 override해야 한다. 
    + (복합키의 일부 컬럼에 @GeneratedValue는 사용하지 못한다. 고 나와있지만 실제로 TEST 시 사용 가능한 경우도 있었다. )

> 참고

아래 코드들은 in-memory db 환경에서 다음과 같은 오류를 발생시킨다. 아직 이유를 찾지 못했다.ㅜ
```
Process 'command 'C:/Program Files/Java/jdk-11.0.15/bin/java.exe'' finished with non-zero exit value 1
```
그러므로 로컬에 H2를 설치한 Github - JpaStudy repository에서 작업했다.

# @Idclass

간단히 Long ID와 String name으로 복합 키를 구성하는 테이블 Users를 만들어 보겠다.

> Users 엔티티
```java
@Entity
@Getter @Setter
@EqualsAndHashCode // equals 와 hashcode() override
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UsersId.class) // UsersId라는 식별자 클래스에 @Id 속성이 다 있어야 한다.
public class Users implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;
    @Id
    @Column(name = "NAME")
    private String name;

    private int age;
}
```
> userId class

복합키를 구성할 필드를 가지고 있는 클래스를 만들어주자.
```java
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UsersId implements Serializable {
    private Long id; // Users Class의 필드 이름이 꼭 같아야 한다.
    private String name; // Users Class의 필드 이름이 꼭 같아야 한다.
}
```
> main 함수
```java
//main
public static void logic(EntityManager em, EntityTransaction tx) throws Exception {
    Users user1 = new Users(1L,"userA",31);
    Users user2 = new Users(2L,"userB",39);
    Users user3 = new Users(3L,"userC",17);
    em.persist(user1);
    em.persist(user2);
    em.persist(user3);

    UsersId id2 = new UsersId(2L, "userB");
    final Users findUser = em.find(Users.class, id2);
    System.out.println("findUser = " + findUser);
}

// 결과 : findUser = Users(id=2, name=userB, age=39)
```

IdClass의 필드 명과 Entity의 @Id 필드명이 다르면 Property of @IdClass not found in entity 에러가 발생한다.

IdClass 보다는 EmbeddedId 방법이 조금 더 객체지향적이다. Embeddable class를 재사용 할 수 있기 때문이다.

EntitiyManager 메서드인 em.find(엔티티클래스, pk값) 로 조회해오는데, pk 값으로 UsersId(식별자 객체)가 들어간 모습을 확인 할 수 있다.

참고로 이때 복합키는 글자순으로 생성된다. (id+name)

# @EmbeddedId

Embeddable Object를 Id로 사용하는 방식이다.

> Embeddable Object

```java
@Embeddable
public class UsersId implements Serializable {
    private Long id;
    private String name;
}
```
> Users Entitiy

```java
// 애노테이션은 생략
public class Users implements Serializable {
    // 식별자 클래스를 별도로 만들 것이다.
//    @Id
//    @Column(name = "ID")
//    private Long id;
//    @Id
//    @Column(name = "NAME")
//    private String name;

    @EmbeddedId
    private UsersId usersId;

    private int age;
}
```
Users 객체도 복잡한 구성 필요 없이 Id로 사용할 필드에 @EmbeddedId annotation만 추가하면 끝이다.

> Main
```java
public static void logic(EntityManager em, EntityTransaction tx) throws Exception {
    UsersId id1 = new UsersId(1L, "userA");
    Users user = new Users(id1,33);
    em.persist(user);

    final Users findUser = em.find(Users.class, id1);
    System.out.println("findUser = " + findUser);
}
```
결과 : findUser = Users(usersId={1,userA}, age=33)


# 3. How use IdClass and EmbeddedId

둘 다 장단점이 있다.

IdClass는 @Id annotation을 여러 개 사용하고 또 식별자 클래스에 각각의 필드명을 맞춰줘야 한다는 점.

EmbeddedId는 JPQL에서 객체 탐색을 더 많이 한다는 점이 있다.

해당 식별자 클래스가 의미가 있어 다른곳에서 사용하면 EmbeddedId, 의미 없이 복합키로서 존재하면 IdClass를 사용하면 될 것이다.

> JPQL 코드
```sql
// IdClass
select u.name from Users u
// EmbeddedId
select u.UsersId.name from Users u
```

# 출처

https://kha0213.github.io/jpa/jpa-composite-key/
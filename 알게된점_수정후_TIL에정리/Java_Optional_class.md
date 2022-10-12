# 출처 : https://kukekyakya.tistory.com/85

자바 8에 추가된 Optional 클래스에 대해서 간단히 정리해보자.

# 문제 상황
개발하다보면 NullPointerException은 흔하게 겪게 됩니다.

아래 상황을 봅시다.

```java
public class Person {
    private Wallet wallet;
    public Wallet getWallet() {
        return wallet;
    }
}

public class Wallet {
    private int money;
    public int getMoney() { 
        return money;
    }
}
```
사람은 지갑을 들고 다니고, 지갑은 돈을 가지고 있습니다.

그런데 갑자기 어떤 공격자가 나타나서, 그 사람이 돈을 얼마나 가지고 있는지 지갑을 꺼내서 엿보려고 합니다.

```java
public class Attacker {
    public int peekMoney(Person person) {
        return person.getWallet().getMoney();
    }
}

--------------------------------------
Attacker.peekMoney(Person)
```
코드에는 아무 문제가 없는것 처럼 보입니다.

그러나 실제로는 지갑을 들고다니지 않는 사람도 있습니다.

이러한 까닭에 공격자가 지갑이 없는 사람을 공격하게 되었을 때 먼저 지갑을 꺼냈을 때 null 참조값을 얻게 되고(getWallet의 return 값)
돈을 꺼내기 위해 getMoney()를 하는 순간 런타임에 NullPointerException이 발생하게 됩니다.

만약 Person 자체가 null값이라면 getWallet()하는 순간부터 예외가 발생할 것입니다.

## 해결방안 1

```java
public class Attacker {
    public int peekMoney(Person person) {
        if(person != null) {
            Wallet wallet = person.getWallet();
            if (wallet != null) {
                return wallet.getMoney();
            }
        }
        return 0;
    }
}
```
공격자가 사람에 대해서, 그 사람이 가지고 있는 지갑에 대해서 모두 검사해보면 됩니다.

하지만 이렇게 되면, 중첩 if 블록이 늘어나면서 가독성이 줄어들게 됩니다.


## 해결 방안2

```java
public class Attacker {
    public int peekMoney(Person person) {
        if (person == null || person.getWallet() == null) return 0;
        return person.getWallet().getMoney();
    }
}
```
하지만 이 경우에도 만약 돈을 꺼내야하는 계층이 늘어날수록 조건문이 추가되는 번거로움이 생기게 됩니다.
+ person, bag, wallet, moneyPocket &rarr; money
rarr
+ 참고 : short circuit으로 인해 앞 조건문이 false였다면, 뒤 조건문은 실행되지 않습니다.

## 문제는 null

null을 사용함으로써 다음과 같은 문제들이 발생하고 있습니다. 

- 에러의 근원 : NullPointerException은 가장 흔하고 치명적인 에러입니다.

- 가독성 저하 : null 확인 코드로 인해 가독성이 저하됩니다.

- 무의미한 표현 : null은 아무런 의미도 표현하지 않습니다.

- 자바 철학의 위배 : 자바는 개발자에게 모든 포인터를 숨겼지만, null 포인터는 예외입니다.

- 형식 시스템의 구멍 : null은 어떠한 형식을 가지고 있지 않으며, 정보를 포함하고 있지 않으므로 모든 참조 형식에 null을 할당할 수 있습니다. 이런 식으로 null이 할당되고, 시스템의 다른 부분으로 null이 퍼졌을 때 애초에 null이 어떤 의미로 사용되었는지 알 수 없게 됩니다.

이러한 null 문제를 해결하기 위해, 자바 8에서는 java.util.Optional<T> 라는 새로운 클래스를 제공합니다.

# OPtional

## Optional 이란?

<img src = "https://github.com/steadykyu/makeBoardProject/blob/master/%EC%95%8C%EA%B2%8C%EB%90%9C%EC%A0%90_%EC%88%98%EC%A0%95%ED%9B%84_TIL%EC%97%90%EC%A0%95%EB%A6%AC/img/Optional_1.png">

Optional는 “존재할 수도 있지만 안 할 수도 있는 객체”, 즉, “null이 될 수도 있는 객체”을 감싸고 있는 일종의 래퍼 클래스입니다. 원소가 없거나 최대 하나 밖에 없는 Collection이나 Stream으로 생각하셔도 좋습니다. 

직접 다루기에 위험하고 까다로운 null을 담을 수 있는 특수한 그릇으로 생각하시면 이해가 쉬우실 것 같습니다.

## Optional 적용

Optional은 선택형 값을 캡슐화하는 클래스입니다.
+ 캡슐화 : 객체의 속성(data fields)과 행위(메서드, methods)를 하나로 묶어둔다.

위 예시에서 사람이 지갑을 가지고 있지 않다면, wallet 변수는 null을 가져야할 것입니다.

하지만 Optional 클래스를 이용하면 null을 할당하는 것이 아니라, wallet의 타입을 Optional<Wallet> 으로 선언하여 **값이 있고 없음을 명시적으로 보여줄 수 있습니다.**

즉, 값이 있으면 Optional 클래스가 Wallet을 감싼채로, 값이 없으면 Optional.empty() 메소드로 Optional을 반환하게 됩니다.

> Optional.empty()

Optional.empty()는 값이 없음을 나타내는 싱글턴 인스턴스를 반환하는 Optional의 스태틱 팩토리 메소드입니다.

null을 담고 있는, 한 마디로 비어있는 Optional 객체를 얻어옵니다.

아래 코드를 봅시다.

```java
public class Person {
    private Optional<Wallet> wallet;
    public Optional<Wallet> getWallet() {
        return wallet;
    }
}
```
이제는 Optional을 사용하면서 wallet의 형식이 Optional<Wallet>으로 나타나고 있습니다. 이는 값이 wallet값이 있을수도 없을 수도 있다는 것을 명시적으로 나타냅니다.

Optional로 감싸진 객체를 만들기 위해서는 어떻게 해야할까요?

> Optional.empty() 인스턴스
```java
Optional<Wallet> wallet = Optional.empty();
```
다음과 같이 스태틱 팩토리 메소드 empty()를 이용해서 값이 비어있음을 나타내는 싱글톤 인스턴스를 얻을 수 있습니다.

> Optional.of(Object); 인스턴스

```java
Wallet wallet = new Wallet();
Optional<Wallet> ofWallet = Optional.of(wallet);
```

또는, 다음과 같이 스태틱 팩토리 메소드 of를 이용해서 null이 아닌 값을 포함하는 Optional을 만들 수도 있습니다.

```java
Wallet wallet = null;
Optional<Wallet> ofWallet = Optional.of(wallet);
```
만약 위와같이 of 메서드에 null 참조를 전달하면, NullPointerException이 발생하게 됩니다.

> of 메서드 내부
```java
class Optional{
    public static Optional of(T value){
        private Optional(T value) {
            this.value = Objects.requireNonNull(value);
        (....)
        }
    }
}
```
of 메소드 내에서 위 private 생성자로 인스턴스가 생성되는데, null인지 검사하는 과정을 거쳐야하기 때문입니다.

> ofNullable()

```java
Wallet wallet = null;
Optional<Wallet> ofWallet = Optional.ofNullable(wallet);
```
ofNullable을 이용하면 null로도 Optional을 만들어낼 수 있습니다. 이 때, null을 전달하면 Optional.empty()가 반환됩니다.

## Optional 에서 값 추출하기

이제 Optional에서 값을 추출하는 몇 가지 방법에 대해서 알아보겠습니다.
```java
Optional<Wallet> wallet = person.getWallet();
Optional<Integer> money = wallet.map(w -> w.getMoney());
```
Optional은 map 메소드를 지원합니다.
+ Optional이 값을 포함하고 있으면 map의 인수로 제공된 함수로 값을 바꾸고,
+ Optional이 값을 포함하고 있지 않으면 아무 일도 일어나지 않습니다.

이 때, map은 Optional로 감싸진 값을 반환하게 됩니다.

이를 이용하면 다음과 같이 코드를 변경할 수 있습니다.
```java
public class Attacker {
    public int peekMoney(Person person) {
        return person.getWallet()
                .map(w -> w.getMoney())
                .orElse(0);
    }
}
```
orElse는 Optional에 값이 없을 때 기본적으로 반환해줄 값을 지정합니다.

위와 같이 명시적으로 null 검사를 하지 않아도, 지갑이 없을 경우 0을 반환하게 됩니다.

## 이중 Optional 구조

위에서 map은 Optioanl로 감싸진 값을 반환하게 된다고 했습니다.

그렇다면 map에서 수행하는 함수에서도 Optional로 감싸진 값을 반환하게 되면 어떻게 될까요?

이를 표현하기 위해 Wallet 클래스를 살짝 수정해보겠습니다.

```java
public class Wallet {
    private Optional<DetachablePocket> detachablePocket;
    public Optional<DetachablePocket> getDetachablePocket() {
        return detachablePocket;
    }
}
```

지갑에는 탈부착할 수 있는 주머니가 있고, 이 주머니에 돈을 보관하고 있다고 가정하겠습니다.

탈부착형 주머니(인스턴스)는 탈부착 형태이기 때문에 있을 수도 없고, 없을 수도 있습니다. 따라서, 또 다시 Optional로 선언해야 합니다.


map을 이용해서 저 주머니를 꺼내려든다면 어떻게 될까요?

```java
Optional<Optional<DetachablePocket>> pocket = person
        .getWallet()
        .map(w -> w.getDetachablePocket());
```
위처럼 중첩된 Optional 구조를 가지게 됩니다.

> flatMap 메서드

이러한 문제는 flatMap 메소드로 해결할 수 있습니다.

이번에는 Person도 Optional로 받아서 peekMoney 메소드를 수행해보겠습니다.

```java
public class Attacker {
    public int peekMoney(Optional<Person> person) {
        return person
                .flatMap(p -> p.getWallet())
                .flatMap(w -> w.getDetachablePocket())
                .map(dp -> dp.getMoney())
                .orElse(0);
    }
}
```
다음과 같이 flatMap 메소드로 중첩된 Optioanl 구조를 평탄화시키면서 돈을 확인할 수 있게 되었습니다.
+ 추가적인 Optional로 감싸지 않고 값을 가져온다.

## Optional unwrapping

이번에는 Optional을 언래핑하는 방법들을 알아보겠습니다.

위 예제에서 사용된 orElse 외에도 다양하게 제공하고 있습니다.

> get() 메서드

```java
public Object unwrap(Optional<Object> obj) {
    return obj.get();
}
```
가장 간단하면서도 안전하지 않은 방법입니다.

값이 있다면 바로 꺼내서 사용할 수 있지만, 값이 없다면 NoSuchElementException을 발생시키게 됩니다.

Optional에 값이 반드시 있다고 확신할 수 있는 상황이 아니라면, 이 방법은 사용하지 않는 것이 바람직합니다.

> orElse()
```java
public void unwrap(Optional<Object> obj) {
    Object defaultObject = new Object();
    Object wrappedObject = obj.orElse(defaultObject);
}
```
예제에서 사용되었던 orElse입니다. Optional에 값이 없다면, 기본 값을 제공할 수 있습니다.

> orElseGet()

```java
public void unwrap(Optional<Object> obj) {
    Object wrappedObject = obj.orElseGet(() -> new Object());
//    Object wrappedObject = obj.orElseGet(Object::new);
}
```
orElseGet은 기본 값이 미리 준비되어있어야하는 orElse와는 다르게, 기본 값을 게으르게 제공합니다.

이를 위해 Supplier를 생성하여 전달해주고, 값이 없을 때만 기본 값을 제공합니다.

즉 orElse는 null이던말던 항상 불리지만,
orElseGet은 null일 때만 불립니다.

기본 값을 준비하는 비용이 비싸거나, Optional이 비어있을 때만 준비해서 제공하고 싶다면 사용할 수 있는 방법입니다.

> orElse(), orElseGet() 차이

https://cfdf.tistory.com/34

> orElseThrow
```java
public void unwrap(Optional<Object> obj) {
    Object wrappedObject = obj.orElseThrow();
}

public void unwrap(Optional<Object> obj) {
    Object wrappedObject = obj.orElseThrow(RuntimeException::new);
}
```
값이 없다면 orElseThrow를 이용하여 예외를 발생시킬 수도 있습니다.

별도로 함수를 전달하지 않을 경우에는 NoSuchElementException을 발생시키기 때문에 get()과 동일하지만,

Supplier로 함수를 전달해주는 경우에는 원하는 예외의 종류를 선택하여 발생시킬 수 있습니다.

( RuntimeException::new 는, () -> new RuntimeException() 과 동일합니다. )

> ifPresent()

```java
public void unwrap(Optional<Object> obj) {
    obj.ifPresent(o -> System.out.println("o = " + o.toString()));
}
```
ifPresent는 값이 있을 때 수행할 행위를 Consumer로 전달할 수 있습니다.

```java
public void unwrap(Optional<Object> obj) {
    obj.ifPresentOrElse(o -> System.out.println("o = " + o.toString()),
            () -> System.out.println("No Element"));
}
```
자바 9에 추가된 ifPresentOrElse는, 값이 없을 때 수행할 행위도 두 번째 인자 Runnable로 전달할 수 있습니다.

## Optional filtering

공격자가 사람이 갖고 있는 돈이 1000원이상 일 경우에만, 그 돈을 취급해주는 상황이라고 가정해보겠습니다.

1000원 미만의 돈을 가지고 있다면 0을 반환해야합니다.

```java
public class Attacker {
    public int peekMoney(Optional<Person> person) {
        return person
                .flatMap(p -> p.getWallet())
                .flatMap(w -> w.getDetachablePocket())
                .map(dp -> dp.getMoney())
                .filter(m -> m.intValue() >= 1000)
                .orElse(0);
    }
}
```
만약 Optional이 비어있다면, filter 연산은 동작하지 않게 됩니다.

## Optional 주의사항

Optional을 사용할 때는 다음과 같은 사항을 주의해야합니다.

Optional은 Serializable 인터페이스를 구현하지 않으므로, 직렬화 모델을 사용하는 도구나 프레임워크에서는 문제가 생길 수 있습니다.

따라서, 다음과 같은 예시처럼 Optional 필드를 선언하는 것 보단 Optional로 필드를 감싸서 반환하는 것을 권장합니다.

```java
public class Person {
    private Wallet wallet;
    public Optional<Wallet> getWallet() {
        return Optional.ofNullable(wallet);
    }
}
```
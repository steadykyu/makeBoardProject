출처 : https://velog.io/@bloomspes/yaml-%ED%8C%8C%EC%9D%BC-%EC%9E%91%EC%84%B1-%EC%9A%94%EB%A0%B9-%EA%B8%B0%EC%B4%88%ED%8E%B8-%EC%8A%A4%ED%94%84%EB%A7%81%ED%8E%B8

# What is a YAML/YML?

Yet Another Markup Language의 약자로, 사람이 읽을 수 있는 데이터 직렬화 언어이다.

사람이 읽을 수 있는 언어라는 수식어가 붙어서 이상하게 들릴 수도 있다. 왜냐면 xml 파일도 우리가 읽을 수 있고 Json 파일도 읽는데 지장은 없기 때문이다. 그렇다면 YAML은 저 두 유형의 파일과 어떻게 다른 것일까?

<img src = "https://github.com/steadykyu/makeBoardProject/blob/master/%EC%95%8C%EA%B2%8C%EB%90%9C%EC%A0%90_%EC%88%98%EC%A0%95%ED%9B%84_TIL%EC%97%90%EC%A0%95%EB%A6%AC/img/yml_1.png">

이렇게 yaml 파일은 셋팅에서 필요한 spec과 property 값이 한 눈에 들어온다. 파일 작성도 다른 양식에 비해 매우 편리하다. 그러므로 많이 애용하는 것이다.

## 기본 문법

Main syntax

- HashMap(key-value)을 기본 구조로 한다.
- value 타입은 Array, String, Number, Boolean 등이 가능하다
- Json 처럼 계층 구조를 가질 수 있다.
- Json과 달리 ""(double quotation marks)없이 문자열 작성이 가능하다.
- YAML/YML 파일은 Json 파일과 상위 호환되기 때문에, Json 시퀀스와 맵을 사용할 수 있습니다.

Special Syntax

- 계층 단계 이동시, 다음 줄에서 Tab 대신에 space bar 2칸으로 들여쓰기 해야한다.
- \- 으로 배열의 원소를 나타낸다.
- \-(하이픈) 다음엔 반드시 space bar가 필요하다.

## YAML의 기본 자료형

- 스칼라(Scalar) : String 혹은 숫자

```yaml
int_type: 1 # : 뒤에 공백이 무조건 필요하다
string_type: "1" # string형식
char_type: 규하
boolean_type: true # 대소문자 구분 X
```

- 시퀀스(Sequence): 배열 혹은 리스트

```yml
# key는 기본적으로 문자열 형식으로 생성된다.
1:
  name: kyu # 문자형식은 ""을 쓰지 않는다.
  age: 25
  isVip: false

# 또는

key: [item, item]
```

- 컬렉션 : 해시 혹은 딕셔너리, key-value 쌍

```yml
key:
  key: value
  key: value

# 또는

key: {
  key: value,
  key: value
}
```

## Collections syntax

> 예시

- 'Sammy Sosa' 노드가 반복되는 경우 - SS로 라벨을 붙여서 선언

```yml
hr:
  - Mark McGwire
  # Following node labeled SS
  - &SS Sammy Sosa
rbi:
  - *SS # Subsequent occurrence
  - Ken Griffey
```

- ?는 Key indicator로 사용된다.
- ? + Space bar(공백) 으로 조건이 포함된 복잡한 매핑 키(complex mapping key)를 선언할 수 있습니다. block collenction 내에서 key-value는 ? 바로 뒤에 선언하는 것이 가능합니다.

```yml
? ‑ Detroit Tigers
  ‑ Chicago cubs
: ‑ 2001‑07‑23

[New York Yankees, Atlanta Braves]: [2001‑07‑02, 2001‑08‑12, 2001‑08‑14]
```

```
내생각

이렇게 복잡한 매핑키 설정시 key 하나에 value가 전부 들어가는 듯하다.
언제 사용할 수 있을지는 모르겠다.
```

> 출처

yml syntax : https://docs.ansible.com/ansible/latest/reference_appendices/YAMLSyntax.html

공식 api : https://yaml.org/spec/1.2.2/

예시 사이트 : http://yaml-online-parser.appspot.com/

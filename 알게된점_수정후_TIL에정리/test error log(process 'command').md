```
Process 'command 'C:/Program Files/Java/jdk-11.0.15/bin/java.exe'' finished with non-zero exit value 1
```

test 함수에서 main 함수를 돌리면 위와같은 error log가 출현한다. 왜인지 알아보자.

composite key 에대 해 공부중 발생했는데, 인 메모리 DB가 문제 인것 같다.

H2 DB를 설치해서 로컬에서 작업하면 해당 코드가 발견되지 않았기 때문이다.

인 메모리시 해당 port를 이미 차지하고 있기에 오류가 발생하는 것 같다.

( 사실 아직 잘 모르겠다ㅜ )

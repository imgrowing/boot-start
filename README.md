# Boot Start Project

## 목적
* 스프링 관련 실습을 하기 위한 프로젝트의 기본 구성을 갖춘다.
* 기술 별로 브랜치를 나누어 관리한다.

## 의존성
* spring web
* spring jpa + data-jpa + queryDsl
* MySQL(MariaDB)
* lombok
* joda time

## 주요 정보
* queryDsl 생성 Task
<pre>
Gradle: Tasks > other > compileQuerydsl
</pre>
* queryDsl 소스 생성 경로:
<pre>
src/main/java
</pre>

## MySQL 접속 정보 지정
* Boot Application 실행할 때에 "VM Options" 에 `-Ddb.server=서버주소:포트` 형식으로 지정한다.

# Boot Start Project

## 목적
* 스프링 관련 실습을 하기 위한 프로젝트의 기본 구성을 갖춘다.
* 기술 별로 브랜치를 나누어 관리한다.

## 의존성
* spring web
* spring jpa + data-jpa + queryDsl
* MySQL(MariaDB)
* Flyway
* Lombok
* joda time

## 기술
* JPA, spring data jpa, queryDSL

## On Going
* Spring Batch

## Todo
* ...

## 주요 정보
* queryDsl 생성 Task
<pre>
Gradle: Tasks > other > compileQuerydsl
</pre>
* queryDsl 소스 생성 경로:
<pre>
src/main/java/generated/
</pre>

## MySQL 접속 정보 지정
* Boot Application 실행할 때에 "VM Options" 에 `-Ddb.server=서버주소:포트` 형식으로 지정한다.

## batch job 실행
* Application 실행 옵션에서 `Program arguments` 항목에 실행하고자 하는 job을 `--job.name=[batchJob이름]` 형식으로 지정한다.
* job 에 대한 파라미터는 `Program arguments` 항목에 지정한다. 하나의 job에 대해 동일한 파라미터로 다시 실행하면 오류가 발생한다.
* 실습시에는 편의상 `version=12` 같은 형태로 job parameter를 지정하여 `run` 한다. 

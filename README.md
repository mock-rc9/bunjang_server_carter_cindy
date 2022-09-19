# 번개장터 Server 개발 일지
- 클라이언트 개발자와 서버 개발자의 협업 프로젝트

## SKILLS
- **Backend**
    - JDK 11 (JAVA)
    - Spring-boot
- **IDE** : IntelliJ
    - **build tool** : gradle
- **Database** : mysql 5.7.21
    - Database tool : DataGrip
- **Deployment** : AWS(EC2)
  - OS : Ubuntu 20.04
  - Web Server : nginx1.10.3
- **Version Control** : Github
- **Open Source** : Kakao Login API

## 패키지 구조
```text
bunjang_server_carter_sindy
  > * build
  > gradle
  > * logs
    | app.log // warn, error 레벨에 해당하는 로그가 작성 되는 파일
    | app-%d{yyyy-MM-dd}.%i.gz
    | error.log // error 레벨에 해당하는 로그가 작성 되는 파일
    | error-%d{yyyy-MM-dd}.%i.gz
  > src.main.java.com.example.demo
    > config
      > secret
        | Secret.java // 시크릿 키 값
      | BaseException.java // Controller, Service, Provider 에서 Response 용으로 공통적으로 사용 될 익셉션 클래스
      | BaseResponse.java // Controller 에서 Response 용으로 공통적으로 사용되는 구조를 위한 모델 클래스
      | BaseResponseStatus.java // Controller, Service, Provider 에서 사용 할 Response Status 관리 클래스 
      | Constant.java // 공통적으로 사용될 상수 값들을 관리하는 곳
    > src
      > test
        | TestController.java // logger를 어떻게 써야하는지 보여주는 테스트 클래스
      > user
        > models
          | GetUserRes.java        
          | PostUserReq.java 
          | PostUserRes.java 
        | UserController.java
        | UserProvider.java
        | UserService.java
        | UserDao.java
      | WebSecurityConfig.java // spring-boot-starter-security, jwt 를 사용하기 위한 클래스 
    > utils
      | AES128.java // 암호화 관련 클래스
      | JwtService.java // jwt 관련 클래스
      | ValidateRegex.java // 정규표현식 관련 클래
    | DemoApplication // SpringBootApplication 서버 시작 지점
  > resources
    | application.yml // Database 연동을 위한 설정 값 세팅 및 Port 정의 파일
    | logback-spring.xml // logger 사용시 console, file 설정 값 정의 파일
build.gradle // gradle 빌드시에 필요한 dependency 설정하는 곳
.gitignore // git 에 포함되지 않아야 하는 폴더, 파일들을 작성 해놓는 곳

```

## 2022-09-17 진행상황
### Carter
- ERD 설계
- EC2 인스턴스 구축
- REST API 리스트업
### Cindy
- ERD 설계
- EC2 인스턴스 구축
- REST API 리스트업

- - - 

## 2022-09-18 진행상황
### Carter
- 더미데이터 역할 분담 및 생성
- EC2 서버 빌드

### Cindy
- 더미데이터 추가
- RDS 데이터베이스 구축
- prod 서버 구축
- SSL 구축

## 2022-09-19 진행상황
### Carter
- 더미데이터 보충
- Home APi 생성중

### Cindy
- 로그인/회원가입 API

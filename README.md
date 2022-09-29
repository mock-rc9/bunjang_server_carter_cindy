# 번개장터 클론 프로젝트
- Server 2인 협업 (Cindy @syb0228, Carter : @GeonH0)
- Client(ios) 3인에게 서버 제공

## SKILLS
- **Backend**
  - JDK 11 (JAVA)
  - Spring-boot
- **IDE** : IntelliJ
  - **build tool** : gradle
- **Database** : mysql 5.7.21
  - Database Service : Amazon RDS 
  - Database tool : DataGrip
- **Deployment** : AWS(EC2)
  - OS : Ubuntu 20.04
  - Web Server : nginx1.10.3
  - Storage Service : Amazon S3
- **Version Control** : Github
- **Open Source**
  - Kakao Login API
  - Naver SENS API
  
- - - 
  
## ERD 설계
![번개장터_20220929_212240](https://user-images.githubusercontent.com/62875437/193030511-59f4ba61-8a9a-4997-ab92-49d2db57ee73.png)
https://aquerytool.com/aquerymain/index/?rurl=5f08af76-cb6f-4f8c-b9c3-1d5f51f07449
(pwd : lbz62x)

- - - 

## API 명세서
https://docs.google.com/spreadsheets/d/1ZS2UCFSkcK28vmRZ-4oA8V6786w9PUaX/edit?usp=sharing&ouid=109976337471917835872&rtpof=true&sd=true

- - - 

## Client-Server 협업 - 기능 기획서 및 역할 분담 개발 일지
https://www.notion.so/softsquared/A-01a5c61b6a3e47fe8e0564023731bdc8

- - - 

## Server 개발 일지 - 진행상황
### 2022-09-17 진행상황
#### Carter
- ERD 설계
- EC2 인스턴스 구축
- REST API 리스트업
#### Cindy
- ERD 설계
- EC2 인스턴스 구축
- REST API 리스트업

### 2022-09-18 진행상황
#### Carter
- 더미데이터 역할 분담 및 생성
- EC2 서버 빌드
#### Cindy
- 더미데이터 추가
- RDS 데이터베이스 구축
- prod 서버 구축
- SSL 구축

### 2022-09-19 진행상황
#### Carter
- 더미데이터 보충
- Home API 생성중
#### Cindy
- 로그인/회원가입 API
- 카카오 로그인 API

### 2022-09-20 진행상황
#### Carter
- Home API 완료
- 상품 상세 페이지 API 완료
- 상품 등록 API 생성중
#### Cindy
- 마이페이지 API

### 2022-09-21 진행상황
#### Carter
- Home API 수정중
- 상품 등록 API 완료
- 상품 수정 API 생성중

#### Cindy
- 마이페이지 API 수정
- 주소 전체 조회 API
- 주소 등록 API
- 주소 수정 API

### 2022-09-22 진행상황
#### Carter
- Home API 수정 완료
- 결제 수단 등록 API
- 결제 수단 전체 조회 API
- 결제 수단 수정 API

#### Cindy
- 주소 삭제 API
- 주문 생성 API
- 주문 상세 내역 조회 API
- 주문 취소 API

### 2022-09-23 진행상황
#### Carter
- Home API SQL 부분 수정 완료
- 상품 API SQL 수정 완료
- Review API 생성중

#### Cindy
- 마이페이지 API 수정
- 상점 소개 편집 API
- 더미데이터 추가

### 2022-09-24 진행상황
#### Carter
- Category API 생성
- 상품 삭제 API  완료
- Review API 생성중

#### Cindy
- 회원가입 API, 카카오 로그인 API 수정
- 팔로워 조회 API
- 팔로잉 조회 API

### 2022-09-25 진행상황
#### Carter
- 홈화면 API 수정
- 상품 상세 API 수정
- 세부 카테고리 API 생성

#### Cindy
- 거래 내역(구매/판매) 조회 API

### 2022-09-26 진행상황
#### Carter
- Review API
- 찜 API 

#### Cindy
- 팔로우 API
- 언팔로우 API
- 회원 탈퇴 API

### 2022-09-27 진행상황
#### Carter
- S3 이용하여 이미지 업로드
- notice API 생성중

#### Cindy
- 판매자 프로필 상세 조회 API
- 내 피드 조회 API
- 내 상점 재재내역 조회 API
- 신고하기 API
- 주문시 상품 정보 조회 API
- 이미지 파일 S3 외부 스토리지에 저장

### 2022-09-28 진행상황
#### Carter
- 상품 검색 API
- 이벤트 API

#### Cindy
- 마이페이지 리뷰 조회 API
- 특정 주소 조회 API
- 문의글 업로드 API
- 문의글 전체 조회 API
- 특정 문의글 조회 API
- 문의 취소 API

### 2022-09-29 진행상황
#### Carter


#### Cindy
- SMS(SENS) API

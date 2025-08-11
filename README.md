# emologue

## 🧵 프로젝트 설명
"감정을 묻는 질문, 나를 마주하는 일기"라는 컨셉의 프로젝트 입니다.
Emologue는 사용자가 문답에 맞춰 일기를 작성하거나 자유형으로 작성한 일기를 분석해 감정 상태를 파악하고 이를 시각적으로 제공하는 감정 분석 일기 서비스입니다.
백엔드 서버는 **회원 관리, 일기 CRUD, 감정 분석 요청 처리, 통계 데이터 제공**등의 기능을 담당합니다.

## 📁 프로젝트 구조
```
project-root
├── src
│   └── main
│       ├── java
│       │   └── com.project.emologue
│       │       ├── config              # 보안, 인코딩, 캐시 설정
│       │       ├── controller          # API 컨트롤러
│       │       ├── exception           # 예외 처리
│       │       ├── external            # python과 연결
│       │       ├── model               # 엔티티, DTO, 에러 모델
│       │       ├── repository          # JPA 인터페이스
│       │       ├── service             # 비즈니스 로직
│       │       └── EmologueApplication # 메인 실행 파일
│       └── resources
│           ├── application.yaml   # 환경 설정
│           ├── application-dev.yaml   # dev 환경 설정
│           ├── application-prod.yaml   # prod 환경 설정
│           └── application-secret.yml   # key값
├── README.md
```

## 🧰 기술 스택
- Java 17
- Spring Boot 3.5
- Spring Data JPA
- QueryDSL
- Spring Security + JWT
- PostgreSQL
- Gradle
- Docker
- Swagger
- Git, GitHub

## ✨ 주요 기능
**1. 회원관리**
- JWT 기반 인증 및 권한 관리 (Spring Security 연동)
- 아이디 중복 체크

**2. 일기 관리**
- 문답형 일기 및 자유형 일기 작성
- 자유형 일기의 경우 딥러닝으로 감정 분석
  
**3. 감정 분석 연동**
- `emologue-ai`서버와 통신해 감정 분석 수행
  
**4. 통계 제공**
- 주/월별 감정 통계
- 일별 감정 변화
- 직업별 감정 통계
  
**5. Swagger 기반 문서화**
- `/swagger-ui`에서 API 확인 가능
- admin/user 구분해서 작성


## 🔥 주요 도전 과제 및 해결 방법
**1. 감정 분석 서버와의 실시간 통신**
- 문제: 딥러닝 서버와의 통신 지연 및 데이터 포맷 불일치
- 해결: REST API 통신 시 JSON 스키마 표준화 및 예외 처리 강화

**2. QueryDSL 통계 쿼리 LocalDate 매핑**
- 문제: SQL DATE 타입과 Java `LocalDate`매핑 시 오류
- 해결: `java.sql.Date` → `.toLocalDate()`변환 적용

**3. Swagger 문서화**
- 문제: Enum, LocalDate 타입 파라미터 문서화 어려움
- 해결: `@Schema`어노테이션 및 예제값 적용

## 📌 관련 저장소
[emologue-ai](https://github.com/shark-coding/emologue-ai)
→ 딥러닝 기반 감정 분석 서버. 로컬 실행 시 백엔드와 함께 실행 필요.

## 📑 API 문서
`spring-openapi`를 사용하여 Swagger UI를 제공합니다.
로컬 서버 실행 후 아래 주소로 접속하면 API 명세를 확인할 수 있습니다.

👇 아래 링크에서 확인하실 수 있습니다:

🔗 (http://localhost:8080/swagger-ui/index.html)

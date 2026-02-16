# 🚀 DNY - 공공 채용공고 조회 서비스

DNY(Dream Next You)는 공공데이터 API를 활용하여 IT 직무의 신입 채용 정보를 쉽고 빠르게 조회할 수 있도록 돕는 서비스입니다.

## 📌 프로젝트 개요
공공기관의 방대한 채용 공고 중 IT 관련 직무와 신입 지원이 가능한 공고만을 정교하게 필터링하여 사용자에게 제공합니다. 복잡한 검색 과정 없이 자신에게 맞는 기회를 놓치지 않도록 돕는 것이 목표입니다.

## ✨ 주요 기능
- **공공데이터포털 연동**: 최신 공공기관 채용 정보를 실시간으로 수집합니다.
- **스마트 필터링**: 
  - **IT 직무 판별**: 정보, 전산, IT, 소프트웨어 등 15개 이상의 핵심 키워드를 기반으로 IT 관련 직무를 자동 분류합니다.
  - **신입 지원 가능 공고**: 경력직 위주의 공고를 제외하고 '신입' 또는 '신입·경력' 공고만 선별합니다.
- **사용자 인증**: 회원가입 및 로그인 기능을 제공합니다.
- **북마크 (개발 중)**: 관심 있는 채용 공고를 저장하고 관리할 수 있습니다.

## 🛠 기술 스택
- **Backend**: Java 17, Spring Boot 3.5.9
- **Database**: MySQL, Spring Data JPA
- **Frontend**: HTML5, JavaScript (Static resources)
- **Security**: BCrypt (Password Hashing)
- **Build Tool**: Gradle

## 📂 프로젝트 구조
```text
src/main/java/com/dny/dny/
├── controller/    # HTTP 요청 처리 (API 엔드포인트)
├── service/       # 비즈니스 로직 및 외부 API 연동
├── repository/    # 데이터베이스 액세스 (JPA)
├── entity/        # 데이터베이스 테이블 매핑
└── dto/           # 데이터 전송 객체
```

## 🚀 시작하기
1. **Repository 클론**
   ```bash
   git clone https://github.com/your-username/DNY-main.git
   ```
2. **데이터베이스 설정**
   - `src/main/resources/application.yaml`에서 MySQL 접속 정보를 설정합니다.
3. **애플리케이션 실행**
   ```bash
   ./gradlew bootRun
   ```

## 📈 향후 개선 사항
- **프론트엔드 고도화**: React 또는 Vue.js 도입 및 JavaScript 파일 분리
- **보안 강화**: Spring Security 및 JWT 도입
- **알림 기능**: 관심 공고 등록 시 이메일 또는 푸시 알림 제공
- **테스트 코드**: 단위 테스트 및 통합 테스트 확충

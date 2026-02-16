# 🚀 DNY (Dream Next You) - IT 신입 공공 채용 큐레이션

공공데이터포털 API를 활용하여 공공기관의 **IT 직무 신입 채용 정보**만 선별하여 제공하는 웹 서비스입니다.

## 📌 주요 기능

### ⚡ 고성능 데이터 수집 및 최적화
- **병렬 수집 (Parallel Processing)**: Java Parallel Stream을 사용하여 수천 건의 공고를 수 초 내에 수집.
- **트래픽 최적화 (Traffic Saving)**: 첫 페이지만 우선 확인하여 신규 공고가 있을 때만 전체 수집을 진행하는 지능형 업데이트 로직.
- **비동기 처리 (@Async)**: 서버 부팅 속도에 영향을 주지 않도록 백그라운드에서 데이터 수집 및 정제 수행.

### 🔍 스마트 검색 및 필터링
- **통합 검색 시스템**: JpaSpecification을 통해 검색어, 지역, 채용구분 필터를 하나의 API로 통합 처리.
- **IT 신입 필터링**: 제목 키워드 및 채용 유형 분석을 통한 IT 직무 자동 분류.
- **마감 공고 자동 관리**: 마감 기한이 지난 공고의 자동 제외 및 DB 자동 삭제 로직.

### 💻 현대적인 사용자 경험
- **Vue.js 기반 UI**: Vanilla JS에서 Vue.js(CDN)로 전환하여 선언적이고 부드러운 화면 전환 제공.
- **마이페이지(스크랩)**: 관심 공고를 저장하고 관리할 수 있는 북마크 시스템.
- **페이징 및 정렬**: Spring Data Pagination 기반의 효율적인 데이터 조회 및 최신순/마감순 정렬 지원.

## 🛠 기술 스택
- **Backend**: Java 17, Spring Boot 3.4.x
- **Frontend**: Vue.js 3 (CDN), HTML5/CSS3
- **Database**: JPA (Spring Data JPA)
- **API**: 공공데이터포털(잡알리오) 채용정보 API

## 📂 프로젝트 구조
- `controller/`: REST API 요청 처리 (통합 검색 및 북마크)
- `service/`: 핵심 비즈니스 로직 (병렬 수집, 필터링, 트래픽 최적화)
- `repository/`: DB 접근 및 동적 쿼리(Specification) 처리
- `resources/static/`: Vue.js 기반 프론트엔드 정적 파일

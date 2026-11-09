# Data Fabric 지식그래프 백엔드 서버

> ## 저장소 상태
>
> 본 저장소는 아래 과제의 **1단계(2023~2024)** 지식그래프 구성요소입니다.
> 관계 데이터 간 유사 속성 분석 기능은 2단계에서 Recommender 저장소의
> 유사 속성 탐지 모듈로 계승되었습니다.
>
> - 과제명: 분산된 데이터에 대한 논리적 데이터 통합과 복합분석을 지원하는 데이터 패브릭 기술개발
> - 과제번호: RS-2023-00222191
> - 개발 기간: 2023년 12월 ~ 2024년 12월
> - 계승 저장소: https://github.com/DataFabricTech/Recommender
>
> 본 저장소의 기본 브랜치는 `develop`입니다.

## 개요

데이터 간 관계 분석 작업을 생성·실행·조회하는 백엔드 서버입니다.
분석 요청을 받아 Kubernetes 워크플로로 실행하고, 진행 상태와 결과 그래프를 API로 제공합니다.

## 스펙

| 항목 | 내용 |
|---|---|
| 언어 | Java 17 |
| 프레임워크 | Spring Boot 2.7.16 |
| 빌드 도구 | Gradle (Wrapper 포함) |
| 패키징 | Jar |
| 데이터베이스 | MySQL 8 (스키마 변경은 Liquibase로 관리) |
| 실행 환경 연동 | Kubernetes Client (Fabric8, io.kubernetes) |

## 구조

헥사고날 구조를 따릅니다.

| 패키지 | 역할 |
|---|---|
| `domain` | 분석·관계·이벤트 도메인 모델과 규칙입니다. |
| `application` | 유스케이스 포트와 구현입니다. 분석 생성·실행·완료·실패 처리, 작업 감시(watcher), 이벤트 발행(outbox)을 담당합니다. |
| `infrastructure` | 영속성 어댑터와 이벤트 발행 어댑터입니다. |
| `presentation` | REST 컨트롤러입니다. |
| `kubernetesclient` | 분석 작업 실행을 위한 Kubernetes 자원(워크플로, 파드, 네임스페이스, 볼륨, RBAC) 제어 코드입니다. |

도메인 이벤트는 Outbox 패턴으로 전달하여, 트랜잭션 커밋과 이벤트 발행의 일관성을 확보합니다.

## 빌드 및 실행

### 요구 사항

- JDK 17
- Docker (로컬 데이터베이스 기동용)

### 로컬 데이터베이스 기동

```bash
docker compose up -d
```

`docker/db/.env`의 값으로 MySQL 컨테이너가 기동됩니다. **예시 값이므로 실제 운영 환경에서는 반드시 변경하십시오.**

### 빌드

```bash
./gradlew clean build
```

### 실행

```bash
./gradlew bootRun
```

프로파일을 지정하여 실행할 수 있습니다. `local`은 로컬 MySQL, `h2`는 인메모리 데이터베이스를 사용합니다.

```bash
./gradlew bootRun --args='--spring.profiles.active=h2'
```

접속 정보, 컨테이너 이미지 경로 등 환경에 따라 달라지는 값은 `src/main/resources/application*.yml`에서
환경 변수로 주입받습니다. 배포 환경에 맞게 설정하십시오.

## 참고 자료

`Analysis-Result/`에는 1단계 분석 기능의 실행 결과 예시(클러스터링 분석, 관계 분석)가 CSV로 포함되어 있습니다.

## 라이선스

Apache License 2.0을 따릅니다. 자세한 내용은 [LICENSE](LICENSE)를 참고하십시오.

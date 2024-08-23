# 프로젝트 이름

배달 앱 서비스 구현

## 1. 프로젝트 생성

### 기술 스택
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <br/>
<img src="https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white"> <br/>
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> <br/>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> 

<img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"> <br/>
<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> 
<img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">


### Dependency

필요한 의존성은 프로젝트 진행 상황에 따라 추가합니다.
~~~ gradle
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
~~~

앱 설정 파일은 `.yaml` 형식을 사용합니다.

GitHub 프로젝트 탭을 생성하여 진행 상황을 관리합니다.

## 2. 개발 플로우 전략

- **`main`**: 최종 배포 브랜치
- **`dev`**: 개발 통합 브랜치
- **`feature#1_닉네임`**: 기능 개발 브랜치 (예: `feature#1_johndoe`)

## 3. 커밋 컨벤션

커밋 메시지는 다음 형식을 따릅니다:

### 커밋 예시

1. **이슈 발행**: 프로젝트 관리 도구에서 이슈를 발행합니다.
2. **브랜치 생성**: 이슈 번호를 포함한 브랜치를 만듭니다. (예: `#1`)
3. **커밋 메시지**: 커밋 메시지에도 이슈 번호를 포함합니다. (예: `#1`)

### PR 규칙

PR 메시지는 다음 형식을 따릅니다
>💡 Feat : 기능 설명
>
>상세 설명: API 개발, 엔티티 수정 등
>관련 이슈 번호: #1, #3

[커밋 메시지 컨벤션 참고 링크](https://velog.io/@archivvonjang/Git-Commit-Message-Convention)

## 4. 역할 분담

### API 담당자

- **이현욱**: 결제
- **조아영**: 사용자 (토큰 발행, 헤더에 넣어서 리턴하는 것까지!), 배송
- **정호준**: 주문, AI
- **이원영**: 음식점, 메뉴

**참고**: 사용자 구현 전까지 다음과 같은 임시 ID를 사용하여 테스트를 진행합니다.

- 고객 아이디: 1
- 가게 주인: 2
- 관리자: 3

## 5. Entity 생성 시 어노테이션 통일
-  setter의 경우에는 필요한 부분에 설정
```java
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "p_example")
public class Example {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter private String name;
    // other fields and methods
}
```

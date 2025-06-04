# 📡 Backend – Spring API Server

> *Spring Boot 3 + Spring Security (JWT) + QueryDSL + MySQL (Aurora)* 로 구성된 API 서버입니다.  
> 이 문서는 로컬 개발 및 운영 배포 흐름을 빠르게 이해할 수 있도록 작성되었습니다.

---

## 🖼️ System Architecture

![image](https://github.com/user-attachments/assets/f22378f4-d553-454b-b957-37d0c8486073)


*Browser* → **Spring Security** → **Controller** → **Service** → **Repository** → **AWS Aurora MySQL**  

---

## 🛠️ Tech Stack

| Layer / Tool | Version | Note |
|--------------|---------|------|
| **Language** | Java 17 | LTS |
| **Framework** | Spring Boot 3.x | Gradle |
| **Auth** | Spring Security + JWT | Access / Refresh Token |
| **DB** | AWS Aurora (MySQL 8) | RDS |
| **ORM** | Spring Data JPA | 동적 쿼리를 위해 QueryDSL |
| **Infrastructure** | AWS EC2 | |

---

## 📁 Project Structure

```text
realconnect-backend/
├─ gradle/
├─ src/
│  ├─ main/
│  │  ├─ java/com/example/RealConnect/
│  │  │  ├─ apartment/        # 아파트 정보
│  │  │  ├─ config/           # QueryDSL
│  │  │  ├─ contract/         # 계약
│  │  │  ├─ inquiry/          # 문의
│  │  │  ├─ property/         # 매물
│  │  │    ├─ controller/       
│  │  │    ├─ domain/           
│  │  │    ├─ exception/        
│  │  │    ├─ repository/       
│  │  │    └─ service/          
│  │  │  ├─ security/         # JWT + Authentication filters
│  │  │  └─ user/             # 유저
│  │  ├─ resources/
│  │  │  ├─ static/images/parkrio/types/ # 평면도
│  │  │  └─ application.properties
│  │  └─ RealConnectApplication.java
│  └─ test/java/com/example/RealConnect/
├─ .gitattributes
├─ .gitignore
├─ build.gradle
├─ gradlew / gradlew.bat
├─ settings.gradle
└─ readme.md
```

## 🚚 Deploy (현재 프로세스)

> _※ CI/CD 자동화 전까지 사용되는 수동 배포 방법입니다._  
> AWS EC2(ubuntu) + **start.sh / stop.sh** 스크립트를 이용해 JAR 를 교체-실행합니다.

| 단계 | 작업 | 
|------|------|
| **1. 프로파일 변경** | `src/main/resources/application.properties` 에서 `#prod` 블록의 주석을 해제하고, `#dev` 블록은 주석 처리 |
| **2. 빌드** | IntelliJ **Gradle ➜ Tasks ➜ build ➜ _bootJar_** 더블 클릭 → `build/libs/RealConnect-0.0.1-SNAPSHOT.jar` 생성 | `docs/deploy/step2_build.png` |
| **3. JAR 전송** | FileZilla (또는 WinSCP)로 **`/home/ubuntu/realconnect/`** 폴더에 방금 생성한 JAR 업로드 <br>기존 JAR 는 이름을 변경(백업)만 하고 삭제 ❌ | 
| **4. 서버 접속** | MobaXterm → SSH 로 EC2 접속 후 `cd ~/realconnect` | 
| **5. 웹 서버 종료** | ```bash ./stop.sh ``` <br>→ **PID** 가 표시되며 프로세스 종료 | 
| **6. 새 버전 실행** | ```bash ./start.sh ``` <br>백그라운드 실행 후 바로 prompt 복귀 (10-30 s 소요) |
| **7. 로그 확인** | ```bash tail -f realconnect.log ``` <br>“`Started RealConnectApplication`” 문구 확인 → 배포 완료 | 

💡 To-do: 추후 GitHub Actions + CodeDeploy 로 자동화(Blue-Green 배포) 예정.

# 🧠 AI 분야별 챗봇 채팅 시스템

특정 분야(예: 대통령, 연인, 국회의원 등)에 특화된 AI와 실시간으로 대화할 수 있는 웹 기반 채팅 서비스이다.  
사용자는 원하는 분야의 챗봇을 선택해 채팅방을 만들고, WebSocket을 통해 실시간으로 대화할 수 있습니다.

---
## 🧠 챗봇 채팅방 시스템

### 💬 채팅방 추가

<p align="center">
  <img src="https://github.com/user-attachments/assets/0b4f3d37-9cfa-470e-bc71-ea0fdd26683c" alt="ChatBot UI - Group 1" width="300"/>
  <img src="https://github.com/user-attachments/assets/80451d11-75e6-48f9-92e4-b213de28f354" alt="ChatBot UI - Group 2" width="300"/>
</p>

---

### 🖥️ 채팅방 목록

<p align="center">
  <img src="https://github.com/user-attachments/assets/c0d2a29a-3bf2-44d2-82ac-04d093d8c9ac" alt="ChatRoom List Page" width="800"/>
</p>

---

### 🗨️ 채팅 내용

<p align="center">
  <img src="https://github.com/user-attachments/assets/146718c2-abad-43e5-ac09-3192e8ab1239" alt="Chat Detail Page" width="400"/>
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/14306af0-ca4a-48d5-97d8-68bd0760a59f" alt="Chat History Page" width="400"/>
</p>

---

## 📌 주요 기능


### 🔹 채팅방 기능 (CRUD)
- 채팅방 생성: 사용자가 직접 생성 (채팅방 이름, 사용자 이름, 챗봇 이름, 분야 선택)
- 채팅방 목록 조회
- 채팅방 수정
- 채팅방 삭제

### 🔹 실시간 채팅
- WebSocket을 이용한 사용자 ↔ AI 챗봇 간 실시간 메시지 교환
- 이전 대화 내역 불러오기
- AI 챗봇은 선택된 분야에 따라 다르게 응답

### 🔹 챗봇 역할 프롬프트
- 사용자가 선택한 챗봇 분야에 따라 다음과 같은 프롬프트가 전달됩니다:

| 분야 | 프롬프트 |
|------|----------|
| 트럼프 대통령 | 너는 전세계에 엄청난 영향을 주는 미국의 트럼프 대통령이야. 너의 말 한마디에 엄청난 영향력이 있다는 것을 인지하며 품격 있게 답변해. |
| 젠슨 황(엔비디아 CEO) | 너는 엔비디아 CEO야. 너에게 기술적 또는 경제적 질문들에 정확하고 명료하게 대답해.|
| 손흥민 | 너는 세계적인 축구선수이며, 대한민국 스포츠 스타야. 사람들에게 꿈을 심어주고 세계 무대로 도전하게 해주는 것을 중심으로 이야기 해|
| 김연아 | 너는 대한민국의 피겨 여왕 김연아야. 스포츠에 대한 열정과 꿈을 심어주는 메시지를 전해.|
| 연인 | 너는 다정하고 애정 어린 연인이야. 따뜻하게 대화해. |
| 국회의원 | 너는 국민의 의견을 듣고 대변하는 국회의원이야. 논리적으로 말해. |
| 치킨집 사장님 | 너는 유쾌한 치킨집 사장이야. 장사 이야기나 주문에 친절히 응대해. |
| 상담가 | 너는 공감 능력이 뛰어난 전문 상담가야. 위로와 조언을 중심으로 이야기해. |

---

## 🧰 기술 스택

| 구분 | 기술 |
|------|------|
| 프론트엔드 | React.js, Axios, Stomp |
| 백엔드 | Spring Boot, Lombok |
| AI 연동 | OpenAI GPT 3.5 API |
| DB | MySQL |
| 통신 | REST API + WebSocket |

---

## 🗃️ DB 설계

### 🔸 ChatRoom 테이블
| 필드명 | 타입 | 설명 |
|--------|------|------|
| id | Long | 채팅방 고유 ID |
| roomName | String | 채팅방 이름 |
| userName | String | 사용자의 이름 |
| aiName | String | AI 챗봇 이름 |
| topic | String | 챗봇 분야 |
| createdAt | LocalDateTime | 생성 시간 |

### 🔸 ChatMessage 테이블
| 필드명 | 타입 | 설명 |
|--------|------|------|
| id | Long | 메시지 ID |
| chatRoomId | Long | 채팅방 ID (FK) |
| sender | String | 보낸 사람 (user / ai) |
| content | String | 메시지 내용 |
| timestamp | LocalDateTime | 보낸 시간 |

---

## 🏁 실행 방법

1. env 파일 생성
2. 빌드 및 실행
   docker-compose up --build
3. 중지
   docker-compose down

#### docker-compose로 오류 발생시 따로 실행 방법
1. 백엔드 서버 실행<br>
   ./gradlew bootRun

2. 프론트엔드 실행<br>
   cd frontend<br> npm install <br> npm start

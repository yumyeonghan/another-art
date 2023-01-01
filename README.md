# Another Art [AI 기반 작품 경매 웹 애플리케이션]

2022 상상기업 프로젝트 [3.9 ~ 11.24]

<br>
<hr>

## 일정
- 기업 공모 및 구성원 모집 [3.9 - 3.29]
- 조직 구성 완료 및 선정 [3.30 ~ 4.5]
- [사업계획서 제출](https://github.com/yumyeonghan/Another_Art/files/10328556/SW.pdf) [5.4 ~ 5.10]
- [중간보고서(1차) 제출](https://github.com/yumyeonghan/Another_Art/files/10328572/18.SW.1.pdf) [6.1 ~ 6.7]
- [중간보고서(2차) 제출](https://github.com/yumyeonghan/Another_Art/files/10328574/SW.2._Another.Art.pdf) [9.8 ~ 9.14]
- [발표 포스터 제출](https://github.com/yumyeonghan/Another_Art/files/10328568/2022.SW._._Another.Art_.pdf) [10.13 ~ 10.19]
- SW상상기업 전시발표회(예선 통과) [11.23]
- [상상기업 결선 PT발표(우수상 수여)](https://github.com/yumyeonghan/Another_Art/files/10328567/SW.PT.pdf) [11.24]

<br>
<hr>


## 개발팀

<table>
  <tr>
    <td colspan=2 align=center>Frontend</td>
  </tr> 
  
  <tr>
   <td align="center">
    <a href="https://github.com/holyPigeon">
      <img src="https://avatars.githubusercontent.com/u/89138189?v=4" width="130px; style="vertical-align:top" alt=""/>
    </a>
   </td>
   <td align="center">
    <a href="https://github.com/ongsim0629">
      <img src="https://avatars.githubusercontent.com/u/99964694?v=4" width="130px;" style="vertical-align:top" alt=""/>
    </a>
   </td>
  </tr>
  
  <tr>
    <td align="center">정재우 (팀원)</td>
    <td align="center">신수빈 (팀원)</td>
  </tr>
</table>

<table>
  <tr>
    <td colspan=4 align=center>Backend</td>
  </tr> 
  
  <tr>
   <td align="center">
    <a href="https://github.com/yumyeonghan">
      <img src="https://avatars.githubusercontent.com/u/75025163?v=4" width="130px; style="vertical-align:top" alt=""/>
    </a>
   </td>
   <td align="center">
    <a href="https://github.com/sjiwon">
      <img src="https://avatars.githubusercontent.com/u/51479381?v=4" width="130px;" style="vertical-align:top" alt=""/>
    </a>
   </td>
   <td align="center">
    <a href="https://github.com/eunchannam">
      <img src="https://avatars.githubusercontent.com/u/75837025?v=4" width="130px;" style="vertical-align:top" alt=""/>
    </a>
   </td>
   <td align="center">
    <a href="https://github.com/Nter-developer">
      <img src="https://avatars.githubusercontent.com/u/59863112?v=4" width="130px;" style="vertical-align:top" alt=""/>
    </a>
   </td>
  </tr>
  
  <tr>
    <td align="center">유명한 (팀장)</td>
    <td align="center">서지원 (팀원)</td>
    <td align="center">남은찬 (팀원)</td>
    <td align="center">이상현 (팀원)</td>
  </tr>
</table>

<br>
<hr>

## 기술 스택

### Frontend
<img src="https://user-images.githubusercontent.com/51479381/210175424-e947f0db-8de5-4284-a33a-5801bb83b950.png" width="50%"/>

### Backend
<img src="https://user-images.githubusercontent.com/51479381/210175746-46062b24-c6eb-4d92-b278-28e8ac070fb6.png" width="50%"/>


### Tool
<img src="https://user-images.githubusercontent.com/51479381/210175862-07f45054-3fef-4212-b77b-349c14bb7e09.png" width="40%"/>


<br>
<hr>

## 핵심 기능
- 회원가입
  - <code>닉네임, 로그인 아이디, 전화번호, 이메일</code>에 대한 중복체크 진행
  - 공공데이터포탈의 대학 정보조회 API를 활용한 학교명 검색
  - 다음 우편번호 조회 API를 활용한 주소 검색
- 로그인
  - 아이디 또는 비밀번호 분실 시 찾기 및 재설정
- 마이페이지
  - 사용자 정보 조회 및 수정
  - 잔여 포인트 조회 및 충전
    - 카카오페이 PG 연동을 통한 포인트 충전
- 작품 등록
  - <code>경매/일반</code> 작품에 대한 등록
    - 경매 작품일 경우 <code>경매 시작/종료 날짜</code> 기입
- 작품 조회
  - <code>경매 작품</code> -> 등록날짜, 비드가격, 비드횟수에 대한 정렬 기준 존재
  - <code>일반 작품</code> -> 등록날짜, 작품가격, 찜횟수에 대한 정렬 기준 존재
- 경매
  - 경매 작품에 대한 비드
  - 경매가 종료될 경우 최고 금액 입찰자에게 낙찰 알림 발송
- 검색
  - <code>키워드</code>기반 작품 텍스트 검색
  - <code>해시태그</code>기반 AI 객체탐지 검색
  
<br>
<hr>

## Demo
![KakaoTalk_Photo_2022-12-31-01-41-25](https://user-images.githubusercontent.com/75025163/210093375-2a4dde90-8452-459b-ae43-6cb15ffd1728.gif)
![KakaoTalk_Photo_2022-12-31-01-41-31](https://user-images.githubusercontent.com/75025163/210093416-bf54fdaa-b8a3-467e-9e33-77b443551d19.gif)
![KakaoTalk_Photo_2022-12-31-01-44-16](https://user-images.githubusercontent.com/75025163/210093559-4a6684a2-cb32-42c4-99e5-8910b00d03c5.gif)
![KakaoTalk_Photo_2022-12-31-01-44-19](https://user-images.githubusercontent.com/75025163/210093594-d7f57e35-9c7b-4a48-856e-4a4207b7d83e.gif)


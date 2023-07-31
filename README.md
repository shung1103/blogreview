# blogreview

## 요구사항
1. 게시글 좋아요 API -> 전부 해결
  - 사용자는 선택한 게시글에 ‘좋아요’를 할 수 있습니다.
  - 사용자가 이미 ‘좋아요’한 게시글에 다시 ‘좋아요’ 요청을 하면 ‘좋아요’를 했던 기록이 취소됩니다.
  - 요청이 성공하면 Client 로 성공했다는 메시지, 상태코드 반환하기
  - 게시글에 좋아요 개수도 함께 반환하기
2. 댓글 좋아요 API -> 전부 해결
  - 사용자는 선택한 댓글에 ‘좋아요’를 할 수 있습니다.
  - 사용자가 이미 ‘좋아요’한 댓글에 다시 ‘좋아요’ 요청을 하면 ‘좋아요’를 했던 기록이 취소됩니다.
  - 요청이 성공하면 Client 로 성공했다는 메시지, 상태코드 반환하기
  - 댓글에 좋아요 개수도 함께 반환하기
3. 예외처리
  - 아래 예외처리를 AOP 를 활용하여 구현하기

## API 명세
https://documenter.getpostman.com/view/27928373/2s93zB5246

|기능|Method|URL|Request|Response|
|--|--|--|--|--|
|게시글 목록 조회|GET|/||게시글 리스트|
|게시글 선택 조회|GET|/api/post/{id}||선택한 게시글|
|게시글 작성|POST|/api/post|{ "title":title,"content":content }|작성한 게시글|
|게시글 수정|PUT|/api/post/{id}|{ "title":title,"content":content }|수정한 게시글|
|게시글 삭제|DELETE|/api/post/{id}|||
|회원가입|POST|/api/user/signup|{ "user_id": userid, "password":password, "username":username, "introduce":introduce }||
|로그인|POST|/api/user/login|{ "user_id":userid, "password":password }|Header에 JWT 토큰|
|회원정보 수정|PUT|/api/user/{id}|{ "username":username, "password":password, "introduce":introduce }|수정된 회원정보|
|댓글 목록 조회|GET|/api/post/{id}||선택한 게시글의 댓글 리스트|
|댓글 작성|POST|/api/post/{id}/comment|{ "content":content }|작성한 댓글|
|댓글 수정|PUT|/api/post/{id}/comment/{commentid}|{ "content":content }|수정한 댓글|
|댓글 삭제|DELETE|/api/post/{id}/comment/{commentid}|||
|게시글 좋아요 등록|POST|/api/like/{postid}||
|게시글 좋아요 취소|DELETE|/api/like/{postid}||
|댓글 좋아요 등록|POST|/api/like/{postid}/{commentid}||
|댓글 좋아요 취소|DELETE|/api/like/{postid}/{commentid}||

## ERD

![252916066-3791962e-2960-454c-b343-b163723a6d69](https://github.com/shung1103/blogreview/assets/133616029/a2e996df-bde7-4799-94ce-c2b3fe173a51)

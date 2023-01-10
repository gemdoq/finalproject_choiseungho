- - -
# 💬 프로젝트 설명
> ![likelion](./img/likelion.png)   
> __회원 가입__ 후 __게시글 작성__ · __조회__ · __수정__ · __삭제__ · __댓글__ · __좋아요__  등을 할 수 있는 `SNS 웹 페이지` 구현
- - -
## ▶ 📃 Swagger 주소
```
http://ec2-43-200-177-246.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/
```
- - -
## 🔨 TECH STACK
![Spring Boot](https://img.shields.io/badge/spring_boot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/spring_security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white)
![GitLab](https://img.shields.io/badge/GitLab-FC6D26?style=for-the-badge&logo=GitLab&logoColor=white)
![MySQL](https://img.shields.io/badge/MYSQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-4479A1?style=for-the-badge&logo=MariaDB&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=AmazonEC2&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white)
- - -
## ▶ 개발환경
> - 에디터 : Intellij Ultimate
> - 개발 툴 : SpringBoot 2.7.5
> - 자바 : JAVA 11
> - 빌드 : Gradle 6.8
> - 서버 : AWS EC2
> - 배포 : Docker
> - 데이터베이스 : MySQL, MariaDB
> - 필수 라이브러리 : SpringBoot Web, MySQL, MariaDB, Spring Data JPA, Lombok, Spring Security
- - -
## ▶ Architectures
> ![architecture](./img/architecture.png)
- - -
## ▶ ERD
> ![erd](./img/erd.png)
- - -
## ▶ Deployment
```shell
sudo sh deploy.sh {db.url} {db.username} {db.password} {jwt.secret} {port} {gitlab.username} {project.name}
```
- - -
## ▶ Access address
```shell
{address}:{port}
```
- - -
## ▶ 📔Endpoint
|  구분  |  HTTP |                URI                   |          설명                      |   RequestBody(Raw JSON)   |
|:-----:|:-----:|:------------------------------------:|:----------------------:|:-------------------------:|
| USER  |  POST |          api/v1/users/join           |      회원 가입            | {"userName":"string","password":"string"} |
| USER  |  POST |          api/v1/users/login          |      로그인 및 토큰 발급   | {"userName":"string","password":"string"} |
| POST  |  POST |             api/v1/posts             |      게시글 등록          | {"title":"string","body":"string"} |
| POST  |  GET  |             api/v1/posts             |      게시글 리스트 조회    | - |
| POST  |  GET  |        api/v1/posts/{postsId}        |      게시글 상세 조회      | - |
| POST  |  PUT  |         api/v1/posts/{id}            |      게시글 수정         | {"title":"string","body":"string"} |
| POST  |DELETE |         api/v1/posts/{id}            |      게시글 삭제         | - |
| POST  |  GET  |            api/v1/posts/my           |      마이피드 조회        | - |
|COMMENT|  POST |    api/v1/posts/{postsId}/comments   |      댓글 등록          | {"comment":"string"} |
|COMMENT|  GET  |    api/v1/posts/{postId}/comments    |      댓글 전체 조회      | - |
|COMMENT|  GET  |api/v1/posts/{postId}/comments/{commentId}|    댓글 상세 조회    | - |
|COMMENT|  PUT  |api/v1/posts/{postId}/comments/{commentId}|      댓글 수정      | {"comment":"string"} |
|COMMENT|DELETE |api/v1/posts/{postId}/comments/{commentId}|      댓글 삭제      | - |
| LIKE  |  POST |      api/v1/posts/{postId}/likes     |      좋아요 누르기        | - |
| LIKE  |  GET  |      api/v1/posts/{postId}/likes     |      좋아요 개수 조회      | - |
| ALARM |  GET  |             api/v1/alarms            |      알람 조회          | - |

- - -
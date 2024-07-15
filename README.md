# Server

## 🔍 System Architecture
***
![image](https://github.com/user-attachments/assets/d27aafa9-ffbf-48c4-abbd-abd569bf3856)


## 📜 ERD 설계도
***
![image](https://github.com/user-attachments/assets/df141b7a-1721-4290-9fed-886a8165ead7)




## 💻 Technology
***
#### Framwork - ![spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)&nbsp; 

#### Security - ![springsecurity](https://img.shields.io/badge/springsecurity-6DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)&nbsp; ![jsonwebtokens](https://img.shields.io/badge/jsonwebtokens-000000.svg?style=for-the-badge&logo=jsonwebtokens&logoColor=white)&nbsp;
#### Database - ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white) &nbsp; ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)

#### Aws - ![ec2](https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)&nbsp; ![RDS](https://img.shields.io/badge/amazonrds-527FFF.svg?style=for-the-badge&logo=amazonrds&logoColor=white) &nbsp; ![S3](https://img.shields.io/badge/amazons3-569A31.svg?style=for-the-badge&logo=amazons3&logoColor=white)&nbsp; ![Route53](https://img.shields.io/badge/amazonroute53-8C4FFF.svg?style=for-the-badge&logo=amazonroute53&logoColor=white)

#### CI/CD - ![docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)  ![GitAction](https://img.shields.io/badge/GitAction-000000.svg?style=for-the-badge&logo=ECS&logoColor=white)&nbsp; 

## 🗂️ Commit Convention
***


| 태그이름       | 내용                                          |
|------------|---------------------------------------------|
| `feat`     | 새로운 기능을 추가할 경우                              |
| `fix `     | 버그를 고친 경우                                   |
| `!hotfix`  | 급하게 치명적인 버그를 고쳐야하는 경우                       |
| `style`    | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우            |
| `refactor` | 코드 리팩토링                                     |
| `comment`  | 필요한 주석 추가 및 변경                              |
| `docs`	    | 문서, Swagger 를 수정한 경우                        |
| `test`     | 테스트 추가, 테스트 리팩토링(프로덕션 코드 변경 X)              |
| `chore`	   | 빌드 태스트 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경 X) |
| `rename`   | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우                |
| `remove`   | 파일을 삭제하는 작업만 수행한 경우                         |
| `ci`       | 배포 방식 수정 및 새로 추가 / 기존 배포 스크립트 수정            |

## 🐬 Git Flow
***
`main`: 출시 가능한 프로덕션 코드의 브랜치

`dev`: 다음 버전을 개발하는 브랜치

`feature`: 기능을 개발하는 브랜치

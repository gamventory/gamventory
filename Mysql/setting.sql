/*gamventory database 생성*/ 
create database gamventory;

/*
사용자 생성 
@localhost는 로컬에서만 접속가능
identified by : 비밀번호
*/
create user 'gamventory.dev'@'localhost' identified by 'dev1234';
/*
gamventory.dev에게 모든 권한을 위임 * 애스터리크는 db안의 모든 테이블을 의미
*/
grant all privileges on test_db.* to 'gamventory.dev'@'localhost';
flush privileges;
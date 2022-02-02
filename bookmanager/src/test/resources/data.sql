--call next value for hibernate_sequence;  -- sequence 증가를 위한 사용
insert into user (`id`,`name`,`email`,`create_at`,`update_at`) values (1, 'yoojin', 'yoojin@naver.com', now(), now());


--call next value for hibernate_sequence;
insert into user (`id`,`name`,`email`,`create_at`,`update_at`) values (2, 'youngmi', 'yoounmi@naver.com', now(), now());


--call next value for hibernate_sequence;
insert into user (`id`,`name`,`email`,`create_at`,`update_at`) values (3, 'heesun', 'heesun@google.com', now(), now());

--call next value for hibernate_sequence;
insert into user (`id`,`name`,`email`,`create_at`,`update_at`) values (4, 'younseok', 'younseok@google.com', now(), now());


--call next value for hibernate_sequence;
insert into user (`id`,`name`,`email`,`create_at`,`update_at`) values (5, 'yoojin', 'yoojin@another.com', now(), now());


 insert into publisher (`id`,`name`) values (1,'yoojin pub');

 insert into book(`id`,`name`,`publisher_id`,`deleted`,`status`) values (1,'JPA TEST',1,false,100);
 insert into book(`id`,`name`,`publisher_id`,`deleted`,`status`) values (2,'SPRING TEST',1,false,200);
 insert into book(`id`,`name`,`publisher_id`,`deleted`,`status`) values (3,'All Test',1,true,100);







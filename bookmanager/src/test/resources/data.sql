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




insert into review(`id`,`title`,`content`,`score`,`user_id`,`book_id`) values (1,'내 인생 바꾼책','너무좋았음',5.0,1,1);
insert into review(`id`,`title`,`content`,`score`,`user_id`,`book_id`) values (2,'너무 진도 빠름','조금 별로',3.0,2,2);


insert into comment(`id`,`comment`,`review_id`) values  ( 1,'저도 좋았어요',1);
insert into comment(`id`,`comment`,`review_id`) values  ( 2,'저는 별로',1);
insert into comment(`id`,`comment`,`review_id`) values  ( 3,'저도 그냥 그랬어요',2);




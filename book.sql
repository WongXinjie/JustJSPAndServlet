drop database if exists bookstores;
create database bookstores default charset=utf8;
use bookstores;

drop table if exists category;
create table category
(
	category_id int not null auto_increment,
	name varchar(50) not null unique,
	primary key (category_id)
);

drop table if exists book;
create table book
(
	book_id int not null auto_increment,
	title varchar(100),
	ISBN varchar(20) not null unique,
	publisher varchar(50),
	published_date date not null,
	description varchar(500),
	cover varchar(100),
	price decimal(10, 2),
	category_id int,
	primary key (book_id)
);

drop table if exists address;
create table address
(
	address_id int not null auto_increment,
	country varchar(50) not null,
	province varchar(50) not null,
	city varchar(50) not null,
	district varchar(50),
	street varchar(50),
	zip varchar(6),
	primary key (address_id)
);

drop table if exists book_user;
create table book_user
(
	book_user_id int not null auto_increment,
	book_user_name varchar(50) not null unique,
	email varchar(50) not null unique,
	password varchar(10) not null,
	address_id int not null, 
	registered_time datetime not null,
	last_login_time datetime not null,
	primary key (book_user_id)
);
/*
delimiter |
create trigger default_datetime
before insert on book_user
begin
	for each row
		if new.registered_time is null then
			set new.registered_time = now();
		end if; 
end; |
delimiter;
*/

drop table if exists manager;
create table manager
(
	manager_id int not null auto_increment,
	manager_name varchar(50) not null unique,
	email varchar(50) not null unique,
	permission tinyint(1) not null default 1,
	create_time datetime not null,
	last_login_time datetime not null,	
	primary key  (manager_id)
);
/*
delimiter |
create trigger default_create_time 
before insert on manager
begin
	for each row
		if new.create_time is null then
			new.create_time = now();
		end if; 
end; |
delimiter;
*/

drop table if exists book_order;
create table book_order
(
	book_order_id int not null auto_increment,
	book_user_id int not null,
	address_id int not null,
	amount decimal(10, 2),
	status tinyint(1),
	order_time datetime not null,
	pay_date datetime not null,
	primary key (book_order_id)
);
/*
delimiter |
create trigger default_order_time 
before insert on order
begin
	for each row 
		if new.order_time is null then
			set new.order_time = now();
		end if; 
end; |
delimiter;
*/

drop table if exists order_books;
create table order_books
(
	order_books_id int not null auto_increment,
	book_id int not null,
	order_id int not null,
	count int not null,
	primary key (order_books_id)
);

drop table if exists collection;
create table collection
(
	collection_id int not null auto_increment,
	book_user_id int not null,
	book_id int not null,
	add_time datetime not null, 
	primary key (collection_id)
);
/*
delimiter |
create trigger default_add_time
before insert on collection
begin 
	for each row
		if new.add_time is null then 
			set new.add_time = now();
		end if; 
end; | 
delimiter;
*/

drop table if exists book_user_comment;
create table book_user_comment
(
	book_user_comment_id int not null auto_increment,
	book_user_id int not null,
	book_id int not null,
	content varchar(1000) not null,
	comment_time datetime not null,
	update_time datetime not null,
	primary key (book_user_comment_id)
);
/*
delimiter | 
create trigger default_comment_time
before insert on book_user_comment
begin
	for each row
		if new.comment_time is null then
			set new.comment_time = now();
		end if; 
end; | 
delimiter;
*/



	
	



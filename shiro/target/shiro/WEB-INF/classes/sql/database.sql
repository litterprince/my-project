create table sys_user(
	id varchar(36) primary key,
	user_code varchar(32),
	user_name varchar(64),
	password varchar(32),
	salt varchar(64),
	locked char(1),
	create_time datetime
)

create table sys_role(
	id varchar(36) primary key,
	name varchar(128),
	available char(1)
)

create table sys_permission(
	id bigint(20) primary key,
	name varchar(128),
	type varchar(32),
	url varchar(128),
	percode varchar(128),
	parent_id bigint(20),
	parent_ids bigint(128),
	sort_string varchar(128),
	available char(1)
)

create table sys_user_role(
	id varchar(36),
	sys_user_id varchar(36),
	sys_role_id varchar(36)
)

create table sys_role_permission(
	id varchar(36),
	sys_role_id varchar(36),
	sys_permission_id varchar(36)
)
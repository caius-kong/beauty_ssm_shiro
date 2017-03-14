
-- 需要 MySQL 5.6.5以上的版本
CREATE DATABASE beauty_ssm;
USE beauty_ssm;

drop table if exists _user;
drop table if exists _organization;
drop table if exists _resource;
drop table if exists _role;

create table _user (
  id bigint auto_increment,
  username varchar(100),
  password varchar(100),
  salt varchar(100),
  role_ids varchar(100),
  locked bool default false,
  constraint pk_user primary key(id)
) charset=utf8 ENGINE=InnoDB;
create unique index idx_user_username on _user(username);

create table _role (
  id bigint auto_increment,
  role varchar(100),
  description varchar(100),
  resource_ids varchar(100),
  available bool default false,
  constraint pk_role primary key(id)
) charset=utf8 ENGINE=InnoDB;
create index idx_role_resource_ids on _role(resource_ids);

create table _resource (
  id bigint auto_increment,
  name varchar(100),
  type varchar(50),
  url varchar(200),
  parent_id bigint,
  parent_ids varchar(100),
  permission varchar(100),
  available bool default false,
  constraint pk_resource primary key(id)
) charset=utf8 ENGINE=InnoDB;
create index idx_resource_parent_id on _resource(parent_id);
create index idx_resource_parent_ids on _resource(parent_ids);

create table _urlFilter (
  id bigint auto_increment,
  name varchar(100),
  url varchar(100),
  roles varchar(100),
  permissions varchar(100),
  constraint pk_urlFilter primary key(id)
) charset=utf8 ENGINE=InnoDB;
create unique index idx_urlFilter_url on _urlFilter(url);
	
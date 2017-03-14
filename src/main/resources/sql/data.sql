DELIMITER ;
delete from _user;
delete from _role;
delete from _resource;
delete from _organization;

insert into _user values(1,'admin','d3c59d25033dbf980d29554025c23a75','8d78869f470951332959580424d4bf4f', '1', false);

insert into _role values(1, 'admin', '超级管理员', '11,21,31,41', true);

insert into _resource values(1, '资源', 'menu', '', 0, '0/', '', true);

insert into _resource values(21, '用户管理', 'menu', '/user', 1, '0/1/', 'user:*', true);
insert into _resource values(22, '用户新增', 'button', '', 21, '0/1/21/', 'user:create', true);
insert into _resource values(23, '用户修改', 'button', '', 21, '0/1/21/', 'user:update', true);
insert into _resource values(24, '用户删除', 'button', '', 21, '0/1/21/', 'user:delete', true);
insert into _resource values(25, '用户查看', 'button', '', 21, '0/1/21/', 'user:view', true);

insert into _resource values(31, '资源管理', 'menu', '/resource', 1, '0/1/', 'resource:*', true);
insert into _resource values(32, '资源新增', 'button', '', 31, '0/1/31/', 'resource:create', true);
insert into _resource values(33, '资源修改', 'button', '', 31, '0/1/31/', 'resource:update', true);
insert into _resource values(34, '资源删除', 'button', '', 31, '0/1/31/', 'resource:delete', true);
insert into _resource values(35, '资源查看', 'button', '', 31, '0/1/31/', 'resource:view', true);

insert into _resource values(41, '角色管理', 'menu', '/role', 1, '0/1/', 'role:*', true);
insert into _resource values(42, '角色新增', 'button', '', 41, '0/1/41/', 'role:create', true);
insert into _resource values(43, '角色修改', 'button', '', 41, '0/1/41/', 'role:update', true);
insert into _resource values(44, '角色删除', 'button', '', 41, '0/1/41/', 'role:delete', true);
insert into _resource values(45, '角色查看', 'button', '', 41, '0/1/41/', 'role:view', true);

insert into _resource values(51, 'URL管理', 'menu', '/urlFilter', 1, '0/1/', 'urlFilter:*', true);
insert into _resource values(52, 'URL新增', 'button', '', 51, '0/1/51/', 'urlFilter:create', true);
insert into _resource values(53, 'URL修改', 'button', '', 51, '0/1/51/', 'urlFilter:update', true);
insert into _resource values(54, 'URL删除', 'button', '', 51, '0/1/51/', 'urlFilter:delete', true);
insert into _resource values(55, 'URL查看', 'button', '', 51, '0/1/51/', 'urlFilter:view', true);

INSERT INTO `_urlFilter` (`id`, `name`, `url`, `roles`, `permissions`)
VALUES
	(1, '用户管理', '/user', '', 'user:view'),
	(2, '用户新增', '/user/*/create', 'admin', ''),
	(3, '用户修改', '/user/*/update', 'admin', ''),
	(4, '用户删除', '/user/*/delete', 'admin', ''),
	(5, '用户密码修改', '/user/*/changePassword', 'guest', ''),
	(6, '用户查看', '/user/findUsers', '', 'user:view');




drop table if exists tt_user;
create table tt_user
(
   id                   bigint not null comment 'ID',
   name                 varchar(100) comment '用户名称',
   primary key (id)
);
alter table tt_user comment '用户';

drop table if exists tt_user_role;
create table tt_user_role
(
   id                   bigint not null comment 'ID',
   user_id              bigint comment '用户ID',
   role_id              bigint comment '角色ID',
   primary key (id)
);
alter table tt_user_role comment '用户-角色';

drop table if exists tt_role;
create table tt_role
(
   id                   bigint not null comment 'ID',
   name                 varchar(100) comment '角色名称',
   primary key (id)
);
alter table tt_role comment '角色';

drop table if exists tt_role_permission;
create table tt_role_permission
(
   id                   bigint not null comment 'ID',
   role_id              bigint comment '角色ID',
   permission_id        bigint comment '权限ID'
);
alter table tt_role_permission comment '角色-权限';

drop table if exists tt_permission;
create table tt_permission
(
   id                   bigint not null comment 'ID',
   name                 varchar(100) comment '权限名称',
   primary key (id)
);
alter table tt_permission comment '权限';




-- 数据 TODO


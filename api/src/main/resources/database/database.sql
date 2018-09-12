create schema blog;
use blog;

create table blog
(
  id            int auto_increment
  comment '博客id'
    primary key,
  create_time   timestamp       not null
  comment '博客发表时间',
  read_count    int default '0' not null
  comment '阅读量',
  user_id       varchar(32)     not null
  comment '博主的用户id',
  title         varchar(20)     not null
  comment '博客标题',
  content       text            not null
  comment '博客正文',
  like_count    int default '0' not null
  comment '点赞数量',
  comment_count int default '0' not null
  comment '评论数目',
  constraint create_time_key
  unique (create_time),
  constraint user_id_create_time_key
  unique (user_id, create_time),
  constraint blog_owner_fk
  foreign key (user_id) references user (id)
)
  comment '博客相关信息';

create table comment
(
  id               int auto_increment
    primary key,
  user_id          varchar(32)                         not null
  comment '发表评论用户id',
  attached_user_id varchar(32)                         null
  comment '被评论用户id',
  create_time      timestamp default CURRENT_TIMESTAMP not null
  comment '评论时间',
  blog_id          int                                 not null
  comment '关联博客id
',
  content          text                                not null
  comment '评论内容',
  constraint comment_user_id_blog_id_pk
  unique (user_id, blog_id),
  constraint comment_blog_id_pk
  unique (blog_id),
  constraint comment_user_id_fk
  foreign key (user_id) references user (id),
  constraint comment_blog_id_fk
  foreign key (blog_id) references blog (id)
);
  comment '评论相关信息';




create table user
(
  id          varchar(32)     not null
  comment '自增id'
    primary key,
  user_name   varchar(10)     not null
  comment '用户名',
  email       varchar(20)     not null
  comment '关联邮箱  通过邮箱登录',
  fans        int default '0' not null
  comment '粉丝数量',
  blogs       int default '0' not null
  comment '博客数量',
  create_time timestamp       not null
  comment '用户创建时间
',
  passwd      varchar(32)     not null
  comment 'MD5加密后的密码',
  constraint email_user_name_key
  unique (email, user_name),
  constraint user_email_uindex
  unique (email)
)
  comment '存储博客用户相关信息';


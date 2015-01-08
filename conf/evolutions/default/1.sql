# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table CATEGORY (
  CATEGORY_ID               bigint auto_increment not null,
  CATEGORY_NAME             varchar(32) not null,
  constraint uq_CATEGORY_CATEGORY_NAME unique (CATEGORY_NAME),
  constraint pk_CATEGORY primary key (CATEGORY_ID))
;

create table GAME (
  GAME_ID                   bigint auto_increment not null,
  GAME_NAME                 varchar(128) not null,
  DETAILS_URL               varchar(255),
  IMAGE_URL                 varchar(255),
  CATEGORY_ID               bigint not null,
  OWNED                     tinyint(1) default 0 not null,
  CREATED                   datetime,
  constraint uq_GAME_GAME_NAME unique (GAME_NAME),
  constraint pk_GAME primary key (GAME_ID))
;

create table VOTES (
  VOTE_ID                   bigint auto_increment not null,
  GAME_ID                   bigint not null,
  VOTE_CREATED              datetime,
  constraint pk_VOTES primary key (VOTE_ID))
;

alter table GAME add constraint fk_GAME_category_1 foreign key (CATEGORY_ID) references CATEGORY (CATEGORY_ID) on delete restrict on update restrict;
create index ix_GAME_category_1 on GAME (CATEGORY_ID);
alter table VOTES add constraint fk_VOTES_game_2 foreign key (GAME_ID) references GAME (GAME_ID) on delete restrict on update restrict;
create index ix_VOTES_game_2 on VOTES (GAME_ID);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table CATEGORY;

drop table GAME;

drop table VOTES;

SET FOREIGN_KEY_CHECKS=1;


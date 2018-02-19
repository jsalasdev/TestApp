# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table centers (
  idcompanies                   integer auto_increment not null,
  name                          varchar(255),
  centernumber                  varchar(255),
  latitude                      double,
  longitude                     double,
  country                       varchar(255),
  constraint pk_centers primary key (idcompanies)
);

create table companies (
  idcompanies                   integer auto_increment not null,
  name                          varchar(255),
  companynumber                 varchar(255),
  latitude                      double,
  longitude                     double,
  country                       varchar(255),
  constraint pk_companies primary key (idcompanies)
);

create table landing_user (
  id                            integer auto_increment not null,
  name                          varchar(255),
  latitude                      double,
  longitude                     double,
  language                      varchar(255),
  id_sector                     integer,
  id_subsector                  integer,
  id_profession                 integer,
  email                         varchar(255),
  type_user                     varchar(255),
  telephone                     varchar(255),
  surname                       varchar(255),
  constraint pk_landing_user primary key (id)
);

create table prof_sector (
  id_profession                 integer auto_increment not null,
  id_sector                     integer,
  constraint pk_prof_sector primary key (id_profession)
);

create table profession (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_profession primary key (id)
);

create table sector (
  id                            integer auto_increment not null,
  name                          varchar(255),
  url                           varchar(255),
  id_sector                     integer,
  constraint pk_sector primary key (id)
);

create table users (
  idusers                       integer auto_increment not null,
  name                          varchar(255),
  surname                       varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  latitude                      double,
  longitude                     double,
  country                       varchar(255),
  constraint pk_users primary key (idusers)
);


# --- !Downs

drop table if exists centers;

drop table if exists companies;

drop table if exists landing_user;

drop table if exists prof_sector;

drop table if exists profession;

drop table if exists sector;

drop table if exists users;


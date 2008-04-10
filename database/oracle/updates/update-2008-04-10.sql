create table lst_lengthofhomeless
(
  code         VARCHAR2(1) not null,
  description  VARCHAR2(80),
  isactive     NUMBER(1),
  displayorder NUMBER,
  primary key(code)
)


create table lst_reasonforhomeless
(
  code         VARCHAR2(1) not null,
  description  VARCHAR2(80),
  isactive     NUMBER(1),
  displayorder NUMBER,
  primary key(code)
)

create table lst_sourceincome
(
  code         VARCHAR2(1) not null,
  description  VARCHAR2(80),
  isactive     NUMBER(1),
  displayorder NUMBER,
  primary key(code)
)


create table lst_livedbefore
(
  code         VARCHAR2(1) not null,
  description  VARCHAR2(80),
  isactive     NUMBER(1),
  displayorder NUMBER,
  primary key(code)
)


create table lst_statusincanada
(
  code         VARCHAR2(1) not null,
  description  VARCHAR2(80),
  isactive     NUMBER(1),
  displayorder NUMBER,
  primary key(code)
)

create table lst_reasonnoadmit
(
  code         VARCHAR2(1) not null,
  description  VARCHAR2(80),
  isactive     NUMBER(1),
  displayorder NUMBER,
  primary key(code)
)


create table lst_cursleeparrangement
(
  code         VARCHAR2(1) not null,
  description  VARCHAR2(80),
  isactive     NUMBER(1),
  displayorder NUMBER,
  primary key(code)
)

create table lst_aboriginal
(
  code         VARCHAR2(1) not null,
  description  VARCHAR2(80),
  isactive     NUMBER(1),
  displayorder NUMBER,
  primary key(code)
)

create table lst_referredto{
  code         VARCHAR2(1) not null,
  description  VARCHAR2(80),
  isactive     NUMBER(1),
  displayorder NUMBER,
  primary key(code)
}

create table lst_referredby{
  code         VARCHAR2(1) not null,
  description  VARCHAR2(80),
  isactive     NUMBER(1),
  displayorder NUMBER,
  primary key(code)
}
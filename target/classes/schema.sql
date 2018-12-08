-- create sequence
-- create sequence if not exists seq_transaction start with 1 increment by 1 maxvalue 99999999;

-- create table transaction
create table if not exists transaction ( 
   id int auto_increment primary key,
   username varchar(10) not null,
   userid varchar(10) not null,
   transaction_date varchar(20) not null,
   transaction_amount decimal(10,2) not null,
   transaction_iof decimal(10,2) not null,
   status int not null default 0
);

-- create table statistic
create table if not exists statistic ( 
   id int auto_increment primary key,
   username varchar(10) not null,
   sumvalue varchar(20) not null
);
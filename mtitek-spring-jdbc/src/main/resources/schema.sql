create table if not exists AppUser (
  id identity,
  name varchar(10) not null
);
 
create table if not exists AppProfile (
  id varchar(1) not null PRIMARY KEY,
  name varchar(20) not null,
  role varchar(10) not null
);

create table if not exists AppUserProfile (
  appUserId bigint not null,
  appProfileId varchar(1) not null
); 
 
alter table AppUserProfile add foreign key (appUserId) references AppUser(id);
alter table AppUserProfile add foreign key (appProfileId) references AppProfile(id);
--ALTER DATABASE HandyEnglish DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

create table test(
	lp int NOT NULL AUTO_INCREMENT primary key,
	string varchar(128),
	data timestamp not null default current_timestamp
)

--ALTER TABLE test DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

SELECT * FROM test;

insert into test (string) values('za¿ó³æ gêœ³¹ jaŸñ');
insert into test (string) values('abc');

-- Schema tables
create table profiles(
	id int NOT NULL AUTO_INCREMENT primary key,
	name text not null,
	pass_hash text not null,
	uptd timestamp not null default current_timestamp on update current_timestamp,
	crtd timestamp not null default current_timestamp
);
create unique index uq_profiles_names on profiles (name(256));
create table words(
	id int NOT NULL AUTO_INCREMENT primary key,
	category int,
	word text not null,
	eqiv text not null,
	defn text,
	exmp text,
	prof int not null,
	crtd timestamp not null default current_timestamp,
foreign key fk_words_prof(prof) references profiles(id)
);
create unique index uq_words_values on words (word(128), eqiv(128), prof);
create index idx_words_word on words (word(512));
create index idx_words_eqiv on words (eqiv(512));
create table categories(
	id int NOT NULL AUTO_INCREMENT primary key,
	category text not null,
	prof int not null,
	crtd timestamp not null default current_timestamp,
foreign key fk_categories_prof(prof) references profiles(id)
);
create unique index uq_categories_values on categories(category(512), prof);
insert into profiles(name, pass_hash) values('5ba73291-a0d2-412f-891a-0665f99cb10f','DEFAULT USER ACCOUNT');
--insert into categories(category, prof) values('ogólne', (SELECT id FROM profiles where name='5ba73291-a0d2-412f-891a-0665f99cb10f'));

drop table words;
drop table categories;
drop table profiles;

SELECT * FROM profiles;
SELECT * FROM words;
SELECT * FROM categories;
SELECT ID FROM profiles where name='5ba73291-a0d2-412f-891a-0665f99cb10f';


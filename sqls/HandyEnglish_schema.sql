--ALTER DATABASE HandyEnglish DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

create table test(
	lp int NOT NULL AUTO_INCREMENT primary key,
	string varchar(128),
	data timestamp not null default current_timestamp
)

--ALTER TABLE test DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

SELECT * FROM test;

insert into test (string) values('za��� gꜳ� ja��');
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
	topic int,
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
create index idx_words_catProf on words (category, prof);
create index idx_words_topicProf on words (topic, prof);
create table categories(
	id int NOT NULL AUTO_INCREMENT primary key,
	category text not null,
	prof int not null,
	crtd timestamp not null default current_timestamp,
foreign key fk_categories_prof(prof) references profiles(id)
);
create unique index uq_categories_values on categories(category(512), prof);
create table topics(
	id int NOT NULL AUTO_INCREMENT primary key,
	topic text not null,
	prof int not null,
	crtd timestamp not null default current_timestamp,
foreign key fk_topics_prof(prof) references profiles(id)
);
create unique index uq_topics_values on topics(topic(512), prof);
create table testSummary(
	id int NOT NULL AUTO_INCREMENT primary key,
	testType text not null,
	category int,
	topic int,
	overall int,
	goodAns int,
	duration int,
	prof int not null,
	crtd timestamp not null default current_timestamp,
foreign key fk_testsSum_prof(prof) references profiles(id)	
);
--create unique index uq_testsSum_values on testSummary(testType(512), prof);
create table grama_rules(
	id int NOT NULL AUTO_INCREMENT primary key,
	grama_categ int,
	titl text not null,
	defn text not null,
	exmp text,
	prof int not null,
	crtd timestamp not null default current_timestamp,
foreign key fk_grules_prof(prof) references profiles(id)
);
create unique index uq_grules_values on grama_rules (titl(128), defn(128), prof);
create index idx_grules_titl on grama_rules (titl(512));
create index idx_grules_defn on grama_rules (defn(512));
create index idx_grules_catProf on grama_rules (grama_categ, prof);
create table grama_categories(
	id int NOT NULL AUTO_INCREMENT primary key,
	grama_categ text not null,
	prof int not null,
	crtd timestamp not null default current_timestamp,
foreign key fk_gcategories_prof(prof) references profiles(id)
);
create unique index uq_gcategories_values on grama_categories(grama_categ(512), prof);
insert into profiles(name, pass_hash) values('5ba73291-a0d2-412f-891a-0665f99cb10f','DEFAULT USER ACCOUNT');
insert into categories(category, prof) values('...', (SELECT id FROM profiles where name='5ba73291-a0d2-412f-891a-0665f99cb10f'));
insert into topics(topic, prof) values('...', (SELECT id FROM profiles where name='5ba73291-a0d2-412f-891a-0665f99cb10f'));
insert into grama_categories(grama_categ, prof) values('...', (SELECT id FROM profiles where name='5ba73291-a0d2-412f-891a-0665f99cb10f'));





drop table words;
drop table grama_rules;
drop table categories;
drop table grama_categories;
drop table testSummary;
drop table profiles;

SELECT * FROM profiles;

SELECT * FROM words;

SELECT * FROM categories;

SELECT * FROM topics;

SELECT ID FROM profiles where name='5ba73291-a0d2-412f-891a-0665f99cb10f';

SELECT * FROM testSummary;


update profiles set name='test@test' where id=2;
SELECT * FROM grama_rules;

SELECT * FROM grama_categories;

delete from grama_rules where id =3;


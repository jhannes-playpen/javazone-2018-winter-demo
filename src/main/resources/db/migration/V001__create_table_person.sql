create table person (
	id varchar(50) primary key,
	given_name varchar(100),
	family_name varchar(100),
	date_of_birth date,
	updated_at timestamp not null,
	created_at timestamp not null
);

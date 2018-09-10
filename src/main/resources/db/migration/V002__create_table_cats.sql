create table cats (
	id uuid primary key,
	name varchar(100),
	date_of_birth date,
	updated_at timestamp not null,
	created_at timestamp not null
);

create table person (
	id uuid primary key,
	givenName varchar(100),
	familyName varchar(100),
	date_of_birth date,
	updated_at timestamp not null,
	created_at timestamp not null
);

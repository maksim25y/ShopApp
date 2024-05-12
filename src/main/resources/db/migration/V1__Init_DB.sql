create table if not exists person
				(
				    id serial primary key,
				    username  varchar(100) not null,
				    year_of_birth integer      not null,
				    email varchar,
					email_active  boolean default false,
				    password  varchar not null,
				    role varchar(100) not null
				);
create table if not exists item(
				    id serial primary key,
				    name varchar unique,
				    description varchar not null,
				    photo varchar not null,
				    user_id int default null references person(id) on delete set default
				);

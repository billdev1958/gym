CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS cat_role (
	id SERIAL PRIMARY KEY,
	name VARCHAR(70) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	name VARCHAR(100) NOT NULL,
	lastname1 VARCHAR(100) NOT NULL,
	lastname2 VARCHAR(100),
	genre CHAR(1) CHECK(genre IN ('M', 'F')) NOT NULL,
	role INTEGER NOT NULL,
	created_at TIMESTAMPTZ DEFAULT NOW(),	
	updated_at TIMESTAMPTZ,
	deleted_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS account (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	id_user UUID NOT NULL UNIQUE,
	email VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	created_at TIMESTAMPTZ DEFAULT NOW(),	
	updated_at TIMESTAMPTZ,
	deleted_at TIMESTAMPTZ 
);

CREATE TABLE IF NOT EXISTS cat_membership_type(
	id SERIAL PRIMARY KEY,
	name VARCHAR(155),
	description VARCHAR(255),
	duration_days INTEGER,
	cost NUMERIC(10, 2),
	created_at TIMESTAMPTZ DEFAULT NOW(),	
	updated_at TIMESTAMPTZ,
	deleted_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS client_membership (
	id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	id_user UUID NOT NULL UNIQUE,
	id_membership_type INTEGER,
	start_date DATE NOT NULL, 
	end_date DATE NOT NULL,
	created_at TIMESTAMPTZ DEFAULT NOW(),	
	updated_at TIMESTAMPTZ,
	deleted_at TIMESTAMPTZ
);

ALTER TABLE users
ADD CONSTRAINT fk_users_role
FOREIGN KEY (role) REFERENCES cat_role(id);

ALTER TABLE account
ADD CONSTRAINT fk_account_user
FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE client_membership
ADD CONSTRAINT fk_membership_user
FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE client_membership
ADD CONSTRAINT fk_cm_type
FOREIGN KEY (id_membership_type) REFERENCES cat_membership_type(id);

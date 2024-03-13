-- this script is created for imitating DB in tests

CREATE TABLE IF NOT EXISTS "user"
(
    id bigint NOT NULL,
    creation_time timestamp(6) without time zone,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    username character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_sb8bbouer5wak8vyiiy4pf2bx UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS role
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id),
    CONSTRAINT role_name_check CHECK (name::text = ANY (ARRAY['ROLE_ADMIN'::character varying, 'ROLE_POSTS_VIEWER'::character varying, 'ROLE_POSTS_EDITOR'::character varying, 'ROLE_USERS_VIEWER'::character varying, 'ROLE_USERS_EDITOR'::character varying, 'ROLE_ALBUMS_VIEWER'::character varying, 'ROLE_ALBUMS_EDITOR'::character varying]::text[]))
    );


CREATE TABLE IF NOT EXISTS user_role
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY (role_id)
    REFERENCES role (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkfgsgxvihks805qcq8sq26ab7c FOREIGN KEY (user_id)
    REFERENCES "user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

CREATE TABLE IF NOT EXISTS resource_type
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT resource_type_pkey PRIMARY KEY (id),
    CONSTRAINT resource_type_name_check CHECK (name::text = ANY (ARRAY['POSTS'::character varying, 'USERS'::character varying, 'ALBUMS'::character varying]::text[]))
    );

CREATE TABLE IF NOT EXISTS audit
(
    id bigint NOT NULL,
    creation_time timestamp(6) without time zone,
    has_access boolean NOT NULL,
    request_method character varying(255) COLLATE pg_catalog."default",
    request_params character varying(255) COLLATE pg_catalog."default",
    user_id bigint,
    resource_type_id bigint,
    CONSTRAINT audit_pkey PRIMARY KEY (id),
    CONSTRAINT fk2wews7n4qwddlbq0jfuy13o9u FOREIGN KEY (resource_type_id)
    REFERENCES resource_type (id) MATCH SIMPLE
                               ON UPDATE NO ACTION
                               ON DELETE NO ACTION,
    CONSTRAINT audit_request_method_check CHECK (request_method::text = ANY (ARRAY['GET'::character varying, 'POST'::character varying, 'PUT'::character varying, 'DELETE'::character varying]::text[]))
);



create sequence db_model_SEQ start with 1 increment by 50;

create table db_model (
    id bigint not null,
    name varchar(255),
    primary key (id)
);

INSERT INTO db_model (id, name)
VALUES
    (1, 'name_a'),
    (2, 'name_b');
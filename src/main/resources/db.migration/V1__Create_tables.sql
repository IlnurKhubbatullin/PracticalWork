create table if not exists document (
    id bigserial not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    doc_title smallint check (doc_title between 0 and 4),
    number varchar(255),
    template_id bigint unique,
    file_id bigint unique,
    removed boolean not null,
    primary key (id));

create table if not exists doc_template (
    id bigserial not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    doc_title smallint check (doc_title between 0 and 4),
    title varchar(255),
    version varchar(255),
    removed boolean not null,
    primary key (id));

create table if not exists doc_field (
    id bigserial not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    name varchar(255),
    type varchar(255),
    placeholder varchar(255),
    default_value varchar(255),
    document_id bigint,
    removed boolean not null,
    primary key (id));

create table if not exists doc_related (
    id bigserial not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    related_id bigint,
    removed boolean not null,
    primary key (id));

create table if not exists doc_file (
    id bigserial not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    name varchar(255),
    store varchar(255),
    size bigint,
    mimetype varchar(255) check (mimetype in ('ZIP','PDF','WORD','EXCEL')),
    description varchar(255),
    removed boolean not null,
    primary key (id));

create table if not exists contractor (
    id bigserial not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    first_name varchar(255),
    patronymic varchar(255),
    last_name varchar(255),
    country varchar(255),
    phone varchar(255),
    email varchar(255),
    telegram varchar(255),
    removed boolean not null,
    primary key (id));

create table if not exists credential (
    id bigserial not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    type_of_contractor varchar(255) check (type_of_contractor in ('INDIVIDUAL','ENTITY')),
    version varchar(255),
    text varchar(255),
    removed boolean not null,
    primary key (id));

create table if not exists comment (
    id bigserial not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    text varchar(255),
    comment_id bigint,
    removed boolean not null,
    primary key (id));

alter table if exists comment add constraint fk_comment foreign key (comment_id) references contractor;
alter table if exists doc_field add constraint fk_doc_field foreign key (document_id) references document;
alter table if exists document add constraint fk_document foreign key (file_id) references doc_file;
alter table if exists document add constraint fk_document foreign key (template_id) references doc_template;
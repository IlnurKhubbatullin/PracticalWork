create table schema_system.document (
    id bigserial not null,
    created_at timestamp,
    doc_title smallint,
    number varchar(255),
    removed boolean not null,
    updated_at timestamp,
    file_id bigint,
    template_id bigint,
    primary key (id));
create table schema_system.template (
    id bigserial not null,
    created_at timestamp,
    doc_title smallint,
    removed boolean not null,
    title varchar(255),
    updated_at timestamp,
    version varchar(255),
    primary key (id));
create table schema_system.field (
    id bigserial not null,
    created_at timestamp,
    default_value varchar(2047),
    name varchar(255),
    placeholder varchar(255),
    removed boolean not null,
    type varchar(255),
    updated_at timestamp,
    document_id bigint,
    template_id bigint,
    primary key (id));
create table schema_system.related (
    id bigserial not null,
    created_at timestamp,
    related_id bigint,
    removed boolean,
    updated_at timestamp,
    document_id bigint, primary key (id));

create table schema_system.comment (
    id bigserial not null,
    created_at timestamp,
    removed boolean not null,
    text varchar(255),
    contactor_id bigint,
    primary key (id));
create table schema_system.contractor (
    id bigserial not null,
    country varchar(255),
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    patronymic varchar(255),
    phone varchar(255),
    removed boolean not null,
    telegram varchar(255),
    credential_id bigint,
    primary key (id));
create table schema_system.contractor_documents (
    contractors_id bigint not null,
    documents_id bigint not null);
create table schema_system.credential (
    id bigserial not null,
    created_at timestamp,
    removed boolean not null,
    text varchar(255),
    type_of_contractor smallint,
    version varchar(255),
    primary key (id));
create table schema_system.doc_file (
    id bigserial not null,
    description varchar(255),
    mimetype smallint,
    name varchar(255),
    removed boolean not null,
    size bigint,
    store varchar(255),
    user_id bigint,
    primary key (id));
create table schema_system.users (
    id bigserial not null,
    created_at timestamp,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    last_visit_at timestamp,
    login varchar(255),
    password varchar(255),
    patronymic varchar(255),
    removed boolean not null,
    role varchar(255),
    telegram varchar(255),
    updated_at timestamp,
    primary key (id));

alter table if exists schema_system.comment add constraint FKrodr200o6xyaeaduxa11pehvl foreign key (contactor_id) references schema_system.contractor;
alter table if exists schema_system.contractor add constraint FKnt58l4w50g3d5w7dy0m3ngkma foreign key (credential_id) references schema_system.credential;
alter table if exists schema_system.contractor_documents add constraint FK83gdpc6sof0024gkns0hn56it foreign key (documents_id) references schema_system.document;
alter table if exists schema_system.contractor_documents add constraint FK3tt44lw1ty7wngwiydirfkbg8 foreign key (contractors_id) references schema_system.contractor;
alter table if exists schema_system.doc_file add constraint FKt1r7a90l3cth54ov78gvcyos1 foreign key (user_id) references schema_system.users;
alter table if exists schema_system.document add constraint FKdi4epn0dyux92joqp5bj8lomo foreign key (file_id) references schema_system.doc_file;
alter table if exists schema_system.document add constraint FKeijg5cw28im526wmdis62lyi2 foreign key (template_id) references schema_system.template;
alter table if exists schema_system.field add constraint FKlmrbf30ugggypq2ccwjf61k1f foreign key (document_id) references schema_system.document;
alter table if exists schema_system.field add constraint FKnjv4ikp4djrothsmynsha2y0s foreign key (template_id) references schema_system.template;
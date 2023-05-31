create table comment (
    id bigserial not null,
    created_at timestamp(6),
    removed boolean not null,
    text varchar(2048),
    contactor_id bigint,
    primary key (id));
create table contractor (
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
create table contractor_documents (
    contractors_id bigint not null,
    documents_id bigint not null);
create table credential (
    id bigserial not null,
    created_at timestamp(6),
    removed boolean not null,
    text varchar(255),
    type_of_contractor smallint,
    version varchar(255),
    primary key (id));
create table doc_file (
    id bigserial not null,
    description varchar(255),
    mimetype smallint,
    name varchar(255),
    removed boolean not null,
    size bigint,
    store varchar(255),
    user_id bigint,
    primary key (id));
create table document (
    id bigserial not null,
    created_at timestamp(6),
    doc_title smallint,
    number varchar(255),
    removed boolean not null,
    updated_at timestamp(6),
    file_id bigint,
    template_id bigint,
    primary key (id));
create table document_doc_related_list (
    document_id bigint not null,
    doc_related_list_id bigint not null);
create table document_fields (
    document_id bigint not null,
    fields_id bigint not null);
create table fields (
    id bigserial not null,
    created_at timestamp(6),
    default_value varchar(255),
    name varchar(255),
    placeholder varchar(255),
    removed boolean not null,
    type varchar(255),
    updated_at timestamp(6),
    primary key (id));
create table related (
    id bigserial not null,
    created_at timestamp(6),
    related_id bigint,
    removed boolean,
    updated_at timestamp(6),
    primary key (id));
create table templates (
    id bigserial not null,
    created_at timestamp(6),
    doc_title smallint,
    removed boolean not null,
    title varchar(255),
    updated_at timestamp(6),
    version varchar(255),
    primary key (id));
create table templates_fields (
    doc_template_id bigint not null,
    fields_id bigint not null);
create table users (
    id bigserial not null,
    created_at timestamp(6),
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    last_visit_at timestamp(6),
    login varchar(255),
    password varchar(255),
    patronymic varchar(255),
    removed boolean not null,
    role varchar(255),
    telegram varchar(255),
    updated_at timestamp(6),
    primary key (id));
alter table if exists document_doc_related_list add constraint UK_ninjjhm3ey186x2xuv1b4mew6 unique (doc_related_list_id);
alter table if exists document_fields add constraint UK_971p9oi5n6x7fvrdpnmlkqm36 unique (fields_id);
alter table if exists templates_fields add constraint UK_18u8ai0i3afcbd1b0ouchceit unique (fields_id);
alter table if exists comment add constraint FKrodr200o6xyaeaduxa11pehvl foreign key (contactor_id) references contractor;
alter table if exists contractor add constraint FKnt58l4w50g3d5w7dy0m3ngkma foreign key (credential_id) references credential;
alter table if exists contractor_documents add constraint FK83gdpc6sof0024gkns0hn56it foreign key (documents_id) references document;
alter table if exists contractor_documents add constraint FK3tt44lw1ty7wngwiydirfkbg8 foreign key (contractors_id) references contractor;
alter table if exists doc_file add constraint FKt1r7a90l3cth54ov78gvcyos1 foreign key (user_id) references users;
alter table if exists document add constraint FKdi4epn0dyux92joqp5bj8lomo foreign key (file_id) references doc_file;
alter table if exists document add constraint FKaid8j4egalnthle0w9wimm36v foreign key (template_id) references templates;
alter table if exists document_doc_related_list add constraint FKgdl273secsi10eja46bp2idmk foreign key (doc_related_list_id) references related;
alter table if exists document_doc_related_list add constraint FKqu9ycyn5xsncb3t1t2rlps4fr foreign key (document_id) references document;
alter table if exists document_fields add constraint FKmeg6sp8d0pmxab78da4v2ojm8 foreign key (fields_id) references fields;
alter table if exists document_fields add constraint FKaa8af1t54b6ea1p4vgi5bq9d4 foreign key (document_id) references document;
alter table if exists templates_fields add constraint FK685a33fpar58irsgi5xqa7ohy foreign key (fields_id) references fields;
alter table if exists templates_fields add constraint FK8qc78cb9hkjomqy3b90fe39gy foreign key (doc_template_id) references templates;
insert into schema_system.field (default_value,
                                 name, placeholder,
                                 removed, type, created_at, updated_at)
values ('The value of deleted field',
        'Header of contract', 'Head part of contract',
        true, 'Contract field', now(), now());

insert into schema_system.template (doc_title, removed, title, version, created_at, updated_at)
values (0, true, 'Template for simple contracts deleted', 'V1.0', now(), now());

insert into schema_system.document (doc_title, number, removed, created_at, updated_at)
values (0, '000456', true, now(), now());
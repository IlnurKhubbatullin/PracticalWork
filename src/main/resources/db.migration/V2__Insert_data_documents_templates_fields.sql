insert into schema_system.template (doc_title, removed, title, version, created_at, updated_at)
values (0, false, 'Template for simple contracts', 'V1.0', now(), now());

insert into schema_system.document (created_at, doc_title, number, removed, updated_at, file_id, template_id)
values (now(), 0, '90119', false, now(), null, 1);


insert into schema_system.field (created_at, default_value,
                                 name, placeholder,
                                 removed, type, updated_at, document_id, template_id)
values (now(), 'Общество с ограниченной ответственностью _______, в дальнейшем именуемое «Заказчик», в лице генерального директора _______, действующего на основании Устава, с одной стороны, и Индивидуальный предприниматель _______, именуемый в дальнейшем «Исполнитель», в лице _______, действующего на основании Свидетельства о государственной регистрации физического лица в качестве индивидуального предпринимателя (№ 002060575 от 17.05.2016 года, ОГРНИП 316332800074701), с другой стороны, далее именуемые «Стороны», заключили настоящий Договор о нижеследующем:',
'Header of contract', 'Information about the customer and contractor',
false, 'Contract field', now(), 1, 1);

insert into schema_system.field (created_at, default_value,
                                 name, placeholder,
                                 removed, type, updated_at, document_id, template_id)
values (now(), '1. ПРЕДМЕТ ДОГОВОРА',
        'Body of contract', 'Subtitle of division',
        false, 'Contract field', now(), 1, 1);

insert into schema_system.field (created_at, default_value, name, placeholder,
                                 removed, type, updated_at, document_id, template_id)
values (now(), '1.1 Заказчик поручает, а Исполнитель принимает на себя обязательства оказать услуги, указанные в Приложениях к настоящему Договору. Заказчик обязуется принять и оплатить эти услуги.1.2 Период оказания услуг, предусмотренных настоящим Договором, определен Приложениями к настоящему Договору (в дальнейшем именуется "Период оказания Услуг").1.3 Для осуществления данной деятельности Исполнитель использует свой собственный персонал, а также, в случае необходимости, нанимает дополнительный персонал или привлекает к сотрудничеству третьих лиц, оставаясь при этом ответственным за их действия.',
'Body of contract', 'Content of the body',
false, 'Contract field', now(), 1, 1);


insert into schema_system.field (created_at, default_value, name, placeholder,
                                 removed, type, updated_at, document_id, template_id)
values (now(), 'Заказчик:
ООО «_______»
ИНН /КПП 7715935894/771501001
Адрес:_______
Банковские реквизиты:
ПАО «Сбербанк» г. Москва
Р/с: 40702810938000114942
БИК: 044525225
К/с 30101810400000000225',
'Footer of contract', 'Customer information',
false, 'Contract field', now(), 1, 1);


insert into schema_system.field (created_at, default_value, name, placeholder,
                                 removed, type, updated_at, document_id, template_id)
values (now(), 'Исполнитель:
ИП _______
ИНН 332801207689
ОГРНИП 316332800074701
Адрес: _______
Банковские реквизиты:
р/с: 40802810302010000172
в АО «АЛЬФА-БАНК» в г. Москва
к/с: 30101810200000000593
БИК: 044525593',
'Footer of contract', 'Executor information',
false, 'Contract field', now(), 1, 1);


insert into schema_system.field (created_at, default_value, name, placeholder,
                                 removed, type, updated_at, document_id, template_id)
values (now(), 'Генеральный директор
 ____________________ /Фамилия И.О./
МП',
'Footer of contract', 'Customer sign',
false, 'Contract field', now(), 1, 1);


insert into schema_system.field (created_at, default_value, name, placeholder,
                                 removed, type, updated_at, document_id, template_id)
values (now(), 'ИП Фамилия И.О.
 ____________________ /Фамилия И.О./
МП',
'Footer of contract', 'Executor sign',
false, 'Contract field', now(), 1, 1);






insert into schema_system.template (doc_title, removed, title, version, created_at, updated_at)
values (2, false, 'Template for simple application', 'V1.0', now(), now());

insert into schema_system.document (doc_title, number, removed, created_at, updated_at)
values (2, '66785', false, now(), now());

insert into schema_system.field (default_value,
                                 name, placeholder,
                                 removed, type, created_at, updated_at)
values ('Limited Liability Company, hereinafter referred to as the "Customer", represented by the General Director, acting on the basis of the Charter, on the one hand, and the Individual Entrepreneur, acting on the basis of State Registration Certificate No. 002060575 dated May 17, 2016, on the other hand, further referred to as the "Parties", have concluded this Annex as follows:',
        'Header of application', 'Information about the customer and contractor',
        false, 'Application field', now(), now());

insert into schema_system.field (default_value,
                                 name, placeholder,
                                 removed, type, created_at, updated_at)
values ('Application subject. The Contractor undertakes, on the instructions of the Customer, to provide services for the assembly of sections for the course "Rosbank Dom - it''s simple." The service includes the layout and publication of the course in the Articulate Storyline software. The Customer undertakes to accept and pay for the specified Services in accordance with the terms of this Annex. To provide services within the framework of this application, the Contractor has the right to involve third parties, while remaining responsible for their actions.',
        'Body of application', 'Sections of application: conditions, description of work, terms of payment and so on',
        false, 'Application field', now(), now());
insert into schema_system.field (default_value,
                                 name, placeholder,
                                 removed, type, created_at, updated_at)
values ('Final provisions. This Appendix comes into force from the date of its signing and is valid until the Parties fully fulfill their obligations under the Appendix.
This Appendix is an integral part of Agreement No. 90119 dated January 9, 2019. This Appendix is drawn up in Russian in two copies, having the same legal force, one copy for each of the Parties.',
        'Footer of application', 'Finish part of application',
        false, 'Application field', now(), now());
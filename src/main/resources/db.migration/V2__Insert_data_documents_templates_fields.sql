insert into schema_system.template (doc_title, removed, title, version, created_at, updated_at)
values (0, false, 'Template for simple contracts', 'V1.0', now(), now());

insert into schema_system.document (created_at, doc_title, number, removed, updated_at, file_id, template_id)
values (now(), 0, '90119', false, now(), null, 1);

insert into schema_system.field (created_at,
                                 default_value, name, placeholder,
                                 removed, type, updated_at, document_id, template_id)
values (now(), 'Limited Liability Company, hereinafter referred to as the Customer, represented by the General Director, acting on the basis of the charter, on the one hand, and the Individual Entrepreneur, hereinafter referred to as the Contractor, acting on the basis of a certificate of state registration of an individual as an individual entrepreneur number 445673847588493, on the other The parties have entered into this agreement as follows:',
        'Header of contract', 'Information about the customer and contractor',
        false, 'Contract field', now(), 1, 1);

insert into schema_system.field (created_at,
                                 default_value, name, placeholder,
                                 removed, type, updated_at, document_id, template_id)
values (now(), 'Subject of the contract. The Customer instructs, and the Contractor assumes the obligation to provide the services specified in the Annexes to this Agreement. The customer undertakes to accept and pay for these services.
The period for the provision of services provided for by this Agreement is determined by the Annexes to this Agreement (hereinafter referred to as the "Period for the provision of Services").
To carry out this activity, the Contractor uses its own staff, and, if necessary, hires additional staff or engages third parties for cooperation, while remaining responsible for their actions.',
        'Body of contract', 'Sections of contract: conditions, description of work, terms of payment and so on',
        false, 'Contract field', now(), 1, 1);

insert into schema_system.field (created_at,
                                 default_value, name, placeholder,
                                 removed, type, updated_at, document_id, template_id)
values (now(), 'Other conditions. When providing services in accordance with this Agreement, each of the Parties undertakes to take into account the recommendations of the other Party regarding the subject matter of this Agreement. The Parties undertake to immediately inform each other about the difficulties that impede the fulfillment of the terms of this Agreement, in order to take the necessary measures in a timely manner.',
        'Footer of contract', 'Finish part of contract',
        false, 'Contract field', now(), 1, 1);




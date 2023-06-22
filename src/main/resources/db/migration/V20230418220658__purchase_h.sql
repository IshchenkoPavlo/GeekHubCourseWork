create table public.purchase_h
(
    id       serial
        constraint purchase_h_pk
            primary key,
    doc_date date,
    comment  varchar,
    total    numeric(14, 2)
);

alter table public.purchase_h
    owner to postgres;


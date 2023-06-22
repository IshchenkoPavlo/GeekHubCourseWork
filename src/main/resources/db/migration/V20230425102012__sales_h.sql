create table public.sales_h
(
    id       serial
        constraint sales_h_pk
            primary key,
    doc_date date,
    total    numeric(14, 2)
);

alter table public.sales_h
    owner to postgres;
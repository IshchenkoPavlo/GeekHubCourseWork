create table public.sales_t
(
    h_id     integer
        constraint sales_t_sales_h_id_fk
            references public.sales_h,
    row_num  integer,
    goods_id integer,
    quantity numeric(12, 3),
    price    numeric(12, 2),
    amount   numeric(14, 2)
);

alter table public.sales_t
    owner to postgres;
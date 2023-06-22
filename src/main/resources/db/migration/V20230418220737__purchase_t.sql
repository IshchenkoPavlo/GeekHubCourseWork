create table public.purchase_t
(
    h_id     integer
        constraint purchase_t_purchase_h_id_fk
            references public.purchase_h,
    row_num  integer,
    goods_id integer,
    quantity numeric(12, 3),
    price    numeric(12, 2),
    amount   numeric(14, 2)
);

alter table public.purchase_t
    owner to postgres;


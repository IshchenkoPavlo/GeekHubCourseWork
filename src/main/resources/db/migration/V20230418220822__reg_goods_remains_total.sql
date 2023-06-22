create table public.reg_goods_remains_total
(
    goods_id integer
        constraint reg_goods_remains_total_goods_id_fk
            references public.goods,
    quantity numeric(14, 3)
);

alter table public.reg_goods_remains_total
    owner to postgres;


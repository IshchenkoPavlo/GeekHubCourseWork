create table public.goods
(
    id             serial
        constraint goods_pk
            primary key,
    is_group       boolean,
    parent_id      integer,
    name           varchar,
    price          numeric(12, 2),
    price_purchase numeric(12, 2)
);

alter table public.goods
    owner to postgres;

create index goods_parent_id_index
    on public.goods (parent_id);


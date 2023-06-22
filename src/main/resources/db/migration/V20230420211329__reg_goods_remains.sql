create table reg_goods_remains
(
    plus_minus int2 not null,
    doc_type   int2 not null,
    doc_num    integer,
    doc_date   timestamp,
    goods_id   integer,
    quantity   numeric(14, 3)
);

create index reg_goods_remains_doc_date_index
    on reg_goods_remains (doc_date);

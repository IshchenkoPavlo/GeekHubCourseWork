alter table purchase_t
    drop constraint purchase_t_purchase_h_id_fk;

alter table purchase_t
    add constraint purchase_t_purchase_h_id_fk
        foreign key (h_id) references purchase_h
            on update cascade on delete cascade;
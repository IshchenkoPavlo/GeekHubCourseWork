ALTER TABLE public.sales_t DROP CONSTRAINT sales_t_sales_h_id_fk;
ALTER TABLE public.sales_t ADD CONSTRAINT sales_t_sales_h_id_fk
    FOREIGN KEY (h_id) REFERENCES public.sales_h(id)
    ON UPDATE CASCADE ON DELETE CASCADE;

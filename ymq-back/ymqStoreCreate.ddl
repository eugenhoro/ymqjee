create sequence hibernate_sequence start with 1 increment by 1
create table DataProduct (id bigint not null, description varchar(10000), image_url varchar(255), isbn varchar(50), product_type integer, nb_of_pages integer, publication_date date, title varchar(200), unit_cost float, primary key (id))

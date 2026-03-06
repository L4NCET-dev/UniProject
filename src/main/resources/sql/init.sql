create table if not exists users
(
    id         int unsigned           not null auto_increment,
    username   varchar(128)           not null unique,
    last_name  varchar(128),
    first_name varchar(128),
    birth_date date,
    role       enum ('USER', 'ADMIN') not null,
    password   varchar(128)           not null,
    primary key (id)
);

create table if not exists product_categories
(
    id   int unsigned not null auto_increment,
    name varchar(128) not null unique,
    primary key (id)
);

create table if not exists products
(
    id          int unsigned not null auto_increment,
    category_id int unsigned not null,
    name        varchar(128) not null unique,
    price       decimal(10, 2) not null,
    active      boolean not null default true,
    primary key (id),
    foreign key (category_id) references product_categories (id)
);

create index idx_products_category_id on products(category_id);

create table if not exists orders
(
    id         int unsigned not null auto_increment,
    user_id    int unsigned not null,
    status     enum ('NEW', 'PAID', 'CANCELLED', 'DONE') not null default 'NEW',
    created_at datetime not null default current_timestamp,
    primary key (id),
    foreign key (user_id) references users (id)
);

create index idx_orders_user_id on orders(user_id);
create index idx_orders_created_at on orders(created_at);

create table if not exists order_items
(
    id         int unsigned not null auto_increment,
    order_id   int unsigned not null,
    product_id int unsigned not null,
    quantity   int unsigned not null,
    unit_price decimal(10, 2) not null,
    primary key (id),
    foreign key (order_id) references orders (id) on delete cascade,
    foreign key (product_id) references products (id),
    unique key uq_order_product (order_id, product_id)
);

create index idx_order_items_order_id on order_items(order_id);
create index idx_order_items_product_id on order_items(product_id);
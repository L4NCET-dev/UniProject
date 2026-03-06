use thesisDB;

insert into users (id, username, last_name, first_name, birth_date, role, password)
values (1, 'admin@gmail.com', 'Admin', 'Super',
        '2000-01-01', 'ADMIN', 'password'),

       (2, 'alice@gmail.com', 'Doe', 'Alice',
        '2002-05-10', 'USER', 'password'),

       (3, 'bob@gmail.com', 'Doe', 'Bob',
        '2001-11-22', 'USER', 'password');

insert into product_categories (id, name)
values (1, 'General'),
       (2, 'Services'),
       (3, 'Tickets');

insert into products (id, category_id, name, price, active)
values (1, 1, 'Standard Item', 99.90, true),
       (2, 1, 'Premium Item', 199.90, true),
       (3, 2, 'Service Basic', 49.50, true),
       (4, 2, 'Service Pro', 149.00, true),
       (5, 3, 'Ticket Economy', 80.00, true),
       (6, 3, 'Ticket Business', 250.00, true);

insert into orders (id, user_id, status, created_at)
values (1, 2, 'NEW', now()),
       (2, 2, 'PAID', now()),
       (3, 3, 'CANCELLED', now()),
       (4, 3, 'DONE', now());

insert into order_items (id, order_id, product_id, quantity, unit_price)
values (1, 1, 1, 1, 99.90),
       (2, 1, 3, 2, 49.50),

       (3, 2, 2, 1, 199.90),
       (4, 2, 4, 1, 149.00),

       (5, 3, 5, 1, 80.00),

       (6, 4, 6, 1, 250.00),
       (7, 4, 1, 2, 99.90);

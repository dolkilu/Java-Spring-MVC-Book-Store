INSERT INTO CUSTOMER (FIRSTNAME, LASTNAME, EMAIL, PHONE, COUNTRY, CITY, ADDRESS1, ADDRESS2, POSTAL_CODE, USERNAME) VALUES
('admin', 'admin', 'admin@hahaha.com', '1834633', 'Hong Kong SAR China', 'HK', 'sever room', '', '', 'admin'),
('test', 'user', 'abc@hahaha.com', '12312312', 'Hong Kong SAR China', 'HK', 'yomama''s room', 'asrasrasa', '', 'testuser'),
('test', 'both', 'abc@hahaha.com', '12312312', 'Hong Kong SAR China', 'HK', 'yomama''s room', 'asrasrasa', '', 'testboth');

INSERT INTO COMMENT (USERNAME, BOOK_ID, DATE, COMMENT) VALUES ('admin', 1, '2024-04-15 04:15:07.000000', 'Testing comment');

INSERT INTO ORDERS (USERNAME, ORDER_DATE, TOTAL, FIRSTNAME, LASTNAME, EMAIL, PHONE, COUNTRY, CITY, ADDRESS1, ADDRESS2, POSTAL_CODE) VALUES
('testuser', '2024-04-15 04:15:07.000000', 316.00, 'test', 'reg', 'abc@hahaha.com', '12312312', 'HK', 'HK', 'yomama''s room', 'asrasrasa', ''),
('admin', '2024-04-18 22:26:01.000000', 99.00, 'admin', 'admin', 'admin@hahaha.com', '1834633', 'Hong Kong SAR China', 'HK', 'sever room', '', ''),
('admin', '2024-04-18 23:05:39.000000', 306.00, 'admin', 'admin', 'admin@hahaha.com', '1834633', 'Hong Kong SAR China', 'HK', 'sever room', '', ''),
('admin', '2024-04-18 23:05:51.000000', 81.00, 'admin', 'admin', 'admin@hahaha.com', '1834633', 'Hong Kong SAR China', 'HK', 'sever room', '', ''),
('admin', '2024-04-18 23:06:05.000000', 45.00, 'admin', 'admin', 'admin@hahaha.com', '1834633', 'Hong Kong SAR China', 'HK', 'sever room', '', '');


INSERT INTO ORDER_BOOK (ORDER_ID, BOOK_ID, QUANTITY) VALUES (1, 1, 1), (1, 3, 1), (1, 4, 1), (1, 7, 1);

INSERT INTO ORDER_BOOK (ORDER_ID, BOOK_ID, QUANTITY) VALUES (2, 1, 1);
INSERT INTO ORDER_BOOK (ORDER_ID, BOOK_ID, QUANTITY) VALUES (3, 2, 1);
INSERT INTO ORDER_BOOK (ORDER_ID, BOOK_ID, QUANTITY) VALUES (3, 3, 1);
INSERT INTO ORDER_BOOK (ORDER_ID, BOOK_ID, QUANTITY) VALUES (4, 4, 3);
INSERT INTO ORDER_BOOK (ORDER_ID, BOOK_ID, QUANTITY) VALUES (5, 8, 1);
INSERT INTO ORDER_BOOK (ORDER_ID, BOOK_ID, QUANTITY) VALUES (5, 11, 1);

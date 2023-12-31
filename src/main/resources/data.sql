INSERT INTO m_url_info (url, title, description, thumbnail) VALUES
('http://example.com', 'Example Domain', 'This domain is for use in illustrative examples in documents.','https://fakeimg.pl/300/'),
('http://example.org', 'Another Example', 'Another example description.','https://fakeimg.pl/300/');

INSERT INTO m_user (user_id, password, name, role) VALUES
('admin@example.com', '$2a$10$APeI3rBtAl558SHXVVjO9ef/Gu.5ojfFR7f.5p/h3qZ1STGAjHAXa', 'admin', 'ROLE_ADMIN'),
('user@example.com', '$2a$10$APeI3rBtAl558SHXVVjO9ef/Gu.5ojfFR7f.5p/h3qZ1STGAjHAXa', 'user', 'ROLE_USER');

INSERT INTO t_url_info (user_id, url, title, description, thumbnail) VALUES
('admin@example.com', 'https://www.example.com', 'Example Title', 'Example Description', 'https://fakeimg.pl/300/');

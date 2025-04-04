CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255),
    password VARCHAR(255),
    user_email VARCHAR(255)
);

INSERT INTO users (id, user_name, password, user_email) VALUES (1, 'Rithy', '12345', 'rithy@quickchat.com');
INSERT INTO users (id, user_name, password, user_email) VALUES (2, 'Alice', '12345', 'alice@quickchat.com');
INSERT INTO users (id, user_name, password, user_email) VALUES (3, 'Bob', '12345', 'bob@quickchat.com');
INSERT INTO users (id, user_name, password, user_email) VALUES (4, 'Charlie', '12345', 'charlie@quickchat.com');
INSERT INTO users (id, user_name, password, user_email) VALUES (5, 'David', '12345', 'david@quickchat.com');

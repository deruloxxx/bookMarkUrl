CREATE TABLE IF NOT EXISTS m_url_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  url VARCHAR,
  title VARCHAR,
  description VARCHAR,
  thumbnail VARCHAR
);

CREATE TABLE IF NOT EXISTS m_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id VARCHAR(50) UNIQUE,
  password VARCHAR(60),
  name VARCHAR(50),
  role VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS t_url_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id VARCHAR(50) REFERENCES m_user(user_id),
  url VARCHAR(255),
  title VARCHAR(255),
  description VARCHAR(255),
  thumbnail VARCHAR(255)
);


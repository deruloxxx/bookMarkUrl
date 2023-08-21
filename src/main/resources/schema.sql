CREATE TABLE IF NOT EXISTS m_url_info (
  id VARCHAR(50) PRIMARY KEY,
  url VARCHAR,
  title VARCHAR,
  description VARCHAR,
  thumbnail VARCHAR
);

CREATE TABLE IF NOT EXISTS m_user (
  id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(50),
  password VARCHAR(60)
);

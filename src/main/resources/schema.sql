CREATE TABLE IF NOT EXISTS m_url_info (
  id VARCHAR(50) PRIMARY KEY,
  url VARCHAR,
  title VARCHAR,
  description VARCHAR,
  thumbnail VARCHAR
);

CREATE TABLE IF NOT EXISTS m_user (
  user_id VARCHAR(50) PRIMARY KEY,
  password VARCHAR(60),
  name VARCHAR(50),
  role VARCHAR(50)
);

CREATE TABLE JournalSummary (
  id BIGINT NOT NULL AUTO_INCREMENT,
  createdAt DATETIME,
  summary varchar(200),
  title varchar(30),
  primary key (id)
);

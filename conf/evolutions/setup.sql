DROP SCHEMA IF EXISTS schema_a CASCADE;
DROP SCHEMA IF EXISTS schema_b CASCADE;

CREATE SCHEMA schema_a;
CREATE SCHEMA schema_b;

CREATE TABLE schema_a.customer
(
  id integer NOT NULL,
  name text NOT NULL,
  CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE schema_a.customer_address
(
  id integer NOT NULL,
  customer_id integer NOT NULL,
  address text NOT NULL,
  CONSTRAINT pk_customer_address PRIMARY KEY (id),
  CONSTRAINT fk_customer FOREIGN KEY (customer_id)
  REFERENCES customer (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
);

INSERT INTO schema_a.customer values(1, 'Tenant A Customer');

INSERT INTO schema_a.customer_address values(1, 1, 'A Street, Belfast, Co.Antrim, N.Ireland');
INSERT INTO schema_a.customer_address values(2, 1, 'A Street, Downpatrick, Co.Down, N.Ireland');

CREATE TABLE schema_b.customer
(
  id integer NOT NULL,
  name text NOT NULL,s
  CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE schema_b.customer_address
(
  id integer NOT NULL,
  customer_id integer NOT NULL,
  address text NOT NULL,
  CONSTRAINT pk_customer_address PRIMARY KEY (id),
  CONSTRAINT fk_customer FOREIGN KEY (customer_id)
  REFERENCES customer (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE CASCADE
);

INSERT INTO schema_b.customer values(1, 'Tenant B Customer');

INSERT INTO schema_b.customer_address values(1, 1, 'B Street, Belfast, Co.Antrim, N.Ireland');
INSERT INTO schema_b.customer_address values(2, 1, 'B Street, Downpatrick, Co.Down, N.Ireland');
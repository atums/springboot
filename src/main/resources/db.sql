DROP TABLE IF EXISTS post;

CREATE TABLE post(
                     id int GENERATED ALWAYS AS IDENTITY NOT NULL,
                     title varchar(256),
                     anons varchar(1024),
                     full_text text,
                     views int,
                     CONSTRAINT pk_post_id PRIMARY KEY (id)
);

INSERT INTO post(title, anons, full_text, views) VALUES
                                                     ('First post', 'It is a first post in this sait', 'Bla-bla-bla....', 1),
                                                     ('Second post', 'It is a second post in this sait', 'Bla-bla-bla....rutine', 22);

SELECT * FROM post;

DROP TABLE countries;
DROP TABLE continents;

CREATE TABLE continents (
                            id NUMBER PRIMARY KEY,
                            name VARCHAR2(100) NOT NULL UNIQUE
);

-- Create countries table
CREATE TABLE countries (
                           id NUMBER PRIMARY KEY,
                           name VARCHAR2(100) NOT NULL,
                           code VARCHAR2(3) NOT NULL UNIQUE,
                           continent NUMBER,
                           CONSTRAINT fk_continent FOREIGN KEY (continent) REFERENCES continents(id)
);

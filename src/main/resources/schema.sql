DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Activity;
DROP TABLE IF EXISTS Participant;
DROP TABLE IF EXISTS Image;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;



CREATE TABLE IF NOT EXISTS Image (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    image_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Participant (
    participant_id VARCHAR(14) PRIMARY KEY,
    full_name VARCHAR(50),
    telephone_number VARCHAR(12),
    comment VARCHAR(5000),
    image_id INT,
    FOREIGN KEY (image_id) REFERENCES Image(image_id)
);

CREATE TABLE IF NOT EXISTS Item (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    participant_id VARCHAR(20),
    image_id INT,
    FOREIGN KEY (participant_id) REFERENCES Participant(participant_id),
    FOREIGN KEY (image_id) REFERENCES Image(image_id)
);
CREATE TABLE IF NOT EXISTS users(
    username varchar(45) primary key not null,
    password varchar(68) not null,
    enabled tinyint not null
);
CREATE TABLE IF NOT EXISTS authorities(
    id INT AUTO_INCREMENT PRIMARY KEY,
    username varchar(45) not null,
    authority varchar(45) not null,
    unique (username, authority),
    constraint authorities_ibfk_1 foreign key (username) references users (username)
);
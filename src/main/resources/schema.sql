DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Participant;
DROP TABLE IF EXISTS Image;



CREATE TABLE IF NOT EXISTS Image (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    image_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Participant (
    participant_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(50),
    telephone_number VARCHAR(12),
    comment VARCHAR(5000),
    image_id INT,
    FOREIGN KEY (image_id) REFERENCES Image(image_id)
);

CREATE TABLE IF NOT EXISTS Item (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    participant_id INT,
    image_id INT,
    FOREIGN KEY (participant_id) REFERENCES Participant(participant_id),
    FOREIGN KEY (image_id) REFERENCES Image(image_id)
);
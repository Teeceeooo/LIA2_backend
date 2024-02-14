INSERT INTO image (image_id, image_url)
VALUES
    (2, 'niklasnohrs.jpg'),
    (1, 'default-image.png');


INSERT INTO participant (participant_id, full_name, telephone_number, image_id)
VALUES
    (8146845, 'Niklas Nohrs', '123-456-7890', 2),
    (5841287, 'Sebastian Granlund', '987-654-3210', NULL);


INSERT INTO Item (item_id, description, participant_id)
VALUES
    (1, 'Chassis', 8146845),        -- Niklas har ett chassis
    (2, 'Monitor', 8146845),        -- Niklas har en monitor
    (3, 'Keyboard', 8146845),       -- Niklas har ett keyboard
    (4, 'Headset', 8146845),        -- Niklas har ett headset
    (5, 'Chassis', 5841287),        -- Sebastian har ett headset
    (6, 'Monitor', 5841287);        -- Sebastian har ett headset
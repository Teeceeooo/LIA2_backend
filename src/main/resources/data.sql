INSERT INTO participant (participant_id, full_name, telephone_number)
VALUES
    (1, 'Niklas Nohrs', '123-456-7890'),
    (2, 'Sebastian Granlund', '987-654-3210');

INSERT INTO Item (item_id, description, participant_id)
VALUES
    (1, 'Chassis', 1),        -- Niklas har ett chassis
    (2, 'Monitor', 1),        -- Niklas har en monitor
    (3, 'Keyboard', 1),       -- Niklas har ett keyboard
    (4, 'Headset', 1),        -- Niklas har ett headset
    (5, 'Chassis', 2),        -- Sebastian har ett headset
    (6, 'Monitor', 2);        -- Sebastian har ett headset
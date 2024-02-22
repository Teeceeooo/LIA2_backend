INSERT INTO image (image_id, image_url)
VALUES
    (2, 'niklasnohrs.jpg'),
    (1, 'default-image.png');


INSERT INTO participant (participant_id, full_name, telephone_number, image_id)
VALUES
    (8146845, 'Niklas Nohrs', '123-456-7890', 2),
    (8146846, 'Niklas asd', '072555553455', 2),
    (8146899, 'Niklas fgdgfd', '07255552345', 2),
    (8146847, 'Niklas vcbvcb', '0765555555', 2),
    (8146848, 'Niklas wewe', '0765555535', 2),
    (8146850, 'Niklas fsdsdf', '123-456-7890', 2),
    (8146851, 'Niklas rty', '123-456-7890', 2),
    (8146852, 'Niklas asdf', '123-456-7890', 2),
    (8146853, 'Niklas vbcvbc', '123-456-7890', 2),
    (8146854, 'Niklas Nohfdagagfdrs', '123-456-7890', 2),
    (8146855, 'Niklas dfgh', '123-456-7890', 2),
    (8146856, 'Niklas werwer', '123-456-7890', 2),
    (8146857, 'Sebastian dhsfdg', '123-456-7890', 2),
    (8146858, 'Sebastian asddas', '123-456-7890', 2),
    (8146859, 'Sebastian weqewq', '123-456-7890', 2),
    (8146860, 'Sebastian dhhd', '123-456-7890', 2),
    (8146861, 'Sebastian SDF', '123-456-7890', 2),
    (8146862, 'Sebastian ASDF', '123-456-7890', 2),
    (8146863, 'Sebastian ZXCVZCXV', '123-456-7890', 2),
    (8146864, 'Sebastian ASDasdohrs', '123-456-7890', 2),
    (8146865, 'Sebastian Nohssadrs', '123-456-7890', 2),
    (5841287, 'Sebastian Granlund', '987-654-3210', NULL);


INSERT INTO Item (item_id, description, participant_id)
VALUES
    (1, 'Chassis', 8146845),        -- Niklas har ett chassis
    (2, 'Monitor', 8146845),        -- Niklas har en monitor
    (3, 'Keyboard', 8146845),       -- Niklas har ett keyboard
    (4, 'Headset', 8146845),        -- Niklas har ett headset
    (5, 'Chassis', 5841287),        -- Sebastian har ett headset
    (6, 'Monitor', 5841287);        -- Sebastian har ett headset
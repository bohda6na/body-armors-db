INSERT INTO manufacturer (brandName, countryOfOrigin) VALUES
('Point Blank Enterprises', 'USA'),
('AR500 Armor', 'USA'),
('DHgate Armory', 'China'),
('Mehler Vario System', 'Germany'),
('Safariland', 'USA')
ON DUPLICATE KEY UPDATE brandName = VALUES(brandName);

INSERT INTO armormodel (manufacturerID, modelName, protectionLevel, v50Velocity, backfaceDeformation, SKU, warrantyYears) VALUES
(1, 'Civilian Soft Guard',        'NIJ-IIA',   436.0, 44.0, 'SKU-CSG-001', 5),
(1, 'Tactical Plate Carrier Pro', 'NIJ-III',   838.0, 44.0, 'SKU-TCP-002', 10),
(3, 'UA Shield IIIA',             'NIJ-IIIA',  580.0, 44.0, 'SKU-UAS-003', 7),
(5, 'Safariland Delta',           'NIJ-II',    358.0, 44.0, 'SKU-SFD-004', 5),
(4, 'Mehler VestPro',             'DSTU-8782', 600.0, 44.0, 'SKU-MVP-005', 7),
(2, 'AR500 Level IV Plate',       'NIJ-IV',    960.0, 25.0, 'SKU-AR5-006', 10),
(1, 'Interceptor-X',              'Level IV',  880.0, 35.0, 'SKU-ITX-007', 5),
(1, 'Point Blank Alpha Elite',    'NIJ-IIIA',  570.0, 44.0, 'SKU-PBA-008', 7),
(1, 'Model 9 Name',               'NIJ-IIIA',  560.0, 44.0, 'SKU-M9-009',  7)
ON DUPLICATE KEY UPDATE modelName = VALUES(modelName);

INSERT INTO armorspec (modelID, maxWeightKg, armorCategory) VALUES
(1, 8.50,  'Hard Armor'),
(2, 10.00, 'Hard Armor'),
(3, 7.20,  'Soft Armor'),
(4, 6.80,  'Soft Armor'),
(5, 9.50,  'Hard Armor'),
(6, 11.00, 'Hard Armor'),
(7, 10.20, 'Hard Armor'),
(8, 7.00,  'Soft Armor'),
(9, 7.50,  'Soft Armor')
ON DUPLICATE KEY UPDATE maxWeightKg = VALUES(maxWeightKg);

INSERT INTO batch (modelID, batchNumber, unitCost, deliveryDate, quantity) VALUES
(1, 'BATCH-2024-A', 320.00, '2024-01-20', 50),
(2, 'BATCH-2024-A', 450.00, '2024-02-18', 40),
(3, 'BATCH-2024-A', 390.00, '2024-01-20', 30),
(3, 'BATCH-2024-B', 390.00, '2024-02-15', 20),
(4, 'BATCH-2024-A', 350.00, '2024-02-18', 40),
(5, 'BATCH-2024-B', 420.00, '2024-03-12', 10),
(6, 'BATCH-2024-A', 510.00, '2024-01-20', 50),
(8, 'BATCH-2024-D', 380.00, '2024-05-01', 15)
ON DUPLICATE KEY UPDATE unitCost = VALUES(unitCost);

INSERT INTO bodyarmor (modelID, serialNumber, productionDate, conditionStatus, weight, size) VALUES
(1, 'SN-001-2024', '2024-01-15', 'New',     3.2, 'M'),
(1, 'SN-002-2024', '2024-01-15', 'New',     3.2, 'M'),
(2, 'SN-003-2024', '2024-02-10', 'New',     7.5, 'L'),
(2, 'SN-004-2024', '2024-02-10', 'Used',    7.5, 'L'),
(3, 'SN-005-2024', '2024-03-01', 'New',     3.8, 'M'),
(3, 'SN-006-2024', '2024-03-01', 'New',     3.8, 'M'),
(4, 'SN-007-2024', '2024-03-20', 'New',     3.5, 'S'),
(5, 'SN-008-2024', '2024-04-05', 'New',     4.2, 'L'),
(6, 'SN-009-2024', '2024-04-10', 'New',     8.0, 'XL'),
(6, 'SN-010-2024', '2024-04-10', 'Expired', 8.0, 'XL'),
(8, 'SN-011-2024', '2024-05-01', 'New',     3.9, 'M')
ON DUPLICATE KEY UPDATE conditionStatus = VALUES(conditionStatus);

UPDATE bodyarmor SET inspectionDate='2024-06-10' WHERE serialNumber IN ('SN-001-2024','SN-002-2024');
UPDATE bodyarmor SET inspectionDate='2024-06-15' WHERE serialNumber IN ('SN-003-2024','SN-004-2024');
UPDATE bodyarmor SET inspectionDate='2024-07-01' WHERE serialNumber IN ('SN-005-2024','SN-006-2024');
UPDATE bodyarmor SET inspectionDate='2024-07-20' WHERE serialNumber = 'SN-007-2024';
UPDATE bodyarmor SET inspectionDate='2024-08-05' WHERE serialNumber IN ('SN-008-2024','SN-009-2024','SN-010-2024');
UPDATE bodyarmor SET inspectionDate='2024-09-01' WHERE serialNumber = 'SN-011-2024';

INSERT INTO certification (certificationCode, standardName) VALUES
('NIJ-IIA',   'NIJ Standard 0101.06 Level IIA'),
('NIJ-II',    'NIJ Standard 0101.06 Level II'),
('NIJ-IIIA',  'NIJ Standard 0101.06 Level IIIA'),
('NIJ-III',   'NIJ Standard 0101.06 Level III'),
('NIJ-IV',    'NIJ Standard 0101.06 Level IV'),
('DSTU-8782', 'DSTU 8782:2018 Ukrainian Standard'),
('CE-EN1063', 'CE EN 1063 European Ballistic Standard')
ON DUPLICATE KEY UPDATE standardName = VALUES(standardName);

INSERT INTO armormodelcertification (modelID, certificationCode, verificationDate, isValid) VALUES
(1, 'NIJ-IIA',   '2024-01-10', 1),
(2, 'NIJ-III',   '2024-01-10', 1),
(2, 'DSTU-8782', '2024-01-10', 1),
(3, 'NIJ-IIIA',  '2024-02-05', 1),
(3, 'DSTU-8782', '2024-02-05', 1),
(4, 'NIJ-II',    '2024-02-05', 1),
(5, 'DSTU-8782', '2024-03-01', 1),
(5, 'NIJ-IIIA',  '2024-03-01', 1),
(6, 'NIJ-IV',    '2024-03-15', 1),
(8, 'CE-EN1063', '2024-05-10', 1),
(8, 'NIJ-IIIA',  '2024-05-10', 1),
(9, 'NIJ-IIIA',  '2024-06-01', 0)
ON DUPLICATE KEY UPDATE verificationDate = VALUES(verificationDate), isValid = VALUES(isValid);

INSERT INTO material (materialName) VALUES
('Ceramic'),('Kevlar'),('Steel'),('UHMWPE'),('Dyneema'),('Titanium')
ON DUPLICATE KEY UPDATE materialName = VALUES(materialName);

INSERT INTO bodypart (bodyPartName) VALUES
('Chest'),('Back'),('Sides'),('Shoulder'),('Groin'),('Neck')
ON DUPLICATE KEY UPDATE bodyPartName = VALUES(bodyPartName);

INSERT INTO modelmaterial (modelID, materialName) VALUES
(1,'Kevlar'),
(2,'Ceramic'),(2,'Kevlar'),
(3,'Kevlar'),(3,'UHMWPE'),
(4,'Kevlar'),
(5,'Ceramic'),
(6,'Steel'),
(7,'Ceramic'),
(8,'Kevlar'),(8,'UHMWPE'),
(9,'Kevlar'),(9,'Ceramic')
ON DUPLICATE KEY UPDATE materialName = VALUES(materialName);

INSERT INTO modelbodypart (modelID, bodyPartName) VALUES
(1,'Chest'),(1,'Back'),
(2,'Chest'),(2,'Back'),(2,'Sides'),
(3,'Chest'),(3,'Back'),
(4,'Chest'),
(5,'Chest'),(5,'Back'),
(6,'Chest'),(6,'Back'),
(7,'Chest'),(7,'Back'),
(8,'Chest')
ON DUPLICATE KEY UPDATE bodyPartName = VALUES(bodyPartName);
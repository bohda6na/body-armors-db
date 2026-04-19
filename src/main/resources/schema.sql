DROP TABLE IF EXISTS modelbodypart;
DROP TABLE IF EXISTS modelmaterial;
DROP TABLE IF EXISTS armormodelcertification;
DROP TABLE IF EXISTS bodyarmor;
DROP TABLE IF EXISTS batch;
DROP TABLE IF EXISTS armorspec;
DROP TABLE IF EXISTS bodypart;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS certification;
DROP TABLE IF EXISTS armormodel;
DROP TABLE IF EXISTS manufacturer;

CREATE TABLE manufacturer (
    manufacturerID  INT AUTO_INCREMENT PRIMARY KEY,
    brandName       VARCHAR(100) NOT NULL,
    countryOfOrigin VARCHAR(50)
);

CREATE TABLE armormodel (
    modelID             INT AUTO_INCREMENT PRIMARY KEY,
    manufacturerID      INT NOT NULL,
    modelName           VARCHAR(100) NOT NULL,
    protectionLevel     VARCHAR(20),
    v50Velocity         DECIMAL(7,2),
    backfaceDeformation DECIMAL(5,2),
    SKU                 VARCHAR(50) UNIQUE,
    warrantyYears       INT,
    CONSTRAINT fk_model_mfr
        FOREIGN KEY (manufacturerID) REFERENCES manufacturer(manufacturerID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE armorspec (
    modelID     INT NOT NULL PRIMARY KEY,
    maxWeightKg DECIMAL(5,2),
    armorCategory VARCHAR(20),
    CONSTRAINT fk_spec_model
        FOREIGN KEY (modelID) REFERENCES armormodel(modelID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE batch (
    modelID      INT NOT NULL,
    batchNumber  VARCHAR(50) NOT NULL,
    unitCost     DECIMAL(10,2),
    deliveryDate DATE,
    quantity     INT UNSIGNED,
    PRIMARY KEY (modelID, batchNumber),
    CONSTRAINT fk_batch_model
        FOREIGN KEY (modelID) REFERENCES armormodel(modelID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE bodyarmor (
    productID       INT AUTO_INCREMENT PRIMARY KEY,
    modelID         INT NOT NULL,
    serialNumber    VARCHAR(100) NOT NULL UNIQUE,
    productionDate  DATE,
    conditionStatus VARCHAR(50),
    weight          DECIMAL(6,2),
    size            VARCHAR(10),
    inspectionDate  DATE,
    CONSTRAINT fk_armor_model
        FOREIGN KEY (modelID) REFERENCES armormodel(modelID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE certification (
    certificationCode VARCHAR(30) NOT NULL PRIMARY KEY,
    standardName      VARCHAR(100)
);

CREATE TABLE armormodelcertification (
    modelID           INT NOT NULL,
    certificationCode VARCHAR(30) NOT NULL,
    verificationDate  DATE,
    isValid           TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (modelID, certificationCode),
    CONSTRAINT fk_amc_model
        FOREIGN KEY (modelID) REFERENCES armormodel(modelID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_amc_cert
        FOREIGN KEY (certificationCode) REFERENCES certification(certificationCode)
);

CREATE TABLE material (
    materialName VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE modelmaterial (
    modelID      INT NOT NULL,
    materialName VARCHAR(50) NOT NULL,
    PRIMARY KEY (modelID, materialName),
    CONSTRAINT fk_mm_model
        FOREIGN KEY (modelID) REFERENCES armormodel(modelID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_mm_mat
        FOREIGN KEY (materialName) REFERENCES material(materialName)
);

CREATE TABLE bodypart (
    bodyPartName VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE modelbodypart (
    modelID      INT NOT NULL,
    bodyPartName VARCHAR(50) NOT NULL,
    PRIMARY KEY (modelID, bodyPartName),
    CONSTRAINT fk_mbp_model
        FOREIGN KEY (modelID) REFERENCES armormodel(modelID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_mbp_bp
        FOREIGN KEY (bodyPartName) REFERENCES bodypart(bodyPartName)
);
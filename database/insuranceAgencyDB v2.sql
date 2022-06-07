DROP DATABASE IF EXISTS AgenceAssurance;
CREATE DATABASE IF NOT EXISTS AgenceAssurance;
USE AgenceAssurance;

CREATE TABLE Logininfo
(
    idLogin    INT(3)      NOT NULL AUTO_INCREMENT,
    userName   VARCHAR(50) NOT NULL,
    pass       VARCHAR(50),
    usertype   VARCHAR(50) DEFAULT NULL,
    GrantedKey VARCHAR(8)  DEFAULT NULL,
    CONSTRAINT pk_Log PRIMARY KEY (idLogin, userName)
);

CREATE TABLE Usages
(
    idUsage     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    libaleUsage VARCHAR(80)
);

CREATE TABLE Client
(
    cin        VARCHAR(20) PRIMARY KEY NOT NULL,
    nomComplet VARCHAR(250),
    adrsClient TEXT,
    teleClient VARCHAR(15)
);

CREATE TABLE Vehicle
(
    numMatric VARCHAR(50) PRIMARY KEY NOT NULL,
    Puissance VARCHAR(50),
    carburant VARCHAR(50),
    idUsage   INT,
    cin       VARCHAR(20),
    CONSTRAINT fk_Client FOREIGN KEY (cin) REFERENCES Client (cin)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_Usage FOREIGN KEY (idUsage) REFERENCES Usages (idUsage)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE Quittance
(
    numQuitt     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    cin          VARCHAR(20),
    dateQuitt    DATETIME,
    primeReele   FLOAT,
    reglement    FLOAT,
    perte        FLOAT,
    quittAnnuler TINYTEXT,
    resiliation  FLOAT,
    CONSTRAINT fk_ct FOREIGN KEY (cin) REFERENCES Client (cin)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE Credit
(
    CreditId      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    numQuitt      INT,
    creditSum     FLOAT,
    Diviser       TINYINT DEFAULT 0,
    dateFragOne   DATE,
    fragOne       FLOAT,
    dateFragTwo   DATE,
    fragTwo       FLOAT,
    dateFragThree DATE            NULL,
    fragThree     FLOAT           NULL,
    dateFragFour  DATE            NULL,
    fragFour      FLOAT           NULL,
    dateFragFive  DATE            NULL,
    fragFive      FLOAT           NULL,
    dateFragSix   DATE            NULL,
    fragSix       FLOAT           NULL,
    CONSTRAINT fk_frgCrdt FOREIGN KEY (numQuitt) REFERENCES Quittance (numQuitt)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE Attestation
(
    idAttest   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    typeAttest VARCHAR(40)
);

CREATE TABLE Fractionnement
(
    idFraction        INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idAttest          INT,
    numQuitt          INT,
    datePremiereFrac  DATE,
    fracOne           FLOAT,
    dateDeuxiemeFrac  DATE,
    fracTwo           FLOAT,
    dateTroisiemeFrac DATE,
    fracThree         FLOAT,
    dateQuatriemeFrac DATE,
    fracFour          FLOAT,
    CONSTRAINT fk_attest FOREIGN KEY (idAttest) REFERENCES Attestation (idAttest)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_qu FOREIGN KEY (numQuitt) REFERENCES Quittance (numQuitt)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE Operation
(
    idOpt    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    libelOpt TEXT,
    dateOpt  DATETIME DEFAULT now(),
    uName    VARCHAR(50),
    CONSTRAINT fk_login FOREIGN KEY (uName) REFERENCES Logininfo (userName)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- setting Database UNICODE--------------------------------------------------------
ALTER DATABASE AgenceAssurance
    CHARACTER SET utf8
    COLLATE utf8_unicode_ci;
ALTER TABLE Logininfo
    CONVERT TO CHARACTER SET utf8
        COLLATE utf8_unicode_ci;
ALTER TABLE Usages
    CONVERT TO CHARACTER SET utf8
        COLLATE utf8_unicode_ci;
ALTER TABLE Client
    CONVERT TO CHARACTER SET utf8
        COLLATE utf8_unicode_ci;
ALTER TABLE Vehicle
    CONVERT TO CHARACTER SET utf8
        COLLATE utf8_unicode_ci;
ALTER TABLE Quittance
    CONVERT TO CHARACTER SET utf8
        COLLATE utf8_unicode_ci;
ALTER TABLE Attestation
    CONVERT TO CHARACTER SET utf8
        COLLATE utf8_unicode_ci;
ALTER TABLE Fractionnement
    CONVERT TO CHARACTER SET utf8
        COLLATE utf8_unicode_ci;
ALTER TABLE Operation
    CONVERT TO CHARACTER SET utf8
        COLLATE utf8_unicode_ci;

-- TRRIGERS -----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS cltCascade;
DELIMITER $$
CREATE TRIGGER cltCascade
    AFTER DELETE
    ON Client
    FOR EACH ROW
BEGIN
    DELETE
    FROM Vehicle
    WHERE cin = old.cin;
END;
$$
DELIMITER ;
-- ------------------------------------------------------
DROP TRIGGER IF EXISTS toUpperInsert;
DELIMITER $$
CREATE TRIGGER toUpperInsert
    BEFORE INSERT
    ON Client
    FOR EACH ROW
BEGIN
    SET new.nomComplet = upper(new.nomComplet);
    SET new.cin = upper(new.cin);
END;
$$
DELIMITER ;
-- -------------------------------------------------------- ------------------------------------------------------
DROP TRIGGER IF EXISTS toUpperVeh;
DELIMITER $$
CREATE TRIGGER toUpperVeh
    BEFORE INSERT
    ON Vehicle
    FOR EACH ROW
BEGIN
    SET new.cin = upper(new.cin);
    SET new.numMatric = upper(new.numMatric);
END;
$$
DELIMITER ;
-- ------------------------------------------------------
/*
DROP TRIGGER IF EXISTS toUpperUpdate;
DELIMITER $$
CREATE TRIGGER toUpperUpdate
AFTER UPDATE ON Client
FOR EACH ROW
  BEGIN
    UPDATE Client
    SET nomComplet = upper(new.nomComplet)
    WHERE cin = new.cin;
  END;
$$
DELIMITER ;
*/
DROP TRIGGER IF EXISTS deleteQuett;
DELIMITER $$
CREATE TRIGGER deleteQuett
    AFTER DELETE
    ON Quittance
    FOR EACH ROW
BEGIN
    DELETE
    FROM Fractionnement
    WHERE numQuitt = old.numQuitt;
    DELETE
    FROM Credit
    WHERE numQuitt = old.numQuitt;
END;
$$
DELIMITER ;


--
-- contenu de la table Usagesa
--
INSERT INTO Usages (libaleUsage)
VALUES ('Tourisme'),
       ('Cyclo'),
       ('Commerce'),
       ('Divers'),
       ('Assistance SAHAM');

--
-- contenu de la table Attestation
--
INSERT INTO Attestation (typeAttest)
VALUES ('Definitive'),
       ('Provisoire'),
       ('Frac + prov');

INSERT INTO logininfo (userName, pass, usertype, GrantedKey)
VALUES ('m\'hamdi', 'ahmed', 'Administrateur', 'Granted'),
       ('bakila', 'mbark', 'Administrateur', 'Granted'),
       ('a', 'a', 'Administrateur', 'Granted');
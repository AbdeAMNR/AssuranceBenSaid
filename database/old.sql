DROP DATABASE IF EXISTS AgenceAssurance;
CREATE DATABASE IF NOT EXISTS AgenceAssurance;
USE AgenceAssurance;

CREATE TABLE Logininfo (
  idLogin int(3) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  pass varchar(50),
  usertype varchar(50) DEFAULT NULL,
  GrantedKey varchar(8) DEFAULT NULL
);

CREATE TABLE Usages (
  idUsage     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  libaleUsage VARCHAR(80)
);

CREATE TABLE Client (
  cin          VARCHAR(20) PRIMARY KEY NOT NULL,
  nomComplet   VARCHAR(250),
  adrsClient   TEXT,
  teleClient   VARCHAR(15),
  dateNassance DATE
);

CREATE TABLE Vehicle (
  numMatric   VARCHAR(50) PRIMARY KEY NOT NULL,
  Puissance   VARCHAR(50),
  arrosserie  VARCHAR(250),
  marque      VARCHAR(50),
  typeVehicle VARCHAR(50),
  carburant   VARCHAR(50),
  nbrAssi     INT,
  anneeFabric DATE,
  color VARCHAR(15),
  idUsage     INT,
  cin VARCHAR(20),
  CONSTRAINT fk_Client FOREIGN KEY (cin) REFERENCES Client (cin)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
  CONSTRAINT fk_Usage FOREIGN KEY (idUsage) REFERENCES Usages (idUsage)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE Contrat (
  numContrat  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  typeContrat VARCHAR(50),
  dateEch     DATE,
  typePaie    VARCHAR(250)             DEFAULT 'Cash',
  numMatric   VARCHAR(50),
  CONSTRAINT fk_Veh FOREIGN KEY (numMatric) REFERENCES Vehicle (numMatric)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

CREATE TABLE Expert (
  codeExpert INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nomComplet VARCHAR(255),
  teleExpert VARCHAR(15)
);


CREATE TABLE Accident (
  numAcc     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  lieuAcc    TEXT,
  dateAcc    DATE,
  descrip    TEXT,
  codeExpert INT,
  CONSTRAINT fk_Expert FOREIGN KEY (codeExpert) REFERENCES Expert (codeExpert)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
  CONSTRAINT fk_Vehi FOREIGN KEY (numMatric) REFERENCES Vehicle (numMatric)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);


CREATE TABLE Rapport (
  numRapport  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  dateRapport DATE,
  CONSTRAINT fk_Expert FOREIGN KEY (codeExpert) REFERENCES Expert (codeExpert)
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
ALTER TABLE Contrat
  CONVERT TO CHARACTER SET utf8
  COLLATE utf8_unicode_ci;
ALTER TABLE Expert
  CONVERT TO CHARACTER SET utf8
  COLLATE utf8_unicode_ci;
ALTER TABLE Accident
  CONVERT TO CHARACTER SET utf8
  COLLATE utf8_unicode_ci;
ALTER TABLE Rapport
  CONVERT TO CHARACTER SET utf8
  COLLATE utf8_unicode_ci;

--
-- Contenu de la table `logininfo`
--
INSERT INTO Logininfo(username, pass, usertype, GrantedKey) VALUES
  ('m\'hamdi', 'ahmed', 'Administrateur', 'Granted'),
  ('bakila', 'mbark', 'Administrateur', 'Granted'),
  ('fardan', 'abdellatif', 'Etudiant', ''),
  ('a', 'a', 'Administrateur', 'Granted');
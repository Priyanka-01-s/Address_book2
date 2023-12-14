-- UC1 : Ability to create a Address Book Service DB

create database ADDRESSBOOK_DB;
SHOW DATABASES;

USE ADDRESSBOOK_DB

-- uc_2 : Ability to create a Address Book Table with first and last names, address, city, state, zip, phone number and 
-- email as its attributes

CREATE TABLE ADDRESSBOOK_TABLE (
	FIRSTNAME VARCHAR(255) NOT NULL,
    LASTNAME VARCHAR(255) NOT NULL,
    ADDRESS VARCHAR(255),
    CITY VARCHAR(255),
    STATE VARCHAR(255),
    ZIP VARCHAR(255),
    PHONE VARCHAR(255),
    EMAIL VARCHAR(255) UNIQUE   
);

DESC ADDRESSBOOK_TABLE;

-- OUTPUT
-- FIRSTNAME	varchar(255)	NO			
-- LASTNAME	varchar(255)	NO			
-- ADDRESS	varchar(255)	YES			
-- CITY	varchar(255)	YES			
-- STATE	varchar(255)	YES			
-- ZIP	varchar(255)	YES			
-- PHONE	varchar(255)	YES			
-- EMAIL	varchar(255)	YES	UNI		

-- UC_3 : Ability to insert new Contacts to Address Book

INSERT INTO ADDRESSBOOK_TABLE(FIRSTNAME, LASTNAME, ADDRESS, CITY, STATE, ZIP, PHONE, EMAIL)
VALUES
    ('Priyanka', 'Sengupta', 'Sec78', 'Noida', 'UP','201307','1234567890','psg@gmail.com'),
    ('Bob', 'Johnson', '123 Main', 'City', 'CA', '90210', '9876543210', 'bob@gmail.com'),
    ('John', 'Doe', '456 Oak', 'Towns', 'NY', '12345', '5551112222', 'johndoe@example.com'),
    ('Jane', 'Smith', '789 Pine', 'Village', 'TX', '54321', '9998887777', 'jane.sm@example.com'),
    ('Michael', 'Johnson', '101 Ave', 'Ham', 'FL', '32100', '7776665555', 'micl.j@example.com');
    

SELECT * FROM ADDRESSBOOK_TABLE

-- OUTPUT
-- Priyanka	Sengupta	Sec78	Noida	UP	201307	1234567890	psg@gmail.com
-- Bob	Johnson	123 Main	City	CA	90210	9876543210	bob@gmail.com
-- John	Doe	456 Oak	Towns	NY	12345	5551112222	johndoe@example.com
-- Jane	Smith	789 Pine	Village	TX	54321	9998887777	jane.sm@example.com
-- Michael	Johnson	101 Ave	Ham	FL	32100	7776665555	micl.j@example.com

-- UC_4 : Ability to edit existing contact person using their name

SET SQL_SAFE_UPDATES = 0;

UPDATE ADDRESSBOOK_TABLE
SET ADDRESS = 'New123 Main', CITY = 'NewCity', STATE = 'NewCA', ZIP = '112233',
    PHONE = '0987654321', EMAIL = 'newemail@abc.com'
WHERE FIRSTNAME = 'Bob' AND LASTNAME = 'Johnson';

SELECT * FROM ADDRESSBOOK_TABLE;

-- OUTPUT
-- Priyanka	Sengupta	Sec78	Noida	UP	201307	1234567890	psg@gmail.com
-- Bob	Johnson	New123 Main	NewCity	NewCA	112233	0987654321	newemail@abc.com
-- John	Doe	456 Oak	Towns	NY	12345	5551112222	johndoe@example.com
-- Jane	Smith	789 Pine	Village	TX	54321	9998887777	jane.sm@example.com
-- Michael	Johnson	101 Ave	Ham	FL	32100	7776665555	micl.j@example.com

-- UC_5 : Ability to delete a person using person's name

DELETE FROM ADDRESSBOOK_TABLE
WHERE FIRSTNAME = 'John' AND LASTNAME = 'Smith';

SELECT * FROM ADDRESSBOOK_TABLE;

-- OUTPUT
-- Priyanka	Sengupta	Sec78	Noida	UP	201307	1234567890	psg@gmail.com
-- Bob	Johnson	New123 Main	NewCity	NewCA	112233	0987654321	newemail@abc.com
-- John	Doe	456 Oak	Towns	NY	12345	5551112222	johndoe@example.com
-- Jane	Smith	789 Pine	Village	TX	54321	9998887777	jane.sm@example.com
-- Michael	Johnson	101 Ave	Ham	FL	32100	7776665555	micl.j@example.com

-- UC_6 : Ability to Retrieve Person belonging to a City or State from the Address Book

SELECT *
FROM ADDRESSBOOK_TABLE
WHERE CITY = 'Noida'
OR STATE = 'TX';

-- OUTPUT
-- Priyanka	Sengupta	Sec78	Noida	UP	201307	1234567890	psg@gmail.com
-- Jane	Smith	789 Pine	Village	TX	54321	9998887777	jane.sm@example.com

-- UC_7 : Ability to understand the size of address book by City and State

SELECT CITY, STATE, COUNT(*) AS NumberOfPersons
FROM ADDRESSBOOK_TABLE
WHERE CITY = 'Noida' AND STATE = 'UP'
GROUP BY CITY, STATE;

-- OUTPUT
-- Noida	UP	1

-- UC_8 : Ability to retrieve entries sorted alphabetically by Person’s name for a given city

INSERT INTO ADDRESSBOOK_TABLE(FIRSTNAME, LASTNAME, ADDRESS, CITY, STATE, ZIP, PHONE, EMAIL)
VALUES
	('Abc', 'Xyz', 'New dde', 'Noida', 'UP', '201308', '9876543210', 'new@example.com'),
    ('Hello', 'World', 'New place', 'Noida', 'UP', '201998', '9876544310', 'hello@world.com');
    
SELECT *
FROM ADDRESSBOOK_TABLE
WHERE CITY = 'Noida'
ORDER BY FIRSTNAME, LASTNAME;

-- OUTPUT
-- Abc	Xyz	New dde	Noida	UP	201308	9876543210	new@example.com
-- Hello	World	New place	Noida	UP	201998	9876544310	hello@world.com
-- Priyanka	Sengupta	Sec78	Noida	UP	201307	1234567890	psg@gmail.com

-- UC_9 : Ability to identify each Address Book with name and Type. 

ALTER TABLE ADDRESSBOOK_TABLE
ADD COLUMN FULL_NAME VARCHAR(255),
ADD COLUMN ENTRY_TYPE VARCHAR(50);

UPDATE ADDRESSBOOK_TABLE
SET FULL_NAME = CONCAT(FIRSTNAME, ' ', LASTNAME);

UPDATE ADDRESSBOOK_TABLE
SET ENTRY_TYPE = 'FRIENDS'
WHERE CITY = 'NewCity';

UPDATE ADDRESSBOOK_TABLE
SET ENTRY_TYPE = 'PROFESSIONALS'
WHERE CITY = 'Towns';

UPDATE ADDRESSBOOK_TABLE
SET ENTRY_TYPE = 'FAMILY'
WHERE CITY = 'Noida';

SELECT * FROM ADDRESSBOOK_TABLE;


-- OUTPUT
-- Priyanka	Sengupta	Sec78	Noida	UP	201307	1234567890	psg@gmail.com	Priyanka Sengupta	FAMILY
-- Bob	Johnson	New123 Main	NewCity	NewCA	112233	0987654321	newemail@abc.com	Bob Johnson	FRIENDS
-- John	Doe	456 Oak	Towns	NY	12345	5551112222	johndoe@example.com	John Doe	PROFESSIONALS
-- Jane	Smith	789 Pine	Village	TX	54321	9998887777	jane.sm@example.com	Jane Smith	FAMILY
-- Michael	Johnson	101 Ave	Ham	FL	32100	7776665555	micl.j@example.com	Michael Johnson	FAMILY
-- Abc	Xyz	New dde	Noida	UP	201308	9876543210	new@example.com	Abc Xyz	FAMILY
-- Hello	World	New place	Noida	UP	201998	9876544310	hello@world.com	Hello World	FAMILY	

-- UC_10 : Ability to get number of contact persons i.e. count by type

SELECT ENTRY_TYPE, COUNT(*) AS NumberOfPersons
FROM ADDRESSBOOK_TABLE
GROUP BY ENTRY_TYPE;

-- OUTPUT
-- FAMILY	5
-- FRIENDS	1
-- PROFESSIONALS	1
 

--UC12
-- For ER creation : Draw the ER Diagram for Address Book Service DB

CREATE TABLE PERSON(
	PERSON_ID INT PRIMARY KEY,
    FIRSTNAME VARCHAR(255) NOT NULL,
    LASTNAME VARCHAR(255) NOT NULL,
    PHONE VARCHAR(255),
    EMAIL VARCHAR(255) UNIQUE
);

CREATE TABLE ADDRESS(
	ADDRESS_ID INT PRIMARY KEY,
    STREET VARCHAR(255),
    CITY VARCHAR(255),
    STATE VARCHAR(255),
    ZIPCODE VARCHAR(255)
);

CREATE TABLE PERSONADDRESS(
	PERSON_ID INT,
    ADDRESS_ID INT,
    PRIMARY KEY (PERSON_ID, ADDRESS_ID),
    FOREIGN KEY (PERSON_ID) REFERENCES PERSON(PERSON_ID),
    FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS(ADDRESS_ID)
    );
    
-- copying the data of addressbook_table to another

CREATE TABLE ADDRESSBOOK_TABLE2 AS SELECT * FROM ADDRESSBOOK_TABLE WHERE 1=0;

INSERT INTO ADDRESSBOOK_TABLE2 SELECT * FROM ADDRESSBOOK_TABLE;

SELECT * FROM ADDRESSBOOK_TABLE;
SELECT * FROM ADDRESSBOOK_TABLE2;


--Modify the ADDRESSBOOK_TABLE

ALTER TABLE ADDRESSBOOK_TABLE
ADD COLUMN PERSON_ID INT,
ADD COLUMN ADDRESS_ID INT;

SET SQL_SAFE_UPDATES = 0;

UPDATE ADDRESSBOOK_TABLE AB1
JOIN PERSON P ON AB1.FIRSTNAME = P.FIRSTNAME AND AB1.LASTNAME = P.LASTNAME
JOIN ADDRESS A ON AB1.STATE = A.STATE AND AB1.CITY = A.CITY AND AB1.ZIP = A.ZIPCODE
SET AB1.PERSON_ID = P.PERSON_ID,
	AB1.ADDRESS_ID = A.ADDRESS_ID;
    
ALTER TABLE ADDRESSBOOK_TABLE
DROP COLUMN FIRSTNAME,
DROP COLUMN LASTNAME,
DROP COLUMN STATE,
DROP COLUMN CITY,
DROP COLUMN ZIP;

ALTER TABLE ADDRESSBOOK_TABLE
ADD FOREIGN KEY (PERSON_ID) REFERENCES Person(PERSON_ID),
ADD FOREIGN KEY (ADDRESS_ID) REFERENCES Address(ADDRESS_ID);


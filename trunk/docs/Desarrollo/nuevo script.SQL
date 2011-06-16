USE Tempore
;


DROP TABLE IF EXISTS ProjectState
;
DROP TABLE IF EXISTS PrivilegeRole
;
DROP TABLE IF EXISTS Privilege
;
DROP TABLE IF EXISTS RoleUserProject
;
DROP TABLE IF EXISTS Role
;
DROP TABLE IF EXISTS TaskType
;
DROP TABLE IF EXISTS TaskUser
;
DROP TABLE IF EXISTS ProjectClient
;
DROP TABLE IF EXISTS ClientContact
;
DROP TABLE IF EXISTS Client
;
DROP TABLE IF EXISTS ContactType
;
DROP TABLE IF EXISTS Contact
;
DROP TABLE IF EXISTS Person
;
DROP TABLE IF EXISTS Alert
;
DROP TABLE IF EXISTS User
;
DROP TABLE IF EXISTS UserProject
;
DROP TABLE IF EXISTS Task
;
DROP TABLE IF EXISTS Project
;

CREATE TABLE ProjectState
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	description VARCHAR(100),
	PRIMARY KEY (id)
) 
;


CREATE TABLE PrivilegeRole
(
	roleId BIGINT NOT NULL,
	privilegeId BIGINT NOT NULL,
	PRIMARY KEY (roleId, privilegeId),
	KEY (privilegeId),
	KEY (roleId)
) 
;


CREATE TABLE Privilege
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	PRIMARY KEY (id)
) 
;


CREATE TABLE RoleUserProject
(
	roleId BIGINT NOT NULL,
	userProjectId BIGINT NOT NULL,
	PRIMARY KEY (roleId, userProjectId),
	KEY (userProjectId),
	KEY (roleId)
) 
;


CREATE TABLE Role
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE UQ_Role_name(name)
) 
;


CREATE TABLE TaskType
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(25),
	description VARCHAR(100),
	PRIMARY KEY (id)
) 
;


CREATE TABLE TaskUser
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	userId BIGINT NOT NULL,
	taskId BIGINT NOT NULL,
	hourCount INTEGER NOT NULL,
	date DATE NOT NULL,
	comment VARCHAR(150),
	PRIMARY KEY (id),
	KEY (taskId),
	KEY (userId)
) 
;


CREATE TABLE ProjectClient
(
	projectId BIGINT NOT NULL,
	clientId BIGINT NOT NULL,
	PRIMARY KEY (projectId, clientId),
	KEY (clientId),
	KEY (projectId)
) 
;


CREATE TABLE ClientContact
(
	contactId BIGINT NOT NULL,
	clientId BIGINT NOT NULL,
	PRIMARY KEY (contactId, clientId),
	KEY (contactId),
	KEY (clientId)
) 
;


CREATE TABLE Client
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	address VARCHAR(100),
	country VARCHAR(50),
	state VARCHAR(50),
	zip VARCHAR(15),
	fiscalNumber VARCHAR(50),
	phone VARCHAR(50),
	PRIMARY KEY (id)
) 
;


CREATE TABLE ContactType
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(25),
	description VARCHAR(100),
	PRIMARY KEY (id)
) 
;


CREATE TABLE Contact
(
	userId BIGINT NOT NULL,
	contactTypeId BIGINT,
	PRIMARY KEY (userId)
) 
;


CREATE TABLE Person
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	lastname VARCHAR(50),
	phone VARCHAR(20),
	email VARCHAR(100),
	country VARCHAR(50),
	address VARCHAR(100),
	zip VARCHAR(25),
	PRIMARY KEY (id)
) 
;


CREATE TABLE Alert
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	description VARCHAR(100),
	userProjectId BIGINT,
	PRIMARY KEY (id),
	KEY (userProjectId)
) 
;


CREATE TABLE User
(
	personId BIGINT NOT NULL,
	state CHAR(1),
	username VARCHAR(50),
	password VARCHAR(50),
	PRIMARY KEY (personId),
	KEY (personId)
) 
;


CREATE TABLE UserProject
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	userId BIGINT NOT NULL,
	projectId BIGINT NOT NULL,
	PRIMARY KEY (id),
	KEY (projectId),
	KEY (userId)
) 
;


CREATE TABLE Task
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	projectId BIGINT NOT NULL,
	taskTypeId BIGINT NOT NULL,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(250),
	taskId BIGINT,
	budget INTEGER,
	PRIMARY KEY (id),
	KEY (taskTypeId),
	KEY (projectId),
	KEY (taskId)
) 
;


CREATE TABLE Project
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(25),
	projectStateId BIGINT,
	description VARCHAR(250),
	initDate DATE,
	endDate DATE,
	budget DOUBLE,
	PRIMARY KEY (id),
	UNIQUE UQ_Project_name(name),
	KEY (projectStateId)
) 
;





ALTER TABLE PrivilegeRole ADD CONSTRAINT FK_PrivilegioRol_Privilegio 
	FOREIGN KEY (privilegeId) REFERENCES Privilege (id)
;

ALTER TABLE PrivilegeRole ADD CONSTRAINT FK_PrivilegioRol_Rol 
	FOREIGN KEY (roleId) REFERENCES Role (id)
;

ALTER TABLE RoleUserProject ADD CONSTRAINT FK_RoleUserProject_UserProject 
	FOREIGN KEY (userProjectId) REFERENCES UserProject (id)
;

ALTER TABLE RoleUserProject ADD CONSTRAINT FK_RolUsuario_Rol 
	FOREIGN KEY (roleId) REFERENCES Role (id)
;

ALTER TABLE TaskUser ADD CONSTRAINT FK_TareaUsuario_Tarea 
	FOREIGN KEY (taskId) REFERENCES Task (id)
;

ALTER TABLE TaskUser ADD CONSTRAINT FK_TaskUser_User 
	FOREIGN KEY (userId) REFERENCES User (personId)
;

ALTER TABLE ProjectClient ADD CONSTRAINT FK_ProyectoCliente_Cliente 
	FOREIGN KEY (clientId) REFERENCES Client (id)
;

ALTER TABLE ProjectClient ADD CONSTRAINT FK_ProyectoCliente_Proyecto 
	FOREIGN KEY (projectId) REFERENCES Project (id)
;

ALTER TABLE ClientContact ADD CONSTRAINT FK_ClientContact_Contact 
	FOREIGN KEY (contactId) REFERENCES Contact (userId)
;

ALTER TABLE ClientContact ADD CONSTRAINT FK_ClienteContacto_Cliente 
	FOREIGN KEY (clientId) REFERENCES Client (id)
;

ALTER TABLE Alert ADD CONSTRAINT FK_Alert_UserProject 
	FOREIGN KEY (userProjectId) REFERENCES UserProject (id)
;

ALTER TABLE User ADD CONSTRAINT FK_User_Person 
	FOREIGN KEY (personId) REFERENCES Person (id)
;

ALTER TABLE UserProject ADD CONSTRAINT FK_UserProject_Project 
	FOREIGN KEY (projectId) REFERENCES Project (id)
;

ALTER TABLE UserProject ADD CONSTRAINT FK_UserProject_User 
	FOREIGN KEY (userId) REFERENCES User (personId)
;

ALTER TABLE Task ADD CONSTRAINT FK_Tarea_TipoTarea 
	FOREIGN KEY (taskTypeId) REFERENCES TaskType (id)
;

ALTER TABLE Task ADD CONSTRAINT FK_Task_Project 
	FOREIGN KEY (projectId) REFERENCES Project (id)
;

ALTER TABLE Task ADD CONSTRAINT FK_Task_Task 
	FOREIGN KEY (taskId) REFERENCES Task (id)
;

ALTER TABLE Project ADD CONSTRAINT FK_Project_ProjectState 
	FOREIGN KEY (projectStateId) REFERENCES ProjectState (id)
;

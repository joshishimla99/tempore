CREATE TABLE Report
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name BIGINT,
	x BIGINT,
	y BIGINT,
	PRIMARY KEY (id)
) 
;


CREATE TABLE ConfigAlert
(
	id BIGINT NOT NULL,
	name BIGINT,
	projectId BIGINT,
	PRIMARY KEY (id),
	KEY (projectId)
) 
;


CREATE TABLE ReportVisibility
(
	userProjectId BIGINT NOT NULL,
	reportProjectId BIGINT NOT NULL,
	PRIMARY KEY (userProjectId, reportProjectId),
	KEY (reportProjectId),
	KEY (userProjectId)
) 
;


CREATE TABLE ReportProject
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	projectId BIGINT NOT NULL,
	reportId BIGINT NOT NULL,
	title VARCHAR(50),
	PRIMARY KEY (id),
	KEY (projectId),
	KEY (reportId)
) 
;


CREATE TABLE ProjectState
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	description VARCHAR(100),
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


CREATE TABLE ClientContact
(
	clientId BIGINT NOT NULL,
	userId BIGINT NOT NULL,
	PRIMARY KEY (clientId, userId),
	KEY (userId),
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
	imageName VARCHAR(100),
	PRIMARY KEY (id),
	UNIQUE UQ_Client_imageName(imageName)
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
	imageName VARCHAR(100),
	PRIMARY KEY (id),
	UNIQUE UQ_Person_imageName(imageName)
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
	owner INTEGER DEFAULT 0,
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
	budget FLOAT(0),
	clientId BIGINT NOT NULL,
	PRIMARY KEY (id),
	KEY (clientId),
	KEY (projectStateId)
) 
;





ALTER TABLE ConfigAlert ADD CONSTRAINT FK_ConfigAlert_Project 
	FOREIGN KEY (projectId) REFERENCES Project (id)
;

ALTER TABLE ReportVisibility ADD CONSTRAINT FK_ReportProjectUserProject_ReportProject 
	FOREIGN KEY (reportProjectId) REFERENCES ReportProject (id)
;

ALTER TABLE ReportVisibility ADD CONSTRAINT FK_ReportProjectUserProject_UserProject 
	FOREIGN KEY (userProjectId) REFERENCES UserProject (id)
;

ALTER TABLE ReportProject ADD CONSTRAINT FK_ReportProject_Project 
	FOREIGN KEY (projectId) REFERENCES Project (id)
;

ALTER TABLE ReportProject ADD CONSTRAINT FK_ReportProject_Report 
	FOREIGN KEY (reportId) REFERENCES Report (id)
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

ALTER TABLE ClientContact ADD CONSTRAINT FK_ClientContact_User 
	FOREIGN KEY (userId) REFERENCES User (personId)
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

ALTER TABLE Project ADD CONSTRAINT FK_Project_Client 
	FOREIGN KEY (clientId) REFERENCES Client (id)
;

ALTER TABLE Project ADD CONSTRAINT FK_Project_ProjectState 
	FOREIGN KEY (projectStateId) REFERENCES ProjectState (id)
;

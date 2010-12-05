DROP TABLE IF EXISTS PrivilegioRol
;
DROP TABLE IF EXISTS Privilegio
;
DROP TABLE IF EXISTS RolUsuario
;
DROP TABLE IF EXISTS Rol
;
DROP TABLE IF EXISTS TipoTarea
;
DROP TABLE IF EXISTS TareaUsuario
;
DROP TABLE IF EXISTS ProyectoCliente
;
DROP TABLE IF EXISTS ClienteContacto
;
DROP TABLE IF EXISTS Cliente
;
DROP TABLE IF EXISTS TipoContacto
;
DROP TABLE IF EXISTS Contacto
;
DROP TABLE IF EXISTS Persona
;
DROP TABLE IF EXISTS Puesto
;
DROP TABLE IF EXISTS Usuario
;
DROP TABLE IF EXISTS UsuarioProyecto
;
DROP TABLE IF EXISTS Tarea
;
DROP TABLE IF EXISTS Proyecto
;

CREATE TABLE PrivilegioRol
(
	rolId BIGINT NOT NULL,
	privilegioId BIGINT NOT NULL,
	PRIMARY KEY (rolId, privilegioId),
	KEY (privilegioId),
	KEY (rolId)
) 
;


CREATE TABLE Privilegio
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(25) NOT NULL,
	PRIMARY KEY (id)
) 
;


CREATE TABLE RolUsuario
(
	rolId BIGINT NOT NULL,
	usuarioId BIGINT NOT NULL,
	PRIMARY KEY (rolId, usuarioId),
	KEY (rolId),
	KEY (usuarioId)
) 
;


CREATE TABLE Rol
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(25) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE UQ_Rol_nombre(nombre)
) 
;


CREATE TABLE TipoTarea
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(25),
	descripcion VARCHAR(100),
	PRIMARY KEY (id)
) 
;


CREATE TABLE TareaUsuario
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	usuarioId BIGINT,
	tareaId BIGINT,
	cantHora INTEGER,
	fechaHora TIMESTAMP,
	PRIMARY KEY (id),
	KEY (tareaId),
	KEY (usuarioId)
) 
;


CREATE TABLE ProyectoCliente
(
	proyectoId BIGINT NOT NULL,
	clienteId BIGINT NOT NULL,
	PRIMARY KEY (proyectoId, clienteId),
	KEY (clienteId),
	KEY (proyectoId)
) 
;


CREATE TABLE ClienteContacto
(
	contactoId BIGINT NOT NULL,
	clienteId BIGINT NOT NULL,
	PRIMARY KEY (contactoId, clienteId),
	KEY (clienteId),
	KEY (contactoId)
) 
;


CREATE TABLE Cliente
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(50),
	PRIMARY KEY (id)
) 
;


CREATE TABLE TipoContacto
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(25),
	descripcion VARCHAR(100),
	PRIMARY KEY (id)
) 
;


CREATE TABLE Contacto
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	personaId BIGINT NOT NULL,
	tipoContactoId BIGINT,
	PRIMARY KEY (id),
	KEY (personaId)
) 
;


CREATE TABLE Persona
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(50),
	apellido VARCHAR(50),
	telefono VARCHAR(20),
	direccion VARCHAR(50),
	provicia VARCHAR(50),
	pais VARCHAR(25),
	PRIMARY KEY (id)
) 
;


CREATE TABLE Puesto
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(25) NOT NULL,
	descripcion VARCHAR(100),
	PRIMARY KEY (id)
) 
;


CREATE TABLE Usuario
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	personaId BIGINT NOT NULL,
	PRIMARY KEY (id),
	KEY (personaId)
) 
;


CREATE TABLE UsuarioProyecto
(
	usuarioId BIGINT NOT NULL,
	proyectoId BIGINT NOT NULL,
	puestoId BIGINT,
	PRIMARY KEY (usuarioId, proyectoId),
	KEY (proyectoId),
	KEY (puestoId),
	KEY (usuarioId)
) 
;


CREATE TABLE Tarea
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	proyectoId BIGINT,
	tipoTareaId BIGINT,
	nombre VARCHAR(50) NOT NULL,
	descripcion VARCHAR(100),
	PRIMARY KEY (id),
	KEY (tipoTareaId)
) 
;


CREATE TABLE Proyecto
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(25),
	PRIMARY KEY (id),
	UNIQUE UQ_Proyecto_nombre(nombre)
) 
;





ALTER TABLE PrivilegioRol ADD CONSTRAINT FK_PrivilegioRol_Privilegio 
	FOREIGN KEY (privilegioId) REFERENCES Privilegio (id)
;

ALTER TABLE PrivilegioRol ADD CONSTRAINT FK_PrivilegioRol_Rol 
	FOREIGN KEY (rolId) REFERENCES Rol (id)
;

ALTER TABLE RolUsuario ADD CONSTRAINT FK_RolUsuario_Rol 
	FOREIGN KEY (rolId) REFERENCES Rol (id)
;

ALTER TABLE RolUsuario ADD CONSTRAINT FK_RolUsuario_Usuario 
	FOREIGN KEY (usuarioId) REFERENCES Usuario (id)
;

ALTER TABLE TareaUsuario ADD CONSTRAINT FK_TareaUsuario_Tarea 
	FOREIGN KEY (tareaId) REFERENCES Tarea (id)
;

ALTER TABLE TareaUsuario ADD CONSTRAINT FK_TareaUsuario_Usuario 
	FOREIGN KEY (usuarioId) REFERENCES Usuario (id)
;

ALTER TABLE ProyectoCliente ADD CONSTRAINT FK_ProyectoCliente_Cliente 
	FOREIGN KEY (clienteId) REFERENCES Cliente (id)
;

ALTER TABLE ProyectoCliente ADD CONSTRAINT FK_ProyectoCliente_Proyecto 
	FOREIGN KEY (proyectoId) REFERENCES Proyecto (id)
;

ALTER TABLE ClienteContacto ADD CONSTRAINT FK_ClienteContacto_Cliente 
	FOREIGN KEY (clienteId) REFERENCES Cliente (id)
;

ALTER TABLE ClienteContacto ADD CONSTRAINT FK_ClienteContacto_Contacto 
	FOREIGN KEY (contactoId) REFERENCES Contacto (id)
;

ALTER TABLE Contacto ADD CONSTRAINT FK_Contacto_Persona 
	FOREIGN KEY (personaId) REFERENCES Persona (id)
;

ALTER TABLE Usuario ADD CONSTRAINT FK_Usuario_Persona 
	FOREIGN KEY (personaId) REFERENCES Persona (id)
;

ALTER TABLE UsuarioProyecto ADD CONSTRAINT FK_UsuarioProyecto_Proyecto 
	FOREIGN KEY (proyectoId) REFERENCES Proyecto (id)
;

ALTER TABLE UsuarioProyecto ADD CONSTRAINT FK_UsuarioProyecto_Puesto 
	FOREIGN KEY (puestoId) REFERENCES Puesto (id)
;

ALTER TABLE UsuarioProyecto ADD CONSTRAINT FK_UsuarioProyecto_Usuario 
	FOREIGN KEY (usuarioId) REFERENCES Usuario (id)
;

ALTER TABLE Tarea ADD CONSTRAINT FK_Tarea_TipoTarea 
	FOREIGN KEY (tipoTareaId) REFERENCES TipoTarea (id)
;

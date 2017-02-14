-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- PostgreSQL version: 9.3
-- Project Site: pgmodeler.com.br
-- Model Author: ---

SET check_function_bodies = false;
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: consultorio_lgm | type: DATABASE --
-- CREATE DATABASE consultorio_lgm
-- ;
-- -- ddl-end --
-- 

-- object: public.paciente | type: TABLE --
CREATE TABLE public.paciente(
	id bigserial NOT NULL,
	nombre varchar(200) NOT NULL,
	apellido_paterno varchar(200) NOT NULL,
	apellido_materno varchar(200),
	genero_femenino boolean NOT NULL DEFAULT false,
	edad integer NOT NULL,
	direccion varchar(255) NOT NULL,
	telefono varchar(10),
	religion varchar(50),
	id_escolaridad integer NOT NULL,
	id_estado_civil integer NOT NULL,
	id_antecedentes bigint NOT NULL,
	id_antecedentes_ginecologicos bigint,
	fecha_nacimiento date,
	CONSTRAINT pk_paciente PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.cat_escolaridad | type: TABLE --
CREATE TABLE public.cat_escolaridad(
	id serial NOT NULL,
	descripcion varchar(50) NOT NULL,
	CONSTRAINT pk_catescolaridad PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.cat_estado_civil | type: TABLE --
CREATE TABLE public.cat_estado_civil(
	id serial NOT NULL,
	descripcion varchar(50) NOT NULL,
	CONSTRAINT pk_catedocivil PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.antecedentes_generales | type: TABLE --
CREATE TABLE public.antecedentes_generales(
	id bigserial NOT NULL,
	carga_genetica_paternal text,
	carga_genetica_maternal text,
	vivienda varchar(500),
	alimentacion varchar(500),
	higiene varchar(500),
	toxicomanias varchar(255),
	quirurgicas varchar(500),
	hospitalizaciones boolean DEFAULT false,
	motivo_hospitalizacion varchar(500),
	transfuciones boolean DEFAULT false,
	motivo_transfuciones varchar(500),
	traumaticas boolean DEFAULT false,
	motivo_traumaticas varchar(500),
	alergias boolean,
	descripcion_alergias varchar(500),
	id_grupo_sanguineo integer NOT NULL,
	rh_positivo boolean DEFAULT true,
	CONSTRAINT pk_antecedentes PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.cat_enfermedades_cronicas | type: TABLE --
CREATE TABLE public.cat_enfermedades_cronicas(
	id serial NOT NULL,
	nombre varchar(50) NOT NULL,
	CONSTRAINT pk_cat_enf_cronicas PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.rel_antecedentes_enfermedades | type: TABLE --
CREATE TABLE public.rel_antecedentes_enfermedades(
	id bigserial NOT NULL,
	id_antecedentes bigint NOT NULL,
	id_enfermedad integer NOT NULL,
	CONSTRAINT pk_rel_antecedentes_enfermedades PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.cat_grupo_sanguineo | type: TABLE --
CREATE TABLE public.cat_grupo_sanguineo(
	id serial NOT NULL,
	nombre varchar(2) NOT NULL,
	CONSTRAINT pk_cat_gpo_sanguineo PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.antecedentes_ginecologicos | type: TABLE --
CREATE TABLE public.antecedentes_ginecologicos(
	id bigserial NOT NULL,
	menarca integer,
	ciclo_menstrual_regular boolean DEFAULT true,
	duracion_ciclo_menstrual varchar(50),
	ivsa integer,
	numero_parejas integer,
	id_cat_mpf integer,
	exploracion_mama boolean,
	papanicolaou boolean,
	resultado_papanicolaou varchar(500),
	gesta smallint,
	parto smallint,
	abortos smallint,
	cesarea smallint,
	fecha_ultima_menstruacion date,
	fecha_probable_parto date,
	complicaciones_embarazo varchar(500),
	CONSTRAINT pk_antecedentes_ginecologicos PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.cat_mpf | type: TABLE --
CREATE TABLE public.cat_mpf(
	id serial NOT NULL,
	nombre varchar(50) NOT NULL,
	CONSTRAINT pk_cat_mpf PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.consulta | type: TABLE --
CREATE TABLE public.consulta(
	id bigserial NOT NULL,
	motivo_consulta varchar(500) NOT NULL,
	fecha date NOT NULL,
	id_paciente bigint NOT NULL,
	peso float,
	ta_sistolica integer,
	ta_diastolica integer,
	diagnostico varchar(500) NOT NULL,
	talla float,
	frecuencia_cardiaca integer,
	frecuencia_respiratoria integer,
	glucosa float,
	exploracion_fisica varchar(500),
	laboratorio_gabinete varchar(500),
	plan varchar(500),
	proxima_cita date,
	temperatura float,
	CONSTRAINT pk_consulta PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.tratamiento | type: TABLE --
CREATE TABLE public.tratamiento(
	id bigserial NOT NULL,
	id_consulta bigint NOT NULL,
	id_medicamento bigint NOT NULL,
	dosis varchar(100) NOT NULL,
	horario varchar(100) NOT NULL,
	duracion varchar(100) NOT NULL,
	recomendaciones varchar(255),
	CONSTRAINT pk_tratamiento PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.medicamento | type: TABLE --
CREATE TABLE public.medicamento(
	id bigserial NOT NULL,
	nombre varchar(100) NOT NULL,
	presentacion integer NOT NULL,
	antibiotico boolean NOT NULL,
	CONSTRAINT pk_medicamento PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.cat_presentacion | type: TABLE --
CREATE TABLE public.cat_presentacion(
	id serial NOT NULL,
	descripcion varchar(100) NOT NULL,
	CONSTRAINT pk_catpresentacion PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.usuario | type: TABLE --
CREATE TABLE public.usuario(
	id serial NOT NULL,
	usuario varchar(255) NOT NULL,
	contrasena varchar(255) NOT NULL,
	nombre varchar(255) NOT NULL,
	apellidopaterno varchar(255) NOT NULL,
	apellidomaterno varchar(255),
	iniciales varchar(10),
	correo varchar(50),
	CONSTRAINT pk_usuario PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.configuracion | type: TABLE --
CREATE TABLE public.configuracion(
	id serial NOT NULL,
	parametro varchar(200) NOT NULL,
	valor varchar(500) NOT NULL,
	CONSTRAINT pk_configuracion PRIMARY KEY (id)

);
-- ddl-end --
-- object: fk_paciente_escolaridad | type: CONSTRAINT --
ALTER TABLE public.paciente ADD CONSTRAINT fk_paciente_escolaridad FOREIGN KEY (id_escolaridad)
REFERENCES public.cat_escolaridad (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_paciente_edocivil | type: CONSTRAINT --
ALTER TABLE public.paciente ADD CONSTRAINT fk_paciente_edocivil FOREIGN KEY (id_estado_civil)
REFERENCES public.cat_estado_civil (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_paciente_antecedentes | type: CONSTRAINT --
ALTER TABLE public.paciente ADD CONSTRAINT fk_paciente_antecedentes FOREIGN KEY (id_antecedentes)
REFERENCES public.antecedentes_generales (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_paciente_ginecologicos | type: CONSTRAINT --
ALTER TABLE public.paciente ADD CONSTRAINT fk_paciente_ginecologicos FOREIGN KEY (id_antecedentes_ginecologicos)
REFERENCES public.antecedentes_ginecologicos (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_cat_gpo_sanguineo | type: CONSTRAINT --
ALTER TABLE public.antecedentes_generales ADD CONSTRAINT fk_cat_gpo_sanguineo FOREIGN KEY (id_grupo_sanguineo)
REFERENCES public.cat_grupo_sanguineo (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_rel_antecedentes | type: CONSTRAINT --
ALTER TABLE public.rel_antecedentes_enfermedades ADD CONSTRAINT fk_rel_antecedentes FOREIGN KEY (id_antecedentes)
REFERENCES public.antecedentes_generales (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_rel_enfermedades | type: CONSTRAINT --
ALTER TABLE public.rel_antecedentes_enfermedades ADD CONSTRAINT fk_rel_enfermedades FOREIGN KEY (id_enfermedad)
REFERENCES public.cat_enfermedades_cronicas (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_ago_catmpf | type: CONSTRAINT --
ALTER TABLE public.antecedentes_ginecologicos ADD CONSTRAINT fk_ago_catmpf FOREIGN KEY (id_cat_mpf)
REFERENCES public.cat_mpf (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_consulta_paciente | type: CONSTRAINT --
ALTER TABLE public.consulta ADD CONSTRAINT fk_consulta_paciente FOREIGN KEY (id_paciente)
REFERENCES public.paciente (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_tratamiento_consulta | type: CONSTRAINT --
ALTER TABLE public.tratamiento ADD CONSTRAINT fk_tratamiento_consulta FOREIGN KEY (id_consulta)
REFERENCES public.consulta (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_tratamiento_medicamento | type: CONSTRAINT --
ALTER TABLE public.tratamiento ADD CONSTRAINT fk_tratamiento_medicamento FOREIGN KEY (id_medicamento)
REFERENCES public.medicamento (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --


-- object: fk_medicamento_presentacion | type: CONSTRAINT --
ALTER TABLE public.medicamento ADD CONSTRAINT fk_medicamento_presentacion FOREIGN KEY (presentacion)
REFERENCES public.cat_presentacion (id) MATCH FULL
ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;
-- ddl-end --




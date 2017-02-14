DELETE FROM usuario;
DELETE FROM medicamento;
DELETE FROM paciente;
DELETE FROM antecedentes_ginecologicos;
DELETE FROM antecedentes_generales;
DELETE FROM cat_enfermedades_cronicas;
DELETE FROM cat_mpf;
DELETE FROM cat_presentacion;
DELETE FROM cat_escolaridad;
DELETE FROM cat_estado_civil;
DELETE FROM cat_grupo_sanguineo;
DELETE FROM configuracion;

ALTER SEQUENCE usuario_id_seq RESTART WITH 2;
ALTER SEQUENCE cat_enfermedades_cronicas_id_seq RESTART WITH 6;
ALTER SEQUENCE cat_mpf_id_seq RESTART WITH 8;
ALTER SEQUENCE cat_presentacion_id_seq RESTART WITH 5;
ALTER SEQUENCE cat_escolaridad_id_seq RESTART WITH 7;
ALTER SEQUENCE cat_estado_civil_id_seq RESTART WITH 5;
ALTER SEQUENCE cat_grupo_sanguineo_id_seq RESTART WITH 5;
ALTER SEQUENCE paciente_id_seq RESTART WITH 1;
ALTER SEQUENCE antecedentes_ginecologicos_id_seq RESTART WITH 1;
ALTER SEQUENCE antecedentes_generales_id_seq RESTART WITH 1;
ALTER SEQUENCE configuracion_id_seq RESTART WITH 1;

--Usuarios
INSERT INTO usuario (id, usuario, contrasena, nombre, apellidopaterno, apellidomaterno, iniciales, correo) 
	VALUES (1, 'Z6by4SNYKmzDjvtOGpfumA==', 'xi2/GcJULaBnwocpbVq7lS2ynA06dBZU4CpjRAjRh+U=', 'zGE9cXrfnOCUsQv9vKVHVQ==', '5yd/Qe44hEXm0tP2h7oPWA==', 'beO8AKj8DhDU5cZZnTzpRw==', 'lgm', NULL);

INSERT INTO cat_enfermedades_cronicas (id, nombre)
        VALUES (1, 'Diabetes'),
                (2, 'Hipertension'),
                (3, 'Colesterol'),
                (4, 'Cancer'),
                (5, 'Artritis');

INSERT INTO cat_mpf (id, nombre)
        VALUES (0, 'Ninguno'),
                (1, 'Hormonal Oral'),
                (2, 'Hormonal Inyectable'),
                (3, 'Parche'),
                (4, 'DIU'),
                (5, 'Implante'),
                (6, 'Preservativo'),
                (7, 'Natural');

INSERT INTO cat_presentacion (id, descripcion)
        VALUES (1, 'Jarabe'),
                (2, 'Tabletas'),
                (3, 'Inyeccion'),
                (4, 'Capsulas');

INSERT INTO cat_escolaridad (id, descripcion)
        VALUES (0, 'Ninguna'),
                (1, 'Primaria'),
                (2, 'Secundaria'),
                (3, 'Preparatoria'),
                (4, 'Licenciatura'),
                (5, 'Postgrado');

INSERT INTO cat_estado_civil (id, descripcion)
        VALUES (1, 'Soltero(a)'),
                (2, 'Casado(a)'),
                (3, 'Divorciado(a)'),
                (4, 'Viudo(a)');

INSERT INTO cat_grupo_sanguineo (id, nombre)
        VALUES (1, 'A'),
                (2, 'B'),
                (3, 'AB'),
                (4, 'O'),
                (5, 'SD';

INSERT INTO configuracion (id, parametro, valor)
        VALUES (1, 'PARAM_RUTA_RECETAS', 'C:\\recetas_consultorio\\');
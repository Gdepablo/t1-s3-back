/*
INSERT INTO `socios`.`categoria` (id, `descripcion`, `prioridad`) VALUES (1,'alta', '1');
INSERT INTO `socios`.`categoria` (id, `descripcion`, `prioridad`) VALUES (2,'media', '2');
INSERT INTO `socios`.`categoria` (id, `descripcion`, `prioridad`) VALUES (3,'baja', '3');

INSERT INTO `socios`.`socio` (id,denominacion, descripcion, fecha_alta, logo, mail, telefono, tipo, web, categoria) values (1,'Bimbo', 'Empresa de pan', '2023-09-20', 'https://www.bimbo.com', 'bimbo@gmail.com', '+54 54745125', 'EMPRESA', 'www.bimbo.com', 'COMITE_EJECUTIVO');
INSERT INTO `socios`.`socio` (id,denominacion, descripcion, fecha_alta, logo, mail, telefono, tipo, web, categoria) values (2,'Coca-Cola', 'Empresa de Gaseosas', '2015-09-18', 'https://www.cocacola.com', 'cocacola@gmail.com', '+54 22542154', 'EMPRESA', 'www.cocacola.com', 'EMPRESAS');
*/

INSERT INTO socios.socio (fecha_alta, descripcion, denominacion, logo, mail, telefono, tipo, web) VALUES ('2018-09-28', 'Empresa de gaseosas', 'Coca-cola', 'www.cocacola.com', 'cocacola@gmail.com', '+11 54 42452124', 'EMPRESA', 'www.cocacola.com');
INSERT INTO socios.socio (fecha_alta, descripcion, denominacion, logo, mail, telefono, tipo, web) VALUES ('2006-09-22', 'Empresa de pan', 'Bimbo', 'www.bimbo.com', 'bimbo@gmail.com', '+11 54 49453924', 'EMPRESA', 'www.bimbo.com');

INSERT INTO socios.categoria (id_socio, categoria) VALUES (1, 'CAMARAS');
INSERT INTO socios.categoria (id_socio, categoria) VALUES (2, 'EMPRESAS');
INSERT INTO socios.categoria (id_socio, categoria) VALUES (2, 'LABORALES');

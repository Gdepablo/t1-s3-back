INSERT INTO `socios`.`categoria` (id, `descripcion`, `prioridad`) VALUES (1,'alta', '1');
INSERT INTO `socios`.`categoria` (id, `descripcion`, `prioridad`) VALUES (2,'media', '2');
INSERT INTO `socios`.`categoria` (id, `descripcion`, `prioridad`) VALUES (3,'baja', '3');

insert into `socios`.`socio` (id,denominacion, descripcion, fecha_alta, logo, mail, telefono, tipo, web, categoria_id) values (1,'Bimbo', 'Empresa de pan', '2023-09-20', 'https://www.bimbo.com', 'bimbo@gmail.com', '+54 54745125', 'EMPRESA', 'www.bimbo.com', 1);
insert into `socios`.`socio` (id,denominacion, descripcion, fecha_alta, logo, mail, telefono, tipo, web, categoria_id) values (2,'Coca-Cola', 'Empresa de Gaseosas', '2015-09-18', 'https://www.cocacola.com', 'cocacola@gmail.com', '+54 22542154', 'EMPRESA', 'www.cocacola.com', 2);

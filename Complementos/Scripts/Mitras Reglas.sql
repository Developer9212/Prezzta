
DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'apertura_perfil_1_30302';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1,dato2,dato3,dato4,dato5) VALUES ('prezzta','apertura_perfil_1_30302','datos para aperturar el prestamo','1300','30301','2','31','10');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'apertura_perfil_2_30302';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1,dato2,dato3,dato4) VALUES ('prezzta','apertura_perfil_2_30302','datos para aperturar el prestamo','1','110','30302','1');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'apertura_prestamo_1';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1) VALUES ('prezzta','apertura_prestamo_1','datos para aperturar el prestamo','30302');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'apertura_prestamo_2';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1) VALUES ('prezzta','apertura_prestamo_2','datos para aperturar el prestamo complemento','30302');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'comision';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1) VALUES ('prezzta','comision','Total de comision por servicio','0');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'concepto_poliza';
INSERT INTO tablas (idtabla,idelemento,nombre,dato2) VALUES ('prezzta','concepto_poliza','Concepto de la poliza','Prestamo en Mitras Movil');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'gastos';
INSERT INTO tablas (idtabla,idelemento,nombre,dato2) VALUES ('prezzta','gastos','alimen|serv|vestido_cal|transporte|escuela|deudas','gastos_tipo1,gastos_tipo2,gastos_tipo3,gastos_tipo4,gastos_tipo5,gastos_tipo6');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'gastos_alimentacion';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1) VALUES ('prezzta','gastos_alimentacion','dato 1,columna a tomar de la tabla socioeconomicos','gastos_tipo1');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'gastos_servicio';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1) VALUES ('prezzta','gastos_servicio','dato 1,columna a tomar de la tabla socioeconomicos','gastos_tipo2');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'nota_comision';
INSERT INTO tablas (idtabla,idelemento,nombre,dato2) VALUES ('prezzta','nota_comision','Mensaje inicial para comision','Comision a cobrar por servicio @comision@');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'nota_dispersion';
INSERT INTO tablas (idtabla,idelemento,nombre,dato2) VALUES ('prezzta','nota_dispersion','Mensaje inicial para dispersion','El monto total a dispersar sera @dispersion@');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'nota_montos_atrasado';
INSERT INTO tablas (idtabla,idelemento,nombre,dato2) VALUES ('prezzta','nota_montos_atrasado','Mensaje inicial para dispersion','El monto total atrasado(capital+intereses) es de @atraso@');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'nota_renovacion';
INSERT INTO tablas (idtabla,idelemento,nombre,dato2) VALUES ('prezzta','nota_renovacion','Mensaje inicial para renovacion','El monto a renovar sera por @renovacion@');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'parte_social';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1,dato2) VALUES ('prezzta','parte_social','Monto para validacion de la parte social','101','1000');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'producto_comision';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1) VALUES ('prezzta','producto_comision','Producto a donde se abonara la comision','5037');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'producto_para_dispersion';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1) VALUES ('prezzta','producto_para_dispersion','Producto a donde se mandara el monto solicitado','130');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'texto_sms_declinacion';
INSERT INTO tablas (idtabla,idelemento,nombre,dato2) VALUES ('prezzta','texto_sms_declinacion','texto sms para declinar','Se ha declinado la solicitud');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'texto_sms_dispersion';
INSERT INTO tablas (idtabla,idelemento,nombre,dato2) VALUES ('prezzta','texto_sms_dispersion','texto sms para dispersion','Se ha depositado a tarjeta de debito un total de @monto@ por concetpo de solictud de prestamo');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'user-ws';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1,dato2) VALUES ('prezzta','user-ws','Usuario para autenticar mediante web service','testing','testing');

DELETE FROM tablas WHERE idtabla = 'prezzta' and idelemento = 'usuario';
INSERT INTO tablas (idtabla,idelemento,dato1) VALUES ('prezzta','usuario','1300');

DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='minimo_solicitud';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1,dato2)VALUES('prezzta','minimo_solicitud','Configuracion minimos para solicitud','500.00','12');

DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='monto_maximo_solicitud';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1,dato2)VALUES('prezzta','monto_maximo_solicitud','Maximo cuando funcion devuelva mayor a 100000','100000','84');

DELETE FROM tablas WHERE idtabla='prezzta' AND idelemento='sopar';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1)VALUES('prezzta','sopar','Bloqueo en tabla sopar evitar soli.','bloqueo_solicitud');


delete from tablas where idtabla='prezzta' and idelemento='horario_actividad';
insert into tablas(idtabla,idelemento,dato1,dato2,dato3) values('prezzta','horario_actividad','06:00','21:00','0|1|2|3|4|5|6');

delete from tablas where idtabla='prezzta' and idelemento='horario_actividad_creditos_backend';
insert into tablas(idtabla,idelemento,dato1,dato2,dato3,dato4) values('prezzta','horario_actividad_creditos_backend','09:00','17:30','1|2|3|4|5|6','13:00');/*Dato4 = hora cierre sabados*/


DELETE FROM tablas WHERE idtabla='prezzta' AND idelemento='sopar';
INSERT INTO tablas(idtabla,idelemento,dato1)VALUES('prezzta','sopar','pruebas_controladas_sopar');




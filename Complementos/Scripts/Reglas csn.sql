
/*Parametrizacion que ayuda a generar el token para authenticar el ws*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='user-ws';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1,dato2) VALUES('prezzta','user-ws','Usuario para autenticar mediante web service','testing','testing');

/*El monto de la parte social que debe tener el socio para considerarlo activo*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='parte_social';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1,dato2) VALUES('prezzta','parte_social','Monto para validacion de la parte social','101','1000');

/*En el dato1 se debe poner la columna de la tabla socieconomicos que lleva los gastos de alimentacion*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='gastos_alimentacion';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1) VALUES('prezzta','gastos_alimentacion','dato 1,columna a tomar de la tabla socioeconomicos','gastos_tipo1');

/*En el dato1 se debe poner la columna de la tabla socieconomicos que lleva los gastos de servicio*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='gastos_servicio';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1) VALUES('prezzta','gastos_servicio','dato 1,columna a tomar de la tabla socioeconomicos','gastos_tipo2');

/*En el dato 1 poner de que manera tienen ordenado sus gastos*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='gastos';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato2) VALUES('prezzta','gastos','alimen|serv|vestido_cal|transporte|escuela|deudas','gastos_tipo1,gastos_tipo2,gastos_tipo3,gastos_tipo4,gastos_tipo5,gastos_tipo6');

/*Producto a donde se dispersara el prestamo*/
DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='producto_para_dispersion';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1)VALUES('prezzta','producto_para_dispersion','Producto a donde se mandara el monto solicitado','110');


/*Comision por uso de servicio*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='comision';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1) VALUES('prezzta','comision','Total de comision por servicio','89');

/*Mensaje que se muestra en web service 1 en el atributo nota para la comision*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='nota_comision';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato2) VALUES('prezzta','nota_comision','Mensaje inicial para comision','Comision a cobrar por servicio @comision@');

/*Mensaje que se muestara en web service 1 en el atributo nota para la renovacion*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='nota_renovacion';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato2) VALUES('prezzta','nota_renovacion','Mensaje inicial para renovacion','El monto a renovar sera por @renovacion@');

/*Mensaje que se muestra en web service 1 en el atributo nota para la dispersion*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='nota_dispersion';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato2) VALUES('prezzta','nota_dispersion','Mensaje inicial para dispersion','El monto total a dispersar sera @dispersion@');

/*Producto a donde se abonara la comision*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='producto_comision';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1) VALUES('prezzta','producto_comision','Producto a donde se abonara la comision','5037');

/*Mensaje que se muestra en web service 1 en el atributo nota para el monto atrasado*/
DELETE FROM TABLAS WHERE idtabla = 'prezzta' AND idelemento='nota_montos_atrasado';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato2) VALUES('prezzta','nota_montos_atrasado','Mensaje inicial para dispersion','El monto total atrasado(capital+intereses) es de @atraso@');

/*Datos para aperturar el folio activacion
  1.- Dato1 : Usuario
  2.- Dato2 : Origen Sucursal
  3.- Dato3 : Tipo amortizacion
  4.- Dato4 : Periodo abonos (default 0)
  5.- Dato5 : Id finalidad(Del catalogo finalidades)*/
DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='apertura_perfil_1_32644';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1,dato2,dato3,dato4,dato5) VALUES('prezzta','apertura_perfil_1_32644','datos para aperturar el prestamo','999','30287','2','0','71');

/*Datos para aperturar folio activacion
  1.- Dato1 : Estatus (default 1)
  2.- Dato2 : idproducto garantia(default 110)
  3.- Dato3 : idproducto activacion
  4.- Dato4 : Switch renovacion 0:no 1:si*/
DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='apertura_perfil_2_32644';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1,dato2,dato3,dato4)VALUES('prezzta','apertura_perfil_2_32644','datos para aperturar el prestamo','1','110','32644','0');


/*Datos para activar folio renovacion*/
/*
  1.- Dato1 : Usuario
  2.- Dato2 : Origen Sucursal
  3.- Dato3 : Tipo amortizacion
  4.- Dato4 : Periodo abonos (default 0)
  5.- Dato5 : Id finalidad(Del catalogo finalidades)*/
DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='apertura_perfil_1_32664';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1,dato2,dato3,dato4,dato5)VALUES('prezzta','apertura_perfil_1_32664','datos para renovar aperturar el prestamo','999','30287','2','0','71');

/*Datos para activar folio renovacion*/
/*
  1.- Dato1 : Estatus (default 1)
  2.- Dato2 : idproducto garantia(default 110)
  3.- Dato3 : idproducto activacion
  4.- Dato4 : Switch renovacion 0:no 1:si*/
DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='apertura_perfil_2_32664';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1,dato2,dato3,dato4) VALUES('prezzta','apertura_perfil_2_32664','datos para renovar el prestamo','1','110','32664','1');

/*Texto para sms dispersion*/
DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='texto_sms_dispersion';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato2) VALUES('prezzta','texto_sms_dispersion','texto sms para dispersion','Se ha depositado a tarjeta de debito un total de @monto@ por concetpo de solictud de prestamo');


/*Activar uso Tdd*/
DELETE FROM tablas WHERE idtabla='prezzta' AND idelemento='activa_tdd';
INSERT INTO tablas(idtabla,idelemento,dato1) VALUES('prezzta','activa_tdd','1');

/*Url tdd: porque el proyecto de consumir tdd esta por aparte entonces tenemos que consumir desde el proyecto de prezzta*/
DELETE FROM tablas WHERE idtabla='prezzta' AND idelemento='url_tdd';
INSERT INTO tablas(idtabla,idelemento,dato2)VALUES('prezzta','url_tdd','https://csn.coop:8085/WSDL_TDD_CSN/');

/*Servicios siscore extra solo se usaen csn*/
INSERT INTO tablas(idtabla,idelemento,dato1,dato2)VALUES('prezzta','url_servicios_score','tu url','tu puerto');


/*Usuario para operar(Generacion de polizas)*/
DELETE FROM tablas WHERE idtabla='prezzta' AND idelemento='usuario';
INSERT INTO tablas(idtabla,idelemento,dato1) VALUES('prezzta','usuario','999');


/*Texto para sms dispersion*/
DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='texto_sms_declinacion';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato2) VALUES('prezzta','texto_sms_declinacion','texto sms para declinar','Se ha declinado la solicitud');


/*Productos que se validan funcion para obtener minimos y maximos que se le puede entregar al socio*/
/*Activacion*/
DELETE FROM tablas WHERE idtabla='prezzta' AND idelemento='apertura_prestamo_1';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1) VALUES('prezzta','apertura_prestamo_1','datos para aperturar el prestamo','32644');

/*Renovacion*/
DELETE FROM tablas WHERE idtabla='prezzta' AND idelemento='apertura_prestamo_2';
INSERT INTO tablas (idtabla,idelemento,nombre,dato1) VALUES('prezzta','apertura_prestamo_2','datos para aperturar el prestamo complemento','32664');


/*Plazo y monto minimo para solicitud*/
DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='minimo_solicitud';
INSERT INTO tablas(idtabla,idelemento,nombre,dato1,dato2)VALUES('prezzta','minimo_solicitud','Configuracion minimos para solicitud','500.00','12');

/*Monto maximo si la funcion devuelve mayor a 100000*/
DELETE FROM TABLAS WHERE idtabla='prezzta' AND idelemento='monto_maximo_solicitud';
INSERT INTO TABLAS(idtabla,idelemento,nombre,dato1,dato2)VALUES('prezzta','monto_maximo_solicitud','Maximo cuando funcion devuelva mayor a 100000','100000','78');


delete from tablas where idtabla='prezzta' and idelemento='horario_actividad';
insert into tablas(idtabla,idelemento,dato1,dato2,dato3) values('prezzta','horario_actividad','06:00','21:00','0|1|2|3|4|5|6');

delete from tablas where idtabla='prezzta' and idelemento='horario_actividad_creditos_backend';
insert into tablas(idtabla,idelemento,dato1,dato2,dato3,dato4) values('prezzta','horario_actividad_creditos_backend','09:00','17:30','1|2|3|4|5|6','13:00');/*Dato4 = hora cierre sabados*/

DELETE FROM tablas WHERE idtabla='prezzta' AND idelemento='prezzta_empleados';
INSERT INTO tablas(idtabla,idelemento,dato1,dato2) VALUES('prezzta','prezzta_empleados','0','pruebas_controladas_sopar');














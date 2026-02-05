

 DROP TABLE IF EXISTS tmp_prestamos_apertura_prezzta;
 CREATE TABLE tmp_prestamos_apertura_prezzta(

 idorigen        integer               ,
 idgrupo         integer               ,
 idsocio         integer               ,
 montoalcanzado  numeric               ,
 tipoapertura    character varying(45) ,
 montorenovar    numeric               ,
 adispersar      numeric               ,
 atrasado        numeric               ,
 idproducto      integer               ,
 opaactivo       character varying(43) ,
 idorigenp       integer               ,
 gastos_pagar    numeric               );


DROP TABLE IF EXISTS users;
CREATE TABLE users(id integer,username varchar(45),password text,create_at date);
INSERT INTO users(1,'prezzta-ws','prezzta-ws',(SELECT now());



DROP TABLE IF EXISTS plazos;
CREATE TABLE plazos(
    
   id Integer,
   montominimo numeric,
   montomaximo numeric,
   plazos varchar(50),

   primary key(id)
);

DROP TABLE IF EXISTS comision_prezzta;
CREATE TABLE comision_prezzta(

idproducto integer,
descripcion text,
porcentaje_comision numeric,

primary key(idproducto)
);


030220 33044 00000904
create or replace function
prezzta_servicio_activo_inactivo () returns boolean as $$
declare
  d_fecha_servidor  date;
  d_fecha_origenes  date;
  b_estatus_oper    boolean;
  t_hora_ini        text;
  t_hora_fin        text;
  t_dow_operables   text;
  dia_hoy      integer;
  t_hora_backend text;

begin
  select
  into   t_hora_ini, t_hora_fin ,t_dow_operables,t_hora_backend dato1, dato2 , dato3 , dato4
  from   tablas
  where  idtabla = 'prezzta' and idelemento = 'horario_actividad';
  
  if sai_findstr(t_hora_ini,':') = 0 or sai_findstr(t_hora_fin,':') = 0 or
     split_part(t_hora_ini,':',1) > split_part(t_hora_fin,':',1)
  then
    raise notice 'MAL DEFINIDAS LAS HORAS EN LA TABLA: prezzta / hora_actividad';
    return NULL;
  end if;

  d_fecha_servidor := now();
  raise notice 'Hora en el servidor:%',now();
  select
  into   d_fecha_origenes, b_estatus_oper date(fechatrabajo), estatus
  from   origenes
  limit  1;


  if t_dow_operables is not null and trim(t_dow_operables) != ''
  then
    if (sai_findstr(t_dow_operables,'|') = 0 and es_numero(t_dow_operables)) or
       sai_findstr(t_dow_operables,'|') > 0
    then
     b_estatus_oper := sai_texto1_like_texto2(extract(dow from d_fecha_origenes)::text,NULL,t_dow_operables,'|') > 0;    
    end if;
  end if;

  raise notice 'Hora en el servidorrrrr:%',current_time::time||',Hora trabajo';
  return not (date(d_fecha_servidor) != d_fecha_origenes or
              current_time::time not between t_hora_ini::time and t_hora_fin::time or
              not b_estatus_oper);
end;
$$ language 'plpgsql';



create or replace function
prezzta_servicio_activo_inactivo_backend() returns boolean as $$
declare
  d_fecha_servidor  date;
  d_fecha_origenes  date;
  b_estatus_oper    boolean;
  t_hora_ini        text;
  t_hora_fin        text;
  t_dow_operables   text;
  dia_hoy      integer;
  t_hora_backend text;

begin
  select
  into   t_hora_ini, t_hora_fin,t_dow_operables,t_hora_backend dato1, dato2,dato3,dato4
  from   tablas
  where  idtabla = 'prezzta' and idelemento = 'horario_actividad_creditos_backend';
  
  if sai_findstr(t_hora_ini,':') = 0 or sai_findstr(t_hora_fin,':') = 0 or
     split_part(t_hora_ini,':',1) > split_part(t_hora_fin,':',1)
  then
    raise notice 'MAL DEFINIDAS LAS HORAS EN LA TABLA: prezzta / horario_actividad_creditos_backend';
    return NULL;
  end if;

  d_fecha_servidor := now();
  raise notice 'Hora en el servidor:%',now();
  select
  into   d_fecha_origenes, b_estatus_oper date(fechatrabajo), estatus
  from   origenes
  limit  1;


  if t_dow_operables is not null and trim(t_dow_operables) != ''
  then
    if (sai_findstr(t_dow_operables,'|') = 0 and es_numero(t_dow_operables)) or
       sai_findstr(t_dow_operables,'|') > 0
    then


      dia_hoy = (SELECT EXTRACT(DOW FROM fechatrabajo) FROM origenes WHERE matriz = 0);      
      IF((SELECT POSITION(dia_hoy::TEXT IN t_dow_operables)) > 0) THEN ---IF 1
            IF(dia_hoy = 5) THEN ---IF 2
                IF(((SELECT EXTRACT(HOUR FROM CURRENT_TIMESTAMP))::text||':'||(SELECT EXTRACT(MINUTE FROM CURRENT_TIMESTAMP))::TEXT)::TIME >= t_hora_ini::TIME AND
                   ((SELECT EXTRACT(HOUR FROM CURRENT_TIMESTAMP))::text||':'||(SELECT EXTRACT(MINUTE FROM CURRENT_TIMESTAMP))::TEXT)::TIME <= t_hora_backend::TIME)THEN 
                   b_estatus_oper := TRUE;
                ELSE --ELSE IF 3
                   b_estatus_oper := FALSE;
                END IF;--END IF 3
            ELSE --ELSE IF 2   
               b_estatus_oper := TRUE; 
            END IF; --END IF 2
      ELSE  ---ELSE IF 1 
         b_estatus_oper := FALSE;
      END IF;--END IF 1
     
    end if;
  end if;
  return not (date(d_fecha_servidor) != d_fecha_origenes or
              current_time::time not between t_hora_ini::time and t_hora_fin::time or
              not b_estatus_oper);
end;
$$ language 'plpgsql';





create or replace function
sai_prezzta_get_tabla_interes_valida_formula (text) returns boolean as $$
declare
  p_formula   alias for $1;
  v_chr       varchar;
  b_ok        boolean;
  x           integer;
begin

  b_ok := TRUE;
  x := 0;
  loop
    x := x + 1;
    v_chr := substr(p_formula,x,1);
    if v_chr != '' then
      b_ok  := v_chr = '|' or v_chr = '0' or v_chr = '1' or v_chr = '2' or v_chr = '3' or v_chr = '4' or v_chr = '5' or v_chr = '6' or v_chr = '7' or
               v_chr = '8' or v_chr = '9' or v_chr = '*' or v_chr = '+' or v_chr = '.';
    end if;
    exit when not b_ok or v_chr = '';
  end loop;

  return b_ok;
end;
$$ language 'plpgsql';


create or replace function
sai_prezzta_get_tabla_interes_evalua_formula (integer,text,integer,integer,integer) returns text as $$
declare
  p_idproducto      alias for $1;
  p_formula         alias for $2;
  p_idorigen        alias for $3;
  p_idgrupo         alias for $4;
  p_idsocio         alias for $5;
  r_paso            record;
  b_usa_disp        boolean;
  v_delim           varchar;
  n_mult            numeric;
  i_idprod          integer;
  n_suma_reciproc   numeric;
  n_saldo_sum       numeric;
  i_x               integer;
  v_oper            text;
  t_formula         text;
begin

  if t_formula is NULL or trim(t_formula) = '' then
    return '0.00|';
  end if;

  if not sai_prezzta_get_tabla_interes_valida_formula (t_formula) then
    return '0.00|EXISTEN CARACTERES NO VALIDOS EN LA FORMULA: '||t_formula;
  end if;

  select
  into   r_paso *
  from   tablas
  where  idtabla = 'param' and idelemento = 'usar_disponible_para_reciprocidades' and
         sai_texto1_like_texto2(dato2,'|',p_idproducto::text,NULL) > 0;
  b_usa_disp := found;

  t_formula := replace(t_formula,'+','|');

  v_delim := '|';

  n_suma_reciproc := 0;
  for i_x in 1..sai_findstr(t_formula,v_delim)+1
  loop
    v_oper := split_part(t_formula,v_delim,i_x);
    if sai_findstr(v_oper,'*') > 0 then

      n_mult   := split_part(v_oper,'*',1)::numeric;
      i_idprod := split_part(v_oper,'*',2)::integer;

      select
      into   n_saldo_sum coalesce(sum(saldo - case when b_usa_disp then garantia else 0.00 end ) * n_mult,0)
      from   auxiliares
      where  idproducto = i_idprod and (idorigen,idgrupo,idsocio) = (p_idorigen,p_idgrupo,p_idsocio) and estatus = 2;
    else
      n_saldo_sum := v_oper::numeric;
    end if;
    n_suma_reciproc := n_suma_reciproc + n_saldo_sum;
  end loop;

  return n_suma_reciproc::text||'|';
end;
$$ language 'plpgsql';

create or replace function
sai_prezzta_get_tabla_intereses (integer,numeric,integer,integer,integer,integer) returns text as $$
declare
  p_idproducto  alias for $1;
  p_monto_sol   alias for $2;
  p_dias        alias for $3;
  p_idorigen    alias for $4;
  p_idgrupo     alias for $5;
  p_idsocio     alias for $6;
  r_prod        record;
  r_tab         record;
  b_montos_ok   boolean;
  b_dias_ok     boolean;
  b_recip_ok    boolean;
  b_found_tabla boolean;
  n_monto       numeric;
  t_tasas       text;
  n_val_1       numeric;
  n_val_2       numeric;
  n_val_5       numeric;
  i_val_3       integer;
  i_val_4       integer;
  t_paso        text;
begin

raise notice 'p_idproducto: %, p_monto_sol: %, p_dias: %, p_idorigen: %, p_idgrupo: %, p_idsocio: %',
             p_idproducto,p_monto_sol,p_dias,p_idorigen,p_idgrupo,p_idsocio;

  for r_tab
  in  select   *
      from     tablas
      where    idtabla = 'tasas' and idelemento like 'tasa%'||p_idproducto::text||'r%'
      order by idelemento,dato1 asc
  loop
    b_found_tabla := TRUE;

    n_val_1 := r_tab.dato1::numeric;
    n_val_2 := case when r_tab.dato2 is NULL or trim(r_tab.dato2) = '' or r_tab.dato2::numeric = 0
                  then 99999999.99
                  else r_tab.dato2::numeric
             end;

    if sai_findstr(r_tab.dato3,'|') = 1 then
      i_val_3 := split_part(r_tab.dato3,'|',1)::integer;
      i_val_4 := split_part(r_tab.dato3,'|',2)::integer;
    else
      i_val_3 := r_tab.dato3::integer;
      i_val_4 := 99999;
    end if;

    if r_tab.dato4 is null or r_tab.dato4 = '' or sai_findstr(r_tab.dato4,'|') != 2 then
      return '0.0|0.0|0.0|ERROR, NO ESTAN DEFINIDAS CORRECTAMENTE LAS TASAS EN EL PARAMETRO 4';
    end if;

    if r_tab.dato5 is not null and r_tab.dato5 != '' then
      t_paso = sai_prezzta_get_tabla_interes_evalua_formula (p_idproducto,r_tab.dato5,p_idorigen,p_idgrupo,p_idsocio);
      if split_part(t_paso,'|',2) != '' then
        return '0.0|0.0|0.0|'||'ERROR EN LA FORMULA DE RECIPROCIDAD (DATO5): '||split_part(t_paso,'|',2);
      end if;
      n_val_5 := split_part(t_paso,'|',1)::numeric;
    end if;

    b_montos_ok := p_monto_sol between n_val_1 and n_val_2;
    b_dias_ok   := p_dias  between i_val_3 and i_val_4;
    b_recip_ok  := case when n_val_5 = 0
                        then TRUE
                        when p_monto_sol <= n_val_5
                        then TRUE
                        else FALSE
                   end;

    continue when (b_montos_ok and b_dias_ok and b_recip_ok) is FALSE;

    t_tasas := case when split_part(r_tab.dato4,'|',1) = ''
                    then '0.00'
                    else split_part(r_tab.dato4,'|',1)
               end||'|'||
               case when split_part(r_tab.dato4,'|',2) = ''
                    then '0.00'
                    else split_part(r_tab.dato4,'|',2)
               end||'|'||
               case when split_part(r_tab.dato4,'|',3) = ''
                    then '0.00'
                    else split_part(r_tab.dato4,'|',3)
               end||'|';
    exit;

  end loop;
  
  if b_found_tabla then
    if b_montos_ok and b_dias_ok and b_recip_ok then
      return t_tasas;
    else
      if not b_montos_ok then
        return '0.0|0.0|0.0|EL MONTO ESTA FUERA DE RANGO SEGUN LA TABLA TASAS';
      end if;
      if not b_dias_ok then
        return '0.0|0.0|0.0|EL PLAZO EN DIAS ESTA FUERA DE RANGO SEGUN LA TABLA TASAS';
      end if;
      if not b_recip_ok then
        return '0.0|0.0|0.0|EL MONTO EXCEDE LA RECIPROCIDAD SEGUN LA TABLA TASAS';
      end if;
    end if;
  end if;

  select
  into   r_prod tasaio,tasaiod,tasaim
  from   productos
  where  idproducto = p_idproducto;
  if found then
    return r_prod.tasaio::text||'|'||r_prod.tasaiod::text||'|'||r_prod.tasaim::text||'|';
  end if;
  
  return '0.0|0.0|0.0|NO HAY TASAS EN TABLA: tasas. NI EN PRODUCTOS';
end;
$$ language 'plpgsql';


/*

 Tablas de configuracion. Datos fijos constantes:

perfil_1:

idtabla:    prezzta
idelemento  apertura_perfil_1_??  : idproducto
dato1:      (idusuario)
dato2:      (vacio)
dato3:      (tipo de amortizacion)
dato4:      (periodo de abonos)
dato5:      (idfinalidad <del catalogo de finalidades>)

perfil_2:

idtabla:    prezzta
idelemento  apertura_perfil_2_??  : idproducto
dato1:      (estatus a aperturar: 0 o 1)
dato2:      (idproducto de garantia)
dato3:      (vacio)
dato4:      (1 o 0: renovacion)
             Nota: el OPA Ref. del prestamo a renovar va en el parametro de la funcion (ver sintaxis...)


Ejemplos (Segun Caja Mitras):

insert
into   tablas
       (idtabla,idelemento,nombre,dato1,dato3,dato4,dato5)
values ('prezzta','apertura_perfil_1_32644','datos para aperturar el prestamo','1300','2','31','10');

insert
into   tablas
       (idtabla,idelemento,nombre,dato1,dato2,dato4)
values ('prezzta','apertura_perfil_2_32644','datos para aperturar el prestamo','1','110','1');


-- Sintaxis de ejecucion de la funcion:
 select sai_prezzta_crea_apertura (origen, grupo, socio, monto_solictado, plazo, idproducto_apertura, opa_ref (o-p-a),
                                  idorigenp_apertura);

-- Ejemplo de Apertura inicial (Segun Caja Mitras):
  select sai_prezzta_crea_apertura (array[text(30303), text(10), text(614), text(500.00), text(24), text(30302),
                                    '0-0-0', text(30306)]);

-- Ejemplo de Apertura inicial (Segun CSN):
  select sai_prezzta_crea_apertura (array[text(30207), text(10), text(217963), text(1000.00), text(12), text(32644),
                                    '0-0-0', text(30214)]);
*/

create or replace function
sai_prezzta_crea_apertura (_text) returns text as $$
declare
  p_mat             alias for $1;
  p_idorigen        integer;
  p_idgrupo         integer;
  p_idsocio         integer;
  p_idorigenp       integer;
  p_idproducto      integer;
  p_fecha           date;
  p_idfinalidad     integer;
  p_monto_sol       numeric;
  p_plazo           integer;
  p_idusuario       integer;
  p_con_tasa        boolean;
  p_periodo         integer;
  p_estatus         integer;
  p_idprod_gar      integer;
  p_porcen_gar      numeric;
  p_idprod_ren      integer;
  p_es_renov        varchar;
  p_opa_ref         text;
  p_idorigenp_ref   integer;
  p_idproducto_ref  integer;
  p_idauxiliar_ref  integer;

  llave_folio       text;
  x_folio           integer;
  x_tasaio          numeric;
  x_tasaiod         numeric;
  x_tasaim          numeric;
  x_montoaut        numeric;
  x_idusraut        integer;
  x_periodo         integer;
  x_pdiafijo        integer;
  x_resint          integer;
  paso              text;
  x_errtasas        text;
  i_tot_dias        integer;
  p_tp_amort        integer;
  r_paso            record;
  b_usa_disp        boolean;
  n_monto_gar       numeric;
  r_usr             record;
  r_org             record;
  r_per             record;
  r_fin             record;
  r_prod_gar        record;
  r_prod            record;
  r_ref             record;
  x_tiporeferencia  integer;
  x_tipoprestamo    integer;
  x_fecha_aut       date;
  x_fecha_sol       date;
  x_fechaact        date;

  montox numeric;
begin
  p_idorigen        := p_mat[1];
  p_idgrupo         := p_mat[2];
  p_idsocio         := p_mat[3];
  p_monto_sol       := p_mat[4];
  p_plazo           := p_mat[5];
  p_idproducto      := p_mat[6];
  p_opa_ref         := p_mat[7];
  p_idorigenp       := p_mat[8];
  

  p_idorigenp_ref   := split_part(p_opa_ref,'-',1)::integer;
  p_idproducto_ref  := split_part(p_opa_ref,'-',2)::integer;
  p_idauxiliar_ref  := split_part(p_opa_ref,'-',3)::integer;

  select
  into   r_paso *
  from   tablas
  where  idtabla = 'prezzta' and idelemento = 'apertura_perfil_1_'||p_idproducto::text;
  if not found then
    return '0|NO ESTA DEFINIDA LA TABLA: prezzta / apertura_perfil_1_'||p_idproducto::text;
  end if;

  p_idusuario   := r_paso.dato1;
--  p_idorigenp   := r_paso.dato2;  Ya no, ahora sera por parametro en la funcion
  p_tp_amort    := r_paso.dato3;
  p_periodo     := r_paso.dato4;
  p_idfinalidad := r_paso.dato5;

  select
  into   r_paso *
  from   tablas
  where  idtabla = 'prezzta' and idelemento = 'apertura_perfil_2_'||p_idproducto::text;
  if not found then
    return '0|NO ESTA DEFINIDA LA TABLA: prezzta / apertura_perfil_2_'||p_idproducto::text;
  end if;

  p_estatus     := r_paso.dato1;
  p_idprod_gar  := r_paso.dato2;
  p_idprod_ren  := r_paso.dato3;
  p_es_renov    := r_paso.dato4;
--  p_porcen_gar  := (r_paso.dato3::numeric / 100.00)::numeric(6,4);

  -- Validaciones ------------
  select
  into   r_usr *
  from   usuarios
  where  idusuario = p_idusuario;
  if not found then
    raise exception 'EL USUARIO NO EXISTE';
  end if;
  if not r_usr.activo then
    raise exception 'EL USUARIO DE APERTURA ESTA INACTIVO';
  end if;

  select
  into   r_org *
  from   origenes
  where  idorigen = p_idorigen;
  if not found then
    raise exception 'EL ORIGEN DE APERTURA NO EXISTE';
  end if;
  p_fecha := date(r_org.fechatrabajo);

  select *
  into   r_per
  from   personas
  where  idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio;
  if not found then
    raise exception 'EL SOCIO NO EXISTE';
  end if;
  if not r_per.estatus then
    raise exception 'EL SOCIO ESTA INACTIVO';
  end if;

  select *
  into   r_fin
  from   finalidades
  where  idfinalidad = p_idfinalidad;
  if not found then
    raise exception 'LA FINALIDAD DEL CREDITO NO EXISTE';
  end if;

  select
  into   r_prod_gar *
  from   productos
  where  idproducto = p_idprod_gar;
  if not found then
    raise exception 'EL PRODUCTO DE GARANTIA NO EXISTE';
  end if;

  select
  into   r_prod *
  from   productos
  where  idproducto = p_idproducto;
  if not found then
    raise exception 'EL PRODUCTO DE APERTURA NO EXISTE';
  end if;

  if p_estatus not in (0,1) then
    return '0|EL ESTATUS ESTA FUERA DE RANGO (0 y 1)...';
  end if;

  if p_plazo <= 0 then
    return '0|EL PLAZO DEBE SER MAYOR IGUAL A 1...';
  end if;

  if p_tp_amort not in (0,2,5) then
    return '0|TIPO AMORTIZACION INCORRECTO';
  end if;

  if p_tp_amort = 0 and p_periodo = 0 then
    return '0|EL TIPO DE AMORTIZACION DEL PRODUCTO: % ES "PAGO PERIODICOS", NO TIENE DEFINIDO EL PERIODO DE ABONOS '
           'EN LA TABLA: prezzta / apertura_perfil_1_%',p_idproducto,p_idproducto;
  end if;

  x_fechaact  := case when r_prod.pagodiafijo = 1
                      then date(p_fecha + p_periodo + '1 month'::interval)
                      when r_prod.pagodiafijo = 2
                      then p_fecha + 15
                      else p_fecha + p_periodo
                end;

  if p_estatus = 0 then
    x_montoaut  := 0;
    x_fecha_sol := p_fecha;
    x_fecha_aut := NULL;
  else
    x_montoaut  := p_monto_sol;
    x_idusraut  := p_idusuario;
    x_fecha_aut := p_fecha;
    x_fecha_sol := p_fecha;
  end if;

  i_tot_dias := case when r_prod.pagodiafijo = 1
                     then date(p_fecha + p_periodo + (p_plazo::text||' month')::interval) - p_fecha
                     when r_prod.pagodiafijo = 2
                     then date(p_fecha + ((p_plazo/2)::text||' month')::interval +
                          ((case when p_plazo%2 > 0 then 15 else 0 end)::text||' days')::interval) - p_fecha
                     else p_plazo * p_periodo
                end;

  


  -- Trae las tasas de producto si p_tasa es verdadero
  -- return 'tot diasssssssssssssssss:'||i_tot_dias;
  paso := sai_prezzta_get_tabla_intereses (p_idproducto,p_monto_sol,i_tot_dias,p_idorigen,p_idgrupo,p_idsocio);

  x_tasaio   := split_part(paso,'|',1)::numeric;
  x_tasaiod  := split_part(paso,'|',2)::numeric;
  x_tasaim   := split_part(paso,'|',3)::numeric;
  x_errtasas := split_part(paso,'|',4);

  if x_errtasas != '' or (x_tasaio + x_tasaiod + x_tasaim) = 0 then
    if x_errtasas = '' then
      return '0|ESTA CONFIGURADO EN CEROS, PORQUE? NO LO SE!!, NO DEBE.';
    end if;
    return '0|'||x_errtasas;
  end if;

  -- Valida el periodoabono
  if p_periodo > 0 then
    x_periodo := p_periodo;
  else
    select into x_periodo int4(dato1)
           from tablas
          where idtabla = 'param' and
                idelemento = ('periodoabono'||text(p_idproducto));
    if not found then
      select into x_periodo int4(dato1)
             from tablas
            where idtabla = 'param' and idelemento = 'periodoabono';
      if not found then
        x_periodo := 30;
        return '0|EL PERIODO DE ABONO FUE ASIGNADO POR DEFAULT A 30';
      end if;
    end if;
  end if;

  x_pdiafijo := r_prod.pagodiafijo;

  x_tiporeferencia := 0;
  x_tipoprestamo   := 0;

  if p_es_renov = '1' and p_idorigenp_ref != 0 and p_idproducto_ref != 0 and p_idauxiliar_ref != 0 then
    paso := sai_prezzta_devuelve_condiciones_apertura(p_idorigen,p_idgrupo,p_idsocio);

    if paso is null or paso = '' then
      raise notice 'ERROR EN LA FUNCION: sai_prezzta_devuelve_condiciones_apertura RETORNA NULL';
      return '0|ERROR EN LA FUNCION: sai_prezzta_devuelve_condiciones_apertura RETORNA NULL';
    end if;

    if split_part(paso,'|',5) != 'R' then
      raise notice 'LA RENOVACION NO ESTA APROVADA';
      return '0|LA RENOVACION NO ESTA APROVADA';
    end if;

    if split_part(paso,'|',10) != p_opa_ref then
      raise notice 'EL FOLIO ORIGINAL NO COINCIDE CON LA REFERENCIA DE LA RENOVACION APROVADA';
      return '0|EL FOLIO ORIGINAL NO COINCIDE CON LA REFERENCIA DE LA RENOVACION APROVADA';
    end if;

    x_tiporeferencia := 2;
    x_tipoprestamo   := 3;

  end if;


  -------------------------------------------
  -- VALIDACION DEL MONTO SOLO PARA MITRAS --
  -------------------------------------------
  if (p_idorigen between 30300 and 30399) or (p_idorigenp between 30300 and 30399) then
    montox := 0.0;
    montox := sai_prezzta_precedencia_garantias(p_idorigen, p_idgrupo, p_idsocio, p_idproducto, p_monto_sol);
    if montox < 0.0 then
      raise notice 'HUBO UN ERROR AL PROCESAR EL PRESTAMO POR LA PRECEDENCIA DE GARANTIAS';
      return '0|HUBO UN ERROR AL PROCESAR EL PRESTAMO POR LA PRECEDENCIA DE GARANTIAS';
    end if;
  end if;


  if x_pdiafijo = 1 then
    x_periodo := 0;
  end if;

  llave_folio := 'APE'||trim(to_char(p_idorigenp,'099999'))||
                 trim(to_char(p_idproducto,'09999'));
  x_folio := sai_folio(TRUE,llave_folio);

  -- Liberacion de la garantia del prestamo que sera renovado (osea el viejo) -----
  if p_es_renov = '1' and p_idorigenp_ref != 0 and p_idproducto_ref != 0 and p_idauxiliar_ref != 0 then
    -- Busca el folio auxiliar del prestamo que sera renovado (osea el viejo)

    select
    into   r_paso *
    from   auxiliares
    where  idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and
           idorigenp = p_idorigenp_ref and idproducto = p_idproducto_ref and idauxiliar = p_idauxiliar_ref and estatus = 2 and garantia > 0;
    if found then
      select         -- Busca la liga de la garantia (prestamo <-> ahorro)
      into   r_ref *
      from   referenciasp rp4
      where  tiporeferencia = 4 and (idorigenp,idproducto,idauxiliar) = (r_paso.idorigenp,r_paso.idproducto,r_paso.idauxiliar);

      -- Quitar garantia al Prestamo ----
      update auxiliares
      set    garantia = garantia - r_paso.garantia
      where  (idorigenp,idproducto,idauxiliar) = (r_ref.idorigenp,r_ref.idproducto,r_ref.idauxiliar);
      -- Quitar garantia al Ahorro ----
      update auxiliares
      set    garantia = garantia - r_paso.garantia
      where  (idorigenp,idproducto,idauxiliar) = (r_ref.idorigenpr,r_ref.idproductor,r_ref.idauxiliarr);
    end if;
  end if;

  select
  into   r_paso *,(saldo - garantia) as disponible
  from   auxiliares
  where  idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and
         idproducto = p_idprod_gar and estatus = 2;

  n_monto_gar := numeric_smaller(p_monto_sol, r_paso.disponible);

  update auxiliares
  set    garantia = garantia + n_monto_gar
  where  (idorigenp,idproducto,idauxiliar) = (r_paso.idorigenp,r_paso.idproducto,r_paso.idauxiliar);

  insert
  into   auxiliares
         (idorigen,idgrupo,idsocio,idorigenp,idproducto,idauxiliar,fechaape,idfinalidad,estatus,tipoamortizacion,garantia,
          plazo,periodoabonos,pagodiafijo,fecha_solicitud,montosolicitado,fecha_autorizacion,montoautorizado,
          tasaio,tasaiod,tasaim,idnotas,elaboro,autorizo,tiporeferencia,tipoprestamo, fechaactivacion)
  values (p_idorigen,p_idgrupo,p_idsocio,p_idorigenp,p_idproducto,x_folio,p_fecha,p_idfinalidad,p_estatus,p_tp_amort,n_monto_gar,
          p_plazo,x_periodo,x_pdiafijo,x_fecha_sol,p_monto_sol,x_fecha_aut,x_montoaut,
          x_tasaio,x_tasaiod,x_tasaim,NULL,p_idusuario,x_idusraut, x_tiporeferencia,x_tipoprestamo,x_fechaact);

  if p_tp_amort = 0 then
    x_resint := sai_amortizaciones_crea_tabla (p_idorigenp,p_idproducto,x_folio,date(p_fecha),'genera_todo');
  else
    x_resint := sai_amortizaciones_crea_tabla_pf (p_idorigenp,p_idproducto,x_folio,date(p_fecha),1);
  end if;

  insert
  into   referenciasp
  values (p_idorigenp,p_idproducto,x_folio,4,n_monto_gar::text||'|0|',r_paso.idorigenp,r_paso.idproducto,r_paso.idauxiliar);
  
  if p_es_renov = '1' and p_idorigenp_ref != 0 and p_idproducto_ref != 0 and p_idauxiliar_ref != 0 then
    insert
    into   referenciasp
           (idorigenp,idproducto,idauxiliar,tiporeferencia,referencia,idorigenpr,idproductor,idauxiliarr)
    values (p_idorigenp,p_idproducto,x_folio,2,NULL,p_idorigenp_ref,p_idproducto_ref,p_idauxiliar_ref);
  end if;

  return x_folio::text||'|';
end;
$$ language 'plpgsql';

/*------------------------------------------------------------------------------
--------------------------------------------------------------------------------

ESTAS FUNCIONES SE HICIERON PARA LA APERTURA DE PRESTAMOS DESDE LA BANCA MOVIL,
DEBE REGRESAR LAS CONDICIONES DE PLAZO Y MONTO QUE SE LE PUEDE PRESTAR AL SOCIO,
Y ADEMAS INDICAR SI ES APERTURA O RENOVACION. EN EL CASO DE RENOVACION, SE DEBE
TAMBIEN INDICAR EL MONTO A CUBRIR DEL PRESTAMO ORIGINAL, LO DISPONIBLE Y SI
TIENE ALGO PENDIENTE POR PAGAR, TAMBIEN SE REPORTA ESA CANTIDAD, DE LA SIGUIENTE
MANERA :

APERTURA NORMAL
plazo minimo|plazo maximo|monto minimo|monto maximo|A|||idproducto

RENOVACION
plazo minimo|plazo maximo|monto minimo|monto maximo|R|
saldo pendiente a cubrir del pestamo original|disponible a entregar|
interes y capital pendiente por cubrir|idproducto|opa a renovar|origen opa

EL PLAZO ES EN MESES

SI SE ENCUENTRA ALGUN DETALLE O FALLA, DEVUELVE LO SIGUIENTE :
ERROR|descripcion del error|0|0|ERROR|||

PARA SABER QUE PRODUCTOS DE APERTURA Y RENOVACION SE VAN A USAR:
idtabla    = prezzta
idelemento = apertura_prestamo_1
parametro1 = idproducto de apertura

idtabla    = prezzta
idelemento = apertura_prestamo_2
parametro1 = idproducto de renovacion

idorigen = 30201 and idgrupo = 10 and idsocio = 1502
insert into tablas values ('prezzta', 'apertura_prestamo_1', NULL, '32644', NULL, NULL, NULL, NULL, 0);
insert into tablas values ('prezzta', 'apertura_prestamo_2', NULL, '32664', NULL, NULL, NULL, NULL, 0);
------------------------------------------------------------------------------*/

create or replace function
sai_prezzta_devuelve_condiciones_apertura(integer, integer , integer)
returns text as $$
declare
  p_idorigen alias for $1;
  p_idgrupo  alias for $2;
  p_idsocio  alias for $3;

  condiciones text;

  x integer;
  y integer;
  a integer;
  b integer;

  origen_p integer;

  r_aux record;

  sai_aux text;

  prod1 integer;
  prod2 integer;

  saldo_ahorro    numeric;
  monto_garantia  numeric;
  otras_garantias numeric;
  monto_maximo    numeric;
  monto_renovar   numeric;

  es_renovacion   boolean;
  otros_prestamos boolean;

  fecha_hoy date;

  montox       numeric;
  acapital     numeric;
  monto_io     numeric;
  monto_iva_io numeric;
  monto_im     numeric;
  monto_iva_im numeric;
  deuda_total  numeric;

  idorigenpx  integer;
  idproductox integer;
  idauxiliarx integer;

  folio_ant varchar;

  mensaje text;

  dias_como_socio integer;

  idorigenx integer;
begin

  select into fecha_hoy date(fechatrabajo) from origenes limit 1;
  if not found or fecha_hoy is NULL then fecha_hoy := date(now()); end if;

  idorigenx := 0;
  select into idorigenx idorigen from origenes where matriz = 0 limit 1;
  if not found or idorigenx is NULL then idorigenx := 0; end if;

  ------------------------
  -- Existe el socio ?? --
  ------------------------
  x := 0;
  select into x count(*)
  from personas
  where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio;
  if not found or x is NULL then x := 0; end if;

  if x <= 0 then
    return 'ERROR|EL SOCIO NO EXISTE !!!|0|0|ERROR|||||';
  end if;


  ------------------------------------------------------------------
  -- El socio tiene atraso en alguno de sus prestamos ?? (MITRAS) --
  ------------------------------------------------------------------
  if idorigenx = 30300 then
    x := 0;
    for r_aux in
      select idorigenp,idproducto,idauxiliar
      from auxiliares
      where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and idorigenp > 0 and
            idproducto in (select idproducto from productos where tipoproducto = 2) and idauxiliar > 0 and
            estatus = 2
    loop
      sai_aux := sai_auxiliar(r_aux.idorigenp, r_aux.idproducto, r_aux.idauxiliar, fecha_hoy);
      if sai_aux is not NULL and length(sai_aux) > 0 then
        x := x + sai_token(4, sai_aux, '|')::integer;
      end if;
    end loop;

    if x > 0 then
      return 'ERROR|LOS PRESTAMOS DEL SOCIO TIENEN DIAS VENCIDOS !!!|0|0|ERROR|||||';
    end if;
  end if;


  dias_como_socio := 0; x := 0;
  select into x fecha_hoy - fechaingreso
  from personas
  where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio;
  if not found or x is NULL then x := 0; end if;
  dias_como_socio := x;


  prod1 := 0; prod2 := 0;
  saldo_ahorro := 0.0; monto_garantia := 0.0; otras_garantias := 0.0; monto_maximo := 0.0; monto_renovar := 0.0;
  acapital := 0.0; monto_io := 0.0; monto_iva_io := 0.0; monto_im := 0.0; monto_iva_im := 0.0; deuda_total := 0.0;
  origen_p := 0;

  ---------------------------------
  -- PRODUCTO DE APERTURA NORMAL --
  ---------------------------------

  select into prod1 dato1::integer
  from tablas
  where lower(idtabla) = 'prezzta' and lower(idelemento) = 'apertura_prestamo_1' and dato1 is not NULL and
        length(dato1) > 0;
  if not found then
    return 'ERROR|FALTA DEFINIR EL PRODUCTO PARA PRESTAMO !!!|0|0|ERROR|||||';
  else
    if prod1 is NULL then prod1 := 0; end if;
  end if;

  select into x count(*)
  from productos where idproducto = prod1 and tipoproducto = 2;
  if not found or x is NULL then x := 0; end if;

  if x <= 0 then
    return 'ERROR|NO EXISTE EL PRODUCTO '||trim(to_char(prod1))||' !!!|0|0|ERROR|||||';
  end if;


  ----------------------------
  -- PRODUCTO DE RENOVACION --
  ----------------------------

  select into prod2 dato1::integer
  from tablas
  where lower(idtabla) = 'prezzta' and lower(idelemento) = 'apertura_prestamo_2' and dato1 is not NULL and
        length(dato1) > 0;
  if not found then
    return 'ERROR|FALTA DEFINIR EL PRODUCTO PARA RENOVACION !!!|0|0|ERROR|||||';
  else
    if prod2 is NULL then prod2 := 0; end if;
  end if;

  select into x count(*)
  from productos where idproducto = prod2 and tipoproducto = 2;
  if not found or x is NULL then x := 0; end if;

  if x <= 0 then
    return 'ERROR|NO EXISTE EL PRODUCTO '||trim(to_char(prod2))||' !!!|0|0|ERROR|||||';
  end if;


  --------------------------------------------
  -- Sera un prestamo nuevo o renovacion ?? --
  --------------------------------------------
  x := 0;
  select into x count(*)
  from auxiliares
  where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and idproducto = prod1 and estatus = 2;
  if not found or x is NULL then x := 0; end if;

  y := 0;
  select into y count(*)
  from auxiliares
  where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and idproducto = prod2 and estatus = 2;
  if not found or y is NULL then y := 0; end if;

  if prod1 != prod2 then
    if x > 0 and y > 0 then
      return 'ERROR|EL SOCIO CUENTA CON FOLIOS ACTIVOS DE '||text(prod1)||' Y '||text(prod2)||' !!!|0|0|ERROR|||||';
    else
      if x > 1 then
        return 'ERROR|EL SOCIO CUENTA CON MAS DE UN FOLIO ACTIVO DEL '||text(prod1)||' !!!|0|0|ERROR|||||';
      end if;

      if y > 1 then
        return 'ERROR|EL SOCIO CUENTA CON MAS DE UN FOLIO ACTIVO DEL '||text(prod2)||' !!!|0|0|ERROR|||||';
      end if;
    end if;
  end if;

  es_renovacion := FALSE; otros_prestamos := FALSE;
  if x > 0 or y > 0 then es_renovacion := TRUE; end if;

  a := 0;
  select into a count(*)
  from auxiliares
  where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and
        idproducto in (select idproducto from productos where tipoproducto = 2) and
        idproducto != prod1 and idproducto != prod2 and estatus in (2,3);
  if not found or a is NULL then a := 0; end if;

  b := 0;
  select into b count(*)
  from auxiliares_h
  where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and
        idproducto in (select idproducto from productos where tipoproducto = 2) and
        idproducto != prod1 and idproducto != prod2 and estatus = 3;
  if not found or b is NULL then b := 0; end if;

  if (a + b) > 0 then otros_prestamos := TRUE; end if;

  -- PARA SELECCIONAR EL ORIGEN DE APERTURA :
  -- Si es el primer prestamo: origen del socio.  
  -- Si es renovado: origen de apertura del prestamo anterior
  -- Si el socio obtuvo algun prestamo previo: origen del ultimo prestamo

  if not es_renovacion and not otros_prestamos then
    origen_p := p_idorigen;
raise notice '----------------------------------------------->> origen del socio';
  else

    if es_renovacion then

      idorigenpx := 0; idproductox := 0; idauxiliarx := 0;

      select into monto_renovar, monto_garantia sum(saldo), sum(garantia)
      from auxiliares
      where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and
            idproducto = (case when y > 0 then prod2 else prod1 end) and estatus = 2;

      if not found then
        monto_renovar := 0.0;
        monto_garantia := 0.0;
      else
        if monto_renovar is NULL then monto_renovar := 0.0; end if;
        if monto_garantia is NULL then monto_garantia := 0.0; end if;
      end if;

      if monto_renovar <= 0.0 then
        return 'ERROR|DEBE SER RENOVACION PERO EL SALDO ES 0.0!!!|0|0|ERROR|||||';
      end if;

      -- En el caso de una renovacion, hay que ver cuanto debe de los prestamos
      -- originales
      for r_aux in
        select idorigenp,idproducto,idauxiliar
        from auxiliares
        where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and
              idproducto = (case when y > 0 then prod2 else prod1 end) and estatus = 2
      loop

        sai_aux := sai_auxiliar(r_aux.idorigenp, r_aux.idproducto, r_aux.idauxiliar, fecha_hoy);
        if sai_aux is not NULL and length(sai_aux) > 0 then

          montox := 0.0;
          montox := sai_token(5, sai_aux, '|')::numeric;
          acapital := acapital + coalesce(montox, 0.0);

          montox := 0.0;
          montox := sai_token(7, sai_aux, '|')::numeric;
          monto_io := monto_io + coalesce(montox, 0.0);

          montox := 0.0;
          montox := sai_token(18, sai_aux, '|')::numeric;
          monto_iva_io := monto_iva_io + coalesce(montox, 0.0);

          montox := 0.0;
          montox := sai_token(16, sai_aux, '|')::numeric;
          monto_im := monto_im + coalesce(montox, 0.0);

          montox := 0.0;
          montox := sai_token(19, sai_aux, '|')::numeric;
          monto_iva_im := monto_iva_im + coalesce(montox, 0.0);

        end if;

        idorigenpx  := r_aux.idorigenp;
        idproductox := r_aux.idproducto;
        idauxiliarx := r_aux.idauxiliar;
      end loop;

      -- EL PRESTAMO ORIGINAL CUMPLE CON LAS CONDICIONES DE CUBRIR UN PORCENTAJE
      -- DEL MONTO PRESTADO Y/O DEL PLAZO ???
      mensaje := sai_valida_saldo_y_plazo_renovarest(idorigenpx, idproductox, idauxiliarx, 1, 0);
      if mensaje is not NULL and length(mensaje) > 0 then
        return 'ERROR|'||replace(mensaje, E'\012', ' ')||'|0|0|ERROR|||||';
      end if;

      deuda_total := acapital + monto_io + monto_iva_io + monto_im + monto_iva_im;

      folio_ant := trim(to_char(idorigenpx,'999999'))||'-'||trim(to_char(idproductox,'99999'))||'-'||
                   trim(to_char(idauxiliarx,'99999999'));

      if folio_ant is NULL or folio_ant = '' or length(folio_ant) < 13 then
        return 'ERROR|DEBE SER RENOVACION PERO NO EXISTE EL FOLIO ORIGINAL!!!|0|0|ERROR|||||';
      end if;

      x := 0;
      select into x idorigenp
      from auxiliares
      where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and
            (idproducto = prod1 or idproducto = prod2) and estatus = 2
      order by fechaactivacion desc limit 1;
      if not found or x is NULL then x := 0; end if;
      origen_p := (case when x = 0 then p_idorigen else x end);
raise notice '----------------------------------------------->> origen del prestamo renovado';
    else
      x := 0;
      select into x t.idorigenp
      from (select idorigenp,idproducto,idauxiliar,fechaactivacion from auxiliares
            where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and idorigenp > 0 and
                  idproducto in (select idproducto from productos where tipoproducto = 2) and
                  idproducto != prod1 and idproducto != prod2 and idauxiliar > 0 and estatus in (2,3)
            union
            select idorigenp,idproducto,idauxiliar,fechaactivacion from auxiliares_h
            where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and idorigenp > 0 and
                  idproducto in (select idproducto from productos where tipoproducto = 2) and
                  idproducto != prod1 and idproducto != prod2 and idauxiliar > 0 and estatus in (2,3)
            order by fechaactivacion desc limit 1) t;
      if not found or x is NULL then x := 0; end if;
      origen_p := (case when x = 0 then p_idorigen else x end);
raise notice '----------------------------------------------->> origen del ultimo prestamo';
    end if;

  end if;


  if origen_p = 0 then
    origen_p := p_idorigen;
raise notice '----------------------------------------------->> origen por default';
  end if;


  -----------------------------------------
  -- Tiene saldo disponible en el 110 ?? --
  -----------------------------------------

  select into saldo_ahorro, otras_garantias sum(saldo), sum(garantia)
  from auxiliares
  where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and idproducto = 110;
  if not found then
    saldo_ahorro := 0.0;
    otras_garantias := 0.0;
  else
    if saldo_ahorro is NULL then saldo_ahorro := 0.0; end if;
    if otras_garantias is NULL then otras_garantias := 0.0; end if;
  end if;

  if saldo_ahorro <= 0.0 then
    return 'ERROR|EL SOCIO NO TIENE SALDO EN EL AHORRO !!!|0|0|ERROR|||||';
  end if;

  if saldo_ahorro <= 500.0 then
    return 'ERROR|EL SALDO EN EL AHORRO DEBE SER MAYOR A $ 500.00 !!|0|0|ERROR|||||';
  end if;

  if saldo_prezzta_productos_incompatibles(p_idorigen, p_idgrupo, p_idsocio,
                                           (case when es_renovacion then prod2 else prod1 end),
                                           (case when es_renovacion
                                                 then (case when y > 0 then prod2 else prod1 end)
                                                 else 0
                                            end))
  then
    return 'ERROR|PRIMERO SE DEBEN LIQUIDAR OTROS PRESTAMOS NO COMPATIBLES|0|0|ERROR|||||';
  end if;


  mensaje := NULL;
  if es_renovacion then
    monto_maximo := saldo_ahorro - otras_garantias + monto_garantia;
    mensaje := 'R|'||trim(to_char(monto_renovar - acapital, '99999999.99'))||'|'||
               trim(to_char(monto_maximo - monto_renovar + acapital, '99999999.99'))||'|'||
               trim(to_char(deuda_total, '99999999.99'))||'|'||text(prod2)||'|'||folio_ant||'|'||text(origen_p);
  else
    monto_maximo := saldo_ahorro - otras_garantias;
    monto_renovar := 0.0;
    mensaje := 'A||||'||text(prod1)||'|0-0-0|'||text(origen_p);
  end if;


  -- ESTA VALIDACION DEL 90% DE LO DISPONIBLE POR EL MOMENTO SOLO APLICA PARA
  -- CSN (JFPA, 18/MAYO/2023)
  if idorigenx = 30200 then
    if dias_como_socio < 365 then monto_maximo := monto_maximo*0.9; end if;
  end if;

  -- LA REVISION DE PRECEDENCIA DE GARANTIAS SOLO FUE SOLICITADA POR MITRAS
  -- CSN (JFPA, 18/MAYO/2023)
  if idorigenx = 30300 then
    montox := 0.0;
    montox := sai_prezzta_precedencia_garantias(p_idorigen, p_idgrupo, p_idsocio,
                                                (case when es_renovacion then prod2 else prod1 end), 0.0);
    if montox > 0.0 and montox > monto_maximo then
      monto_maximo := montox;
      raise notice '----------------------------------------------->> cambio de monto maximo por precedencia de garantias';
    end if;
  end if;


  condiciones := '1|72|0|'||trim(to_char(monto_maximo, '99999999.99'))||'|'||mensaje;

  ----------------------------------------------------------------------------------------------------------------------
  -- ULTIMA MODIFICACION : 22/AGOSTO/2023 ------------------------------------------------------------------------------
  ----------------------------------------------------------------------------------------------------------------------

  return condiciones;
end;
$$ language 'plpgsql';

create or replace function saldo_prezzta_productos_incompatibles(integer, integer, integer, integer, integer)
returns boolean as $$
declare
  p_idorigen  alias for $1;
  p_idgrupo   alias for $2;
  p_idsocio   alias for $3;
  p_producto  alias for $4;
  p_producto2 alias for $5;

  px1     text;
  p_prods text;

  saldo_aux  numeric;

  consulta text;

  r record;

  reemplazo varchar;
begin

raise notice 'productossssssssssssssssssssssssssss:%',p_producto||'-'||p_producto2;

  -- El prestamo actual tiene productos incompatibles ???
  p_prods := NULL;
  select into p_prods
              (case when dato2 is not null
                    then case when length(dato2) = 0 then NULL else replace(dato2, '|', ',') end
                    else NULL
               end) from tablas
  where lower(idtabla) = 'saldo_productos_incompatibles' and idelemento::integer = p_producto;

raise notice 'prods:%',p_prods;
  if not found or p_prods = '' then p_prods := NULL; end if;

  if p_prods is NULL then return FALSE; end if;

  -- El socio tiene folios activos de esos productos incompatibles ???
/*
  consulta :=
  'select sum(saldo) as saldo '||
  'from (select sum(saldo) as saldo '||
        'from   auxiliares '||
        'where  idorigen = '||p_idorigen||' and idgrupo = '||p_idgrupo||' and '||
               'idsocio = '||p_idsocio||' and idorigenp > 0 and '||
               'idproducto in ('||p_prods||') and idauxiliar > 0 and '||
               'estatus = 2 '||
        'union '||
        'select sum(saldo) as saldo '||
        'from   auxiliares_ext '||
        'where  idorigen='||p_idorigen||' and idgrupo='||p_idgrupo||' and '||
               'idsocio='||p_idsocio||' and idorigenp > 0 and '||
               'idproducto in ('||p_prods||') and idauxiliar > 0 and '||
               'estatus = 2) as x';
*/
  consulta :=
  'select idorigenp,idproducto,idauxiliar,saldo '||
  'from   auxiliares '||
  'where  idorigen = '||p_idorigen||' and idgrupo = '||p_idgrupo||' and idsocio = '||p_idsocio||' and '||
         'idorigenp > 0 and idproducto in ('||p_prods||') and idauxiliar > 0 and estatus = 2 '||
  'union '||
  'select idorigenp,idproducto,idauxiliar,saldo '||
  'from   auxiliares_ext '||
  'where  idorigen = '||p_idorigen||' and idgrupo = '||p_idgrupo||' and idsocio = '||p_idsocio||' and '||
         'idorigenp > 0 and idproducto in ('||p_prods||') and idauxiliar > 0 and estatus = 2';

  saldo_aux := 0.0;
  for r in execute consulta
  loop
    if p_producto2 = 0 then saldo_aux := saldo_aux + r.saldo;
    else
      if r.idproducto != p_producto2 then
        saldo_aux := saldo_aux + r.saldo;
      end if;
    end if;
  end loop;
  if not found or saldo_aux is NULL then saldo_aux := 0.0; end if;

  if saldo_aux = 0 then return FALSE; end if;

  return TRUE;
end;
$$ language 'plpgsql';

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
-- ESTA FUNCION ES PARA CANCELAR UN PRESTAMO AUTORIZADO PERO QUE NO PASO LA
-- REVISION DE ALGUN AREA DE CREDITOS, ADEMAS DE QUE SE LE BORRA LA GARANTIA
-- (JFPA, 21/MARZO/2023)
--------------------------------------------------------------------------------
create or replace function
sai_prezzta_cancela_prestamo_rechazado (integer, integer, integer)
returns text as $$
declare
  p_idorigenp  alias for $1;
  p_idproducto alias for $2;
  p_idauxiliar alias for $3;

  r_aux        record;

  idorigenp_g  integer;
  idproducto_g integer;
  idauxiliar_g integer;

  idorigenp_1  integer;
  idproducto_1 integer;
  idauxiliar_1 integer;

  monto_g    numeric;
  saldo_ah   numeric;
  monto_g_ah numeric;
  saldo_p    numeric;
  monto_p    numeric;

  prod1 integer;
  prod2 integer;
  x     integer;

  px1 varchar;
begin

  ----------------------------------------
  -- PRODUCTOS DE APERTURA Y RENOVACION --
  ----------------------------------------

  prod1 := 0; prod2 := 0;

  select into prod1 dato1::integer
  from tablas
  where lower(idtabla) = 'prezzta' and lower(idelemento) = 'apertura_prestamo_1' and dato1 is not NULL and
        length(dato1) > 0;
  if not found then
    raise notice 'ERROR: FALTA DEFINIR EL PRODUCTO PARA APERTURAS !!';
    return 0;
  else
    if prod1 is NULL then prod1 := 0; end if;
  end if;

  select into prod2 dato1::integer
  from tablas
  where lower(idtabla) = 'prezzta' and lower(idelemento) = 'apertura_prestamo_2' and dato1 is not NULL and
        length(dato1) > 0;
  if not found then
    raise notice 'ERROR: FALTA DEFINIR EL PRODUCTO PARA RENOVACION !!';
    return 0;
  else
    if prod2 is NULL then prod2 := 0; end if;
  end if;

  if prod1 <= 0 or prod2 <= 0 then
    raise notice 'ERROR: NO ESTAN BIEN DEFINIDOS LOS PRODUCTOS !!';
    return 0;
  end if;

  -----------------------------------
  -- EXISTE EL FOLIO A CANCELAR ?? --
  -----------------------------------

  select into r_aux *
  from auxiliares
  where idorigenp = p_idorigenp and idproducto = p_idproducto and idauxiliar = p_idauxiliar;
  if not found then return 0;
  else
    if r_aux.estatus != 1 then
      raise notice 'ERROR: EL PRESTAMO NO ESTA AUTORIZADO, TIENE OTRO ESTATUS !!';
      return 0;
    end if;
  end if;

  --------------------------
  -- FOLIO DE LA GARANTIA --
  --------------------------

  idorigenp_g := 0; idproducto_g := 0; idauxiliar_g := 0;
  select into idorigenp_g, idproducto_g, idauxiliar_g
              idorigenpr,  idproductor,  idauxiliarr
  from referenciasp
  where idorigenp = p_idorigenp and idproducto = p_idproducto and idauxiliar = p_idauxiliar and tiporeferencia = 4;
  if not found then return 0;
  else
    if idorigenp_g is NULL then  idorigenp_g := 0; end if;
    if idproducto_g is NULL then idproducto_g := 0; end if;
    if idauxiliar_g is NULL then idauxiliar_g := 0; end if;
  end if;

  if idorigenp_g <= 0 or idproducto_g <= 0 or idauxiliar_g <= 0 then
    raise notice 'ERROR: NO EXISTE DATOS DEL FOLIO DE AHORRO EN GARANTIA !!';
    return 0;
  end if;

  monto_g := 0.0; saldo_ah := 0.0; monto_g_ah := 0.0; saldo_p := 0.0; monto_p := 0.0;

  monto_g := r_aux.garantia;

  update auxiliares
  set garantia = 0, estatus = 4
  where idorigenp = p_idorigenp and idproducto = p_idproducto and idauxiliar = p_idauxiliar;

  update auxiliares
  set garantia = garantia - monto_g
  where idorigenp = idorigenp_g and idproducto = idproducto_g and idauxiliar = idauxiliar_g;

  --------------------------------------------------------------------
  -- SI ES UNA APERTURA NUEVA, YA NO SE HACE NADA MAS EN LA FUNCION --
  --------------------------------------------------------------------
  if p_idorigenp = prod1 then return 1; end if;

  ------------------------------------------------------------------------------
  -- SI ES UNA RENOVACION, SE DEBE REPONER LA GARANTIA AL FOLIO ORIGINAL YA SEA
  -- DEL prod1 O DEL prod2
  ------------------------------------------------------------------------------

  idorigenp_1 := 0; idproducto_1 := 0; idauxiliar_1 := 0;
  select into idorigenp_1, idproducto_1, idauxiliar_1
              idorigenp,  idproducto,  idauxiliar
  from auxiliares
  where idorigenp > 0 and (idproducto = prod1 or idproducto = prod2) and idauxiliar > 0 and
        estatus = 2 and garantia <= 0 and
        (idorigen, idgrupo, idsocio) in
        (select idorigen, idgrupo, idsocio
         from auxiliares
         where idorigenp = p_idorigenp and idproducto = p_idproducto and idauxiliar = p_idauxiliar)
  order by fechaactivacion desc limit 1;

  if not found then
    raise notice 'ERROR: NO EXISTE DATOS DEL PRESTAMO ORIGINAL !!';
    return 0;
  else
    if idorigenp_1 is NULL then  idorigenp_1 := 0; end if;
    if idproducto_1 is NULL then idproducto_1 := 0; end if;
    if idauxiliar_1 is NULL then idauxiliar_1 := 0; end if;
  end if;

  if idorigenp_1 <= 0 or idproducto_1 <= 0 or idauxiliar_1 <= 0 then
    raise notice 'ERROR: NO EXISTE DATOS DEL PRESTAMO ORIGINAL !!';
    return 0;
  end if;

  ---------------------------------------------------
  --  SALDO Y MONTO PRESTADO DEL PRESTAMO ORIGINAL --
  ---------------------------------------------------
  select into saldo_p,monto_p
              saldo,montoprestado
  from auxiliares
  where idorigenp = idorigenp_1 and idproducto = idproducto_1 and idauxiliar = idauxiliar_1;
  if not found then
    raise notice 'ERROR: NO SE TIENEN DATOS DEL PRESTAMO ORIGINAL !!';
    return 0;
  else
    if saldo_p is NULL then saldo_p := 0.0; end if;
    if monto_p is NULL then monto_p := 0.0; end if;
  end if;

  if saldo_p <= 0.0 or monto_p <= 0.0 then
    raise notice 'ERROR: NO SE TIENEN DATOS DEL PRESTAMO ORIGINAL !!';
    return 0;
  end if;

  ----------------------------------
  --  SALDO Y GARANTIA DEL AHORRO --
  ----------------------------------
  select into saldo_ah,monto_g_ah
              saldo,garantia
  from auxiliares
  where idorigenp = idorigenp_g and idproducto = idproducto_g and idauxiliar = idauxiliar_g;
  if not found then
    raise notice 'ERROR: NO SE TIENEN DATOS DEL AHORRO EN GARANTIA !!';
    return 0;
  else
    if saldo_ah   is NULL then saldo_ah   := 0.0; end if;
    if monto_g_ah is NULL then monto_g_ah := 0.0; end if;
  end if;

  if saldo_ah <= 0.0 then
    raise notice 'ERROR: NO SE TIENEN DATOS DEL AHORRO EN GARANTIA !!';
    return 0;
  end if;

  -------------------------------------------------
  -- CALCULO DE LA GARANTIA QUE SE DEBE REGRESAR --
  -------------------------------------------------
  monto_g := 0.0;
  monto_g := saldo_p;

  if (monto_g + monto_g_ah) > saldo_ah then
    raise notice 'ERROR: LA GARANTIA QUE SE QUIERE USAR ES MAYOR A LA DISPONIBLE !!';
    return 0;
  end if;

  update auxiliares
  set garantia = monto_g
  where idorigenp = idorigenp_1 and idproducto = idproducto_1 and idauxiliar = idauxiliar_1;

  update auxiliares
  set garantia = garantia + monto_g
  where idorigenp = idorigenp_g and idproducto = idproducto_g and idauxiliar = idauxiliar_g;

  ------------------------------------------------------------------------------
  -- POR SI LAS DUDAS, REVISO QUE ESTE EL DATO DE LA GARANTIA EN referenciasp --
  ------------------------------------------------------------------------------
  select into x count(*)
  from referenciasp
  where idorigenp = idorigenp_1 and idproducto = idproducto_1 and idauxiliar = idauxiliar_1 and
        tiporeferencia = 4 and
        idorigenpr = idorigenp_g and idproductor = idproducto_g and idauxiliarr = idauxiliar_g;
  if not found or x is NULL then x := 0; end if;

  if x = 0 then
    px1 := trim(to_char(monto_p, '99999999.99'))||'|0|';
    insert into referenciasp
    values (idorigenp_1, idproducto_1, idauxiliar_1, 4, px1, idorigenp_g, idproducto_g, idauxiliar_g);
  end if;

  return 1;
end;
$$ language 'plpgsql';

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- ESTAS FUNCIONES SON PARA EL TEMA QUE SE USA EN EL SAICOOP DE LA PRECEDENCIA
-- DE GARANTIAS, PARA PODER OFRECER EN LA OFERTA DEL PRESTAMO UN MONTO MAYOR AL
-- DISPONIBLE, SI SE PUEDE QUITAR GARANTIA QUE TENGAN OTROS PRESTAMOS
-- (JFPA 30/MAYO/2023)
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

create or replace function sai_precedencia_garantias_esta_en_lista_productos(text, integer)
returns boolean as $$
declare
  p_lista      alias for $1;
  p_idproducto alias for $2;

  px1 varchar;

  subp2 integer;
  subp3 integer;
  subp4 integer;

  x integer;
  y integer;
  z integer;

  si_esta boolean;
begin

  if p_lista is NULL or p_lista = '' then return FALSE; end if;

  if p_idproducto is NULL then return FALSE; end if;

  px1   := p_idproducto::varchar;
  subp2 := substr(px1,1,2)::integer;
  subp3 := substr(px1,1,3)::integer;
  subp3 := substr(px1,1,4)::integer;

  x := sai_findstr(p_lista, ',') + 1;

  if x = 1 then
    y := p_lista::integer;

    return (y = p_idproducto or y = subp2 or y = subp3 or y = subp4);
  end if;

  si_esta := FALSE; y := 0;
  while y < x
  loop
    y := y + 1;

    px1 := sai_token(y, p_lista, ',');
    z := px1::integer;

    if z = p_idproducto or z = subp2 or z = subp3 or z = subp4 then
      si_esta := TRUE;
      exit;
    end if;
  end loop;
/*
a.idproducto IN (SELECT idproducto::INTEGER FROM regexp_split_to_table(prodx, ',') AS idproducto)
select idproducto in
       (select idproducto::integer
        from regexp_split_to_table((select sai_replace(dato2,'|',',') from tablas
                                    where lower(idtabla)='param' and lower(idelemento) like 'precedencia_garantia_prest%%' and
                                          sai_texto1_like_texto2(30102::text,null,sai_replace(dato1,'|',','),',') > 0), ',') as idproducto);

select 30134 in
       (select idproducto::integer
        from regexp_split_to_table((select sai_replace(dato2,'|',',') from tablas
                                    where lower(idtabla)='param' and lower(idelemento) like 'precedencia_garantia_prest%%' and
                                          sai_texto1_like_texto2(30102::text,null,sai_replace(dato1,'|',','),',') > 0), ',') as idproducto);
*/
  return si_esta;
end;
$$ language 'plpgsql';

create or replace function sai_es_prestamo_con_precedencia_de_garantias(integer)
returns boolean as $$
declare
  p_idproducto alias for $1;

  lista_productos text;

  x integer;

  si_es_producto boolean;
begin

  x := 0;
  select into x count(*)
  from tablas
  where lower(idtabla)='param' and lower(idelemento) like 'precedencia_garantia_prest%%';
  if not found or x is NULL then x := 0; end if;
  if x <= 0 then return FALSE; end if;


  select into lista_productos sai_replace(dato1, '|', ',')
  from tablas
  where lower(idtabla) = 'param' and lower(idelemento) like 'precedencia_garantia_prest%%' and
        sai_texto1_like_texto2(p_idproducto::text, NULL, dato1, '|') > 0;

  si_es_producto := FALSE;
  si_es_producto := sai_precedencia_garantias_esta_en_lista_productos(lista_productos, p_idproducto);

  return si_es_producto;
end;
$$ language 'plpgsql';

create or replace function sai_prezzta_precedencia_garantias(integer, integer, integer, integer, numeric)
returns numeric as $$
declare
  p_idorigen   alias for $1;
  p_idgrupo    alias for $2;
  p_idsocio    alias for $3;
  p_idproducto alias for $4;
  p_monto      alias for $5; -- = 0 : se esta calculando el monto disponible para el prestamo
                             -- > 0 : ya se esta solicitando el prestamo
  lista1 text;
  lista2 text;

  saldo_ah          numeric;
  garantia_actual   numeric;
  garantia_otros    numeric;
  garantia_no_tocar numeric;
  monto_aut         numeric;
  disponible        numeric;
  disponible2       numeric;
  nueva_garantia    numeric;
  disp_otros        numeric;
  gar_temp          numeric;
  dif               numeric;

  idorigenp_ah  integer;
  idproducto_ah integer;
  idauxiliar_ah integer;

  r_folios record;

  x integer;
begin

  if not sai_es_prestamo_con_precedencia_de_garantias(p_idproducto) then return 0.0; end if;

  saldo_ah          := 0.0;
  garantia_actual   := 0.0;
  garantia_otros    := 0.0;
  garantia_no_tocar := 0.0;
  monto_aut         := 0.0;
  disponible        := 0.0;
  disponible2       := 0.0;
  nueva_garantia    := 0.0;
  disp_otros        := 0.0;

  -- Listas de productos donde esta el producto original y la 2a lista que son a
  -- los que se les puede quitar garantia
  ------------------------------------------------------------------------------
  -- OJO : EN LA CONFIGURACION NORMAL DE ESTA TABLA, SI dato3 = 1 QUIERE DECIR
  -- QUE SOLO SE LE AVISA AL USUARIO QUE SE USARA LA GARANTIA, AQUI SE HARA ESO
  -- SIN CONSIDERAR ESTE DATO
  ------------------------------------------------------------------------------
  lista1 := NULL; lista2 := NULL;
  select into lista1, lista2
              sai_replace(dato1, '|', ','), sai_replace(dato2, '|', ',')
  from tablas
  where lower(idtabla) = 'param' and lower(idelemento) like 'precedencia_garantia_prest%%' and
        sai_texto1_like_texto2(p_idproducto::text, NULL, dato1, '|') > 0;
  if not found then
    lista1 := ''; lista2 := '';
  else
    if lista1 is NULL then lista1 := ''; end if;
    if lista2 is NULL then lista2 := ''; end if;
  end if;

  if lista1 is NULL or length(lista1) = 0 or lista2 is NULL or length(lista2) = 0 then
    if p_monto > 0 then
      raise notice 'ERROR : SE HIZO UN CALCULO PARA MAYOR MONTO DISPONIBLE, PERO NO EXISTE LA TABLA ''param''';
      raise notice 'IDELEMENTO ''precedencia_garantia_prestamos''. LLAME A SISTEMAS.';

      return -1.0;
    end if;

    return 0.0;
  end if;

  if not sai_precedencia_garantias_esta_en_lista_productos(lista1, p_idproducto) then
    if p_monto > 0 then
      raise notice 'ERROR : SE HIZO UN CALCULO PARA MAYOR MONTO DISPONIBLE, PERO EL PRODUCTO YA NO EXISTE';
      raise notice 'EN LA TABLA ''param'' IDELEMENTO ''precedencia_garantia_prestamos''. LLAME A SISTEMAS.';

      return -1.0;
    end if;

    return 0.0;
  end if;

  ----------------------------
  -- DATOS DEL PRODUCTO 110 --
  ----------------------------
  select into x count(*)
  from auxiliares
  where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and
        idorigenp > 0 and idproducto = 110 and idauxiliar > 0 and estatus = 2;
  if not found or x is NULL then x := 0; end if;
  if x <= 0 then
    raise notice 'ERROR : EL SOCIO NO TIENE FOLIO DEL PRODUCTO 110 !!!';
    return -1.0;
  end if;
  if x > 1 then
    raise notice 'ERROR : EL SOCIO TIENE MAS DE UN FOLIO DEL PRODUCTO 110 !!!';
    return -1.0;
  end if;

  select into idorigenp_ah, idproducto_ah, idauxiliar_ah, saldo_ah, garantia_actual
              idorigenp, idproducto, idauxiliar, saldo, garantia
  from auxiliares
  where idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and
        idorigenp > 0 and idproducto = 110 and idauxiliar > 0 and estatus = 2;
  if not found then
    raise notice 'ERROR : EL SOCIO NO TIENE FOLIO DEL PRODUCTO 110 !!!';
    return -1.0;
  else
    if idorigenp_ah    is NULL then idorigenp_ah := 0; end if;
    if idproducto_ah   is NULL then idproducto_ah := 0; end if;
    if idauxiliar_ah   is NULL then idauxiliar_ah := 0; end if;
    if saldo_ah        is NULL then saldo_ah := 0.0; end if;
    if garantia_actual is NULL then garantia_actual := 0.0; end if;
  end if;

  if idorigenp_ah <= 0 or idproducto_ah <= 0 or idauxiliar_ah <= 0 then
    raise notice 'ERROR : EL SOCIO NO TIENE FOLIO DEL PRODUCTO 110 !!!';
    return -1.0;
  end if;

  disponible := saldo_ah - garantia_actual;

  -----------------------------------------------------------
  -- FOLIOS DE PRESTAMOS DEL SOCIO GARANTIZADOS POR EL 110 --
  -----------------------------------------------------------
  for r_folios in
    select a.idorigenp, a.idproducto, a.idauxiliar, a.garantia
    from auxiliares a inner join productos p using (idproducto)
    where a.idorigen = p_idorigen and a.idgrupo = p_idgrupo and a.idsocio = p_idsocio and
          p.tipoproducto = 2 and a.estatus < 3 and a.garantia > 0 and
          (a.idorigenp, a.idproducto, a.idauxiliar) in
          (select idorigenp, idproducto, idauxiliar
           from referenciasp
           where idorigenpr = idorigenp_ah and idproductor = idproducto_ah and idauxiliarr = idauxiliar_ah and
                 tiporeferencia = 4)
  loop
    if sai_precedencia_garantias_esta_en_lista_productos(lista2, r_folios.idproducto) then
      garantia_otros := garantia_otros + r_folios.garantia;
    else
      garantia_no_tocar := garantia_no_tocar + r_folios.garantia;
    end if;
  end loop;

  -- EL DISPONIBLE AUMENTA SI DEL MONTO QUE YA ESTA GARANTIZADO ES DE PRESTAMOS
  -- QUE SE LES PUEDE QUITAR POR LA PRECEDENCIA
  disponible2 := disponible + garantia_otros;


  -- SI APENAS SE ESTA HACIENDO LA OFERTA DE CUANTO PUEDE PEDIR EL SOCIO, AQUI
  -- SE REGRESA ESE MONTO
  if p_monto <= 0 then return disponible2; end if;

  ------------------------------------------------------------------------------
  -- SI p_monto > 0 ES QUE YA SE ESTA HACIENDO LA APERTURA DEL PRESTAMO, POR LO
  -- QUE SE DEBE BORRAR LA GARANTIA DE OTROS FOLIOS
  ------------------------------------------------------------------------------

  if p_monto > disponible2 then
    raise notice 'ERROR : EL DISPONIBLE DEL AHORRO PARA GARANTIA ES MENOR A LO SOLICITADO.';
    raise notice 'REVISE LOS SALDOS DE LA PERSONA O LLAME A SISTEMAS !!';
    return -1.0;
  end if;

  ------------------------------------------------------------------------------
  -- SI p_monto <= disponible QUIERE DECIR QUE NO OCUPARA LA GARANTIA DE OTROS
  -- FOLIOS, POR LO QUE NO SE DEBE BORRAR NADA
  ------------------------------------------------------------------------------
  if p_monto <= disponible then
    return 0.0;
  end if;

  -------------------------------------------------------------
  --- Actualizacion de las garantias de los otros prestamos ---
  -------------------------------------------------------------
  garantia_otros := 0.0; disp_otros := 0.0;

  -- Cuanto le tengo que quitar de garantia a los otros prestamos ??
  disp_otros := p_monto - disponible;

  for r_folios in
    select a.idorigenp, a.idproducto, a.idauxiliar, a.garantia
    from auxiliares a inner join productos p using (idproducto)
    where a.idorigen = p_idorigen and a.idgrupo = p_idgrupo and a.idsocio = p_idsocio and
          p.tipoproducto = 2 and a.estatus < 3 and a.garantia > 0 and
          (a.idorigenp, a.idproducto, a.idauxiliar) in
          (select idorigenp, idproducto, idauxiliar
           from referenciasp
           where idorigenpr = idorigenp_ah and idproductor = idproducto_ah and idauxiliarr = idauxiliar_ah and
                 tiporeferencia = 4)
    order by a.garantia
  loop

    if disp_otros > 0 and sai_precedencia_garantias_esta_en_lista_productos(lista2, r_folios.idproducto) then
      gar_temp := 0.0; dif := 0.0;

      if r_folios.garantia > disp_otros then
        gar_temp := r_folios.garantia - disp_otros;
        dif := disp_otros;
        disp_otros := 0.0;
      else
        gar_temp := 0.0;
        dif := r_folios.garantia;
        disp_otros := disp_otros - r_folios.garantia;
      end if;

      if disp_otros < 0.005 then disp_otros := 0.0; end if;

      update auxiliares set garantia = gar_temp
      where  idorigenp = r_folios.idorigenp and idproducto = r_folios.idproducto and idauxiliar = r_folios.idauxiliar;

      -- SE DEBE AFECTAR INMEDIATAMENTE AL AHORRO, PARA FINES DEL TRIGGER EN
      -- PRESTAMOS (detalle_mov_garantia)
      update auxiliares set garantia = garantia - dif
      where idorigenp = idorigenp_ah and idproducto = idproducto_ah and idauxiliar = idauxiliar_ah;

    end if;

  end loop;

  -- POR SI LAS DUDAS, REVISO LOS PRESTAMOS DEL SOCIO PAGADOS Y CANCELADOS PARA
  -- BORRARLES EL DATO DE LA GARANTIA
  for r_folios in
    select a.idorigenp, a.idproducto, a.idauxiliar, 1 as cual
    from auxiliares a inner join productos p using (idproducto)
    where a.idorigen = p_idorigen and a.idgrupo = p_idgrupo and a.idsocio = p_idsocio and
          p.tipoproducto = 2 and a.estatus > 2 and a.garantia > 0
    union
    select a.idorigenp, a.idproducto, a.idauxiliar, 0 as cual
    from auxiliares_h a inner join productos p using (idproducto)
    where a.idorigen = p_idorigen and a.idgrupo = p_idgrupo and a.idsocio = p_idsocio and
          p.tipoproducto = 2 and a.estatus > 2 and a.garantia > 0
  loop

    if r_folios.cual = 1 then
      update auxiliares set garantia = 0.0
      where  idorigenp = r_folios.idorigenp and idproducto = r_folios.idproducto and idauxiliar = r_folios.idauxiliar;
    else
      update auxiliares_h set garantia = 0.0
      where  idorigenp = r_folios.idorigenp and idproducto = r_folios.idproducto and idauxiliar = r_folios.idauxiliar;
    end if;

  end loop;

  return 0.0;
end;
$$ language 'plpgsql';


drop type if exists tipo_lista_pagos_con_descuento cascade;
create type tipo_lista_pagos_con_descuento as (
  idorigenp      integer,
  idproducto     integer,
  idauxiliar     integer,
  idamortizacion integer,
  vence          date,
  saldo          numeric(12,2),
  abono          numeric(12,2),
  iod            numeric(12,2),
  iva_iod        numeric(12,2),
  total_iod      numeric(12,2),
  io             numeric(12,2),
  iva_io         numeric(12,2),
  total_io       numeric(12,2),
  descuento      numeric(12,2)
);

create or replace function
sai_lista_pagos_con_descuento (integer,integer,integer)
returns setof tipo_lista_pagos_con_descuento as $$
declare
  p_idorigenp  alias for $1;
  p_idproducto alias for $2;
  p_idauxiliar alias for $3;

  r tipo_lista_pagos_con_descuento%rowtype;

  x integer;
  y integer;

  r_pagos record;

  tasa_io  numeric;
  tasa_iva numeric;
  saldo_pr numeric;

  fecha_ini date;
  fecha_fin date;

  px1 varchar;

  consulta text;

  saldox        numeric;
  monto_iox     numeric;
  monto_iva_iox numeric;
begin

  x := 0; y := 0;

  select into x count(*) from amortizaciones
  where idorigenp = p_idorigenp and idproducto = p_idproducto and idauxiliar = p_idauxiliar;
  if not found or x is NULL then x := 0; end if;

  select into y count(*) from amortizaciones_h
  where idorigenp = p_idorigenp and idproducto = p_idproducto and idauxiliar = p_idauxiliar;
  if not found or y is NULL then y := 0; end if;

  if x <= 0 and y <= 0 then
    px1 := 'EL FOLIO '||p_idorigenp::varchar||'-'||p_idproducto::varchar||'-'||p_idauxiliar::varchar||
           ' NO TIENE TABLA DE PAGOS !!!';
    raise exception '%', px1;
    return;
  end if;

  tasa_iva := 0.0;
  tasa_iva := tasa_iva_por_sucursal(p_idorigenp, p_idproducto);

  drop table if exists tmpx_lista_pagos_con_descuento;
  create local temp table tmpx_lista_pagos_con_descuento (
    idamortizacion integer,
    vence          date,
    abono          numeric(12,2),
    iod            numeric(12,2),
    iva_iod        numeric(12,2),
    total_iod      numeric(12,2)
  );

  consulta := 
  'insert into tmpx_lista_pagos_con_descuento '||
  '(select idamortizacion,vence,abono,io,io*('||text(tasa_iva)||'/100.0), '||
          '(abono + io*(1 + ('||text(tasa_iva)||'/100.0))) '||
   'from '||(case when x > 0 then 'amortizaciones '
                  else 'amortizaciones_h '
             end)||
   'where idorigenp = '||text(p_idorigenp)||' and idproducto = '||text(p_idproducto)||' and '||
         'idauxiliar = '||text(p_idauxiliar)||')';
  execute consulta;


  tasa_io := 0.0; saldo_pr := 0.0;
  if x > 0 then -- AUXILIARES

    select into tasa_io, saldo_pr, fecha_ini
                tasaio, (case when estatus = 0 then montosolicitado else montoautorizado end),
                (case when estatus =  0 then fechaape
                      when estatus =  1 then fecha_autorizacion
                      when estatus >= 2 then fechaactivacion
                 end)
    from auxiliares
    where idorigenp = p_idorigenp and idproducto = p_idproducto and idauxiliar = p_idauxiliar;
    if not found then
      tasa_io := 0.0; saldo_pr := 0.0;
    else
      if tasa_io  is NULL then tasa_io := 0.0; end if;
      if saldo_pr is NULL then saldo_pr := 0.0; end if;
    end if;

  else -- AUXILIARES_H

    select into tasa_io, saldo_pr, fecha_ini
                tasaio, montoprestado, fechaactivacion
    from auxiliares_h
    where idorigenp = p_idorigenp and idproducto = p_idproducto and idauxiliar = p_idauxiliar;
    if not found then
      tasa_io := 0.0; saldo_pr := 0.0;
    else
      if tasa_io  is NULL then tasa_io := 0.0; end if;
      if saldo_pr is NULL then saldo_pr := 0.0; end if;
    end if;

  end if;

  if tasa_io <= 0.0 or saldo_pr <= 0.0 then
    px1 := 'EL FOLIO '||p_idorigenp::varchar||'-'||p_idproducto::varchar||'-'||p_idauxiliar::varchar||
           ' TIENE UN ERROR EN LA TASA DE INT. ORDINARIO O EN EL MONTO !!!';
    raise exception '%', px1;
    return;
  end if;

  for r_pagos in
    select * from tmpx_lista_pagos_con_descuento
    order by idamortizacion
  loop

    monto_iox     := round((saldo_pr*((tasa_io/100.0)/30.0)*(r_pagos.vence - fecha_ini)), 2);
    monto_iva_iox := round((monto_iox*(tasa_iva/100.0)), 2);
    saldo_pr      := round((saldo_pr - r_pagos.abono), 2);

    fecha_ini := r_pagos.vence;

    r.idorigenp      := p_idorigenp;
    r.idproducto     := p_idproducto;
    r.idauxiliar     := p_idauxiliar;
    r.idamortizacion := r_pagos.idamortizacion;
    r.vence          := r_pagos.vence;
    r.saldo          := saldo_pr;
    r.abono          := r_pagos.abono;
    r.iod            := r_pagos.iod;
    r.iva_iod        := r_pagos.iva_iod;
    r.total_iod      := r_pagos.total_iod;
    r.io             := monto_iox;
    r.iva_io         := monto_iva_iox;
    r.total_io       := r_pagos.abono + monto_iox + monto_iva_iox;
    r.descuento      := (r_pagos.abono + monto_iox + monto_iva_iox) - r_pagos.total_iod;

    return next r;
  end loop;

  drop table if exists tmpx_lista_pagos_con_descuento;

  return;
end;
$$ language 'plpgsql';

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------


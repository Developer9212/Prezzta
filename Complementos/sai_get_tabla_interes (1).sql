create or replace function
sai_pr_get_tabla_interes_valida_formula (text) returns boolean as $$
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
sai_pr_get_tabla_interes_evalua_formula (integer,text,integer,integer,integer) returns text as $$
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
begin

  if p_formula is NULL or trim(p_formula) = '' then
    return '0.00|';
  end if;

  if sai_pr_get_tabla_interes_valida_formula (p_formula) then
    return '0.00|EXISTEN CARACTERES NO VALIDOS EN LA FORMULA: '||p_formula;
  end if;

  select
  into   r_paso *
  from   tablas
  where  idtabla = 'param' and idelemento = 'usar_disponible_para_reciprocidades' and
         sai_texto1_like_texto2(dato2,'|',p_idproducto::text,NULL) > 0;
  b_usa_disp := found;

  if sai_findstr(p_formula,'|') > 0 and sai_findstr(p_formula,'+') then
    return '0.00|SE PUEDE USAR PIPAS (|) O SIGNO DE MAS (+), PERO NO AMBOS: '||p_formula;
  end if;

  v_delim := case when sai_findstr(p_formula,'|') > 0
                  then '|'
                  when sai_findstr(p_formula,'+') > 0
                  then '+'
                  else ''
             end;

  n_suma_reciproc := 0;
  for i_x in 1..sai_findstr(p_formula,v_delim)+1
  loop
    v_oper := split_part(p_formula,v_delim,i_x);
    if sai_findstr(v_oper,'*') > 0 then

      n_mult   := split_part(v_oper,'*',1)::numeric;
      i_idprod := split_part(v_oper,'*',2)::integer;

      select
      into   n_saldo_sum coalesce(sum(saldo - case when b_usa_disp then garantia else 0.00 end ) * n_mul,0)
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
sai_prestamos_get_tabla_intereses (integer,numeric,integer,integer,integer,integer) returns text as $$
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
  n_tasas       numeric;
  n_val_1       numeric;
  n_val_2       numeric;
  n_val_5       numeric;
  i_val_3       integer;
  i_val_4       integer;
  t_paso        text;
begin

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

    if sai_findstr(t_tab.dato3,'|') = 1 then
      i_val_3 := split_part(t_tab.dato3,'|',1)::integer;
      i_val_4 := split_part(t_tab.dato3,'|',2)::integer;
    else
      i_val_3 := r_tab.dato3::integer;
      i_val_4 := 99999;
    end if;

    if r_tab.dato4 is null or r_tab.dato4 = '' or sai_findstr(r_tab.dato4,'|') != 2 then
      return '0.0|0.0|0.0|ERROR, NO ESTAN DEFINIDAS CORRECTAMENTE LAS TASAS EN EL PARAMETRO 4';
    end if;

    if r_tab.dato5 is not null and r_tab.dato5 != '' then
      t_paso = sai_pr_get_tabla_interes_evalua_formula (p_idproducto,r_tab.dato5,p_idorigen,p_idgrupo,p_idsocio);
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

    n_tasas := case when split_part(r_tab.dato4,'|',1) = ''
                    then 0.00
                    else split_part(r_tab.dato4,'|',1)::numeric(6,4)
               end||
               case when split_part(r_tab.dato4,'|',2) = ''
                    then 0.00
                    else split_part(r_tab.dato4,'|',2)::numeric(6,4)
               end||
               case when split_part(r_tab.dato4,'|',3) = ''
                    then 0.00
                    else split_part(r_tab.dato4,'|',3)::numeric(6,4)
               end;
    exit;

  end loop;
  
  if b_found_tabla then
    if b_montos_ok and b_dias_ok and b_recip_ok then
      return n_tasas;
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
  
  return '0.0|0.0|0.0|';
end;
$$ language 'plpgsql';


/*

 Tablas de configuracion:

perfil_1:
p1: usuario
p2: origen de sucursal        (el origen que llevaran los prestamos OPA)
p3: tipo de amortizacion      (default 5)
p4: periodo de abonos         (default 0)
p5: idfinalidad               (del catalogo de finalidades)

perfil_2:
p1: estatus al aperturar      (default 1 "autorizado")
p2: idproducto de garantia    (default 110)
p3: idproducto de renovacion
p4: Es la renovacion          (switch 1:Es, 0:No es)


insert
into   tablas
       (idtabla,idelemento,nombre,dato1,dato2,dato3,dato4,dato5)
values ('prezzta','apertura_perfil_1_32644','datos para aperturar el prestamo','999','30287','5','0','71');

insert
into   tablas
       (idtabla,idelemento,nombre,dato1,dato2,dato3,dato4)
values ('prezzta','apertura_perfil_2_32644','datos para aperturar el prestamo','1','110','32664','0');

insert
into   tablas
       (idtabla,idelemento,nombre,dato1,dato2,dato3,dato4,dato5)
values ('prezzta','apertura_perfil_1_32664','datos para aperturar el prestamo','999','30287','5','0','71');

insert
into   tablas
       (idtabla,idelemento,nombre,dato1,dato2,dato3,dato4)
values ('prezzta','apertura_perfil_2_32664','datos para aperturar el prestamo','1','110','32664','1');

*/

create or replace function
sai_prestamos_crea_apertura_v2 (_text) returns text as $$
/*
-- Sintaxis:
   select sai_prestamos_crea_apertura_v2 (origen_soc, grupo_soc, socio_soc, monto, plazo, producto_apert, origenp_ref, prod_ref, aux_ref)
   NOTA:
     En caso de aperturar el producto RENOVACION, se requiere la Referencia del OPA al que se va renovar (origenp_ref, prod_ref, aux_ref)
     Si es un producto NORMAL solo porner "0,0,0" en los OPA Ref

*/
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
  p_es_renov        boolean;
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

begin
  p_idorigen        := p_mat[1];
  p_idgrupo         := p_mat[2];
  p_idsocio         := p_mat[3];
  p_monto_sol       := p_mat[4];
  p_plazo           := p_mat[5];
  p_idproducto      := p_mat[6];
  p_opa_ref         := p_mat[7];

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
  p_idorigenp   := r_paso.dato2;
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
    raise exception 'EL FINALIDAD DEL CREDITO NO EXISTE';
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

  if p_estatus = 0 then
    x_montoaut := 0;
  else
    x_montoaut := p_monto_sol;
    x_idusraut := p_idusuario;
  end if;

  i_tot_dias := case when r_prod.pagodiafijo = 1
                     then date(p_fecha + (p_plazo::text||' month')::interval) - p_fecha
                     when r_prod.pagodiafijo = 2
                     then date(p_fecha + ((p_plazo/2)::text||' month')::interval +
                          ((case when p_plazo%2 > 0 then 15 else 0 end)::text||' days')::interval) - p_fecha
                     else p_plazo * p_periodo
                end;

  -- Trae las tasas de producto si p_tasa es verdadero
  paso := sai_prestamos_get_tabla_intereses (p_idproducto,p_monto_sol,i_tot_dias,p_idorigen,p_idgrupo,p_idsocio);

  x_tasaio   := split_part(paso,'|',1)::numeric;
  x_tasaiod  := split_part(paso,'|',2)::numeric;
  x_tasaim   := split_part(paso,'|',3)::numeric;

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

  if x_pdiafijo = 1 then
    x_periodo := 0;
  end if;

  llave_folio := 'APE'||trim(to_char(p_idorigenp,'099999'))||
                 trim(to_char(p_idproducto,'09999'));
  x_folio := sai_folio(TRUE,llave_folio);
/*
  paso := 'insert into auxiliares (idorigen,idgrupo,idsocio,idorigenp,idproducto,'||
          'idauxiliar,fechaape,idfinalidad,estatus,plazo,periodoabonos,pagodiafijo,'||
          'montosolicitado,montoautorizado,tasaio,tasaiod,tasaim,idnotas,'||
          'elaboro,autorizo) values(';

  raise notice '% %,%,%,%,%,%,%,%,%,%,%,%,%,%,%,%,NULL,%,%);',paso,p_idorigen,
               p_idgrupo,p_idsocio,p_idorigenp,p_idproducto,x_folio,p_fecha,
               p_idfinalidad,p_estatus,p_plazo,x_periodo,x_pdiafijo,p_monto_sol,
               x_montoaut,x_tasaio,x_tasaiod,x_tasaim,p_idusuario,x_idusraut;
*/

/*
  select
  into   r_paso *
  from   tablas
  where  idtabla = 'param' and idelemento = 'usar_disponible_para_reciprocidades' and
         sai_texto1_like_texto2(dato2,'|',p_idproducto::text,NULL) > 0;
  b_usa_disp := found;

  select
  into   r_paso *,case when b_usa_disp then (saldo - garantia) else saldo end as disponible
  from   auxiliares
  where  idorigen = p_idorigen and idgrupo = p_idgrupo and idsocio = p_idsocio and
         idproducto = p_idprod_gar and estatus = 2;
  
  if round((p_monto_sol * p_porcen_gar),2) > r_paso.disponible then
    n_monto_gar := r_paso.diponible;
  else
    n_monto_gar := round((p_monto_sol * p_porcen_gar),2);
  end if;
*/
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

raise notice 'p_idorigen: %,  p_idgrupo: %,  p_idsocio: %,  p_idorigenp: %,  p_idproducto: %,  x_folio: %,  p_fecha: %,  p_idfinalidad: %,  p_estatus: %,  p_tp_amort: %,  n_monto_gar: %,
          p_plazo: %,  x_periodo: %,  x_pdiafijo: %,  p_monto_sol: %,  x_montoaut: %,  x_tasaio: %,  x_tasaiod: %,  x_tasaim: %,  NULL,  p_idusuario: %,  x_idusraut: %',
          p_idorigen,p_idgrupo,p_idsocio,p_idorigenp,p_idproducto,x_folio,p_fecha,p_idfinalidad,p_estatus,p_tp_amort,n_monto_gar,
          p_plazo,x_periodo,x_pdiafijo,p_monto_sol,x_montoaut,x_tasaio,x_tasaiod,x_tasaim,p_idusuario,x_idusraut;
  insert
  into   auxiliares
         (idorigen,idgrupo,idsocio,idorigenp,idproducto,idauxiliar,fechaape,idfinalidad,estatus,tipoamortizacion,garantia,
          plazo,periodoabonos,pagodiafijo,montosolicitado,montoautorizado,tasaio,tasaiod,tasaim,idnotas,elaboro,autorizo)
  values (p_idorigen,p_idgrupo,p_idsocio,p_idorigenp,p_idproducto,x_folio,p_fecha,p_idfinalidad,p_estatus,p_tp_amort,n_monto_gar,
          p_plazo,x_periodo,x_pdiafijo,p_monto_sol,x_montoaut,x_tasaio,x_tasaiod,x_tasaim,NULL,p_idusuario,x_idusraut);

  if p_tp_amort = 0 then
    x_resint := sai_amortizaciones_crea_tabla (p_idorigenp,p_idproducto,x_folio,date(p_fecha),'genera_todo');
  else
    x_resint := sai_amortizaciones_crea_tabla_pf (p_idorigenp,p_idproducto,x_folio,date(p_fecha),1);
  end if;

  insert
  into   referenciasp
  values (p_idorigenp,p_idproducto,x_folio,4,n_monto_gar::text||'|0|',r_paso.idorigenp,r_paso.idproducto,r_paso.idauxiliar);

  return x_folio::text||'|';
end;
$$ language 'plpgsql';




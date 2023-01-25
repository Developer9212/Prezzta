/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.stereotype.Service;

/**
 *
 * @author wilmer
 */
@Service
public class FicheroConexion {

    String fichero = "json.txt";
    String archivoConexion = "conexion_prezzta.txt";
    String nbd = "";
    String ipbd = "";

    public FicheroConexion() {
        leeTxt();
    }

    private String obtenerDireccionTxt() {
        return System.getProperty("user.home");
    }

    private String obeterSeparador() {
        return System.getProperty("file.separator");
    }

    private File obtenerTxt() {
        String sf = obtenerDireccionTxt() + obeterSeparador() + archivoConexion;
        File f = new File(sf);
        if (f.exists()) {
            return f;
        } else {
            System.out.println("El fichero no existe: " + sf);
            return null;
        }
    }
    
    

    private void leeTxt() {
        if (obtenerTxt() != null) {
            try {
                FileReader fr = new FileReader(obtenerTxt());
                BufferedReader br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    leer_lineas(linea);
                }

            } catch (Exception e) {
                System.out.println("Excepcion leyendo txt" + archivoConexion + ": " + e);
            }
        } else {
            System.out.println("No se encontro el fichero.");
        }
    }
    
    public String obtenerTexto() {
    	String t="";
    	 String sf = obtenerDireccionTxt() + obeterSeparador() + fichero;
         File f1 = new File(sf);
        if (obtenerTxt() != null) {
            try {
                FileReader fr = new FileReader(f1);
                BufferedReader br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    t = t+linea;
                }
                br.close();

            } catch (Exception e) {
                System.out.println("Excepcion leyendo txt" + fichero + ": " + e);
            }
        } else {
            System.out.println("No se encontro el fichero.");
        }
        System.out.println("Json Response:"+t);
        return t;
    }
    

    private void leer_lineas(String linea) {
        if (linea.contains("base_de_datos")) {
            nbd = linea.split("=")[1];
        }
        if (linea.contains("direccion_servidor")) {
            ipbd = linea.split("=")[1];
        }
    }

    public String getDatabase() {
        return nbd;
    }

    public String getHost() {
        return ipbd;
    }
}

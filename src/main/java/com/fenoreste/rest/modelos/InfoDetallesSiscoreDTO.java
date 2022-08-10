/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fenoreste.rest.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author wilmer
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InfoDetallesSiscoreDTO {
    private String message;
    private Integer code;
    private DetallesSiscore data;

}

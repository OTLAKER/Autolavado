/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autolavado.controller;

import com.mycompany.autolavado.conexion.conexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ventaModel;

/**
 *
 * @author Victor Dorian
 */
public class ventaController {
    
    public static boolean insertarVenta(ventaModel vent) {
        String sql = "INSERT INTO tbd_venta_servicio (idUsuarioC,idUsuarioL,idServicio,idVehiculo,fecha,hora,estatus,pagado) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = conexionDB.obtenerConexion(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, vent.getIdUsuarioC());
            ps.setInt(2, vent.getIdUsuarioL());
            ps.setInt(3, vent.getIdServicio());
            ps.setInt(4, vent.getIdVehiculo());
            ps.setString(5, vent.getFecha());
            ps.setString(6, vent.getHora());
            ps.setString(7, vent.getEstatus());
            ps.setString(8, vent.getPagado());

            
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;

        }

    }
    
    public static List<ventaModel> obtenerTodos() {
        var lista = new ArrayList<ventaModel>();
        String sql = "SELECT * FROM tbd_venta_servicio";
    try(Connection con = conexionDB.obtenerConexion();
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(sql)){
    while (rs.next()) {
            lista.add(new ventaModel(rs.getInt("idVenta"), rs.getInt("idUsuarioC"), rs.getInt("idUsuarioL"), rs.getInt("idServicio"), rs.getInt("idVehiculo"), rs.getString("fecha"), rs.getString("hora"), rs.getString("estatus"), rs.getString("pagado")));
        }
    }
    catch(SQLException ex){
        
    ex.printStackTrace();
    }
    return lista ;
}
}

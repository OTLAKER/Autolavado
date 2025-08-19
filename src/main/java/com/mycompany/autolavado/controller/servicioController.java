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
import model.servicioModel;

/**
 *
 * @author Victor Dorian
 */
public class servicioController {
    public static boolean insertarServicio(servicioModel serv) {
        String sql = "INSERT INTO tbc_servicios (nombre,descripcion,precio,estatus) VALUES (?,?,?,?)";
        try (Connection con = conexionDB.obtenerConexion(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, serv.getNombre());
            ps.setString(2, serv.getDescripcion());
            ps.setString(3, serv.getPrecio());
            ps.setString(4, serv.getEstatus());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;

        }
        
    }
    public static List<servicioModel> obtenerTodos() {
        var lista = new ArrayList<servicioModel>();
        String sql = "SELECT * FROM tbc_servicios";
    try(Connection con = conexionDB.obtenerConexion();
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(sql)){
    while (rs.next()) {
            lista.add(new servicioModel(rs.getInt("idServicio"), rs.getString("nombre"), rs.getString("descripcion"), rs.getString("precio"), rs.getString("estatus")));
        }
    }
    catch(SQLException ex){
        
    ex.printStackTrace();
    }
    return lista ;
}
    public static List<servicioModel>buscarServicioPorNombre(String serviciobusqueda){
        List<servicioModel>listaServicios = new ArrayList<>();
        String sql = "SELECT * FROM tbc_servicios WHERE nombre LIKE ?";
        try(Connection con = conexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, "%" + serviciobusqueda + "%");
                ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                servicioModel servicio = new servicioModel();
                servicio.setIdServicio(rs.getInt("idServicio"));
                servicio.setNombre(rs.getString("nombre"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getString("precio"));
                servicio.setEstatus(rs.getString("estatus"));
                listaServicios.add(servicio);
                
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
         return listaServicios;       
    }
    public static boolean eliminarServicio(int idServicio){
        String sql = "DELETE FROM tbc_servicios WHERE idServicio= ?";
        try(Connection con = conexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, idServicio);
            ps.executeUpdate();
            return true;
            
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    public static boolean actualizarServicio(servicioModel servicio) {
        String sql = "UPDATE tbc_servicios SET nombre=?, descripcion=?, precio=?, estatus=? WHERE idServicio=? ";
        boolean actualizado = false;
        try (Connection con = conexionDB.obtenerConexion(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setString(3, servicio.getPrecio());
            ps.setString(4, servicio.getEstatus());
            ps.setInt(5, servicio.getIdServicio());

            int filasAfectadas = ps.executeUpdate();
            actualizado = filasAfectadas>0;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
        return actualizado;
    }
}
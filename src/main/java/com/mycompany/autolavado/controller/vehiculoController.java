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
import model.vehiculoModel;

/**
 *
 * @author Victor Dorian
 */
public class vehiculoController {
    
    public static boolean insertarVehiculo(vehiculoModel vehicul) {
        String sql = "INSERT INTO tbb_vehiculos (matricula,modelo,color,marca,anio,tipo,idCliente) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = conexionDB.obtenerConexion(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicul.getMatricula());
            ps.setString(2, vehicul.getModelo());
            ps.setString(3, vehicul.getColor());
            ps.setString(4, vehicul.getMarca());
            ps.setString(5, vehicul.getAnio());
            ps.setString(6, vehicul.getTipo());
            ps.setInt(7, vehicul.getIdCliente());

            
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;

        }

    }
    
    public static List<vehiculoModel> obtenerTodos() {
        var lista = new ArrayList<vehiculoModel>();
        String sql = "SELECT * FROM tbb_vehiculos";
    try(Connection con = conexionDB.obtenerConexion();
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(sql)){
    while (rs.next()) {
            lista.add(new vehiculoModel(rs.getInt("idVehiculo"), rs.getString("matricula"), rs.getString("modelo"), rs.getString("color"), rs.getString("marca"), rs.getString("anio"), rs.getString("tipo"), rs.getInt("idCliente")));
        }
    }
    catch(SQLException ex){
        
    ex.printStackTrace();
    }
    return lista ;
}
    public static List<vehiculoModel>buscarVehiculoPorNombre(String vehiculobusqueda){
        List<vehiculoModel>listaVehiculos = new ArrayList<>();
        String sql = "SELECT * FROM tbb_vehiculos WHERE marca LIKE ?";
        try(Connection con = conexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, "%" + vehiculobusqueda + "%");
                ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                vehiculoModel vehiculo = new vehiculoModel();
                vehiculo.setIdVehiculo(rs.getInt("idVehiculo"));
                vehiculo.setMatricula(rs.getString("matricula"));
                vehiculo.setModelo(rs.getString("modelo"));
                vehiculo.setColor(rs.getString("color"));
                vehiculo.setMarca(rs.getString("marca"));
                vehiculo.setAnio(rs.getString("anio"));
                vehiculo.setTipo(rs.getString("tipo"));
                vehiculo.setIdCliente(rs.getInt("idCliente"));
                listaVehiculos.add(vehiculo);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
         return listaVehiculos;       
    }
    public static boolean eliminarVehiculo(int idVehiculo){
        String sql = "DELETE FROM tbb_vehiculos WHERE idVehiculo= ?";
        try(Connection con = conexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, idVehiculo);
            ps.executeUpdate();
            return true;
            
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    public static boolean actualizarVehiculo(vehiculoModel vehiculo) {
        String sql = "UPDATE tbb_vehiculos SET matricula=?, modelo=?, color=?, marca=?, anio=?, tipo=?, idCliente=? WHERE idVehiculo=? ";
        boolean actualizado = false;
        try (Connection con = conexionDB.obtenerConexion(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehiculo.getMatricula());
            ps.setString(2, vehiculo.getModelo());
            ps.setString(3, vehiculo.getColor());
            ps.setString(4, vehiculo.getMarca());
            ps.setString(5, vehiculo.getAnio());
            ps.setString(6, vehiculo.getTipo());
            ps.setInt(7, vehiculo.getIdCliente());
            ps.setInt(8, vehiculo.getIdVehiculo());

            int filasAfectadas = ps.executeUpdate();
            actualizado = filasAfectadas>0;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
        return actualizado;
    }
}

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
import model.clienteModel;

/**
 *
 * @author Victor Dorian
 */
public class clienteController {
    
   public static boolean insertarCliente(clienteModel client) {
        String sql = "INSERT INTO tbc_clientes (nombre,apellidoPaterno,apellidoMaterno,direccion,telefono,correo,fechaNacimiento,password) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = conexionDB.obtenerConexion(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, client.getNombre());
            ps.setString(2, client.getApellidoPaterno());
            ps.setString(3, client.getApellidoMaterno());
            ps.setString(4, client.getDireccion());
            ps.setString(5, client.getTelefono());
            ps.setString(6, client.getCorreo());
            ps.setString(7, client.getFechaNacimiento());
            ps.setString(8, client.getPassword());
            
            
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;

        }

    } 
   public static List<clienteModel> obtenerTodos() {
        var lista = new ArrayList<clienteModel>();
        String sql = "SELECT * FROM tbc_clientes";
    try(Connection con = conexionDB.obtenerConexion();
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(sql)){
    while (rs.next()) {
            lista.add(new clienteModel(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("apellidoPaterno"), rs.getString("apellidoMaterno"), rs.getString("direccion"), rs.getString("telefono"), rs.getString("correo"), rs.getString("fechaNacimiento"),rs.getString("password")));
        }
    }
    catch(SQLException ex){
        
    ex.printStackTrace();
    }
    return lista ;
}
   public static List<clienteModel>buscarClientePorNombre(String clientebusqueda){
        List<clienteModel>listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM tbc_clientes WHERE nombre LIKE ?";
        try(Connection con = conexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, "%" + clientebusqueda + "%");
                ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                clienteModel cliente = new clienteModel();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidoPaterno(rs.getString("apellidoPaterno"));
                cliente.setApellidoMaterno(rs.getString("apellidoMaterno"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setFechaNacimiento(rs.getString("fechaNacimiento"));
                cliente.setPassword(rs.getString("password"));
                listaClientes.add(cliente);
                
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
         return listaClientes;       
    }
   public static boolean eliminarCliente(int idCliente){
        String sql = "DELETE FROM tbc_clientes WHERE idCliente= ?";
        try(Connection con = conexionDB.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setInt(1, idCliente);
            ps.executeUpdate();
            return true;
            
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
   public static boolean actualizarCliente(clienteModel cliente) {
        String sql = "UPDATE tbc_clientes SET nombre=?, apellidoPaterno=?, apellidoMaterno=?, direccion=?, telefono=?, correo=?, fechaNacimiento=?, password=? WHERE idCliente=? ";
        boolean actualizado = false;
        try (Connection con = conexionDB.obtenerConexion(); 
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidoPaterno());
            ps.setString(3, cliente.getApellidoMaterno());
            ps.setString(4, cliente.getDireccion());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getCorreo());
            ps.setString(7, cliente.getFechaNacimiento());
            ps.setString(8, cliente.getPassword());
            ps.setInt(9, cliente.getIdCliente());

            int filasAfectadas = ps.executeUpdate();
            actualizado = filasAfectadas>0;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
        return actualizado;
    }
}

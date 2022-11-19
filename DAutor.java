/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import entidades.Autor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author labc205
 */
public class DAutor {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public void obtRegistros(){
        try{
            conn = Conexion.obtConexion();
            String tSQL = "Select * from Autor";
            ps = conn.prepareStatement(tSQL,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE,
                    ResultSet.HOLD_CURSORS_OVER_COMMIT);
            rs = ps.executeQuery();
        }catch(SQLException ex) {
            System.out.println("Error al obtener registros:" + ex.getMessage());
        }
    }
    
    public ArrayList<Autor> listarAutor(){
        ArrayList<Autor> lista = new ArrayList<>();
        try{
            this.obtRegistros();
            while (rs.next()){
                lista.add(new Autor(
                        rs.getInt("AuthorID"), 
                        rs.getString("firstName"), 
                        rs.getString("lastName")
                ));
            }
        } catch(SQLException ex){
            System.out.println("Error al listar autor " + ex.getMessage());
        } finally {
            
            try{
                if (rs != null){
                    rs.close();
                }
                
                if (ps != null){
                    ps.close();
                }
                
                if (conn != null){
                    Conexion.cerrarConexion(conn);
                }
            } catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
         return lista;
    }
    
    public boolean guardarAutor(Autor a){
        boolean guardado = false;
        this.obtRegistros();
        try{
            rs.moveToInsertRow();
            rs.updateString("Firstname", a.getFirstName());
            rs.updateString("Lastname", a.getLastName());
            rs.insertRow();
            rs.moveToCurrentRow();
            guardado = true;
        } catch(SQLException ex){
            System.out.println("Error al guardar autor: " + ex.getMessage());
        }
        finally {
            try{
                if (rs != null){
                    rs.close();
                }
                
                if (ps != null){
                    ps.close();
                }
                
                if (conn != null){
                    Conexion.cerrarConexion(conn);
                }
            } catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
         return guardado;
    }
    
    public boolean existeAutor(int id) {
        boolean resp = false;
        this.obtRegistros();
        try{
            rs.beforeFirst();
            while(rs.next()){
                if(rs.getInt("AuthorID") == id){
                    resp = true;
                    break;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error al buscar autor: " + ex.getMessage());
        }
        finally {
            try{
                if (rs != null){
                    rs.close();
                }
                
                if (ps != null){
                    ps.close();
                }
                
                if (conn != null){
                    Conexion.cerrarConexion(conn);
                }
            } catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
         return resp;
    }
    
    public boolean editarAutor(Autor a){
        boolean resp = false;
        this.obtRegistros();
        try{
            rs.beforeFirst();
            while(rs.next()){
                if (rs.getInt("AuthorID") == a.getAuthorID()){
                    rs.updateString("Firstname", a.getFirstName());
                    rs.updateString("Lastname", a.getLastName());
                    rs.updateRow();
                    resp = true;
                    break;
                }
            }
        } catch (SQLException ex){
            System.out.println("Error al editar: " + ex.getMessage());
        }
        
        finally {
            try{
                if (rs != null){
                    rs.close();
                }
                
                if (ps != null){
                    ps.close();
                }
                
                if (conn != null){
                    Conexion.cerrarConexion(conn);
                }
            } catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
         return resp;
    }
    
    public boolean eliminarAutor(int id){
        boolean resp = false;
        this.obtRegistros();
        try{
            rs.beforeFirst();
            while(rs.next()){
                if(rs.getInt("AuthorID")==id){
                    rs.deleteRow();
                    resp = true;
                    break;
                }
            }
        } catch(SQLException ex){
            System.out.println("Error al eliminar autor"+ ex.getMessage());
        } finally {
            try{
                if (rs != null){
                    rs.close();
                }
                
                if (ps != null){
                    ps.close();
                }
                
                if (conn != null){
                    Conexion.cerrarConexion(conn);
                }
            } catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
         return resp;
    }
}

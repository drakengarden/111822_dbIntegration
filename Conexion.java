/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 *
 * @author labc205
 */

import java.sql.*;


public class Conexion {
    private static Conexion conx = null;
    private static Connection con = null;
    private static String url = "jdbc:sqlserver://localhost;"
            + "databaseName=BDLibros;"
            + "Persist Security Info=True;";
    private static String user = "sa";
    private static String password = "123";

    public Conexion() {
    }
    
    public static Conexion obtInstancia(){
        if(conx==null){
            conx = new Conexion();
        }
        return conx;
    }
    
    public static Connection obtConexion() {
        if (estaConectado()==false){
            Conexion.crearConexion();
        }
        return con;
    }
    
    @SuppressWarnings("UseSpecificCatch")
    public static boolean estaConectado(){
        boolean resp = false;
        try{
            resp = !((con==null) || (con.isClosed()));
        }
        catch(Exception e){
            System.out.println("Error al consultar el estado de la conexion: "
            +e.getMessage());
        }
        return resp;
    }
    
    public static void crearConexion()
    {
        try{
            Class.forName("Com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con= DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException e){
            con = null;
            System.out.println("Error al cargar el driver: " +e.getMessage());
        }
        catch(SQLException e){
            con=null;
            System.out.println("Error al establecer la conexion:" +e.getMessage());
        }
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static void cerrarConexion(Connection con){
        if(estaConectado()!=false){
            try{
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
                System.out.println("Error al cerrar la conexion: "+e.getMessage());
            }
        }
    }
}

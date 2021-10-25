/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigoton.controlers;

import codigoton.models.Cliente;
import codigoton.models.Cuenta;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jairo
 */
public class ConexionBD {
    private static Connection connection;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/evalart_reto";
    private static ConexionBD conexionBD;
    private ArrayList<Cliente> clientes;
    private ArrayList<Cuenta> cuentas;

    private ConexionBD() {
    }
    /**
     * Se obtiene un unico objeto te tipo ConexionBD para agilizar el acceso a la informacion previamente cargada
     * @return 
     */
    public static ConexionBD getConexionBD(){
       if (conexionBD==null){
           conexionBD = new ConexionBD();
       } 
       return conexionBD;
    }
    /**
     * Método para la conexion a la BD 
     * @return 
     */
    public Connection conectar(){
        connection=null;
        try {
            Class.forName(driver);
            connection=DriverManager.getConnection(url, user, password);
            
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
    public void desconectar(){
        connection=null;
    }
    /**
     * se hace una busqueda de la informacion de los clientes 
     * y se calcula el total de todas la cuentas de cada cliente
     */
    public void cargarClientes() {
        clientes = new ArrayList<>();
        cuentas = new ArrayList<>();
        try {
            
                Statement st = conexionBD.conectar().createStatement();
                ResultSet rs= st.executeQuery("SELECT * FROM client");
                while(rs.next()){
                    Cliente cliente= new Cliente(rs.getInt(1), rs.getString(2), 
                            rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7));
                    clientes.add(cliente);
                }
                rs= st.executeQuery("SELECT * FROM account");
                while(rs.next()){
                    Cuenta cuenta= new Cuenta(rs.getInt(1), rs.getInt(2),rs.getDouble(3));
                    cuentas.add(cuenta);
                }
                desconectar();
                for (int i=0; i<clientes.size();i++){
                    for (int j=0; j<cuentas.size();j++){
                        if(clientes.get(i).getId()==cuentas.get(j).getClient_id()){
                            clientes.get(i).getCuentas().add(cuentas.get(j));
                        }
                    }
                    clientes.get(i).calcularBalanceTotal();
                }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    
    
    
    
    
}

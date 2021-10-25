/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigoton;

import codigoton.controlers.ConexionBD;
import codigoton.controlers.ControlMesas;
import codigoton.controlers.ControlWebService;

/**
 *
 * @author Jairo
 */
public class Codigoton {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Conexion a la base de datos
        ConexionBD conexionBD = ConexionBD.getConexionBD();
        // Se cargan Todos los clientes en una sola lista
        conexionBD.cargarClientes();
        // Objeto de CONTROL  de las mesa y su organizacion 
        ControlMesas controlMesas = new ControlMesas();
        //se carga el archivo TXT con la informacion de las mesas y sus filtros
        controlMesas.cargarEntrada("entrada.txt");
        // creacion de las mesas
        controlMesas.crearMesa();
        //llamado para mostrar la informacion de los clientes y su ubicacion en las mesas
        controlMesas.imprimirOrganizacion();
        
        
        
        
    }
    
}

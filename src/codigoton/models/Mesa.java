/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigoton.models;

import codigoton.controlers.ConexionBD;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Jairo
 */
public class Mesa {
    
    private String Nombre;
    private boolean estado;
    private ArrayList<Cliente> clientes;
    private ArrayList<Filtro> filtros;

    public Mesa(String Nombre) {
        this.Nombre = Nombre;
        clientes = new ArrayList<>();
        filtros = new ArrayList<>();
    }

    public String getNombre() {
        return Nombre;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Filtro> getFiltros() {
        return filtros;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    /**
     * metodo para cargar los Clientes que puedan ser candidatos a ocupar un puesta en las mesas
     */
    public void cargarCandidatos(){
        ConexionBD conexionBD = ConexionBD.getConexionBD();
        for(int i=0; i<conexionBD.getClientes().size();i++){ //carga de la info de los clientes
            clientes.add(conexionBD.getClientes().get(i));
        }
        for (int i=0; i<filtros.size();i++){// se hace un recorrido por los filtro de la mesa
            int caso =0;
            boolean sw=true;
            int contador=0;
            if(filtros.get(i).getNombre().equals("TC")){//segun el caso se estipulara un caso para cada filtro
                caso=1;//Tipo cliente
            }else
                if(filtros.get(i).getNombre().equals("UG")){
                    caso=2;//Ubicacion geografica
                }else
                    if(filtros.get(i).getNombre().equals("RI")){
                        caso=3;//Rango inicia balance
                    }else
                        if(filtros.get(i).getNombre().equals("RF")){
                            caso=4;//Rango Final Balance
                        }
            switch (caso){// segun sea el caso se hace una disccriminacion de los clientes para depurar la lista de clientes
                case 1:
                    
                    while(sw){
                        
                        for (int j =0; j< clientes.size();j++){
                            if(clientes.get(j).getType()!=filtros.get(i).getValor()){
                                contador++;
                            }
                        }
                        for (int j =0; j< clientes.size();j++){
                            if(clientes.get(j).getType()!=filtros.get(i).getValor()){
                                clientes.remove(j);
                            }
                        }
                        if (contador==0){
                            sw=false;
                        }else{
                            contador=0;
                        }
                    }    
                    break;
                case 2:
                    sw=true;
                    contador=0;
                    while(sw){
                        for (int j =0; j< clientes.size();j++){
                            if(Integer.parseInt(clientes.get(j).getLocation())!=filtros.get(i).getValor()){
                                contador++;
                            }
                        }
                        for (int j =0; j< clientes.size();j++){
                            if(Integer.parseInt(clientes.get(j).getLocation())!=filtros.get(i).getValor()){
                                clientes.remove(j);
                            }
                        }
                        if (contador==0){
                            sw=false;
                        }else{
                            contador=0;
                        }
                    }
                    
                    break;
                case 3:
                    sw=true;
                    contador=0;
                    while(sw){
                        for (int j =0; j< clientes.size();j++){
                            if(clientes.get(j).getTotalBalance()<filtros.get(i).getValor()){
                                contador++;
                            }
                        }
                        for (int j =0; j< clientes.size();j++){
                            if(clientes.get(j).getTotalBalance()<filtros.get(i).getValor()){
                                clientes.remove(j);
                            }
                        }
                        if (contador==0){
                            sw=false;
                        }else{
                            contador=0;
                        }
                    }
                    break;
                case 4:
                    sw=true;
                    contador=0;
                    while(sw){
                        for (int j =0; j< clientes.size();j++){
                            if(clientes.get(j).getTotalBalance()>filtros.get(i).getValor()){
                                contador++;
                            }
                        }
                        for (int j =0; j< clientes.size();j++){
                            if(clientes.get(j).getTotalBalance()>filtros.get(i).getValor()){
                                clientes.remove(j);
                            }
                        }
                        if (contador==0){
                            sw=false;
                        }else{
                            contador=0;
                        }
                    }
                    break;
                default:
                    break;
                    
            }
        }
    }
    /**
     * metodos para hacer una segunda depuracion de los cliente que pueden ocupar un sitio en la mesa
     */
    public void organizarMesa(){
        
        Collections.sort(clientes);// se organiza la lista segun el balance total y se coloca en oden descendente
        ArrayList<Cliente> candidatos=new ArrayList<>();//lista para candidatos sin aplicar segunda depuracion
        ArrayList<Cliente> hombres = new ArrayList<>();//total de hombres que hay en lista para un cupo
        ArrayList<Cliente> mujeres = new ArrayList<>();//Total de mujeres que hay en lista para un cupo
        for(int i =0; i<clientes.size();i++){//se crea llena la lista de candidatos
            candidatos.add(clientes.get(i));
        }
        //se limpia la lista de clientes para al final del metodo solo tener una lista depuarada con los usuario finales en uso de la mesa 
        clientes.clear();

        for(int i=0; i< candidatos.size();i++){// se separa la lista de candidatos segun su sexo
            if(candidatos.get(i).getMsle()==0){
                mujeres.add(candidatos.get(i));
            }else{
                hombres.add(candidatos.get(i));
            }
        }
        for(int i=0;i<hombres.size();i++){//se agregan los 4 primeros hombres que cumplan la condicion
            if(clientes.size()==0){
                clientes.add(hombres.get(i));
            }else{
                for(int j=0; j<clientes.size(); j++){
                    if(hombres.get(i).getId()!=clientes.get(j).getId()){
                        if(!hombres.get(i).getCompany().equals(clientes.get(j).getCompany())){
                            if(clientes.size()<4){
                                clientes.add(hombres.get(i));
                                break;
                            }
                        }
                    }
                }
            }
        }
        for(int i=0;i<mujeres.size();i++){//se agregan las 4 primeras mujeres que cumplan la condicion
            for(int j=0; j<clientes.size(); j++){
                    if(mujeres.get(i).getId()!=clientes.get(j).getId()){
                        if(!mujeres.get(i).getCompany().equals(clientes.get(j).getCompany())){
                            if(clientes.size()<8){
                                clientes.add(mujeres.get(i));
                                break;
                            }
                        }
                    }
                }
        }
        
        //en esta seccion del codigo se hace una revisio de las cantidades de hombres y mujeres
        // el objetivo es que sean iguales por mesa y se mantenga un numero minimo
        //sino se mraca como CANCELADA
        int nHombres=0;
        int nMujeres=0;
        for(int i=0;i<clientes.size();i++){
            if(clientes.get(i).getMsle()==1){
                nHombres++;
            }else{
                nMujeres++;
            }
        }
        while(nHombres!=nMujeres){
            for(int i=clientes.size()-1;i>=0;i--){
                if(nHombres>nMujeres){
                    if(clientes.get(i).getMsle()==1){
                        clientes.remove(i);
                        break;
                    }
                }else{
                    if(clientes.get(i).getMsle()==0){
                        clientes.remove(i);
                        break;
                    }
                }
            }
            nHombres=0;
            nMujeres=0;
            for(int i=0;i<clientes.size();i++){
                if(clientes.get(i).getMsle()==1){
                    nHombres++;
                }else{
                    nMujeres++;
                }
            }
        }
        
        if(clientes.size()>=4)
            estado=true;
        else
            estado=false;
        //se hace una segunda organizacion de los clientes
        Collections.sort(clientes);
        
    }
    
}

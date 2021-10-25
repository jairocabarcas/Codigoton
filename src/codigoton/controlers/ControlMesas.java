/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigoton.controlers;

import codigoton.models.Filtro;
import codigoton.models.Mesa;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jairo
 */
public class ControlMesas {
    
    ArrayList<String> entradas;
    ArrayList<Mesa> mesas;

    public ArrayList<Mesa> getMesas() {
        return mesas;
    }
    
    
    /**
     * Recibe la ubicacion del archivo TXT a encontrar y cargar
     * Lee cada linea del archivo y lo agrega a un ArrayList
     * @param file 
     */
    public void cargarEntrada(String file){
        entradas = new ArrayList<>();
        try {
            BufferedReader bf= new BufferedReader(new FileReader(file));
            String linea="";
            while((linea=bf.readLine())!=null){
                entradas.add(linea);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControlMesas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /**
     * Crea los onjetos de tipo Mesa y le asigna los filtros a utilizar en cada uno
     */
    public void crearMesa(){
        Mesa mesa=null;
        mesas = new ArrayList<>();
        //se hace el reccorido por cada item del Arrray de entradas para diferenciar los nombres de las mesas de sus filtros
        for (int i=0; i<entradas.size();i++){
            if(entradas.get(i).startsWith("<")){//condicional para detectar el nombre de la mesa que se sabe que empieza por < y termina con >
                mesa= new Mesa(entradas.get(i));//creacion de un onjeto tipo Mesa que se asiga el nombre de la mesa encontrado en dicha posicion
                for (int j=i+1;j<entradas.size();j++){// para los filtros se parte siempres desde el indice siguiente a donde encontro la mesa 
                    if(!entradas.get(j).startsWith("<")){
                        
                        mesa.getFiltros().add(new Filtro(entradas.get(j).substring(0, 2), 
                                Integer.parseInt(entradas.get(j).substring(3))));
                    }else{
                        break;
                    }
                }
                mesas.add(mesa);
            }
        } 
    }
    
    
    /**
     * Muestra la organizacionde las mesas segun el formarto requerido para las salidas
     */
    public void imprimirOrganizacion(){
        ControlWebService cws = new ControlWebService();
        for(int m =0; m<mesas.size();m++){
            System.out.println(mesas.get(m).getNombre());
            mesas.get(m).cargarCandidatos(); // se se carga los clientes que cumplen con los requisitos para estar en la mesa
            mesas.get(m).organizarMesa();// se organiza la mesa por totsal de balance y numero de hombres y de mujeres
            if (mesas.get(m).isEstado()){// si la mesa cumple con el número minimo de participantes, se procede a organizar el formato de salida
                String participantes="";
                
                for(int c=0; c<mesas.get(m).getClientes().size();c++){
                    if(c==0){
                        // condicion para desemcriptar el codigo del cliente en caso de que sea requerido
                        if(mesas.get(m).getClientes().get(c).getEncrypt()==0)
                            participantes=mesas.get(m).getClientes().get(c).getCode();
                        else
                            participantes=cws.desemcriptarnombre(mesas.get(m).getClientes().get(c).getCode());
                    }else{
                        if(mesas.get(m).getClientes().get(c).getEncrypt()==0)
                            participantes=participantes+","+mesas.get(m).getClientes().get(c).getCode();
                        else
                            participantes=participantes+","+cws.desemcriptarnombre(mesas.get(m).getClientes().get(c).getCode());
                    }
                    
                }
                System.out.println(participantes);
            }else{// si no se cumple se muestra que la mesa esta cancelada
                System.out.println("CANCELADA");
            }
        }
    }
    
}

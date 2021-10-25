/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigoton.controlers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;








/**
 *
 * @author Jairo
 */
public class ControlWebService {
    
    /**
     * encargado de hacer la conexion con el servicio web y de capturar el valor del nombre desencriptado
     * @param nombre
     * @return 
     */
    
    public String desemcriptarnombre(String nombre){
        
        String nombreDesencriptado="";
        String html="";
        String codificacion = StandardCharsets.ISO_8859_1.toString();
        try {
            URL url= new URL("https://test.evalartapp.com/extapiquest/code_decrypt/"+nombre);
            URLConnection urlConnection = url.openConnection();
            String tipoConexion=urlConnection.getContentType();
            InputStream input = new BufferedInputStream(urlConnection.getInputStream());
            Reader reader = new InputStreamReader(input);
            int caracter;
            while((caracter=reader.read())!=-1){
                html=html+(char) caracter;
            }
            nombreDesencriptado=html.substring(1, html.length()-1);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ControlWebService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nombreDesencriptado;
    }
    
}

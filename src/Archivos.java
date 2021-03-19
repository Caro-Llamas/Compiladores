/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorlexico;

import java.io.*;

/**
 *
 * @author usuario
 */
public class Archivos {
    private BufferedReader bufferReader;
    private FileReader fileReader;

    public Archivos(FileReader fileReader) {
        this.fileReader = fileReader;
        this.bufferReader = new BufferedReader(this.fileReader);
    }

    public char leerArchivo(){
        char caracterLeido = 0;
        
        try{
            caracterLeido = (char)this.bufferReader.read();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return caracterLeido;
      
    }
    
    public void imprimirCaracter(){
        char salida = 0;
        salida = leerArchivo();
        
        System.out.println(salida);
    }
    
    
    
        
}

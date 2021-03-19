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
public class AnalizadorLexico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        
        Archivos ar = new Archivos(new FileReader("C:\\Users\\usuario\\Documents\\UPIIZ\\6TO SEM\\COMPILADORES\\AnalizadorLexico\\archivo.txt"));
        
        
        ar.imprimirCaracter();
        ar.imprimirCaracter();
        ar.imprimirCaracter();
        ar.imprimirCaracter();
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import clases_de_analisis.AnalizadorArchivos;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

/**
 *
 * @author depot
 */
public class Compiladores {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        
        AnalizadorArchivos analizador = new AnalizadorArchivos(chooser.getSelectedFile());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        System.out.println(analizador.obtenerCadena());
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_de_analisis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.*;

/**
 *
 * @author depot
 */
public class AnalizadorArchivos {
    
    private final File archivo_analizar;
    private final FileReader lector;
    private final BufferedReader buffer;
    private int valorCaracterActual;
    private int valorCaracterAnterior;
    private boolean estado_lector;
    
    //Objetos para checar identificadores
    private final Pattern patron;
    

    public AnalizadorArchivos(File archivo) throws FileNotFoundException {
        this.archivo_analizar = archivo;
        this.lector = new FileReader(this.archivo_analizar);
        this.buffer = new BufferedReader(lector);
        this.valorCaracterActual = Caracter.ESPACIO_BLANCO.getValue();
        this.valorCaracterAnterior = Caracter.ESPACIO_BLANCO.getValue();
        this.estado_lector = false;//falso si no se ha empezado a leer, y verdadero en caso contrario
        this.patron = Pattern.compile("^(int$)|(float$)|(char$)|(double$)|(String$)");//expresion regular para identificadores
    }
    
    public char getCaracterActual() throws IOException{
        return (char)this.buffer.read(); 
    }
    
    public void imprimirSiguiente() throws IOException{
        System.out.println(getCaracterActual()+"");
    }
    
    public void cerrarBuffer() throws IOException{
        this.buffer.close();
    }
    
    public String obtenerCadena() throws IOException{
        
        if(this.valorCaracterAnterior == Caracter.FIN_DOCUMENTO.getValue()){
            return "Fin del archivo";
        }
             
        this.valorCaracterActual = 0;//Para obtener el caracter actual
        String palabra_retorno = "";//Aqui irá la palabra, numero o identificador obtenido
        
        if(!this.estado_lector){//Si aun no se habia empezado a leer el archivo
           this.valorCaracterAnterior = this.buffer.read();
           //Nos posicionamos en la primer letra por ser la primer lectura
           if(this.valorCaracterAnterior == Caracter.ESPACIO_BLANCO.getValue()){
              while((this.valorCaracterAnterior = this.buffer.read()) != Caracter.ESPACIO_BLANCO.getValue()){}
           }
        }
    
        //Agregamos la letra que ya se leyó
        palabra_retorno += (char) this.valorCaracterAnterior;
        
        //Checamos en donde entra el primer caracter
        if(Caracter.LETRA_MAY.isInRange(this.valorCaracterAnterior) || 
           Caracter.LETRA_MIN.isInRange(this.valorCaracterAnterior)){
            
            //Aqui verificamos si es un valor de variable valido
            palabra_retorno = obtenerVariableValido((char) this.valorCaracterAnterior);
            
            //Checamos si es identificador o no
            Matcher matcher = this.patron.matcher(palabra_retorno);
            if(matcher.find()){
                System.out.println("Hay un identificador: " + palabra_retorno);
            }else{
                System.out.println("No hay identificador");
            }
            
            this.valorCaracterAnterior = this.valorCaracterActual;
        }else if(Caracter.NUMERO.isInRange(this.valorCaracterAnterior)){
           //Aqui verificamos que sea un numero valido
           palabra_retorno =  obtenerNumeroValido((char)this.valorCaracterAnterior);
            this.valorCaracterAnterior = this.valorCaracterActual;
        }else if(Caracter.SIMBOLOS_PARENTESIS.isInRange(this.valorCaracterAnterior)){
            //Si son parentesis, unicamente se manda, y el que sigue
            palabra_retorno = (char) this.valorCaracterAnterior + "";
            this.valorCaracterAnterior = this.buffer.read();
        }else if(Caracter.SIMBOLO_ABRIR.getValue() == this.valorCaracterAnterior ||
                 Caracter.SIMBOLO_CERRAR.getValue() == this.valorCaracterAnterior){
            //Si son simbolos de corchetes, unicamente se mandan
            palabra_retorno = (char) this.valorCaracterAnterior + "";
            this.valorCaracterAnterior = this.buffer.read();
        }else if(Caracter.SIMBOLOS_OPERACION.isInRange(this.valorCaracterAnterior)){
            palabra_retorno = (char) this.valorCaracterAnterior + "";
            this.valorCaracterAnterior = this.buffer.read();
        }else{
            this.valorCaracterAnterior = this.buffer.read();
            palabra_retorno = " ";
        }
        
        //Checamos si ya acabamos de leer todo el documento
        if(this.valorCaracterActual == Caracter.FIN_DOCUMENTO.getValue()){
            this.buffer.close();//Cerramos el flujo de datos
        }
            
        this.estado_lector = true;
        
        return palabra_retorno;
    }
    
    private String obtenerNumeroValido(char primerDigito) throws IOException{
        String numeroValido = primerDigito + "";//Aqui guardamos el string resultante
        
        //Aqui guardamos el caracter anterior,
        //esto en caso de que ya hayamos leido y tener el caracter
        //que se leyó en algún lado
        int caracterAnterior = this.buffer.read();
        
        //Comparamos los primeros digitos
        while(Caracter.NUMERO.isInRange(caracterAnterior)){
           numeroValido += (char) caracterAnterior;//agregamos el numero
           caracterAnterior = this.buffer.read();
        }
        
        //Si el caracter ya no es un numero, checamos si es un punto decimal o no
        if(caracterAnterior == Caracter.SIMBOLO_PUNTO.getValue()){
            numeroValido += (char)Caracter.SIMBOLO_PUNTO.getValue();
            caracterAnterior = this.buffer.read();//Leemos el siguiente caracter
            //Si es punto decimal, checamos los numeros del lado derecho
            while(Caracter.NUMERO.isInRange(caracterAnterior)){
                numeroValido += (char) caracterAnterior;//agregamos el numero
                caracterAnterior = this.buffer.read();
            }
            //Asignamos al valor del caracter actual donde se quedó el buffer
            this.valorCaracterActual = caracterAnterior;
        }else{
            //Ya no seria un numero valido asi que regresamos lo que llevamos de numero
            this.valorCaracterActual = caracterAnterior;//Avanzamos al siguiente numero
            return numeroValido;
        }
        
        return numeroValido;
    }

    private String obtenerVariableValido(char caracter) throws IOException {
        String variableValida = "";
        int caracterAnterior = this.buffer.read();
       
        //Checamos los caracteres
        if(Caracter.LETRA_MAY.isInRange((int) caracter)){
            variableValida += caracter;
            //Ciclo que obtiene lo que es una cadena valida
            while(caracterAnterior != Caracter.ESPACIO_BLANCO.getValue() || 
                caracterAnterior !=  Caracter.FIN_DOCUMENTO.getValue()){
               
                //Checamos si es minuscula, mayuscula o linea _
                if(Caracter.LETRA_MAY.isInRange(caracterAnterior) ||
                    Caracter.LETRA_MIN.isInRange(caracterAnterior) ||
                    Caracter.SIMBOLO_LINEA_BAJA.getValue() == caracterAnterior){
                    variableValida += (char)caracterAnterior;//agregamos el caracter
                    caracterAnterior = this.buffer.read();//avanzamos al siguiente
                }else{
                    //No seria valido, asi que finalizamos el ciclo
                    this.valorCaracterActual = caracterAnterior;
                    return variableValida;
                }
               
            }
        }else if(Caracter.LETRA_MIN.isInRange((int) caracter)){
            variableValida += caracter;
            
            //Ciclo que obtiene lo que es una cadena valida
            while(caracterAnterior != Caracter.ESPACIO_BLANCO.getValue() || 
                caracterAnterior !=  Caracter.FIN_DOCUMENTO.getValue()){
                //Checamos si es minuscula, mayuscula o linea _
                if(Caracter.LETRA_MAY.isInRange(caracterAnterior) ||
                    Caracter.LETRA_MIN.isInRange(caracterAnterior) ||
                    Caracter.SIMBOLO_LINEA_BAJA.getValue() == caracterAnterior){
                    variableValida += (char)caracterAnterior;//agregamos el caracter
                    caracterAnterior = this.buffer.read();//avanzamos al siguiente
                }else{
                    //No seria valido, asi que finalizamos el ciclo
                    this.valorCaracterActual = caracterAnterior;
                    return variableValida;
                }
            }
        }else{
           //Si no cumple ninguna, no formaria un nombre valido
           this.valorCaracterActual = caracterAnterior; //Asignamos al valor del caracter actual donde se quedó el buffer
           return variableValida;
        }
        return variableValida;
    }
    
}

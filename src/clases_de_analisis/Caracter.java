/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_de_analisis;

/**
 *
 * @author depot
 * Clase que nos ayuda a tener mejor visualizacion sobre el estado del caracter leido
 */
public enum Caracter{
    
    ESPACIO_BLANCO(32), 
    FIN_DOCUMENTO(-1),
    NUMERO(48,57),
    LETRA_MAY(65,90),
    LETRA_MIN(97,122),
    SIMBOLOS_OPERACION(59,62),
    SIMBOLOS_PARENTESIS(40,41),
    SIMBOLO_ABRIR(123),
    SIMBOLO_PUNTO(46),
    SIMBOLO_LINEA_BAJA(95),
    SIMBOLO_CERRAR(125);
    
    private int valor_ascii;
    private int valor_inicio_ascii;
    private int valor_final_ascii;
    
    private Caracter(int valor){
        this.valor_ascii = valor;
    }
    
    private Caracter(int rango_inicio, int rango_final){
        this.valor_inicio_ascii = rango_inicio;
        this.valor_final_ascii = rango_final;
    }
    
    public boolean isInRange(int valor_comparar){
        if(valor_comparar >= this.valor_inicio_ascii && valor_comparar <= this.valor_final_ascii){
            return true;
        }
        return false;
    }
    
    public int getValue(){
        return this.valor_ascii;
    }
    
    public void setValue(int valor){
        this.valor_ascii = valor;
    }
    
}

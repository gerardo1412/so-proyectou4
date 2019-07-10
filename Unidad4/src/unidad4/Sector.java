/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4;

/**
 *
 * @author Mario
 */
/**
 *
 * @author seba
 */
public class Sector 
{
    private byte[] contenido;
    private String palabra;
    
    public Sector()
    {
        palabra = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                + "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                + "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                + "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                + "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                + "000000000000";
        
        this.contenido = palabra.getBytes();
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public void imprimirCantidadBits(){
        System.out.println(contenido.length);
    }
            
}
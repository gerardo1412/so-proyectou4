/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4;

/**
 *
 * @author Maximo Hernandez
 */
public class Unidad4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Disco disco = new Disco();
        disco.montarDisco(128);
        Sector s = disco.leerSector(0);
    }
    
}

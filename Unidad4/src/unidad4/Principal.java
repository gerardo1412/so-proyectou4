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
public class Principal {
    
    public void imprimirSector( Sector s ){
        int i = 0;
        for(byte b : s.getContenido()){
            System.out.print(Character.toString((char) b));
            i++;
            if(i == 8){
                i=0;
                System.out.print("\n");
            }
        }
    }
}

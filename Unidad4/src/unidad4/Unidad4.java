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
        Menu menu = new Menu();
        Principal principal = new Principal(menu);
        int con = 0;
        while (con != 9){
            con = menu.menu();
            switch(con){
                case 1:
                    principal.formatearDisco();
                    System.out.println("Disco creado.");
                    break;
                case 2:
                    principal.crearArchivo();
                    break;
                case 3:
                    principal.removerArchivo();
                    break;
                case 4:
                    principal.leerArchivo();
                    break;
                case 5:
                    principal.imprimirSector();
                    break;
                case 6:
                    principal.escribirArchivo();
                    break;
                case 7:
                    principal.imprimirArchivo();
                    break;
                case 8:
                    principal.listarDisco();
                    break;
                case 9:
                    break;    
                default:
                    con = 9;
                    break;
            }
        }
        System.out.println("Programa finalizado.");
        
    }
    
}

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
    
    private Disco disco;
    private Menu menu;
    
    public Principal(Menu menu){
        this.disco = new Disco();
        this.menu = menu;
    }
    
    public void formatearDisco(){
        int numSectores = menu.formatearDisco();
        disco.montarDisco(numSectores);
    }
    
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

    public void crearArchivo() {
        
        String[] archivo = menu.crearArchivo();
        
        for(String e: archivo)
            System.out.println(e);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4;

import java.nio.charset.StandardCharsets;

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
    
    public void imprimirSector(){
        int sector = menu.imprimirSector(disco.getNumSectores());
        Sector s = disco.leerSector(sector);
        int i = 0, e = 0;
        byte[] byteList = "00000000".getBytes();
        for(byte b : s.getContenido()){
            //String str = new String(Character.toString((char) b).getBytes(), StandardCharsets.US_ASCII);
            //System.out.println(str);
            System.out.print(Character.toString((char) b));
            
            byteList[i] = b;
            i++;
            if(i == 8){
                i=0;
                e++;
                System.out.print("\n");
                
                /*if(e != 9)
                    System.out.print(new Character ((char)Integer.parseInt(new String(byteList) , 2)));
                else{
                    System.out.print(Integer.parseInt(new String(byteList) , 2));
                    System.out.println("e");
                }*/
            }
            /*if (e == 9)
            {
                e = 0;
                System.out.print("\n");
            }*/
        }
        s.imprimirCantidadBits();
    }

    public void crearArchivo() {
        
        String[] archivo = menu.crearArchivo();
        String listadoDeBytes = "";
        String bytesNoFormateados;
        
        String s = archivo[0];
        for(Character c : s.toCharArray()){
            bytesNoFormateados = Integer.toBinaryString(c);
            while(bytesNoFormateados.length() != 8)
                bytesNoFormateados = "0" + bytesNoFormateados;
            listadoDeBytes = listadoDeBytes + bytesNoFormateados;
        }
        while(listadoDeBytes.length()<64)
            listadoDeBytes = listadoDeBytes + "00000000";
        
        int nSectorInt =  busquedaSector();
        String nSector = Integer.toBinaryString(nSectorInt);
        String numSectores = archivo[1];
        
        while(nSector.length() != 8)
                nSector = "0" + nSector;
        listadoDeBytes = listadoDeBytes + nSector;
        if(this.comprobarEspaciosLibres(Integer.parseInt(numSectores))){
            int nuevoSector = this.comprobarEscritura(listadoDeBytes, nSectorInt);

            System.out.println(archivo.length);

            if(nuevoSector != -1){
                String sectorEsc = "";
                
                for(int contador = 0; contador < Integer.parseInt(numSectores); contador++){
                    int nSectorEscritura = busquedaSector();
                    String nSectorEscString = Integer.toBinaryString(nSectorEscritura);
                    while (nSectorEscString.length() < 8)
                        nSectorEscString = "0" + nSectorEscString;
                    sectorEsc = sectorEsc + nSectorEscString;
                }
                this.escribirInformacionArchivo(disco.leerSector(nuevoSector), sectorEsc, 0, nuevoSector);
            }
        }
        else
            System.out.println("Disco lleno, no se puede escribir");
    }
    
    public int busquedaSector(){
        Sector sector = disco.leerSector(1);
        int bandera = 0;
        int contador = 0;
        while(bandera == 0){
            //int random = (int) (Math.random()*(disco.getNumSectores()-2)) + 2;
            for(int random = 2; random < disco.getNumSectores(); random++){
            if((sector.getContenido())[random] != 49){
                byte[] nuevoSector = sector.getContenido();
                nuevoSector[random] = 49;
                sector.setContenido(nuevoSector);
                disco.escribirSector(1, sector);
                return random;
            }
            contador++;
            if (contador >= (disco.getNumSectores()-2))
                bandera = -1;
        }}
        return bandera;
    }
    
    private boolean comprobarEspaciosLibres(int nSectoresO){
        Sector sector = disco.leerSector(1);
        int nSLibres = 0;
        for(int random = 2; random < disco.getNumSectores(); random++){
            if((sector.getContenido())[random] != 49)
                nSLibres++;
        }
        if(nSLibres >= (nSectoresO+1))
            return true;
        return false;
    }

    private int comprobarEscritura(String listadoDeBytes, int nSectorInt) {
        int nSector = nSectorInt;
        if(nSector != -1){
            Sector s = disco.leerSector(0);
            byte[] directorio = s.getContenido();
            int bandera = 0, contador = 0, multiplicador = 0;
            while (bandera == 0){
                if(directorio[contador + (multiplicador*72)] != 48){
                    contador = 0;
                    multiplicador++;
                }
                else
                    contador++;
                if(contador==8){
                    this.escribirNombreArchivo(s, listadoDeBytes, multiplicador);
                    bandera = 1;
                    return nSector;
                }
                if((multiplicador*72) >= 512){
                    bandera = 1;
                    System.out.println("Directorio lleno, no se puede escribir");
                }
            }
        }
        else
            System.out.println("Bitmap lleno");
        return -1;
    }

    private void escribirNombreArchivo(Sector s, String listadoDeBytes, int multiplicador) {
        byte[] listado = listadoDeBytes.getBytes();
        byte[] contenidoSector = s.getContenido();
        for(int contador = 0; contador < 72; contador++)
            contenidoSector[(multiplicador*72) + contador] = listado[contador];
        s.setContenido(contenidoSector);
        this.disco.escribirSector(0, s);
    }
    
    private void escribirInformacionArchivo(Sector s, String listadoDeBytes, int multiplicador, int nSector){
        byte[] listado = listadoDeBytes.getBytes();
        byte[] contenidoSector = s.getContenido();
        for(int contador = 0; contador < listadoDeBytes.length(); contador++)
            contenidoSector[(multiplicador*72) + contador] = listado[contador];
        s.setContenido(contenidoSector);
        this.disco.escribirSector(nSector, s);
    }
}

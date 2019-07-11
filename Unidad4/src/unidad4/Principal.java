/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * @author Maximo Hernandez
 */
public class Principal {
    
    private Disco disco;
    private Menu menu;
    private String nombreArchivo;
    private ArrayList<Sector> sectores;
    private ArrayList<Integer> numeroSectores;
    
    public Principal(Menu menu){
        this.disco = new Disco();
        this.menu = menu;
        this.nombreArchivo = "";
        this.sectores = new ArrayList<>();
    }
    
    public void formatearDisco(){
        int numSectores = menu.formatearDisco();
        disco.montarDisco(numSectores);
    }
    
    public void imprimirArchivo(){
        String beat = "";
        for(int i = 0, contador = 0; i<this.numeroSectores.size(); i++){
            byte [] cadenaBytes = this.sectores.get(i).getContenido();
            for(byte b : cadenaBytes){
                beat = beat + ((char)Integer.parseInt(Integer.toBinaryString(b), 2));
                contador++;
                if (contador == 8){
                    System.out.print(new Character((char)Integer.parseInt(beat, 2)));
                    beat = "";
                    contador = 0;
                }
            }
        }
        System.out.println("");
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
    
    public void leerArchivo(){
        String archivo = menu.leerArchivo();
        
        String listadoDeBytes = "";
        String bytesNoFormateados;
        
        
        for(Character c : archivo.toCharArray()){
            bytesNoFormateados = Integer.toBinaryString(c);
            while(bytesNoFormateados.length() != 8)
                bytesNoFormateados = "0" + bytesNoFormateados;
            listadoDeBytes = listadoDeBytes + bytesNoFormateados;
        }
        while(listadoDeBytes.length()<64)
            listadoDeBytes = listadoDeBytes + "00000000";
        
        Sector fcb = this.busquedaPorNombre(listadoDeBytes);
        
        if(fcb != null){
            
        }
        else{
            System.out.println("Archivo no encontrado");
        }
        
    }
    
    private Sector busquedaPorNombre(String listadoDeBytes) {
        
        byte[] directorio = disco.leerSector(0).getContenido();
        byte[] listado = listadoDeBytes.getBytes();
        byte[] direccion = "00000000".getBytes();
        int bandera = 0;
        int contador = 0;
        
        for(int i = 0, e = 0, salto = 0; i < 512; ){
            if(directorio[i] == listado[e]){
                e++;
                if(e==64){
                    bandera = 1;
                    e=0;
                }
            }
            else{
                salto++;
                e = 0;
            }
            i++;
            if (bandera == 1){
                direccion[contador] = directorio[i];
                contador++;
                if (contador == 8)
                    return this.getSector(direccion);
            }
        }
        
        return null;
        
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

    private Sector getSector(byte[] direccion) {
        
        this.sectores = new ArrayList<>();
        this.numeroSectores = new ArrayList<>();
        
        int nFcb = Integer.parseInt(new String(direccion), 2);
        
        byte[] fcb = disco.leerSector(nFcb).getContenido();
        byte[] lSector = "00000000".getBytes();
        
        for(int i = 0, e = 0,bandera = 0; bandera != 1; i++){
            lSector[e] = fcb[i];
            e++;
            if(e == 8){
                if(Integer.parseInt(new String(lSector), 2) == 0){
                    bandera = 1;
                    e = 0;
                }
                else{
                    this.numeroSectores.add(Integer.parseInt(new String(lSector), 2));
                    this.sectores.add(disco.leerSector(Integer.parseInt(new String(lSector), 2)));
                    e = 0;
                }
            }
        }
        
        System.out.println(this.sectores.size());
        return disco.leerSector(nFcb);
    }

    public void escribirArchivo(){
        
        String linea = menu.escribirArchivo();
        
        String listadoDeBytes = "";
        String bytesNoFormateados;
        
        for(Character c : linea.toCharArray()){
            bytesNoFormateados = Integer.toBinaryString(c);
            while(bytesNoFormateados.length() != 8)
                bytesNoFormateados = "0" + bytesNoFormateados;
            listadoDeBytes = listadoDeBytes + bytesNoFormateados;
        }
        
        this.escribirEnSectores(listadoDeBytes);
        
    }
    
    private void escribirEnSectores(String linea){
        int nSectoresAOcupar = (int)Math.ceil(linea.length()/512.0);
                
        if(linea.length()<=512){
            while(linea.length()<512*nSectoresAOcupar)
                linea = linea + "00000000";
            for(int i = 0; i<nSectoresAOcupar && i<this.numeroSectores.size(); i++){
                this.sectores.get(i).setContenido(linea.getBytes());
                disco.escribirSector(this.numeroSectores.get(i), this.sectores.get(i));
            }
        }
        if(linea.length()>512){
            while(linea.length()<512*nSectoresAOcupar)
                linea = linea + "00000000";
            for(int i = 0; i<nSectoresAOcupar && i<this.numeroSectores.size(); i++){
                this.sectores.get(i).setContenido(linea.substring(i*512, (i+1)*512).getBytes());
                disco.escribirSector(this.numeroSectores.get(i), this.sectores.get(i));
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import java.io.RandomAccessFile;


/**
 *
 * @author seba
 */
public class Disco 
{
    private File archivoDeDisco;
    private int numSectores;
    
    public Disco()
    {
        this.numSectores = 0;
        this.archivoDeDisco = new File("DISCO");
    }
    
    public void montarDisco(int numSectores)
    {
        this.numSectores = numSectores;
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(this.archivoDeDisco);
            pw = new PrintWriter(fichero);
            
            for(int i=0; i<this.numSectores; i++)
            {
                pw.println(new String((new Sector()).getContenido()));               
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {  
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }    
    
    public Sector leerSector(int numSector)
    {		
        if(this.numSectores >= numSector && numSector >= 0)
        {
            Sector sector = new Sector();
            Scanner lector;
            try 
            {
                lector = new Scanner(this.archivoDeDisco);
                                        
                for (int i=0; i<numSectores && lector.hasNextLine(); i++) 
                {
                    String linea = lector.nextLine();
                    if(i == numSector)
                    {
                        sector.setContenido(linea.getBytes());
                        lector.close();
                        return sector;
                    }                                             
                                                         
                }                        
                lector.close();
            } 
            catch (FileNotFoundException e) 
            {
                e.printStackTrace();
            }
			
            return sector;
        }
        return null;
    }
	
    public void escribirSector(int numSector, Sector sector)
    {
        try {
            RandomAccessFile fichero = new RandomAccessFile("DISCO", "rw");
            
            long posicionPuntero = 512*numSector + numSector*2;
            
            fichero.seek(posicionPuntero);
            String s = new String(sector.getContenido());
            
            fichero.writeBytes(s);

        } catch (IOException ex) {
            Logger.getLogger(Disco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
    public int getNumSectores() {
        return this.numSectores;
    }
}
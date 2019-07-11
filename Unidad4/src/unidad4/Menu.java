/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unidad4;

import java.util.Scanner;

/**
 *
 * @author Maximo Hernandez
 */
public class Menu {
    
    
    public Menu(){
    }
    
    public int menu(){
        Scanner scanner = new Scanner(System.in);
        String atributo = "0";
        int bandera = 0;
        while (bandera==0){
            switch(atributo){
                case "0":
                    System.out.println("Menu\n(1) para crear disco\n(2) para crear un archivo"
                            + "\n(3) para remover un archivo\n(4) para abrir un archivo"
                            + "\n(5) para leer un archivo\n(6) para escribir dentro de un archivo"
                            + "\n(7) para imprimir lo que esta dentro de un archivo"
                            + "\n(8) para listar los archivos\n(9) para salir"
                            + "\nIngrese un numero: ");   
                    atributo = scanner.next();
                    break;
                case "1":
                    return 1;   
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "4":
                    return 4;
                case "5":
                    return 5;
                case "6":
                    return 6;
                case "7":
                    return 7;
                case "8":
                    return 8;
                case "9":
                    return 9;
                default:
                    System.out.println("Error fatal, cerrando programa.");
                    return 9;
            }
        }
        return 0;
    }
    
    public int formatearDisco(){
        Scanner scanner = new Scanner(System.in);
        int atributo = 0;
        while (atributo == 0){
            try{
                System.out.println("Ingrese la cantidad de sectores que su disco posee\nhasta 126 sectores permitidos\n(Tome en cuenta de que los sectores de directorio y bitmap\nse crean por defecto): ");
                atributo = scanner.nextInt();
                if(atributo <= 0 || atributo > 126)
                    throw new Exception();
                return atributo + 2;
            }
            catch (Exception e){
                System.out.println("Por favor, ingrese un numero entre 1 y 126.");
                atributo = 0;
            }
        }
        return atributo;
    }

    public String[] crearArchivo() {
        Scanner scanner = new Scanner(System.in);
        int bandera = 0;
        String[] archivo = {""};
        while(bandera == 0){
                System.out.println("Ingrese el nombre del archivo (sin su extension)\ny el numero de bloques que este ocupara\nseparados por un espacio.");
                archivo = scanner.nextLine().split("\\s+");
                if(archivo.length != 2){
                    System.out.println("Por favor, ingrese el nombre del archivo (sin su extension)\ny el numero de bloques que este ocupara");
                }
                else if(archivo[0].length()<1 || archivo[0].length()>8)
                    System.out.println("Por favor, ingrese el nombre del archivo (sin su extension)\ny el numero de bloques que este ocupara");
                else
                    return archivo;
        }
        return archivo;
    }
    
    public int imprimirSector(int nSectores){
        Scanner scanner = new Scanner(System.in);
        int atributo = -1;
        while (atributo == -1){
            try{
                System.out.println("Ingrese el numero del sector a imprimir: ");
                atributo = scanner.nextInt();
                if(atributo < 0 || atributo >= nSectores)
                    throw new Exception();
                return atributo;
            }
            catch (Exception e){
                System.out.println("Por favor, ingrese un numero entre 0 y " + nSectores + " .");
                atributo = -1;
            }
        }
        return atributo;
    }
    
    public String leerArchivo(){
        Scanner scanner = new Scanner(System.in);
        int bandera = 0;
        String[] archivo = {""};
        while(bandera == 0){
                System.out.println("Ingrese el nombre del archivo (sin su extension).");
                archivo = scanner.nextLine().split("\\s+");
                if(archivo.length != 1){
                    System.out.println("Por favor, ingrese el nombre del archivo (sin su extension).");
                }
                else
                    return archivo[0];
        }
        return archivo[0];
    }

    String escribirArchivo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el texto que desea escribir.");
        String linea = "";
        while(linea.length()==0)
            linea = scanner.nextLine();
        return linea;
    }

    int elegirOpcionEscritura() {
        Scanner scanner = new Scanner(System.in);
        int atributo = -1;
        while (atributo == -1){
            try{
                System.out.println("¿Desea continuar desde donde lo dejo?(1 para Si y 2 para No): ");
                atributo = scanner.nextInt();
                if(atributo != 1 && atributo != 2)
                    throw new Exception();
                return atributo;
            }
            catch (Exception e){
                System.out.println("Por favor, ingrese una de las dos opciones.");
                atributo = -1;
            }
        }
        return atributo;
    }

    String eliminarArchivo() {
        Scanner scanner = new Scanner(System.in);
        int bandera = 0;
        String[] archivo = {""};
        while(bandera == 0){
                System.out.println("Ingrese el nombre del archivo (sin su extension).");
                archivo = scanner.nextLine().split("\\s+");
                if(archivo.length != 1){
                    System.out.println("Por favor, ingrese el nombre del archivo (sin su extension).");
                }
                else
                    return archivo[0];
        }
        return archivo[0];
    }

}

/*
 * libreria que lee y muestra por consola
 */
package consolereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Martinez Piñeiro, Lara Vázquez Dorna
 */
public class ConsoleReader {

    String msg;

    //constructores
    public ConsoleReader(String msg) {
        this.msg = msg;
    }

    public ConsoleReader() {
        this.msg = "";
    }
    /**
     * Metodo que crea un buffer de lectura que guarda del teclado e imprime 
     * en consola el mensaje . Si no se escribe nada devuelve vacio.
     * @return devuelve String con el mensaje que se desea mostrar
     */
    public String read() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            if (!this.msg.isEmpty()){
               System.out.print(this.msg); 
            }
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ConsoleReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}

package principal;

import java.util.concurrent.Exchanger;

/** Hilo consumidor cuyo metodo run() repite indefinidamente el bucle:
 * 1. imprimir en la Salida la cadena de 10 caracteres recibida en el último intercambio con el hilo Productor
 * 2. cambiar con el Productor una cadena vacía por la que ha rellenado hasta que el Productor le proporcione una
 *    cadena vacía, lo que indica que el Productor ha terminado (Señal de parada)
 */
public class HiloConsumidor extends Thread {
    final Exchanger<String> INTERCAMBIADORCADENA; // Exchanger para intercambiar cadenas de caracteres
    String str; // Cadena de caracteres recibida en el último intercambio con el Productor

    /** Constructor de la clase HiloConsumidor
     * @param exchanger Exchanger para intercambiar cadenas de caracteres
     */
    public HiloConsumidor(Exchanger<String> exchanger) {
        INTERCAMBIADORCADENA = exchanger; // Inicializa el Exchanger
    }

    /** Metodo run() del hilo consumidor
     * 1. imprimir en la Salida la cadena de 10 caracteres recibida en el último intercambio con el hilo Productor
     * 2. cambiar con el Productor una cadena vacía por la que ha rellenado hasta que el Productor le proporcione una
     *    cadena vacía, lo que indica que el Productor ha terminado (Señal de parada)
     */
    @Override
    public void run() {
        try {
            // llama a exchange(""), para intercambiar con el hilo Productor una cadena vac'ia por otra rellena (esto
            // bloquea la ejecucion del Consumidor hasta que el Productor esta listo para realizar el intercambio).
            str = INTERCAMBIADORCADENA.exchange("");

            // si en el intercambio no se ha recibido la señal de parada
            if(str.length() > 0) {
                // imprime la cadena recibida en la Salida
                System.out.println("Consumidor escribe " + str + ", mientras Productor compone la siguiente...");
            }
        } catch (InterruptedException e) {
            System.err.println("HiloConsumidor.run() ha sido interrumpido");
            e.printStackTrace();
        }
    }

}

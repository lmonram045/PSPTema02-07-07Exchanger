package principal;

import java.util.concurrent.Exchanger;

/** Hilo productor cuyo método run() ejecuta el bucle:
 * 1 - Agregar 10 carácteres consecutivos a la cadena vacía proporcionada por el hilo Consumidor
 * 2 - Intercambiar con el Consumidor la cadena rellena por una cadena vacía hasta que se recibe una llamada al método
 *     parada()
 */
public class HiloProductor extends Thread {
    final Exchanger<String> INTERCAMBIADORCADENA; // Exchanger para intercambiar cadenas de caracteres
    boolean continuar;

    String str;

    /** Constructor de la clase HiloProductor
     * @param exchanger Exchanger para intercambiar cadenas de caracteres
     */
    public HiloProductor(Exchanger<String> exchanger) {
        INTERCAMBIADORCADENA = exchanger; // Inicializa el Exchanger
        this.str = ""; // Inicializa la cadena de caracteres
        continuar = true; // Inicializa la señal de parada
    }

    /** mientras que no se llama al método parada(), ejecuta el bucle:
     * 1 - Agregar 10 carácteres consecutivos a la cadena vacía proporcionada por el hilo Consumidor
     * 2 - Cambiar con el hilo Consumidor la cadena compuesta por otra vacía cuando se llama a parada(), intercambia la
     *    cadena vacía con el Consumidor (la señal de parada para el Consumidor)
     */
    @Override
    public void run() {
        final char CHTOPE = 1 + 'Z'; // Carácter final de la cadena (tope)
        char ch = 'A'; // Carácter inicial de la cadena
        str = ""; // Inicializa la cadena de caracteres
        // mientras no se indica parada
        while (continuar) {
            // agrega 10 caracteres consecutivos a la cadena vacia recibida en el intercambio anterior
            for (int j = 0; j < 10; j++) {
                str += (char) ch++; // agrega el carácter a la cadena
                // si llego al tope, reinicia el contador
                if (ch == CHTOPE) {
                    ch = 'A';
                }
            }
            try {
                // llama a exchange(str), para intercambiar con el hilo Consumidor la cadena rellenada por otra vacía
                // (esto bloquea la ejecución del Productor hasta que el Consumidor esta listo para realizar el
                // intercambio).
                str = INTERCAMBIADORCADENA.exchange(str);
            } catch (InterruptedException e) {
                System.err.println("HiloProductor.run() ha sido interrumpido");
                e.printStackTrace();
            }
        }

        // si se indica parada
        try {
            // intercambia con el hilo consumidor la cadena vacía (la señal de parada para el Consumidor), por otra
            // cadena vacía (que ya no se vuelve a usar, porque el bucle ha finalizado)
            INTERCAMBIADORCADENA.exchange(str);
        } catch (InterruptedException e) {
            System.err.println("HiloProductor.run() ha sido interrumpido");
            e.printStackTrace();
        }
    }

    /** Indica al hilo Productor que pare
     */
    public void parada() {
        continuar = false; // indica parada
    }


}

package principal;

import java.util.concurrent.Exchanger;

/**
 * El hilo principal de esta aplicación, crea dos nuevos hilos con un punto de sincronización tipo Exchanger.
 *
 * El denominado hilo productor se encarga de rellenar una cadena de diez caracteres (o lista de 10 caracteres)
 *
 * El denominado hilo consumidor imprime esa lista
 *
 * Dejaremos que ambos hilos funcionen alrededor de un segundo, antes de enviarles la orden de parada
 */
public class Principal {
    public static void main(String[] args) {
        Exchanger<String> exgr = new Exchanger<String>(); // Exchanger para intercambiar cadenas de caracteres
        HiloProductor productor = new HiloProductor(exgr); // Crea el hilo productor
        productor.start(); // Inicia el hilo productor
        (new HiloConsumidor(exgr)).start(); // Crea e inicia el hilo consumidor (otra forma de crear hilos)

        try {
            Thread.sleep(1000); // deja que los hilos funcionen un segundo
        } catch (InterruptedException e) {
            System.err.println("Hilo principal ha sido interrumpido");
            e.printStackTrace();
        }
        productor.parada(); // envia la señal de parada al hilo productor
    }
}

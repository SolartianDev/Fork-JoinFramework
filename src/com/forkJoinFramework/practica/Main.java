
package com.forkJoinFramework.practica;

import java.util.concurrent.ForkJoinPool;

public class Main {

    // Utilizamos mucha memoria
    // Las propiedades del proyecto deben tener una opción de ejecución VM de -Xmx1024m
    public static void main(String[] args) {
        int[] datos = new int[1024 * 1024 * 128]; // 512 MB    (1) creacion de datos
        // Crea un gran arreglo de enteros con 512 MB de datos.

//        for (int i = 0; i < datos.length; i++) {
//            datos[i] = ThreadLocalRandom.current().nextInt();
//        }
//        Genera valores aleatorios en el arreglo utilizando un bucle. Este código está comentado, por lo que no se ejecuta.

//        int maximo = Integer.MIN_VALUE;
//        for (int valor : datos) {
//            if (valor > maximo) {
//                maximo = valor;
//            }
//        }
//        System.out.println("Valor máximo encontrado:" + maximo);
//        Encuentra el valor máximo en el arreglo utilizando un bucle. Este código también está comentado.

        ForkJoinPool pool = new ForkJoinPool();
        // (2) Crea una instancia de ForkJoinPool, que se utiliza para la concurrencia.

        AccionArrayAleatorio accion = new AccionArrayAleatorio(datos, 0, datos.length-1, datos.length/16);
        // (3) Crea una instancia de AccionArrayAleatorio para generar números aleatorios en el arreglo.
        pool.invoke(accion); // (4) Invoca la acción para realizar la generación de números aleatorios en paralelo.

        TareaEncontrarMaximo tarea = new TareaEncontrarMaximo(datos, 0, datos.length-1, datos.length/16);
        // (5) Crea una instancia de TareaEncontrarMaximo para encontrar el valor máximo en el arreglo.
        Integer resultado = pool.invoke(tarea);
        // (6) Invoca la tarea para encontrar el valor máximo en paralelo.
        System.out.println("Valor máximo encontrado:" + resultado); 
        // (7) Imprime el valor máximo encontrado en el arreglo.
    }
}

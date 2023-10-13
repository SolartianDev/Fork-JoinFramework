package com.forkJoinFramework.practica;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class AccionArrayAleatorio extends RecursiveAction {  // (1) Define una clase llamada AccionArrayAleatorio que extiende RecursiveAction.

    private final int umbral;  // (2) Declara una variable para el umbral que determinará cuándo generar números aleatorios directamente.
    private final int[] miArray;  // (3) Declara un arreglo de enteros para almacenar los números aleatorios.
    private int inicio;  // (4) Declara una variable para el índice de inicio del sub-array.
    private int fin;  // (5) Declara una variable para el índice de fin del sub-array.

    // Constructor para inicializar los atributos con los valores dados
    public AccionArrayAleatorio(int[] miArray, int inicio, int fin, int umbral) {  // (6) Constructor que recibe el arreglo, los índices de inicio y fin, y el umbral.
        this.umbral = umbral;
        this.miArray = miArray;
        this.inicio = inicio;
        this.fin = fin;
    }

    // Método compute() obligatorio para las tareas RecursiveAction
    @Override
    protected void compute() {  // (7) Implementa el método compute(), que se ejecuta cuando se inicia una tarea RecursiveAction.
        // Si el tamaño del sub-array es menor que el umbral, generar números aleatorios directamente
        if (fin - inicio < umbral) {  // (8) Comprueba si el tamaño del sub-array es menor que el umbral.
            for (int i = inicio; i <= fin; i++) {  // (9) Genera números aleatorios para cada elemento del sub-array.
                miArray[i] = ThreadLocalRandom.current().nextInt();  // (10) Genera un número aleatorio y lo almacena en el arreglo.
            }
        } else {
            // Si el tamaño es mayor que el umbral, dividir la tarea en dos sub-tareas
            int mitad = (fin + inicio) / 2;  // (11) Calcula el punto medio del sub-array.
            AccionArrayAleatorio r1 = new AccionArrayAleatorio(miArray, inicio, mitad, umbral);  // (12) Crea una nueva tarea para la primera mitad del sub-array.
            AccionArrayAleatorio r2 = new AccionArrayAleatorio(miArray, mitad + 1, fin, umbral);  // (13) Crea una nueva tarea para la segunda mitad del sub-array.
            
            // Realizar la bifurcación (fork) y ejecución paralela de las sub-tareas
            invokeAll(r1, r2);  // (14) Invoca (fork) y ejecuta paralelamente las sub-tareas creadas.
        }
    }
}

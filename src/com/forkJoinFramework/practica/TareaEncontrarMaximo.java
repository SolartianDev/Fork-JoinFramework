package com.forkJoinFramework.practica;

import java.util.concurrent.RecursiveTask;

public class TareaEncontrarMaximo extends RecursiveTask<Integer> {  // (1) Define una clase llamada TareaEncontrarMaximo que extiende RecursiveTask.

    private final int umbral;  // (2) Declara una variable para el umbral que determinará cuándo encontrar el máximo directamente.
    private final int[] miArray;  // (3) Declara un arreglo de enteros en el que se buscará el máximo.
    private int inicio;  // (4) Declara una variable para el índice de inicio del sub-array.
    private int fin;  // (5) Declara una variable para el índice de fin del sub-array.

    // Constructor para inicializar los atributos con los valores dados
    public TareaEncontrarMaximo(int[] miArray, int inicio, int fin, int umbral) {  // (6) Constructor que recibe el arreglo, los índices de inicio y fin, y el umbral.
        this.umbral = umbral;
        this.miArray = miArray;
        this.inicio = inicio;
        this.fin = fin;
    }

    // Método compute() obligatorio para las tareas RecursiveTask
    @Override
    protected Integer compute() {  // (7) Implementa el método compute(), que se ejecuta cuando se inicia una tarea RecursiveTask.
    // Si el tamaño del sub-array es menor que el umbral, encontrar el máximo directamente
    if (fin - inicio < umbral) {  // (8) Comprueba si el tamaño del sub-array es menor que el umbral.
        int maximo = Integer.MIN_VALUE;  // (9) Inicializa una variable para el máximo con el valor mínimo de un entero.
        for (int i = inicio; i <= fin; i++) {  // (10) Recorre el sub-array para encontrar el máximo.
            int n = miArray[i];  // (11) Obtiene el valor del elemento actual del arreglo.
            if (n > maximo) {  // (12) Compara el valor actual con el máximo actual.
                    maximo = n;  // (13) Actualiza el máximo si el valor actual es mayor.
            }
        }
            return maximo;  // (14) Devuelve el máximo encontrado en el sub-array.
    } else {
        // Si el tamaño es mayor que el umbral, dividir la tarea en dos sub-tareas
        int mitad = (fin - inicio) / 2 + inicio;  // (15) Calcula el punto medio del sub-array.
        TareaEncontrarMaximo a1 = new TareaEncontrarMaximo(miArray, inicio, mitad, umbral);  // (16) Crea una nueva tarea para la primera mitad del sub-array.
        a1.fork();  // (17) Bifurca (fork) la primera sub-tarea para ejecución paralela.
        TareaEncontrarMaximo a2 = new TareaEncontrarMaximo(miArray, mitad + 1, fin, umbral);  // (18) Crea una nueva tarea para la segunda mitad del sub-array.
            
        // Combinar los resultados de las sub-tareas y encontrar el máximo global
        return Math.max(a2.compute(), a1.join());  // (19) Combina los resultados de las sub-tareas y encuentra el máximo global.
    }
  }
}

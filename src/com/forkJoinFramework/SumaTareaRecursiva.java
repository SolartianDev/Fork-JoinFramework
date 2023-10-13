package com.forkJoinFramework;

import java.util.concurrent.RecursiveTask;

public class SumaTareaRecursiva extends RecursiveTask<Integer> {
    // (1) Define una clase llamada SumaTareaRecursiva que extiende RecursiveTask.

    private static final int UMBRAL = 5;  // (2) Define una constante llamada UMBRAL para determinar cuándo calcular la suma directamente.
    private int[] array;  // (3) Declara un arreglo de enteros en el que se calculará la suma.
    private int inicio;  // (4) Declara una variable para el índice de inicio del sub-array.
    private int fin;  // (5) Declara una variable para el índice de fin del sub-array.

    // Constructor que inicializa los atributos con los valores dados
    public SumaTareaRecursiva(int[] array, int inicio, int fin) {  // (6) Constructor que recibe el arreglo, los índices de inicio y fin.
        this.array = array;
        this.inicio = inicio;
        this.fin = fin;
    }

    // Método compute() obligatorio para las tareas RecursiveTask
    @Override
    protected Integer compute() {  // (7) Implementa el método compute(), que se ejecuta cuando se inicia una tarea RecursiveTask.
        // Si el tamaño del sub-array es menor o igual al umbral, calcular la suma directamente
        if (fin - inicio <= UMBRAL) {  // (8) Comprueba si el tamaño del sub-array es menor o igual al umbral.
            int suma = 0;  // (9) Inicializa una variable para la suma.
            for (int i = inicio; i < fin; i++) {  // (10) Recorre el sub-array para calcular la suma.
                suma += array[i];  // (11) Suma el valor del elemento actual al acumulador de la suma.
            }
            return suma;  // (12) Devuelve la suma total del sub-array.
        } else {
            // Si el tamaño es mayor que el umbral, dividir la tarea en dos sub-tareas
            int mitad = (inicio + fin) / 2;  // (13) Calcula el punto medio del sub-array.
            SumaTareaRecursiva tareaIzquierda = new SumaTareaRecursiva(array, inicio, mitad);  // (14) Crea una nueva tarea para la primera mitad del sub-array.
            SumaTareaRecursiva tareaDerecha = new SumaTareaRecursiva(array, mitad, fin);  // (15) Crea una nueva tarea para la segunda mitad del sub-array.

            // Realizar la bifurcación (fork) de las sub-tareas
            tareaIzquierda.fork();  // (16) Bifurca (fork) la primera sub-tarea para ejecución paralela.
            tareaDerecha.fork();  // (17) Bifurca (fork) la segunda sub-tarea para ejecución paralela.

            // Obtener los resultados de las sub-tareas
            int resultadoIzquierda = tareaIzquierda.join();  // (18) Obtiene el resultado de la primera sub-tarea.
            int resultadoDerecha = tareaDerecha.join();  // (19) Obtiene el resultado de la segunda sub-tarea.

            // Combinar los resultados y devolver el resultado total
            return resultadoIzquierda + resultadoDerecha;  // (20) Combina los resultados de las sub-tareas y devuelve la suma total.
        }
    }

    // Método principal para probar la tarea recursiva
    public static void main(String[] args) {  // (21) Método principal para probar la tarea recursiva.
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};  // (22) Crea un arreglo de enteros para calcular la suma.
        SumaTareaRecursiva tarea = new SumaTareaRecursiva(array, 0, array.length);  // (23) Crea una instancia de SumaTareaRecursiva.
        int resultado = tarea.compute();  // (24) Calcula la suma total utilizando la tarea.
        System.out.println("Suma total: " + resultado);  // (25) Muestra el resultado de la suma.
    }
}


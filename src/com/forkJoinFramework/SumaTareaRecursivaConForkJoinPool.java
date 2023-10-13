package com.forkJoinFramework;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class SumaTareaRecursivaConForkJoinPool extends RecursiveTask<Integer> {
    // (1) Define una clase llamada SumaTareaRecursivaConForkJoinPool que extiende RecursiveTask.

    private static final int UMBRAL = 5; 
    // (2) Define una constante llamada UMBRAL para determinar cuándo calcular la suma directamente.
    private int[] array;  // (3) Declara un arreglo de enteros en el que se calculará la suma.
    private int inicio;  // (4) Declara una variable para el índice de inicio del sub-array.
    private int fin;  // (5) Declara una variable para el índice de fin del sub-array.

    public SumaTareaRecursivaConForkJoinPool(int[] array, int inicio, int fin) {  // (6) Constructor que recibe el arreglo, los índices de inicio y fin.
        this.array = array;
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override// (7) Implementa el método compute(), que se ejecuta cuando se inicia una tarea RecursiveTask.
    protected Integer compute() {  
        if (fin - inicio <= UMBRAL) {
            // (8) Comprueba si el tamaño del sub-array es menor o igual al umbral.
            int suma = 0;
            // (9) Inicializa una variable para la suma.
            for (int i = inicio; i < fin; i++) { 
                // (10) Recorre el sub-array para calcular la suma.
                suma += array[i];
                // (11) Suma el valor del elemento actual al acumulador de la suma.
            }
            return suma;  // (12) Devuelve la suma total del sub-array.
        } else {
            int mitad = (inicio + fin) / 2;  // (13) Calcula el punto medio del sub-array.
            SumaTareaRecursivaConForkJoinPool tareaIzquierda = new SumaTareaRecursivaConForkJoinPool(array, inicio, mitad); 
            // (14) Crea una nueva tarea para la primera mitad del sub-array.
            SumaTareaRecursivaConForkJoinPool tareaDerecha = new SumaTareaRecursivaConForkJoinPool(array, mitad, fin); 
            // (15) Crea una nueva tarea para la segunda mitad del sub-array.

            // Realizar la bifurcación (fork) de las sub-tareas y obtener los resultados
            invokeAll(tareaIzquierda, tareaDerecha);
            // (16) Bifurca (fork) ambas sub-tareas y ejecútalas en paralelo.

            // Combinar los resultados y devolver el resultado total
            return tareaIzquierda.join() + tareaDerecha.join();
            // (17) Combina los resultados de las sub-tareas y devuelve la suma total.
        }
    }

    public static void main(String[] args) {  // (18) Método principal para probar la tarea recursiva.
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};  // (19) Crea un arreglo de enteros para calcular la suma.
        ForkJoinPool pool = new ForkJoinPool();  // (20) Crea un ForkJoinPool para ejecutar tareas.

        // Crear una tarea con el array completo y ejecutarla en el ForkJoinPool
        SumaTareaRecursivaConForkJoinPool tarea = new SumaTareaRecursivaConForkJoinPool(array, 0, array.length);  // (21) Crea una instancia de SumaTareaRecursivaConForkJoinPool.
        int resultado = pool.invoke(tarea);  // (22) Ejecuta la tarea en el ForkJoinPool y obtén el resultado.
        System.out.println("Suma total: " + resultado);  // (23) Muestra el resultado de la suma.

        // Es importante cerrar el ForkJoinPool después de su uso
        pool.shutdown();  // (24) Cierra el ForkJoinPool cuando ya no es necesario.
    }
}


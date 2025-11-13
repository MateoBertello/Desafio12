package desafiobd12; // Paquete corregido

import desafiobd12.dao.CategoriaDAO;
import desafiobd12.dao.LibroDAO;
import desafiobd12.dao.productoDAO;
import desafiobd12.modelo.Categoria;
import desafiobd12.modelo.Libro;
import desafiobd12.modelo.Producto;

// Imports añadidos para el Ejercicio 3
import desafiobd12.dao.EstudianteDAO;
import desafiobd12.dao.CalificacionDAO;
import desafiobd12.modelo.Estudiante;
import desafiobd12.modelo.Calificacion;
import java.sql.Date; // Añadido para el Ejercicio 3

import java.util.List;
import java.util.Scanner;

// Esta es ahora la clase principal correcta
public class DesafioBD12 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Qué proyecto deseas ejecutar?");
        System.out.println("1. Biblioteca");
        System.out.println("2. Tienda");
        System.out.println("3. Academia (Notas)"); // Nueva opción
        System.out.print("Elige una opción (1, 2 o 3): ");
        
        String opcion = scanner.nextLine();
        
        if ("1".equals(opcion)) {
            ejecutarLogicaBiblioteca();
        } else if ("2".equals(opcion)) {
            ejecutarLogicaTienda();
        } else if ("3".equals(opcion)) { // Nueva lógica
            ejecutarLogicaAcademia();
        } else {
            System.out.println("Opción no válida.");
        }
        scanner.close();
    }

    // --- Lógica copiada de tu MainBiblioteca ---
    public static void ejecutarLogicaBiblioteca() {
        LibroDAO libroDAO = new LibroDAO();

        System.out.println("--- 1. Insertando libro nuevo ---");
        Libro nuevoLibro = new Libro("Ficciones", "Jorge Luis Borges", 1944, "978-8420633121", true);
        if (libroDAO.insertar(nuevoLibro)) {
            System.out.println("Insertado: " + nuevoLibro.getTitulo());
        }
        
        System.out.println("\n--- 2. Lista de todos los libros ---");
        List<Libro> todos = libroDAO.obtenerTodos();
        todos.forEach(System.out::println);

        System.out.println("\n--- 3. Libros disponibles (Método extra 1) ---");
        List<Libro> disponibles = libroDAO.obtenerDisponibles();
        disponibles.forEach(System.out::println);

        System.out.println("\n--- 4. Buscar libros por 'García' (Método extra 2) ---");
        List<Libro> porAutor = libroDAO.obtenerPorAutor("García");
        porAutor.forEach(System.out::println);

        // ... (El resto de la lógica de biblioteca) ...
    }

    // --- Lógica copiada de tu MainTienda ---
    public static void ejecutarLogicaTienda() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        productoDAO productoDAO = new productoDAO();

        System.out.println("--- 1. Listar todos los productos (con JOIN) ---");
        productoDAO.obtenerTodos().forEach(System.out::println);

        System.out.println("\n--- 2. Insertar nueva categoría y producto ---");
        // Nota: La categoría 'Frutas y Verduras' ya está en tu script de README.md.
        // Vamos a usar una nueva para evitar errores de UNIQUE constraint.
        Categoria catLimpieza = new Categoria("Limpieza");
        categoriaDAO.insertar(catLimpieza); 
        
        System.out.println("Categoría insertada: " + catLimpieza); 

        Producto lavandina = new Producto("Lavandina 1L", 2.50, 70, catLimpieza);
        if (productoDAO.insertar(lavandina)) {
            System.out.println("Producto insertado: " + lavandina.getNombre());
        }

        System.out.println("\n--- 3. Lista actualizada de productos ---");
        productoDAO.obtenerTodos().forEach(System.out::println);
        
        // ... (El resto de la lógica de tienda) ...
    }
    
    /**
     * Nueva lógica para el Ejercicio 3
     */
    public static void ejecutarLogicaAcademia() {
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        CalificacionDAO calificacionDAO = new CalificacionDAO();
        
        System.out.println("--- 1. Insertando nuevo estudiante ---");
        Estudiante nuevoEstudiante = new Estudiante("Carlos", "Santana");
        if (estudianteDAO.insertar(nuevoEstudiante)) {
            System.out.println("Estudiante insertado: " + nuevoEstudiante);
        } else {
            System.out.println("Error al insertar estudiante.");
            return; // Salimos si no se pudo crear
        }

        System.out.println("\n--- 2. Registrando calificaciones ---");
        
        // Obtenemos la fecha actual de SQL
        long millis = System.currentTimeMillis();
        Date fechaHoy = new Date(millis);
        
        Calificacion c1 = new Calificacion(nuevoEstudiante.getId(), "Física", 9.5, fechaHoy);
        Calificacion c2 = new Calificacion(nuevoEstudiante.getId(), "Química", 7.0, fechaHoy);
        Calificacion c3 = new Calificacion(nuevoEstudiante.getId(), "Programación", 10.0, fechaHoy);

        calificacionDAO.registrar(c1);
        calificacionDAO.registrar(c2);
        calificacionDAO.registrar(c3);
        System.out.println("3 calificaciones registradas para " + nuevoEstudiante.getNombre());

        System.out.println("\n--- 3. Historial de notas del estudiante ---");
        List<Calificacion> historial = calificacionDAO.obtenerHistorial(nuevoEstudiante.getId());
        if (historial.isEmpty()) {
            System.out.println("El estudiante no tiene notas.");
        } else {
            historial.forEach(System.out::println);
        }

        System.out.println("\n--- 4. Promedio del estudiante ---");
        double promedio = calificacionDAO.calcularPromedio(nuevoEstudiante.getId());
        System.out.printf("El promedio de %s es: %.2f\n", nuevoEstudiante.getNombre(), promedio);
    }
}
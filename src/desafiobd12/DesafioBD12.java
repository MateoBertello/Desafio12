package desafiobd12; // Paquete corregido

import desafiobd12.dao.CategoriaDAO;
import desafiobd12.dao.LibroDAO;
import desafiobd12.dao.productoDAO;
import desafiobd12.modelo.Categoria;
import desafiobd12.modelo.Libro;
import desafiobd12.modelo.Producto;

import java.util.List;
import java.util.Scanner;

// Esta es ahora la clase principal correcta
public class DesafioBD12 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Qué proyecto deseas ejecutar?");
        System.out.println("1. Biblioteca");
        System.out.println("2. Tienda");
        System.out.print("Elige una opción (1 o 2): ");
        
        String opcion = scanner.nextLine();
        
        if ("1".equals(opcion)) {
            ejecutarLogicaBiblioteca();
        } else if ("2".equals(opcion)) {
            ejecutarLogicaTienda();
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
        Categoria catFrutas = new Categoria("Frutas y Verduras");
        categoriaDAO.insertar(catFrutas); 
        
        System.out.println("Categoría insertada: " + catFrutas); 

        Producto manzana = new Producto("Manzana Roja (kg)", 1.20, 150, catFrutas);
        if (productoDAO.insertar(manzana)) {
            System.out.println("Producto insertado: " + manzana.getNombre());
        }

        System.out.println("\n--- 3. Lista actualizada de productos ---");
        productoDAO.obtenerTodos().forEach(System.out::println);
        
        // ... (El resto de la lógica de tienda) ...
    }
}
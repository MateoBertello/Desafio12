package biblioteca;

import biblioteca.dao.LibroDAO;
import biblioteca.modelo.Libro;
import java.util.List;

public class MainBiblioteca {
    public static void main(String[] args) {
        LibroDAO libroDAO = new LibroDAO();

        System.out.println("--- 1. Insertando libro nuevo ---");
        Libro nuevoLibro = new Libro("Ficciones", "Jorge Luis Borges", 1944, "978-8420633121", true);
        if (libroDAO.insertar(nuevoLibro)) {
            System.out.println("Insertado: " + nuevoLibro.getTitulo());
        }
        
        System.out.println("\n--- 2. Lista de todos los libros ---");
        List<Libro> todos = libroDAO.obtenerTodos();
        todos.forEach(System.out::println); // Imprime cada libro

        System.out.println("\n--- 3. Libros disponibles (Método extra 1) ---");
        List<Libro> disponibles = libroDAO.obtenerDisponibles();
        disponibles.forEach(System.out::println);

        System.out.println("\n--- 4. Buscar libros por 'García' (Método extra 2) ---");
        List<Libro> porAutor = libroDAO.obtenerPorAutor("García");
        porAutor.forEach(System.out::println);

        System.out.println("\n--- 5. Buscar '1984' y marcar como NO disponible (Actualizar) ---");
        Libro libro1984 = libroDAO.obtenerPorId(3);
        if (libro1984 != null) {
            System.out.println("Encontrado: " + libro1984);
            libro1984.setDisponible(false);
            libroDAO.actualizar(libro1984);
            System.out.println("Actualizado: " + libroDAO.obtenerPorId(3));
        }
        
        System.out.println("\n--- 6. Eliminar 'El Señor de los Anillos' (ID 2) ---");
        if (libroDAO.eliminar(2)) {
            System.out.println("Libro ID 2 eliminado.");
        }

        System.out.println("\n--- 7. Lista final de libros ---");
        libroDAO.obtenerTodos().forEach(System.out::println);
    }
}


package tienda;

import tienda.dao.CategoriaDAO;
import tienda.dao.ProductoDAO;
import tienda.modelo.Categoria;
import tienda.modelo.Producto;

public class MainTienda {
    public static void main(String[] args) {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ProductoDAO productoDAO = new ProductoDAO();

        System.out.println("--- 1. Listar todos los productos (con JOIN) ---");
        // El método obtenerTodos() ya usa el JOIN como pedía el ejercicio
        productoDAO.obtenerTodos().forEach(System.out::println);

        System.out.println("\n--- 2. Insertar nueva categoría y producto ---");
        // El ID se auto-asigna en el DAO después de insertar
        Categoria catFrutas = new Categoria("Frutas y Verduras");
        categoriaDAO.insertar(catFrutas); 
        
        System.out.println("Categoría insertada: " + catFrutas); // Debería mostrar el ID nuevo

        // Usamos el objeto 'catFrutas' (que ya tiene su ID)
        Producto manzana = new Producto("Manzana Roja (kg)", 1.20, 150, catFrutas);
        if (productoDAO.insertar(manzana)) {
            System.out.println("Producto insertado: " + manzana.getNombre());
        }

        System.out.println("\n--- 3. Lista actualizada de productos ---");
        productoDAO.obtenerTodos().forEach(System.out::println);

        System.out.println("\n--- 4. Cambiar 'Pan Francés' de precio y stock ---");
        Producto pan = productoDAO.obtenerPorId(3);
        
        if (pan != null) {
            System.out.println("Antes: " + pan);
            pan.setPrecio(0.85); // Actualizar precio
            pan.setStock(180);  // Actualizar stock
            productoDAO.actualizar(pan);
            System.out.println("Después: " + productoDAO.obtenerPorId(3));
        }
        
        System.out.println("\n--- 5. Eliminar 'Gaseosa 2L' (ID 4) ---");
        if(productoDAO.eliminar(4)){
            System.out.println("Producto ID 4 eliminado.");
        }
        
        System.out.println("\n--- 6. Lista final ---");
        productoDAO.obtenerTodos().forEach(System.out::println);
    }
}
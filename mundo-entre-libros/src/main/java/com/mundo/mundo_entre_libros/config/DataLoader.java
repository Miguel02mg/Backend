package com.mundo.mundo_entre_libros.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mundo.mundo_entre_libros.repository.BookRepository;
import com.mundo.mundo_entre_libros.repository.CategoryRepository;
import com.mundo.mundo_entre_libros.repository.SagaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.mundo.mundo_entre_libros.seed.JsonData;
import org.springframework.core.io.ClassPathResource;
import com.mundo.mundo_entre_libros.model.Book;
import com.mundo.mundo_entre_libros.model.Category;
import com.mundo.mundo_entre_libros.model.Saga;
import com.mundo.mundo_entre_libros.seed.BookJson;
import com.mundo.mundo_entre_libros.seed.CategoryJson;
import com.mundo.mundo_entre_libros.seed.SagaJson;
import java.util.HashMap;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final SagaRepository sagaRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @PersistenceContext
    private EntityManager entityManager;


    public DataLoader(BookRepository bookRepository,
                      CategoryRepository categoryRepository,
                      SagaRepository sagaRepository ){

        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.sagaRepository = sagaRepository;

    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        System.out.println("=================================");
        System.out.println("INICIANDO CARGA DE DATOS...");
        System.out.println("=================================");

// 🔥 desactivar FK checks
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();

// 🔥 hijos primero
        entityManager.createNativeQuery("TRUNCATE TABLE books").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE cart_items").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE wishlist_items").executeUpdate();

// 🔥 padres después
        entityManager.createNativeQuery("TRUNCATE TABLE categories").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE sagas").executeUpdate();

// 🔥 reactivar FK checks
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();

        JsonData data = objectMapper.readValue(
                new ClassPathResource("data/datos.json").getInputStream(),
                JsonData.class
        );

        System.out.println("Categorías: " + data.getCategorias().size());
        System.out.println("Sagas: " + data.getSagas().size());
        System.out.println("Libros: " + data.getLibros().size());

        Map<String, Category> categoriasMap = new HashMap<>();
        Map<String, Saga> sagasMap = new HashMap<>();

        for (CategoryJson categoriaJson : data.getCategorias()) {

            System.out.println("Guardando categoría: " + categoriaJson.getNombre());

            Category categoria = new Category();
            categoria.setName(categoriaJson.getNombre());

            categoryRepository.save(categoria);

            categoriasMap.put(categoriaJson.getId(), categoria);
        }
        System.out.println("===== TODAS LAS CATEGORÍAS EN BD =====");
        System.out.println(categoryRepository.findAll());
        System.out.println("Total en BD: " + categoryRepository.count());

        System.out.println("Guardando sagas...");

        Map<String, Saga> sagaMap = new HashMap<>();

        for (SagaJson sagaJson : data.getSagas()) {

            Saga saga = new Saga();
            saga.setName(sagaJson.getNombre());
            saga.setAuthor(sagaJson.getAutor());
            saga.setEditorial(sagaJson.getEditorial());
            saga.setPrice(sagaJson.getPrecio());
            saga.setCoverUrl(sagaJson.getPortada());
            saga.setDescription(sagaJson.getDescripcion());
            saga.setISBN(sagaJson.getIsbn());

            sagaRepository.save(saga);

            sagaMap.put(sagaJson.getId(), saga);
        }

        System.out.println("Sagas guardadas: " + sagaRepository.count());

        System.out.println("Guardando libros...");

        for (BookJson libroJson : data.getLibros()) {

            Book book = new Book();

            book.setTitle(libroJson.getTitulo());
            book.setAuthor(libroJson.getAutor());
            book.setEdition(libroJson.getEdicion());
            book.setISBN(libroJson.getIsbn());
            book.setPrice(libroJson.getPrecio());
            book.setCoverUrl(libroJson.getPortada());
            book.setSynopsis(libroJson.getSinopsis());

            // 🔥 RELACIÓN CATEGORÍA (OBLIGATORIA)
            Category category = categoriasMap.get(libroJson.getCategoria_id());
            book.setCategory(category);

            // 🔥 RELACIÓN SAGA (OPCIONAL)
            if (libroJson.getSaga_id() != null) {
                Saga saga = sagaMap.get(libroJson.getSaga_id());
                book.setSaga(saga);
            }

            bookRepository.save(book);
        }

        System.out.println("Libros guardados: " + bookRepository.count());

    }

}
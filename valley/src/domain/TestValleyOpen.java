package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.File;
import java.io.FileWriter;

/**
 * Pruebas unitarias para el método open() de Valley.
 * Verifica la correcta recuperación de datos y el manejo de excepciones
 * usando el mensaje estandarizado OPEN_ERROR.
 */
public class TestValleyOpen {

    private Valley valley;
    private File testFile;

    @Before
    public void setUp() {
        valley = new Valley();
        testFile = new File("test_valley_open.dat");
    }

    @After
    public void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    /**
     * Verifica que se pueda abrir un archivo válido y que el estado se actualice.
     */
    @Test
    public void testOpenValidFile() {
        try {
            // 1. Preparar: Crear un valle, modificarlo (simulado) y guardarlo
            Valley originalValley = new Valley();
            // Simulamos un cambio de estado guardando el objeto
            originalValley.save(testFile);

            // 2. Actuar: Abrir el archivo sobre la instancia 'valley'
            valley.open(testFile);

            // 3. Verificar: No debe haber excepciones y los datos básicos deben coincidir
            assertEquals("El tamaño debe ser consistente después de abrir", 
                        originalValley.getSize(), valley.getSize());
            
        } catch (ValleyException e) {
            fail("No debería lanzar excepción con un archivo válido: " + e.getMessage());
        }
    }

    /**
     * Verifica que se lance ValleyException con el mensaje OPEN_ERROR
     * cuando el archivo no existe.
     */
    @Test
    public void testOpenNonExistentFile() {
        File nonExistent = new File("archivo_fantasma.dat");
        try {
            valley.open(nonExistent);
            fail("Debería lanzar ValleyException");
        } catch (ValleyException e) {
            // Verificamos que use EXACTAMENTE la constante requerida
            assertEquals("El mensaje debe ser OPEN_ERROR", 
                        ValleyException.OPEN_ERROR, e.getMessage());
        }
    }

    /**
     * Verifica que se lance ValleyException con el mensaje OPEN_ERROR
     * cuando el archivo está corrupto o no es un objeto Valley.
     */
    @Test
    public void testOpenCorruptedFile() {
        try {
            // Crear un archivo de texto plano (corrupto para ObjectInputStream)
            FileWriter fw = new FileWriter(testFile);
            fw.write("Esto no es un objeto serializado de Java");
            fw.close();

            valley.open(testFile);
            fail("Debería lanzar ValleyException ante archivo corrupto");
        } catch (Exception e) {
            // Aseguramos que sea ValleyException y tenga el mensaje correcto
            if (e instanceof ValleyException) {
                assertEquals("El mensaje debe ser OPEN_ERROR", 
                            ValleyException.OPEN_ERROR, e.getMessage());
            } else {
                fail("Debería lanzar ValleyException, pero lanzó: " + e.getClass().getSimpleName());
            }
        }
    }
}
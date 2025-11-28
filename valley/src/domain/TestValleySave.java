package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.File;

/**
 * Pruebas unitarias para el método save() de Valley.
 * Verifica que el valle se pueda guardar correctamente en un archivo .dat
 * 
 * @author IbañezRubiano
 * @version 1.0
 */
public class TestValleySave {

    private Valley valley;
    private File testFile;

    @Before
    public void setUp() {
        valley = new Valley();
        testFile = new File("test_valley.dat");
    }

    @After
    public void tearDown() {
        // Limpiar archivo de prueba después de cada test
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    /**
     * Verifica que el método save crea un archivo.
     */
    @Test
    public void testSaveCreatesFile() {
        try {
            valley.save(testFile);
            assertTrue("El archivo debe existir después de guardar", testFile.exists());
        } catch (ValleyException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    /**
     * Verifica que el archivo guardado tenga extensión .dat
     */
    @Test
    public void testSaveWithDatExtension() {
        try {
            File fileWithoutExtension = new File("test_valley");
            valley.save(fileWithoutExtension);

            File expectedFile = new File("test_valley.dat");
            assertTrue("El archivo debe tener extensión .dat", expectedFile.exists());

            // Limpiar
            expectedFile.delete();
        } catch (ValleyException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    /**
     * Verifica que el archivo guardado no esté vacío
     */
    @Test
    public void testSaveFileNotEmpty() {
        try {
            valley.save(testFile);
            assertTrue("El archivo no debe estar vacío", testFile.length() > 0);
        } catch (ValleyException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    /**
     * Verifica que se lance excepción con ruta inválida
     */
    @Test
    public void testSaveWithInvalidPath() {
        File invalidFile = new File("/ruta/invalida/que/no/existe/test.dat");
        try {
            valley.save(invalidFile);
            fail("Debería lanzar ValleyException con ruta inválida");
        } catch (ValleyException e) {
            assertTrue("El mensaje debe contener 'Error'",
                    e.getMessage().contains("Error"));
        }
    }
}

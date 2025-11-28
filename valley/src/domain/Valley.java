package domain;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Valle que contiene una matriz de unidades y gestiona la simulación.
 *
 * Guarda, crea y actualiza las unidades (ovejas, lobos, heno, etc.) durante
 * cada tic.
 *
 * @author MorenoRubiano
 * @author2 IbañezRubiano
 * @version 1.0
 */
public class Valley implements Serializable {
    private static final long serialVersionUID = 1L;

    static private int SIZE = 25;
    private Unit[][] places;

    /**
     * Crea un nuevo valle con un tamaño fijo.
     * Inicializa todas las posiciones vacías y agrega algunas unidades por defecto.
     */
    public Valley() {
        places = new Unit[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                places[r][c] = null;
            }
        }
        someUnits();
    }

    /**
     * Retorna el tamaño del valle.
     * 
     * @return tamaño del valle
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Retorna la unidad ubicada en una posición específica.
     * 
     * @param r fila
     * @param c columna
     * @return unidad en la posición indicada o null si está vacía
     */
    public Unit getUnit(int r, int c) {
        return places[r][c];
    }

    /**
     * Asigna una unidad a una posición específica del valle.
     * 
     * @param r fila
     * @param c columna
     * @param e unidad a colocar
     */
    public void setUnit(int r, int c, Unit e) {
        places[r][c] = e;
    }

    /**
     * Crea y ubica algunas unidades iniciales (lobos, ovejas, heno y hoyos)
     * en posiciones predefinidas del valle.
     */
    public void someUnits() {
        Wolf akela = new Wolf(this, 10, 10);
        Wolf larka = new Wolf(this, 15, 15);

        Oveja chaun = new Oveja(this, 1, 5);
        Oveja woolly = new Oveja(this, 13, 10);

        Hay alarm = new Hay(this, 0, 0);
        Hay alert = new Hay(this, 0, SIZE - 1);
        places[0][0] = alarm;
        places[0][SIZE - 1] = alert;

        setUnit(10, 10, akela);
        setUnit(15, 15, larka);

        Humano moreno = new Humano(this, 15, 5);
        Humano rubiano = new Humano(this, 18, 20);

        // Hole Moreno = new Hole(this, 9, 9);
        // Hole Rubiano = new Hole(this, 18, 18);

        // places[9][9] = Moreno;
        // places[18][18] = Rubiano;
    }

    /**
     * Calcula cuántas unidades vecinas son del mismo tipo que la ubicada en la
     * posición dada.
     * 
     * @param r fila
     * @param c columna
     * @return número de vecinos iguales
     */
    public int neighborsEquals(int r, int c) {
        int num = 0;
        if (inValley(r, c) && places[r][c] != null) {
            for (int dr = -1; dr < 2; dr++) {
                for (int dc = -1; dc < 2; dc++) {
                    if ((dr != 0 || dc != 0) && inValley(r + dr, c + dc) &&
                            (places[r + dr][c + dc] != null)
                            && (places[r][c].getClass() == places[r + dr][c + dc].getClass()))
                        num++;
                }
            }
        }
        return num;
    }

    /**
     * Verifica si una posición está vacía dentro del valle.
     * 
     * @param r fila
     * @param c columna
     * @return true si está vacía, false en caso contrario
     */
    public boolean isEmpty(int r, int c) {
        return (inValley(r, c) && places[r][c] == null);
    }

    /**
     * Verifica si una posición está dentro de los límites del valle.
     * 
     * @param r fila
     * @param c columna
     * @return true si la posición es válida
     */
    private boolean inValley(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Ejecuta un ciclo de simulación (tic-tac) para todas las unidades del valle.
     * Cada unidad realiza su acción correspondiente llamando a su método act().
     */
    public void ticTac() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Unit unit = places[r][c];
                if (unit != null) {
                    unit.act();
                }
            }
        }
    }

    // IbañezRubianoLab06 Agregacion de métodos para manejo de archivos (a

    /**
     * Abre un archivo del valle (por implementar).
     * 
     * @param file Archivo a abrir
     * @throws ValleyException con mensaje indicando que está en construcción
     */
    public void open(File file) throws ValleyException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Valley loadedValley = (Valley) ois.readObject();
            ois.close();

            // Actualizamos el estado de ESTA instancia con los datos cargados
            // para no romper la referencia que tiene la GUI.
            this.SIZE = loadedValley.SIZE;
            this.places = loadedValley.places;
            
        } catch (Exception e) {
            // Se cumple la restricción: solo usar OPEN_ERROR
            throw new ValleyException(ValleyException.OPEN_ERROR);
        }
    }

    /**
     * Copia del método open (versión 00).
     * Abre un archivo del valle (por implementar).
     * 
     * @param file Archivo a abrir
     * @throws ValleyException con mensaje indicando que está en construcción
     */
    public void open00(File file) throws ValleyException {
        throw new ValleyException(ValleyException.OPEN_ERROR + ". Archivo " + file.getName());
    }

    /**
     * Guarda el estado actual del valle en un archivo.
     * Serializa el objeto Valley completo con todas sus unidades.
     * 
     * @param file Archivo donde guardar el valle
     * @throws ValleyException si ocurre un error durante el guardado
     */
    public void save(File file) throws ValleyException {
        try {
            // Asegurar que el archivo tenga extensión .dat
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".dat")) {
                file = new File(filePath + ".dat");
            }

            // Crear el flujo de salida para objetos
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

            // Escribir el objeto Valley completo
            oos.writeObject(this);

            // Cerrar el flujo
            oos.close();

        } catch (IOException e) {
            throw new ValleyException("Error al guardar el archivo: " + e.getMessage());
        }
    }

    /**
     * Copia del método save (versión 00).
     * Guarda el valle en un archivo (por implementar).
     * 
     * @param file Archivo donde guardar
     * @throws ValleyException con mensaje indicando que está en construcción
     */
    public void save00(File file) throws ValleyException {
        throw new ValleyException(ValleyException.SAVE_ERROR + ". Archivo " + file.getName());
    }

    /**
     * Importa datos desde un archivo (por implementar).
     * 
     * @param file Archivo a importar
     * @throws ValleyException con mensaje indicando que está en construcción
     */
    public void importFile(File file) throws ValleyException {
        throw new ValleyException(ValleyException.IMPORT_ERROR + ". Archivo " + file.getName());
    }

    /**
     * Exporta datos a un archivo (por implementar).
     * 
     * @param file Archivo donde exportar
     * @throws ValleyException con mensaje indicando que está en construcción
     */
    public void export(File file) throws ValleyException {
        throw new ValleyException(ValleyException.EXPORT_ERROR + ". Archivo " + file.getName());
    }
}

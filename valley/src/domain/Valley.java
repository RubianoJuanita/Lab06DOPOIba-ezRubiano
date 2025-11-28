package domain;

import java.util.*;
import java.io.*;

/**
 * Valle que contiene una matriz de unidades y gestiona la simulación.
 *
 * Guarda, crea y actualiza las unidades (ovejas, lobos, heno, etc.) durante
 * cada tic.
 *
 * @author MorenoRubiano
 * @author2 IbañezRubiano
 * @version 1.1
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
     * * @return tamaño del valle
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Retorna la unidad ubicada en una posición específica.
     * * @param r fila
     * @param c columna
     * @return unidad en la posición indicada o null si está vacía
     */
    public Unit getUnit(int r, int c) {
        if (inValley(r, c)) {
            return places[r][c];
        }
        return null;
    }

    /**
     * Asigna una unidad a una posición específica del valle.
     * * @param r fila
     * @param c columna
     * @param e unidad a colocar
     */
    public void setUnit(int r, int c, Unit e) {
        if (inValley(r, c)) {
            places[r][c] = e;
        }
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
    }

    /**
     * Calcula cuántas unidades vecinas son del mismo tipo que la ubicada en la
     * posición dada.
     * * @param r fila
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
     * * @param r fila
     * @param c columna
     * @return true si está vacía, false en caso contrario
     */
    public boolean isEmpty(int r, int c) {
        return (inValley(r, c) && places[r][c] == null);
    }

    /**
     * Verifica si una posición está dentro de los límites del valle.
     * * @param r fila
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

    /**
     * Abre un archivo binario (.dat) del valle y actualiza el estado.
     * Solo actualiza la matriz de unidades, respetando el tamaño estático.
     * * @param file Archivo a abrir
     * @throws ValleyException si ocurre un error de lectura
     */
    public void open(File file) throws ValleyException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Valley loadedValley = (Valley) ois.readObject();
            ois.close();

            this.places = loadedValley.places;
            
        } catch (Exception e) {
            throw new ValleyException(ValleyException.OPEN_ERROR);
        }
    }

    /**
     * Copia del método open (versión 00).
     * * @param file Archivo a abrir
     * @throws ValleyException indicando construcción
     */
    public void open00(File file) throws ValleyException {
        throw new ValleyException(ValleyException.OPEN_ERROR + ". Archivo " + file.getName());
    }

    /**
     * Guarda el estado actual del valle en un archivo binario (.dat).
     * * @param file Archivo donde guardar
     * @throws ValleyException si ocurre un error durante el guardado
     */
    public void save(File file) throws ValleyException {
        try {
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".dat")) {
                file = new File(filePath + ".dat");
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            throw new ValleyException("Error al guardar el archivo: " + e.getMessage());
        }
    }

    /**
     * Copia del método save (versión 00).
     * * @param file Archivo donde guardar
     * @throws ValleyException indicando construcción
     */
    public void save00(File file) throws ValleyException {
        throw new ValleyException(ValleyException.SAVE_ERROR + ". Archivo " + file.getName());
    }

    /**
     * Importa datos desde un archivo (versión 00).
     * * @param file Archivo a importar
     * @throws ValleyException indicando construcción
     */
    public void importFile00(File file) throws ValleyException {
        throw new ValleyException(ValleyException.IMPORT_ERROR + ". Archivo " + file.getName());
    }
    
    /**
     * Importa el estado del valle desde un archivo de texto plano.
     * El formato esperado no incluye cabecera de tamaño.
     * Reinicia el valle al tamaño estático por defecto.
     * * @param f El archivo de texto a leer.
     * @throws ValleyException Si ocurre un error de lectura o formato.
     */
    public void importFile(File f) throws ValleyException {
        final String IMPORT_ERROR = "Error importar"; 
        
        if (!f.exists() || f.isDirectory()) {
            throw new ValleyException(IMPORT_ERROR);
        }
        try (Scanner s = new Scanner(f)) {
            places = new Unit[SIZE][SIZE];            
            while (s.hasNext()) {
                String t = s.next();
                int r = s.nextInt(), c = s.nextInt();
                Unit u = null;                 
                if (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                    if (t.equals("Humano")) u = new Humano(this, r, c);
                    else if (t.equals("Oveja")) u = new Oveja(this, r, c);
                    else if (t.equals("Hay")) u = new Hay(this, r, c); 
                    else if (t.equals("Hole")) u = new Hole(r, c);                    
                    if (u != null) setUnit(r, c, u);
                }                
                if (u instanceof Animal || (u == null && isAnimalType(t))) {
                    if (s.hasNextInt()) {
                        int energy = s.nextInt();
                        if (u instanceof Animal) ((Animal)u).setEnergy(energy);
                    }
                    if (s.hasNextInt()) s.nextInt(); 
                }
            }
        } catch (Exception e) { throw new ValleyException(IMPORT_ERROR); }
    }
    
    /**
     * Método auxiliar para determinar si un tipo de texto corresponde a un animal.
     * Usado para consumir tokens correctamente si la unidad falló al crearse.
     */
    private boolean isAnimalType(String type) {
        return type.equals("Humano") || type.equals("Oveja") || type.equals("Wolf");
    }

    /**
     * Exporta el estado actual del valle a un archivo de texto plano.
     * No incluye cabecera de tamaño.
     * * @param f Archivo donde exportar
     * @throws ValleyException si ocurre un error de escritura.
     */
    public void export(File f) throws ValleyException {
        final String EXPORT_ERROR = "Error exportar"; 

        if (!f.getName().toLowerCase().endsWith(".txt")) {
            f = new File(f.getAbsolutePath() + ".txt");
        }

        try (PrintWriter p = new PrintWriter(new FileWriter(f))) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    Unit u = places[i][j];
                    if (u != null) {
                        String d = u.getClass().getSimpleName() + " " + i + " " + j;
                        if (u instanceof Animal) {
                            Animal a = (Animal) u;
                            d += " " + a.getEnergy() + " " + a.getDays(); 
                        }
                        p.println(d);
                    }
                }
            }
        } catch (IOException e) { throw new ValleyException(EXPORT_ERROR); }
    }
}
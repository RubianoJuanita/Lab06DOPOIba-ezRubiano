package domain;

/**
 * Excepción personalizada para operaciones del Valle.
 * Se lanza cuando ocurren errores relacionados con operaciones
 * de entrada/salida o acciones no implementadas.
 * 
 * @author IbañezRubiano
 * @version 1.0
 */
public class ValleyException extends Exception {

    public static final String OPEN_ERROR = "Opción open en construcción";
    public static final String SAVE_ERROR = "Opción save en construcción";
    public static final String IMPORT_ERROR = "Opción import en construcción";
    public static final String EXPORT_ERROR = "Opción export en construcción";

    /**
     * Constructor de la excepción con mensaje personalizado.
     * 
     * @param message Mensaje descriptivo del error
     */
    public ValleyException(String message) {
        super(message);
    }
}
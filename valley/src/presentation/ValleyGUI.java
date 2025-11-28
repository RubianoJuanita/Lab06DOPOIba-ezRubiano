package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ValleyGUI extends JFrame {
    public static final int SIDE = 20;

    public final int SIZE;
    private JButton ticTacButton;
    private JPanel controlPanel;
    private PhotoValley photo;
    private Valley theValley;

    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenuItem menuNuevo, menuAbrir, menuGuardarComo;
    private JMenuItem menuImportar, menuExportarComo, menuSalir;

    private ValleyGUI() {
        theValley = new Valley();
        SIZE = theValley.getSize();
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        setTitle("Schelling Valley");
        prepareElementsMenu();
        photo = new PhotoValley(this);
        ticTacButton = new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo, BorderLayout.NORTH);
        add(ticTacButton, BorderLayout.SOUTH);
        setSize(new Dimension(SIDE * SIZE + 15, SIDE * SIZE + 72));
        setResizable(false);
        photo.repaint();
    }

    /**
     * Prepara los elementos del menú de la aplicación.
     * Crea el menú Archivo con las opciones de entrada/salida.
     */
    private void prepareElementsMenu() {
        // Creamos la barra de menú
        menuBar = new JMenuBar();
        // Creamos el menú "Archivo"
        menuArchivo = new JMenu("Menú");
        // Creamos los items del menú
        menuNuevo = new JMenuItem("Nuevo");
        menuAbrir = new JMenuItem("Abrir");
        menuGuardarComo = new JMenuItem("Guardar como");
        menuImportar = new JMenuItem("Importar");
        menuExportarComo = new JMenuItem("Exportar como");
        menuSalir = new JMenuItem("Salir");
        // Se agregan los items al menú con separadores
        menuArchivo.add(menuNuevo);
        menuArchivo.add(menuAbrir);
        menuArchivo.add(menuGuardarComo);
        menuArchivo.addSeparator(); // Primer separador
        menuArchivo.add(menuImportar);
        menuArchivo.add(menuExportarComo);
        menuArchivo.addSeparator(); // Segundo separador
        menuArchivo.add(menuSalir);
        // Agregamos el menú a la barra
        menuBar.add(menuArchivo);
        // Establecemos la barra de menú en la ventana
        setJMenuBar(menuBar);
    }

    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ticTacButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ticTacButtonAction();
                    }
                });
        prepareActionsMenu();

    }

    /**
     * Prepara las acciones (listeners) del menú.
     * Conecta cada opción del menú con su método correspondiente.
     */
    private void prepareActionsMenu() {
        // Acción para Nuevo
        menuNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionNew();
            }
        });

        // Acción Abrir
        menuAbrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionOpen();
            }
        });

        // Acción Guardar como
        menuGuardarComo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionSave();
            }
        });

        // Acción Importar
        menuImportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionImport();
            }
        });

        // Acción Exportar como
        menuExportarComo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionExport();
            }
        });

        // Acción Salir
        menuSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionExit();
            }
        });
    }

    private void ticTacButtonAction() {
        theValley.ticTac();
        photo.repaint();
    }

    public Valley gettheValley() {
        return theValley;
    }

    public static void main(String[] args) {
        ValleyGUI cg = new ValleyGUI();
        cg.setVisible(true);
    }

    // Metodos del controlador

    /**
     * Opción Reinicia el valle.
     */
    private void optionNew() {
        theValley = new Valley();// Creamos un nuevo objeto del dominio, un Valle
        photo.repaint();
        JOptionPane.showMessageDialog(this, "Nuevo valle creado exitosamente",
                "Nuevo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Opción Permite seleccionar y abrir un archivo del valle.
     */
    private void optionOpen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir archivo del valle");

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                theValley.open(selectedFile);
                photo.repaint();
                JOptionPane.showMessageDialog(this, "Archivo abierto exitosamente",
                        "Abrir", JOptionPane.INFORMATION_MESSAGE);
            } catch (ValleyException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Opción Guardar como: Permite guardar el valle en un archivo.
     */
    private void optionSave() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar valle como");

        // Filtro para archivos .dat
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos de Valle (*.dat)", "dat");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                theValley.save(selectedFile);
                JOptionPane.showMessageDialog(this,
                        "Valle guardado exitosamente en: " + selectedFile.getName(),
                        "Guardar",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (ValleyException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error al guardar",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Opción Importar: Permite importar datos desde un archivo.
     */
    private void optionImport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Importar archivo");

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                theValley.importFile(selectedFile);
                photo.repaint();
                JOptionPane.showMessageDialog(this, "Archivo importado exitosamente",
                        "Importar", JOptionPane.INFORMATION_MESSAGE);
            } catch (ValleyException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Opción Exportar como: Permite exportar datos a un archivo.
     */
    private void optionExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar como");

        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                theValley.export(selectedFile);
                JOptionPane.showMessageDialog(this, "Archivo exportado exitosamente",
                        "Exportar", JOptionPane.INFORMATION_MESSAGE);
            } catch (ValleyException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Opción Salir: Cierra la aplicación.
     */
    private void optionExit() {
        System.exit(0);
    }

}

class PhotoValley extends JPanel {
    private ValleyGUI gui;

    public PhotoValley(ValleyGUI gui) {
        this.gui = gui;
        setBackground(Color.white);
        setPreferredSize(new Dimension(gui.SIDE * gui.SIZE + 10, gui.SIDE * gui.SIZE + 10));
    }

    public void paintComponent(Graphics g) {
        Valley theValley = gui.gettheValley();
        super.paintComponent(g);

        for (int c = 0; c <= theValley.getSize(); c++) {
            g.drawLine(c * gui.SIDE, 0, c * gui.SIDE, theValley.getSize() * gui.SIDE);
        }
        for (int f = 0; f <= theValley.getSize(); f++) {
            g.drawLine(0, f * gui.SIDE, theValley.getSize() * gui.SIDE, f * gui.SIDE);
        }
        for (int f = 0; f < theValley.getSize(); f++) {
            for (int c = 0; c < theValley.getSize(); c++) {
                if (theValley.getUnit(f, c) != null) {
                    g.setColor(theValley.getUnit(f, c).getColor());
                    if (theValley.getUnit(f, c).shape() == Unit.SQUARE) {
                        g.fillRoundRect(gui.SIDE * c + 1, gui.SIDE * f + 1, gui.SIDE - 2, gui.SIDE - 2, 2, 2);
                    } else {
                        g.fillOval(gui.SIDE * c + 1, gui.SIDE * f + 1, gui.SIDE - 2, gui.SIDE - 2);
                    }
                    if (theValley.getUnit(f, c).isAnimal()) {
                        g.setColor(Color.red);
                        if (((Animal) theValley.getUnit(f, c)).getEnergy() >= 50) {
                            g.drawString("u", gui.SIDE * c + 6, gui.SIDE * f + 15);
                        } else {
                            g.drawString("~", gui.SIDE * c + 6, gui.SIDE * f + 17);
                        }
                    }
                }
            }
        }
    }

}
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Stack;

public class TextEditor extends JFrame implements ActionListener {

    // Text Area for editing
    private JTextPane textPane;
    private UndoManager undoManager;
    private JPopupMenu popupMenu;

    // Toolbars
    private JToolBar editToolBar;
    private JToolBar formatToolBar;

    // Buttons for Toolbars
    private JButton newButton, openButton, saveButton, printButton;
    private JButton cutButton, copyButton, pasteButton, undoButton, redoButton;
    private JButton boldButton, italicButton, underlineButton;

    // File Chooser for open/save
    private JFileChooser fileChooser;

    // মেনু বার
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, formatMenu, helpMenu;
    private JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, printMenuItem, exitMenuItem;
    private JMenuItem cutMenuItem, copyMenuItem, pasteMenuItem, deleteMenuItem, undoMenuItem, redoMenuItem, selectAllMenuItem;
    private JMenuItem boldMenuItem, italicMenuItem, underlineMenuItem;
    private JMenuItem aboutMenuItem;

    // বোতাম আইকনগুলির জন্য পাথ
    private final String imagePath = "icons/"; //Ensure you have an icons folder with the below images.

    public TextEditor() {
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Initialize components
        textPane = new JTextPane();
        undoManager = new UndoManager();
        textPane.getDocument().addUndoableEditListener(undoManager);
        popupMenu = createPopupMenu();

        // Set the text area for right-click popup
        textPane.setComponentPopupMenu(popupMenu);

        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("HTML Files (*.html, *.htm)", "html", "htm"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("All Files (*.*)", "*")); //Added this

        createMenuBar();
        createToolBars(); //create the toolbars
        createStatusBar();

        // Add components to the frame
        add(textPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        // File Menu
        fileMenu = new JMenu("File");
        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open...");
        saveMenuItem = new JMenuItem("Save");
        saveAsMenuItem = new JMenuItem("Save As...");
        printMenuItem = new JMenuItem("Print...");
        exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(printMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        // Edit Menu
        editMenu = new JMenu("Edit");
        cutMenuItem = new JMenuItem("Cut");
        copyMenuItem = new JMenuItem("Copy");
        pasteMenuItem = new JMenuItem("Paste");
        deleteMenuItem = new JMenuItem("Delete");
        undoMenuItem = new JMenuItem("Undo");
        redoMenuItem = new JMenuItem("Redo");
        selectAllMenuItem = new JMenuItem("Select All");

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(deleteMenuItem);
        editMenu.addSeparator();
        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);
        editMenu.addSeparator();
        editMenu.add(selectAllMenuItem);

        // Format Menu
        formatMenu = new JMenu("Format");
        boldMenuItem = new JMenuItem("Bold");
        italicMenuItem = new JMenuItem("Italic");
        underlineMenuItem = new JMenuItem("Underline");

        formatMenu.add(boldMenuItem);
        formatMenu.add(italicMenuItem);
        formatMenu.add(underlineMenuItem);

        // Help Menu
        helpMenu = new JMenu("Help");
        aboutMenuItem = new JMenuItem("About Text Editor");
        helpMenu.add(aboutMenuItem);

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Add action listeners to menu items
        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        saveAsMenuItem.addActionListener(this);
        printMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);
        deleteMenuItem.addActionListener(this);
        undoMenuItem.addActionListener(this);
        redoMenuItem.addActionListener(this);
        selectAllMenuItem.addActionListener(this);
        boldMenuItem.addActionListener(this);
        italicMenuItem.addActionListener(this);
        underlineMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);
    }

    private void createToolBars() {
        // Edit ToolBar
        editToolBar = new JToolBar("Edit");
        newButton = createButton("New", "new.png", "Create a new document");
        openButton = createButton("Open", "open.png", "Open an existing document");
        saveButton = createButton("Save", "save.png", "Save the current document");
        printButton = createButton("Print", "print.png", "Print the current document");
        cutButton = createButton("Cut", "cut.png", "Cut the selected text");
        copyButton = createButton("Copy", "copy.png", "Copy the selected text");
        pasteButton = createButton("Paste", "paste.png", "Paste the text from clipboard");
        undoButton = createButton("Undo", "undo.png", "Undo the last action");
        redoButton = createButton("Redo", "redo.png", "Redo the last undone action");

        editToolBar.add(newButton);
        editToolBar.add(openButton);
        editToolBar.add(saveButton);
        editToolBar.add(printButton);
        editToolBar.addSeparator();
        editToolBar.add(cutButton);
        editToolBar.add(copyButton);
        editToolBar.add(pasteButton);
        editToolBar.addSeparator();
        editToolBar.add(undoButton);
        editToolBar.add(redoButton);

        // Format ToolBar
        formatToolBar = new JToolBar("Format");
        boldButton = createButton("Bold", "bold.png", "Bold");
        italicButton = createButton("Italic", "italic.png", "Italic");
        underlineButton = createButton("Underline", "underline.png", "Underline");

        formatToolBar.add(boldButton);
        formatToolBar.add(italicButton);
        formatToolBar.add(underlineButton);

        // Add toolbars to the frame
        add(editToolBar, BorderLayout.NORTH);
        add(formatToolBar, BorderLayout.SOUTH); // Added formatToolBar
        editToolBar.setFloatable(false); //Added this to fix the toolbar floating issue
        formatToolBar.setFloatable(false);
    }

    private JButton createButton(String actionCommand, String iconName, String toolTipText) {
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        // Load the icon
        try {
            // ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath + iconName)); //This line caused the program to crash
            ImageIcon imageIcon = new ImageIcon(imagePath + iconName);
            button.setIcon(imageIcon);
        } catch (Exception e) {
            System.err.println("Error loading icon: " + iconName + " - " + e.getMessage());
            // Handle the error, e.g., use a default icon or disable the button
        }
        return button;
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem selectAll = new JMenuItem("Select All");

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        delete.addActionListener(this);
        selectAll.addActionListener(this);

        popupMenu.add(cut);
        popupMenu.add(copy);
        popupMenu.add(paste);
        popupMenu.add(delete);
        popupMenu.addSeparator();
        popupMenu.add(selectAll);

        return popupMenu;
    }
    private void createStatusBar() {
        //create status bar
        JPanel statusBar = new JPanel();
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Ready");
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }

    private void newFile() {
        textPane.setText("");
        undoManager.discardAllEdits();
        setTitle("Text Editor - Untitled");
    }

    private void openFile() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileReader reader = new FileReader(file);
                textPane.read(reader, null);
                reader.close();
                setTitle("Text Editor - " + file.getName());
                undoManager.discardAllEdits();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        if (fileChooser.getSelectedFile() != null) {
            saveFileAs(fileChooser.getSelectedFile());
        } else {
            saveAsFile();
        }
    }

    private void saveAsFile() {
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
             if (!file.getName().contains(".")) {
                file = new File(file.getAbsolutePath() + ".txt");
             }
            saveFileAs(file);
        }
    }

    private void saveFileAs(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            textPane.write(writer);
            writer.close();
            setTitle("Text Editor - " + file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printFile() {
        try {
            textPane.print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error printing document: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cutText() {
        textPane.cut();
    }

    private void copyText() {
        textPane.copy();
    }

    private void pasteText() {
        textPane.paste();
    }

    private void deleteText() {
        textPane.replaceSelection("");
    }

    private void undoText() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    private void redoText() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }

    private void selectAllText() {
        textPane.selectAll();
    }

    private void setBold() {
        MutableAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, !StyleConstants.isBold(textPane.getCharacterAttributes()));
        textPane.setCharacterAttributes(attributes, false);
    }

    private void setItalic() {
        MutableAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setItalic(attributes, !StyleConstants.isItalic(textPane.getCharacterAttributes()));
        textPane.setCharacterAttributes(attributes, false);
    }

    private void setUnderline() {
        MutableAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setUnderline(attributes, !StyleConstants.isUnderline(textPane.getCharacterAttributes()));
        textPane.setCharacterAttributes(attributes, false);
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this, "Text Editor\nVersion 1.0\nDeveloped by [Your Name]", "About Text Editor", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exitApp() {
        System.exit(0);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":         newFile(); break;
            case "Open":        openFile(); break;
            case "Save":        saveFile(); break;
            case "Save As...":  saveAsFile(); break;
            case "Print...":    printFile(); break;
            case "Exit":        exitApp(); break;

            case "Cut":         cutText(); break;
            case "Copy":        copyText(); break;
            case "Paste":       pasteText(); break;
            case "Delete":      deleteText(); break;
            case "Undo":        undoText(); break;
            case "Redo":        redoText(); break;
            case "Select All":  selectAllText(); break;

            case "Bold":        setBold(); break;
            case "Italic":      setItalic(); break;
            case "Underline":   setUnderline(); break;

            case "About Text Editor": showAbout(); break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::new);
    }
}


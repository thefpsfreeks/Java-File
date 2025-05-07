import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math; 

public class ScientificCalculator extends JFrame implements ActionListener {

    private JTextField display;
    private String currentInput = "";
    private double firstOperand = 0;
    private String operator = "";
    private boolean decimalPointUsed = false;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5)); // Example layout for basic buttons

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "CE"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // You'll create another panel for scientific buttons and add it to the frame
        JPanel scientificPanel = new JPanel();
        scientificPanel.setLayout(new GridLayout(4, 3, 5, 5)); // Example layout
        String[] scientificLabels = {"sin", "cos", "tan", "log", "ln", "^", "sqrt", "!", "pi", "e", "(", ")"};
        for (String label : scientificLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            scientificPanel.add(button);
        }
        add(scientificPanel, BorderLayout.WEST); // Or another suitable location

        setSize(400, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
                currentInput += command;
                display.setText(currentInput);
                break;
            case ".":
                if (!decimalPointUsed) {
                    currentInput += ".";
                    display.setText(currentInput);
                    decimalPointUsed = true;
                }
                break;
            case "+": case "-": case "*": case "/":
                if (!currentInput.isEmpty()) {
                    firstOperand = Double.parseDouble(currentInput);
                    operator = command;
                    currentInput = "";
                    decimalPointUsed = false;
                }
                break;
            case "=":
                if (!currentInput.isEmpty() && !operator.isEmpty()) {
                    double secondOperand = Double.parseDouble(currentInput);
                    double result = 0;
                    switch (operator) {
                        case "+": result = firstOperand + secondOperand; break;
                        case "-": result = firstOperand - secondOperand; break;
                        case "*": result = firstOperand * secondOperand; break;
                        case "/":
                            if (secondOperand != 0) {
                                result = firstOperand / secondOperand;
                            } else {
                                display.setText("Error: Division by zero");
                                return;
                            }
                            break;
                    }
                    display.setText(String.valueOf(result));
                    currentInput = String.valueOf(result);
                    operator = "";
                    decimalPointUsed = display.getText().contains(".");
                }
                break;
            case "C":
                currentInput = "";
                display.setText("0");
                firstOperand = 0;
                operator = "";
                decimalPointUsed = false;
                break;
            case "CE":
                currentInput = "";
                display.setText("0");
                decimalPointUsed = false;
                break;
            case "sin":
                if (!currentInput.isEmpty()) {
                    double value = Math.sin(Math.toRadians(Double.parseDouble(currentInput))); // Assuming degrees for now
                    display.setText(String.valueOf(value));
                    currentInput = String.valueOf(value);
                }
                break;
            // Implement other scientific functions similarly using Math class methods
            case "cos":
                if (!currentInput.isEmpty()) {
                    double value = Math.cos(Math.toRadians(Double.parseDouble(currentInput)));
                    display.setText(String.valueOf(value));
                    currentInput = String.valueOf(value);
                }
                break;
            case "tan":
                if (!currentInput.isEmpty()) {
                    double value = Math.tan(Math.toRadians(Double.parseDouble(currentInput)));
                    display.setText(String.valueOf(value));
                    currentInput = String.valueOf(value);
                }
                break;
            case "log":
                if (!currentInput.isEmpty()) {
                    double value = Math.log10(Double.parseDouble(currentInput));
                    display.setText(String.valueOf(value));
                    currentInput = String.valueOf(value);
                }
                break;
            case "ln":
                if (!currentInput.isEmpty()) {
                    double value = Math.log(Double.parseDouble(currentInput));
                    display.setText(String.valueOf(value));
                    currentInput = String.valueOf(value);
                }
                break;
            case "^":
                if (!currentInput.isEmpty()) {
                    firstOperand = Double.parseDouble(currentInput);
                    operator = "^";
                    currentInput = "";
                    decimalPointUsed = false;
                }
                break;
            case "sqrt":
                if (!currentInput.isEmpty()) {
                    double value = Math.sqrt(Double.parseDouble(currentInput));
                    display.setText(String.valueOf(value));
                    currentInput = String.valueOf(value);
                }
                break;
            case "!":
                if (!currentInput.isEmpty()) {
                    try {
                        int n = Integer.parseInt(currentInput);
                        if (n < 0) {
                            display.setText("Error: Invalid input");
                            currentInput = "";
                        } else {
                            long factorial = 1;
                            for (int i = 1; i <= n; i++) {
                                factorial *= i;
                            }
                            display.setText(String.valueOf(factorial));
                            currentInput = String.valueOf(factorial);
                        }
                    } catch (NumberFormatException ex) {
                        display.setText("Error: Invalid input");
                        currentInput = "";
                    }
                }
                break;
            case "pi":
                display.setText(String.valueOf(Math.PI));
                currentInput = String.valueOf(Math.PI);
                decimalPointUsed = true;
                break;
            case "e":
                display.setText(String.valueOf(Math.E));
                currentInput = String.valueOf(Math.E);
                decimalPointUsed = true;
                break;
            // Implement parentheses logic (this will be more involved)
            case "(":
            case ")":
                // Handle parentheses logic - might require a different approach for evaluation
                currentInput += command;
                display.setText(currentInput);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScientificCalculator::new);
    }
}
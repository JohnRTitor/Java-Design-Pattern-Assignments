package pizza;

import javax.swing.*;
import java.awt.*;

public class PizzaStoreGui extends JFrame {

    private JComboBox<String> typeBox;
    private JComboBox<String> styleBox;
    private JTextArea outputArea;

    public PizzaStoreGui() {
        setTitle("Pizza Store");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Top: input panel ──────────────────────────────────────────────────
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Pizza Type:"));
        typeBox = new JComboBox<>(new String[]{"chicken", "paneer", "corn"});
        inputPanel.add(typeBox);

        inputPanel.add(new JLabel("Pizza Style:"));
        styleBox = new JComboBox<>(new String[]{"American", "NorthIndian", "SouthIndian"});
        inputPanel.add(styleBox);

        JButton orderBtn = new JButton("Order Pizza");
        JButton clearBtn = new JButton("Clear");
        inputPanel.add(orderBtn);
        inputPanel.add(clearBtn);

        add(inputPanel, BorderLayout.NORTH);

        // ── Center: output area ───────────────────────────────────────────────
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // ── Actions ───────────────────────────────────────────────────────────
        orderBtn.addActionListener(e -> placeOrder());
        clearBtn.addActionListener(e -> outputArea.setText(""));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void placeOrder() {
        String type  = (String) typeBox.getSelectedItem();
        String style = (String) styleBox.getSelectedItem();

        // Capture steps manually since we can't redirect System.out in Swing
        Pizza pizza = null;
        switch (type) {
            case "chicken": pizza = new ChickenPizza(style); break;
            case "paneer":  pizza = new PaneerPizza(style);  break;
            case "corn":    pizza = new CornPizza(style);    break;
        }

        if (pizza == null) return;

        outputArea.append("--- New Order ---\n");
        outputArea.append("Preparing " + pizza.getName() + "\n");
        outputArea.append("Baking "    + pizza.getName() + "\n");
        outputArea.append("Cutting "   + pizza.getName() + "\n");
        outputArea.append("Boxing "    + pizza.getName() + "\n");
        outputArea.append(pizza.getName() + " is ready!\n\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PizzaStoreGui::new);
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainPageView {
    private JFrame jf;
    private JPanel jp;
    private JTextField productName, productQuantity, productPrice, productBrand, productExpiry;
    private JTextArea listTextArea;
    private JButton addToListButton, orderButton, historyButton;

    
    private SkincareItem[] listItems = new SkincareItem[100];
    private int itemCount = 0;
    private double totalCost = 0.0;

    public MainPageView() {
        jf = new JFrame("Skincare Product List");
        jf.setSize(800, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jp = new JPanel();
        jp.setLayout(null);

        JLabel label;

        
        label = new JLabel("Product name:");
        label.setBounds(50, 50, 150, 20);
        jp.add(label);

        productName = new JTextField();
        productName.setBounds(160, 50, 150, 30);
        jp.add(productName);

        
        label = new JLabel("Product quantity:");
        label.setBounds(50, 100, 150, 20);
        jp.add(label);

        productQuantity = new JTextField();
        productQuantity.setBounds(160, 100, 150, 30);
        jp.add(productQuantity);

        
        label = new JLabel("Price per Product:");
        label.setBounds(50, 150, 150, 20);
        jp.add(label);

        productPrice = new JTextField();
        productPrice.setBounds(160, 150, 150, 30);
        jp.add(productPrice);

        
        label = new JLabel("Brand:");
        label.setBounds(50, 200, 150, 20);
        jp.add(label);

        productBrand = new JTextField();
        productBrand.setBounds(160, 200, 150, 30);
        jp.add(productBrand);

        
        label = new JLabel("Expiry date:");
        label.setBounds(50, 250, 150, 20);
        jp.add(label);

        productExpiry = new JTextField();
        productExpiry.setBounds(160, 250, 150, 30);
        jp.add(productExpiry);

        
        label = new JLabel("Product List:");
        label.setBounds(560, 20, 80, 20);
        jp.add(label);

        listTextArea = new JTextArea();
        listTextArea.setBounds(450, 50, 300, 350);
        listTextArea.setEditable(false);
        jp.add(listTextArea);

        
        addToListButton = new JButton("Add to List");
        addToListButton.setBounds(50, 350, 110, 30);
        jp.add(addToListButton);

        orderButton = new JButton("Order now");
        orderButton.setBounds(170, 350, 110, 30);
        jp.add(orderButton);

        historyButton = new JButton("Order history");
        historyButton.setBounds(290, 350, 130, 30);
        jp.add(historyButton);

        
        addToListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = productName.getText().trim();
                String quantityStr = productQuantity.getText().trim();
                String priceStr = productPrice.getText().trim();
                String brand = productBrand.getText().trim();
                String expiry = productExpiry.getText().trim();

                if (name.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
                    JOptionPane.showMessageDialog(jf,
                            "Please enter name, quantity and price.",
                            "Blank",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int quantity = Integer.parseInt(quantityStr);
                    double price = Double.parseDouble(priceStr);

                    
                    if (itemCount >= listItems.length) {
                        JOptionPane.showMessageDialog(jf,
                                "List is full",
                                "List Full",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    SkincareItem item = new SkincareItem(name, quantity, price, brand, expiry);
                    listItems[itemCount] = item;
                    itemCount++;
                    totalCost += item.getCost();

                    
                    String listText = "";
                    for (int i = 0; i < itemCount; i++) {
                        listText = listText + listItems[i].display() + "\n";
                    }
                    listText = listText + "\nTotal: " + totalCost + " tk";
                    listTextArea.setText(listText);

                    
                    productName.setText("");
                    productQuantity.setText("");
                    productPrice.setText("");
                    productBrand.setText("");
                    productExpiry.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(jf,
                            "Invalid input",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (itemCount == 0) {
                    JOptionPane.showMessageDialog(jf,
                            "List is empty, please add items",
                            "Empty List",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    OrderRepository.saveOrder(listItems, itemCount, totalCost);
                    JOptionPane.showMessageDialog(jf,
                            "Order confirm!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    
                    for (int i = 0; i < itemCount; i++) {
                        listItems[i] = null;
                    }
                    itemCount = 0;
                    totalCost = 0.0;
                    listTextArea.setText("");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(jf,
                            "Failed to save order.",
                            "I/O Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf.setVisible(false);
                new OrderHistoryView(jf);
            }
        });

        jf.add(jp);
    }

    
    public JFrame getFrame() {
        return jf;
    }
}
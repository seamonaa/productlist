import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OrderHistoryView {
    private JFrame jf;
    private JPanel jp;
    private JButton backButton, deleteButton;
    private JTextArea productHistoryArea;

  
    public OrderHistoryView(final JFrame mainFrame) {
        jf = new JFrame("Order History");
        jf.setSize(800, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jp = new JPanel();
        jp.setLayout(null);

        
        productHistoryArea = new JTextArea();
        productHistoryArea.setBounds(50, 50, 680, 300);
        productHistoryArea.setEditable(false);
        jp.add(productHistoryArea);

        
        backButton = new JButton("Back to main");
        backButton.setBounds(240, 370, 150, 30);
        jp.add(backButton);

        
        deleteButton = new JButton("Delete History");
        deleteButton.setBounds(410, 370, 150, 30);
        jp.add(deleteButton);

        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf.setVisible(false);
                mainFrame.setVisible(true);
            }
        });

        
        deleteButton.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        jf,
                        "Delete order history ?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        OrderRepository.deleteOrderHistory();
                        productHistoryArea.setText("Order history deleted");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(jf,
                                "Failed to delete order history.",
                                "I/O Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        
        String historyText = OrderRepository.loadOrderHistory();
        productHistoryArea.setText(historyText);

        jf.add(jp);
        jf.setVisible(true);
    }
}
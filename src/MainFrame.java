import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author micro
 */
public class MainFrame {

    private Connection connection;

    public MainFrame() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(),
                    "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     */
    @SuppressWarnings("deprecation")
    public void show() {

        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 100);

        // Centralizando o JFrame e deslocando um pouco para a esquerda
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2 - 10; // Deslocamento de 100 pixels para a esquerda
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JButton cadastrarButton = new JButton("Cadastrar Produto");
        cadastrarButton.setBackground(new Color(0, 102, 204)); // Azul Claro
        cadastrarButton.setForeground(Color.WHITE);

        JButton consultarButton = new JButton("Consultar Produto");
        consultarButton.setBackground(new Color(51, 153, 102)); // Verde
        consultarButton.setForeground(Color.WHITE);

        cadastrarButton.addActionListener((ActionEvent e) -> {
            new ProductRegistrationFrame(connection).show();
        });

        consultarButton.addActionListener((ActionEvent e) -> {
            new ProductSearchFrame(connection).show();
        });

        panel.add(cadastrarButton);
        panel.add(consultarButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame().show();
    }
}

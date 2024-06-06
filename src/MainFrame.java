import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class MainFrame {

    private Connection connection;
    private ProductRegistrationFrame registrationFrame;
    private ProductSearchFrame searchFrame;
    private boolean registrationFrameOpen = false;

    public MainFrame() {
        try {
            connection = DatabaseConfig.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(),
                    "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("deprecation")
    public void show() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 600);
        frame.setResizable(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setColor(new Color(0, 153, 204));
                g.fillRect(0, 0, 50, getHeight());
                g.fillRect(getWidth() - 50, 0, 50, getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Sistema de Alocação Autônomo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Corbel", Font.PLAIN, 28));
        titleLabel.setForeground(new Color(30, 30, 30));

        // Carregar a imagem usando o ClassLoader
        ImageIcon logoIcon = loadIcon("resources/analytics.gif");
        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        ImageIcon registerIcon = loadIcon("resources/register.png");
        ImageIcon searchIcon = loadIcon("resources/find.png");

        JButton registrarButton = new JButton("Registrar Produto", registerIcon);
        registrarButton.setPreferredSize(new Dimension(180, 50));
        registrarButton.setBackground(new Color(0, 153, 204));
        registrarButton.setForeground(Color.WHITE);
        registrarButton.setFocusPainted(false);
        registrarButton.setToolTipText("Clique para registrar um novo produto");
        registrarButton.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 204), 2, true));
        registrarButton.setMargin(new Insets(5, 15, 5, 15));

        JButton consultarButton = new JButton("Consultar Produto", searchIcon);
        consultarButton.setPreferredSize(new Dimension(180, 50));
        consultarButton.setBackground(new Color(0, 153, 102));
        consultarButton.setForeground(Color.WHITE);
        consultarButton.setFocusPainted(false);
        consultarButton.setToolTipText("Clique para consultar um produto existente");
        consultarButton.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 102), 2, true));
        consultarButton.setMargin(new Insets(5, 15, 5, 15));

        addIconHoverEffects(registrarButton, registerIcon);
        addIconHoverEffects(consultarButton, searchIcon);

        registrarButton.addActionListener((ActionEvent e) -> {
            if (!registrationFrameOpen) {
                registrationFrameOpen = true;
                if (registrationFrame == null) {
                    registrationFrame = new ProductRegistrationFrame(connection);
                    registrationFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            registrationFrameOpen = false;
                        }
                    });
                }
                registrationFrame.show();
            }
        });

        consultarButton.addActionListener((ActionEvent e) -> {
            if (searchFrame == null) {
                searchFrame = new ProductSearchFrame(connection);
            }
            searchFrame.setVisible(true);
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        buttonPanel.add(registrarButton, gbc);
        gbc.gridx = 1;
        buttonPanel.add(consultarButton, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        panel.add(logoLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(5, 50, 5, 50);
        panel.add(buttonPanel, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    private ImageIcon loadIcon(String path) {
        URL imgUrl = getClass().getClassLoader().getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("Image not found: " + path);
            return null;
        }
    }

    private void addIconHoverEffects(JButton button, ImageIcon icon) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon newIcon = scaleIcon(icon, 1.08); // aumenta o tamanho do ícone
                button.setIcon(newIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(icon); // restaura o tamanho original do ícone
            }
        });
    }

    private ImageIcon scaleIcon(ImageIcon icon, double scaleFactor) {
        int width = (int) (icon.getIconWidth() * scaleFactor);
        int height = (int) (icon.getIconHeight() * scaleFactor);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        new MainFrame().show();
    }
}

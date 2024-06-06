import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class ProductSearchFrame extends JFrame {

    private final Connection connection;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public ProductSearchFrame(Connection connection) {
        this.connection = connection;
        initializeUI();
        refreshTable();
        centerFrame();
    }

    private void centerFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2 + 500; // Deslocamento de 500 pixels para a direita
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private void initializeUI() {
        setTitle("Consulta de Cassetes");
        setSize(800, 600); // Aumenta o tamanho da janela para acomodar mais colunas
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        Border searchBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        searchPanel.setBorder(searchBorder);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.addActionListener(e -> search());
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Pesquisar");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.addActionListener(e -> search());
        searchPanel.add(searchButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class; // Define a classe das colunas como String
            }
        };
        tableModel.addColumn("Locação");
        tableModel.addColumn("Nível");
        // Aplicar renderizador de cor à coluna "Nível"
        tableModel.addColumn("Item");

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    int modelRow = table.convertRowIndexToModel(row); // Converte para o índice do modelo
                    String cassete = (String) tableModel.getValueAt(modelRow, 0);
                    showCasseteDetails(cassete);
                }
            }
        });
        

        // Define o comparador personalizado para a coluna "Locação"
        sorter.setComparator(0, (String s1, String s2) -> {
            try {
                String[] parts1 = s1.split(" ");
                String[] parts2 = s2.split(" ");
                if (parts1.length > 1 && parts2.length > 1) {
                    return Integer.compare(Integer.parseInt(parts1[1]), Integer.parseInt(parts2[1]));
                }
            } catch (NumberFormatException e) {
                // Handle exception if the parsing fails
                e.printStackTrace();
            }
            return s1.compareTo(s2); // Fallback to lexicographic comparison
        });
    }

    private void showCasseteDetails(String cassete) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM produtos WHERE alocacao = ?")) {
            stmt.setString(1, cassete);

            ResultSet rs = stmt.executeQuery();
            StringBuilder details = new StringBuilder();
            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String bitola = rs.getString("bitola");
                String comprimento = rs.getString("comprimento");
                String cor = rs.getString("cor");
                details.append("<b>Produto:</b> ").append(codigo).append("<br>")
                       .append("<b>Bitola:</b> ").append(bitola).append("<br>")
                       .append("<b>Comprimento:</b> ").append(comprimento).append("<br>")
                       .append("<b>Cor:</b> ").append(cor).append("<br><br>");
            }
            if (details.length() == 0) {
                JOptionPane.showMessageDialog(this, "<html><b>Nenhum produto encontrado no " + cassete + "</b></html>",
                        "Detalhes do Cassete", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "<html><body style='width: 200px'>" +
                        "<b>Conteúdo do " + cassete + ":</b><br>" + details.toString() +
                        "</body></html>", "Detalhes do Cassete", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar produtos no cassete " + cassete + ": " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT l.ID, l.nivel, l.pn, p.codigo, p.bitola, p.comprimento, p.cor " +
                            "FROM locacoes l LEFT JOIN produtos p ON l.ID = p.alocacao " +
                            "WHERE l.ID LIKE ? OR p.codigo LIKE ? OR p.bitola LIKE ? OR p.comprimento LIKE ? OR p.cor LIKE ?")) {
                String wildcardTerm = "%" + searchTerm + "%";
                stmt.setString(1, wildcardTerm);
                stmt.setString(2, wildcardTerm);
                stmt.setString(3, wildcardTerm);
                stmt.setString(4, wildcardTerm);
                stmt.setString(5, wildcardTerm);

                ResultSet rs = stmt.executeQuery();
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(this, "Nenhum resultado encontrado para o termo de pesquisa.",
                            "Pesquisa", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    tableModel.setRowCount(0); // Limpa a tabela antes de adicionar os novos dados
                    while (rs.next()) {
                        String cassete = rs.getString("ID");
                        String nivel = rs.getString("nivel");
                        String estado = rs.getString("pn");
                        tableModel.addRow(new Object[] { cassete, nivel, estado });
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao realizar a pesquisa: " + e.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            refreshTable();
        }
    }

    private void refreshTable() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM locacoes")) {

            tableModel.setRowCount(0); // Limpa a tabela antes de adicionar os novos dados
            while (rs.next()) {
                String cassete = rs.getString("ID");
                String nivel = rs.getString("nivel");
                String estado = rs.getString("pn");
                tableModel.addRow(new Object[] { cassete, nivel, estado });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar a tabela: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            // Conectar ao banco de dados
            Connection connection = DatabaseConfig.getConnection();

            // Definir o visual do programa para parecer com o sistema operacional padrão
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Criar e exibir a janela de consulta
            SwingUtilities.invokeLater(() -> {
                ProductSearchFrame searchFrame = new ProductSearchFrame(connection);
                searchFrame.setVisible(true);
            });
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException
                | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(),
                    "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }
}

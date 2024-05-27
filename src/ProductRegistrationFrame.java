import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProductRegistrationFrame {

    private final Connection connection;
    private JTextField codigoField;
    private JComboBox<Double> bitolaComboBox;
    private JTextField comprimentoField;
    private JComboBox<String> corComboBox;
    private JComboBox<String> alocacaoComboBox;

    public ProductRegistrationFrame(Connection connection) {
        this.connection = connection;
    }

    public void show() {
        JFrame frame = new JFrame("Cadastro de Produto");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Centralizando o JFrame e deslocando um pouco para a esquerda
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2 - 400; // Deslocamento de 100 pixels para a esquerda
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new GridLayout(7, 2));
        frame.setContentPane(contentPane);

        JLabel codigoLabel = new JLabel("Código do Produto:");
        codigoField = new JTextField();
        codigoField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        contentPane.add(codigoLabel);
        contentPane.add(codigoField);

        JLabel bitolaLabel = new JLabel("Bitola:");
        bitolaComboBox = new JComboBox<>(new Double[] { 0.35, 0.50, 1.00, 1.50 });
        bitolaComboBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        contentPane.add(bitolaLabel);
        contentPane.add(bitolaComboBox);

        JLabel comprimentoLabel = new JLabel("Comprimento (mm):");
        comprimentoField = new JTextField();
        comprimentoField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        contentPane.add(comprimentoLabel);
        contentPane.add(comprimentoField);

        JLabel corLabel = new JLabel("Cor:");
        corComboBox = new JComboBox<>(new String[] { "Vermelho", "Verde", "Azul", "Amarelo", "Branco", "Preto" });
        corComboBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        contentPane.add(corLabel);
        contentPane.add(corComboBox);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.setBackground(new Color(0, 102, 204)); // Azul Claro
        buscarButton.setForeground(Color.WHITE);
        contentPane.add(new JLabel()); // Placeholder
        contentPane.add(buscarButton);

        JLabel alocacaoLabel = new JLabel("Alocação:");
        alocacaoComboBox = new JComboBox<>();
        alocacaoComboBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        contentPane.add(alocacaoLabel);
        contentPane.add(alocacaoComboBox);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBackground(new Color(51, 153, 102)); // Verde
        cadastrarButton.setForeground(Color.WHITE);
        contentPane.add(new JLabel()); // Placeholder
        contentPane.add(cadastrarButton);

        buscarButton.addActionListener((ActionEvent e) -> {
            buscarAlocacao();
        });

        cadastrarButton.addActionListener((ActionEvent e) -> {
            cadastrarProduto();
        });

        frame.setVisible(true);
    }

    private void buscarAlocacao() {
        try {
            Double bitola = (Double) bitolaComboBox.getSelectedItem();
            String comprimentoText = comprimentoField.getText();

            if (comprimentoText.isEmpty()) {
                exibirAlerta("Campo vazio", "Por favor, preencha o campo de comprimento antes de buscar a alocação.");
                return;
            }

            Double comprimento = Double.valueOf(comprimentoText);

            if (bitola != null) {
                String nivel = calcularNivel(bitola, comprimento);

                if (nivel != null) {
                    alocacaoComboBox.removeAllItems();
                    PreparedStatement stmt = connection.prepareStatement(
                            "SELECT ID FROM locacoes WHERE nivel = ? AND pn = 'Vazio'");
                    stmt.setString(1, nivel);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        alocacaoComboBox.addItem(rs.getString("ID"));
                    }
                    if (alocacaoComboBox.getItemCount() == 0) {
                        exibirAlerta("Nenhum Cassete Disponível",
                                "Não há cassetes disponíveis para o nível calculado.");
                    }
                } else {
                    exibirAlerta("Nível não encontrado", "Não foi possível encontrar o nível de alocação.");
                }
            } else {
                exibirAlerta("Bitola não selecionada", "Por favor, selecione uma bitola antes de buscar a alocação.");
            }
        } catch (NumberFormatException e) {
            exibirAlerta("Erro de Formato", "O Campo Comprimento deve ser um número válido.");
        } catch (SQLException e) {
            exibirAlerta("Erro de Banco de Dados", "Erro ao buscar cassetes: " + e.getMessage());
        }
    }

    private String calcularNivel(Double bitola, Double comprimento) {
        String nivel = null;

        if (bitola != null) {
            System.out.println("Bitola: " + bitola);
            System.out.println("Comprimento: " + comprimento);

            // Comparação das bitolas utilizando um pequeno intervalo de tolerância
            if (Math.abs(bitola - 0.35) < 0.0001) {
                if (comprimento <= 800) {
                    nivel = "D";
                } else if (comprimento <= 1500) {
                    nivel = "C";
                } else if (comprimento <= 2000) {
                    nivel = "B";
                } else {
                    nivel = "A";
                }
            } else if (Math.abs(bitola - 0.50) < 0.0001) {
                if (comprimento <= 500) {
                    nivel = "D";
                } else if (comprimento <= 1000) {
                    nivel = "C";
                } else if (comprimento <= 1500) {
                    nivel = "B";
                } else {
                    nivel = "A";
                }
            } else if (Math.abs(bitola - 1.00) < 0.0001) {
                if (comprimento <= 200) {
                    nivel = "D";
                } else if (comprimento <= 500) {
                    nivel = "C";
                } else if (comprimento <= 1000) {
                    nivel = "B";
                } else {
                    nivel = "A";
                }
            } else if (Math.abs(bitola - 1.50) < 0.0001) {
                if (comprimento <= 100) {
                    nivel = "D";
                } else if (comprimento <= 300) {
                    nivel = "C";
                } else if (comprimento <= 700) {
                    nivel = "B";
                } else {
                    nivel = "A";
                }
            }
        }

        System.out.println("Nível: " + nivel);
        return nivel;
    }

    private void cadastrarProduto() {
        String codigo = codigoField.getText();
        Double bitola = (Double) bitolaComboBox.getSelectedItem();
        String comprimentoText = comprimentoField.getText();
        String cor = (String) corComboBox.getSelectedItem();
        String alocacao = (String) alocacaoComboBox.getSelectedItem();

        if (codigo.isEmpty() || bitola == null || comprimentoText.isEmpty() || cor == null || alocacao == null) {
            exibirAlerta("Campos incompletos", "Por favor, preencha todos os campos antes de cadastrar o produto.");
            return;
        }

        Double comprimento;
        try {
            comprimento = Double.valueOf(comprimentoText);
        } catch (NumberFormatException e) {
            exibirAlerta("Erro de Formato", "O Campo Comprimento deve ser um número válido.");
            return;
        }

        if (codigo.length() > 8) {
            exibirAlerta("Código Inválido", "O código do produto deve ter no máximo 8 dígitos.");
            return;
        }

        try {
            // Verificar se o código já existe
            PreparedStatement checkStmt = connection.prepareStatement("SELECT COUNT(*) FROM produtos WHERE codigo = ?");
            checkStmt.setString(1, codigo);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                exibirAlerta("Código Existente", "Já existe um produto com este código.");
                return;
            }

            // Cadastrar o produto
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO produtos (codigo, bitola, comprimento, cor, alocacao) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, codigo);
            statement.setDouble(2, bitola);
            statement.setDouble(3, comprimento);
            statement.setString(4, cor);
            statement.setString(5, alocacao);
            statement.executeUpdate();

            // Atualizar o estado do cassete para ocupado
            PreparedStatement updateCasseteStmt = connection.prepareStatement(
                    "UPDATE locacoes SET pn = 'Ocupado' WHERE ID = ?");
            updateCasseteStmt.setString(1, alocacao);
            updateCasseteStmt.executeUpdate();

            exibirAlerta("Produto cadastrado", "O produto foi cadastrado com sucesso.");
            limparCampos();
        } catch (SQLException e) {
            exibirAlerta("Erro no cadastro", "Ocorreu um erro ao cadastrar o produto: " + e.getMessage());
        }
    }

    private void exibirAlerta(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private void limparCampos() {
        codigoField.setText("");
        bitolaComboBox.setSelectedIndex(-1);
        comprimentoField.setText("");
        corComboBox.setSelectedIndex(-1);
        alocacaoComboBox.setSelectedIndex(-1);
        alocacaoComboBox.removeAllItems();
    }
}

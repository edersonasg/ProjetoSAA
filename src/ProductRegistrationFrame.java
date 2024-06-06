import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class ProductRegistrationFrame extends JFrame {

    private final Connection connection;
    private JTextField codigoField;
    private JComboBox<Double> bitolaComboBox;
    private JTextField comprimentoField;
    private JComboBox<String> corComboBox;
    private JComboBox<String> alocacaoComboBox;
    private JLabel loadingLabel;

    private void habilitarAlocacaoComboBox(boolean habilitar) {
        alocacaoComboBox.setEnabled(habilitar);
    }

    public ProductRegistrationFrame(Connection connection) {
        this.connection = connection;
        setTitle("Cadastro de Produto");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel codigoLabel = new JLabel(" Código do Produto:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(codigoLabel, gbc);
        codigoField = new JTextField(11);
        codigoField.setText("Exemplo: L1234567");
        codigoField.setForeground(Color.GRAY);
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPane.add(codigoField, gbc);

        JLabel corLabel = new JLabel("   Cor:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        contentPane.add(corLabel, gbc);

        corComboBox = new JComboBox<>(new String[] { "Vermelho", "Verde", "Azul", "Amarelo", "Branco", "Preto" });
        corComboBox.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 3;
        gbc.gridy = 0;
        contentPane.add(corComboBox, gbc);

        JLabel comprimentoLabel = new JLabel("Comprimento (mm):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(comprimentoLabel, gbc);
        comprimentoField = new JTextField(11);
        comprimentoField.setText("Exemplo: 80.50 - 1000");
        comprimentoField.setForeground(Color.GRAY);
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPane.add(comprimentoField, gbc);

        JLabel bitolaLabel = new JLabel("Bitola:");
        gbc.gridx = 2;
        gbc.gridy = 1;
        contentPane.add(bitolaLabel, gbc);

        bitolaComboBox = new JComboBox<>(new Double[] { 0.35, 0.50, 1.00, 1.50 });
        bitolaComboBox.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 3;
        gbc.gridy = 1;
        contentPane.add(bitolaComboBox, gbc);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(300, 5));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        contentPane.add(separator, gbc);

        JLabel alocacaoLabel = new JLabel("     Alocação");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(alocacaoLabel, gbc);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.setBackground(new Color(0, 102, 204));
        buscarButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(buscarButton, gbc);

        alocacaoComboBox = new JComboBox<>();
        alocacaoComboBox.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        contentPane.add(alocacaoComboBox, gbc);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBackground(new Color(51, 153, 102));
        cadastrarButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        contentPane.add(cadastrarButton, gbc);

        loadingLabel = new JLabel(new ImageIcon("src/resources/loading.gif"));
        loadingLabel.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 5;
        contentPane.add(loadingLabel, gbc);

        buscarButton.addActionListener((ActionEvent e) -> {
            buscarAlocacao();
        });

        cadastrarButton.addActionListener((ActionEvent e) -> {
            cadastrarProduto();
        });

        codigoField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (codigoField.getText().equals("Exemplo: L1234567")) {
                    codigoField.setText("");
                    codigoField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (codigoField.getText().isEmpty()) {
                    codigoField.setText("Exemplo: L1234567");
                    codigoField.setForeground(Color.GRAY);
                }
            }
        });

        comprimentoField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (comprimentoField.getText().equals("Exemplo: 80.50 - 1000")) {
                    comprimentoField.setText("");
                    comprimentoField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (comprimentoField.getText().isEmpty()) {
                    comprimentoField.setText("Exemplo: 80.50 - 1000");
                    comprimentoField.setForeground(Color.GRAY);
                }
            }
        });

        setContentPane(contentPane);
        setVisible(true);
    }

    public void addRegistrationFrameListener(WindowListener listener) {
        addWindowListener(listener);
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
                loadingLabel.setVisible(true);
                habilitarAlocacaoComboBox(false);
                alocacaoComboBox.removeAllItems();
                alocacaoComboBox.addItem("Buscando Alocação...");
                new Thread(() -> {
                    try {
                        Thread.sleep(3000); // 10 segundos
                        SwingUtilities.invokeLater(() -> {
                            loadingLabel.setVisible(false);
                            alocacaoComboBox.removeItem("Buscando Alocação...");
                            habilitarAlocacaoComboBox(true);
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                String nivel = calcularNivel(bitola, comprimento);

                if (nivel != null) {
                    PreparedStatement stmt = connection.prepareStatement(
                            "SELECT ID FROM locacoes WHERE nivel = ? AND pn = 'Vazio'");
                    stmt.setString(1, nivel);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        alocacaoComboBox.addItem(rs.getString("ID"));
                    }
                    if (alocacaoComboBox.getItemCount() == 1) {
                        exibirAlerta("Nenhum Cassete Disponível",
                                "Não há cassetes disponíveis para o nível calculado.");
                        alocacaoComboBox.removeItem("Buscando Alocação...");
                        habilitarAlocacaoComboBox(true);
                    }
                } else {
                    exibirAlerta("Nível não encontrado", "Não foi possível encontrar o nível de alocação.");
                    alocacaoComboBox.removeItem("Buscando Alocação...");
                    habilitarAlocacaoComboBox(true);
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
            PreparedStatement checkStmt = connection.prepareStatement("SELECT COUNT(*) FROM produtos WHERE codigo = ?");
            checkStmt.setString(1, codigo);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                exibirAlerta("Código Existente", "Já existe um produto com este código.");
                return;
            }

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO produtos (codigo, bitola, comprimento, cor, alocacao) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, codigo);
            statement.setDouble(2, bitola);
            statement.setDouble(3, comprimento);
            statement.setString(4, cor);
            statement.setString(5, alocacao);
            statement.executeUpdate();

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

package cadastroComBancoExample;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CadastroComBancoExample extends JFrame{
	private JTextField nomeField;
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JPasswordField confirmarSenhaField;
    private JButton cadastrarButton;

    public CadastroComBancoExample() {
        setTitle("Cadastre-se");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        nomeField = new JTextField();
        usuarioField = new JTextField();
        senhaField = new JPasswordField();
        confirmarSenhaField = new JPasswordField();
        cadastrarButton = new JButton("Cadastrar");

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Usuário:"));
        panel.add(usuarioField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);
        panel.add(new JLabel("Confirmar Senha:"));
        panel.add(confirmarSenhaField);
        panel.add(new JLabel()); // Espaço em branco para alinhar botão
        panel.add(cadastrarButton);

        add(panel);

        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        String nome = nomeField.getText();
        String usuario = usuarioField.getText();
        String senha = new String(senhaField.getPassword());
        String confirmarSenha = new String(confirmarSenhaField.getPassword());

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO usuarios (nome, usuario, senha) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, usuario);
                preparedStatement.setString(3, senha);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CadastroComBancoExample example = new CadastroComBancoExample();
            example.setVisible(true);
        });
    }
}

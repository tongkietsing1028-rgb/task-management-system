package gui;

import model.User;
import service.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JTextField passwordField;

    private JButton loginButton;
    private JButton registerButton;

    private JLabel statusLabel;
    private AuthService authService;

    public LoginFrame()
    {
        authService = new AuthService();
        initializeUI();
        setVisible(true);
    }

    public  void initializeUI()
    {
        setTitle("Task Management System");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JLabel title = new JLabel("Task Management System", SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(5, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        centerPanel.add(usernameLabel);
        usernameField = new JTextField();
        centerPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        centerPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        centerPanel.add(passwordField);

        loginButton = new JButton("Login");
        centerPanel.add(loginButton);
        registerButton = new JButton("Register");
        centerPanel.add(registerButton);

        statusLabel = new JLabel("");
        centerPanel.add(statusLabel);
        add(centerPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> loginAction() );
        registerButton.addActionListener(e -> registerAction() );

    }
    private void loginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = authService.login(username, password);


        if(user != null) {
            statusLabel.setText("Login successful");
            setVisible(false);
            new MainFrame(user);
            dispose();
        }
        else {
            statusLabel.setText("Login failed");
            statusLabel.setForeground(Color.RED);
        }

    }

    private void registerAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isSuccess = authService.register(username, password);
        if(isSuccess) {
            statusLabel.setText("Registration successful");
        }
        else {
            statusLabel.setText("Registration failed");
            statusLabel.setForeground(Color.RED);
        }
    }

}

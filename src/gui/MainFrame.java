package gui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private User user;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    private TaskPanel taskPanel;
    private GroupPanel groupPanel;

    public MainFrame(User user) {
        this.user = user;
        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        setTitle(String.format("Main Dashboard-%s",user.getUsername()));
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel usernameLabel = new JLabel(String.format("Welcome, %s!",user.getUsername()));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            setVisible(false);
            new LoginFrame();
            dispose();
        });
        topPanel.add(usernameLabel);
        topPanel.add(logoutButton);
        add(topPanel,BorderLayout.NORTH);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(2,1));
        JButton taskButton = new JButton("Tasks");
        JButton groupButton = new JButton("Groups");
        sidePanel.add(taskButton);
        sidePanel.add(groupButton);
        add(sidePanel,BorderLayout.WEST);


        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        taskPanel = new TaskPanel(user);
        groupPanel = new GroupPanel(user);

        contentPanel.add(taskPanel,"task");
        contentPanel.add(groupPanel,"group");

        add(contentPanel,BorderLayout.CENTER);

        taskButton.addActionListener(e -> cardLayout.show(contentPanel,"task"));
        groupButton.addActionListener(e -> cardLayout.show(contentPanel,"group"));

    }

}

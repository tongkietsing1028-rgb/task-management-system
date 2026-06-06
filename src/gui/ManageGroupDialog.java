package gui;

import model.Group;
import model.User;
import model.enums.TaskPriority;
import repository.TaskRepository;
import repository.UserRepository;
import service.GroupService;
import service.TaskService;

import javax.swing.*;
import java.awt.*;

public class ManageGroupDialog extends JDialog {
    private final String targetGroupId;
    private final String currentUserId;
    private DefaultListModel<String> memberListModel;
    private JList<String> memberList;


    public ManageGroupDialog(Group group,User currentUser)
    {
        this.targetGroupId = group.getGroupId();
        this.currentUserId = currentUser.getUserId();


        setModal(true);
        initializeUI();
        loadMembers();
        setVisible(true);
    }

    private void initializeUI()
    {
        setSize(400, 300);
        setLocationRelativeTo(null);
        setTitle("Manage Group");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        memberListModel = new DefaultListModel<>();
        memberList = new JList<>(memberListModel);
        JScrollPane scrollPane = new JScrollPane(memberList);
        add(scrollPane, BorderLayout.CENTER);

        JButton addBtn = new JButton("Add Member");
        JButton removeBtn = new JButton("Remove Member");
        JButton closeBtn = new JButton("Close");

        Group group = GroupService.findGroupById(targetGroupId);
        boolean isOwner =  group != null && group.getOwnerId().equals(currentUserId);
        addBtn.setEnabled(isOwner);
        removeBtn.setEnabled(isOwner);

        JPanel southPanel = new JPanel();
        southPanel.add(addBtn);
        southPanel.add(removeBtn);
        southPanel.add(closeBtn);
        add(southPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addMember());
        removeBtn.addActionListener(e -> removeMember());
        closeBtn.addActionListener(e -> dispose());
    }

    private void loadMembers()
    {
        memberListModel.clear();


        Group latestGroup = GroupService.findGroupById(targetGroupId);

        if (latestGroup == null)
        {
            JOptionPane.showMessageDialog(this, "Group not found");
            return;
        }

        setTitle("Manage Group - " + latestGroup.getGroupName());

        var members = latestGroup.getMemberIds();
        if (members == null) return;

        for (var member : members)
        {
            memberListModel.addElement(member);
        }
    }

    private void removeMember()
    {
        var selectedMember = memberList.getSelectedValue();
        if (selectedMember == null)
        {
            return;
        }

        Group group = GroupService.findGroupById(targetGroupId);
        if (group != null && group.getOwnerId().equals(selectedMember)) {
            JOptionPane.showMessageDialog(this, "Cannot remove the group owner.");
            return;
        }
        GroupService.removeMember(targetGroupId, selectedMember);
        loadMembers();
    }

    private void addMember()
    {
        var member = JOptionPane.showInputDialog(this, "Enter member ID to add:");
        if (member == null || member.trim().isEmpty())
        {
            return;
        }
        if (GroupService.isMember(targetGroupId, member))
        {
            JOptionPane.showMessageDialog(this, "Member already exists");
            return;
        }

        boolean success = GroupService.addMember(targetGroupId, member);
        if(success)
        {


            JOptionPane.showMessageDialog(this, "Member added successfully");
            loadMembers();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Failed to add member. User ID may not exist.");
        }

        loadMembers();
    }
}

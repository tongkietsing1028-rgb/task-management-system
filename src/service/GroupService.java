package service;

import model.Group;
import model.Task;
import model.User;
import model.enums.TaskPriority;
import repository.GroupRepository;
import repository.UserRepository;
import util.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GroupService {
    public static Group createGroup(User owner,String name)
    {
       List<String> memberIds = new ArrayList<>();
        memberIds.add(owner.getUserId());
        var group = new Group(IdGenerator.generateGroupId(),name,owner.getUserId(),memberIds);
        GroupRepository.save(group);
        return group;
    }

    public static boolean addMember(String groupId,String memberId)
    {
        if(UserRepository.findByUserId(memberId) == null)
        {
            return false;
        }
        Group group = GroupRepository.findByGroupId(groupId);
        if(group == null)
        {
            return false;
        }
        if(group.getMemberIds().contains(memberId))
        {
            return false;
        }
        group.addMember(memberId);
        GroupRepository.update(group);
        return true;
    }

    public static boolean removeMember(String groupId, String userId)
    {
        Group group = GroupRepository.findByGroupId(groupId);
        if(group == null)
        {
            return false;
        }
        if(!group.getMemberIds().contains(userId))
        {
            return false;
        }
        group.removeMember(userId);
        GroupRepository.update(group);
        return true;
    }

    public static List<Group> getGroupsByUser(String userId)
    {
        List<Group> groups = new ArrayList<>();
        for(Group group : GroupRepository.findAll())
        {
            if(group.getMemberIds().contains(userId))
            {
                groups.add(group);
            }
        }
        return groups;
    }

    public static Group findGroupById(String groupId)
    {
        return GroupRepository.findByGroupId(groupId);
    }

    public static boolean renameGroup(String groupId, String newName)
    {
        Group group = GroupRepository.findByGroupId(groupId);
        if(group == null)
        {
            return false;
        }
        group.setGroupName(newName);
        GroupRepository.update(group);
        return true;
    }

    public static boolean deleteGroup(String groupId)
    {
        Group group = GroupRepository.findByGroupId(groupId);
        if(group == null)
        {
            return false;
        }
        GroupRepository.delete(groupId);
        return true;
    }

    public static boolean isMember(String groupId, String userId)
    {
        Group group = GroupRepository.findByGroupId(groupId);
        if(group == null)
        {
            return false;
        }
        if(group.getMemberIds().contains(userId))
        {
            return true;
        }
        return false;
    }


}

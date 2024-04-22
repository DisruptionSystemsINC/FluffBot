package com.disruption.EventListeners.utility;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class MemberChecks {
    public boolean isStaff(Member mem, Guild guild){
        Role staffrole = guild.getRolesByName("staff", true).get(0);
        return mem.getRoles().contains(staffrole);
    }
}

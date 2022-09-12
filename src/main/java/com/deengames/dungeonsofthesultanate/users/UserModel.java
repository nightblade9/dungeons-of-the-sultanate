package com.deengames.dungeonsofthesultanate.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class UserModel {

    @Id @Getter @Setter
    private String id;

    @Getter @Setter
    private String userName;

    @Getter @Setter
    private String emailAddress;

    @Getter @Setter
    private Date lastLoginUtc;

    public UserModel(String id, String userName, String emailAddress, Date lastLoginUtc)
    {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.lastLoginUtc = lastLoginUtc;
    }
}

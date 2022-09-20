package com.deengames.dungeonsofthesultanate;

import com.deengames.dungeonsofthesultanate.users.UserModel;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTests {

    @Test
    void junitWorks() {
        assertEquals(2 + 3, 5);
    }

    @Test
    void lombokMagicWorks()
    {
        // For lombok to work in tests, specify it as a plugin, not an old-school Gradle dependency.
        // Arrange
        var userModel = new UserModel(new ObjectId(), "test@test.com", new Date());
        userModel.setEmailAddress("fake@fake.com");

        // Act
        var actualId = userModel.getEmailAddress();

        // Assert
        assertEquals(actualId, "fake@fake.com");
    }
}

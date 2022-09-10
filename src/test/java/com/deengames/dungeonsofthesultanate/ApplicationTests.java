package com.deengames.dungeonsofthesultanate;

import com.deengames.dungeonsofthesultanate.users.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

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
        var userModel = new UserModel("id1", "testUser", "test@test.com");
        userModel.setId("newID");

        // Act
        var actualId = userModel.getId();

        // Assert
        assertEquals(actualId, "newID");
    }
}

package com.deengames.dungeonsofthesultantate;

import com.deengames.dungeonsofthesultantate.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class DungeonsOfTheSultantateApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testCodeWorks() {
		Assert.isTrue(2 + 3 == 5);
	}

	@Test
	void lombokMagicWorks()
	{
		// For lombok to work in tests, specify it as a plugin, not an oldschool Gradle dependency.
		// Arrange
		User user = new User();
		var expectedId = "1031";
		user.setId(expectedId);

		// Act/Assert
		Assert.isTrue(user.getId() == expectedId);
	}
}

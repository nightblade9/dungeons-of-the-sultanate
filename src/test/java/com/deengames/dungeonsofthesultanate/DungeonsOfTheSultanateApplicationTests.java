package com.deengames.dungeonsofthesultanate;

import com.deengames.dungeonsofthesultanate.users.UserModel;
import com.deengames.dungeonsofthesultanate.users.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DungeonsOfTheSultanateApplicationTests extends BaseIntegrationTest
{

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void databaseAccessWorks()
	{
		// Arrange
		var expectedEmailAddress = "test@test.com";
		var expectedUser = new UserModel(new ObjectId(), null, expectedEmailAddress, new Date());

		// Act
		userRepository.save(expectedUser);

		// Assert
		var actual = userRepository.findUserByEmailAddress(expectedEmailAddress);
		assertNotNull(actual);
		assertEquals(actual.getId(), expectedUser.getId());
	}
}

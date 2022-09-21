package com.deengames.dungeonsofthesultanate;

import com.deengames.dungeonsofthesultanate.users.UserModel;
import com.deengames.dungeonsofthesultanate.users.UserRepository;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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
		Assert.assertNotNull(actual);
		Assert.assertEquals(actual.getId(), expectedUser.getId());
	}
}

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
class DungeonsOfTheSultanateApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void databaseAccessWorks()
	{
		// Arrange
		// TODO: move this to @Before; not sure why @Before/@After aren't called ...
		userRepository.deleteAll();

		var expectedEmailAddress = "test@test.com";
		var expectedUser = new UserModel(new ObjectId(), null, expectedEmailAddress, new Date());

		// Act
		userRepository.save(expectedUser);

		// Assert
		var actual = userRepository.findAll().get(0);
		// Wierdly enough, userRepository.findUserByEmailAddress(expectedEmailAddress) yields null...
		Assert.assertEquals(actual.getEmailAddress(), expectedUser.getEmailAddress());
		Assert.assertEquals(actual.getId(), expectedUser.getId());
	}
}

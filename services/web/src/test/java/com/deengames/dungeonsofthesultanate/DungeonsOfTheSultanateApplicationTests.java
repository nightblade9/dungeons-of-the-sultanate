package com.deengames.dungeonsofthesultanate;

import com.deengames.dungeonsofthesultanate.web.users.UserModel;
import com.deengames.dungeonsofthesultanate.web.users.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

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
		assertEquals(expectedUser.getId(), actual.getId());
	}

	@Test
	void embeddedMongoDbWorks(@Autowired final MongoTemplate mongoTemplate) {
		assertNotNull(mongoTemplate.getDb());
	}

}

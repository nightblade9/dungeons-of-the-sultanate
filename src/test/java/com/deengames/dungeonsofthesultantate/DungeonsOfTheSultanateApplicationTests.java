package com.deengames.dungeonsofthesultanate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class DungeonsOfTheSultanateApplicationTests {

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
		// For lombok to work in tests, specify it as a plugin, not an old-school Gradle dependency.
	}
}

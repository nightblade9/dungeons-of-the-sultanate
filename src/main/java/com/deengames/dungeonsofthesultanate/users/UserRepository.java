package com.deengames.dungeonsofthesultanate.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<UserModel, String> {

    @Query("{username: '?0'}")
    UserModel findUserByEmailAddress(String emailAddress);
}

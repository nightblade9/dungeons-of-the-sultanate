package com.deengames.dungeonsofthesultanate.web.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<UserModel, String> {

    @Query("{emailAddress: '?0'}")
    UserModel findUserByEmailAddress(String emailAddress);

    @Query("{username: '?0'}")
    UserModel findUserByUsername(String userName);
}

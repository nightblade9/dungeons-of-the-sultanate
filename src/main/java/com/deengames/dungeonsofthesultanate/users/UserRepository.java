package com.deengames.dungeonsofthesultanate.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<UserModel, String> {

    @Query("{userName: '?0'")
    UserModel findUserByUsername(String userName);
}

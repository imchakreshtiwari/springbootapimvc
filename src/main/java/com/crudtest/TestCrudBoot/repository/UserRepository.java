package com.crudtest.TestCrudBoot.repository;

import org.springframework.data.repository.CrudRepository;

import com.crudtest.TestCrudBoot.model.User;

public interface UserRepository extends CrudRepository<User,Long> {

}

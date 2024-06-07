package com.authentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.authentication.dto.ProfileResponseDto;
import com.authentication.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmailAndPassword(String email, String password);

	User findByEmail(String email);

	List<User> findByEmailIn(List<String> emails);
	
	List<User> findByIsActiveTrue();

//     @Query("SELECT new com.authentication.dto.ResponseDto(u.id, u.username, u.email, u.password) FROM User u WHERE u.email IN :emails AND u.role = 'ROLE_USER' ")
//     List<ResponseDto> getAllUserByEmails(@Param("emails") List<String> emails);
//    
//    @Query("SELECT new com.authentication.dto.ResponseDto(u.id, u.username, u.email, u.password) FROM newUser u WHERE u.role = 'ROLE_USER' AND u.isActive = true")
//    List<ResponseDto> getAllActiveUser();
//
//    // @Query("SELECT new com.authentication.dto.ProfileResponseDto(u.id, u.username, u.email, u.password) FROM User u WHERE (:searchString IS NULL OR u.username LIKE %:searchString% OR u.email LIKE %:searchString%)")
//    // List<ProfileResponseDto> profileSearch(String searchString);

    @Query("SELECT new com.authentication.dto.ProfileResponseDto(u.id, u.username, u.email, u.password) FROM User u WHERE ((:searchString IS NULL OR :searchString = '' OR LOWER(u.username) LIKE LOWER(CONCAT('%', :searchString, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchString, '%'))) AND u.role = 'ROLE_USER' )")
    List<ProfileResponseDto> profileSearchForUser(String searchString);

    @Query("SELECT new com.authentication.dto.ProfileResponseDto(u.id, u.username, u.email, u.password) FROM User u WHERE (:searchString IS NULL OR :searchString = '' OR LOWER(u.username) LIKE LOWER(CONCAT('%', :searchString, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchString, '%')) )")
    List<ProfileResponseDto> profileSearchForAdmin(String searchString);
//
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isActive = false WHERE u.id = :userId")
    void profileDeactivate(@Param("userId") int userId);

	@Query("SELECT u.isActive FROM User u WHERE (u.email = :email )")
	boolean isProfileActive(@Param("email") String email);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.isActive = true WHERE u.id = :userId")
	void activateUser(@Param("userId") int userId);
}

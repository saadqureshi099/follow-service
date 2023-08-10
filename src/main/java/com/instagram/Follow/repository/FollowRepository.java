package com.instagram.Follow.repository;

import com.instagram.Follow.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    List<Follow> findByUserId(Long uid);

    List<Follow> findByFollowedId(Long followedId);
    @Modifying
    @Query("DELETE FROM Follow f WHERE f.userId = :userId AND f.followedId = :followedId")
    void deleteByUserIdAndFollowedId(long userId, long followedId);
}

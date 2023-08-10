package com.instagram.Follow.repository;

import com.instagram.Follow.model.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestsRepository extends JpaRepository<Requests, Long> {
    List<Requests> findAllByFollowedId(Long userid);
    @Modifying
    @Query("DELETE FROM Requests r WHERE r.userId = :userId AND r.followedId = :followedId")
    void deleteByUserIdAndFollowedId(long userId, long followedId);
}

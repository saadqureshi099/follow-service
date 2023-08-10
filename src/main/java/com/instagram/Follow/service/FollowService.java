package com.instagram.Follow.service;

import com.instagram.Follow.clients.UserServiceClient;
import com.instagram.Follow.dto.FollowDto;
import com.instagram.Follow.dto.UserDto;
import com.instagram.Follow.model.Follow;
import com.instagram.Follow.model.Requests;
import com.instagram.Follow.repository.FollowRepository;
import com.instagram.Follow.repository.RequestsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {
    private final UserServiceClient userServiceClient;
    private final FollowRepository followRepository;
    private final RequestsRepository requestsRepository;

    /**
     * Returns User Followers
     * @param followedId
     * @return
     */

    public List<Follow> getUserFollowers(long followedId) {
        return followRepository.findByFollowedId(followedId);
    }

    /**
     * Returns Users that user have followed
     * @param userid
     * @return
     */
    public  List<Follow> getUserFollowed(long userid) {
        return followRepository.findByUserId(userid);
    }

    public List<Follow> getAllFollows() {
        return followRepository.findAll();
    }

    /**
     *If user profile is private add to requests
     * else follow user
     * @param followDto
     */
    public void addFollow(FollowDto followDto) {
        UserDto userDetails = userServiceClient.findByUserId(followDto.getFollowedId()).getBody();
        if(userDetails!=null && Boolean.TRUE.equals(userDetails.getIsPrivate())){
            Requests request = Requests.builder()
                    .userId(followDto.getUserId())
                    .followedId(followDto.getFollowedId())
                    .build();
            requestsRepository.save(request);
        }
       else {
            followed(followDto);
        }
    }

    /**
     * The method to add a new follow
     * @param followDto
     */
    public void followed(FollowDto followDto) {
        Follow follow = Follow.builder()
                .userId(followDto.getUserId())
                .followedId(followDto.getFollowedId())
                .build();
        log.info("User {} followed user {} ", followDto.getUserId(), followDto.getFollowedId());
        followRepository.save(follow);
    }

    /**
     * When a User unfollows another user it is deleted from follow table
     * @param followDto
     */
    @Transactional
    public void unFollow(FollowDto followDto) {
       followRepository.deleteByUserIdAndFollowedId(followDto.getUserId(), followDto.getFollowedId());
    }

    /**
     * Returns User requests pending
     * @param userId
     * @return
     */
    public List<Requests> getUserRequests(long userId) {
       return requestsRepository.findAllByFollowedId(userId);
    }

    /**
     * When a user accepts a request, the request is deleted from requests and added to the follow table
     * @param followDto
     */
    @Transactional
    public void acceptRequest(FollowDto followDto) {
        requestsRepository.deleteByUserIdAndFollowedId(followDto.getUserId(),followDto.getFollowedId());
        followed(followDto);
    }
}

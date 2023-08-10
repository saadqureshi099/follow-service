package com.instagram.Follow.controller;

import com.instagram.Follow.dto.FollowDto;
import com.instagram.Follow.model.Follow;
import com.instagram.Follow.model.Requests;
import com.instagram.Follow.service.FollowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/follows")
    public List<Follow> getFollows(){
        return followService.getAllFollows();
    }
    @GetMapping("/followed/{userid}")
    public List<Follow> getFollowed(@PathVariable long userid){
        return followService.getUserFollowed(userid);
    }
    @GetMapping("/followers/{followedId}")
    public List<Follow> getFollowers(@PathVariable long followedId){return followService.getUserFollowers(followedId);}
    @PostMapping("/addFollow")
    public void addFollow(@RequestBody FollowDto followdto){
         followService.addFollow(followdto);
    }
    @PostMapping("/acceptRequest")
    public void acceptRequest(@RequestBody FollowDto followdto){
       followService.acceptRequest(followdto);
   }
    @PostMapping("/unFollow")
    public void unFollow(@RequestBody FollowDto followdto){
        followService.unFollow(followdto);
    }
    @GetMapping("/requests/{userid}")
    public List<Requests> getRequests(@PathVariable long userid){
        return followService.getUserRequests(userid);
    }

}

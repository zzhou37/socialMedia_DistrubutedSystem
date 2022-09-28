package com.socialmedia.contentservice;

import com.socialmedia.clients.friendservice.UserPair;
import com.socialmedia.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping
public class ContentServiceController {

    ContentServiceService contentServiceService;

    @PostMapping("/get-conversation")
    public String getConversation(@RequestBody UserPair userPair){
        log.info("fetch content of user 1 and user2");
        return contentServiceService.getConversation(userPair);
    }
}

package com.network.content;

import com.network.clients.friendservice.UserPair;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/content")
public class ContentController {

    ContentService contentService;

    @PostMapping("/get-conversation")
    public String getConversation(@RequestBody UserPair userPair){
        log.info("fetch content of user 1 and user2");
        return contentService.getConversation(userPair);
    }
}

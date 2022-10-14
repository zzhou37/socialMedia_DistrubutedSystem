package com.network.friend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Friend {
    @Id
    private String id;

    @Indexed(unique = true)
    private String userName;

    private String email;
    private List<String> friendList;
    private List<String> friendWaitList;

}

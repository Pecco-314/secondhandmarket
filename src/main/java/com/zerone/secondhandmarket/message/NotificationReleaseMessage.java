package com.zerone.secondhandmarket.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationReleaseMessage {
    private Integer releaser;
    private String token;
    private Integer target;
    private String message;
}

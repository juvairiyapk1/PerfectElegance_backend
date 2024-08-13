package com.perfectElegance.controller;

import com.perfectElegance.modal.SignalMessage;
import com.perfectElegance.service.VideoCallService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignalingController {

    private final VideoCallService videoCallService;

    public SignalingController(VideoCallService videoCallService) {
        this.videoCallService = videoCallService;
    }

    @MessageMapping("/call/initiate")
    @SendTo("/user/queue/call")
    public SignalMessage initiateCall(SignalMessage message) {
        String callerId = message.getSender();
        return videoCallService.initiateCall(callerId, message.getReceiver());
    }

    @MessageMapping("/call/offer")
    @SendTo("/user/queue/call")
    public SignalMessage handleOffer(SignalMessage message) {
        System.out.println("Received offer: " + message);
        return videoCallService.handleOffer(message.getSender(), message.getReceiver(), (String) message.getPayload());
    }

    @MessageMapping("/call/answer")
    @SendTo("/user/queue/call")
    public SignalMessage handleAnswer(SignalMessage message) {
        System.out.println("Received answer: " + message);
        return videoCallService.handleAnswer(message.getReceiver(), message.getSender(), (String) message.getPayload());
    }

    @MessageMapping("/call/ice-candidate")
    @SendTo("/user/queue/call")
    public SignalMessage handleIceCandidate(SignalMessage message) {
        return videoCallService.handleIceCandidate(message.getSender(), message.getReceiver(), (String) message.getPayload());
    }
}

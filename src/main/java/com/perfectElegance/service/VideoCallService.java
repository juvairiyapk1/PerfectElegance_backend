package com.perfectElegance.service;

import com.perfectElegance.modal.SignalMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoCallService {

    private final SimpMessagingTemplate messagingTemplate;

    public SignalMessage initiateCall(String callerId, String calleeId) {
        return new SignalMessage("call_initiation", callerId, calleeId, null);
    }

    public SignalMessage handleOffer(String callerId, String calleeId, String offer) {
        return new SignalMessage("offer", callerId, calleeId, offer);
    }

    public SignalMessage handleAnswer(String callerId, String calleeId, String answer) {
        return new SignalMessage("answer", calleeId, callerId, answer);
    }

    public SignalMessage handleIceCandidate(String callerId, String calleeId, String iceCandidate) {
        return new SignalMessage("ice-candidate", callerId, calleeId, iceCandidate);
    }

}

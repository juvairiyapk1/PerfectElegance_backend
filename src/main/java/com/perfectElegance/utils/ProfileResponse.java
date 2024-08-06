package com.perfectElegance.utils;

import com.perfectElegance.modal.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ProfileResponse {

    private Profile profile;
    private  boolean isSubscribed;
}

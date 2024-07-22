package com.perfectElegance.utils;

import lombok.Data;
@Data
public class ToggleProfileRequest {
    private Integer userId;
    private boolean hidden;

}

package com.perfectElegance.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadReceipt {
    private Integer messageId;
    private Integer readByUserId;
}

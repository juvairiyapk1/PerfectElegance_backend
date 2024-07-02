package com.perfectElegance.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

@Getter
@Setter
public class PageRequestDto {
    private Integer pageNo = 0;
    private Integer pageSize = 10;

    public Pageable getPageable() {
        return PageRequest.of(pageNo, pageSize);
    }

}

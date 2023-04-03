package com.picstory.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Role Enum
 *
 * @author 서재건
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    USER("USER")
    ;

    private final String name;
}

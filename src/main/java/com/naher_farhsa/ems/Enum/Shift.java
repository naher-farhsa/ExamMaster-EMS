package com.naher_farhsa.ems.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Shift {
    MORNING("9AM-12PM"),
    AFTERNOON("1PM-4PM");
    private String shiftTime;
}

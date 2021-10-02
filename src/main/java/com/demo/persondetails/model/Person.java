package com.demo.persondetails.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.beans.Transient;
import java.time.*;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class Person {
    @NonNull
    private String name;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy",timezone = "UTC")
    private LocalDate dateOfBirth;
    @NonNull
    private String place;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private int year;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int months;

    public int getYear(){
        Period period = Period.between(this.dateOfBirth, LocalDate.now());
        return period.getYears();
    }
    public int getMonths(){
        Period period = Period.between(this.dateOfBirth, LocalDate.now());
        return period.getMonths();
    }
}

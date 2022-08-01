package com.example.supertest.Wrapper;

import java.lang.reflect.Field;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company_List__c {

    public Company_List__c() {
    }

    public Company_List__c(String COMPANY, String NAME1) {
        this.Name = NAME1;
        this.COMPANY__c = COMPANY;
    }

    public Company_List__c(String error) {
        this.error = error;
    }

    String Name;
    String COMPANY__c;
    String error;

    public void setField(String fieldName, String value) throws NoSuchFieldException, IllegalAccessException {
        Field field = getClass().getDeclaredField(fieldName + "__c");
        field.set(this, value);
        if (fieldName.equals("NAME1"))
            this.Name = value;
    }
}

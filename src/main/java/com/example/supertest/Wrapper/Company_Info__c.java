package com.example.supertest.Wrapper;

import java.lang.reflect.Field;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company_Info__c {

    public Company_Info__c() {}

    public Company_Info__c(String COMPANY, String NAME1, String NAME2, String COUNTRY, String LANGU, String STREET, String PO_BOX, String POSTL_COD1, String CITY, String CURRENCY, String COUNTRY_ISO, String LANGU_ISO) {
        this.Name = NAME1;
        this.COMPANY__c = COMPANY;
        this.NAME1__c = NAME1;
        this.NAME2__c = NAME2;
        this.COUNTRY__c = COUNTRY;
        this.LANGU__c = LANGU;
        this.STREET__c = STREET;
        this.PO_BOX__c = PO_BOX;
        this.POSTL_COD1__c = POSTL_COD1;
        this.CITY__c = CITY;
        this.CURRENCY_ISO__c = CURRENCY;
        this.COUNTRY_ISO__c = COUNTRY_ISO;
        this.LANGU_ISO__c = LANGU_ISO;
    }

    public Company_Info__c(String error) {
        this.error = error;
    }

    String Name;
    String COMPANY__c;
    String NAME1__c;
    String NAME2__c;
    String COUNTRY__c;
    String LANGU__c;
    String STREET__c;
    String PO_BOX__c;
    String POSTL_COD1__c;
    String CITY__c;
    String CURRENCY_ISO__c;
    String COUNTRY_ISO__c;
    String LANGU_ISO__c;

    String error;

    public void setField(String fieldName, String value) throws NoSuchFieldException, IllegalAccessException {
        Field field = getClass().getDeclaredField(fieldName + "__c");
        field.set(this, value);
        if (fieldName.equals("NAME1")) this.Name = value;
    }
}

package com.example.supertest.Client;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.example.supertest.Wrapper.Company_Info__c;
import com.example.supertest.Wrapper.Company_List__c;
import com.google.gson.Gson;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class JcoClient {
    static String ABAP_AS = "ABAP_AS_WITHOUT_POOL";
    static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
    static String ABAP_MS = "ABAP_MS_WITHOUT_POOL"; // Use Message Server

    // Properties 설정
    static {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "192.168.0.120"); // 호스트
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "20"); // 시스템 번호
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "800"); // 클라이언트 번호
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, "SBTRMSDI"); // 계정
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "thqpxpr2022~"); // 암호
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "KO"); // 언어
        createDestinationDataFile(ABAP_AS, connectProperties);

        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3"); // 대상에서 열린 상태로 유지되는 최대 유휴 연결
                                                                                       // 개수입니다. Default = 1

        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10"); // 대상에 대해 동시에 만들 수 있는 최대 활성 연결
                                                                                     // 개수입니다. Default = 0(무제한)

        createDestinationDataFile(ABAP_AS_POOLED, connectProperties);
    }

    // 연결정보 파일 생성
    static void createDestinationDataFile(String destinationName, Properties connectProperties) {
        File destCfg = new File(destinationName + ".jcoDestination");
        try {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create thedestination files", e);
        }
    }

    // 회사코드 입력하여 특정 회사코드만 import
    public static Company_Info__c getCompany(String id) throws JCoException {
        JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        JCoFunction function = destination.getRepository().getFunction("BAPI_COMPANY_GETDETAIL");
        if (function == null)
            throw new RuntimeException("Function을 찾을 수 없습니다.");
        try {
            function.getImportParameterList().setValue("COMPANYID", id);
            function.execute(destination);
        } catch (AbapException e) {
            System.out.println(e.toString());
            return null;
        }
        JCoStructure detail = function.getExportParameterList().getStructure("COMPANY_DETAIL");
        JCoMetaData detailMeta = detail.getMetaData();
        Company_Info__c company = new Company_Info__c();
        System.out.println(detail.toString());
        for (int i = 0; i < detailMeta.getFieldCount(); i++) {
            try {
                company.setField(detailMeta.getName(i), detail.getString(detailMeta.getName(i)));
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return company;
    }

    // BAPI_COMPANY_GETLIST : import parameter 값 없이 모든 리스트 import
    public static Company_Info__c getCompanyList() throws JCoException {
        JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        JCoFunction function = destination.getRepository().getFunction("BAPI_COMPANY_GETLIST");
        if (function == null)
            throw new RuntimeException("Function을 찾을 수 없습니다.");
        try {
            function.execute(destination);
        } catch (AbapException e) {
            System.out.println(e.toString());
            return null;
        }
        JCoStructure detail = function.getExportParameterList().getStructure("COMPANY_DETAIL");
        JCoMetaData detailMeta = detail.getMetaData();
        Company_List__c companyList = new Company_List__c();
        System.out.println(detail.toString());
        for (int i = 0; i < detailMeta.getFieldCount(); i++) {
            try {
                companyList.setField(detailMeta.getName(i), detail.getString(detailMeta.getName(i)));
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return companyList;
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        try {
            System.out.println(gson.toJson(getCompany("1")));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}

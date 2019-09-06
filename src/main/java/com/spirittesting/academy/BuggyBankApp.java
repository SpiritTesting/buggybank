package com.spirittesting.academy;

import com.spirittesting.academy.exceptions.KontoNotFoundException;
import com.spirittesting.academy.exceptions.KundeNotFoundException;

import java.math.BigDecimal;

public class BuggyBankApp {

    public static void main(String[] args) {
        KundenService kundenService = new KundenService();

        kundenService.addKunde("Hannes", 2);
        kundenService.addKunde("Werner", 2);

        try {
            kundenService.addZahlung(kundenService.getKonto("01"), kundenService.getKonto("03"), BigDecimal.valueOf(10));
            kundenService.addZahlung(kundenService.getKonto("01"), kundenService.getKonto("03"), BigDecimal.valueOf(15));
            kundenService.addZahlung(kundenService.getKonto("03"), kundenService.getKonto("04"), BigDecimal.valueOf(5));
        } catch (KontoNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("------");
        try {
            System.out.println(kundenService.getKundeninfo("001").toString());
        } catch (KundeNotFoundException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("------");
        try {
            System.out.println(kundenService.getKundeninfo("002").toString());
        } catch (KundeNotFoundException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("------");
        try {
            System.out.println(kundenService.getKontoinfo("01"));
        } catch (KontoNotFoundException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("------");
        try {
            System.out.println(kundenService.getKontoinfo("02"));
        } catch (KontoNotFoundException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("------");
        try {
            System.out.println(kundenService.getKontoinfo("03"));
        } catch (KontoNotFoundException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("------");
        try {
            System.out.println(kundenService.getKontoinfo("04"));
        } catch (KontoNotFoundException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("------");

    }

}

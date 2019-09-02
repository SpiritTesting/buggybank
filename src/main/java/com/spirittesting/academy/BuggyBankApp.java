package com.spirittesting.academy;

import java.math.BigDecimal;

public class BuggyBankApp {

    public static void main(String[] args) {
        KundenService kundenService = new KundenService();

        Kunde hannes = new Kunde("001", "Hannes");
        Kunde werner = new Kunde("002", "Werner");

        Konto hannes1 = new Konto("01", hannes);
        Konto hannes2 = new Konto("02", hannes);
        Konto werner1 = new Konto("03", werner);
        Konto werner2 = new Konto("04", werner);

        Zahlung hannes1werner1a = new Zahlung(hannes1, werner1, BigDecimal.TEN);
        Zahlung hannes1werner1b = new Zahlung(hannes1, werner1, BigDecimal.valueOf(15));
        Zahlung werner1werner2 = new Zahlung(werner1, werner2, BigDecimal.valueOf(5));

        hannes1.addZahlung(hannes1werner1a);
        hannes1.addZahlung(hannes1werner1b);
        werner1.addZahlung(hannes1werner1a);
        werner1.addZahlung(hannes1werner1b);
        werner1.addZahlung(werner1werner2);
        werner2.addZahlung(werner1werner2);

        kundenService.addKunde(hannes);
        kundenService.addKunde(werner);
        kundenService.addKonto(hannes1);
        kundenService.addKonto(hannes2);
        kundenService.addKonto(werner1);
        kundenService.addKonto(werner2);

        System.out.println("------");
        System.out.println(kundenService.getKundeninfo("001"));
        System.out.println("------");
        System.out.println(kundenService.getKundeninfo("002"));
        System.out.println("------");
        System.out.println(kundenService.getKontoinfo("01"));
        System.out.println("------");
        System.out.println(kundenService.getKontoinfo("02"));
        System.out.println("------");
        System.out.println(kundenService.getKontoinfo("03"));
        System.out.println("------");
        System.out.println(kundenService.getKontoinfo("04"));
        System.out.println("------");
    }

}

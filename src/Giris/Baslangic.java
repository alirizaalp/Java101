package Giris;

import java.util.Scanner;

public class Baslangic {

    public static void main(String[] args) {
        MayinTarlasiOyunu mayinTarlaOyunu = new MayinTarlasiOyunu(4, 3);
        mayinTarlaOyunu.oyunuBaslat();
    }
}

class MayinTarlasiOyunu {
    private int satirSayisi;
    private int sutunSayisi;
    private int[][] mayinKonumlari;
    private char[][] oyunTahtasi;
    private boolean devamEdiyor = true;

    public MayinTarlasiOyunu(int satirSayisi, int sutunSayisi) {
        this.satirSayisi = satirSayisi;
        this.sutunSayisi = sutunSayisi;
        this.mayinKonumlari = new int[satirSayisi][sutunSayisi];
        this.oyunTahtasi = new char[satirSayisi][sutunSayisi];
        oyunTahtasiniOlustur();
        mayinlariYerlestir();
    }

    private void oyunTahtasiniOlustur() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                oyunTahtasi[i][j] = '-';
            }
        }
    }

    private void mayinlariYerlestir() {
        int mayinSayisi = satirSayisi * sutunSayisi / 4;
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < mayinSayisi; i++) {
            int randomSatir = random.nextInt(satirSayisi);
            int randomSutun = random.nextInt(sutunSayisi);

            // Aynı yere mayın koymamak için kontrol
            while (mayinKonumlari[randomSatir][randomSutun] == 1) {
                randomSatir = random.nextInt(satirSayisi);
                randomSutun = random.nextInt(sutunSayisi);
            }

            mayinKonumlari[randomSatir][randomSutun] = 1;
        }
    }

    private void oyunTahtasiniYazdir() {
        System.out.println("Mayın Tarlası Oyunu");
        System.out.println();

        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(oyunTahtasi[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean gecerliHamleMi(int satir, int sutun) {
        return satir >= 0 && satir < satirSayisi && sutun >= 0 && sutun < sutunSayisi;
    }

    private void hucreyiKontrolEt(int satir, int sutun) {
        if (mayinKonumlari[satir][sutun] == 1) {
            System.out.println("Mayına bastınız! Oyunu kaybettiniz.");
            devamEdiyor = false;
        } else {
            int mayinSayisi = komsuMayinSayisiniSay(satir, sutun);
            oyunTahtasi[satir][sutun] = (char) (mayinSayisi + '0');

            if (mayinSayisi == 0) {
                bosHucreleriAc(satir, sutun);
            }
        }
    }

    private int komsuMayinSayisiniSay(int satir, int sutun) {
        int sayac = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int yeniSatir = satir + i;
                int yeniSutun = sutun + j;
                if (gecerliHamleMi(yeniSatir, yeniSutun) && mayinKonumlari[yeniSatir][yeniSutun] == 1) {
                    sayac++;
                }
            }
        }
        return sayac;
    }

    private void bosHucreleriAc(int satir, int sutun) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int yeniSatir = satir + i;
                int yeniSutun = sutun + j;
                if (gecerliHamleMi(yeniSatir, yeniSutun) && oyunTahtasi[yeniSatir][yeniSutun] == '-') {
                    hucreyiKontrolEt(yeniSatir, yeniSutun);
                }
            }
        }
    }

    private boolean oyunKazanildiMi() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                if (oyunTahtasi[i][j] == '-' && mayinKonumlari[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void oyunuBaslat() {
        Scanner scanner = new Scanner(System.in);

        while (devamEdiyor) {
            oyunTahtasiniYazdir();
            System.out.print("Satır seçin: ");
            int satir = scanner.nextInt();

            System.out.print("Sütun seçin: ");
            int sutun = scanner.nextInt();

            if (!gecerliHamleMi(satir, sutun)) {
                System.out.println("Geçersiz bir nokta seçtiniz. Lütfen tekrar seçin.");
                continue;
            }

            hucreyiKontrolEt(satir, sutun);

            if (oyunKazanildiMi()) {
                System.out.println("Tebrikler! Oyunu kazandınız.");
                devamEdiyor = false;
            }
        }

        scanner.close();
    }
}

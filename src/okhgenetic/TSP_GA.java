/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okhgenetic;

 import java.io.File;
 import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 import java.util.Scanner;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import java.util.ArrayList;

public class TSP_GA {   
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    
    public static void main(String[] args) throws FileNotFoundException {
        
        System.out.println("Pilih File : ");
        System.out.println("1. Big 1");
        System.out.println("2. Big 2");
        System.out.println("3. Medium 1");
        System.out.println("4. Medium 2");
        System.out.println("5. Small 1");
        System.out.println("6. Small 2");
        System.out.println("7. Hiddeninstance 1");
        System.out.println("8. Hiddeninstance 2");
         
        Scanner scan = new Scanner(System.in);
        System.out.println("File yang dipilih (1/2/3/4/5/6/7/8) : ");
        int pilih = scan.nextInt();
        String fileName;
        
        switch (pilih) {
            case 1:  fileName = "D:\\ant\\big1.csv";
                     break;
            case 2:  fileName = "D:\\ant\\big2.csv";
                     break;
            case 3:  fileName = "D:\\ant\\medium1.csv";
                     break;
            case 4:  fileName = "D:\\ant\\medium2.csv";
                     break;
            case 5:  fileName = "D:\\ant\\small1.csv";
                     break;
            case 6:  fileName = "D:\\ant\\small2.csv";
                     break;
            case 7:  fileName = "D:\\ant\\hiddeninstance1.csv";
                     break;
            case 8:  fileName = "D:\\ant\\hiddeninstance2.csv";
                     break;

            default: fileName = "D:\\ant\\small1.csv";
                     break;
        }
        final long startTime = System.currentTimeMillis();
        
        File file = new File(fileName); 
        try {
            Scanner inputStream = new Scanner(file);
            int isi=inputStream.nextInt();
            City a[]=new City[isi];
            int  index=0;
            while (inputStream.hasNext()){
                String b=inputStream.next();
                String array1[]= b.split(",");

                a[index]=new City(index+1, Integer.parseInt(array1[0]),Integer.parseInt(array1[1]));
                TourManager.addCity(a[index]);
                index++;
            }
            inputStream.close();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    
        System.out.println("");
        System.out.println("Data " + fileName);
        
        // Initialize population
        Population pop = new Population(50, true);
        System.out.println("");
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        // Evolve population for 100 generations 
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 10; i++) {
            pop = GA.evolvePopulation(pop);
        }
        final long endTime = System.currentTimeMillis();
        
        LocalDateTime now = LocalDateTime.now();
        String datenow = dtf.format(now);
        String datenow1 = datenow.replace("/", "");
        String datenow2 = datenow1.replace(":", "");
        String datecsv = datenow2.replace(" ", "");
        //System.out.println(datecsv);
        
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest()); 
        System.out.println("Running time: " + (endTime - startTime));
        
        PrintWriter pw = new PrintWriter(new File("D://ant/result/GA_"+datecsv +".csv"));
        StringBuilder sbr = new StringBuilder();
        sbr.append("Final distance : " + pop.getFittest().getDistance());
        sbr.append('\n');
        sbr.append("Solution : " +pop.getFittest());
        sbr.append('\n');
        sbr.append("Running time : " + (endTime - startTime) +" milisecond");

        pw.write(sbr.toString());
        pw.close();
        System.out.println("");
        System.out.println("Done Printing CSV! \nLocation D://ant/result/GA_"+datecsv +".csv");
    }
}

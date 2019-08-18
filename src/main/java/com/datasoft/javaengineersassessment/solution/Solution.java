package com.datasoft.javaengineersassessment.solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.gson.*;
import com.sun.tools.javac.util.ArrayUtils;

public class Solution implements Runnable {


    /**
     * Application entry to your solution
     *
     * @see Thread#run()
     */
    @Override
    public void run() {



        int TestCase;
        int NumberOfTables;
        int NumberOfObjects;


        File file = new File("input.txt");

        try (Scanner sc = new Scanner(file)) {

            TestCase = Integer.parseInt(sc.nextLine());

            for (int TestCaseCounter = 0; TestCaseCounter < TestCase; TestCaseCounter++) {

                List<String> Keys = new ArrayList<>();
                List<String> UniqueKeys = new ArrayList<>();
                String Data = "";
                String[] Temp1;
                String[] TableNames;
                ArrayList<JsonObject> Objects = new ArrayList<>();


                Temp1 = sc.nextLine().split(" ");
                TableNames = sc.nextLine().split(" ");
                NumberOfTables = Integer.parseInt(Temp1[0]);
                NumberOfObjects = Integer.parseInt(Temp1[1]);

                /* Modifying tables names */

                Map<String, String> Tables = new HashMap<String, String>();

                for (int q = 0; q < TableNames.length; q++) {

                    if (TableNames[q].contains("(")) {

                        String order = TableNames[q].substring(TableNames[q].indexOf("(") + 1, TableNames[q].indexOf(")"));
                        TableNames[q] = TableNames[q].replace(("(" + order + ")"), "");
                        Tables.put(TableNames[q], order);
                    } else Tables.put(TableNames[q], "asc");

                }

                /* /////////// */

                for (int ObjectCounter = 0; ObjectCounter < NumberOfObjects; ObjectCounter++) {

                    while (sc.hasNextLine()) {
                        String Temp2 = sc.nextLine();
                        Data += Temp2;
                        if (Temp2.equals("}")) {

                            JsonParser parser = new JsonParser();
                            JsonElement jsonTree = parser.parse(Data);
                            JsonObject jsonObject = jsonTree.getAsJsonObject();
                            Objects.add(jsonObject);

                            Data = "";
                            break;
                        }
                    }
                }

                if (sc.hasNextLine()) sc.nextLine();



                //output

                System.out.println("Test# " + (TestCaseCounter + 1));

                for (int TableCounter = 0; TableCounter < TableNames.length; TableCounter++) {

                    System.out.println(TableNames[TableCounter]);




                    if (UniqueKeys.contains(TableNames[TableCounter])) {

                        List<String> Keys_nested = new ArrayList<>();
                        List<String> UniqueKeys_nested = new ArrayList<>();

                        int id = 1;

                        for (JsonObject obj : Objects) {


                                 try {

                                     JsonObject obj2 = obj.getAsJsonObject(TableNames[TableCounter]);
                                     Set<Map.Entry<String, JsonElement>> entries = obj2.entrySet();
                                     for (Map.Entry<String, JsonElement> entry : entries) {
                                         Keys_nested.add(entry.getKey());
                                     }

                                     UniqueKeys_nested = Keys_nested.stream().distinct().collect(Collectors.toList());

//                                     System.out.print("id ");
//
//                                     for (String Coloumns : UniqueKeys_nested) {
//
//                                         if (id == 1) System.out.print(Coloumns + " ");
//
//                                     }

//                                     if (id == 1) System.out.println();


                                     System.out.println((id++) + " " + obj.get(TableNames[TableCounter]) + " ");

                                    // for (String Values : UniqueKeys_nested) {

                                         //if (id == 1) System.out.print("id " + obj.get(Values) + " ");

                                    //}


                                 } catch (Exception e) {

                                     if (id == 1) System.out.print("id" + " " + TableNames[TableCounter] + "\n");
                                     JsonArray obj2 = obj.getAsJsonArray(TableNames[TableCounter]);
                                     System.out.println((id++) + " " + obj2);


                                 }




                        }


                    } else {

                        for (JsonObject obj : Objects) {

                            Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
                            for (Map.Entry<String, JsonElement> entry : entries) {
                                Keys.add(entry.getKey());
                            }

                        }

                        UniqueKeys = Keys.stream().distinct().collect(Collectors.toList());

                        int id = 1;

                        System.out.print("id ");

                        for (String Coloumns : UniqueKeys) {

                            if (Arrays.asList(TableNames).contains(Coloumns)) continue;

                            System.out.print(Coloumns + " ");

                        }

                        System.out.println();

                        for (JsonObject obj : Objects) {

                            System.out.print(id++ + " ");

                            for (String Coloumns : UniqueKeys) {

                                if (Arrays.asList(TableNames).contains(Coloumns)) continue;

                                System.out.print(obj.get(Coloumns) + " ");

                            }
                            System.out.println();

                        }


                    }


                    System.out.println();

                }


            }

        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found.Please Input Data in input.txt at root directory");
        }


    }

}


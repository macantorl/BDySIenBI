/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsiproject;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario07
 */
public class PythonConnector {

    Runtime rt;
    Process pr;
    BufferedReader input;
    String line;
    List<String> data;

    public PythonConnector() {
        rt = Runtime.getRuntime();
        line = null;
    }

    public Object[] getItems(String item, int type) {
        try {
            data = new ArrayList<>();
            pr = rt.exec("C:\\Python27\\python.exe " + System.getProperty("user.dir") + "\\python\\KEGGServiceList.py " + item);
            input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.equals("")) {
                    if (item.toLowerCase().equals("org")) {
                        if (type == 0) {
                            data.add((line.split("\t")[0]) + ": " + line.split("\t")[2]);
                        } else {
                            data.add(line.split("\t")[1]);
                        }
                    } else if (item.toLowerCase().contains("map") && line.contains("NAME")) {
                        String[] temp = line.split("        ");
                        data.add(temp[1]);
                        return data.toArray();
                    } else if (!item.toLowerCase().equals("org") && !item.toLowerCase().contains("map")) {
                        data.add((line.split("\t")[0]).split(":")[1] + ": " + line.split("\t")[1]);
                    }
                    //data.add(line);
                }
            }
            if (data.isEmpty()) {
                input = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                }
            }
            return data.toArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void visualize(String pathway) {
        try {
            File f = new File(System.getProperty("user.dir") + "\\Downloads\\PDF\\" + pathway + ".pdf");
            if (!f.exists()) {
                pr = rt.exec("C:\\Python27\\python.exe " + System.getProperty("user.dir") + "\\python\\KEGGServiceGet.py " + pathway);
                input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                line = input.readLine();
            }
            Desktop.getDesktop().open(f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    List<String> compare(String org1, String org2) {
        List<String> link1, link2;
        link1 = getLink(org1.split(":")[0]);
        link2 = getLink(org2.split(":")[0]);
        data = new ArrayList<>();
        data.add(org1.split(":")[1] + " - " + link1.get(0));
        data.add(org2.split(":")[1] + " - " + link2.get(0));
        for (int i = 1; i < link1.size(); i++) {
            String string = link1.get(i);
            if (link2.indexOf(string) != -1 && data.indexOf(string) == -1) {
                data.add(string);
            }
        }
        for (int i = 1; i < link2.size(); i++) {
            String string = link2.get(i);
            if (link1.indexOf(string) != -1 && data.indexOf(string) == -1) {
                data.add(string);
            }
        }
        /*System.out.println("FinalSize: " + (data.size()));
        System.out.println("Link1Size: " + (link1.size()));
        System.out.println("Link2Size: " + (link2.size()));
        for (String string : link1) {
            if (data.indexOf(string) == -1 && link1.indexOf(string) != 0) {
                System.out.println("NF1: " + string);
            }
        }
        for (String string : link2) {
            if (data.indexOf(string) == -1 && link2.indexOf(string) != 0) {
                System.out.println("NF2: " + string);
            }
        }*/
        return data;
    }

    private List<String> getLink(String org) {
        List<String> ret = new ArrayList<>();
        try {
            pr = rt.exec("C:\\Python27\\python.exe " + System.getProperty("user.dir") + "\\python\\KEGGServiceCompare.py " + org);
            input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.equals("")) {
                    if (ret.isEmpty()) {
                        ret.add((((line.split("\t")[1]).split(":")[1]).substring(0, 3)));
                    }
                    if (ret.indexOf(((line.split("\t")[1]).split(":")[1]).substring(3)) == -1) {
                        ret.add((((line.split("\t")[1]).split(":")[1]).substring(3)));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return ret;
    }
}

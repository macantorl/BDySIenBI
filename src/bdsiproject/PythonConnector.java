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

    public Object[] getItems(String item) {
        try {
            data = new ArrayList<>();
            pr = rt.exec("C:\\Python27\\python.exe " + System.getProperty("user.dir") + "\\python\\KEGGServiceList.py " + item);
            input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.equals("")) {
                    data.add((line.split("\t")[0]).split(":")[1] + ": " + line.split("\t")[1]);
                    //data.add(line);
                }
            }
            if (data.isEmpty()) {
                data.add("Error!");
            }
            return data.toArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void visualize(String pathway) {
        try {
            pr = rt.exec("C:\\Python27\\python.exe " + System.getProperty("user.dir") + "\\python\\KEGGServiceGet.py " + pathway);
            input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            line = input.readLine();
            File f = new File(System.getProperty("user.dir") + "\\Downloads\\PDF\\" + line);
            Desktop.getDesktop().open(f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

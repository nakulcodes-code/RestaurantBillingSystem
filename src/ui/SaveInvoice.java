package ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveInvoice {

    public static void saveInvoice(String invoiceContent, String filePath) {
        try {
            File invoiceFile = new File(filePath);
            if (!invoiceFile.exists()) {
                invoiceFile.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(invoiceFile));
            writer.write(invoiceContent);
            writer.close();
            System.out.println("Invoice saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

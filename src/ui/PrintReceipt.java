package ui;

import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintReceipt {

    public static void printReceipt(String receiptContent) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }

            graphics.drawString("Restaurant Billing System", 100, 100);
            graphics.drawString("Receipt Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 100, 120);
            graphics.drawString(receiptContent, 100, 140);

            return PAGE_EXISTS;
        });

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }
}

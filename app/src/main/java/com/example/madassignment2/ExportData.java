package com.example.madassignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExportData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_data);

        CardView exportButton = findViewById(R.id.pdfExportOption);
        CardView comingSoonButton = findViewById(R.id.comingSoonButton);

        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 0); // Replace 'defaultValue' with the default value if not found
        String userName = intent.getStringExtra("userName");
        String userPhone = intent.getStringExtra("userPhone");
        String userEmail = intent.getStringExtra("userEmail");

        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve data from the database
                List<String> medicineDataList = retrieveDataFromDatabase();

                // Generate the PDF using the retrieved data
                String filePath = generatePdf(medicineDataList, userName,  userPhone, userEmail);

                Toast.makeText(ExportData.this, "PDF Exported Successfully at " + filePath, Toast.LENGTH_SHORT).show();
            }
        });
        comingSoonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExportData.this, "Opss! I'm still under construction =.= ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Retrieve data from the database and format it as a list of strings
    private List<String> retrieveDataFromDatabase() {
        MedicineDatabase dbHelper = new MedicineDatabase(this);
        Cursor cursor = dbHelper.readAllData();

        List<String> medicineDataList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String medicineType = cursor.getString(cursor.getColumnIndex("medicine_type"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("medicine_name"));
                @SuppressLint("Range") int amount = cursor.getInt(cursor.getColumnIndex("consume_amount"));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex("consume_type"));
                @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex("extra_note"));

                // Format the data and add it to the list
                String dataRow = " Record " + id + " : " +
                        "\n Medicine Type: " + medicineType +
                        "\n Name: " + name +
                        "\n Amount: " + amount +
                        "\n Type: " + type +
                        "\n Note: " + note + "\n\n";
                medicineDataList.add(dataRow);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return medicineDataList;
    }

    // Generate the PDF using the retrieved data
    private String generatePdf(List<String> data, String userName, String userPhone, String userEmail) {
        try {
            String trimmedUsername = userName.replaceAll("\\s", "");
            String filePath = Environment.getExternalStorageDirectory() + "/"+trimmedUsername+"_MedicationReport.pdf";

            // Open a new PDF file
            Document document = new Document(PageSize.A4);
            // Initialize the PDF writer
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // TextFormat
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
            Font fontCoverPage = new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.NORMAL);
            Font fontCoverPageTitle = new Font(Font.FontFamily.TIMES_ROMAN, 30, Font.BOLD);

            //Cover page with userInfo
            // Add the cover page with user information
            Paragraph coverPage = new Paragraph();
            coverPage.setAlignment(Element.ALIGN_CENTER);

            //
            for(int i=0;i<=8;i++){
                coverPage.add(Chunk.NEWLINE);
            }

            Paragraph title = new Paragraph("MyMedic Medication Report", fontCoverPageTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            coverPage.add(title);

            Paragraph nameParagraph = new Paragraph("Name: " + userName, fontCoverPage);
            nameParagraph.setAlignment(Element.ALIGN_CENTER);
            coverPage.add(nameParagraph);

            Paragraph emailParagraph = new Paragraph("User Email: " + userEmail, fontCoverPage);
            emailParagraph.setAlignment(Element.ALIGN_CENTER);
            coverPage.add(emailParagraph);

            Paragraph phoneParagraph = new Paragraph("User Phone: " + userPhone, fontCoverPage);
            phoneParagraph.setAlignment(Element.ALIGN_CENTER);
            coverPage.add(phoneParagraph);

            // Add the cover page to the document
            document.add(coverPage);
            // Start a new page for the data
            document.newPage();


            // Add each line of data to the PDF
            for (String line : data) {
                // Create a rectangle to bound the record
                Rectangle rect = new Rectangle(36, 36, 559, 806);
                rect.setBorder(Rectangle.BOX);
                rect.setBorderWidth(1);
                document.add(rect);

                Paragraph paragraph = new Paragraph(line, font);
                document.add(paragraph);
            }

            // Close the document
            document.close();
            return filePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "no";
    }
}

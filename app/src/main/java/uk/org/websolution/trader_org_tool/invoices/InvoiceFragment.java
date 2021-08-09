package uk.org.websolution.trader_org_tool.invoices;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.org.websolution.trader_org_tool.R;

public class InvoiceFragment extends Fragment {
    EditText editTextNr, editTextFrom, editTextTo, editTextDate, editTextDue, editTextJob1, editTextJob2, editTextRate1, editTextRate2;
    MaterialButton createInvoiceBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_invoice, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        init();
    }

    private void init() {
        editTextNr = getActivity().findViewById(R.id.edit_tex_invoice_nr);
        editTextFrom = getActivity().findViewById(R.id.edit_text_bill_from);
        editTextTo = getActivity().findViewById(R.id.edit_text_bill_to);
        editTextDate = getActivity().findViewById(R.id.edit_text_date);
        editTextDue = getActivity().findViewById(R.id.edit_text_due_date);
        editTextJob1 = getActivity().findViewById(R.id.edit_text_job1);
        editTextJob2 = getActivity().findViewById(R.id.edit_text_job2);
        editTextRate1 = getActivity().findViewById(R.id.edit_text_rate1);
        editTextRate2 = getActivity().findViewById(R.id.edit_text_due_rate2);
        createInvoiceBtn = getActivity().findViewById(R.id.send_invoice_btn);

        createInvoiceBtn.setOnClickListener(v -> createPdf(editTextNr.getText().toString(),
                editTextFrom.getText().toString(),
                editTextTo.getText().toString(),
                editTextDate.getText().toString(),
                editTextDue.getText().toString(),
                editTextJob1.getText().toString(),
                editTextJob2.getText().toString(),
                editTextRate1.getText().toString(),
                editTextRate2.getText().toString()
        ));
    }

    private void createPdf(String title, String from, String to, String date, String due, String job1, String job2, String rate1, String rate2) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawText(title, 20, 40, paint);
        canvas.drawText(from, 20, 60, paint);
        canvas.drawText(to, 20, 80, paint);
        canvas.drawText(date, 20, 100, paint);
        canvas.drawText(due, 20, 120, paint);
        canvas.drawText(job1, 20, 140, paint);
        canvas.drawText(rate1, 20, 160, paint);
        canvas.drawText(job2, 20, 180, paint);
        canvas.drawText(rate2, 20, 200, paint);
        document.finishPage(page);

        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path + title + ".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(getContext(), "Invoice saved to /mydpf/ folder", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error " + e.toString());
            Toast.makeText(getContext(), "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
        document.close();
    }


}

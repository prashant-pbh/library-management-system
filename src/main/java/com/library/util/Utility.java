package com.library.util;

import com.library.model.Sequence;
import com.library.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class Utility
{
    @Autowired
    SequenceRepository sequenceRepository;
    public int generateOrderId()
    {
        List<Sequence> sequences = sequenceRepository.findAll();
        int nextOrderIdSequence = 0;
        for(Sequence sqn: sequences)
        {
            if(sqn.getId().equalsIgnoreCase("orderId"))
            {
                nextOrderIdSequence = sqn.getSequence();
                sqn.setSequence(sqn.getSequence()+1);
                sequenceRepository.save(sqn);
            }
        }
        return nextOrderIdSequence;
    }

    public String getISTFormattedDateAndTime(String timestamp){
        Instant instant = Instant.parse(timestamp);

        // Convert to LocalDateTime in the system's default time zone
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy hh:mm:ss a");

        // Format the LocalDateTime
        return localDateTime.format(formatter);
    }

    public ByteArrayDataSource createPDF(String htmlContent)
    {
        ByteArrayOutputStream object = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(object);

        ByteArrayDataSource pdfDataSource = new ByteArrayDataSource(object.toByteArray(), "application/pdf");
         return pdfDataSource;
    }
}

package com.library.util;

import com.library.model.Sequence;
import com.library.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}

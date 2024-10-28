package com.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Sequence
{
    @Id
    String id;
    int sequence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}

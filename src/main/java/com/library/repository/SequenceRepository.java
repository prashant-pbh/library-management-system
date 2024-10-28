package com.library.repository;

import com.library.model.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence,String>
{

}

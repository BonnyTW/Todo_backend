package com.backend.Arch.Reppo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.Arch.Model.Task;

@Repository
public interface TaskReppo extends JpaRepository<Task,Integer>{
	
}

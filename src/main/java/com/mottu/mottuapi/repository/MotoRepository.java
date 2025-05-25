package com.mottu.mottuapi.repository;

import com.mottu.mottuapi.entity.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoRepository extends JpaRepository<Moto, Long> {

	void deleteById(Long id);
}

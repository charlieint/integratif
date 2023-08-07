package com.charlie.penyewa.repository;

import com.charlie.penyewa.entity.PenyewaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenyewaRepository extends JpaRepository<PenyewaEntity, Long> {
    PenyewaEntity findByNIKPenyewa(String NIKPenyewa);
    List<PenyewaEntity> findByStatusHapus(boolean statusHapus);
    List<PenyewaEntity> findByStatusSedangSewa(boolean statusSedangSewa);
}

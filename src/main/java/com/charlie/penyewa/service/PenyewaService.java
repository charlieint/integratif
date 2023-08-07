package com.charlie.penyewa.service;

import com.charlie.penyewa.DTO.PenyewaDTO;
import com.charlie.penyewa.entity.PenyewaEntity;

import java.util.List;

public interface PenyewaService {
    PenyewaDTO saveOrUpdatePenyewa(PenyewaDTO penyewaDTO);
    List<PenyewaDTO> getAllPenyewa();
    List<PenyewaDTO> getAllActivePenyewa();
    List<PenyewaDTO> getAllSedangSewa();
    void updateStatusHapus(Long penyewaId, boolean statusHapus);
    void updateStatusSedangSewa(Long penyewaId, boolean statusSedangSewa);
    void deletePenyewa(Long penyewaId);
    PenyewaDTO findPenyewaById(Long idPenyewa);
}
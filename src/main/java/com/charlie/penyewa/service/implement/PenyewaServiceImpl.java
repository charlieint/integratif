package com.charlie.penyewa.service.implement;

import com.charlie.penyewa.DTO.PenyewaDTO;
import com.charlie.penyewa.entity.PenyewaEntity;
import com.charlie.penyewa.repository.PenyewaRepository;
import com.charlie.penyewa.service.PenyewaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PenyewaServiceImpl implements PenyewaService {
    private final PenyewaRepository penyewaRepository;

    @Autowired
    public PenyewaServiceImpl(PenyewaRepository penyewaRepository) {
        this.penyewaRepository = penyewaRepository;
    }

    // Metode untuk mengubah PenyewaDTO menjadi PenyewaEntity
    private PenyewaEntity mapToEntity(PenyewaDTO penyewaDTO) {
        PenyewaEntity penyewa = new PenyewaEntity();
        penyewa.setIdPenyewa(penyewaDTO.getIdPenyewa());
        penyewa.setNamaPenyewa(penyewaDTO.getNamaPenyewa());
        penyewa.setNIKPenyewa(penyewaDTO.getNIKPenyewa());
        penyewa.setNoTlpnPenyewa(penyewaDTO.getNoTlpnPenyewa());
        penyewa.setAlamatPenyewa(penyewaDTO.getAlamatPenyewa());
        penyewa.setStatusSedangSewa(penyewaDTO.isStatusSedangSewa());
        penyewa.setStatusHapus(penyewaDTO.isStatusHapus());

        return penyewa;
    }

    // Metode untuk mengubah PenyewaEntity menjadi PenyewaDTO
    private PenyewaDTO mapToDTO(PenyewaEntity penyewaEntity) {
        PenyewaDTO penyewaDTO = new PenyewaDTO();
        penyewaDTO.setIdPenyewa(penyewaEntity.getIdPenyewa());
        penyewaDTO.setNamaPenyewa(penyewaEntity.getNamaPenyewa());
        penyewaDTO.setNIKPenyewa(penyewaEntity.getNIKPenyewa());
        penyewaDTO.setNoTlpnPenyewa(penyewaEntity.getNoTlpnPenyewa());
        penyewaDTO.setAlamatPenyewa(penyewaEntity.getAlamatPenyewa());
        penyewaDTO.setStatusSedangSewa(penyewaEntity.isStatusSedangSewa());
        penyewaDTO.setStatusHapus(penyewaEntity.isStatusHapus());

        return penyewaDTO;
    }

    @Override
    public PenyewaDTO saveOrUpdatePenyewa(PenyewaDTO penyewaDTO) {
        PenyewaEntity existingPenyewa = penyewaRepository.findByNIKPenyewa(penyewaDTO.getNIKPenyewa());

        if (existingPenyewa != null) {
            existingPenyewa.setNamaPenyewa(penyewaDTO.getNamaPenyewa());
            existingPenyewa.setNoTlpnPenyewa(penyewaDTO.getNoTlpnPenyewa());
            existingPenyewa.setAlamatPenyewa(penyewaDTO.getAlamatPenyewa());
            return mapToDTO(penyewaRepository.save(existingPenyewa));
        } else {
            PenyewaEntity penyewa = mapToEntity(penyewaDTO);
            return mapToDTO(penyewaRepository.saveAndFlush(penyewa));
        }
    }

    @Override
    public List<PenyewaDTO> getAllPenyewa() {
        List<PenyewaEntity> penyewaEntities = penyewaRepository.findAll();
        return penyewaEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PenyewaDTO> getAllSedangSewa() {
        List<PenyewaEntity> penyewaEntities = penyewaRepository.findByStatusSedangSewa(true);
        return penyewaEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PenyewaDTO> getAllActivePenyewa() {
        List<PenyewaEntity> penyewaEntities = penyewaRepository.findByStatusHapus(false);
        return penyewaEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatusHapus(Long penyewaId, boolean statusHapus) {
        PenyewaEntity penyewa = penyewaRepository.findById(penyewaId)
                .orElseThrow(() -> new RuntimeException("Penyewa not found with id: " + penyewaId));

        penyewa.setStatusHapus(statusHapus);
        penyewaRepository.save(penyewa);
    }

    @Override
    public void updateStatusSedangSewa(Long penyewaId, boolean statusSedangSewa) {
        PenyewaEntity penyewa = penyewaRepository.findById(penyewaId)
                .orElseThrow(() -> new RuntimeException("Penyewa not found with id: " + penyewaId));

        penyewa.setStatusSedangSewa(statusSedangSewa);
        penyewaRepository.save(penyewa);
    }

    @Override
    public void deletePenyewa(Long penyewaId) {
        penyewaRepository.deleteById(penyewaId);
    }

    @Override
    public PenyewaDTO findPenyewaById(Long idPenyewa) {
        PenyewaEntity penyewaEntity = penyewaRepository.findById(idPenyewa)
                .orElseThrow(() -> new EntityNotFoundException("Penyewa not found"));
        return mapToDTO(penyewaEntity);
    }
}

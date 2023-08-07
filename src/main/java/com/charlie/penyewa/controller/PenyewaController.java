package com.charlie.penyewa.controller;

import com.charlie.penyewa.DTO.PenyewaDTO;
import com.charlie.penyewa.entity.PenyewaEntity;
import com.charlie.penyewa.service.PenyewaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/penyewa")
public class PenyewaController {
    private final PenyewaService penyewaService;

    @Autowired
    public PenyewaController(PenyewaService penyewaService) {
        this.penyewaService = penyewaService;
    }

    @PostMapping("/create")
    public ResponseEntity<PenyewaDTO> saveOrUpdatePenyewa(@RequestBody PenyewaDTO penyewaDTO) {
        PenyewaDTO savedPenyewa = penyewaService.saveOrUpdatePenyewa(penyewaDTO);
        return new ResponseEntity<>(savedPenyewa, HttpStatus.CREATED);
    }

    @GetMapping("/super-all")
    public ResponseEntity<List<PenyewaDTO>> getAllPenyewa() {
        List<PenyewaDTO> allPenyewaList = penyewaService.getAllPenyewa();
        return new ResponseEntity<>(allPenyewaList, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PenyewaDTO>> getAllActivePenyewa() {
        List<PenyewaDTO> penyewaList = penyewaService.getAllActivePenyewa();
        return new ResponseEntity<>(penyewaList, HttpStatus.OK);
    }

    @GetMapping("/sedang-sewa")
    public ResponseEntity<List<PenyewaDTO>> getAllSedangSewa() {
        List<PenyewaDTO> activePenyewaList = penyewaService.getAllSedangSewa();
        return new ResponseEntity<>(activePenyewaList, HttpStatus.OK);
    }

    @PutMapping("/hapus/{penyewaId}/{statusHapus}")
    public ResponseEntity<Void> updateStatusHapus(@PathVariable Long penyewaId,
                                                  @PathVariable boolean statusHapus) {
        penyewaService.updateStatusHapus(penyewaId, statusHapus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/sedang-sewa/{penyewaId}/{statusSedangSewa}")
    public ResponseEntity<Void> updateStatusSedangSewa(@PathVariable Long penyewaId,
                                                       @PathVariable boolean statusSedangSewa) {
        penyewaService.updateStatusSedangSewa(penyewaId, statusSedangSewa);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/hapus-permanen/{penyewaId}")
    public ResponseEntity<Void> deletePenyewa(@PathVariable Long penyewaId) {
        penyewaService.deletePenyewa(penyewaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find/{idPenyewa}")
    public ResponseEntity<PenyewaDTO> findPenyewaById(@PathVariable Long idPenyewa) {
        PenyewaDTO penyewaDTO = penyewaService.findPenyewaById(idPenyewa);
        return ResponseEntity.ok(penyewaDTO);
    }
}

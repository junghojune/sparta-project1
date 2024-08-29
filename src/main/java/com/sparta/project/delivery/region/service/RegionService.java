package com.sparta.project.delivery.region.service;import com.sparta.project.delivery.region.dto.RegionDto;import com.sparta.project.delivery.region.entity.Region;import com.sparta.project.delivery.region.repository.RegionRepository;import jakarta.persistence.EntityExistsException;import jakarta.persistence.EntityNotFoundException;import jakarta.transaction.Transactional;import lombok.RequiredArgsConstructor;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;import org.springframework.stereotype.Service;import java.util.Optional;@RequiredArgsConstructor@Servicepublic class RegionService {    private final RegionRepository regionRepository;    public Page<RegionDto> getRegion(Pageable pageable) {        return regionRepository.findAll(pageable).map(RegionDto::from);    }    @Transactional    public void addRegion(RegionDto dto) {        if (dto.city() == null) {            throw new IllegalArgumentException("City cannot be null");        }        if (regionRepository.findByCityAndSiGunAndGuAndVillage(dto.city(), dto.siGun(), dto.gu(), dto.village()).isPresent()) {            throw new EntityExistsException("Region already exists");        }        regionRepository.save(dto.toEntity());    }    @Transactional    public Optional<RegionDto> updateRegion(String regionId, RegionDto dto) {        if (dto.city() == null && dto.siGun() == null && dto.gu() == null && dto.village() == null) {            throw new IllegalArgumentException("region info cannot be empty");        }        if (regionRepository.findByCityAndSiGunAndGuAndVillage(dto.city(), dto.siGun(), dto.gu(), dto.village()).isPresent()) {            throw new EntityExistsException("Region already exists");        }        Region region = regionRepository.findById(regionId)                .orElseThrow(() -> new EntityNotFoundException("region is not exist"));        if (dto.city() != null && !dto.city().equals(region.getCity())) {            region.setCity(dto.city());        }        if (dto.siGun() != null && !dto.siGun().equals(region.getSiGun())) {            region.setSiGun(dto.siGun());        }        if (dto.gu() != null && !dto.gu().equals(region.getGu())) {            region.setGu(dto.gu());        }        if (dto.village() != null && !dto.village().equals(region.getVillage())) {            region.setVillage(dto.village());        }        return regionRepository.findById(regionId).map(RegionDto::from);    }    @Transactional    public void deleteRegion(String regionId) {        if (regionRepository.findById(regionId).isEmpty()) {            throw new EntityNotFoundException("Region does not exist");        }        regionRepository.deleteById(regionId);    }}
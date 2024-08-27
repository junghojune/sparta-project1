package com.sparta.project.delivery.region.repository;import com.sparta.project.delivery.common.type.City;import com.sparta.project.delivery.region.entity.Region;import org.springframework.data.jpa.repository.JpaRepository;import java.util.Optional;public interface RegionRepository extends JpaRepository<Region, String> {    Optional<Region> findByCityAndSiGunAndGuAndVillage(City city, String siGun, String gu, String village);}
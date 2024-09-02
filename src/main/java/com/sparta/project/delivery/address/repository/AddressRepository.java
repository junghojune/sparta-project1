package com.sparta.project.delivery.address.repository;

import com.sparta.project.delivery.address.entity.Address;
import com.sparta.project.delivery.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {
    Page<Address> findAllByUser(User user, Pageable pageable);

    Optional<Address> findByAddressAndUser(String address, User user);

    Optional<Address> findByAddressIdAndUser(String address_id, User user);
}

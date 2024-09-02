package com.sparta.project.delivery.address.service;

import com.sparta.project.delivery.address.dto.AddressDto;
import com.sparta.project.delivery.address.dto.request.UpdateAddress;
import com.sparta.project.delivery.address.dto.response.AddressResponse;
import com.sparta.project.delivery.address.entity.Address;
import com.sparta.project.delivery.address.repository.AddressRepository;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.common.response.CommonResponse;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.sparta.project.delivery.common.exception.DeliveryError.*;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public CommonResponse<Void> create(User user, AddressDto dto) {
        // 중복 체크
        checkDuplicateAddress(dto,user);

        // repository에 저장
        addressRepository.save(dto.toEntity(user));

        // 등록된 주소지 가져오기
        Optional<Address> address = addressRepository.findByAddressAndUser(dto.address(), user);

        return CommonResponse.success(address.get().getAddressId() + " 주소지가 등록되었습니다.");
    }

//    public Page<AddressDto> getAll(User user, Pageable pageable) {
//        return addressRepository.findAllByUser(user, pageable)
//                .map(AddressDto::from);
//    }

    @Transactional
    public CommonResponse<AddressResponse> update(String addressId, AddressDto dto, User user) {
        // repository에서 해당 주소 가져오기
        Address address  = addressRepository.findById(addressId).orElseThrow(
                () -> new CustomException(ADDRESS_NOT_FOUND)
        );

        // 주인 확인, 중복 확인
        checkAddressOwner(address,user);
        checkDuplicateAddress(dto,user);

        // dto, entity 업데이트
        address.setAddress(dto.address());

        // entity에서 값 꺼내온 뒤 dto 변환, 리턴
        return CommonResponse.success(AddressResponse.from(dto));
    }

    @Transactional
    public CommonResponse<Void> delete(String addressId, User user) {
        // repository에서 해당 주소 가져오기
        Address address  = addressRepository.findById(addressId).orElseThrow(
                () ->  new CustomException(ADDRESS_NOT_FOUND)
        );

        // 주소지 주인 확인
        checkAddressOwner(address,user);

        // dto, entity 업데이트
        address.deleteAddress(LocalDateTime.now(),user.getEmail());

        return CommonResponse.success("주소지를 삭제했습니다.");
    }

    // 주소지 주인 = 수행하려는 사람인지 확인
    // MANAGER, MASTER가 아닐 경우 주소지를 작성한 사용자만 주소지 수정, 삭제 가능
    private void checkAddressOwner(Address address, User user){
        if( !(user.getRole() == UserRoleEnum.MANAGER || user.getRole() == UserRoleEnum.MASTER)
                && !address.getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }
    }

    // 한 사용자가 중복된 주소를 등록하진 않았는지 중복 체크
    private void checkDuplicateAddress (AddressDto dto, User user){
        Optional<Address> CheckAddress = addressRepository.findByAddressAndUser(dto.address(), user);
        if (CheckAddress.isPresent() && CheckAddress.get().getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(ADDRESS_ALREADY_EXISTS);
        }
    }
}

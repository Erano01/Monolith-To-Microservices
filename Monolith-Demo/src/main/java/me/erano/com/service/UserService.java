package me.erano.com.service;

import me.erano.com.dto.AddressDto;
import me.erano.com.dto.UserRequest;
import me.erano.com.dto.UserResponse;
import me.erano.com.model.Address;
import me.erano.com.repository.UserRepository;
import me.erano.com.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<UserResponse> fetchAllUsers(){
        return userRepository
                .findAll()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
    public void addUser(UserRequest userRequest){
        userRepository.save(updateUser(new User(),userRequest));
    }
    public Optional<UserResponse> getUserById(Long userId){
        return userRepository
                .findById(userId)
                .map(this::mapToUserResponse);
    }
    public boolean updateUser(Long id, UserRequest updateUserRequest){
        return userRepository.findById(id)
                .map(existingUser -> {
                    userRepository.save(updateUser(existingUser,updateUserRequest));
                    return true;
                })
                .orElse(false);
    }

    //private methods

    private UserResponse mapToUserResponse(User user){
        return new UserResponse(
                String.valueOf(user.getId()),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                mapToAddressDto(user.getAddress())
        );
    }
    private AddressDto mapToAddressDto(Address address){
        return new AddressDto(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry()
        );
    }
    private User updateUser(User user,UserRequest userRequest){
        User updatedUser = user;
        updatedUser.setFirstName(userRequest.getFirstName());
        updatedUser.setLastName(userRequest.getLastName());
        updatedUser.setEmail(userRequest.getEmail());
        updatedUser.setPhone(userRequest.getPhone());
        updatedUser.setAddress(updateAddress(user.getAddress(),userRequest.getAddressDto()));
        return updatedUser;
    }
    private Address updateAddress(Address address,AddressDto addressDto){
        Address updatedAddress = address;
        updatedAddress.setStreet(addressDto.getStreet());
        updatedAddress.setCity(addressDto.getCity());
        updatedAddress.setState(addressDto.getState());
        updatedAddress.setZipCode(addressDto.getZipCode());
        updatedAddress.setCountry(addressDto.getCountry());
        return updatedAddress;
    }

}

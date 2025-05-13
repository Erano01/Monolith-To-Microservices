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
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }
    public Optional<UserResponse> getUserById(Long userId){
        return userRepository
                .findById(userId)
                .map(this::mapToUserResponse);
    }
    public boolean updateUser(Long id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    //private methods

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddressDto() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddressDto().getStreet());
            address.setState(userRequest.getAddressDto().getState());
            address.setZipCode(userRequest.getAddressDto().getZipCode());
            address.setCity(userRequest.getAddressDto().getCity());
            address.setCountry(userRequest.getAddressDto().getCountry());
            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDto addressDTO = new AddressDto();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipCode(user.getAddress().getZipCode());
            response.setAddressDto(addressDTO);
        }
        return response;
    }

}

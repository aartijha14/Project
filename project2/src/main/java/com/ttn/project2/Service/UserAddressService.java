package com.ttn.project2.Service;

import com.ttn.project2.ExceptionHandler.ResourceNotFoundException;
import com.ttn.project2.Model.User;
import com.ttn.project2.Model.UserAddress;
import com.ttn.project2.repository.UserAddressRepository;
import com.ttn.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserAddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAddressRepository userAddressRepository;


    public void save(UserAddress useradd) {
        userAddressRepository.save(useradd);
    }


    //View Address
    public List<UserAddress> viewAddress(@PathVariable long id) throws ResourceNotFoundException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("userId : " + id + " is not found"));

        return this.userAddressRepository.findAll();
    }


    //Add Address
    public String addAddress(UserAddress userAddress) {

        // Optional<User> user1= userRepository.findById(user.getId());
        // User user2=user1.get();

        //if(user2!=null)
        //{
        // user.setAddress(user.getAddress());

        Optional<UserAddress> userAddress1 = userAddressRepository.findById(userAddress.getId());

        if (userAddress1.isPresent()) {

            //userAddress.setAddress_line(userAddress.getAddress_line());

            userAddressRepository.save(userAddress);

            return "Address Added Successfully ! ";
        } else {
            return "User not found, Address can not be added ! ";
        }
    }

    //Delete Address
    public String deleteAddress(@PathVariable long id) {

        Optional<UserAddress> userAddress = userAddressRepository.findById(id);
        if (userAddress.isPresent()) {

            userAddressRepository.deleteById(id);
            return "Address Deleted Successfully ! ";
        } else {
            return "Address Id " + id + " not found ! ";
        }
    }
}
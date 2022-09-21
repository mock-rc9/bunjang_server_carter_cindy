package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.src.address.model.PatchAddressReq;
import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional
public class AddressService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AddressDao addressDao;
    private final AddressProvider addressProvider;

    @Autowired
    public AddressService(AddressDao addressDao, AddressProvider addressProvider){
        this.addressDao = addressDao;
        this.addressProvider = addressProvider;
    }

    public PostAddressRes createAddress(int userIdx, PostAddressReq postAddressReq) throws BaseException {
        try{
            int addressId = addressDao.createAddress(userIdx, postAddressReq);
            return new PostAddressRes(addressId);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

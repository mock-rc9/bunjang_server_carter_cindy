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

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class AddressService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AddressDao addressDao;

    @Autowired
    public AddressService(AddressDao addressDao){
        this.addressDao = addressDao;
    }

    public PostAddressRes createAddress(int userIdx, PostAddressReq postAddressReq) throws BaseException {
        try{
            int addressId = addressDao.createAddress(userIdx, postAddressReq);
            return new PostAddressRes(addressId);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyAddress(int addressIdx, PatchAddressReq patchAddressReq) throws BaseException {
        try{
            int result = addressDao.modifyAddress(addressIdx, patchAddressReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_ADDRESS);
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteAddress(int addressIdx) throws BaseException {
//        try {
            int result = addressDao.deleteAddress(addressIdx);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_ADDRESS);
            }
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
    }


}

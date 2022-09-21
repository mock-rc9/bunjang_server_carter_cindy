package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.src.address.model.GetAddressRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
public class AddressProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AddressDao addressDao;

    @Autowired
    public AddressProvider(AddressDao addressDao){
        this.addressDao = addressDao;
    }

    public List<GetAddressRes> getAddresses(int userIdx) throws BaseException {
        try{
            List<GetAddressRes> getAddressRes = addressDao.getAddresses(userIdx);
            return getAddressRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

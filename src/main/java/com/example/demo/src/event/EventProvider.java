package com.example.demo.src.event;

import com.example.demo.config.BaseException;
import com.example.demo.src.event.model.GetEventRes;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.GET_EVENT_EMPTY;

@Service
public class EventProvider {

    private final EventDao eventDao;

    public EventProvider(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public GetEventRes getevent(int eventIdx) throws BaseException {
        if(eventDao.checkEventExist(eventIdx)==0){
            throw new BaseException(GET_EVENT_EMPTY);
        }
        try {
            GetEventRes getEventRes = eventDao.getEvent(eventIdx);
            return getEventRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }




    public List<GetEventRes> getevents() throws BaseException {
        try {


            List<GetEventRes> getEventRes = eventDao.getEvents();
            return getEventRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEventExits(int eventIdx) throws BaseException {
        try{
            return eventDao.checkEventExist(eventIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}

package com.example.demo.src.event;

import com.example.demo.config.BaseException;
import com.example.demo.src.event.model.GetEventRes;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class EventProvider {

    private final EventDao eventDao;

    public EventProvider(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public GetEventRes getevent(int eventIdx) throws BaseException {
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
}

package com.example.demo.src.event;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.event.model.GetEventRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final EventProvider eventProvider;

    @Autowired
    private final EventService eventService;

    public EventController(EventProvider eventProvider, EventService eventService) {
        this.eventProvider = eventProvider;
        this.eventService = eventService;
    }
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetEventRes>> getEvents(){

        try {
            List<GetEventRes> getEventRes = eventProvider.getevents();
            return new BaseResponse<>(getEventRes);
        }catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{eventIdx}")
    public BaseResponse <GetEventRes> getEvent(@PathVariable("eventIdx") int eventIdx){

        try {
            GetEventRes getEventRes = eventProvider.getevent(eventIdx);
            return new BaseResponse<>(getEventRes);
        }catch (BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

package com.deengames.dungeonsofthesultanate.turnservice.stuff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceThing {

    @Autowired
    private InnerClassThing innerClassThing;

    public void goGoGo() throws InterruptedException {
        innerClassThing.go();
    }
}

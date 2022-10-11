package com.deengames.dungeonsofthesultanate.web.maps;

import com.deengames.dungeonsofthesultanate.web.BaseController;
import com.deengames.dungeonsofthesultanate.web.security.client.ServiceToServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class BaseMapController extends BaseController {

    @Autowired
    private ServiceToServiceClient s2sClient;

    @Autowired
    private Environment environment;

    public int getNumTurns()
    {
        var user = this.getCurrentUser();
        String url = String.format("%s/turns?userId=%s", environment.getProperty("dots.serviceToService.turnService"), user.getId());
        var result = s2sClient.get(url, Integer.class);
        return result;
    }
}

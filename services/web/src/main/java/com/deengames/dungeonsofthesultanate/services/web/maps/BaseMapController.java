package com.deengames.dungeonsofthesultanate.services.web.maps;

import com.deengames.dungeonsofthesultanate.services.web.BaseController;
import com.deengames.dungeonsofthesultanate.services.web.security.client.ServiceToServiceClient;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMapController extends BaseController {

    @Autowired
    private ServiceToServiceClient s2sClient;

    public int getNumTurns()
    {
        var user = this.getCurrentUser();
        // TODO: make these DRY. Also, they should be across HTTPS, not HTTP.
        var result = s2sClient.post("http://localhost:8081/player", user.getId(), Integer.class);
        return result;
    }
}

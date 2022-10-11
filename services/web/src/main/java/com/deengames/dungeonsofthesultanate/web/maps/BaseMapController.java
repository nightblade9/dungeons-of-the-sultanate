package com.deengames.dungeonsofthesultanate.web.maps;

import com.deengames.dungeonsofthesultanate.web.BaseController;
import com.deengames.dungeonsofthesultanate.web.security.client.ServiceToServiceClient;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseMapController extends BaseController {

    @Autowired
    private ServiceToServiceClient s2sClient;

    public int getNumTurns()
    {
        var user = this.getCurrentUser();
        // TODO: make these DRY. Also, they should be across HTTPS, not HTTP.
        String url = String.format("http://localhost:8081/player?userId=%s", user.getId());
        var result = s2sClient.get(url, Integer.class);
        return result;
    }
}

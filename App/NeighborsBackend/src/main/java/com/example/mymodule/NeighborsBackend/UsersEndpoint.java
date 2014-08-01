package com.example.mymodule.NeighborsBackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * Created by demouser on 01/08/2014.
 */

@Api(name = "users_retrival", version = "v1", namespace = @ApiNamespace(ownerDomain = "NeighborsBackend.mymodule.example.com", ownerName = "NeighborsBackend.mymodule.example.com", packagePath=""))
public class UsersEndpoint {
    private static final Logger log = Logger.getLogger(UsersEndpoint.class.getName());
    @ApiMethod(name = "get_users")
    public void getUsers(@Named("userId") String userId) {

    }
}

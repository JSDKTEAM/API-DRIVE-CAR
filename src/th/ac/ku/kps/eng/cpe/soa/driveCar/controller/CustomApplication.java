package th.ac.ku.kps.eng.cpe.soa.driveCar.controller;

import org.glassfish.jersey.server.ResourceConfig;

import th.ac.ku.kps.eng.cpe.soa.driveCar.filter.AuthFilter;

public class CustomApplication extends ResourceConfig {
	
	public CustomApplication()
    {
        packages("com.howtodoinjava.jersey"); 
        //Register Auth Filter here
        register(AuthFilter.class);
    }
}

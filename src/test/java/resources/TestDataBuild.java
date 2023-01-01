package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {
	
	public AddPlace addPlacePayload(String name, String address, String language) {
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress(address);
		p.setLanguage(language);
		p.setName(name);
		p.setPhone_number("9963320668");
		p.setWebsite("www.google.com");
		
		List<String> myList = new ArrayList<String>();
		myList.add("Shoe park");
		myList.add("Shop");
		p.setTypes(myList);
		
		Location l = new Location();
		l.setLat(-38.383452);
		l.setLng(56.48567);
		p.setLocation(l);
		return p;
	}
	
	public String deletePlacePayload(String place_id) {
		return "{\r\n    \"place_id\":\""+place_id+"\"\r\n}";
	}

}

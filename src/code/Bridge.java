package code;

import java.net.*;
import org.json.*;

public class Bridge {
	
	private URL url;
	private ColorSpaceConverter converter;
	
	public Bridge(String ip, String username) {
		url = null;
		try {
			url = new URL("http://" + ip + "/api/" + username + "/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		converter = new ColorSpaceConverter();
	}
	
	public boolean isOn(int light) {
		boolean retVal = false;
		try {
			URL path = new URL(url.toString() + "lights/" + light + "/");
			JSONObject json = new JSONObject(CLIPUtilities.getHTML(path));
			retVal = json.getJSONObject("state").getBoolean("on");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public void setOn(int light, boolean status) {
		JSONObject content = new JSONObject();
		URL path = null;
		try {
			path = new URL(url.toString() + "lights/" + light + "/state/");
			content.put("on", status);
			CLIPUtilities.putHTML(path, content);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void toggleOnOff(int light) {
		setOn(light, !isOn(light));
	}
	
	public void setHue(int light, int hue) {
		JSONObject content = new JSONObject();
		URL path = null;
		try {
			path = new URL(url.toString() + "lights/" + light + "/state/");
			content.put("colormode","hs");
			content.put("hue", hue);
			CLIPUtilities.putHTML(path, content);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setColorRGB(int light, int r, int g, int b) {
		setColorMode(light,"xy");
		JSONObject content = new JSONObject();
		URL path = null;
		try {
			path = new URL(url.toString() + "lights/" + light + "/state/");
			JSONArray xy = new JSONArray();
			double[] vals = converter.XYZtoxyY(converter.RGBtoXYZ(r, g, b));
			xy.put(vals[0]);
			xy.put(vals[1]);
			content.put("colormode","xy");
			content.put("xy", xy);
			CLIPUtilities.putHTML(path, content);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disco(int light, int loops) {
		for(int i=0;i<loops;i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setColorRGB(3,255,0,0);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setColorRGB(3,0,255,0);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setColorRGB(3,0,0,255);
		}
	}
	
	public void blinkMode(int light, int blinks, int defCon) {
		setOn(light,false);
		for(int i=0;i<blinks;i++) {
			try {
				Thread.sleep(500*defCon);
				toggleOnOff(light);
				Thread.sleep(1000);
				toggleOnOff(light);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void setColorMode(int light, String mode) {
		JSONObject content = new JSONObject();
		URL path = null;
		try {
			path = new URL(url.toString() + "lights/" + light + "/state/");
			content.put("colormode", mode);
			CLIPUtilities.putHTML(path, content);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
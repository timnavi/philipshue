package code;

public class Test {

	public static void main(String[] args) {
		String ip = "10.2.7.100";
		String un = "F2tBlLHlVrega0C3dkIW57ZOuTEhjwXkvjdiIGfs";
		Bridge bridge = new Bridge(ip,un);
		bridge.toggleOnOff(3);
	}

}


public class Coordinates {

	private double latitude;
	private double longitude;
	
	public Coordinates(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return this.latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	@Override
	public String toString() {
		String str = this.latitude + ";" + this.longitude;
		return str;
	}
}

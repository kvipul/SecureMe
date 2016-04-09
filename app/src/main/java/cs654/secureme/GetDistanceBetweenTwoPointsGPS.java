package cs654.secureme;

/**
 * Created by sunil on 9/4/16.
 */
public class GetDistanceBetweenTwoPointsGPS {

    public double getDist(double lat1, double long1, double lat2, double long2){
        double tempLat, tempLong;
        tempLat = toRad(lat2 - lat1);
        tempLong = toRad(long2 - long1);

        double x = Math.sin(tempLat / 2) * Math.sin(tempLat / 2) + Math.sin(tempLong / 2) * Math.sin(tempLong / 2) * Math.cos(toRad(lat1)) * Math.cos(toRad(lat2));
        double y = 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1 - x));
        double xy = 6371000 * y;
        return xy;
    }


    public double toRad(double x) {
        double res = x * Math.PI / 180;
        return res;
    }
}

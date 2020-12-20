package api;

import java.util.Arrays;
/**
 * This class implements geo_location interface represents a geo location <x,y,z>, aka Point3D
 */

public class Geo implements geo_location {
   private double x,y,z;

    //constructor
    public Geo(){

    }
    public Geo(double _x, double _y, double _z) {
        this.x = _x;
        this.y = _y;
        this.z= _z;

    }
    /**
     * copy constructor
     */
    public Geo(geo_location p) {
        this.x = p.x();
        this.y = p.y();
        this.z = p.z();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geo geo = (Geo) o;

        if (Double.compare(geo.x, x) != 0) return false;
        if (Double.compare(geo.y, y) != 0) return false;
        return Double.compare(geo.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    /**
     * return the x value of this point
     * @return
     */
    @Override
    public double x() {
        return this.x;
    }
    /**
     * return the y value of this point
     * @return
     */

    @Override
    public double y() {
        return this.y;
    }
    /**
     * return the z value of this point
     * @return
     */

    @Override
    public double z() {
        return this.z;
    }
    /**
     * return the distance from this point to other point
     * @param g
     * @return
     */
    @Override
    public double distance(geo_location g) {
        double pow_point = Math.pow((this.x - g.x()), 2) + Math.pow((this.y - g.y()), 2) + Math.pow((this.z() - g.z()), 2);
        pow_point = Math.sqrt(pow_point);
        return pow_point;
    }

    @Override
    public String toString() {
        return "location{" +
                "X=" + x +
                ", Y=" + y +
                ", Z=" + z +
                '}';
    }
}

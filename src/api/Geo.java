package api;

public class Geo implements geo_location {
   private double X=0, Y=0, Z=0;

    //constructor
    public Geo(){

    }
    public Geo(double _x, double _y, double _z) {
        this.X = _x;
        this.Y = _y;
        this.Z = _z;

    }

    //copy constructor
    public Geo(geo_location p) {
        this.X = p.x();
        this.Y = p.y();
        this.Z = p.z();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geo geo = (Geo) o;

        if (Double.compare(geo.X, X) != 0) return false;
        if (Double.compare(geo.Y, Y) != 0) return false;
        return Double.compare(geo.Z, Z) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(X);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(Y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(Z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public double x() {
        return this.X;
    }

    @Override
    public double y() {
        return this.Y;
    }

    @Override
    public double z() {
        return this.Z;
    }

    @Override
    public double distance(geo_location g) {
        double pow_point = Math.pow((this.X - g.x()), 2) + Math.pow((this.Y - g.y()), 2) + Math.pow((this.Z - g.z()), 2);
        pow_point = Math.sqrt(pow_point);
        return pow_point;
    }

    @Override
    public String toString() {
        return "location{" +
                "X=" + X +
                ", Y=" + Y +
                ", Z=" + Z +
                '}';
    }
}

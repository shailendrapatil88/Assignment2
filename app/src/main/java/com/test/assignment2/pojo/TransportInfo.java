package com.test.assignment2.pojo;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Shailendra on 3/8/2016.
 */
public class TransportInfo {
    private String id;
    private String name;
    private Mode fromcentral;
    private TransportLocation location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mode getFromCentral() {
        return fromcentral;
    }

    public void setFromCentral(Mode fromcentral) {
        this.fromcentral = fromcentral;
    }

    public TransportLocation getTransportLocation() {
        return location;
    }

    public void setLocation(TransportLocation location) {
        this.location = location;
    }

    public static class Mode {
        private String car;
        private String train;

        public Mode(String car, String train) {
            this.car = car;
            this.train = train;
        }

        public String getTrain() {
            return train;
        }

        public void setTrain(String train) {
            this.train = train;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }
    }

    public static class TransportLocation {
        private double latitude;
        private double longitude;

        public TransportLocation(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}

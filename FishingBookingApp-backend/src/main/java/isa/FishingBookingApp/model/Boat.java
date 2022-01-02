package isa.FishingBookingApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;

@Entity
public class Boat extends ReservationEntities {

    public Boat(){
        this.setType("boat");
    }

    public Boat(String name, Address address, String promotionalDescription, String rulesOfConduct, BoatOwner boatOwner,
                String boatType, double boatLength, int numberOfEngines, int enginePower, int maxSpeed, String navigationEquipment,
                String fishingEquipment, int capacity, String cancellationConditions, double price) {
        super(name, address, promotionalDescription, rulesOfConduct, "boat", price);
        this.boatOwner = boatOwner;
        this.boatType = boatType;
        this.boatLength = boatLength;
        this.numberOfEngines = numberOfEngines;
        this.enginePower = enginePower;
        this.maxSpeed = maxSpeed;
        this.navigationEquipment = navigationEquipment;
        this.fishingEquipment = fishingEquipment;
        this.capacity = capacity;
        this.cancellationConditions = cancellationConditions;
    }

    @ManyToOne
    private BoatOwner boatOwner;
    private String boatType;
    private double boatLength;
    private int numberOfEngines;
    private int enginePower;
    private int maxSpeed;
    private String navigationEquipment;
    private String fishingEquipment;
    private int capacity;
    private String cancellationConditions;

    public BoatOwner getBoatOwner() {
        return boatOwner;
    }

    public void setBoatOwner(BoatOwner boatOwner) {
        this.boatOwner = boatOwner;
    }

    public String getBoatType() {
        return boatType;
    }

    public void setBoatType(String boatType) {
        this.boatType = boatType;
    }

    public double getBoatLength() {
        return boatLength;
    }

    public void setBoatLength(double boatLength) {
        this.boatLength = boatLength;
    }

    public int getNumberOfEngines() {
        return numberOfEngines;
    }

    public void setNumberOfEngines(int numberOfEngines) {
        this.numberOfEngines = numberOfEngines;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getNavigationEquipment() {
        return navigationEquipment;
    }

    public void setNavigationEquipment(String navigationEquipment) {
        this.navigationEquipment = navigationEquipment;
    }

    public String getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(String fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCancellationConditions() {
        return cancellationConditions;
    }

    public void setCancellationConditions(String cancellationConditions) {
        this.cancellationConditions = cancellationConditions;
    }

}

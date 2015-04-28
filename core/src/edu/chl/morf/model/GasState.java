package edu.chl.morf.model;

public class GasState implements WaterState{

    @Override
    public String toString(){
        return "gas";
    }

    @Override
    public WaterState heat() {
        return null;
    }

    @Override
    public WaterState cool() {
        return new LiquidState();
    }
}

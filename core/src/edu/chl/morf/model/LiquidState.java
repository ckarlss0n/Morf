package edu.chl.morf.model;

public class LiquidState  implements WaterState{

    @Override
    public WaterState heat() {
        return new GasState();
    }

    @Override
    public WaterState cool() {
        return new SolidState();
    }
}

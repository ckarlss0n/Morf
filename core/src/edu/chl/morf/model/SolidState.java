package edu.chl.morf.model;

public class SolidState  implements WaterState{

    @Override
    public WaterState heat() {
        return new LiquidState();
    }

    @Override
    public WaterState cool() {
        return null;
    }
}

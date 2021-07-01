package com.example.qrapp.services;

public class ButtonAnim implements android.view.animation.Interpolator {


    private final float mCycles = 0.5f;

    @Override
    public float getInterpolation(final float input) {
        return (float) Math.sin(2.0f * mCycles * Math.PI * input);
    }
}

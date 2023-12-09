package com.openelements.benchscape.server.store.math;

import java.io.Serializable;

public enum InterpolationType implements Serializable {
    NONE,
    SPLINE,
    LOESS,
    NEVILLE,
    AKIMA,
    DIVIDED_DIFFERENCE,
    LINEAR;
}

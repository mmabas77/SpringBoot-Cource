package com.mmabas77.enums;

public enum PlansEnum {

    BASIC(1, "PLAN_BASIC"),
    PRO(2, "PLAN_PRO");//For Stripe We Set (2)

    private int id;
    private String planName;

    PlansEnum(int id, String planName) {
        this.id = id;
        this.planName = planName;
    }

    public int getId() {
        return id;
    }

    public String getPlanName() {
        return planName;
    }
}

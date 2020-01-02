package com.Brodski.restApi.model;


import lombok.ToString;

@ToString
public class FilterYt {
    public float minDuration;
    public float maxDuration;
    public String channelId;
}

package com.example.ninjaone.request;

import java.math.BigInteger;

public record ServiceRequest(Long id, String name, BigInteger cost){}

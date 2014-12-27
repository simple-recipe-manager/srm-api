package com.simplerecipemanager.health;

import com.codahale.metrics.health.HealthCheck;

public class ConnectionHealthCheck extends HealthCheck {

	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}

}

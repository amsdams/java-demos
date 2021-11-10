package com.amsdams.testconfigs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("a_dev")
 class DemoServiceIT {
	@Autowired
	DemoService demoService;
	
	@Autowired 
	DemoConfig demoConfig;
	
	@Test
	void getDisplayDiskUsageStatistics() {
		String displayDiskUsageStatistics= demoService.getDisplayDiskUsageStatistics(demoConfig.getPath());
		Assertions.assertTrue(!displayDiskUsageStatistics.isEmpty());
	}
	
	@Test
	void getDisplayFreeDiskSpace() {
		String displayFreeDiskSpace= demoService.getDisplayFreeDiskSpace(demoConfig.getPath());
		Assertions.assertTrue(!displayFreeDiskSpace.isEmpty());
	}
}

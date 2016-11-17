package com.vmware.photon.controller.api.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.vmware.photon.controller.api.model.Task;
import com.vmware.photon.controller.api.model.Task.Entity;
import com.vmware.photon.controller.api.model.VmCreateSpec;

public class VcClient {
	private static final Logger logger = LoggerFactory.getLogger(VcClient.class);
	private static volatile VcClient instance = null;
	
	public static synchronized VcClient getVcClient() {
		if (instance == null) {
			instance = new VcClient();
		}
		return instance;
	}

	public void createVmAsync(String projectId, VmCreateSpec composeVmCreateSpec, FutureCallback<Task> callback) {
		logger.info("Creating VM in project {} with specification {}", projectId, composeVmCreateSpec);
		
		Task vmCreateTask = new Task();
		String id = UUID.randomUUID().toString();
		vmCreateTask.setId(id);
		vmCreateTask.setState("COMPLETED");
		Entity vmEntity = new Entity();
		vmEntity.setId(id);
		vmCreateTask.setEntity(vmEntity);
		callback.onSuccess(vmCreateTask);
	}
}

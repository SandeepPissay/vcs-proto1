package com.vmware.photon.controller.api.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.vmware.photon.controller.api.model.NetworkConnection;
import com.vmware.photon.controller.api.model.Task;
import com.vmware.photon.controller.api.model.Task.Entity;
import com.vmware.photon.controller.api.model.VmCreateSpec;
import com.vmware.photon.controller.api.model.VmNetworks;
import com.vmware.vsphere.client.CommandArgument;
import com.vmware.vsphere.client.CommandExecutor;

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
		// TODO: Do the actual VM create
		Map<String, String> args = new HashMap<>();
		args.put(CommandArgument.VM_NAME, "cluster-manager-vm");
		CommandExecutor.createVm(args);

		Task vmCreateTask = new Task();
		String id = UUID.randomUUID().toString();
		vmCreateTask.setId(id);
		vmCreateTask.setState("COMPLETED");
		Entity vmEntity = new Entity();
		vmEntity.setId(id);
		vmCreateTask.setEntity(vmEntity);
		logger.info("Successfully created VM with Id {} in project {}", id, projectId);
		callback.onSuccess(vmCreateTask);
	}

	public Task uploadAndAttachIso(String vmId, String isoFile) {
		logger.info("Uploading and attaching isoFile {} to VM {}", isoFile, vmId);
		// TODO do the upload and attach ISO
		Task uploadAttachTask = new Task();
		String id = UUID.randomUUID().toString();
		uploadAttachTask.setId(id);
		uploadAttachTask.setState("COMPLETED");
		Entity vmEntity = new Entity();
		vmEntity.setId(vmId);
		uploadAttachTask.setEntity(vmEntity);
		logger.info("Successfully uploaded and attached isoFile {} to VM {}", isoFile, vmId);
		return uploadAttachTask;
	}

	public void performStartOperationAsync(String vmId, FutureCallback<Task> futureCallback) {
		logger.info("Starting VM {}", vmId);
		// TODO Start the VM
		
		Task startVm = new Task();
		String id = UUID.randomUUID().toString();
		startVm.setId(id);
		startVm.setState("COMPLETED");
		Entity vmEntity = new Entity();
		vmEntity.setId(vmId);
		startVm.setEntity(vmEntity);
		futureCallback.onSuccess(startVm);
		logger.info("Successfully started VM {}", vmId);
		
	}

	public void getNetworksAsync(String vmId, FutureCallback<Task> futureCallback) {
		logger.info("Getting network details for VM {}", vmId);
		Task networkTask = new Task();
		String id = UUID.randomUUID().toString();
		networkTask.setId(id);
		networkTask.setState("COMPLETED");
		Entity vmEntity = new Entity();
		vmEntity.setId(vmId);
		networkTask.setEntity(vmEntity);
		VmNetworks vmNetwork = new VmNetworks();
		Set<NetworkConnection> networkConnections = new HashSet<>();
		NetworkConnection netConn = new NetworkConnection("00:0c:29:xx:yy:zz");
		netConn.setIpAddress("10.10.10.10");
		networkConnections.add(netConn);
		vmNetwork.setNetworkConnections(networkConnections);
		networkTask.setResourceProperties(vmNetwork);
		futureCallback.onSuccess(networkTask);
		logger.info("Successfully got network details for VM {}", vmId);
		// TODO Auto-generated method stub
		
	}
}

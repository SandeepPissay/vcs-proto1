package com.vmware.vsphere.client;

import static com.vmware.vsphere.client.config.VcClientProperties.INSTANCE;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vmware.vim25.AlreadyExistsFaultMsg;
import com.vmware.vim25.DuplicateNameFaultMsg;
import com.vmware.vim25.FileFaultFaultMsg;
import com.vmware.vim25.InsufficientResourcesFaultFaultMsg;
import com.vmware.vim25.InvalidCollectorVersionFaultMsg;
import com.vmware.vim25.InvalidDatastoreFaultMsg;
import com.vmware.vim25.InvalidNameFaultMsg;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.InvalidStateFaultMsg;
import com.vmware.vim25.OutOfBoundsFaultMsg;
import com.vmware.vim25.RuntimeFaultFaultMsg;
import com.vmware.vim25.TaskInProgressFaultMsg;
import com.vmware.vim25.VmConfigFaultFaultMsg;
import com.vmware.vsphere.client.commands.VMCreate;

public class CommandExecutor {

	private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);
	public static Map<String, String> createVm(Map<String, String> args) {
		Map<String, String> output = new HashMap<>();
		VMCreate vmCreate = new VMCreate();
		vmCreate.setVirtualMachineName(args.get(CommandArgument.VM_NAME));
		vmCreate.setHostname(INSTANCE.getHostName());
		vmCreate.setDataCenterName(INSTANCE.getDatacenterName());
                try {
                	output.put(CommandOutput.VM_MOREF, vmCreate.createVirtualMachine());
		} catch (RemoteException | RuntimeFaultFaultMsg
				| InvalidPropertyFaultMsg | InvalidCollectorVersionFaultMsg
				| OutOfBoundsFaultMsg | DuplicateNameFaultMsg
				| VmConfigFaultFaultMsg | InsufficientResourcesFaultFaultMsg
				| AlreadyExistsFaultMsg | InvalidDatastoreFaultMsg
				| FileFaultFaultMsg | InvalidStateFaultMsg
				| InvalidNameFaultMsg | TaskInProgressFaultMsg e) {
			String message = "VM creation for parameters " + args + "failed.";
			logger.error(message, e);
			throw new RuntimeException(message, e);
		}
		return output;
	}
}
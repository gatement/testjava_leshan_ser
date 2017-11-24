package lgh.testjava.leshan.ser;

import java.util.Arrays;
import java.util.List;

import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.DiscoverRequest;
import org.eclipse.leshan.core.request.ExecuteRequest;
import org.eclipse.leshan.core.request.ReadRequest;
import org.eclipse.leshan.core.response.DiscoverResponse;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.registration.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeviceMgmtUtil {
	@Autowired
	private LeshanServer lwServer;

	public void discover(Registration reg, String path) {
		DiscoverRequest request = new DiscoverRequest(path);
		try {
			DiscoverResponse response = lwServer.send(reg, request, 3000);
			log.info("Discover " + path + " ============:");
			Arrays.stream(response.getObjectLinks()).forEach(link -> log.info(link.getUrl()));

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void read(Registration reg, List<String> paths) {
		for (String path : paths) {
			ReadRequest request = new ReadRequest(path);
			try {
				ReadResponse response = lwServer.send(reg, request, 3000);
				LwM2mResource resource = (LwM2mResource) response.getContent();
				log.info("Read {} ============: {}", path, resource.getValue().toString());
			} catch (

			InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void execute(Registration reg, String path) {
		ExecuteRequest request = new ExecuteRequest(path);
		try {
			ExecuteResponse response = lwServer.send(reg, request, 3000);
			if (response.isSuccess()) {
				log.info("Execute {} ============: success", path);
			} else {
				log.info("Execute {} ============: error, msg={}", path, response.getErrorMessage());
			}
		} catch (

		InterruptedException e) {
			e.printStackTrace();
		}
	}
}
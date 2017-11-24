package lgh.testjava.leshan.ser;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyRegistrationListener implements RegistrationListener {
	private DeviceMgmtUtil deviceMgmtUtil;
	private ScheduledExecutorService scheduledExecutorService;

	public MyRegistrationListener(DeviceMgmtUtil deviceMgmtUtil, ScheduledExecutorService scheduledExecutorService) {
		this.deviceMgmtUtil = deviceMgmtUtil;
		this.scheduledExecutorService = scheduledExecutorService;
	}

	@Override
	public void updated(RegistrationUpdate update, Registration updatedReg, Registration previousReg) {
		log.info("Registration updated");
	}

	@Override
	public void unregistered(Registration reg, Collection<Observation> observations, boolean expired,
			Registration newReg) {
		log.info("Registration updated");
	}

	@Override
	public void registered(Registration reg, Registration previousReg, Collection<Observation> previousObsersations) {
		log.info("New registered client with endpoint={}, id={}, addr={}, port={}", reg.getEndpoint(), reg.getId(),
				reg.getAddress().toString(), reg.getPort());
		Arrays.stream(reg.getSortedObjectLinks()).forEach(link -> log.info(link.getUrl()));

		/*
		// discover
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			deviceMgmtUtil.discover(reg, "/3/0");
		}, 2, 10, TimeUnit.SECONDS);
		// read
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			deviceMgmtUtil.read(reg, Arrays.asList("/3/0/0", "/3/0/1", "/3/0/2"));
		}, 3, 10, TimeUnit.SECONDS);
		// execute: reboot
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			deviceMgmtUtil.execute(reg, "/3/0/4");
		}, 4, 10, TimeUnit.SECONDS);
		*/
	}
}
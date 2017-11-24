package lgh.testjava.leshan.ser;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.californium.scandium.dtls.pskstore.InMemoryPskStore;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SerApp {

	public static void main(String[] args) {
		SpringApplication.run(SerApp.class, args);
	}

	@Bean
	public LeshanServer lwServer(MyRegistrationListener myRegistrationListener) {
		InMemoryPskStore pskStore = new InMemoryPskStore();
		pskStore.setKey("Client_identity", "secretPSK".getBytes());
		DtlsConnectorConfig.Builder dtlsConfigBuilder = new DtlsConnectorConfig.Builder().setPskStore(pskStore);

		LeshanServer lwServer = new LeshanServerBuilder()
				.setDtlsConfig(dtlsConfigBuilder) // set the psk for setup examination
				.disableSecuredEndpoint()
				.build();

		lwServer.getRegistrationService().addListener(myRegistrationListener);
		lwServer.start();
		
		return lwServer;
	}
	
	@Bean
	public ScheduledExecutorService getScheduledExecutorService() {
		return Executors.newSingleThreadScheduledExecutor();
	}
}

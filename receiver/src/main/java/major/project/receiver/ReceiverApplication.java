package major.project.receiver;


import major.project.receiver.aws.GetParameter;
import major.project.receiver.mqtt.ReceiverClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class ReceiverApplication  {

//	@Autowired
//	private GetParameter getParameter;

	public static void main(String[] args) {
		SpringApplication.run(ReceiverApplication.class, args);
	}


}

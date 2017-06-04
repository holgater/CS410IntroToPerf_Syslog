import java.io.Serializable;
import java.time.*;

public class Packet implements Serializable {
	private LocalTime initTime;
	private LocalTime arrivalTime;
	private LocalTime processTime;
	private String data;
	private Priority priority;

	public void SetInit(LocalTime init) {
		initTime = init;
	}

	public LocalTime GetInit() {
		return initTime;
	}

	public void SetData(String data) {
		this.data = data;
	}

	public String GetData() {
		return data;
	}

	public void SetArrivalTime(LocalTime arrival){
		arrivalTime = arrival;
	}

	public LocalTime GetArrivalTime() {
		return arrivalTime;
	}

	public void SetProcessTime(LocalTime afterProcess){
		processTime = afterProcess;
	}

	public LocalTime GetProcessTime() {
		return processTime;
	}
}

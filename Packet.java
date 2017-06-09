import java.io.Serializable;
import java.time.*;

public class Packet implements Serializable {
	private long initTime;
	private long arrivalTime;
	private long processTime;
	private String data;
	private Priority priority;

	public void SetInit(long init) {
		initTime = init;
	}

	public long GetInit() {
		return initTime;
	}

	public void SetData(String data) {
		this.data = data;
	}

	public String GetData() {
		return data;
	}

	public void SetArrivalTime(long arrival){
		arrivalTime = arrival;
	}

	public long GetArrivalTime() {
		return arrivalTime;
	}

	public void SetProcessTime(long afterProcess){
		processTime = afterProcess;
	}

	public long GetProcessTime() {
		return processTime;
	}
}

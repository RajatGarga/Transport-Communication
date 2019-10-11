package taskscheduler;

import javax.print.attribute.standard.JobPriority;

public class Job implements Runnable {
    private String jobName;
    private JobPriority jobPriority;
     
    @Override
    public void run() {
        System.out.println("Job:" + jobName +
          " Priority:" + jobPriority);
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}



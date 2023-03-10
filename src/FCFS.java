import java.util.ArrayList;
import java.util.List;

public class FCFS implements Algorithm{
    List<Task> OriginalQueue;
    List<Task> RQueue;
    int counter=0;
    double WaitTime=0;
    double TurnAroundTime=0;
    int size=0;
    FCFS(List<Task> queue)
    {
        OriginalQueue=queue;
        RQueue=queue;
    }

    FCFS()
    {
    }

    @Override
    public void schedule() {
        Task temp;
        while (!RQueue.isEmpty())
        {
            temp=pickNextTask();
            CPU.run(temp,0);
            WaitTime=WaitTime+temp.getBurst()*(RQueue.size()-1);
            TurnAroundTime=TurnAroundTime+temp.getBurst();
            temp.setBurst(0);
            if(temp.getBurst()==0)
            {
                System.out.println("Task "+temp.getName()+" finished.");
                RQueue.remove(counter);
                size++;
            }
            else counter++;

        }

        System.out.println("Average times: waiting "+WaitTime/size+", turnaround: "+(WaitTime+TurnAroundTime)/size);

    }

    @Override
    public Task pickNextTask() {

        if(RQueue.isEmpty())return null;
        else
        {
            return RQueue.get(counter);
        }
    }
}

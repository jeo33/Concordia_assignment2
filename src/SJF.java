import java.util.List;

public class SJF implements Algorithm {

    List<Task> OriginalQueue;
    List<Task> RQueue;
    int counter=0;
    double WaitTime=0;
    double TurnAroundTime=0;
    int size=0;
    SJF(List<Task> queue)
    {
        OriginalQueue=queue;
        RQueue=Sort(queue);
    }

    SJF()
    {
    }

    public List<Task> Sort(List<Task> a)
    {
        Task temp1;

        for(int i=0;i<a.size();i++)
        {
            for(int j=i;j<a.size();j++)
            {
                if(a.get(j).getBurst()<a.get(i).getBurst())
                {
                    temp1=a.get(j);
                    a.set(j,a.get(i));
                    a.set(i,temp1);
                }

            }
        }
        return a;
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

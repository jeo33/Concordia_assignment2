import java.util.List;

public class RR implements Algorithm{
    int Timeout=Integer.MAX_VALUE;
    List<Task> OriginalQueue;
    List<Task> RQueue;
    int counter=0;
    int quantum;
    double WaitTime=0;
    double TurnAroundTime=0;
    double RunTime=0;
    int size=0;
    RR(List<Task> queue,int q)
    {
        OriginalQueue=queue;
        RQueue=queue;
        quantum=q;
        size=OriginalQueue.size();
    }
    RR(List<Task> queue,int q,int timeout)
    {
        OriginalQueue=queue;
        RQueue=queue;
        quantum=q;
        size=OriginalQueue.size();
        Timeout=timeout;
    }

    @Override
    public void schedule() {
        Task temp;
        int SliceTime;
        while (!RQueue.isEmpty())
        {
            temp=pickNextTask();
            CPU.run(temp,0);
            if(temp.getBurst()<=quantum)
            {
                SliceTime=temp.getBurst();
            }
            else SliceTime=quantum;
            WaitTime=WaitTime+SliceTime*(RQueue.size()-1);
            TurnAroundTime=TurnAroundTime+SliceTime;
            temp.setBurst(temp.getBurst()-SliceTime);

            if(temp.getBurst()==0)
            {
                System.out.println("Task "+temp.getName()+" finished.");
                RQueue.remove(temp);
            }
            else
            {
                RQueue.add(temp);
                RQueue.remove(0);
            }
        }

        System.out.println("Average times: waiting "+WaitTime/size+", turnaround: "+(WaitTime+TurnAroundTime)/size);

    }


    public void fss_helper(int quantum) {
        int UserTimeOut = Timeout;
        Task temp;
        int SliceTime;
        while (!RQueue.isEmpty()& UserTimeOut !=0)
        {
            temp=pickNextTask();
            CPU.run(temp,0);
            if(temp.getBurst()<=quantum)
            {
                SliceTime=temp.getBurst();
            }
            else SliceTime=quantum;
            if(SliceTime> UserTimeOut)
            {
                SliceTime= UserTimeOut;
            }
            UserTimeOut = UserTimeOut -SliceTime;
            WaitTime=WaitTime+SliceTime*(RQueue.size()-1);
            TurnAroundTime=TurnAroundTime+SliceTime;
            temp.setBurst(temp.getBurst()-SliceTime);

            if(temp.getBurst()==0)
            {
                System.out.println("Task "+temp.getName()+" finished.");
                RQueue.remove(temp);
            }
            else
            {
                RQueue.add(temp);
                RQueue.remove(0);
            }
        }

        if(RQueue.isEmpty())
        {
            System.out.println("Average times: waiting "+WaitTime/size+", turnaround: "+(WaitTime+TurnAroundTime)/size);

        }

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

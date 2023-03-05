import java.util.*;

public class FSS implements Algorithm {


    List<Task> OriginalQueue;
    List<Task> RQueue;
    Hashtable<Integer, List<Task>> AssignUserQueue;

    List<RR> UserList=new ArrayList<>();
    int KeySize=0;
    int quantum=0;
    int UserQuantum=0;
    int counter=0;
    double WaitTime=0;
    double TurnAroundTime=0;
    int size=0;
    FSS(List<Task> queue,int UserQuantum,int Quantum)
    {
        OriginalQueue=queue;
        RQueue=queue;
        AssignUserQueue=AssignUserQueue(queue);
        this.quantum=Quantum;
        this.UserQuantum=UserQuantum;
    }
    public Hashtable<Integer, List<Task>> AssignUserQueue(List<Task> q)
    {
        Hashtable<Integer, List<Task>> my_dict=new  Hashtable<Integer, List<Task>>();
        Task temp;
        for (Task task : q) {
            temp = task;
            if (!my_dict.containsKey(temp.getPriority())) {
                List<Task> tempQueue = new ArrayList<>();
                tempQueue.add(temp);
                KeySize++;
                my_dict.put(temp.getPriority(), tempQueue);
            } else {
                List<Task> tempQueue = my_dict.get(temp.getPriority());
                tempQueue.add(temp);
            }
        }
        return my_dict;
    }


    FSS()
    {
    }




    @Override
    public void schedule() {
        int wait_time_interval=0;
        //System.out.println(AssignUserQueue.toString());

        for (Iterator<Integer> it = AssignUserQueue.keys().asIterator(); it.hasNext(); ) {
            int x = it.next();
            RR RThread=new RR(AssignUserQueue.get(x),quantum,UserQuantum);
            UserList.add(RThread);
        }
        while (!UserList.isEmpty())
        {
            for(RR x:UserList)
            {
                if(x.RQueue.isEmpty())
                {
                    UserList.remove(x);
                    break;
                }
                else {
                    wait_time_interval=x.fss_helper(quantum);
                    x.WaitTime=x.WaitTime-wait_time_interval*x.RQueue.size();
                }
                System.out.println("******************************************************");
                for(RR z:UserList)
                {
                    z.WaitTime+=wait_time_interval*z.RQueue.size();
                }
            }
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

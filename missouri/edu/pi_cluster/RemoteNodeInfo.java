package missouri.edu.pi_cluster;

public class RemoteNodeInfo extends NodeInfo{
    
    private String ipAddress = null;

    @Override
    public float getCpuTemp(){
        return cpuTemp;
    }

    @Override
    public String getStatus(){
        // TODO Auto-generated method stub
        return null;
    }

}
